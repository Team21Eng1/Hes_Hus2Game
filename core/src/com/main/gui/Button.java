package com.main.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Button {
    public final Texture texture;
    public int x,y,width,height;
    public final float scale;
    public boolean showing;
    public int[] padding = new int[] {0,0,0,0};
    public int xPad,yPad;

    public Button (Texture texture, int x, int y)
    {

        this.texture = texture;
        this.x = x;
        this.y = y;
        this.scale = 1;
        this.width = (int) (texture.getWidth()*scale);
        this.height = (int) (texture.getHeight()*scale);
        this.showing = false;

    }
    public Button (Texture texture, int x, int y, float scale)
    {

        this.texture = texture;
        this.x = x;
        this.y = y;
        this.scale = scale;
        this.width = (int) (texture.getWidth()*scale);
        this.height = (int) (texture.getHeight()*scale);
        this.showing = false;

    }
    public void Centre()
    {
        x = (int) (x - width/2);
        y = (int) (y - height/2);
    }
    public void Top()
    {
        y = (int) (y-(height + padding[2] + padding[3]));
    }
    public void Right()
    {
        x = (int) (x-(width + padding[0] + padding[1]));
    }


    public Boolean overlap(Vector2 mouse,float zoom)
    {

        if ((mouse.x > (x)*zoom && mouse.x < (x)*zoom + width*zoom) && (mouse.y > (y)*zoom && mouse.y < (y)*zoom + height*zoom))
        {
            return true;
        }
        return false;
    }
    public void render(SpriteBatch batch)
    {
        batch.draw(texture,x,y,width,height);
    }

    public void pad(int left,int right,int up, int down)
    {
        padding = new int[] {left,right,up,down};
        this.x = x + left;
        this.y = y + down;
        this.xPad = (int) (left+right + width);
        this.yPad = (int) (up+down+ height);
    }


}
