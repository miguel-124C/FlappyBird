package com.flappybird.views;

import com.flappybird.core.ConfigCore;
import com.flappybird.graphics.BasicRender;
import com.flappybird.interfaces.enums.PlayerState;
import com.flappybird.models.World;

public class GameBasicRender implements IRender {
    
    private final BasicRender RENDER;
    private final World WORLD;

    public GameBasicRender(World world, BasicRender render){
        this.RENDER = render;
        this.WORLD = world;
    }

    @Override
    public void draw(float deltaTime) {
        RENDER.draw(deltaTime);

        for (var pipe : WORLD.getPipes()) {
            var dimension = pipe.getDimensions();
            RENDER.dibujarRect(dimension.X, dimension.Y, dimension.WIDTH, dimension.HEIGHT, 0.18f, 0.70f, 0.25f);
        }

        for (var player : ConfigCore.getInstance().getPlayers()) {
            if (player.state == PlayerState.OUT_SCREEN){
                System.out.println("Esta fuera de pantalla");
                continue;
            }
            var dimension = player.BIRD.getDimensions();
            var color = player.COLOR;
            RENDER.dibujarRect(dimension.X, dimension.Y, dimension.WIDTH, dimension.HEIGHT, color.R, color.G, color.B);
        }
    }

    @Override
    public void initialize() {
        RENDER.initialize();
    }

    @Override
    public void cleanUp() {
        RENDER.cleanUp();
    }

}