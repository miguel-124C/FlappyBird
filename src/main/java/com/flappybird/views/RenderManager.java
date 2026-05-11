package com.flappybird.views;

import com.flappybird.core.ConfigCore;

public class RenderManager implements IRender {

    private final MenuRender menuRender;
    private final IRender gameRender;

    public RenderManager(IRender gameRender){
        menuRender = new MenuRender();
        this.gameRender = gameRender;
    }

    @Override
    public void draw(float deltaTime) {
        switch (ConfigCore.getInstance().gameState) {
            case MENU:
                menuRender.draw(deltaTime);
                break;
            case PLAYING:
                gameRender.draw(deltaTime);
            case PAUSE:
                // Draw pause
                gameRender.draw(deltaTime);
            case GAME_OVER:
                // Draw Gameover
                gameRender.draw(deltaTime);
            default:
                break;
        }
    }

    @Override
    public void initialize() {
        menuRender.initialize();
        gameRender.initialize();
    }

    @Override
    public void cleanUp() {
        menuRender.cleanUp();
        gameRender.cleanUp();
    }

}