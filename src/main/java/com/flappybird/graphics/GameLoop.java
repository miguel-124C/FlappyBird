package com.flappybird.graphics;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;

import com.flappybird.controllers.InputManager;
import com.flappybird.core.CoreManager;
import com.flappybird.utils.Constants;
import com.flappybird.views.RenderManager;

public class GameLoop {

    private long window;

    private final InputManager INPUT_MANAGER;
    private final CoreManager CORE_MANAGER;
    private final RenderManager RENDER_MANAGER;

    public GameLoop(InputManager inputManager, CoreManager coreManager, RenderManager mr){
        this.INPUT_MANAGER = inputManager;
        this.CORE_MANAGER = coreManager;
        RENDER_MANAGER = mr;

        init();
        loop();
        RENDER_MANAGER.cleanUp();
        GLFW.glfwDestroyWindow(window);
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

        // Contexto + VSync + mostrar.
        GLFW.glfwMakeContextCurrent(window);
        GLFW.glfwSwapInterval(1);
        GLFW.glfwShowWindow(window);
        // Cargar funciones OpenGL.
        GL.createCapabilities();

        INPUT_MANAGER.initialize(window);
        CORE_MANAGER.initialize();
        RENDER_MANAGER.initialize();
        // ConfigCore.getInstance().gameState = GameState.PLAYING;
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
            CORE_MANAGER.update(deltaTime);
            RENDER_MANAGER.draw(deltaTime);

            // Presentar frame y leer eventos.
            GLFW.glfwSwapBuffers(window);
            GLFW.glfwPollEvents();
        }
    }

}