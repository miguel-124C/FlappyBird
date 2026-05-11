package com.flappybird.models.entities;

import com.flappybird.interfaces.Entity;
import com.flappybird.interfaces.Sprite;
import com.flappybird.utils.Vector2;

public class CoinEntity extends Entity {

    private int value;

    public CoinEntity(Vector2 position, Sprite sprite, int value){
        super(position, sprite);
        this.value = value;
    }

    public int getValue(){
        return value;
    }

}