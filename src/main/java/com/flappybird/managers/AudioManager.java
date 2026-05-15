package com.flappybird.managers;

import com.flappybird.utils.AudioPlayer;
import com.flappybird.utils.Global;

public class AudioManager {
    
    private static AudioManager instance;

    private AudioPlayer sfDie;
    private AudioPlayer sfHit;
    private AudioPlayer sfPoint;
    private AudioPlayer sfSwooshing;
    private AudioPlayer sfWing;

    private final String PATH_SOUNDS;

    private AudioManager(){
        PATH_SOUNDS = Global.resolvePath("sounds");
    }

    public static AudioManager getInstance(){
        if (instance == null)
            instance = new AudioManager();

        return instance;
    }

    public void loadSounds(){
        sfDie = AudioPlayer.load(PATH_SOUNDS + "/sfx_die.wav");
        sfHit = AudioPlayer.load(PATH_SOUNDS + "/sfx_hit.wav");
        sfPoint = AudioPlayer.load(PATH_SOUNDS + "/sfx_point.wav");
        sfSwooshing = AudioPlayer.load(PATH_SOUNDS + "/sfx_swooshing.wav");
        sfWing = AudioPlayer.load(PATH_SOUNDS + "/sfx_wing.wav");
    }
    
    public void playSfDie(){
        sfDie.play();
    }

    public void playSfHit(){
        sfHit.play();
    }

    public void playSfPoint(){
        sfPoint.play();
    }

    public void playSfSwoosing(){
        sfSwooshing.play();
    }

    public void playSfWing(){
        sfWing.play();
    }

}