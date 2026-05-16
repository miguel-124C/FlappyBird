package com.flappybird.views;

import com.flappybird.core.MenuCore;
import com.flappybird.graphics.SpriteRenderer;
import com.flappybird.utils.Global;

public class MenuRender implements IRender {

    private final MenuCore MENU;
    private final SpriteRenderer RENDER;

    public MenuRender(MenuCore menuCore, SpriteRenderer render){
        MENU = menuCore;
        this.RENDER = render;
    }

    @Override
    public void draw(float deltaTime) {
        for (var entity : MENU.getEntities()) {
            var spriteToDraw = Global.getSpriteToDraw(entity, 0);
            RENDER.draw(entity.position, spriteToDraw,entity.scale);
        }
    }

    @Override
    public void initialize() {
        RENDER.initialize();
    }

    @Override
    public void cleanUp() {
        RENDER.cleanUp();
    }
    
}