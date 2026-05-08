package com.flappybird.models;

import com.flappybird.factories.PipeFactory;
import com.flappybird.interfaces.State;

public class GameCore {
    
    public final World world;
    private State state;
    private final PipeFactory pipeFactory;

    private float timeSpawnPipes = 0;
    public final float TIME_PER_PIPES = 4;

    public GameCore(World world, PipeFactory pipeFactory){
        state = State.PLAYING;
        this.world = world;
        this.pipeFactory = pipeFactory;
        spawnPipes();
    }

    public void update(float deltaTime){
        timeSpawnPipes += deltaTime;

        var bird = world.Bird;
        bird.fall(deltaTime);

        if (world.hasCollision() || world.isBirdOutScreen()) {
            state = State.GAME_OVER;
            return;
        }

        if (timeSpawnPipes >= TIME_PER_PIPES) {
            spawnPipes();
            timeSpawnPipes = 0;
        }

        world.moveAllPipes(deltaTime);
    }

    private void spawnPipes(){
        var firstPipe = pipeFactory.spawnPipe();
        var secondPipe = pipeFactory.spawnSecondPipe(firstPipe);

        world.addPipe(firstPipe);
        world.addPipe(secondPipe);
    }

    public void changeState(State state){
        this.state = state;
    }

    public State getState(){
        return this.state;
    }

}