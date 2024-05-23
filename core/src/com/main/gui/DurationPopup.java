package com.main.gui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class DurationPopup {

    private int x, y;
    private final float scale;
    public Button durMenuBg, durUpB, durDownB, BackB,GoB;
    public TextBox ActTextBox, DurTextBox;
    private BitmapFont font;
    public Boolean showing;
    private int duration;

    public DurationPopup(int x, int y, float scale, BitmapFont font)
    {
        duration = 1;
        this.x = x;
        this.y = y;
        this.scale = scale;
        this.font = font;
        this.showing = false;

        SetUp();
    }

    public void SetUp()
    {
        durMenuBg = new Button(new Texture("duration_menu_background.png"),x,y,scale);
        durMenuBg.Centre();
        GoB = new Button(new Texture("go_button.png"),(int) (durMenuBg.x + durMenuBg.width/2),durMenuBg.y,scale);
        GoB.pad(0,0,0,30);
        GoB.Centre();
        BackB = new Button(new Texture("settings_gui/back_button.png"),x,y-115,scale);
        BackB.Centre();
        durUpB = new Button(new Texture("settings_gui/arrow_right_button.png"), (int) (durMenuBg.x + durMenuBg.width),GoB.y,scale);
        durUpB.pad(10,10,10,10);
        durUpB.Right();
        durDownB = new Button(new Texture("settings_gui/arrow_left_button.png"),durMenuBg.x,GoB.y,scale);
        durDownB.pad(10,10,10,10);
        font.getData().setScale(2);
        ActTextBox = new TextBox("Activity \nDuration?",durMenuBg.x, (int) (durMenuBg.y+ durMenuBg.height) + 10,durMenuBg.width,200,font);

        DurTextBox = new TextBox(String.valueOf(duration),durMenuBg.x , durMenuBg.y + 150,durMenuBg.width,200,font);





    }
    public void setPopText(String activity)
    {
        ActTextBox.text = activity+" \nDuration?";
    }
    public void changeDur(int i)
    {
        if (duration+i!=0 && duration+i!=5)
        {
            duration+=i;
            DurTextBox.text=String.valueOf(duration);
        }
    }
    public int getDuration()
    {
        return duration;
    }



    public void render(SpriteBatch batch)
    {
        if (showing){
            durMenuBg.render(batch);
            BackB.render(batch);
            durUpB.render(batch);
            durDownB.render(batch);
            font.getData().setScale(1.5f);
            ActTextBox.render(batch);
            font.getData().setScale(3);
            DurTextBox.render(batch);
            font.getData().setScale(1);
            GoB.render(batch);
        }



    }


}
