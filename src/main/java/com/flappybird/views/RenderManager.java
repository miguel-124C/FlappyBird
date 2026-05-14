package com.flappybird.views;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import com.flappybird.core.ConfigCore;

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
        GL11.glClearColor(0.52f, 0.80f, 0.92f, 1.0f);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        switch (ConfigCore.getInstance().gameState) {
            case MENU:
                MENU_RENDER.draw(deltaTime);
                break;
            case PLAYING:
                GAME_RENDER.draw(deltaTime);
                break;
            case PAUSE:
                // Draw pause
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
    }

    @Override
    public void cleanUp() {
        MENU_RENDER.cleanUp();
        GAME_RENDER.cleanUp();
        GLFW.glfwTerminate();
    }

}