package com.flappybird.interfaces;

import com.flappybird.utils.*;

public class Sprite {
    public final Texture TEXTURE;
    public final Rectangle SOURCET_RECTANGLE;
    public final int TOTAL_FRAMES;
    private int currentFrame;

    public Sprite(Texture texture, Rectangle sourceRectangle, int currentFrame, int totalFrames) {
        TEXTURE = texture;
        SOURCET_RECTANGLE = sourceRectangle;
        TOTAL_FRAMES = totalFrames;
        this.currentFrame = currentFrame;
    }

    public void changeFrame(int indexFrame){
        if (indexFrame > TOTAL_FRAMES - 1){
            currentFrame = 0;
        } else if(indexFrame < 0){
            currentFrame = TOTAL_FRAMES - 1;
        }
        currentFrame = indexFrame;
    }

    public int getCurrentFrame(){
        return currentFrame;
    }
}
