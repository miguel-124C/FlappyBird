package com.flappybird.utils;

import java.time.LocalTime;

import com.flappybird.interfaces.Entity;
import com.flappybird.interfaces.Sprite;
import com.flappybird.interfaces.enums.TimeDay;

public class Global {
    
    public static String resolvePath(String path) {
        var url = Texture.class.getClassLoader().getResource(path);
        if (url == null)
            throw new RuntimeException("Recurso no encontrado en classpath: " + path);
        
        try {
            // URI decodifica automáticamente %20 → espacio
            return java.nio.file.Paths.get(url.toURI()).toString();
        } catch (java.net.URISyntaxException e) {
            throw new RuntimeException("Ruta inválida: " + path, e);
        }
    }

    public static Sprite getSpriteToDraw(Entity entity, float gapSprite){
        var dimension = entity.getDimensions();
        var sprite = entity.sprite;
        var sourceRectangle = sprite.SOURCET_RECTANGLE;

        var x = sourceRectangle.X;
        var y = sourceRectangle.Y;
        switch (sprite.getAnimDirection()) {
            case DOWN:
                y = sourceRectangle.Y + (sprite.getCurrentFrame() * (dimension.HEIGHT + gapSprite));
                break;
            case RIGHT:
                x = sourceRectangle.X + (sprite.getCurrentFrame() * (dimension.WIDTH + gapSprite));
                break;
            default:
                break;
        }

        var sourceRec = new Rectangle(x, y, dimension.WIDTH, dimension.HEIGHT);
        return new Sprite(sprite.TEXTURE, sourceRec, sprite.TOTAL_FRAMES);
    }

    public static TimeDay getTimeDay(){
        var localTime = LocalTime.now();
        var hour = localTime.getHour();

        return (hour >= 5 && hour <= 17)
            ? TimeDay.DAY
            : TimeDay.NIGHT;
    }

}
