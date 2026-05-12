package com.flappybird.factories;

import com.flappybird.interfaces.Sprite;
import com.flappybird.models.entities.DefaultEntity;
import com.flappybird.utils.*;

public class DefaultEntityFactory {

    private final SpriteAtlasJson SPRITE_ATLAS;

    public DefaultEntityFactory(SpriteAtlasJson spriteAtlasJson){
        SPRITE_ATLAS = spriteAtlasJson;
    }

    public DefaultEntity createEntity(Vector2 position, String nameSprite, String nameTexture){
        var spAtlas = SPRITE_ATLAS.getSprite(nameSprite);

        var texture = new Texture(nameTexture);
        var sourceRectangle = new Rectangle(spAtlas.x(), spAtlas.y(), spAtlas.w(), spAtlas.h());

        var sprite = new Sprite(texture, sourceRectangle, 0, spAtlas.totalFrames());
        return new DefaultEntity(position, sprite);
    }

    public DefaultEntity createEntityInCenter(String nameSprite, String nameTexture, Vector2 scale){
        var spAtlas = SPRITE_ATLAS.getSprite(nameSprite);

        var texture = new Texture(nameTexture);
        var sourceRectangle = new Rectangle(spAtlas.x(), spAtlas.y(), spAtlas.w(), spAtlas.h());

        var xCenter = (int)(Constants.screenWidth - (spAtlas.w() * scale.x())) / 2;
        var yCenter = (int)(Constants.screenHeight - (spAtlas.h() * scale.y())) / 2;
        var position = new Vector2(xCenter, yCenter);

        var sprite = new Sprite(texture, sourceRectangle, 0, spAtlas.totalFrames());
        return new DefaultEntity(position, sprite);
    }
}