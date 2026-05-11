package com.flappybird.models;

import java.util.ArrayList;
import java.util.List;

import com.flappybird.factories.PipeFactory;
import com.flappybird.models.entities.PipeEntity;
import com.flappybird.utils.Constants;

public class World {

    private List<PipeEntity> pipes = new ArrayList<>();
    private final PipeFactory pipeFactory;

    public World(PipeFactory pipeFactory){
        this.pipeFactory = pipeFactory;
    }

    public boolean hasCollision(Player player){
        var bird = player.BIRD;
        var dimension = bird.getDimensions();

        for (var pipe : pipes){
            var dimensionPipe = pipe.getDimensions();
            
            if (dimension.intersect(dimensionPipe))
                return true;
        }

        var outScreenY = dimension.Y <= 0 || dimension.Y + dimension.HEIGHT >= Constants.screenHeight;
        var outScreenX = dimension.X <= 0 || dimension.X + dimension.WIDTH >= Constants.screenWidth;
        return (outScreenX || outScreenY);
    }

    public void moveAllPipes(float time){
        for (int i = 0; i < pipes.size(); i++) {
            var pipe = pipes.get(i);

            pipe.moveLeft(time);

            checkPipeBehind(pipe);
            checkPipeOutScreen(pipe, i);
        }
    }

    private void checkPipeBehind(PipeEntity pipe){
        if (pipe.isBehind) return;
        var dimensionPipe = pipe.getDimensions();
            
        // if (Bird.position.x() > dimensionPipe.X + dimensionPipe.WIDTH){
        //     pipe.isBehind = true;
            
        // }
    }

    private void checkPipeOutScreen(PipeEntity pipe, int index){
        var dimension = pipe.getDimensions();
        if (dimension.X + dimension.WIDTH < 0) {
            pipes.remove(index);
        }
    }

    public void spawnPipes(){
        var firstPipe = pipeFactory.spawnPipe();
        var secondPipe = pipeFactory.spawnSecondPipe(firstPipe);

        pipes.add(firstPipe);
        pipes.add(secondPipe);
    }

    public List<PipeEntity> getPipes(){
        return pipes;
    }
}