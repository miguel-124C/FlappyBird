package com.flappybird.controllers;

import org.lwjgl.glfw.GLFW;

import com.flappybird.core.ConfigCore;

public class InputManager {

    private long window;
    
    public InputManager(){
    }

    public void initialize(long window){
        this.window = window;
    }
    
    public void update(float deltaTime){
        switch (ConfigCore.getInstance().gameState) {
            case PLAYING:
                handleInGame(deltaTime);
                break;
            case PAUSE:
                handleInPause(deltaTime);
            default:
                break;
        }
    }

    public void handleInGame(float deltaTime){
        if (isPressed(GLFW.GLFW_KEY_ESCAPE))
            GLFW.glfwSetWindowShouldClose(window, true);

        var players = ConfigCore.getInstance().getPlayers();
        for (var player : players) {
            var gameControl = player.GAME_CONTROL;
            int keyJump = gameControl.getKeyJump();
            boolean keyJumpNow = isPressed(keyJump);

            if (keyJumpNow && !gameControl.prevKey) {
                player.jump();
            }
            gameControl.prevKey = keyJumpNow;
        }
    }

    private void handleInPause(float deltaTime){
        
    }

    private boolean isPressed(int key){
        return GLFW.glfwGetKey(window, key) == GLFW.GLFW_PRESS;
    }

}