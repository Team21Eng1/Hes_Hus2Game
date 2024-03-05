package com.main.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.main.Main;

public class MainGameScreen implements Screen {

    public static final float speed = 120;
    public static final int character_width = 16;
    public static final int character_heigth = 16;

    //Animation[]

    //Texture img;
    float[] positions;
    float x;
    float y;
    int spriteNum;

    Main game;

    public MainGameScreen (Main Game) {
        game = new Main();
        y = 15;
        x = (float) Game.screenWidth /2 - (float) Game.screenHeight /2;
        //positions = new float[]
    }

    @Override
    public void show () {
        //img = new Texture("badlogic.jpg");
        game.batch = new SpriteBatch();
    }

    @Override
    public void render (float delta) {
        // MOVEMENT WITH DELTA TIME
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            y += (speed * Gdx.graphics.getDeltaTime());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            y -= (speed * Gdx.graphics.getDeltaTime());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            x -= (speed * Gdx.graphics.getDeltaTime());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            x += (speed * Gdx.graphics.getDeltaTime());
        }

        ScreenUtils.clear(0, 0, 1, 1);
        game.batch.begin();
        //game.batch.draw(img, x, y);
        game.batch.end();
    }

    @Override
    public void resize (int width, int height) {

    }

    @Override
    public void pause () {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        game.batch.dispose();
    }
}
