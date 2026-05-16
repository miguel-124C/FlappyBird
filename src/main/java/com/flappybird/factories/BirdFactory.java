package com.flappybird.factories;

import com.flappybird.interfaces.Sprite;
import com.flappybird.interfaces.enums.BirdColor;
import com.flappybird.interfaces.enums.Direction;
import com.flappybird.models.entities.BirdEntity;
import com.flappybird.utils.*;

public class BirdFactory {

    private final SpriteAtlasJson SPRITE_ATLAS;

    public BirdFactory(SpriteAtlasJson spriteAtlasJson){
        this.SPRITE_ATLAS = spriteAtlasJson;
    }

    public BirdEntity create(Vector2 position, Vector2 scale, BirdColor birdColor){
        String nameSprite;
        switch (birdColor) {
            case YELLOW:
                nameSprite = "BIRD_YELLOW";
                break;
            case RED:
                nameSprite = "BIRD_RED";
                break;
            case BLUE:
                nameSprite = "BIRD_BLUE";
                break;
            case GREEN:
                nameSprite = "BIRD_GREEN";
                break;
            case PURPLE:
                nameSprite = "BIRD_PURPLE";
                break;
            default:
                nameSprite = "BIRD_YELLOW";
                break;
        }

        var spAtlas = SPRITE_ATLAS.getSprite(nameSprite);
        var texture = new Texture("assets/game_scenary.png");

        var sourceRectangle = new Rectangle(spAtlas.x(), spAtlas.y(), spAtlas.w(), spAtlas.h());
        var sprite = new Sprite(texture, sourceRectangle, 0, spAtlas.totalFrames());
        sprite.setAnimationDirection(Direction.RIGHT);

        return new BirdEntity(position, sprite, scale);
    }

}