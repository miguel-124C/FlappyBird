package com.flappybird.controllers;

import org.lwjgl.glfw.GLFW;

import com.flappybird.core.ConfigCore;
import com.flappybird.core.GameOverCore;
import com.flappybird.core.MenuCore;
import com.flappybird.managers.AudioManager;
import com.flappybird.utils.Direction;

public class InputManager {

    private long window;
    private final MenuCore MENU;
    private final GameOverCore GAMEOVER_CORE;

    private boolean isPrevKeyEnter = false;
    
    public InputManager(MenuCore menuCore, GameOverCore gameOverCore){
        MENU = menuCore;
        GAMEOVER_CORE = gameOverCore;
    }

    public void initialize(long window){
        this.window = window;
    }
    
    public void update(float deltaTime){
        switch (ConfigCore.getInstance().gameState) {
            case PLAYING:
                handleInGame();
                break;
            case MENU:
                handleInMenu();
                break;
            case GAME_OVER:
                handleInGameOver();
                break;
            default:
                break;
        }
    }

    public void handleInGame(){
        // if (isPressed(GLFW.GLFW_KEY_ESCAPE))
        //     GLFW.glfwSetWindowShouldClose(window, true);

        var players = ConfigCore.getInstance().getPlayers();
        for (var player : players) {
            var gameControl = player.GAME_CONTROL;
            int keyJump = gameControl.getKeyJump();
            boolean keyJumpNow = isPressed(keyJump);

            if (keyJumpNow && !gameControl.prevKey) {
                player.jump();
                AudioManager.getInstance().playSfSwoosing();
            }
            gameControl.prevKey = keyJumpNow;
        }
    }

    private void handleInMenu(){
        if (isPressed(GLFW.GLFW_KEY_ESCAPE))
            GLFW.glfwSetWindowShouldClose(window, true);

        boolean keyUpNow = isPressed(GLFW.GLFW_KEY_UP);
        boolean keyDownNow = isPressed(GLFW.GLFW_KEY_DOWN);
        boolean keyEnterNow = isPressed(GLFW.GLFW_KEY_ENTER);

        if (keyUpNow && !MENU.isPrevKeyUp)
            MENU.changeGameMode(Direction.UP);
        
        if (keyDownNow && !MENU.isPrevKeyDown)
            MENU.changeGameMode(Direction.DOWN);

        if (keyEnterNow && !isPrevKeyEnter)
            MENU.startGame();

        MENU.isPrevKeyUp = keyUpNow;
        MENU.isPrevKeyDown = keyDownNow;
        isPrevKeyEnter = keyEnterNow;
    }

    private void handleInGameOver(){
        var keyEnterNow = isPressed(GLFW.GLFW_KEY_ENTER);
        if (keyEnterNow && !isPrevKeyEnter)
            GAMEOVER_CORE.resetGame();

        isPrevKeyEnter = keyEnterNow;
    }

    private boolean isPressed(int key){
        return GLFW.glfwGetKey(window, key) == GLFW.GLFW_PRESS;
    }

}