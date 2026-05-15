package com.flappybird.views;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import com.flappybird.core.ConfigCore;
import com.flappybird.utils.Color;
import com.flappybird.utils.Global;

public class RenderManager implements IRender {

    private final MenuRender MENU_RENDER;
    private final IRender GAME_RENDER;
    private final GameOverRender GAME_OVER_RENDER;

    public RenderManager(MenuRender menuRender, IRender gameRender, GameOverRender gameOverRender){
        this.MENU_RENDER = menuRender;
        this.GAME_RENDER = gameRender;
        GAME_OVER_RENDER = gameOverRender;
    }

    @Override
    public void draw(float deltaTime) {
        // Cielo.
        var timeDay = Global.getTimeDay();
        Color color;
        switch (timeDay) {
            case DAY:
                color = Color.custom(0.4392f, 0.7725f, 0.8078f, 1);
                break;
            case NIGHT:
                color = Color.custom(0.2431f, 0.3451f, 0.3569f, 1);
                break;
            default:
                color = Color.custom(0.52f, 0.80f, 0.92f, 1.0f);
                break;
        }
        GL11.glClearColor(color.R, color.G, color.B, color.ALPHA);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);


        switch (ConfigCore.getInstance().gameState) {
            case MENU:
                MENU_RENDER.draw(deltaTime);
                break;
            case PLAYING:
                GAME_RENDER.draw(deltaTime);
                break;
            case GAME_OVER:
                GAME_RENDER.draw(deltaTime);
                GAME_OVER_RENDER.draw(deltaTime);
                break;
            default:
                break;
        }
    }

    @Override
    public void initialize() {
        MENU_RENDER.initialize();
        GAME_RENDER.initialize();
        GAME_OVER_RENDER.initialize();
    }

    @Override
    public void cleanUp() {
        MENU_RENDER.cleanUp();
        GAME_RENDER.cleanUp();
        GLFW.glfwTerminate();
    }

}