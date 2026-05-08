package com.flappybird.models;

import com.flappybird.interfaces.Entity;
import com.flappybird.interfaces.Sprite;
import com.flappybird.utils.Vector2;

public class PipeEntity extends Entity {

    public boolean isBehind = false;

    public PipeEntity(Vector2 position, Sprite sprite){
        super(position, sprite);
    }

    public void moveLeft(float distance){
        this.position = new Vector2(
            this.position.x() - distance,
            this.position.y()
        );
    }

}