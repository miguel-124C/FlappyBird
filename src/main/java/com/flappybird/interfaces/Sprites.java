package com.flappybird.interfaces;

public record Sprites(
    String name,
    int x,
    int y,
    int w,
    int h,
    int totalFrames
) { }