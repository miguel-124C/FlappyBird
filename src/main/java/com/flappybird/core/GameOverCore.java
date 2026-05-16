package com.flappybird.core;

import java.util.ArrayList;
import java.util.List;

import com.flappybird.factories.DefaultEntityFactory;
import com.flappybird.interfaces.Entity;
import com.flappybird.models.World;
import com.flappybird.utils.Vector2;

public class GameOverCore implements ICore {
    
    private final DefaultEntityFactory DEFAULT_FACTORY;
    private final World WORLD;
    private List<Entity> entities = new ArrayList<>();

    private float timeEnlapsed = 0;
    private float timeToGoMenu = 60; // 1min

    public GameOverCore(DefaultEntityFactory defaultEntityFactory, World world){
        DEFAULT_FACTORY = defaultEntityFactory;
        WORLD = world;
    }

    @Override
    public void initialize() {
        var scale = new Vector2(6, 6);
        var titleGameOver = DEFAULT_FACTORY.createEntityInCenter("TITLE_GAME_OVER", "assets/sprites.png", scale);
        entities.add(titleGameOver);
    }

    @Override
    public void update(float deltaTime) {
        timeEnlapsed += deltaTime;
        if (timeEnlapsed >= timeToGoMenu) {
            resetGame();
            timeEnlapsed = 0;
        }
    }

    public void resetGame(){
        ConfigCore.getInstance().reset();
        WORLD.getPipes().clear();
    }

    public List<Entity> getEntities(){
        return entities;
    }
    
}