package com.flappybird.controllers;

import org.lwjgl.glfw.GLFW;

import com.flappybird.core.ConfigCore;
import com.flappybird.core.MenuCore;
import com.flappybird.utils.Direction;

public class InputManager {

    private long window;
    private final MenuCore MENU;
    
    public InputManager(MenuCore menuCore){
        MENU = menuCore;
    }

    public void initialize(long window){
        this.window = window;
    }
    
    public void update(float deltaTime){
        switch (ConfigCore.getInstance().gameState) {
            case PLAYING:
                handleInGame(deltaTime);
                break;
            case MENU:
                handleInMenu(deltaTime);
            case PAUSE:
                handleInPause(deltaTime);
            default:
                break;
        }
    }

    public void handleInGame(float deltaTime){
        // if (isPressed(GLFW.GLFW_KEY_ESCAPE))
        //     GLFW.glfwSetWindowShouldClose(window, true);

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

    private void handleInMenu(float deltaTime){
        if (isPressed(GLFW.GLFW_KEY_ESCAPE))
            GLFW.glfwSetWindowShouldClose(window, true);

        boolean keyUpNow = isPressed(GLFW.GLFW_KEY_UP);
        boolean keyDownNow = isPressed(GLFW.GLFW_KEY_DOWN);
        boolean keyEnterNow = isPressed(GLFW.GLFW_KEY_ENTER);

        if (keyUpNow && !MENU.isPrevKeyUp)
            MENU.changeGameMode(Direction.UP);
        
        if (keyDownNow && !MENU.isPrevKeyDown)
            MENU.changeGameMode(Direction.DOWN);

        if (keyEnterNow)
            MENU.startGame();

        MENU.isPrevKeyUp = keyUpNow;
        MENU.isPrevKeyDown = keyDownNow;
        MENU.isPrevKeyEnter = keyEnterNow;
    }

    private void handleInPause(float deltaTime){
        
    }

    private boolean isPressed(int key){
        return GLFW.glfwGetKey(window, key) == GLFW.GLFW_PRESS;
    }

}