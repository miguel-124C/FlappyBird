package com.flappybird.graphics;

import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import com.flappybird.controllers.InputManager;
import com.flappybird.models.GameCore;
import com.flappybird.utils.Constants;

public class GameLoop {

    private long window;
    private int programa;
    private int vao;
    private int vbo;

    // Uniforms de transformacion y color.
    private int uOffsetLocation;
    private int uScaleLocation;
    private int uColorLocation;
    private int uProjectionLocation;

    private final InputManager INPUT_MANAGER;
    private final GameCore GAME_CORE;

    public GameLoop(InputManager inputManager, GameCore gameCore){
        this.INPUT_MANAGER = inputManager;
        this.GAME_CORE = gameCore;

        init();
        loop();
        cleanup();
    }

    private void init() {
        // game.initialize();
        // game.loadContent();

        // Arranque de GLFW.
        if (!GLFW.glfwInit())
            throw new IllegalStateException("No se pudo iniciar GLFW");

        // Config de ventana/contexto.
        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GLFW.GLFW_TRUE);

        // Crear ventana.
        window = GLFW.glfwCreateWindow(Constants.screenWidth, Constants.screenHeight, "Flappy Bird OpenGL", 0, 0);
        if (window == 0)
            throw new RuntimeException("No se pudo crear la ventana");

        INPUT_MANAGER.initialize(window, GAME_CORE);

        // Contexto + VSync + mostrar.
        GLFW.glfwMakeContextCurrent(window);
        GLFW.glfwSwapInterval(1);
        GLFW.glfwShowWindow(window);
        // Cargar funciones OpenGL.
        GL.createCapabilities();

        // Crear pipeline y quad unitario reutilizable.
        crearShaders();
        crearQuadBase();
    }

    private void loop(){
        float lastTime = (float) GLFW.glfwGetTime();
        while (!GLFW.glfwWindowShouldClose(window)) {
            float now = (float) GLFW.glfwGetTime();
            float deltaTime = now - lastTime;
            lastTime = now;
            // Limite de dt para evitar "saltos" grandes si el frame se congela.

            // if (deltaTime > 0.033f)
            //     deltaTime = 0.033f;

            INPUT_MANAGER.update(deltaTime);
            GAME_CORE.update(deltaTime);
            render();

            // Presentar frame y leer eventos.
            GLFW.glfwSwapBuffers(window);
            GLFW.glfwPollEvents();
        }
    }

    private void cleanup() {
        GL30.glDeleteVertexArrays(vao);
        GL15.glDeleteBuffers(vbo);
        GL20.glDeleteProgram(programa);
        GLFW.glfwDestroyWindow(window);
        GLFW.glfwTerminate();
    }

    private void crearShaders() {
        String vertexSrc = """
            #version 330 core
            layout (location = 0) in vec3 aPos;
            uniform vec2 uOffset;
            uniform vec2 uScale;
            uniform mat4 uProjection;
            void main() {
                vec2 finalPos = aPos.xy * uScale + uOffset;
                gl_Position = uProjection * vec4(finalPos, aPos.z, 1.0);
            }
            """;

        // Color solido por objeto.
        String fragmentSrc = """
            #version 330 core
            uniform vec3 uColor;
            out vec4 fragColor;
            void main() {
                fragColor = vec4(uColor, 1.0);
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
     * - Rango x,y de -0.5 a +0.5.
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
        vao = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vao);

        // VBO.
        vbo = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);

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

    private void render() {
        // Cielo.
        GL11.glClearColor(0.52f, 0.80f, 0.92f, 1.0f);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

        // Activar pipeline y malla base.
        GL20.glUseProgram(programa);
        GL30.glBindVertexArray(vao);

        Matrix4f projection = new Matrix4f().ortho(0, Constants.screenWidth, Constants.screenHeight, 0, -1, 1); // 0,0 arriba izquierda
        float[] matrixBuffer = new float[16];
        projection.get(matrixBuffer);
        GL20.glUniformMatrix4fv(uProjectionLocation, false, matrixBuffer);

        var world = GAME_CORE.world;
        for (var pipe : world.getPipes()) {
            var dimension = pipe.getDimensions();
            dibujarRect(dimension.X, dimension.Y, dimension.WIDTH, dimension.HEIGHT, 0.18f, 0.70f, 0.25f);
        }

        var bird = world.Bird;
        var dimension = bird.getDimensions();

        dibujarRect(dimension.X, dimension.Y, dimension.WIDTH, dimension.HEIGHT, 0.98f, 0.85f, 0.20f);
    }

    // Helper de dibujo parametrico de rectangulos.
    private void dibujarRect(float x, float y, float ancho, float alto, float r, float g, float b) {
        // Traslacion del quad.
        GL20.glUniform2f(uOffsetLocation, x, y);
        // Escala del quad.
        GL20.glUniform2f(uScaleLocation, ancho, alto);
        // Color.
        GL20.glUniform3f(uColorLocation, r, g, b);
        // Dibujar 2 triangulos.
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 6);
    }
}