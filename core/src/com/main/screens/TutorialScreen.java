package com.main.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.main.Main;
import com.main.gui.Button;
import com.main.utils.ScreenType;

/**
 * The MainControlScreen class provides a visual representation of control instructions
 * for the game, alongside a back button to navigate back to the main menu.
 * It implements both the Screen and InputProcessor interfaces from libGDX,
 * handling rendering and input events within the control screen context.
 */
public class TutorialScreen implements Screen, InputProcessor {
    Main game;
    BitmapFont font;
    String objective;
    private final float zoom = 1f;
    private Button backButton,controlLabel,controls,playButton;
    // X and Y coordinates
    private float instructionX, instructionY;
    // Buttons dimensions
    private float instructionGap;

    /**
     * Constructor for MainControlScreen.
     * Initializes the screen with game controls instructions, sets up textures for display elements,
     * and configures input processing.
     *
     * @param game The main game object that this screen is a part of.
     */
    public TutorialScreen(Main game) {
        this.game = game;
        font = new BitmapFont(Gdx.files.internal("font/WhitePeaberry.fnt"));

        this.backButton = new Button(new Texture("settings_gui/back_button.png"),(game.screenWidth)/2 -100, 100,5);
        this.backButton.Centre();
        this.playButton = new Button(new Texture("menu_gui/play_button.png"),(game.screenWidth)/2+ 100, 100,5);
        this.playButton.Centre();
        this.controlLabel = new Button(new Texture("controls_gui/controls_label.png"),(game.screenWidth)/2,game.screenHeight-80,6);
        this.controlLabel.Centre();
        this.controls = new Button(new Texture("controls_gui/controls.png"),(game.screenWidth)/2-200,game.screenHeight/2-50,5);
        this.controls.Centre();


        font.getData().setScale(1.5f * game.scaleFactorX, 1.5f * game.scaleFactorY);
        instructionGap = 87 * game.scaleFactorY;

        instructionY = game.screenHeight / 1.45f;
        instructionX = game.screenWidth / 2f - 90 * game.scaleFactorX;

        objective = "Welcome to Heslington Hustle! You are a second-year Computer Science student with exams in only 7 days. Explore the map, \n" +
                "and interact with buildings to eat, study, sleep and have fun. To get a good grade, you need to balance hours of studying with \n" +
                "self-care and recreation. Good luck!";


    }





    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
        game.batch.setProjectionMatrix(game.defaultCamera.combined);
    }

    /**
     * The main render method for the screen. Called every frame and responsible for
     * drawing the screen's contents.
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.3f, 0.55f, 0.7f, 1);
        game.batch.setProjectionMatrix(game.defaultCamera.combined);
        game.batch.begin();
        font.draw(game.batch, objective, 0, game.screenHeight-140, game.screenWidth, Align.center, false);
        float instructionY = this.instructionY;
        String[] instructions = {
                "Up - Move forward",
                "Left - Turn left",
                "Right - Turn right",
                "Down - Move backward",
                "Shift - Sprint",
                "Esc - Pause"
        };

        for (String instruction : instructions) {
            font.draw(game.batch, instruction, instructionX, instructionY);
            instructionY -= instructionGap; // Spacing between instructions
        }
        controlLabel.render(game.batch);
        controls.render(game.batch);
        backButton.render(game.batch);
        playButton.render(game.batch);

        game.batch.end();
    }

    /**
     * Handles touch down input events. Specifically, checks if the back button is pressed
     * and navigates back to the main menu screen.
     *
     * @param touchX The x-coordinate of the touch, in screen coordinates.
     * @param touchY The y-coordinate of the touch, in screen coordinates.
     * @param pointer The pointer for the event.
     * @param button The button pressed.
     * @return true if the event was handled, false otherwise.
     */
    public boolean touchDown(int touchX, int touchY, int pointer, int button) {
        touchY = (game.screenHeight - touchY);

        if (backButton.overlap(new Vector2(touchX,touchY),zoom)) {
            game.screenManager.setScreen(ScreenType.MAIN_MENU);
            game.audio.buttonClickedSoundActivate();
        }
        if (playButton.overlap(new Vector2(touchX,touchY),zoom)) {
            game.screenManager.setScreen(ScreenType.GAME_SCREEN);
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
        // Implement scrolling behavior if needed
        return false; // Return false if the event was not handled
    }

    @Override
    public void resize(int width, int height) {
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
        font.dispose();
    }

}
