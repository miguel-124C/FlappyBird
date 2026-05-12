package com.flappybird;

import com.flappybird.controllers.InputManager;
import com.flappybird.core.ConfigCore;
import com.flappybird.core.CoreManager;
import com.flappybird.core.GameCore;
import com.flappybird.core.MenuCore;
import com.flappybird.factories.BirdFactory;
import com.flappybird.factories.DefaultEntityFactory;
import com.flappybird.factories.PipeFactory;
import com.flappybird.graphics.BasicRender;
import com.flappybird.graphics.GameLoop;
import com.flappybird.models.World;
import com.flappybird.utils.SpriteAtlasJson;
import com.flappybird.views.GameBasicRender;
import com.flappybird.views.MenuRender;
import com.flappybird.views.RenderManager;

public class App {

    public static void main(String[] args) {
        SpriteAtlasJson spriteAtlasJson = new SpriteAtlasJson();
        spriteAtlasJson.loadJson();

        ConfigCore.getInstance();
        var pipeFactory = new PipeFactory(spriteAtlasJson);
        var birdFactory = new BirdFactory(spriteAtlasJson);
        var defaultEntityFactory = new DefaultEntityFactory(spriteAtlasJson);
        var world = new World(pipeFactory);

        var gameCore = new GameCore(world);
        var menuCore = new MenuCore(birdFactory, defaultEntityFactory);

        var gameBasicRender = new GameBasicRender(gameCore.world, new BasicRender());
        var menuRender = new MenuRender(menuCore);
        
        var inputManager = new InputManager(menuCore);
        var coreManager = new CoreManager(gameCore, menuCore);
        var renderManager = new RenderManager(menuRender, gameBasicRender);

        new GameLoop(inputManager, coreManager, renderManager);
    }
    
}