package com.main.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.ScreenUtils;
import com.main.Main;
import com.badlogic.gdx.InputProcessor;
import com.main.gui.TextBox;
import com.main.utils.ScreenType;
import com.main.screens.LeaderboardScreen;

public class TutorialScreen implements Screen, InputProcessor {

    Main game;

    // Need to add a leaderboard button texture
    Texture heslingtonHustleLabel, playButton, controlsButton, settingsButton, exitButton, leaderButton;

    int heslingtonHustleLabelHeight, playButtonHeight, controlsButtonHeight, settingsButtonHeight, exitButtonHeight, leaderButtonHeight;
    int heslingtonHustleLabelWidth, playButtonWidth, controlsButtonWidth, settingsButtonWidth, exitButtonWidth, leaderButtonWidth;

    int x;
    float heslingtonHustleLabelX;
    float heslingtonHustleLabelY, playButtonY, controlsButtonY, settingsButtonY, exitButtonY, leaderButtonY;

    boolean exitFlag;
    public TextBox tutorial;
    BitmapFont font;

    /**
     * Constructs a MainMenuScreen with the specified game instance.
     * Initializes UI elements and calculates their positions.
     *
     * @param game the game instance this screen belongs to.
     */
    public TutorialScreen(Main game) {
        this.game = game;
        font = new BitmapFont(Gdx.files.internal("font/WhitePeaberry.fnt"));
        loadTextures();
        calculateDimensions();
        calculatePositions();
        tutorial = new TextBox("Welcome to Heslington Hustle here are some tips before you start playing",400,800,20,400,font,game.batch);
    }

    /**
     * Loads textures for UI elements from the assets directory.
     */
    private void loadTextures() {
        playButton = new Texture("menu_gui/play_button.png");
    }

    /**
     * Calculates the dimensions of buttons based on their textures.
     */
    private void calculateDimensions() {
        playButtonHeight = (int) (playButton.getHeight() * 10 * game.scaleFactorY);
        playButtonWidth = (int) (playButton.getWidth() * 10 * game.scaleFactorX);

    }

    /**
     * Calculates the positions of buttons to be drawn on the screen.
     */
    private void calculatePositions() {

        x = (int) ((game.screenWidth - playButtonWidth) / 2f);
        playButtonY = game.screenHeight - playButtonHeight * 2.25f;

    }

    @Override
    public void show() {
        game.batch.setProjectionMatrix(game.defaultCamera.combined);
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        if (exitFlag) return;
        ScreenUtils.clear(0.3f, 0.55f, 0.7f, 1);
        game.batch.setProjectionMatrix(game.defaultCamera.combined);

        game.batch.begin();
        tutorial.render();
        game.batch.draw(playButton, x, playButtonY, playButtonWidth, playButtonHeight);
        game.batch.end();
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

    public boolean touchDown(int touchX, int touchY, int pointer, int button) {
        touchY = game.screenHeight - touchY;

        if (touchX >= x && touchX <= x + playButtonWidth &&
                touchY >= playButtonY && touchY <= playButtonY + playButtonHeight) {
            game.audio.buttonClickedSoundActivate();
            game.screenManager.setScreen(ScreenType.GAME_SCREEN);
        }
        return true;
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
        playButton.dispose();
    }
}
