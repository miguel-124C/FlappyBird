package com.flappybird.factories;

import java.util.Random;

import com.flappybird.interfaces.Sprite;
import com.flappybird.models.entities.PipeEntity;
import com.flappybird.utils.*;

public class PipeFactory {

    private final float GAP_BETWEEN_PIPES = 200f;
    private final float MAX_HEIGHT_PIPE;
    private final float MIN_HEIGHT_PIPE;

    private final int LIMIT_WIDTH = Constants.screenWidth; // 1024
    private final int LIMIT_HEIGHT = Constants.screenHeight; // 768

    private final String TEXTURE_PATH;

    private final SpriteAtlasJson SPRITE_ATLAS;
    private final Random RANDOM = new Random();
    private final Vector2 SCALE = new Vector2(3, 4);

    public PipeFactory(SpriteAtlasJson spriteAtlasJson){
        this.SPRITE_ATLAS = spriteAtlasJson;
        TEXTURE_PATH = SPRITE_ATLAS.getTexturePath();
        MIN_HEIGHT_PIPE = 100f;
        MAX_HEIGHT_PIPE = LIMIT_HEIGHT - GAP_BETWEEN_PIPES - MIN_HEIGHT_PIPE;
    }

    public PipeEntity spawnPipe() {
        var texture = new Texture(TEXTURE_PATH);
        
        var spAtlas = SPRITE_ATLAS.getSprite("PIPE_TOP");
        
        var scaledPipeHeight = spAtlas.h() * SCALE.y();
        var visibleHeight =  RANDOM.nextFloat(MIN_HEIGHT_PIPE, MAX_HEIGHT_PIPE);
        // Empujamos el pipe hacia arriba (Y negativo) para que solo se asome 'visibleHeight'
        // Como el (0,0) es arriba a la izquierda, restamos el alto total menos lo visible.
        float yPosition = visibleHeight - scaledPipeHeight;
        var position = new Vector2(LIMIT_WIDTH, yPosition);

        var sourceRectangle = new Rectangle(spAtlas.x(), spAtlas.y(), spAtlas.w(), spAtlas.h());

        var sprite = new Sprite(texture, sourceRectangle, 0, spAtlas.totalFrames());
        return new PipeEntity(position, sprite, SCALE);
    }

    public PipeEntity spawnSecondPipe(PipeEntity pipe){
        var spAtlas = SPRITE_ATLAS.getSprite("PIPE_BOTTOM");
        var texture = new Texture(TEXTURE_PATH);

        var pipeDimension = pipe.getDimensions();
        // Como pipeDimension.Y es negativo, al sumarle su alto real nos da exactamente el borde inferior visible.
        var topPipeBottomEdge = pipeDimension.Y + (pipeDimension.HEIGHT * pipe.scale.y());

        var ySecondPipe = topPipeBottomEdge + GAP_BETWEEN_PIPES;
        var position = new Vector2(pipeDimension.X, ySecondPipe);

        var sourceRectangle = new Rectangle(spAtlas.x(), spAtlas.y(), spAtlas.w(), spAtlas.h());

        var sprite = new Sprite(texture, sourceRectangle, 0, spAtlas.totalFrames());
        return new PipeEntity(position, sprite, SCALE);
    }

}