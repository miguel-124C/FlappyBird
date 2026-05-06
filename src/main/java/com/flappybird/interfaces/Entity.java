package com.flappybird.interfaces;

import com.flappybird.utils.Rectangle;
import com.flappybird.utils.Vector2;

public abstract class Entity {
    public Vector2 position;
    public Vector2 scale;
    public Sprite sprite;

    public Rectangle getDimensions(){
        var sourceRectangle = sprite.sourceRectangle;

        return
            new Rectangle(position.x(), position.y(), sourceRectangle.WIDTH, sourceRectangle.HEIGHT);
    }

    public Entity(Vector2 position, Sprite sprite){
        this.position = position;
        this.sprite = sprite;
    }
}