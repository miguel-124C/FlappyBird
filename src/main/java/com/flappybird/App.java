package com.flappybird;

import com.flappybird.controllers.InputManager;
import com.flappybird.core.*;
import com.flappybird.core.Game.*;
import com.flappybird.factories.*;
import com.flappybird.graphics.*;
import com.flappybird.managers.AudioManager;
import com.flappybird.models.World;
import com.flappybird.utils.SpriteAtlasJson;
import com.flappybird.views.*;

public class App {

    public static void main(String[] args) {
        SpriteAtlasJson spriteAtlasJson = new SpriteAtlasJson();
        spriteAtlasJson.loadJson();
        AudioManager.getInstance().loadSounds();


        ConfigCore.getInstance();
        var pipeFactory = new PipeFactory(spriteAtlasJson);
        var birdFactory = new BirdFactory(spriteAtlasJson);
        var defaultEntityFactory = new DefaultEntityFactory(spriteAtlasJson);
        var world = new World(pipeFactory);

        var gameBasicCore = new GameBasicCore(world);
        var gameSpriteCore = new GameSpriteCore(world, defaultEntityFactory);
        var menuCore = new MenuCore(birdFactory, defaultEntityFactory);
        var gameOverCore = new GameOverCore(defaultEntityFactory, world);

        var basicRender = new BasicRender();
        var spriteRender = new SpriteRenderer();

        var gameBasicRender = new GameBasicRender(gameBasicCore.world, basicRender);
        var gameSpriteRender = new GameSpriteRender(gameSpriteCore, spriteRender);
        var menuRender = new MenuRender(menuCore, spriteRender);
        var gameOverRender = new GameOverRender(gameOverCore, basicRender, spriteRender);
        
        var inputManager = new InputManager(menuCore, gameOverCore);
        var coreManager = new CoreManager(gameSpriteCore, menuCore, gameOverCore);
        var renderManager = new RenderManager(menuRender, gameSpriteRender, gameOverRender);

        new GameLoop(inputManager, coreManager, renderManager);
    }
    
}