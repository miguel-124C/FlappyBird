package com.flappybird.models;

import com.flappybird.factories.PipeFactory;
import com.flappybird.interfaces.State;

public class GameCore {
    
    public final World world;
    private State state;
    private final PipeFactory pipeFactory;

    private float timeSpawnPipes = 0;
    public final float TIME_PER_PIPES = 1;

    public GameCore(World world, PipeFactory pipeFactory){
        state = State.MENU;
        this.world = world;
        this.pipeFactory = pipeFactory;
    }

    public void update(float deltaTime){
        timeSpawnPipes += deltaTime;

        if (world.hasCollision()) {
            state = State.GAME_OVER;
            return;
        }

        if (timeSpawnPipes >= TIME_PER_PIPES) {
            var firstPipe = pipeFactory.spawnPipe();
            var secondPipe = pipeFactory.spawnSecondPipe(firstPipe);

            world.addPipe(firstPipe);
            world.addPipe(secondPipe);
            timeSpawnPipes = 0;
        }

        world.moveAllPipes(deltaTime);
    }

    public void changeState(State state){
        this.state = state;
    }

    public State getState(){
        return this.state;
    }

}
