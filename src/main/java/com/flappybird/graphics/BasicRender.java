package com.flappybird.graphics;

import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import com.flappybird.utils.Color;
import com.flappybird.utils.Constants;
import com.flappybird.utils.Rectangle;
import com.flappybird.views.IRender;

public class BasicRender implements IRender {
    private int programa;
    
    // IDs para el Cuadrado (usado por Rectángulos y Círculos)
    private int vaoQuad;
    private int vboQuad;
    
    // IDs para el Triángulo
    private int vaoTri;
    private int vboTri;

    // Uniforms de transformacion y color.
    private int uOffsetLocation;
    private int uScaleLocation;
    private int uColorLocation;
    private int uProjectionLocation;
    private int uIsCircleLocation;

    @Override
    public void draw(float deltaTime) {
        // Activar pipeline y malla base.
        GL20.glUseProgram(programa);

        // Desactivar estados que puedan ocultar la UI
        GL11.glDisable(GL11.GL_DEPTH_TEST); // Ignorar la profundidad (dibujar por encima de todo)
        GL11.glDisable(GL11.GL_CULL_FACE);  // Dibujar ambas caras de los triángulos

        // Activar la transparencia
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        Matrix4f projection = new Matrix4f().ortho(0, Constants.screenWidth, Constants.screenHeight, 0, -1, 1); // 0,0 arriba izquierda
        float[] matrixBuffer = new float[16];
        projection.get(matrixBuffer);
        GL20.glUniformMatrix4fv(uProjectionLocation, false, matrixBuffer);
    }

    // Helper de dibujo parametrico de rectangulos.
    public void dibujarRect(Rectangle souRectangle, Color color) {
        GL30.glBindVertexArray(vaoQuad);
        // Traslacion del quad.
        GL20.glUniform2f(uOffsetLocation, souRectangle.X, souRectangle.Y);
        // Escala del quad.
        GL20.glUniform2f(uScaleLocation, souRectangle.WIDTH, souRectangle.HEIGHT);
        // Color.
        GL20.glUniform4f(uColorLocation, color.R, color.G, color.B, color.ALPHA);

        // Apagamos el modo círculo (0)
        GL20.glUniform1i(uIsCircleLocation, 0);
        // Dibujar 2 triangulos.
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 6);
    }

    // Dibuja un circulo perfecto dentro del area del rectangulo
    public void dibujarCirculo(Rectangle bounds, Color color) {
        GL30.glBindVertexArray(vaoQuad);

        GL20.glUniform2f(uOffsetLocation, bounds.X, bounds.Y);
        GL20.glUniform2f(uScaleLocation, bounds.WIDTH, bounds.HEIGHT);
        GL20.glUniform4f(uColorLocation, color.R, color.G, color.B, color.ALPHA);
        
        // Encendemos el modo círculo (1)
        GL20.glUniform1i(uIsCircleLocation, 1); 
        
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 6);
    }

    public void dibujarTriangulo(Rectangle bounds, Color color) {
        // 1. Enlazar la malla específica del triángulo
        GL30.glBindVertexArray(vaoTri);
        
        GL20.glUniform2f(uOffsetLocation, bounds.X, bounds.Y);
        GL20.glUniform2f(uScaleLocation, bounds.WIDTH, bounds.HEIGHT);
        GL20.glUniform4f(uColorLocation, color.R, color.G, color.B, color.ALPHA);
        GL20.glUniform1i(uIsCircleLocation, 0); // Apagar modo círculo
        
        // Dibujar solo 3 vértices
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 3);
    }

    @Override
    public void initialize() {
        crearShaders();
        crearQuadBase();
        crearTrianguloBase();
    }

    private void crearShaders() {
        String vertexSrc = """
            #version 330 core
            layout (location = 0) in vec3 aPos;
            uniform vec2 uOffset;
            uniform vec2 uScale;
            uniform mat4 uProjection;

            out vec2 vLocalPos;

            void main() {
                vLocalPos = aPos.xy;
                vec2 finalPos = aPos.xy * uScale + uOffset;
                gl_Position = uProjection * vec4(finalPos, aPos.z, 1.0);
            }
            """;

        // Color solido por objeto.
        String fragmentSrc = """
            #version 330 core
            in vec2 vLocalPos;

            uniform vec4 uColor;
            uniform int uIsCircle;
            out vec4 fragColor;
            void main() {
                if (uIsCircle == 1) {
                    // Calculamos la distancia al centro (0.5, 0.5)
                    float dist = distance(vLocalPos, vec2(0.5, 0.5));
                    if(dist > 0.5) {
                        discard; // Cortar esquinas
                    }
                }

                fragColor = uColor;
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
        uProjectionLocation = GL20.glGetUniformLocation(programa, "uProjection");
        uIsCircleLocation = GL20.glGetUniformLocation(programa, "uIsCircle");

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

    /**
     * Crea un rectangulo unitario centrado en origen:
     * - Rango x,y de 0 a +1.
     * - 2 triangulos (6 vertices).
     * Cualquier objeto 2D se dibuja escalando y moviendo este quad.
     */
    private void crearQuadBase() {
        float[] vertices = {
            0.0f, 0.0f, 0.0f, // Arriba Izquierda
            1.0f, 0.0f, 0.0f, // Arriba Derecha
            1.0f, 1.0f, 0.0f, // Abajo Derecha
            0.0f, 0.0f, 0.0f,
            1.0f, 1.0f, 0.0f,
            0.0f, 1.0f, 0.0f
        };

        // VAO.
        vaoQuad = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vaoQuad);

        // VBO.
        vboQuad = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboQuad);

        // Subida de vertices.
        FloatBuffer buffer = BufferUtils.createFloatBuffer(vertices.length);
        buffer.put(vertices).flip();
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);

        // Atributo posicion.
        GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 3 * Float.BYTES, 0);
        GL20.glEnableVertexAttribArray(0);

        // Desbind.
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL30.glBindVertexArray(0);
    }

    private void crearTrianguloBase() {
        float[] vertices = {
            0.5f, 0.0f, 0.0f, // Punta superior central
            1.0f, 1.0f, 0.0f, // Esquina inferior derecha
            0.0f, 1.0f, 0.0f  // Esquina inferior izquierda
        };

        vaoTri = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vaoTri);

        vboTri = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboTri);

        FloatBuffer buffer = BufferUtils.createFloatBuffer(vertices.length);
        buffer.put(vertices).flip();
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);

        GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 3 * Float.BYTES, 0);
        GL20.glEnableVertexAttribArray(0);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL30.glBindVertexArray(0);
    }

    @Override
    public void cleanUp() {
        GL30.glDeleteVertexArrays(vaoQuad);
        GL15.glDeleteBuffers(vboQuad);
        GL30.glDeleteVertexArrays(vaoTri);
        GL15.glDeleteBuffers(vboTri);
        GL20.glDeleteProgram(programa);
    }
}