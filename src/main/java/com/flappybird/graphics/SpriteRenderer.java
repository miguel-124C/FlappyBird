package com.flappybird.graphics;

import com.flappybird.utils.Vector2;

import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import com.flappybird.interfaces.Sprite;
import com.flappybird.utils.Constants;

public class SpriteRenderer {

    private int programa;
    private int vao;
    private int vbo;

    // Uniforms de transformacion y color.
    private int uOffsetLocation;
    private int uScaleLocation;
    private int uColorLocation;
    public int uTextureLocation;
    private int uProjectionLocation;
    private int uRotationLocation;

    // Uniforms para el recorte de la textura (UVs)
    private int uTexOffsetLocation;
    private int uTexScaleLocation;

    public void draw(
        Vector2 position,
        Sprite sprite,
        Vector2 scale
    ) {
        // Activar pipeline y malla base.
        GL20.glUseProgram(programa);
        GL30.glBindVertexArray(vao);

        // Desactivar estados que puedan ocultar la UI
        GL11.glDisable(GL11.GL_DEPTH_TEST); // Ignorar la profundidad (dibujar por encima de todo)
        GL11.glDisable(GL11.GL_CULL_FACE);  // Dibujar ambas caras de los triángulos

        Matrix4f projection = new Matrix4f().ortho(0, Constants.screenWidth, Constants.screenHeight, 0, -1, 1); // 0,0 arriba izquierda
        float[] matrixBuffer = new float[16];
        projection.get(matrixBuffer);
        GL20.glUniformMatrix4fv(uProjectionLocation, false, matrixBuffer);
        
        var texture = sprite.TEXTURE;
        var sourceRect = sprite.SOURCET_RECTANGLE;

        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        texture.bind();
        GL20.glUniform1i(uTextureLocation, 0); // sampler2D = unidad 0

        // --- CALCULAR RECORTE UV (NUEVO) ---
        // Necesitamos el tamaño total de la textura para normalizar los valores de 0 a 1
        float texWidth = texture.getWidth();   // Ajusta según cómo obtengas el ancho en tu clase Texture
        float texHeight = texture.getHeight(); // Ajusta según cómo obtengas el alto en tu clase Texture

        // Mapeo: píxeles -> normalizado (0.0 a 1.0)
        float uvX = sourceRect.X / texWidth;      // Asumiendo que tu Rectangle tiene .x
        float uvY = sourceRect.Y / texHeight;     // Asumiendo que tu Rectangle tiene .y
        float uvWidth = sourceRect.WIDTH / texWidth;
        float uvHeight = sourceRect.HEIGHT / texHeight;

        // Enviamos el recorte al shader
        GL20.glUniform2f(uTexOffsetLocation, uvX, uvY);
        GL20.glUniform2f(uTexScaleLocation, uvWidth, uvHeight);

        GL20.glUniform4f(uColorLocation, 1f, 1f, 1f, 1f); // sin tinte
        GL20.glUniform2f(uOffsetLocation, position.x(), position.y());

        float rotationInRadians = (float)Math.toRadians(sprite.rotation);
        GL20.glUniform1f(uRotationLocation, rotationInRadians);

        // Multiplicamos el tamaño base por la escala deseada
        GL20.glUniform2f(uScaleLocation, sourceRect.WIDTH * scale.x(), sourceRect.HEIGHT * scale.y());
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 6);
        texture.unbind();
    }

    // @Override
    public void initialize() {
        crearShaders();
        crearQuadBase();

        // Habilitar el canal Alpha para la transparencia
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    }

    private void crearShaders() {
        String vertexSrc = """
            #version 330 core
            layout (location = 0) in vec3 aPos;
            layout (location = 1) in vec2 aTextCoord;

            // Transformacion de geometria
            uniform vec2 uOffset;
            uniform vec2 uScale;
            uniform mat4 uProjection;

            // Transformacion de textura
            uniform vec2 uTexOffset;
            uniform vec2 uTexScale;
            uniform float uRotation;

            out vec2 vTextCoord;

            void main() {
                vTextCoord = (aTextCoord * uTexScale) + uTexOffset;

                float c = cos(uRotation);
                float s = sin(uRotation);
                mat2 rotMat = mat2(c, -s, s, c);
                vec2 pos = rotMat * aPos.xy;

                vec2 finalPos = pos * uScale + uOffset;
                gl_Position = uProjection * vec4(finalPos, aPos.z, 1.0);
            }
            """;

        // void main() {
        //     vTextCoord = (aTextCoord * uTexScale) + uTexOffset;
            
        //     // 1. Mover el centro del quad (0.5, 0.5) al origen (0,0)
        //     vec2 pos = aPos.xy - vec2(0.5, 0.5);
            
        //     // 2. Calcular la matriz de rotación 2D
        //     float c = cos(uRotation);
        //     float s = sin(uRotation);
        //     mat2 rotMat = mat2(c, -s, s, c);
            
        //     // 3. Aplicar rotación y devolver el centro a su lugar
        //     pos = (rotMat * pos) + vec2(0.5, 0.5);
            
        //     // 4. Aplicar escala y posición final
        //     vec2 finalPos = pos * uScale + uOffset;
        //     gl_Position = uProjection * vec4(finalPos, aPos.z, 1.0);
        // }

        // Color solido por objeto.
        String fragmentSrc = """
            #version 330 core
            in vec2 vTextCoord;

            uniform sampler2D uTexture;
            uniform vec4 uColor;
            out vec4 fragColor;
            void main() {
                fragColor = texture(uTexture, vTextCoord) * uColor;
            }
            """;

        // Compilar vertex shader.
        int vertexShader = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
        GL20.glShaderSource(vertexShader, vertexSrc);
        GL20.glCompileShader(vertexShader);
        comprobarShader(vertexShader, "Vertex");

        // Compilar fragment shader.
        int fragmentShader = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
        GL20.glShaderSource(fragmentShader, fragmentSrc);
        GL20.glCompileShader(fragmentShader);
        comprobarShader(fragmentShader, "Fragment");

        // Link de programa.
        programa = GL20.glCreateProgram();
        GL20.glAttachShader(programa, vertexShader);
        GL20.glAttachShader(programa, fragmentShader);
        GL20.glLinkProgram(programa);

        if (GL20.glGetProgrami(programa, GL20.GL_LINK_STATUS) == GL11.GL_FALSE) {
            throw new RuntimeException("Error al enlazar programa: " + GL20.glGetProgramInfoLog(programa));
        }

        // Resolver uniforms.
        uOffsetLocation = GL20.glGetUniformLocation(programa, "uOffset");
        uScaleLocation = GL20.glGetUniformLocation(programa, "uScale");
        uColorLocation = GL20.glGetUniformLocation(programa, "uColor");
        uTextureLocation = GL20.glGetUniformLocation(programa, "uTexture");
        uProjectionLocation = GL20.glGetUniformLocation(programa, "uProjection");
        uTexOffsetLocation = GL20.glGetUniformLocation(programa, "uTexOffset");
        uTexScaleLocation = GL20.glGetUniformLocation(programa, "uTexScale");
        uRotationLocation = GL20.glGetUniformLocation(programa, "uRotation");

        if (uOffsetLocation == -1 || uScaleLocation == -1 || uColorLocation == -1) {
            throw new RuntimeException("No se pudieron obtener uniforms del shader");
        }

        // Limpiar objetos shader temporales.
        GL20.glDeleteShader(vertexShader);
        GL20.glDeleteShader(fragmentShader);
    }

    // Verificacion de compilacion GLSL.
    private void comprobarShader(int shader, String tipo) {
        if (GL20.glGetShaderi(shader, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            throw new RuntimeException(tipo + " shader: " + GL20.glGetShaderInfoLog(shader));
        }
    }

    private void crearQuadBase() {
        float[] vertices = {
            // X     Y     Z      U     V
            0.0f, 0.0f, 0.0f,  0.0f, 0.0f,  // Arriba Izquierda
            1.0f, 0.0f, 0.0f,  1.0f, 0.0f,  // Arriba Derecha
            1.0f, 1.0f, 0.0f,  1.0f, 1.0f,  // Abajo Derecha
            0.0f, 0.0f, 0.0f,  0.0f, 0.0f,
            1.0f, 1.0f, 0.0f,  1.0f, 1.0f,
            0.0f, 1.0f, 0.0f,  0.0f, 1.0f
        };

        // VAO.
        vao = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vao);

        int stride = 5 * Float.BYTES; // XYZ + UV = 5 floats

        // VBO.
        vbo = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);

        // Subida de vertices.
        FloatBuffer buffer = BufferUtils.createFloatBuffer(vertices.length);
        buffer.put(vertices).flip();
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);

        // Atributo posicion.
        // Posición (location 0) — igual que antes
        GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, stride, 0);
        GL20.glEnableVertexAttribArray(0);

        // UV (location 1) — empieza después de los 3 floats de posición
        GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, stride, 3 * Float.BYTES);
        GL20.glEnableVertexAttribArray(1);

        // Desbind.
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL30.glBindVertexArray(0);
    }

    public void cleanUp() {
        GL30.glDeleteVertexArrays(vao);
        GL15.glDeleteBuffers(vbo);
        GL20.glDeleteProgram(programa);
    }
}