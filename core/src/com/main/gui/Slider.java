package com.main.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;

public class Slider {
    private final int x;
    private final int y;
    private final int width;
    public float barWidth;
    private final int height;
    private final float scale;
    private final Color color;
    private final ShapeRenderer sr;

    public Slider(int x, int y, int width, int height, float scale, Color color)
    {

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.scale = scale;
        this.color = color;
        this.sr = new ShapeRenderer();
        this.barWidth = width;
    }

    public void render(Matrix4 proj) {

        sr.setProjectionMatrix(proj);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(color); // Adjust alpha for darkness
        sr.rect(x, y, barWidth*scale, height*scale);

        sr.end();
    }

    public void setFill(float percent)
    {
        barWidth = width * (percent);
    }


}
