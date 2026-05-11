package com.flappybird.views;

import com.flappybird.factories.BirdFactory;
import com.flappybird.graphics.SpriteRenderer;
import com.flappybird.utils.Rectangle;
import com.flappybird.utils.Texture;
import com.flappybird.utils.Vector2;

public class MenuRender implements IRender {

    private Texture birdTexture;
    private final SpriteRenderer RENDER = new SpriteRenderer();
    private final BirdFactory BIRD_FACTORY = new BirdFactory();

    @Override
    public void draw(float deltaTime) {
        var scale = new Vector2(3,3);
        var sourceRect = new Rectangle(264, 64, 18, 12);
        RENDER.draw(birdTexture, new Vector2(0 ,0), scale, sourceRect);
    }

    @Override
    public void initialize() {
        RENDER.initialize();
        BIRD_FACTORY.create(new Vector2(500,500), "assets/sprites.png");
        birdTexture = new Texture("assets/sprites.png");
    }

    @Override
    public void cleanUp() {
        RENDER.cleanUp();
    }
    
}