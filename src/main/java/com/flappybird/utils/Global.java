package com.flappybird.utils;

import java.time.LocalTime;

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

    public static TimeDay getTimeDay(){
        var localTime = LocalTime.now();
        var hour = localTime.getHour();

        return (hour >= 5 && hour <= 17)
            ? TimeDay.DAY
            : TimeDay.NIGHT;
    }

}
