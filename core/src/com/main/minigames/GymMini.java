package com.main.minigames;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.main.Main;
import com.main.entity.Entity;
import com.main.gui.Slider;
import com.main.gui.TextBox;
import com.main.utils.ActivityType;
import com.main.utils.ScreenType;

public class GymMini implements Screen, InputProcessor {
    Main game;
    int duration;
    public Entity arm;
    private Animation bicep;
    float scale;
    float waveFlt;
    float t = 0;

    public int clicker,maxClick;
    private float score;
    public TextBox textBox;
    private BitmapFont font;
    private Slider timer,timerBG;

    private float clock,TimeLimit;


    public GymMini(Main game, int Duration){
        this.game = game;
        this.duration = Duration;
        this.arm = new Entity((int) (game.screenWidth/2f), (int) (game.screenHeight /2.5f) - 100);
        this.bicep = new Animation(1f,arm.getFrames(new Texture("gymmini/ArmSheet.png"),0,3,0,64,128,false));
        this.arm.currentAnimation = this.bicep;
        this.scale = 4f;

        this.font = new BitmapFont(Gdx.files.internal("font/WhitePeaberry.fnt"));

        this.waveFlt = 0;
        this.clicker = 0;
        this.maxClick = 50;
        this.textBox = new TextBox("Pump it",0,0,200,200,font);

        this.timer = new Slider(20,20,300,20, 1,Color.RED);
        this.timerBG = new Slider(15,15,310,30, 1,Color.BLACK);

        this.TimeLimit = 5f;
        this.clock = 5f;
        this.score = 0;

    }

    @Override
    public void render(float delta) {

        clock -= delta;
        timer.setFill(clock/TimeLimit);
        if (clock < 0){
            score = score-100;
            game.eventM.logEvent(ActivityType.EXCERCISE,(int) score);
            game.screenManager.setScreen(ScreenType.GAME_SCREEN);
            //end
            }
        waveFlt += delta*3;
        if (waveFlt > 2*Math.PI) {waveFlt = 0;};

        t+=delta;
        if (t>0.3f - 0.29f*clicker/maxClick)
        {
            clicker--;
            t=0;
        }
        this.score += 20*delta*getScoreConst((float) clicker /maxClick);


        ScreenUtils.clear(0.3f, 0.55f, 0.7f, 1);
        game.batch.setProjectionMatrix(game.defaultCamera.combined);
        game.batch.begin();
        textBox.scaleFont(2);
        textBox.render(game.batch);
        textBox.scaleFont(1);
        font.getData().setScale(8);
        font.draw(game.batch,"Pump it!", game.screenWidth/2-1000, (float) (game.screenHeight-15*(Math.sin(waveFlt))+80),2000,Align.center,true);
        font.getData().setScale(4);
        font.draw(game.batch,"Click as fast as you can!", game.screenWidth/2-1000, (float) (game.screenHeight-5*(Math.cos(waveFlt))-120),2000,Align.center,true);
        font.getData().setScale(1);
        game.batch.draw(arm.getCurrentFrame(),arm.worldX,arm.worldY,0,0,64,128,scale,scale,-15);
        game.batch.draw(new TextureRegion(new Texture("gymmini/ForeArm.png")),arm.worldX + 72,arm.worldY+16,35,16,64,128,scale*1.2f,scale*1.2f,forearmRot(clicker));
        game.batch.end();
        timerBG.render(game.defaultCamera.combined);
        timer.render(game.defaultCamera.combined);

    }
    public float getScoreConst(float perc)
    {

        if (perc < 0.50) { return 1;}
        else if (perc > .49 && perc < .80) {return 2;}
        else { return 3;}
    }

    public float forearmRot(float clicker)
    {
        if (clicker > maxClick-1){clicker = maxClick;} else if (clicker < 1){clicker = 1;}
        this.arm.stateTime = 3.5f*clicker/(maxClick+2);
        // 110 to 30
        return 110 - 80*(clicker/maxClick);
    }


    @Override
    public boolean keyDown(int i) {

        return false;
    }

    @Override
    public boolean keyUp(int i) {
        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int i, int i1, int i2, int i3) {
        clicker++;clicker++;
        return false;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        clicker++;clicker++;
        return false;
    }

    @Override
    public boolean touchCancelled(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }

    @Override
    public boolean scrolled(float v, float v1) {
        return false;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
    }


    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
