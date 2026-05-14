package com.flappybird.models;

import java.util.ArrayList;
import java.util.List;

import com.flappybird.core.ConfigCore;
import com.flappybird.factories.PipeFactory;
import com.flappybird.models.entities.PipeEntity;
import com.flappybird.utils.Constants;
import com.flappybird.utils.Rectangle;

public class World {

    private List<PipeEntity> pipes = new ArrayList<>();
    private final PipeFactory pipeFactory;

    public World(PipeFactory pipeFactory){
        this.pipeFactory = pipeFactory;
    }

    public boolean hasCollision(Rectangle dimension){
        for (var pipe : pipes){
            var dimensionPipe = pipe.getDimensions();
            
            if (dimension.intersect(dimensionPipe))
                return true;
        }

        var outScreenY = dimension.Y <= 0 || dimension.Y + dimension.HEIGHT >= Constants.screenHeight;
        var outScreenX = dimension.X <= 0 || dimension.X + dimension.WIDTH >= Constants.screenWidth;
        return (outScreenX || outScreenY);
    }

    public void checkPipeBehind(PipeEntity pipe, Player player){
        if (pipe.isBehind && pipe.playersId.contains(player.getId())) return;
        var dimensionPipe = pipe.getDimensions();
        var bird = player.BIRD;

        if (bird.position.x() > dimensionPipe.X + dimensionPipe.WIDTH){
            pipe.isBehind = true;
            pipe.playersId.add(player.getId());
            player.addScore(pipe.VALUE);
            ConfigCore.getInstance().checkMaxScore(player.getScore());
        }
    }

    public void checkPipeOutScreen(PipeEntity pipe, int index){
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