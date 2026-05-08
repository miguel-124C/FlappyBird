package com.flappybird.views;

import com.flappybird.graphics.SpriteRenderer;

public interface IRender {

    public void initialize();
    public void draw(SpriteRenderer spriteRender);
    public void cleanUp();

}