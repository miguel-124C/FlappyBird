package com.flappybird.interfaces;

import com.flappybird.interfaces.enums.Direction;
import com.flappybird.utils.*;

public class Sprite {
    public final Texture TEXTURE;
    public final Rectangle SOURCET_RECTANGLE;
    public final int TOTAL_FRAMES;
    public double rotation = 0;
    private int currentFrame;
    private Direction animationDirection = Direction.DOWN;
    private Color tintColor = Color.custom(1, 1, 1, 1);

    public Sprite(Texture texture, Rectangle sourceRectangle, int currentFrame, int totalFrames) {
        TEXTURE = texture;
        SOURCET_RECTANGLE = sourceRectangle;
        TOTAL_FRAMES = totalFrames;
        this.currentFrame = currentFrame;
    }

    public Sprite(Texture texture, Rectangle sourceRectangle, int totalFrames) {
        this(texture, sourceRectangle, 0, totalFrames);
    }

    public Sprite(Texture texture, Rectangle sourceRectangle, int currentFrame, int totalFrames, Direction animationDirection) {
        this(texture, sourceRectangle, currentFrame, totalFrames);
        this.animationDirection = animationDirection;
    }

    public Sprite(Texture texture, Rectangle sourceRectangle, int totalFrames, Direction animationDirection) {
        this(texture, sourceRectangle, 0, totalFrames);
        this.animationDirection = animationDirection;
    }

    public void changeFrame(int indexFrame){
        if (indexFrame > TOTAL_FRAMES - 1)
            currentFrame = 0;
        else if(indexFrame < 0)
            currentFrame = TOTAL_FRAMES - 1;
        else
            currentFrame = indexFrame;
    }

    public int getCurrentFrame(){
        return currentFrame;
    }

    public void setTintColor(Color color){
        tintColor = color;
    }

    public Color getTinColor(){
        return tintColor;
    }

    public void setAnimationDirection(Direction direction){
        animationDirection = direction;
    }

    public Direction getAnimDirection(){
        return animationDirection;
    }
}