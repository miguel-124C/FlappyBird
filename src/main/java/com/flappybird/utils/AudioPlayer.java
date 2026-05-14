package com.flappybird.utils;

import javax.sound.sampled.*;
import java.io.File;

public class AudioPlayer {
    
    private Clip clip;

    private AudioPlayer(Clip clip) {
        this.clip = clip;
    }

    public static AudioPlayer load(String filePath) {
        try {
            File soundFile = new File(filePath);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            
            return new AudioPlayer(clip);
        } catch (Exception e) {
            System.err.println("Error al cargar el audio: " + filePath);
            e.printStackTrace();
            return null;
        }
    }

    // Reproduce el sonido de forma instantánea
    public void play() {
        if (clip != null) {
            // Detener el clip si ya estaba sonando
            clip.stop();
            // Regresar el "cabezal" al inicio del sonido (frame 0)
            clip.setFramePosition(0);
            // Reproducir
            clip.start();
        }
    }

    // Opcional: Para cuando cambies de escena y quieras liberar RAM
    public void dispose() {
        if (clip != null && clip.isOpen()) {
            clip.close();
        }
    }
    
}