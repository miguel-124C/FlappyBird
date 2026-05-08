package com.flappybird.models;

import java.util.ArrayList;
import java.util.List;

public class World {
    public final BirdEntity Bird;
    private List<PipeEntity> pipes = new ArrayList<>();

    private final float SPEED_PIPES = 0.62f;
    

    public World(BirdEntity bird){
        this.Bird = bird;
    }

    public void addPipe(PipeEntity pipe){
        pipes.add(pipe);
    }

    public boolean hasCollision(){
        var dimensionBird = Bird.getDimensions();

        for (var pipe : pipes){
            var dimensionPipe = pipe.getDimensions();
            
            if (dimensionBird.intersect(dimensionPipe))
                return true;
        }

        return false;
    }

    public void moveAllPipes(float time){
        for (int i = 0; i < pipes.size(); i++) {
            var pipe = pipes.get(i);

            pipe.moveLeft(SPEED_PIPES * time);

            checkPipeBehind(pipe);
            checkPipeOutScreen(pipe, i);
        }
    }

    private void checkPipeBehind(PipeEntity pipe){
        if (!pipe.isBehind) return;
        var dimensionPipe = pipe.getDimensions();
            
        if (Bird.position.x() > dimensionPipe.X + dimensionPipe.WIDTH){
            pipe.isBehind = true;
            // Sumar puntos y score

        }
    }

    private void checkPipeOutScreen(PipeEntity pipe, int index){
        var dimension = pipe.getDimensions();
        if (dimension.X + dimension.WIDTH < 0) {
            pipes.remove(index);
        }
    }

    public List<PipeEntity> getPipes(){
        return pipes;
    }
}