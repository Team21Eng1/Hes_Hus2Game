package com.main.gui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;

public class TextBox {
    private final String text;
    private final int height;
    private final int width;
    private final SpriteBatch batch;
    private final int xPos;
    private final int yPos;
    BitmapFont font;

    public TextBox(String text,int xPos,int yPos, int height, int width, BitmapFont font, SpriteBatch batch)
    {
        this.text = text;
        this.height = height;
        this.width = width;
        this.font = font;
        this.batch = batch;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public void render()
    {
        font.draw(batch, text, xPos, yPos,width ,height,true);
    }

}
