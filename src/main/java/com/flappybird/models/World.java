package com.flappybird.models;

import java.util.ArrayList;
import java.util.List;

import com.flappybird.utils.Constants;

public class World {
    public final BirdEntity Bird;
    private List<PipeEntity> pipes = new ArrayList<>();
    private List<CoinEntity> coins = new ArrayList<>();

    private int score = 0;
    private int level = 1;

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

            pipe.moveLeft(time);

            checkPipeBehind(pipe);
            checkPipeOutScreen(pipe, i);
        }
    }

    public boolean isBirdOutScreen(){
        var dimension = Bird.getDimensions();

        var outScreenY = dimension.Y <= 0 || dimension.Y + dimension.HEIGHT >= Constants.screenHeight;
        var outScreenX = dimension.X <= 0 || dimension.X + dimension.WIDTH >= Constants.screenWidth;
        return (outScreenX || outScreenY);
    }

    private void checkPipeBehind(PipeEntity pipe){
        if (pipe.isBehind) return;
        var dimensionPipe = pipe.getDimensions();
            
        if (Bird.position.x() > dimensionPipe.X + dimensionPipe.WIDTH){
            pipe.isBehind = true;
            score += 5;
            System.out.println(score);
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