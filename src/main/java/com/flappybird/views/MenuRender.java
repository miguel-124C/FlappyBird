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
            var scale = new Vector2(3, 3);
            var sprite = entity.sprite;
            var sourceRectangle = sprite.SOURCET_RECTANGLE;

            var y = sourceRectangle.Y + (sprite.getCurrentFrame() * dimension.HEIGHT * scale.y());
            var sourceRec = new Rectangle(sourceRectangle.X, y, dimension.WIDTH, dimension.HEIGHT);
            RENDER.draw(sprite.TEXTURE, entity.position, scale, sourceRec);
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