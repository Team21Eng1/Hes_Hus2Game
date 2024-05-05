package com.main.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.files.FileHandle;
import com.main.Main;
import com.main.utils.ScreenType;

import java.util.ArrayList;
import java.util.List;

public class LeaderboardScreen implements Screen, InputProcessor {
    Main game;
    BitmapFont font, titleFont;
    List<String> highScores;
    private final Texture backButton;
    private float backButtonX, backButtonY, backButtonWidth, backButtonHeight;
    private float titleY;

    public LeaderboardScreen(Main game) {
        this.game = game;
        font = new BitmapFont(Gdx.files.internal("font/WhitePeaberry.fnt"));
        titleFont = new BitmapFont(Gdx.files.internal("font/WhitePeaberry.fnt"));
        backButton = new Texture("settings_gui/back_button.png");
        highScores = new ArrayList<>();
        loadHighScores();

        calculateDimensions();
        calculatePositions();
        titleFont.getData().setScale(3.0f * game.scaleFactorX, 3.0f * game.scaleFactorY);  // Increased scale for title
    }

    private void loadHighScores() {
        FileHandle file = Gdx.files.local("leaderboard.csv");
        if (file.exists()) {
            String[] scoreEntries = file.readString().split("\\r?\\n");
            for (int i = 1; i < scoreEntries.length; i++) { // Start from index 1 to skip the header
                String entry = scoreEntries[i];
                if (entry != null && !entry.isEmpty()) {
                    highScores.add(entry.trim()); // Trim to remove any unwanted spaces
                }
            }
        }
    }

    private void calculateDimensions() {
        font.getData().setScale(1.5f * game.scaleFactorX, 1.5f * game.scaleFactorY);
        backButtonWidth = 200 * game.scaleFactorX;
        backButtonHeight = 100 * game.scaleFactorY;
    }

    private void calculatePositions() {
        backButtonX = (game.screenWidth - backButtonWidth) / 2f;
        backButtonY = game.screenHeight / 6f - 120 * game.scaleFactorY;
        titleY = game.screenHeight - 100;  // Position for the title
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
        titleFont.draw(game.batch, "Leaderboard", 0, titleY, game.screenWidth, Align.center, false);
        float y = titleY - 200; // Adjust starting position for scores to accommodate the title
        for (String score : highScores) {
            String formattedScore = formatScoreEntry(score);
            font.draw(game.batch, formattedScore, 0, y, game.screenWidth, Align.center, false);
            y -= font.getLineHeight(); // Move to the next line
        }
        game.batch.draw(backButton, backButtonX, backButtonY, backButtonWidth, backButtonHeight);
        game.batch.end();
    }

    private String formatScoreEntry(String scoreEntry) {
        String[] parts = scoreEntry.split(",");
        if (parts.length == 2) {
            return parts[0].trim() + ": " + parts[1].trim(); // Format as "Name: Score"
        }
        return scoreEntry; // Return original if not in expected format
    }

    /**
     * Handles touch down input events. Specifically, checks if the back button is pressed
     * and navigates back to the main menu screen.
     *
     * @param screenX The x-coordinate of the touch, in screen coordinates.
     * @param screenY The y-coordinate of the touch, in screen coordinates.
     * @param pointer The pointer for the event.
     * @param button The button pressed.
     * @return true if the event was handled, false otherwise.
     */
    public boolean touchDown(int touchX, int touchY, int pointer, int button) {
        touchY = (game.screenHeight - touchY);

        if (touchX >= backButtonX && touchX <= backButtonX + backButtonWidth &&
                touchY >= backButtonY && touchY <= backButtonY + backButtonHeight) {
            game.screenManager.setScreen(ScreenType.MAIN_MENU);
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
