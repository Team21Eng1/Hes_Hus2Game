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
        this.width = texture.getWidth();
        this.height = texture.getHeight();
        this.showing = false;
        this.scale = 1;
    }
    public Button (Texture texture, int x, int y, float scale)
    {

        this.texture = texture;
        this.x = x;
        this.y = y;
        this.width = texture.getWidth();
        this.height = texture.getHeight();
        this.showing = false;
        this.scale = scale;
    }
    public void Centre()
    {
        x = (int) (x - width*scale/2);
        y = (int) (y - height*scale/2);
    }
    public void Top()
    {
        y = (int) (y-(height * scale + padding[2] + padding[3]));
    }
    public void Right()
    {
        x = (int) (x-(width * scale + padding[0] + padding[1]));
    }


    public Boolean overlap(Vector2 mouse,float zoom)
    {

        if ((mouse.x > (x)*zoom && mouse.x < (x)*zoom + width*scale*zoom) && (mouse.y > (y)*zoom && mouse.y < (y)*zoom + height*scale*zoom))
        {
            return true;
        }
        return false;
    }
    public void render(SpriteBatch batch)
    {
        batch.draw(texture,x,y,width * scale,height * scale);
    }

    public void pad(int left,int right,int up, int down)
    {
        padding = new int[] {left,right,up,down};
        this.x = x + left;
        this.y = y + down;
        this.xPad = (int) (left+right + scale*width);
        this.yPad = (int) (up+down+height*scale);
    }


}
