package com.flappybird.core;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.glfw.GLFW;

import com.flappybird.factories.BirdFactory;
import com.flappybird.factories.DefaultEntityFactory;
import com.flappybird.interfaces.*;
import com.flappybird.interfaces.enums.*;
import com.flappybird.managers.AudioManager;
import com.flappybird.models.Player;
import com.flappybird.utils.*;

public class MenuCore implements ICore {

    public boolean isPrevKeyDown;
    public boolean isPrevKeyUp;

    private final BirdFactory BIRD_FACTORY;
    private final DefaultEntityFactory DEFAULT_FACTORY;

    private Entity menuTitle;
    private Entity bird;
    private Entity gameModeSelect;
    private List<Entity> entities = new ArrayList<>();

    private float progress = 0.5f;
    private float seconds = 1.5f;
    private float amountMove = 0.7f;

    public MenuCore(BirdFactory birdFactory, DefaultEntityFactory defaultEntityFactory){
        BIRD_FACTORY = birdFactory;
        DEFAULT_FACTORY = defaultEntityFactory;
    }

    @Override
    public void initialize() {
        var scale = new Vector2(3,3);
        menuTitle = DEFAULT_FACTORY.createEntityInCenter("TITLE_MENU", "assets/sprites.png", scale);

        var dimMenu = menuTitle.getDimensions();
        var positionBird = new Vector2(dimMenu.X + (dimMenu.WIDTH * menuTitle.scale.x()) + 10, dimMenu.Y);
        bird = BIRD_FACTORY.create(positionBird, scale);

        var menuTitleDim = menuTitle.getDimensions();
        var positionGameMode = new Vector2(menuTitleDim.X, menuTitleDim.Y + menuTitleDim.HEIGHT + 100);
        gameModeSelect = DEFAULT_FACTORY.createEntity(positionGameMode, "GAME_MODE", "assets/selection-mode-sprite.png", scale);

        var positionBackground = new Vector2(0, 0);
        var scaleBackground = new Vector2(4, 4);
        var backgroundMenu = DEFAULT_FACTORY.createEntity(positionBackground, "BACKGROUND_MENU", "assets/game_scenary.png", scaleBackground);
        
        var timeDay = Global.getTimeDay();
        if (timeDay == TimeDay.NIGHT) { // Cambia el fondo en caso sea de de noche, por defecto muestra fondo de dia
            backgroundMenu.sprite.changeFrame(1);
            backgroundMenu.sprite.setAnimationDirection(Direction.RIGHT);
        }
        
        entities.add(backgroundMenu);
        entities.add(menuTitle);
        entities.add(bird);
        entities.add(gameModeSelect);
    }

    @Override
    public void update(float deltaTime) {
        progress += deltaTime;
        var menuTitlePos = menuTitle.position;
        var birdPos = bird.position;

        if (progress >= seconds) {
            amountMove = amountMove * -1;
            progress = 0;
        }

        menuTitle.position = new Vector2(menuTitlePos.x(), menuTitlePos.y() + amountMove);
        bird.position = new Vector2(birdPos.x(), birdPos.y() + amountMove);
    }

    public void changeGameMode(Direction direction){
        var sprite = gameModeSelect.sprite;
        var currentFrame = sprite.getCurrentFrame();

        switch (direction) {
            case UP:
                sprite.changeFrame(currentFrame - 1);
                break;
            case DOWN:
                sprite.changeFrame(currentFrame + 1);
                break;
            default:
                break;
        }

        AudioManager.getInstance().playSfWing();
        
        ConfigCore.getInstance().playMode =
            (sprite.getCurrentFrame() == 0)
            ? PlayModes.SINGLE_PLAYER
            : PlayModes.MULTI_PLAYER;
    }

    public void startGame(){
        var configCore = ConfigCore.getInstance();
        var controlOne = GameControl.createControl(GLFW.GLFW_KEY_SPACE, GLFW.GLFW_KEY_ESCAPE);
        var playerOne = createPlayer(new Vector2(500, 500), Color.blue(), controlOne);
        
        switch (configCore.playMode) {
            case SINGLE_PLAYER:
                configCore.addPlayer(playerOne);
                break;
            case MULTI_PLAYER:
                var controlTwo = GameControl.createControl(GLFW.GLFW_KEY_W, GLFW.GLFW_KEY_BACKSPACE);
                var playerTwo = createPlayer(new Vector2(400, 500), Color.orange(), controlTwo);
                configCore.addPlayer(playerOne);
                configCore.addPlayer(playerTwo);
                break;
            default:
                throw new Error("Error play mode incorrect");
        }

        configCore.gameState = GameState.PLAYING;
    }

    private Player createPlayer(Vector2 position, Color color, GameControl gameControl){
        var sacale = new Vector2(3, 3);
        var bird = BIRD_FACTORY.create(position, sacale);
        return new Player(bird, gameControl, color);
    }

    public List<Entity> getEntities(){
        return entities;
    }

}