package com.flappybird.views;

import com.flappybird.core.MenuCore;
import com.flappybird.graphics.SpriteRenderer;
import com.flappybird.utils.Rectangle;
import com.flappybird.utils.Vector2;

public class MenuRender implements IRender {

    private final MenuCore MENU;
    private final SpriteRenderer RENDER = new SpriteRenderer();

    public MenuRender(MenuCore menuCore){
        MENU = menuCore;
    }

    @Override
    public void draw(float deltaTime) {
        for (var entity : MENU.getEntities()) {
            var dimension = entity.getDimensions();
            var sprite = entity.sprite;
            var sourceRectangle = sprite.SOURCET_RECTANGLE;

            var x = sourceRectangle.X;
            var y = sourceRectangle.Y;
            switch (sprite.getAnimDirection()) {
                case DOWN:
                    y = sourceRectangle.Y + (sprite.getCurrentFrame() * dimension.HEIGHT * entity.scale.y());
                    break;
                case LEFT:
                    x = sourceRectangle.X + (sprite.getCurrentFrame() * dimension.WIDTH * entity.scale.x());
                    break;
                default:
                    break;
            }

            var sourceRec = new Rectangle(x, y, dimension.WIDTH, dimension.HEIGHT);
            RENDER.draw(sprite.TEXTURE, entity.position, entity.scale, sourceRec);
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