package com.main.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.main.Main;
import com.main.entity.Player;
import com.main.entity.Student;
import com.main.map.GameMap;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.main.utils.CollisionHandler;
import com.main.utils.ScreenType;

/**
 * The MainGameScreen class is responsible for rendering and updating all the game elements
 * including the player, game world, UI, and handling user input during the main gameplay phase.
 */
public class MainGameScreen implements Screen, InputProcessor {

    // Final attributes
    private final Color shader;
    private final float zoom = 3f;
    private final Player player;
    private final BitmapFont font, popupFont, durationFont;
    private final GameMap gameMap;
    private final OrthographicCamera camera;
    private final ShapeRenderer shapeRenderer;
    private final Main game;
    private final Texture menuButton, popupMenu, durationUpButton, durationDownButton,
            menuBackButton, menuStudyButton, menuSleepButton, menuGoButton,
            durationMenuBackground, counterBackground;
    private final float gameDayLengthInSeconds;
    private final float secondsPerGameHour;

    // Non-final attributes
    private Texture energyBar;
    private float menuButtonY, menuButtonX, menuButtonWidth, menuButtonHeight;
    private float counterBackgroundY, counterBackgroundX, counterBackgroundWidth, counterBackgroundHeight;
    private float popupMenuWidth, popupMenuHeight;
    private float durationUpButtonX, durationDownButtonX, durationMenuBackgroundX, durationButtonY, durationMenuBackgroundY;
    private float durationUpButtonWidth, durationDownButtonWidth, durationMenuBackgroundWidth, durationUpButtonHeight, durationDownButtonHeight, durationMenuBackgroundHeight;
    private float menuBackButtonWidth, menuBackButtonHeight, activityButtonWidth, activityButtonHeight;
    private float menuBackButtonX, activityButtonX, durationMenuButtonY;
    private float durationTextY, menuTitleY, hoursLabelY;
    private float energyBarY, energyBarX, energyBarWidth, energyBarHeight;
    private String activity, popupMenuType;
    private int energyCounter, duration, dayNum, recActivity, studyHours, mealCount, currentHour;
    private float timeElapsed, fadeTime, minShade;
    private boolean fadeOut, lockTime, lockMovement, lockPopup, resetPos, popupVisible, showMenu;


    private Student student;

    /**
     * Constructs the main game screen with necessary game components.
     * Initializes game map, player, camera, UI elements, and sets the initial game state.
     *
     * @param game The main game application instance.
     */
    public MainGameScreen(Main game) {
        this.game = game;
        this.shader = new Color(0.5f, 0.5f, 0.5f, 1);
        this.gameDayLengthInSeconds = 60f;
        this.secondsPerGameHour = this.gameDayLengthInSeconds / 16; // Assuming 16 hours in a day

        // Initialize final Texture objects
        this.menuButton = new Texture("menu_buttons/menu_icon.png");
        this.counterBackground = new Texture("counter_background.png");
        this.popupMenu = new Texture("popup_menu.png");
        this.durationMenuBackground = new Texture("duration_menu_background.png");
        this.durationUpButton = new Texture("settings_gui/arrow_right_button.png");
        this.durationDownButton = new Texture("settings_gui/arrow_left_button.png");
        this.menuBackButton = new Texture("settings_gui/back_button.png");
        this.menuStudyButton = new Texture("study_button.png");
        this.menuSleepButton = new Texture("sleep_button.png");
        this.menuGoButton = new Texture("go_button.png");

        // Initialize non-final attributes
        this.activity = "";
        this.popupMenuType = "";
        this.energyCounter = 10;
        this.duration = 1;
        this.dayNum = 1;
        this.timeElapsed = 0f;
        this.currentHour = 10;
        this.fadeTime = 0;
        this.minShade = 0;
        this.fadeOut = this.lockTime = this.lockMovement = this.lockPopup = this.resetPos = this.popupVisible = this.showMenu = false;

        // Setting up the game
        this.camera = new OrthographicCamera();
        this.gameMap = new GameMap(this.camera);
        this.player = new Player(this.game, this.gameMap, this.camera);

        this.student = new Student(this.game, this.gameMap);

        this.font = new BitmapFont(Gdx.files.internal("font/WhitePeaberry.fnt"));
        this.popupFont = new BitmapFont(Gdx.files.internal("font/WhitePeaberry.fnt"));
        this.durationFont = new BitmapFont(Gdx.files.internal("font/WhitePeaberry.fnt"));
        this.shapeRenderer = new ShapeRenderer();
        this.energyBar = setEnergyBar();

        this.calculateDimensions();
        this.calculatePositions();
        this.popupFont.getData().setScale(0.4f, 0.4f);
        this.player.setPos(1389, 635);
        this.camera.setToOrtho(false, this.game.screenWidth / this.zoom, this.game.screenHeight / this.zoom);
        this.camera.update();
    }


    private void calculateDimensions(){
        menuButtonWidth = 64 * game.scaleFactorX;
        menuButtonHeight = 64 * game.scaleFactorY;
        energyBarWidth = 200 * game.scaleFactorX;
        energyBarHeight = 64 * game.scaleFactorY;
        counterBackgroundWidth = 370 * game.scaleFactorX;
        counterBackgroundHeight = 150 * game.scaleFactorY;
        durationUpButtonWidth = 50 * game.scaleFactorX;
        durationUpButtonHeight = 50 * game.scaleFactorY;
        durationDownButtonWidth = 50 * game.scaleFactorX;
        durationDownButtonHeight = 50 * game.scaleFactorY;
        durationMenuBackgroundWidth = 500 * game.scaleFactorX;
        durationMenuBackgroundHeight = 500 * game.scaleFactorY;
        menuBackButtonWidth = 150 * game.scaleFactorX;
        menuBackButtonHeight = 75 * game.scaleFactorY;
        activityButtonWidth = 150 * game.scaleFactorX;
        activityButtonHeight = 75 * game.scaleFactorY;
        popupMenuWidth = 35;
        popupMenuHeight = 10;
        font.getData().setScale(game.scaleFactorX, game.scaleFactorY);
        durationFont.getData().setScale(3f * game.scaleFactorX, 3f * game.scaleFactorY);

    }

    private void calculatePositions(){
        menuButtonX = 10 * game.scaleFactorX;
        menuButtonY = game.screenHeight - menuButtonHeight - 10 * game.scaleFactorY;
        energyBarX = 30 * game.scaleFactorX + menuButtonWidth;
        energyBarY = game.screenHeight - energyBarHeight - 10 * game.scaleFactorY;
        counterBackgroundX = game.screenWidth - counterBackgroundWidth;
        counterBackgroundY = game.screenHeight - counterBackgroundHeight;
        durationMenuBackgroundX = game.screenWidth/2f - durationMenuBackgroundWidth/2f;
        durationMenuBackgroundY = game.screenHeight/2f - durationMenuBackgroundHeight/2f;
        durationDownButtonX = game.screenWidth/2f - durationDownButtonWidth/2f - 150 * game.scaleFactorX;
        durationUpButtonX = game.screenWidth/2f - durationUpButtonWidth/2f + 150 * game.scaleFactorX;
        durationButtonY = game.screenHeight/2f - durationUpButtonHeight/2f - 60 * game.scaleFactorY;
        menuBackButtonX = durationDownButtonX;
        activityButtonX = durationUpButtonX + durationUpButtonWidth - activityButtonWidth;
        durationMenuButtonY = durationButtonY - 125 * game.scaleFactorY;
        menuTitleY = 730 * game.scaleFactorY;
        durationTextY = 503 * game.scaleFactorY;
        hoursLabelY = 580 * game.scaleFactorY;
    }

    @Override
    public void render(float delta) {
        if (!lockMovement) player.update(delta);
        if (!lockTime) updateGameTime(delta); // Update the game clock

        ScreenUtils.clear(0, 0, 1, 1);
        drawWorldElements(delta);
        drawUIElements();
        drawGameTime(); // Draw current time
    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
        lockTime = false;
        player.updateGender();
    }

    /**
     * Identifies which door, if any, the player is currently touching.
     * @return The name of the door the player is touching or an empty string if none.
     */
    private String getDoorTouching(){
        CollisionHandler collisionHandler = player.getCollisionHandler();
        if (collisionHandler.isTouching("Comp_sci_door", player.getHitBox())) return "Comp_sci_door";
        if (collisionHandler.isTouching("Piazza_door", player.getHitBox())) return "Piazza_door";
        if (collisionHandler.isTouching("Gym_door", player.getHitBox())) return "Gym_door";
        if (collisionHandler.isTouching("Goodricke_door", player.getHitBox())) return "Goodricke_door";
        return "";
    }

    /**
     * Determines the menu title based on the current activity selected by the player.
     * @return The title for the menu based on the current activity.
     */
    private String getMenuTitle() {
        switch (activity) {
            case "study":
                return "Study Schedule";
            case "sleep":
                return "Sleep Early";
            case "exercise":
                return "Exercise";
            default:
                return "";
        }
    }

    /**
     * Retrieves the appropriate button texture based on the current activity.
     * @return The texture for the activity button.
     */
    private Texture getActivityButton() {
        switch (activity) {
            case "study":
                return menuStudyButton;
            case "sleep":
                return menuSleepButton;
            case "exercise":
                return menuGoButton;
            default:
                return null;
        }
    }

    /**
     * Checks if the cursor is hovering over a menu option and changes its color accordingly.
     * @param posX The X position of the menu option.
     * @param posY The Y position of the menu option.
     */
    private void isHovering(float posX, float posY){
        int mouseX = Gdx.input.getX();
        int mouseY = game.screenHeight - Gdx.input.getY();
        Vector3 menuOpt = camera.project(new Vector3(posX, posY, 0));
        if (mouseX >= menuOpt.x && mouseX <= menuOpt.x + popupMenuWidth * zoom && mouseY >= menuOpt.y && mouseY <= menuOpt.y + popupMenuHeight * zoom) {
            game.batch.setColor(shader);
        }
        else {
            game.batch.setColor(Color.WHITE);
        }
    }

    /**
     * Draws a menu option at the specified position with a specified text and shade option.
     * @param posX The X position of the menu option.
     * @param posY The Y position of the menu option.
     * @param text The text to display on the menu option.
     * @param shadeOption Determines the shade of the menu option.
     */
    private void drawMenuOption(float posX, float posY, String text, int shadeOption){
        if (shadeOption == 0) isHovering(posX, posY);
        else if (shadeOption == 1) game.batch.setColor(Color.WHITE);
        else if (shadeOption == 2) game.batch.setColor(shader);
        GlyphLayout layout = new GlyphLayout();
        layout.setText(popupFont, text);
        game.batch.draw(popupMenu, posX, posY, popupMenuWidth, popupMenuHeight);
        popupFont.draw(game.batch, text, posX + (popupMenuWidth - layout.width)/2, posY + (popupMenuHeight + layout.height)/2f - popupFont.getDescent() - layout.height/4f);
        game.batch.setColor(Color.WHITE);
    }

    /**
     * Renders the menu for setting the duration of an activity.
     *
     * This functionality of the popup menu (along with all the other methods that relate to this functionality)
     * should be segregated into its own class to reduce overheads and processing delay.
     */
    private void drawDurationMenu(){
        game.batch.begin();
        Texture activityButton;
        String title;
        activityButton = getActivityButton();
        title = getMenuTitle();

        game.batch.draw(durationMenuBackground, durationMenuBackgroundX, durationMenuBackgroundY, durationMenuBackgroundWidth, durationMenuBackgroundHeight);
        game.batch.draw(menuBackButton, menuBackButtonX, durationMenuButtonY, menuBackButtonWidth, menuBackButtonHeight);
        game.batch.draw(activityButton, activityButtonX, durationMenuButtonY, activityButtonWidth, activityButtonHeight);
        durationFont.draw(game.batch, title, 0, menuTitleY, game.screenWidth, Align.center, false);

        if (!activity.equals("sleep")) {
            game.batch.draw(durationDownButton, durationDownButtonX, durationButtonY, durationDownButtonWidth, durationDownButtonHeight);
            game.batch.draw(durationUpButton, durationUpButtonX, durationButtonY, durationUpButtonWidth, durationUpButtonHeight);
            durationFont.draw(game.batch, Integer.toString(duration), 0, durationTextY, game.screenWidth, Align.center, false);
            durationFont.draw(game.batch, "Hours", 0, hoursLabelY, game.screenWidth, Align.center, false);
        }

        game.batch.end();
    }

    /**
     * Draws the popup menu for interaction with various doors.
     */
    private void drawPopUpMenu(){
        popupMenuType = getDoorTouching();
        switch (popupMenuType) {
            case "Comp_sci_door":
                drawMenuOption(player.worldX + 30, player.worldY + 20, "Study", 0);
                popupVisible = true;
                break;
            case "Piazza_door":
                drawMenuOption(player.worldX + 30, player.worldY + 20, "Study", 0);
                drawMenuOption(player.worldX + 30, player.worldY + 35, "Eat", 0);
                popupVisible = true;
                break;
            case "Gym_door":
                drawMenuOption(player.worldX + 30, player.worldY + 20, "Exercise", 0);
                popupVisible = true;
                break;
            case "Goodricke_door":
                int shadeOption;
                if (currentHour >= 20) {
                    popupVisible = true;
                    shadeOption = 0;
                } else {
                    popupVisible = false;
                    shadeOption = 2;
                }
                drawMenuOption(player.worldX + 30, player.worldY + 20, "Sleep", shadeOption);
                break;
            default:
                popupVisible = false;
                break;
        }
    }

    /**
     * Draws a shade overlay on the screen with a specified alpha transparency.
     * @param alpha The transparency level of the overlay.
     */
    private void drawShadeOverlay(float alpha){
        Gdx.gl.glEnable(GL20.GL_BLEND);
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0, 0, 0, alpha); // Adjust alpha for darkness
        shapeRenderer.rect(0, 0, gameMap.getWidth(), gameMap.getHeight());
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    /**
     * Initiates the fade out process and optionally resets the player's position.
     * @param resetPos A boolean indicating whether to reset the player's position.
     */
    private void executeFadeOut(boolean resetPos){
        if (fadeOut) return;
        fadeOut = true;
        lockMovement = true;
        lockTime = true;
        lockPopup = true;
        showMenu = false;
        this.resetPos = resetPos;
        minShade = timeElapsed/secondsPerGameHour > 11 ? (timeElapsed - 11 * secondsPerGameHour)/(gameDayLengthInSeconds - 11 * secondsPerGameHour) : 0;
    }

    /**
     * Manages the stepwise execution of the fade-out effect.
     * @param delta The time elapsed since the last frame.
     */
    private void fadeOutStep(float delta){
        if (fadeOut){
            if (fadeTime == 0) fadeTime = minShade;
            if (fadeTime <= 1) {
                fadeTime += delta;
                drawShadeOverlay(fadeTime);
            }
            else{
                if (resetPos) {
                    player.setPos( 1389, 635);
                    player.setDirection('D');
                }
                fadeTime = 0;
                fadeOut = false;
                lockTime = false;
                lockMovement = false;
                lockPopup = false;
                resetPos = false;
            }
        }
    }

    /**
     * Renders the game world elements including the map and player.
     * @param delta The time elapsed since the last frame.
     */
    private void drawWorldElements(float delta){
        gameMap.update(delta);
        gameMap.render();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        updateEntities(delta);
        drawEntities();

        if (!lockPopup) drawPopUpMenu();
        game.batch.end();
        if (!fadeOut && timeElapsed/secondsPerGameHour > 11) drawShadeOverlay((timeElapsed - 11 * secondsPerGameHour)/(gameDayLengthInSeconds - 11 * secondsPerGameHour));
        fadeOutStep(delta);
    }

    private void drawEntities()
    {
        game.batch.draw(player.getCurrentFrame(), player.worldX, player.worldY, Player.spriteX, Player.spriteY);
        game.batch.draw(student.getCurrentFrame(),student.worldX,student.worldY,Student.spriteX,Student.spriteY);
    }
    private void updateEntities(float delta)
    {
        student.update(delta);
    }


    /**
     * Renders the UI elements of the game.
     */
    private void drawUIElements(){
        String counterString = String.format("Recreation Activities done: " + recActivity + "\nStudy hours: " + studyHours + "\nMeals Eaten: " + mealCount, dayNum, timeElapsed );
        game.batch.setProjectionMatrix(game.defaultCamera.combined);
        if (showMenu) drawDurationMenu();
        game.batch.begin();
        game.batch.draw(menuButton, menuButtonX, menuButtonY, menuButtonWidth, menuButtonHeight);
        game.batch.draw(energyBar, energyBarX, energyBarY, energyBarWidth, energyBarHeight);
        game.batch.draw(counterBackground, counterBackgroundX, counterBackgroundY, counterBackgroundWidth, counterBackgroundHeight);
        font.draw(game.batch, counterString, game.screenWidth - 320 * game.scaleFactorX, game.screenHeight - 40 * game.scaleFactorY);
        game.batch.end();
    }

    /**
     * Updates the game time and handles the transition from day to night.
     * @param delta The time elapsed since the last frame.
     */
    private void updateGameTime(float delta) {
        timeElapsed += delta;

        // Calculate the current hour in game time
        int hoursPassed = (int)(timeElapsed / secondsPerGameHour);
        currentHour = 8 + hoursPassed; // Starts at 08:00 AM

        // Ensure the hour cycles through the active hours correctly (8 AM to 12 AM)
        if (currentHour >= 24) { // If it reaches 12 AM, reset to 8 AM the next day
            if (dayNum == 7) game.screenManager.setScreen(ScreenType.END_SCREEN);
            resetDay();
        }
    }

    /**
     * Resets the game day, including resetting time and increasing the day counter.
     */
    private void resetDay(){
        executeFadeOut(true);
        currentHour = 8;
        dayNum++;
        timeElapsed = 0;
        energyCounter += 4;
        if (energyCounter > 10) energyCounter = 10;
        energyBar.dispose();
        energyBar = setEnergyBar();
    }

    /**
     * Draws the game time display.
     */
    private void drawGameTime() {
        // Adjust the format if you want to display minutes or seconds
        String timeString = String.format("Day: %d       Time: %02d:00", dayNum, currentHour%24);
        game.batch.begin();
        font.draw(game.batch, timeString, game.screenWidth - 320 * game.scaleFactorX, game.screenHeight - 15 * game.scaleFactorY);
        game.batch.end();
    }

    /**
     * Sets the texture for the energy bar based on the current energy level.
     * @return The texture of the current energy bar.
     */
    public Texture setEnergyBar() {
        if (energyCounter > 0) {
            return new Texture("energy/energy_" + energyCounter + ".png");
        } else {
            return new Texture("energy/energy_0.png");
        }
    }

    /**
     * Handles touch input from the user, managing interactions with UI elements and game objects.
     *
     * @param touchX The x-coordinate of the touch.
     * @param touchY The y-coordinate of the touch.
     * @param pointer The pointer for the event.
     * @param button The button that was pressed.
     * @return true if the input was processed, false otherwise.
     */
    @Override
    public boolean touchDown(int touchX, int touchY, int pointer, int button){
        touchY = game.screenHeight - touchY;

        if (touchX >= menuButtonX && touchX <= menuButtonX + menuButtonWidth && touchY >= menuButtonY && touchY <= menuButtonY + menuButtonHeight) {
            game.audio.buttonClickedSoundActivate();
            game.screenManager.setScreen(ScreenType.MAIN_MENU);
        }
        else if (showMenu){
            switch (activity){
                case "study":
                    if (touchX >= durationUpButtonX && touchX <= durationUpButtonX + durationUpButtonWidth && touchY >= durationButtonY && touchY <= durationButtonY + durationUpButtonHeight) {
                        game.audio.buttonClickedSoundActivate();
                        if (duration < 4) duration++;
                    } else if (touchX >= durationDownButtonX && touchX <= durationDownButtonX + durationDownButtonWidth && touchY >= durationButtonY && touchY <= durationButtonY + durationDownButtonHeight) {
                        game.audio.buttonClickedSoundActivate();
                        if (duration > 1) duration--;
                    } else if (touchX >= menuBackButtonX && touchX <= menuBackButtonX + menuBackButtonWidth && touchY >= durationMenuButtonY && touchY <= durationMenuButtonY + menuBackButtonHeight) {
                        game.audio.buttonClickedSoundActivate();
                        showMenu = false;
                        lockMovement = fadeOut;
                        duration = 1;
                    } else if (touchX >= activityButtonX && touchX <= activityButtonX + activityButtonWidth && touchY >= durationMenuButtonY && touchY <= durationMenuButtonY + activityButtonHeight) {
                        game.audio.buttonClickedSoundActivate();
                        showMenu = false;
                        lockMovement = fadeOut;
                        studyHours += duration;
                        if (energyCounter > (duration+1)/2) energyCounter -= (duration+1)/2;
                        energyBar.dispose();
                        energyBar = setEnergyBar();
                        timeElapsed += duration * secondsPerGameHour;
                        game.screenManager.setScreen(ScreenType.MINI_GAME, duration);
                    }
                    break;

                case "exercise":
                    if (touchX >= durationUpButtonX && touchX <= durationUpButtonX + durationUpButtonWidth && touchY >= durationButtonY && touchY <= durationButtonY + durationUpButtonHeight) {
                        game.audio.buttonClickedSoundActivate();
                        if (duration < 4) duration++;
                    } else if (touchX >= durationDownButtonX && touchX <= durationDownButtonX + durationDownButtonWidth && touchY >= durationButtonY && touchY <= durationButtonY + durationDownButtonHeight) {
                        game.audio.buttonClickedSoundActivate();
                        if (duration > 1) duration--;
                    } else if (touchX >= menuBackButtonX && touchX <= menuBackButtonX + menuBackButtonWidth && touchY >= durationMenuButtonY && touchY <= durationMenuButtonY + menuBackButtonHeight) {
                        game.audio.buttonClickedSoundActivate();
                        showMenu = false;
                        lockMovement = fadeOut;
                        duration = 1;
                    } else if (touchX >= activityButtonX && touchX <= activityButtonX + activityButtonWidth && touchY >= durationMenuButtonY && touchY <= durationMenuButtonY + activityButtonHeight) {
                        game.audio.buttonClickedSoundActivate();
                        if (energyCounter >= duration) {
                            executeFadeOut(false);
                            showMenu = false;
                            lockMovement = fadeOut;
                            recActivity++;
                            energyCounter -= duration;
                            energyBar.dispose();
                            energyBar = setEnergyBar();
                            timeElapsed += duration * secondsPerGameHour;
                            duration = 1;
                        }
                    }
                    break;

                case "sleep":
                    if (touchX >= durationUpButtonX && touchX <= durationUpButtonX + durationUpButtonWidth && touchY >= durationButtonY && touchY <= durationButtonY + durationUpButtonHeight) {
                        game.audio.buttonClickedSoundActivate();
                        if (duration < 10) duration++;
                    } else if (touchX >= durationDownButtonX && touchX <= durationDownButtonX + durationDownButtonWidth && touchY >= durationButtonY && touchY <= durationButtonY + durationDownButtonHeight) {
                        game.audio.buttonClickedSoundActivate();
                        if (duration > 1) duration--;
                    } else if (touchX >= menuBackButtonX && touchX <= menuBackButtonX + menuBackButtonWidth && touchY >= durationMenuButtonY && touchY <= durationMenuButtonY + menuBackButtonHeight) {
                        game.audio.buttonClickedSoundActivate();
                        showMenu = false;
                        lockMovement = fadeOut;
                        duration = 1;
                    } else if (touchX >= activityButtonX && touchX <= activityButtonX + activityButtonWidth && touchY >= durationMenuButtonY && touchY <= durationMenuButtonY + activityButtonHeight) {
                        game.audio.buttonClickedSoundActivate();
                        showMenu = false;
                        lockMovement = fadeOut;
                        resetDay();
                        duration = 1;
                    }
                    break;
            }
        }
        else if (popupVisible){
            Vector3 studyOpt = camera.project(new Vector3(player.worldX + 30, player.worldY + 20, 0));
            Vector3 eatOpt = camera.project(new Vector3(player.worldX + 30, player.worldY + 35, 0));
            switch (popupMenuType) {
                case "Comp_sci_door":
                    if (touchX >= studyOpt.x && touchX <= studyOpt.x + popupMenuWidth * zoom && touchY >= studyOpt.y && touchY <= studyOpt.y + popupMenuHeight * zoom) {
                        game.audio.buttonClickedSoundActivate();
                        showMenu = true;
                        lockMovement = true;
                        activity = "study";
                        duration = 1;
                    }
                    break;

                case "Piazza_door":
                    if (touchX >= studyOpt.x && touchX <= studyOpt.x + popupMenuWidth * zoom && touchY >= studyOpt.y && touchY <= studyOpt.y + popupMenuHeight * zoom) {
                        game.audio.buttonClickedSoundActivate();
                        showMenu = true;
                        lockMovement = true;
                        activity = "study";
                        duration = 1;
                    }
                    else if (touchX >= eatOpt.x && touchX <= eatOpt.x + popupMenuWidth * zoom && touchY >= eatOpt.y && touchY <= eatOpt.y + popupMenuHeight * zoom) {
                        game.audio.buttonClickedSoundActivate();
                        game.audio.eatingSoundActivate();
                        energyCounter += 3;
                        mealCount++;
                        if (energyCounter > 10) energyCounter = 10;
                        energyBar.dispose();
                        energyBar = setEnergyBar();
                    }
                    break;

                case "Gym_door":
                    if (touchX >= studyOpt.x && touchX <= studyOpt.x + popupMenuWidth * zoom && touchY >= studyOpt.y && touchY <= studyOpt.y + popupMenuHeight * zoom) {
                        game.audio.buttonClickedSoundActivate();
                        showMenu = true;
                        lockMovement = true;
                        activity = "exercise";
                        duration = 1;
                    }
                    break;

                case "Goodricke_door":
                    if (touchX >= studyOpt.x && touchX <= studyOpt.x + popupMenuWidth * zoom && touchY >= studyOpt.y && touchY <= studyOpt.y + popupMenuHeight * zoom) {
                        game.audio.buttonClickedSoundActivate();
                        showMenu = true;
                        lockMovement = true;
                        activity = "sleep";
                        duration = 1;
                    }
                    break;
            }
        }

        return true;
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
        shapeRenderer.dispose();
        menuButton.dispose();
        counterBackground.dispose();
        popupMenu.dispose();
        durationMenuBackground.dispose();
        durationUpButton.dispose();
        durationDownButton.dispose();
        menuBackButton.dispose();
        menuStudyButton.dispose();
        menuSleepButton.dispose();
        menuGoButton.dispose();
        energyBar.dispose();
        player.dispose();
        font.dispose();
        popupFont.dispose();
        durationFont.dispose();
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
}
