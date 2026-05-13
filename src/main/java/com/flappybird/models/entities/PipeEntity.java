package com.flappybird.models.entities;

import java.util.ArrayList;
import java.util.List;

import com.flappybird.interfaces.Entity;
import com.flappybird.interfaces.Sprite;
import com.flappybird.utils.Vector2;

public class PipeEntity extends Entity {

    public final int VALUE = 5;
    public boolean isBehind = false;
    public List<Integer> playersId = new ArrayList<>();

    public PipeEntity(Vector2 position, Sprite sprite){
        super(position, sprite);
    }

    public PipeEntity(Vector2 position, Sprite sprite, Vector2 scale){
        super(position, sprite, scale);
    }

    public void moveLeft(float distance){
        this.position = new Vector2(
            this.position.x() - distance,
            this.position.y()
        );
    }

}