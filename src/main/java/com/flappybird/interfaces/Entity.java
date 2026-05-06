package com.flappybird.interfaces;

import com.flappybird.utils.Rectangle;
import com.flappybird.utils.Vector2;

public abstract class Entity {
    public Vector2 position;
    public Rectangle sourceRectangle; // Para mapear los sprites

    public Rectangle getDimensions(){
        return
            new Rectangle(position.x(), position.y(), sourceRectangle.WIDTH, sourceRectangle.HEIGHT);
    }

    public Entity(Vector2 position, Rectangle souRectangle){
        this.position = position;
        this.sourceRectangle = souRectangle;
    }
}