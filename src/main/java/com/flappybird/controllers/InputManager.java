package com.flappybird.controllers;

import org.lwjgl.glfw.GLFW;

import com.flappybird.models.GameCore;

public class InputManager {

    private long window;
    private GameCore gameCore;
    private boolean prevSpace;
    
    public void initialize(long window, GameCore gameCore){
        this.window = window;
        this.gameCore = gameCore;
    }
    
    public void update(float deltaTime){
        switch (gameCore.getState()) {
            case PLAYING:
                handleSpace(deltaTime);
                break;
            default:
                break;
        }
    }

    public void handleSpace(float deltaTime){
        if (isPressed(GLFW.GLFW_KEY_ESCAPE))
            GLFW.glfwSetWindowShouldClose(window, true);

        boolean spaceAhora = isPressed(GLFW.GLFW_KEY_SPACE);
        if (spaceAhora && !prevSpace) {
            var bird = gameCore.world.Bird;
            bird.jump(deltaTime);
        }
        prevSpace = spaceAhora;
    }

    private boolean isPressed(int key){
        return GLFW.glfwGetKey(window, key) == GLFW.GLFW_PRESS;
    }

}