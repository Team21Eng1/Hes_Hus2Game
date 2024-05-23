package com.main.gui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Popup {

    public int x, y;
    public final float scale;
    public Button durMenuBg, durUpB, durDownB, BackB,GoB;
    public TextBox Title, CloseDesc;
    public BitmapFont font;
    public Boolean showing;
    private int duration;
    float t,timer;
    boolean flash = false;

    public Popup(int x, int y, float scale, BitmapFont font)
    {
        duration = 1;
        this.x = x;
        this.y = y;
        this.scale = scale;
        this.font = font;
        this.showing = true;
        this.timer = 0.6f;
        this.t = 0;
        SetUp();
    }
    public void SetUp() {
        durMenuBg = new Button(new Texture("duration_menu_background.png"), x, y, scale);
        durMenuBg.Centre();
        font.getData().setScale(2);
        Title = new TextBox("TestPopup", durMenuBg.x, (int) (durMenuBg.y + durMenuBg.height) - 20, durMenuBg.width, 200, font);
        CloseDesc = new TextBox("Press space to close", durMenuBg.x, durMenuBg.y + 150, durMenuBg.width, 200, font);


    }
    public void render(SpriteBatch batch) {
        if (showing) {
            durMenuBg.render(batch);
            font.getData().setScale(1.5f);
            Title.render(batch);
            font.getData().setScale(0.7f);
            if (flash) CloseDesc.render(batch);

            font.getData().setScale(1);
        }
    }
    public void update(float delta)
    {
        t+=delta;
        if (t>timer) {
            flash = !flash;
            t=0;
        }
    }

}
