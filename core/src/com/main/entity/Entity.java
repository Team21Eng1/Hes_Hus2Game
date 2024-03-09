package com.main.entity;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Entity {
    public float x, y;
    public float speed; // walking speed per frame
    public Animation<TextureRegion> currentAnimation;
    public float stateTime; // Tracks animation time

    // Constructor, getters, setters, and other common methods can be added here
}