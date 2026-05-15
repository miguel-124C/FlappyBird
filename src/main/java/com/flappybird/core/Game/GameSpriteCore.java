package com.flappybird.core.Game;

import java.util.ArrayList;
import java.util.List;

import com.flappybird.core.ConfigCore;
import com.flappybird.factories.DefaultEntityFactory;
import com.flappybird.interfaces.Entity;
import com.flappybird.interfaces.enums.GameState;
import com.flappybird.models.World;
import com.flappybird.utils.*;

public class GameSpriteCore extends GameCore {

    private final DefaultEntityFactory DEFAULT_FACTORY;
    private List<Entity> entities = new ArrayList<>();
    private final String NAME_TEXTURE_SCENARY = "assets/game_scenary.png";
    private final Vector2 SCALE = new Vector2(4, 4);

    private Entity Cloud;
    private Entity Cloud2;
    private Entity Building;
    private Entity Building2;
    private Entity Grass;
    private Entity Grass2;

    public GameSpriteCore(World world, DefaultEntityFactory defaultEntityFactory){
        super(world);
        DEFAULT_FACTORY = defaultEntityFactory;
    }

    @Override
    public void initialize() {
        var timeDay = Global.getTimeDay();
        
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

        Cloud = DEFAULT_FACTORY.createEntity(positionCloud, nameSpriteCloud, NAME_TEXTURE_SCENARY, SCALE);
        Cloud2 = createEntityParallax(Cloud, nameSpriteCloud, 0.1f);
        
        Building = DEFAULT_FACTORY.createEntity(positionBuilding, nameSpriteBuilding, NAME_TEXTURE_SCENARY, SCALE);
        Building2 = createEntityParallax(Building, nameSpriteBuilding, 0.4f);

        Grass = DEFAULT_FACTORY.createEntity(positionGrass, nameSpriteGrass, NAME_TEXTURE_SCENARY, SCALE);
        Grass2 = createEntityParallax(Grass, nameSpriteGrass, 0.7f);

        super.initialize();
    }

    @Override
    public void update(float deltaTime){
        updatedEntityParallaxEffect(deltaTime, Cloud, Cloud2);
        updatedEntityParallaxEffect(deltaTime, Building, Building2);
        updatedEntityParallaxEffect(deltaTime, Grass, Grass2);

        super.update(deltaTime);
        
        if (ConfigCore.getInstance().gameState == GameState.GAME_OVER)
            for (Entity entity : entities)
                entity.desplazmentEnlapsed = 0;
    }

    private void updatedEntityParallaxEffect(float deltaTime, Entity entity1, Entity entity2) {
        // Acumular el movimiento total según el tiempo transcurrido
        entity1.desplazmentEnlapsed += (getPipeSpeed() * deltaTime) * entity1.factorParallax;
        
        // Aplicar el truco del módulo matemático
        var widthSprite = entity1.getDimensions().WIDTH * entity1.scale.x();
        float xSprite1 = entity1.desplazmentEnlapsed % widthSprite;

        float xSprite2 = xSprite1 - widthSprite;

        var positionOne = entity1.position;
        var positionTwo = entity2.position;

        entity1.position = new Vector2(xSprite1, positionOne.y());
        entity2.position = new Vector2(xSprite2, positionTwo.y());
    }

    private Entity createEntityParallax(Entity entity, String nameSprite, float factorParallax){
        entity.factorParallax = factorParallax;
        entities.add(entity);

        var dimension = entity.getDimensions();
        var position = new Vector2(dimension.X - (dimension.WIDTH * Cloud.scale.x()), dimension.Y);
        var entity2 = DEFAULT_FACTORY.createEntity(position, nameSprite, NAME_TEXTURE_SCENARY, SCALE);
        entity2.sprite.changeFrame(1);
        entities.add(entity2);

        return entity2;
    }

    public List<Entity> getEntities(){
        return entities;
    }

}