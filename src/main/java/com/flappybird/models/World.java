package com.flappybird.models;

import java.util.ArrayList;
import java.util.List;

public class World {
    
    private final BirdEntity Bird;
    private List<PipeEntity> pipes = new ArrayList<>();

    public World(BirdEntity bird){
        this.Bird = bird;
    }

    public void addPipe(PipeEntity pipe){
        pipes.add(pipe);
    }

    public void removePipe(int index){
        pipes.remove(index);
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

    public boolean pipeIsBack(){
        for (var pipe : pipes){
            var dimensionPipe = pipe.getDimensions();
            
            if (Bird.position.x() > dimensionPipe.X + dimensionPipe.WIDTH)
                return true;
        }

        return false;
    }
}