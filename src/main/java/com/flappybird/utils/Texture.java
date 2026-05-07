package com.flappybird.utils;

public class Texture {

    private int id;
    private int width;
    private int height;

    public Texture(String path) {
        // cargar imagen con STBImage
        // crear textura OpenGL
    }

    public void bind() {
        // glBindTexture(...)
    }

    public void unbind() {
        // glBindTexture(..., 0)
    }

    public void cleanup() {
        // glDeleteTextures(...)
    }

    public int getId() {
        return id;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}