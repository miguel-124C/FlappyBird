package com.flappybird.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import com.flappybird.interfaces.*;
import com.google.gson.Gson;

public class SpriteAtlasJson {
    
    private String texturePath;
    private final Gson gson = new Gson();
    private Map<String, Sprites> spriteAtlas = new HashMap<>();

    public void loadJson(){
        var path = Global.resolvePath("sprite-atlas.json");
        try {
            String contenido = Files.readString(Path.of(path));
            var json = gson.fromJson(contenido, SpriteAtlas.class);
            
            texturePath = json.texturePath();
            for (var sprite : json.sprites()) {
                spriteAtlas.put(sprite.name(), sprite);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Sprites getSprite(String nameSprite){
        return spriteAtlas.get(nameSprite);
    }

    public String getTexturePath(){
        return texturePath;
    }

}
