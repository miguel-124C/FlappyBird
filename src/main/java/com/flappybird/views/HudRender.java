package com.flappybird.views;

import com.flappybird.core.ConfigCore;
import com.flappybird.core.HudCore;
import com.flappybird.graphics.SpriteRenderer;
import com.flappybird.interfaces.Sprite;
import com.flappybird.utils.Rectangle;
import com.flappybird.utils.Texture;
import com.flappybird.utils.Vector2;

public class HudRender implements IRender {

    private final HudCore HUD_CORE;
    private final SpriteRenderer RENDER;
    private Texture textureNumber;
    private final Vector2 POS_VELOCITY;

    public HudRender(HudCore hudCore, SpriteRenderer render){
        HUD_CORE = hudCore;
        this.RENDER = render;
        POS_VELOCITY = new Vector2(HUD_CORE.POS_VELOCITY.x() + 105, HUD_CORE.POS_VELOCITY.y() + 10);
    }

    @Override
    public void initialize() {
        RENDER.initialize();
        textureNumber = new Texture("assets/sprites.png");
    }

    @Override
    public void draw(float deltaTime) {
        for (var entity : HUD_CORE.gEntities())
            RENDER.draw(entity.position, entity.sprite, entity.scale);

        for (var player : ConfigCore.getInstance().getPlayers())
            drawNumber(player.getScore() / 10, player.positionToScoreText);

        drawNumber((int)(ConfigCore.getInstance().speedGame * 100), POS_VELOCITY);
    }

    private void drawNumber(int score, Vector2 startPosition) {
        String scoreText = String.valueOf(score);
        float spacing = 7; 
        
        float currentX = startPosition.x();
        float currentY = startPosition.y();

        for (int i = 0; i < scoreText.length(); i++) {
            char c = scoreText.charAt(i);
            int digit = c - '0'; // forma más rápida de pasar de '3' a 3

            Rectangle sourceRect = HUD_CORE.DIGIT_REGION.digitRegions[digit];
            // Construimos el sprite con su recorte exacto
            Sprite digitSprite = new Sprite(textureNumber, sourceRect, 1);
            
            RENDER.draw(
                new Vector2(currentX, currentY), 
                digitSprite, 
                new Vector2(3f, 3f)
            );
            
            // Sumamos el ancho ESPECÍFICO de este número
            currentX += sourceRect.WIDTH + spacing;
        }
    }

    @Override
    public void cleanUp() {
        RENDER.cleanUp();
    }
    
}