package com.main.gui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;

public class TextBox {
    private final String text;
    private final int height;
    private final int width;
    private int xPos;
    private int yPos;
    BitmapFont font;

    public TextBox(String text,int xPos,int yPos, int height, int width, BitmapFont font)
    {
        this.text = text;
        this.height = height;
        this.width = width;
        this.font = font;
        this.xPos = xPos;
        this.yPos = yPos;
    }
    public void setPosition(int x,int y)
    {
        xPos=x;
        yPos=y;
    }


    public void render(SpriteBatch batch)
    {
        font.draw(batch, text, xPos, yPos,width ,height,false);
    }

}
