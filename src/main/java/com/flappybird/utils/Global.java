package com.flappybird.utils;

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

}
