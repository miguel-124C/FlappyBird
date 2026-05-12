package com.flappybird.interfaces;

public record SpriteAtlas(
    String texturePath,
    Sprites[] sprites
){ }