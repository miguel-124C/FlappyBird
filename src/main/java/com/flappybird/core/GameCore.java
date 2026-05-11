package com.flappybird.core;

import com.flappybird.models.World;

public class GameCore implements ICore {
    
    public final World world;    

    private float timeSpawnPipes = 0;
    public final float TIME_PER_PIPES = 4;

    public GameCore(World world){
        this.world = world;
    }

    @Override
    public void initialize() {
        world.spawnPipes();
    }

    @Override
    public void update(float deltaTime){
        timeSpawnPipes += deltaTime;

        for (var player : ConfigCore.getInstance().getPlayers()) {
            player.BIRD.fall(deltaTime);
            world.hasCollision(player);
        }

        if (timeSpawnPipes >= TIME_PER_PIPES) {
            world.spawnPipes();
            timeSpawnPipes = 0;
        }

        world.moveAllPipes(deltaTime);
    }
    
    @Override
    public void reset() {

    }

}