package com.flappybird.views;

import com.flappybird.core.GameOverCore;
import com.flappybird.graphics.BasicRender;
import com.flappybird.graphics.SpriteRenderer;
import com.flappybird.utils.*;

public class GameOverRender implements IRender {

    private final GameOverCore GAME_OVER_CORE;
    private final BasicRender BASIC_RENDER;
    private final SpriteRenderer SPRITE_RENDER;

    public GameOverRender(GameOverCore gameOverCore, BasicRender basicRender, SpriteRenderer spriteRender){
        BASIC_RENDER = basicRender;
        SPRITE_RENDER = spriteRender;
        GAME_OVER_CORE = gameOverCore;
    }

    @Override
    public void initialize() {
        BASIC_RENDER.initialize();
        SPRITE_RENDER.initialize();
    }

    @Override
    public void draw(float deltaTime) {
        // Draw dark modal
        BASIC_RENDER.draw(deltaTime);
        var souRectangle = new Rectangle(0, 0, Constants.screenWidth, Constants.screenHeight);
        BASIC_RENDER.dibujarRect(souRectangle, Color.custom(0, 0, 0, 0.5f));

        for (var entity : GAME_OVER_CORE.getEntities())
            SPRITE_RENDER.draw(entity.position, entity.sprite, entity.scale);

    }

    @Override
    public void cleanUp() {
        BASIC_RENDER.cleanUp();
        SPRITE_RENDER.cleanUp();
    }
    
}
