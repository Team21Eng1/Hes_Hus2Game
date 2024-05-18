package com.main.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.main.Main;

import java.util.ArrayList;

public class GUI {
    private final Main game;
    private final float zoom;
    private OrthographicCamera cam;
    public ScreenOverlay overlay;

    public Button menuButton,energyBarClear,counterBackground;
    public Slider energyFill;
    public TextBox counterText;
    public Popup popupMenu;


    public GUI(Main Game, OrthographicCamera camera,float zoom){
        this.game = Game;
        this.cam = camera;
        this.overlay = new ScreenOverlay();
        this.zoom = zoom;
        setUI();
    }

    public void setUI()
    {
        counterBackground = new Button(new Texture("counter_background.png"),(int) ((game.screenWidth)/zoom), (int) ((game.screenHeight)/zoom),2);
        counterBackground.pad(6,2,7,5); counterBackground.Right(); counterBackground.Top();
        menuButton = new Button(new Texture("menu_buttons/menu_icon.png"),0, (int) ((game.screenHeight)/zoom),2);
        menuButton.pad(6,2,7,5);
        menuButton.Top();
        energyBarClear = new Button(new Texture("energy/energy_clear.png"), menuButton.x + menuButton.xPad,  (int) ((game.screenHeight)/zoom),2);
        energyBarClear.pad(0,5,5,5);
        energyBarClear.Top();
        energyFill = new Slider(energyBarClear.x, energyBarClear.y, energyBarClear.width, energyBarClear.height, energyBarClear.scale, Color.GREEN);
        energyFill.setFill(50);
        counterText = new TextBox("text",counterBackground.x + 15, counterBackground.y + counterBackground.yPad - counterBackground.padding[2]-12,counterBackground.width, counterBackground.height, game.font);

        popupMenu = new Popup((int) ((game.screenWidth/2)/zoom), (int) ((game.screenHeight/2)/zoom), 3,game.font);
    }


    public void render(SpriteBatch batch)
    {
        energyFill.render(cam.combined);
        batch.setProjectionMatrix(cam.combined);
        batch.begin();
        counterBackground.render(batch);
        menuButton.render(batch);

        energyBarClear.render(batch);
        game.font.getData().setScale(0.4f);
        counterText.render(batch);
        game.font.getData().setScale(1);
        popupMenu.render(batch);


        batch.end();
    }
    public void update(float delta)
    {

    }

}
