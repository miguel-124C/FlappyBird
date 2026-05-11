package com.flappybird.core;

public class CoreManager implements ICore {

    GameCore gameCore;
    MenuCore menuCore;

    public CoreManager(GameCore gameCore, MenuCore menuCore){
        this.gameCore = gameCore;
        this.menuCore = menuCore;
    }

    @Override
    public void initialize() {
        gameCore.initialize();
        menuCore.initialize();
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
            default:
                break;
        }
    }

    @Override
    public void reset() {
        gameCore.reset();
        menuCore.reset();
    }
    
}