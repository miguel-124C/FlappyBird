package com.flappybird.core;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.glfw.GLFW;

import com.flappybird.factories.BirdFactory;
import com.flappybird.factories.DefaultEntityFactory;
import com.flappybird.interfaces.*;
import com.flappybird.interfaces.enums.GameState;
import com.flappybird.interfaces.enums.PlayModes;
import com.flappybird.models.Player;
import com.flappybird.utils.*;

public class MenuCore implements ICore {

    public boolean isPrevKeyDown;
    public boolean isPrevKeyUp;
    public boolean isPrevKeyEnter;

    private final BirdFactory BIRD_FACTORY;
    private final DefaultEntityFactory DEFAULT_FACTORY;

    private Entity menuTitle;
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

        var menuTitleDim = menuTitle.getDimensions();
        var positionGameMode = new Vector2(menuTitleDim.X, menuTitleDim.Y + menuTitleDim.HEIGHT + 100);
        gameModeSelect = DEFAULT_FACTORY.createEntity(positionGameMode, "GAME_MODE", "assets/selection-mode-sprite.png");
        
        entities.add(menuTitle);
        entities.add(gameModeSelect);
    }

    @Override
    public void update(float deltaTime) {
        progress += deltaTime;
        var menuTitlePos = menuTitle.position;

        if (progress >= seconds) {
            amountMove = amountMove * -1;
            progress = 0;
        }

        menuTitle.position = new Vector2(menuTitlePos.x(), menuTitlePos.y() + amountMove);
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
        
        ConfigCore.getInstance().playMode =
            (sprite.getCurrentFrame() == 0)
            ? PlayModes.SINGLE_PLAYER
            : PlayModes.MULTI_PLAYER;
    }

    public void startGame(){
        var colorOne = Color.blue();
        var configCore = ConfigCore.getInstance();
        var birdOne = BIRD_FACTORY.create(new Vector2(100, 500));
        var controlOne = GameControl.createControl(GLFW.GLFW_KEY_SPACE, GLFW.GLFW_KEY_ESCAPE);
        var playerOne = new Player(birdOne, controlOne, colorOne);
        
        switch (configCore.playMode) {
            case SINGLE_PLAYER:
                configCore.addPlayer(playerOne);
                break;
            case MULTI_PLAYER:
                var colorTwo = Color.orange();
                var birdTwo = BIRD_FACTORY.create(new Vector2(600, 500));
                var controlTwo = GameControl.createControl(GLFW.GLFW_KEY_W, GLFW.GLFW_KEY_BACKSPACE);
                var playerTwo = new Player(birdTwo, controlTwo, colorTwo);
                configCore.addPlayer(playerOne);
                configCore.addPlayer(playerTwo);
                break;
            default:
                throw new Error("Error play mode incorrect");
        }

        configCore.gameState = GameState.PLAYING;
    }

    @Override
    public void reset() {
        
    }

    public List<Entity> getEntities(){
        return entities;
    }

}