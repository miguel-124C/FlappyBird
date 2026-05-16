package com.flappybird.views;

import com.flappybird.core.ConfigCore;
import com.flappybird.graphics.BasicRender;
import com.flappybird.interfaces.enums.PlayerState;
import com.flappybird.models.World;
import com.flappybird.utils.*;
import com.flappybird.interfaces.Entity;

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
            drawBodyBird(bird);
        }
    }

    // Las partes del ave no tendran collision
    private void drawBodyBird(Entity bird){
        var dimension = bird.getDimensions();

        var sourcAla = new Rectangle(dimension.X + 5, dimension.Y + (dimension.WIDTH * bird.scale.y() / 2), dimension.WIDTH * 0.6f * bird.scale.x(), dimension.HEIGHT * 0.4f * bird.scale.y());
        RENDER.dibujarRect(sourcAla, Color.custom(0.4f, 0.4f, 0.1f, 1));
        
        var sourcPico = new Rectangle(dimension.X + (dimension.WIDTH * bird.scale.x()) - 20,  dimension.Y + 10, 30, 30);
        RENDER.dibujarTriangulo(sourcPico, Color.red());

        var sourcEye = new Rectangle(dimension.X + (dimension.WIDTH * bird.scale.x()) - 20, dimension.Y + 5, 20, 20);
        RENDER.dibujarCirculo(sourcEye, Color.custom(1, 1, 1, 1));
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