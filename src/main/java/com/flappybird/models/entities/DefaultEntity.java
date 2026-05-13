package com.flappybird.models.entities;

import com.flappybird.interfaces.Entity;
import com.flappybird.interfaces.Sprite;
import com.flappybird.utils.Vector2;

public class DefaultEntity extends Entity {

    public DefaultEntity(Vector2 position, Sprite sprite) {
        super(position, sprite);
    }
    
    public DefaultEntity(Vector2 position, Sprite sprite, Vector2 scale) {
        super(position, sprite, scale);
    }
}
