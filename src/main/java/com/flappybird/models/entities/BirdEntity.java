package com.flappybird.models.entities;

import com.flappybird.interfaces.Entity;
import com.flappybird.interfaces.Sprite;
import com.flappybird.utils.Vector2;

public class BirdEntity extends Entity {

    // Fisica vertical.
    private final float GRAVITY = 400f;          // Aumentado para reacción rápida
    private final float JUMP_IMPULSE = -250f;     // Negativo para subir
    private final float MAX_FALL_SPEED = 500f;    // Límite de caída rápido
    private float speedY = 0;

    private int currentFrame = 0;
    private final int totalFrame;
    private final int gapFrames = 14;

    public BirdEntity(Vector2 position, Sprite sprite, int totalFrame){
        super(position, sprite);
        this.totalFrame = totalFrame;
    }

    public void jump(float deltaTime){
        // var dimension = this.getDimensions();
        // var y = dimension.Y - gapFrames - dimension.HEIGHT;
        // this.sprite.sourceRectangle = new Rectangle(dimension.X, y, dimension.WIDTH, dimension.HEIGHT);

        speedY = JUMP_IMPULSE;
    }

    public void fall(float deltaTime){
        var dimension = this.getDimensions();

        // var y = dimension.Y + (currentFrame * (dimension.WIDTH + gapFrames));
        // this.sprite.sourceRectangle = new Rectangle(dimension.X, y, dimension.WIDTH, dimension.HEIGHT);

        // Integracion de fisica simple.
        speedY += GRAVITY * deltaTime;
        // Limitar velocidad de caida para sensacion jugable estable.
        if (speedY > MAX_FALL_SPEED) {
            speedY = MAX_FALL_SPEED;
        }

        var newY = dimension.Y + (speedY * deltaTime);
        this.position = new Vector2(dimension.X, newY);
    }

}