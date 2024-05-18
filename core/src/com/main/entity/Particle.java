package com.main.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.utils.Disposable;
import com.main.utils.ParticleSys;

public class Particle extends Entity implements Disposable {
    ParticleSys ps;
    public Particle(int x, int y, Texture sprSheet, ParticleSys parent)
    {
        super(x,y);
        currentAnimation = new Animation<>(0.1f, getFrames(sprSheet,0,5,0,32,32,32,0,16,16,false));
        this.loop = false;
        this.ps = parent;
    }
    public void setPos(int x, int y)
    {
        this.worldX = x;
        this.worldY = y;
    }
    public void update(float delta)
    {
        stateTime += delta;

    }
    public void Down()
    {
        worldX = worldX - getCurrentFrame().getRegionWidth()/2 + 2;
        worldY = worldY - getCurrentFrame().getRegionHeight()/2 + 10;
    }
    public void Left()
    {
        worldX = worldX - getCurrentFrame().getRegionWidth()/2;
        worldY = worldY - 4;
    }
    public void Right()
    {
        worldX = worldX - getCurrentFrame().getRegionWidth()/2 + 4;
        worldY = worldY - 4;
    }
    public void Up()
    {
        worldX = worldX - getCurrentFrame().getRegionWidth()/2 + 3;
        worldY = worldY - getCurrentFrame().getRegionHeight()/2;
    }




    @Override
    public void dispose() {

    }
}
