package com.flappybird.core.Game;

import java.util.ArrayList;
import java.util.List;

import com.flappybird.factories.DefaultEntityFactory;
import com.flappybird.interfaces.Entity;
import com.flappybird.models.World;
import com.flappybird.utils.*;

public class GameSpriteCore extends GameCore {

    private final DefaultEntityFactory DEFAULT_FACTORY;
    private List<Entity> entities = new ArrayList<>();
    private final String NAME_TEXTURE_SCENARY = "assets/game_scenary.png";

    private Entity Cloud;
    private Entity Building;
    private Entity Grass;

    public GameSpriteCore(World world, DefaultEntityFactory defaultEntityFactory){
        super(world);
        DEFAULT_FACTORY = defaultEntityFactory;
    }

    @Override
    public void initialize() {
        var timeDay = Global.getTimeDay();

        var scale = new Vector2(4, 4);
        
        String nameSpriteCloud;
        String nameSpriteBuilding;
        String nameSpriteGrass;
        switch (timeDay) {
            case DAY:
                nameSpriteCloud = "CLOUD_DAY";
                nameSpriteBuilding = "BUILDING_DAY";
                nameSpriteGrass = "GRASS_DAY";
                break;
            case NIGHT:
                nameSpriteCloud = "CLOUD_NIGHT";
                nameSpriteBuilding = "BUILDING_NIGHT";
                nameSpriteGrass = "GRASS_NIGHT";
                break;
            default:
                nameSpriteCloud = "CLOUD_DAY";
                nameSpriteBuilding = "BUILDING_DAY";
                nameSpriteGrass = "GRASS_DAY";
                break;
        }
        var positionCloud = new Vector2(0, (int)(0.3177 * Constants.screenHeight));
        var positionBuilding = new Vector2(0, (int)(0.489583 * Constants.screenHeight));
        var positionGrass = new Vector2(0, (int)(0.739583 * Constants.screenHeight));
        
        Cloud = DEFAULT_FACTORY.createEntity(positionCloud, nameSpriteCloud, NAME_TEXTURE_SCENARY, scale);
        Building = DEFAULT_FACTORY.createEntity(positionBuilding, nameSpriteBuilding, NAME_TEXTURE_SCENARY, scale);
        Grass = DEFAULT_FACTORY.createEntity(positionGrass, nameSpriteGrass, NAME_TEXTURE_SCENARY, scale);

        entities.add(Cloud);
        entities.add(Building);
        entities.add(Grass);

        super.initialize();
    }

    @Override
    public void update(float deltaTime){
        super.update(deltaTime);
    }

    public List<Entity> getEntities(){
        return entities;
    }

}