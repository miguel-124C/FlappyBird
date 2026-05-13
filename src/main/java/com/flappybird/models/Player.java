package com.flappybird.models;

import com.flappybird.interfaces.GameControl;
import com.flappybird.interfaces.enums.PlayerState;
import com.flappybird.models.entities.BirdEntity;
import com.flappybird.utils.Color;

public class Player {

    private static int nextId = 0;
    private final int id;
    public final Color COLOR;
    public final BirdEntity BIRD;
    public PlayerState state = PlayerState.LIVE;
    public final GameControl GAME_CONTROL;
    public float fallVelocity = 0f;

    private int score = 0;

    public Player(BirdEntity bird, GameControl gc, Color color){
        this.BIRD = bird;
        this.GAME_CONTROL = gc;
        this.COLOR = color;
        id = nextId;
        nextId++;
    }

    public void jump(){
        BIRD.jump(0);
    }

    public void addScore(int score){
        this.score += score;
    }

    public void changeState(PlayerState state){
        this.state = state;
    }

    public int getScore(){
        return score;
    }

    public PlayerState getState(){
        return state;
    }

    public int getId(){
        return id;
    }
}