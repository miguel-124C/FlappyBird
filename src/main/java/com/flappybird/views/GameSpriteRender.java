package com.flappybird.views;

import com.flappybird.core.ConfigCore;
import com.flappybird.core.Game.GameSpriteCore;
import com.flappybird.graphics.SpriteRenderer;

public class GameSpriteRender implements IRender {

    private final GameSpriteCore GAME_CORE;
    private final SpriteRenderer RENDER;

    public GameSpriteRender(GameSpriteCore gameCore, SpriteRenderer render){
        this.GAME_CORE = gameCore;
        RENDER = render;
    }

    @Override
    public void initialize() {
        RENDER.initialize();
    }

    @Override
    public void draw(float deltaTime){
       for (var entity : GAME_CORE.getEntities()) {
            RENDER.draw(entity.position, entity.sprite, entity.scale);
       }
       for (var pipe : GAME_CORE.world.getPipes()) {
            RENDER.draw(pipe.position, pipe.sprite, pipe.scale);
       }
       for (var player : ConfigCore.getInstance().getPlayers()) {
            var bird = player.BIRD;
            RENDER.draw(bird.position, bird.sprite, bird.scale);
       }
    }

    @Override
    public void cleanUp() {
        RENDER.cleanUp();
    }
}
