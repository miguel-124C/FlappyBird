package com.flappybird.utils;

public class Rectangle{
    public final float X;
    public final float Y;
    public final float WIDTH;
    public final float HEIGHT;

    public Rectangle(float X, float Y, float WIDTH, float HEIGHT){
        this.X = X;
        this.Y = Y;
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
    }

    public boolean intersect(Rectangle rect){
        // # Fórmula de Colisión AABB
        // Existe colisión si:
        // (A.x < B.x + B.w) AND (A.x + A.w > B.x) AND
        // (A.y < B.y + B.h) AND (A.y + A.h > B.y)
        var intersectX = (rect.X < this.X + this.WIDTH) && (rect.X + rect.WIDTH > this.X);
        var intersectY = (rect.Y < this.Y + this.HEIGHT) && (rect.Y + rect.HEIGHT > this.Y);

        return intersectX && intersectY;
    }

}
