package com.flappybird.interfaces;

public class GameControl {
    private int jump;
    private int pause;

    public boolean prevKey;

    private GameControl(int jump, int pause){
        this.jump = jump;
        this.pause = pause;
    }

    public static GameControl createControl(int jump, int pause){
        return new GameControl(jump, pause);
    }

    public void changeControlJump(int key){
        if(isKeyAssigment(key)) return;
        this.jump = key;
    }

    public void changeControlPause(int key){
        if(isKeyAssigment(key)) return;
        this.pause = key;
    }

    private boolean isKeyAssigment(int key){
        return jump == key || pause == key;
    }

    public int getKeyJump(){
        return jump;
    }

    public int getKeyPause(){
        return pause;
    }

}