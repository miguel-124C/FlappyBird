package com.flappybird.utils;

public class Color {

    public final int R;
    public final int G;
    public final int B;
    public final float ALPHA;

    private Color(int r, int g, int b, float alpha){
        this.R = r;
        this.G = g;
        this.B = b;
        this.ALPHA = alpha;
    }

    public static Color custom(int r, int g, int b, float alpha){
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