package com.flappybird.graphics;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;

import com.flappybird.controllers.InputManager;
import com.flappybird.models.GameCore;
import com.flappybird.utils.Constants;
import com.flappybird.views.ManagerRender;
import com.flappybird.interfaces.*;;

public class GameLoop {

    private long window;

    private final InputManager INPUT_MANAGER;
    private final GameCore GAME_CORE;
    private final ManagerRender MANAGER_RENDER;

    public GameLoop(InputManager inputManager, GameCore gameCore, ManagerRender mr){
        this.INPUT_MANAGER = inputManager;
        this.GAME_CORE = gameCore;
        MANAGER_RENDER = mr;

        init();
        MANAGER_RENDER.initialize();
        loop();
        MANAGER_RENDER.cleanUp();
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

        INPUT_MANAGER.initialize(window, GAME_CORE);

        // Contexto + VSync + mostrar.
        GLFW.glfwMakeContextCurrent(window);
        GLFW.glfwSwapInterval(1);
        GLFW.glfwShowWindow(window);
        // Cargar funciones OpenGL.
        GL.createCapabilities();
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

            if (GAME_CORE.getState() == State.PLAYING) {
                GAME_CORE.update(deltaTime);
            }
            MANAGER_RENDER.draw(null);

            // Presentar frame y leer eventos.
            GLFW.glfwSwapBuffers(window);
            GLFW.glfwPollEvents();
        }
    }

}