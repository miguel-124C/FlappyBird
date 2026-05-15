package com.flappybird.utils;

import com.flappybird.interfaces.enums.GameRenderType;

public abstract class Constants {
    public static final int screenWidth = 1024;
    public static final int screenHeight = 768;

    // El valor de PIPE_SPEED * TIME_PER_PIPES debe dar DISTANCE_PER_PIPES
    public static float PIPE_SPEED = 200f;
    public static float TIME_PER_PIPES = 2;
    public static float DISTANCE_PER_PIPES = 400;

    public static GameRenderType GAME_RENDER_TYPE = GameRenderType.SPRITES;
}