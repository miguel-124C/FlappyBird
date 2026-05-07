package com.flappybird.interfaces;

import com.flappybird.utils.*;

public class Sprite {
    Texture texture;
    public Rectangle sourceRectangle;

    public Sprite(Texture texture, Rectangle sourceRectangle) {
        this.texture = texture;
        this.sourceRectangle = sourceRectangle;
    }
}
