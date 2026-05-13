package com.flappybird.factories;

import com.flappybird.interfaces.Sprite;
import com.flappybird.models.entities.BirdEntity;
import com.flappybird.utils.*;

public class BirdFactory {

    private final SpriteAtlasJson SPRITE_ATLAS;
    private final String TEXTURE_PATH;

    public BirdFactory(SpriteAtlasJson spriteAtlasJson){
        this.SPRITE_ATLAS = spriteAtlasJson;
        TEXTURE_PATH = SPRITE_ATLAS.getTexturePath();
    }

    public BirdEntity create(Vector2 position, Vector2 scale){
        var spAtlas = SPRITE_ATLAS.getSprite("BIRD");
        var texture = new Texture(TEXTURE_PATH);

        var sourceRectangle = new Rectangle(spAtlas.x(), spAtlas.y(), spAtlas.w(), spAtlas.h());
        var sprite = new Sprite(texture, sourceRectangle, 0, spAtlas.totalFrames());

        return new BirdEntity(position, sprite, scale);
    }

}