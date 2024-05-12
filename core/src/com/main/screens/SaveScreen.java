package com.main.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.files.FileHandle;
import com.main.Main;
import com.main.utils.ScreenType;

public class SaveScreen implements Screen, InputProcessor {
    Main game;
    BitmapFont font, titleFont;
    private final Texture saveButton;
    private String username = "";
    private boolean acceptInput = true;
    private float saveButtonX, saveButtonY, saveButtonWidth, saveButtonHeight;
    private float displayTextY, titleY;
    private int score;

    public SaveScreen(Main game) {
        this.game = game;
        font = new BitmapFont(Gdx.files.internal("font/WhitePeaberry.fnt"));
        titleFont = new BitmapFont(Gdx.files.internal("font/WhitePeaberry.fnt"));
        saveButton = new Texture("menu_gui/save_button.png");
        calculateDimensions();
        calculatePositions();
        Gdx.input.setInputProcessor(this);
        titleFont.getData().setScale(3.0f * game.scaleFactorX, 3.0f * game.scaleFactorY);
    }

    private void calculateDimensions() {
        font.getData().setScale(1.5f * game.scaleFactorX, 1.5f * game.scaleFactorY);
        saveButtonWidth = 200 * game.scaleFactorX;
        saveButtonHeight = 100 * game.scaleFactorY;
    }

    private void calculatePositions() {
        saveButtonX = (game.screenWidth - saveButtonWidth) / 2f;
        saveButtonY = game.screenHeight / 6f - 120 * game.scaleFactorY;
        displayTextY = game.screenHeight / 2f;
        titleY = game.screenHeight - 100;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.3f, 0.55f, 0.7f, 1);
        game.batch.begin();
        titleFont.draw(game.batch, "Save Score", 0, titleY, game.screenWidth, Align.center, false);
        if (acceptInput) {
            font.draw(game.batch, "Input username: " + username, 0, displayTextY, game.screenWidth, Align.center, false);
        }
        game.batch.draw(saveButton, saveButtonX, saveButtonY, saveButtonWidth, saveButtonHeight);
        game.batch.end();
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
    public boolean keyDown(int i) {
        return false;
    }

    @Override
    public boolean keyUp(int i) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        if (acceptInput) {
            if (character == '\b' && username.length() > 0) {  // Handle backspace
                username = username.substring(0, username.length() - 1);
            } else if (character >= ' ' && character <= '~') {  // Accept only printable characters
                username += character;
            }
        }
        return true;
    }

    @Override
    public boolean touchDown(int touchX, int touchY, int pointer, int button) {
        touchY = game.screenHeight - touchY;
        if (touchX >= saveButtonX && touchX <= saveButtonX + saveButtonWidth &&
                touchY >= saveButtonY && touchY <= saveButtonY + saveButtonHeight) {
            if (!username.isEmpty()) {
                saveScore(username, this.score);
                game.audio.buttonClickedSoundActivate();
                game.screenManager.setScreen(ScreenType.END_SCREEN);
            }
            return true;
        }
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
    public boolean scrolled(float v, float v1) {
        return false;
    }

    private void saveScore(String username, int score) {
        FileHandle file = Gdx.files.local("leaderboard.csv");
        String scoreEntry = username + ", " + score + "\n";
        file.writeString(scoreEntry, true);
        game.screenManager.setScreen(ScreenType.MAIN_MENU);
    }

    @Override
    public void dispose() {
        saveButton.dispose();
        font.dispose();
        titleFont.dispose();
    }
}