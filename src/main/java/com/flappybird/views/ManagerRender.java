package com.flappybird.views;

import com.flappybird.graphics.SpriteRenderer;
import com.flappybird.interfaces.State;
import com.flappybird.models.GameCore;

public class ManagerRender implements IRender {

    private State state;
    private final MenuRender menuRender;
    private final IRender gameRender;

    public ManagerRender(GameCore gameCore, IRender gameRender){
        state = gameCore.getState();
        menuRender = new MenuRender();
        this.gameRender = gameRender;
    }

    @Override
    public void draw(SpriteRenderer spriteRender) {
        switch (state) {
            case MENU:
                menuRender.draw(spriteRender);
                break;
            case PLAYING:
                gameRender.draw(spriteRender);
            case PAUSE:
                // Draw pause
                gameRender.draw(spriteRender);
            case GAME_OVER:
                // Draw Gameover
                gameRender.draw(spriteRender);
            default:
                break;
        }
    }

    @Override
    public void initialize() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'initialize'");
    }

    @Override
    public void cleanUp() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cleanUp'");
    }

}