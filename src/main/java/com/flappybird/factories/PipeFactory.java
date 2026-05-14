package com.flappybird.factories;

import java.util.Random;

import com.flappybird.interfaces.Sprite;
import com.flappybird.models.entities.PipeEntity;
import com.flappybird.utils.*;

public class PipeFactory {

    private final float GAP_BETWEEN_PIPES = 200f;
    private final float MAX_HEIGHT_PIPE;
    private final float MIN_HEIGHT_PIPE;

    private final int LIMIT_WIDTH = Constants.screenWidth;
    private final int LIMIT_HEIGHT = Constants.screenHeight;

    private final int WIDTH_PIPE = 48;
    private final String TEXTURE_PATH;

    private final SpriteAtlasJson SPRITE_ATLAS;
    private final Random RANDOM = new Random();

    public PipeFactory(SpriteAtlasJson spriteAtlasJson){
        this.SPRITE_ATLAS = spriteAtlasJson;
        TEXTURE_PATH = SPRITE_ATLAS.getTexturePath();
        MIN_HEIGHT_PIPE = 50f;
        MAX_HEIGHT_PIPE = LIMIT_HEIGHT - GAP_BETWEEN_PIPES - MIN_HEIGHT_PIPE;
    }

    public PipeEntity spawnPipe() {
        var position = new Vector2(LIMIT_WIDTH, 0);

        var spAtlas = SPRITE_ATLAS.getSprite("PIPE_TOP");

        var texture = new Texture(TEXTURE_PATH);
        var heightSource = RANDOM.nextFloat(MIN_HEIGHT_PIPE, MAX_HEIGHT_PIPE);
        var sourceRectangle = new Rectangle(spAtlas.x(), spAtlas.y(), WIDTH_PIPE, heightSource);

        var sprite = new Sprite(texture, sourceRectangle, 0, spAtlas.totalFrames());
        return new PipeEntity(position, sprite);
    }

    public PipeEntity spawnSecondPipe(PipeEntity pipe){
        var pipeDimension = pipe.getDimensions();
        var heightPipe = pipeDimension.Y + pipeDimension.HEIGHT;

        var ySecondPipe = heightPipe + GAP_BETWEEN_PIPES;
        var position = new Vector2(pipeDimension.X, ySecondPipe);

        var spAtlas = SPRITE_ATLAS.getSprite("PIPE_BOTTOM");

        var texture = new Texture(TEXTURE_PATH);
        var heightSource = LIMIT_HEIGHT - ySecondPipe;
        var sourceRectangle = new Rectangle(spAtlas.x(), spAtlas.y(), WIDTH_PIPE, heightSource);

        var sprite = new Sprite(texture, sourceRectangle, 0, spAtlas.totalFrames());
        return new PipeEntity(position, sprite);
    }

}