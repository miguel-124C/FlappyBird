package com.flappybird.core;

import java.util.ArrayList;
import java.util.List;

import com.flappybird.factories.DefaultEntityFactory;
import com.flappybird.interfaces.Entity;
import com.flappybird.utils.Constants;
import com.flappybird.utils.DigitRegion;
import com.flappybird.utils.Rectangle;
import com.flappybird.utils.Vector2;

public class HudCore implements ICore {

    private List<Entity> entities = new ArrayList<>();
    private final DefaultEntityFactory DEFAULT_FACTORY;
    private final Vector2 SCALE = new Vector2(3, 3);
    private final float GAP_SCORE = 5;
    
    public final Vector2 POS_VELOCITY = new Vector2(10, (float)(0.92 * Constants.screenHeight));

    private final ConfigCore CONFIG_CORE;
    public final DigitRegion DIGIT_REGION;

    public HudCore(DefaultEntityFactory defaultEntityFactory, DigitRegion digitRegion){
        DEFAULT_FACTORY = defaultEntityFactory;
        CONFIG_CORE = ConfigCore.getInstance();
        DIGIT_REGION = digitRegion;
    }

    @Override
    public void initialize() {
        var velocityQuad = DEFAULT_FACTORY.createEntity(POS_VELOCITY, "VELOCITY_QUAD", "assets/game_scenary.png", SCALE);
        entities.add(velocityQuad);
    }

    @Override
    public void update(float deltaTime) {
        if (!CONFIG_CORE.isHudCreated) {
            Rectangle prevRectangle = null;
            Vector2 prevScale = null;

            for (var i = 0; i < CONFIG_CORE.getPlayers().size(); i++) {
                var player = CONFIG_CORE.getPlayers().get(i);

                Vector2 positionScore = (prevRectangle != null && prevScale != null)
                    ? new Vector2(10 + ((prevRectangle.WIDTH + GAP_SCORE) * prevScale.x() * i), 10)
                    : new Vector2(10, 10);

                var scoreQuad = DEFAULT_FACTORY.createEntity(positionScore, "SCORE_QUAD", "assets/game_scenary.png", SCALE);
                scoreQuad.sprite.setTintColor(player.COLOR);
                
                prevRectangle = scoreQuad.getDimensions();
                prevScale = scoreQuad.scale;

                var xPosScoreText = prevRectangle.X + (prevRectangle.WIDTH * prevScale.x() * 0.6f);
                player.positionToScoreText = new Vector2( xPosScoreText, prevRectangle.Y + prevRectangle.HEIGHT / 2);

                entities.add(scoreQuad);
                CONFIG_CORE.isHudCreated = true;
            }
        }
    }

    public List<Entity> gEntities(){
        return entities;
    }

}