package com.main.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.main.Main;
import com.main.utils.ScreenType;

public class EndScreen implements Screen, InputProcessor {
    Main game;
    Texture playAgainButton, exitButton, leaderboardButton, achievementsButton;
    BitmapFont font;
    String titleText;
    float playAgainButtonY, exitButtonY, leaderboardButtonY, achievementsButtonY;
    float buttonX, buttonWidth, buttonHeight, achievementsButtonX, achievementsButtonWidth, achievementsButtonHeight;
    float titleY;
    boolean exitFlag;

    public EndScreen(Main game) {
        this.game = game;
        titleText = "The End";
        loadAssets();
        calculateDimensions();
        calculatePositions();
    }

    private void loadAssets() {
        playAgainButton = new Texture("end_gui/play_button.png");
        exitButton = new Texture("end_gui/exit_button.png");
        leaderboardButton = new Texture("menu_gui/score_button.png");
        achievementsButton = new Texture("menu_buttons/menu_icon.png");
        font = new BitmapFont(Gdx.files.internal("font/WhitePeaberry.fnt"));
    }

    private void calculateDimensions() {
        buttonWidth = playAgainButton.getWidth() * 10 * game.scaleFactorX;
        buttonHeight = playAgainButton.getHeight() * 10 * game.scaleFactorY;
        achievementsButtonWidth = buttonWidth * 0.5f;
        achievementsButtonHeight = buttonHeight * 0.8f;
        font.getData().setScale(5.5f * game.scaleFactorX, 5.5f * game.scaleFactorY);
    }

    private void calculatePositions() {
        buttonX = (game.screenWidth - buttonWidth) / 2f;
        achievementsButtonX = 30;
        playAgainButtonY = game.screenHeight - buttonHeight * 3.5f;
        leaderboardButtonY = game.screenHeight - buttonHeight * 5.0f;
        exitButtonY = game.screenHeight - buttonHeight * 6.5f;
        achievementsButtonY = 50;
        titleY = game.screenHeight - 120f * game.scaleFactorY;
    }

    @Override
    public void render(float v) {
        if (exitFlag) return;
        ScreenUtils.clear(0.3f, 0.55f, 0.7f, 1);
        game.batch.setProjectionMatrix(game.defaultCamera.combined);
        game.batch.begin();
        font.draw(game.batch, titleText, 0, titleY, game.screenWidth, Align.center, false);
        game.batch.draw(playAgainButton, buttonX, playAgainButtonY, buttonWidth, buttonHeight);
        game.batch.draw(leaderboardButton, buttonX, leaderboardButtonY, buttonWidth, buttonHeight);
        game.batch.draw(exitButton, buttonX, exitButtonY, buttonWidth, buttonHeight);
        game.batch.draw(achievementsButton, achievementsButtonX, achievementsButtonY, achievementsButtonWidth, achievementsButtonHeight);
        game.batch.end();
    }

    @Override
    public boolean touchDown(int touchX, int touchY, int pointer, int button) {
        touchY = game.screenHeight - touchY; // Convert the touch coordinates from screen to game's coordinate system.

        if (touchX >= buttonX && touchX <= buttonX + buttonWidth) {
            if (touchY >= playAgainButtonY && touchY <= playAgainButtonY + buttonHeight) {
                game.audio.buttonClickedSoundActivate();
                game.setup();
                return true;
            } else if (touchY >= leaderboardButtonY && touchY <= leaderboardButtonY + buttonHeight) {
                game.audio.buttonClickedSoundActivate();
                game.screenManager.setScreen(ScreenType.LEADERBOARD);
                return true;
            } else if (touchY >= exitButtonY && touchY <= exitButtonY + buttonHeight) {
                game.audio.buttonClickedSoundActivate();
                game.screenManager.clearMemory();
                exitFlag = true;
                dispose();
                Gdx.app.exit();
                return true;
            }
        }

        // Check the achievements button with its specific X coordinate and width.
        if (touchX >= achievementsButtonX && touchX <= achievementsButtonX + achievementsButtonWidth &&
                touchY >= achievementsButtonY && touchY <= achievementsButtonY + achievementsButtonHeight) {
            game.audio.buttonClickedSoundActivate();
            game.screenManager.setScreen(ScreenType.ACHIEVEMENTS);
            return true;
        }

        return false;
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
    public boolean scrolled(float v, float v1) {
        return false;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
        game.batch.setProjectionMatrix(game.defaultCamera.combined);
    }

    @Override
    public void resize(int i, int i1) {
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
        playAgainButton.dispose();
        exitButton.dispose();
        font.dispose();
    }
}
