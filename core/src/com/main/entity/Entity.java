package com.main.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;

public class Entity extends Sprite {
    public float worldX, worldY;
    public float speed; // walking speed per frame
    public Animation<TextureRegion> currentAnimation;
    public float stateTime; // Tracks animation time

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
        return currentAnimation.getKeyFrame(stateTime, true);
    }

}
