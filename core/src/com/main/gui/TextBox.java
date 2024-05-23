package com.main.gui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Align;

public class TextBox {
    public String text;
    private final int height;
    private final int width;
    private int xPos;
    private int yPos;
    BitmapFont font;
    public int align;
    public TextBox(String text,int xPos,int yPos, int width, int height, BitmapFont font)
    {
        this.text = text;
        this.height = height;

        this.font = font;
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.align = Align.center;
        font.getData().setScale(1);
    }
    public void scaleFont(float scale)
    {
        font.getData().setScale(scale);
    }

    public void setPosition(int x,int y)
    {
        xPos=x;
        yPos=y;
    }


    public void render(SpriteBatch batch)
    {
        font.draw(batch, text, xPos, yPos,width, align,true);
    }

    public void Centre()
    {
        xPos = (int) (xPos - width/2);
        //yPos = (int) (yPos - height/2);
    }
    public int getX(){return xPos;}
    public int getY(){return yPos;}
}
