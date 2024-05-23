package com.main.gui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SleepPopup extends Popup{

    private TextBox Content;

    public SleepPopup(int x, int y, float scale, BitmapFont font) {
        super(x, y, scale, font);

    }

    public void SetUp() {
        durMenuBg = new Button(new Texture("duration_menu_background.png"), x, y, scale);
        durMenuBg.Centre();
        font.getData().setScale(2);
        Title = new TextBox("Can't Sleep", durMenuBg.x, (durMenuBg.y + durMenuBg.height), durMenuBg.width, 200, font);
        Content = new TextBox("It's too early to sleep, go study!", durMenuBg.x, (int) (Title.getY() -font.getLineHeight()*2) , durMenuBg.width, 200, font);
        CloseDesc = new TextBox("Press space to close...", durMenuBg.x, (int) (durMenuBg.y+font.getLineHeight()*2), durMenuBg.width, 200, font);


    }
    public void render(SpriteBatch batch) {
        if (showing) {
            durMenuBg.render(batch);
            font.getData().setScale(1.5f);
            Title.render(batch);
            font.getData().setScale(0.7f);
            Content.render(batch);
            if (flash) CloseDesc.render(batch);

            font.getData().setScale(1);
        }
    }
}
