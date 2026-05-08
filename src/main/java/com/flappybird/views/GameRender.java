package com.flappybird.views;

import com.flappybird.graphics.SpriteRenderer;
import com.flappybird.models.World;

public class GameRender implements IRender {

    private final World WORLD_GAME;

    public GameRender(World world){
        this.WORLD_GAME = world;
    }

    public void draw(SpriteRenderer spriteRenderer){
        drawBird();
        drawPipes();
    }

    private void drawBird(){
        var source = WORLD_GAME.Bird.sprite.sourceRectangle;
    }

    private void drawPipes(){
        for (var pipe : WORLD_GAME.getPipes()) {
            var source = pipe.sprite.sourceRectangle;
        }
    }

    @Override
    public void initialize() {
        throw new UnsupportedOperationException("Unimplemented method 'initialize'");
    }

    @Override
    public void cleanUp() {
        throw new UnsupportedOperationException("Unimplemented method 'cleanUp'");
    }
}
