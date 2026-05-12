package com.flappybird.models.entities;

import com.flappybird.interfaces.Entity;
import com.flappybird.interfaces.Sprite;
import com.flappybird.utils.Vector2;

public class DefaultEntity extends Entity {

    public DefaultEntity(Vector2 position, Sprite sprite) {
        super(position, sprite);
    }
    
}
