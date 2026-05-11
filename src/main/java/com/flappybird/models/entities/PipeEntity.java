package com.flappybird.models.entities;

import com.flappybird.interfaces.Entity;
import com.flappybird.interfaces.Sprite;
import com.flappybird.utils.Vector2;

public class PipeEntity extends Entity {

    private final float SPEED_PIPE = 100f;
    public final int VALUE = 5;
    public boolean isBehind = false;

    public PipeEntity(Vector2 position, Sprite sprite){
        super(position, sprite);
    }

    public void moveLeft(float time){
        var distance = SPEED_PIPE * time;

        this.position = new Vector2(
            this.position.x() - distance,
            this.position.y()
        );
    }

}