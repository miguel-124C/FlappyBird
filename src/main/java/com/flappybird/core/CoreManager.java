package com.flappybird.core;

import com.flappybird.core.Game.GameCore;

public class CoreManager implements ICore {

    GameCore gameCore;
    MenuCore menuCore;
    GameOverCore gameOverCore;

    public CoreManager(GameCore gameCore, MenuCore menuCore, GameOverCore gameOverCore){
        this.gameCore = gameCore;
        this.menuCore = menuCore;
        this.gameOverCore = gameOverCore;
    }

    @Override
    public void initialize() {
        menuCore.initialize();
        gameCore.initialize();
        gameOverCore.initialize();
    }

    @Override
    public void update(float deltaTime) {
        switch (ConfigCore.getInstance().gameState) {
            case PLAYING:
                gameCore.update(deltaTime);
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