package com.flappybird.utils;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;

import org.lwjgl.stb.STBImage;

public class Texture {

    private int id;
    private int width;
    private int height;

    public Texture(String pathImg) {
        String fullPath = Global.resolvePath(pathImg);
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer canales = stack.mallocInt(1);

            // STBImage voltea la imagen para que Y=0 sea abajo (convención OpenGL)
            STBImage.stbi_set_flip_vertically_on_load(false);

            ByteBuffer datos = STBImage.stbi_load(fullPath, w, h, canales, 4);
            if (datos == null)
                throw new RuntimeException("No se pudo cargar: " + fullPath
                    + " — " + STBImage.stbi_failure_reason());

            width  = w.get(0);
            height = h.get(0);

            id = GL11.glGenTextures();
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);

            // Qué hacer si la UV sale fuera de [0,1]
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_CLAMP);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_CLAMP);

            // Filtro al escalar (NEAREST = pixel art, LINEAR = suavizado)
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

            GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA,
                width, height, 0,
                GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, datos);

            STBImage.stbi_image_free(datos);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
        }
    }

    public void cleanup() {
        // glDeleteTextures(...)
    }

    public void bind()   { GL11.glBindTexture(GL11.GL_TEXTURE_2D, id); }
    public void unbind() { GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0); }
    public void delete() { GL11.glDeleteTextures(id); }
    public int getId()   { return id; }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}