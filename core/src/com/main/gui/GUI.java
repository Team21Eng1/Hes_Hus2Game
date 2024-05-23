package com.main.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.main.Main;

public class GUI {
    private final Main game;
    private final float zoom;
    private OrthographicCamera cam;
    public ScreenOverlay overlay;

    public Button menuButton,energyBarClear,counterBackground;
    public Slider energyFill;
    public TextBox timeText;
    public DurationPopup durationPopupMenu;
    public Popup popup;
    public SleepPopup sleep;
    private Button counterBig;
    public TextBox sideText;
    public EnergyPopup energyP;


    public GUI(Main Game, OrthographicCamera camera,float zoom){
        this.game = Game;
        this.cam = camera;
        this.overlay = new ScreenOverlay();
        this.zoom = zoom;
        setUI();
    }

    public void setUI()
    {
        counterBackground = new Button(new Texture("counter_background.png"),(int) ((game.screenWidth)/zoom), (int) ((game.screenHeight)/zoom),1.2f);
        counterBackground.pad(6,5,7,5); counterBackground.Right(); counterBackground.Top();
//        counterBig = new Button(new Texture("counter_big.png"), (int) ((game.screenWidth)/zoom),30,1);
//        counterBig.pad(6,7,7,5); counterBig.Right();
        menuButton = new Button(new Texture("menu_buttons/menu_icon.png"),0, (int) ((game.screenHeight)/zoom),2);
        menuButton.pad(6,2,7,5);
        menuButton.Top();
        energyBarClear = new Button(new Texture("energy/energy_clear.png"), menuButton.x + menuButton.xPad,  (int) ((game.screenHeight)/zoom),2);
        energyBarClear.pad(0,5,5,5);
        energyBarClear.Top();
        energyFill = new Slider(energyBarClear.x, energyBarClear.y, energyBarClear.width, energyBarClear.height, energyBarClear.scale, Color.GREEN);
        energyFill.setFill(50);
        timeText = new TextBox("text",counterBackground.x, counterBackground.y+counterBackground.height+3,counterBackground.width, counterBackground.height, game.font);
        sideText = new TextBox("text",counterBackground.x-70, counterBackground.y+counterBackground.height+3,counterBackground.width, counterBackground.height, game.font);
        sideText.align = Align.topLeft;


        sleep = new SleepPopup((int) ((game.screenWidth/2)/zoom), (int) ((game.screenHeight/2)/zoom), 3,game.font);
        energyP = new EnergyPopup((int) ((game.screenWidth/2)/zoom), (int) ((game.screenHeight/2)/zoom), 3,game.font);
        popup = energyP;
        popup.showing=false;

        durationPopupMenu = new DurationPopup((int) ((game.screenWidth/2)/zoom), (int) ((game.screenHeight/2)/zoom), 3,game.font);
    }


    public void render(SpriteBatch batch)
    {
        energyFill.render(cam.combined);
        batch.setProjectionMatrix(cam.combined);
        batch.begin();
        counterBackground.render(batch);

        menuButton.render(batch);

        energyBarClear.render(batch);
        game.font.getData().setScale(1f);
        timeText.render(batch);
        sideText.render(batch);
        game.font.getData().setScale(1);
        durationPopupMenu.render(batch);
        popup.render(batch);


        batch.end();
    }
    public void update(float delta)
    {
        popup.update(delta);
        timeText.text = game.eventM.hours + ":" +game.eventM.mins;
        sideText.text = "Day: " + game.eventM.curDay;
    }

}
