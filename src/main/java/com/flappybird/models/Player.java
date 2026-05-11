package com.flappybird.models;

import com.flappybird.interfaces.GameControl;
import com.flappybird.interfaces.enums.PlayerState;
import com.flappybird.models.entities.BirdEntity;
import com.flappybird.utils.Color;

public class Player {

    public final Color COLOR;
    public final BirdEntity BIRD;
    public PlayerState state = PlayerState.LIVE;
    public final GameControl GAME_CONTROL;

    private int level = 1;
    private int score = 0;

    public Player(BirdEntity bird, GameControl gc, Color color){
        this.BIRD = bird;
        this.GAME_CONTROL = gc;
        this.COLOR = color;
    }

    public void jump(){
        BIRD.jump(0);
    }

    public void addScore(int score){
        this.score += score;
        System.out.println(score);
        this.level = (int) Math.floor((this.score / 100) + 1);
    }

    public void changeState(PlayerState state){
        this.state = state;
    }

    public int getLevel(){
        return level;
    }

    public int getScore(){
        return score;
    }

    public PlayerState getState(){
        return state;
    }
}