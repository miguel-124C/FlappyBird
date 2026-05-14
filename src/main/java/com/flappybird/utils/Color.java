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
        return new Color(128, 0, 0, 1);
    }

    public static Color yelllow(){
        return new Color(128, 0, 0, 1);
    }

    public static Color blue(){
        return new Color(0, 0, 128, 1);
    }

    public static Color orange(){
        return new Color(128, 0, 0, 1);
    }

    public static Color purple(){
        return new Color(128, 0, 0, 1);
    }

    public static Color green(){
        return new Color(128, 0, 0, 1);
    }

}