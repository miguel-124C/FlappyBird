package com.flappybird.models;

import com.flappybird.interfaces.Entity;
import com.flappybird.interfaces.Sprite;
import com.flappybird.utils.Rectangle;
import com.flappybird.utils.Vector2;

public class BirdEntity extends Entity {

    // Fisica vertical.
    private final float GRAVITY = -1.9f;
    private final float JUMP_IMPULSE = 0.85f;
    private final float MAXIMUM_FALL_SPEED = -1.8f;

    private int currentFrame = 0;
    private final int totalFrame;
    private final int gapFrames = 14;

    public BirdEntity(Vector2 position, Sprite sprite, int totalFrame){
        super(position, sprite);
        this.totalFrame = totalFrame;
    }

    public void jump(){
        var dimension = this.getDimensions();
        var y = dimension.Y - gapFrames - dimension.HEIGHT;
        this.sprite.sourceRectangle = new Rectangle(dimension.X, y, dimension.WIDTH, dimension.HEIGHT);
    }

    public void fall(){
        var dimension = this.getDimensions();

        var y = dimension.Y + (currentFrame * (dimension.WIDTH + gapFrames));
        this.sprite.sourceRectangle = new Rectangle(dimension.X, y, dimension.WIDTH, dimension.HEIGHT);
    }

}