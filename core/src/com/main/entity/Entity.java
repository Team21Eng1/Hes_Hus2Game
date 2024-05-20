package com.main.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.main.utils.CollisionHandler;

import java.util.ArrayList;

public class Entity extends Sprite {
    public CollisionHandler collisionHandler;
    public float worldX, worldY;
    public static int spriteX;// this is in reference to the sprite sheet
    public static int spriteY;
    public char dir;
    public float speed; // walking speed per frame
    public Animation<TextureRegion> currentAnimation;
    public float stateTime; // Tracks animation time
    public Boolean loop = true;
    public Boolean isMoving = false;
    public Entity(int startX,int startY)
    {
        worldX = startX;
        worldY = startY;
        this.dir = 'D';
    }
    public void setMoving(Boolean bool)
    {
        this.isMoving = bool;
    }
    public void setDir(char newDir)
    {
        this.dir = newDir;
    }


    public TextureRegion[] getFrames(Texture sprSheet, int start, int end, int row, int width, int height, int gutX, int gutY, int marX, int marY, boolean flip)
    {
        int x,y;
        ArrayList<TextureRegion> temp = new ArrayList<TextureRegion>();
        for (int i = 0; i < end-start+1; i++) {
            x = marX + i*(width+gutX);
            y = marY + row*(height+gutY);
            TextureRegion reg = new TextureRegion(sprSheet,x,y,width,height);
            reg.flip(flip, false);
            temp.add(reg);
        }
        TextureRegion[] frames = temp.toArray(new TextureRegion[0]);
        return frames;
    }
    public TextureRegion[] getFrames(Texture sprSheet, int start, int end, int row, int width, int height, boolean flip)
    {
        int x,y;
        ArrayList<TextureRegion> temp = new ArrayList<TextureRegion>();
        for (int i = 0; i < end-start+1; i++) {
            x = i*width;
            y = row*height;
            TextureRegion reg = new TextureRegion(sprSheet,x,y,width,height);
            reg.flip(flip, false);
            temp.add(reg);
        }
        TextureRegion[] frames = temp.toArray(new TextureRegion[0]);
        return frames;
    }
    public TextureRegion[] getFrames(Texture sprSheet,int[] ind , int row, int width, int height, boolean flip)
    {
        int x,y;
        ArrayList<TextureRegion> temp = new ArrayList<TextureRegion>();
        for (int i = 0; i < ind.length; i++) {
            x = ind[i]*width;
            y = row*height;
            TextureRegion reg = new TextureRegion(sprSheet,x,y,width,height);
            reg.flip(flip, false);
            temp.add(reg);
        }
        TextureRegion[] frames = temp.toArray(new TextureRegion[0]);
        return frames;
    }

    public TextureRegion getCurrentFrame(){
        return currentAnimation.getKeyFrame(stateTime, this.loop);
    }

    public void update(float delta) {

    }
    public int getSpriteX()
    {
        return spriteX;
    }
    public int getSpriteY()
    {
        return spriteY;
    }

}
