package com.flappybird;

import com.flappybird.controllers.InputManager;
import com.flappybird.factories.BirdFactory;
import com.flappybird.factories.PipeFactory;
import com.flappybird.graphics.GameLoop;
import com.flappybird.models.BirdEntity;
import com.flappybird.models.GameCore;
import com.flappybird.models.World;
import com.flappybird.utils.Vector2;
import com.flappybird.views.ManagerRender;

public class App {

    public static void main(String[] args) {
        System.out.println("Hello World!");

        var bird = createBird();
        var world = new World(bird);

        var pipeFactory = new PipeFactory();

        var gameCore = new GameCore(world, pipeFactory);
        var inputManager = new InputManager();

        var managerRender = new ManagerRender(gameCore, null);

        new GameLoop(inputManager, gameCore, managerRender);
    }

    private static BirdEntity createBird(){
        var birdFactory = new BirdFactory();
        return birdFactory.create(new Vector2(500, 500), "fds");
    }
    
}