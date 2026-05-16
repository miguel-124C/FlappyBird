package com.flappybird.views;

import com.flappybird.core.ConfigCore;
import com.flappybird.graphics.BasicRender;
import com.flappybird.interfaces.enums.PlayerState;
import com.flappybird.models.World;
import com.flappybird.utils.Color;
import com.flappybird.utils.Rectangle;

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
            
            var widthScaled = dimension.WIDTH * pipe.scale.x();
            var heightScaled = dimension.HEIGHT * pipe.scale.y();

            var souRectangle = new Rectangle(dimension.X, dimension.Y, widthScaled, heightScaled);
            var color = Color.custom(0.18f, 0.70f, 0.25f, 1);
            RENDER.dibujarRect(souRectangle, color);
        }

        for (var player : ConfigCore.getInstance().getPlayers()) {
            if (player.state == PlayerState.OUT_SCREEN) continue;

            var bird = player.BIRD;
            var dimension = bird.getDimensions();
            var souRectangle = new Rectangle(dimension.X, dimension.Y, dimension.WIDTH * bird.scale.x(), dimension.HEIGHT * bird.scale.y());
            var color = player.COLOR;
            RENDER.dibujarRect(souRectangle, color);
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