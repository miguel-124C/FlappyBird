package com.flappybird.factories;

import com.flappybird.interfaces.Sprite;
import com.flappybird.models.BirdEntity;
import com.flappybird.utils.*;

public class BirdFactory {

    public BirdEntity create(Vector2 position, String nameTexture){
        var texture = new Texture(nameTexture);

        var sourceRectangle = new Rectangle(264, 64, 28, 12);
        var sprite = new Sprite(texture, sourceRectangle);

        return new BirdEntity(position, sprite, 2);
    }

}