package com.flappybird.core;

import com.flappybird.core.Game.GameCore;

public class CoreManager implements ICore {

    GameCore gameCore;
    MenuCore menuCore;
    GameOverCore gameOverCore;
    HudCore hudCore;

    public CoreManager(GameCore gameCore, MenuCore menuCore, GameOverCore gameOverCore, HudCore hudCore){
        this.gameCore = gameCore;
        this.menuCore = menuCore;
        this.gameOverCore = gameOverCore;
        this.hudCore = hudCore;
    }

    @Override
    public void initialize() {
        menuCore.initialize();
        gameCore.initialize();
        gameOverCore.initialize();
        hudCore.initialize();
    }

    @Override
    public void update(float deltaTime) {
        switch (ConfigCore.getInstance().gameState) {
            case PLAYING:
                gameCore.update(deltaTime);
                hudCore.update(deltaTime);
                break;
            case MENU:
                menuCore.update(deltaTime);
                break;
            case GAME_OVER:
                gameOverCore.update(deltaTime);
                break;
            default:
                break;
        }
    }
    
}