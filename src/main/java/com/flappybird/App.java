package com.flappybird;

import com.flappybird.controllers.InputManager;
import com.flappybird.core.ConfigCore;
import com.flappybird.core.CoreManager;
import com.flappybird.core.GameCore;
import com.flappybird.core.MenuCore;
import com.flappybird.factories.PipeFactory;
import com.flappybird.graphics.BasicRender;
import com.flappybird.graphics.GameLoop;
import com.flappybird.models.World;
import com.flappybird.views.GameBasicRender;
import com.flappybird.views.RenderManager;

public class App {

    public static void main(String[] args) {
        System.out.println("Hello World!");

        ConfigCore.getInstance();
        var pipeFactory = new PipeFactory();
        var world = new World(pipeFactory);

        var gameCore = new GameCore(world);
        var menuCore = new MenuCore();

        var gameBasicRender = new GameBasicRender(gameCore.world, new BasicRender());
        
        var inputManager = new InputManager();
        var coreManager = new CoreManager(gameCore, menuCore);
        var renderManager = new RenderManager(gameBasicRender);

        new GameLoop(inputManager, coreManager, renderManager);
    }
    
}