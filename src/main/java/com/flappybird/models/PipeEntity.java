package com.flappybird.models;

import com.flappybird.interfaces.Entity;
import com.flappybird.utils.Rectangle;
import com.flappybird.utils.Vector2;

public class PipeEntity extends Entity {
    
    public PipeEntity(Vector2 position, Rectangle sourceRectangle){
        super(position, sourceRectangle);
    }

}
