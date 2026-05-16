package com.flappybird.utils;

public class Color {

    public final float R;
    public final float G;
    public final float B;
    public final float ALPHA;

    private Color(float r, float g, float b, float alpha){
        this.R = r;
        this.G = g;
        this.B = b;
        this.ALPHA = alpha;
    }

    public static Color custom(float r, float g, float b, float alpha){
        return new Color(r, g, b, alpha);
    }

    public static Color red(){
        return new Color(0.7333f, 0.1804f, 0.1804f, 1);
    }

    public static Color yelllow(){
        return new Color(0.8314f, 0.7490f, 0.1529f, 1);
    }

    public static Color blue(){
        return new Color(0.1529f, 0.4118f, 0.8314f, 1);
    }

    public static Color purple(){
        return new Color(0.8314f, 0.1529f, 0.7216f, 1);
    }

    public static Color green(){
        return new Color(0.1922f, 0.8314f, 0.1529f, 1);
    }

}