package com.flappybird.factories;

import java.util.Random;

import com.flappybird.interfaces.Sprite;
import com.flappybird.models.PipeEntity;
import com.flappybird.utils.*;

public class PipeFactory {

    private final float GAP_BETWEEN_PIPES = 200f;
    private final float MAX_HEIGHT_PIPE;
    private final float MIN_HEIGHT_PIPE;

    private final int LIMIT_WIDTH = Constants.screenWidth;
    private final int LIMIT_HEGHT = Constants.screenHeight;

    private final int WIDTH_PIPE = 48;

    private final Random random = new Random();

    public PipeFactory(){
        MIN_HEIGHT_PIPE = 50f;
        MAX_HEIGHT_PIPE = Constants.screenHeight - GAP_BETWEEN_PIPES - MIN_HEIGHT_PIPE;
    }

    public PipeEntity spawnPipe() {
        var position = new Vector2(LIMIT_WIDTH, 0);

        var texture = new Texture("");
        var heightSource = random.nextFloat(MIN_HEIGHT_PIPE, MAX_HEIGHT_PIPE);
        var sourceRectangle = new Rectangle(303, 0, WIDTH_PIPE, heightSource);

        var sprite = new Sprite(texture, sourceRectangle);
        return new PipeEntity(position, sprite);
    }

    public PipeEntity spawnSecondPipe(PipeEntity pipe){
        var pipeDimension = pipe.getDimensions();
        var heightPipe = pipeDimension.Y + pipeDimension.HEIGHT;

        var ySecondPipe = heightPipe + GAP_BETWEEN_PIPES;
        var position = new Vector2(pipeDimension.X, ySecondPipe);

        var texture = new Texture("");
        var heightSource = LIMIT_HEGHT - ySecondPipe;
        var sourceRectangle = new Rectangle(330, 0, WIDTH_PIPE, heightSource);

        var sprite = new Sprite(texture, sourceRectangle);
        return new PipeEntity(position, sprite);
    }

}