package com.main.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.main.Main;
import com.main.utils.EventManager;
import com.main.utils.ScreenManager;
import com.main.utils.ScreenType;

import java.util.List;

public class AchievementsScreen implements Screen, InputProcessor {
    Main game;
    BitmapFont font, titleFont;
    List<String> achievements;
    private final Texture backButton;
    private float backButtonX, backButtonY, backButtonWidth, backButtonHeight;
    private float displayTextY, titleY;

    public AchievementsScreen(Main game) {
        this.game = game;
        font = new BitmapFont(Gdx.files.internal("font/WhitePeaberry.fnt"));
        titleFont = new BitmapFont(Gdx.files.internal("font/WhitePeaberry.fnt"));
        backButton = new Texture("settings_gui/back_button.png");
        achievements = game.screenManager.getAchievements();

        calculateDimensions();
        calculatePositions();
        titleFont.getData().setScale(3.0f * game.scaleFactorX, 3.0f * game.scaleFactorY);
    }

    private void calculateDimensions() {
        font.getData().setScale(1.5f * game.scaleFactorX, 1.5f * game.scaleFactorY);
        backButtonWidth = 200 * game.scaleFactorX;
        backButtonHeight = 100 * game.scaleFactorY;
    }

    private void calculatePositions() {
        backButtonX = (game.screenWidth - backButtonWidth) / 2f;
        backButtonY = game.screenHeight / 6f - 120 * game.scaleFactorY;
        displayTextY = game.screenHeight / 2f - 200;
        titleY = game.screenHeight - 100;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
        game.batch.setProjectionMatrix(game.defaultCamera.combined);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.3f, 0.55f, 0.7f, 1);
        game.batch.begin();
        titleFont.draw(game.batch, "Achievements", 0, titleY, game.screenWidth, Align.center, false);
        float y = displayTextY;
        for (String achievement : achievements) {
            font.draw(game.batch, achievement, 0, y, game.screenWidth, Align.center, false);
            y -= font.getLineHeight() + 20;
        }
        game.batch.draw(backButton, backButtonX, backButtonY, backButtonWidth, backButtonHeight);
        game.batch.end();
    }

    @Override
    public boolean touchDown(int touchX, int touchY, int pointer, int button) {
        touchY = (game.screenHeight - touchY);

        if (touchX >= backButtonX && touchX <= backButtonX + backButtonWidth &&
                touchY >= backButtonY && touchY <= backButtonY + backButtonHeight) {
            game.screenManager.setScreen(ScreenType.END_SCREEN);
            game.audio.buttonClickedSoundActivate();
        }
        return true;
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
    public boolean touchUp(int i, int i1, int i2, int i3) {
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
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    @Override
    public void resize(int width, int height) {
        calculateDimensions();
        calculatePositions();
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
        backButton.dispose();
        font.dispose();
    }

}