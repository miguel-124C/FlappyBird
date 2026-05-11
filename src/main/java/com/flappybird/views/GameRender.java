package com.flappybird.views;

import com.flappybird.models.World;

public class GameRender implements IRender {

    private final World WORLD_GAME;

    public GameRender(World world){
        this.WORLD_GAME = world;
    }

    @Override
    public void initialize() {
        
    }

    @Override
    public void draw(float deltaTime){
       
    }

    @Override
    public void cleanUp() {
        
    }
}
