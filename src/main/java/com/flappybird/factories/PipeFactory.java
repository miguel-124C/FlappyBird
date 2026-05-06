package com.flappybird.factories;

import java.util.Random;

import com.flappybird.models.PipeEntity;
import com.flappybird.utils.*;

public class PipeFactory {

    private final float GAP_BETWEEN_PIPES = 50f;
    private final float MAX_HEIGHT_PIPE   = 10f;
    private final float MIN_HEIGHT_PIPE   = 5f;

    private final int LIMIT_WIDTH = Constants.screenWidth;
    private final int LIMIT_HEGHT = Constants.screenHeight;

    private final Random random = new Random();

    public PipeEntity spawnPipe() {
        var position = new Vector2(LIMIT_WIDTH, 0);

        var heightSource = random.nextFloat(MIN_HEIGHT_PIPE, MAX_HEIGHT_PIPE);
        var sourceRectangle = new Rectangle(303, 0, 24, heightSource);

        return new PipeEntity(position, sourceRectangle);
    }

    public PipeEntity spawnSecondPipe(PipeEntity pipe){
        var pipeDimension = pipe.getDimensions();
        var heightPipe = pipeDimension.Y + pipeDimension.HEIGHT;

        var ySecondPipe = heightPipe + GAP_BETWEEN_PIPES;
        var position = new Vector2(pipeDimension.X, ySecondPipe);

        var heightSource = LIMIT_HEGHT - ySecondPipe;
        var sourceRectangle = new Rectangle(330, 0, 24, heightSource);

        return new PipeEntity(position, sourceRectangle);
    }

}