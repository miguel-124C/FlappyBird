package com.flappybird.factories;

import com.flappybird.interfaces.Sprite;
import com.flappybird.models.BirdEntity;
import com.flappybird.utils.*;

public class BirdFactory {

    public BirdEntity create(){
        var position = new Vector2(500, 0);
        // var sourceRectangle = new Rectangle(264, 64, 28, 12);
        var sprite = new Sprite();

        return new BirdEntity(position, sprite, 2);
    }

}