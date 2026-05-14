package com.flappybird.core;

import java.util.ArrayList;
import java.util.List;

import com.flappybird.interfaces.enums.GameState;
import com.flappybird.interfaces.enums.PlayModes;
import com.flappybird.models.Player;

public class ConfigCore {
    
    private static ConfigCore instance;
    public PlayModes playMode = PlayModes.SINGLE_PLAYER;
    public GameState gameState = GameState.MENU;
    private List<Player> players = new ArrayList<>();
    private int maxScore = 0;

    private ConfigCore(){ }

    public static ConfigCore getInstance(){
        if (instance == null)
            instance = new ConfigCore();

        return instance;
    }

    public void addPlayer(Player player){
        players.add(player);
    }

    public void reset(){
        maxScore = 0;
        playMode = PlayModes.SINGLE_PLAYER;
        gameState = GameState.MENU;
        players.clear();
    }

    public int getMaxScore(){
        return maxScore;
    }

    public void checkMaxScore(int score){
        if (score > maxScore) {
            maxScore = score;
        }
    }

    public List<Player> getPlayers(){
        return players;
    }
}