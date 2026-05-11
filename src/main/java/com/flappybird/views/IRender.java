package com.flappybird.views;

public interface IRender {

    public void initialize();
    public void draw(float deltaTime);
    public void cleanUp();

}