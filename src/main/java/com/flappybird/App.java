package com.flappybird;

import com.flappybird.controllers.InputManager;
import com.flappybird.core.*;
import com.flappybird.core.Game.*;
import com.flappybird.factories.*;
import com.flappybird.graphics.*;
import com.flappybird.managers.AudioManager;
import com.flappybird.models.World;
import com.flappybird.utils.Constants;
import com.flappybird.utils.DigitRegion;
import com.flappybird.utils.SpriteAtlasJson;
import com.flappybird.views.*;

public class App {

    public static void main(String[] args) {
        // Se carga el json con los datos mapeados de las imagenes y se cargan los efectos de sonido
        SpriteAtlasJson spriteAtlasJson = new SpriteAtlasJson();
        spriteAtlasJson.loadJson();
        AudioManager.getInstance().loadSounds();
        DigitRegion digitRegion = new DigitRegion();
        digitRegion.initializeNumbers();

        // Inicio del ConfigCore, clase Singleton
        ConfigCore.getInstance();
        
        // Factories, encargados de crear entidades
        var pipeFactory = new PipeFactory(spriteAtlasJson);
        var birdFactory = new BirdFactory(spriteAtlasJson);
        var defaultEntityFactory = new DefaultEntityFactory(spriteAtlasJson);
        
        // La clase principal, crea pipes y verifica colisiones
        var world = new World(pipeFactory);

        // CORES: Logica pura, encargados de crear entidades, para luego mostrarlas.
        var gameBasicCore = new GameBasicCore(world);
        var gameSpriteCore = new GameSpriteCore(world, defaultEntityFactory);
        var hudCore = new HudCore(defaultEntityFactory, digitRegion);
        var menuCore = new MenuCore(birdFactory, defaultEntityFactory);
        var gameOverCore = new GameOverCore(defaultEntityFactory, world);

        // RENDERS, los encargados de dibujar en la pantalla
        var basicRender = new BasicRender();
        var spriteRender = new SpriteRenderer();

        // Llaman un render y dibujan con dicho render
        var gameRender = getGameRender(gameBasicCore, gameSpriteCore, basicRender, spriteRender);
        var hudRender = new HudRender(hudCore, spriteRender);
        var menuRender = new MenuRender(menuCore, spriteRender);
        var gameOverRender = new GameOverRender(gameOverCore, basicRender, spriteRender);
    
        // Lee cada tecla que el usuario precione.
        var inputManager = new InputManager(menuCore, gameOverCore);
        // MANAGERS: Dicen que CORE, RENDER se ejecutara, en base al estado del juego
        var coreManager = new CoreManager(gameSpriteCore, menuCore, gameOverCore, hudCore);
        var renderManager = new RenderManager(menuRender, gameRender, gameOverRender, hudRender);

        // GameLoop princiapl, inicio del juego.
        new GameLoop(inputManager, coreManager, renderManager);
    }
    
    // En base al tipo de render que hayamos puesto en Constants.GAME_RENDER_TYPE, devolvera un IRender
    private static IRender getGameRender(GameBasicCore gameBasicCore, GameSpriteCore gameSpriteCore, BasicRender basicRender, SpriteRenderer spriteRenderer){
        switch (Constants.GAME_RENDER_TYPE) {
            case BASIC:
                return new GameBasicRender(gameBasicCore.world, basicRender);
            case SPRITES:
                return new GameSpriteRender(gameSpriteCore, spriteRenderer);
            default:
                return new GameBasicRender(gameBasicCore.world, basicRender);
        }
    }

}