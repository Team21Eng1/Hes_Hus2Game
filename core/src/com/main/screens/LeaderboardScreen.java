package com.main.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.files.FileHandle;
import com.main.Main;
import com.main.gui.Button;
import com.main.gui.TextBox;
import com.main.utils.AchievementType;
import com.main.utils.ScreenType;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.badlogic.gdx.Input.Keys.ENTER;

public class LeaderboardScreen implements Screen, InputProcessor {
    Main game;
    BitmapFont font, titleFont;
    List<String[]> highScores;  // List to hold score entries as arrays
    private String UserName;
    private boolean inputName = false;
    private float backButtonX, backButtonY, backButtonWidth, backButtonHeight;

    private float titleY;
    private OrthographicCamera cam;
    private Button backButton,backButton2,Achieve;
    float timer,t;
    char inputCap;
    private ArrayList<AchievementType> achmnts;

    private TextBox AchievmentText;

    public LeaderboardScreen(Main game) {
        this.game = game;
        font = new BitmapFont(Gdx.files.internal("font/WhitePeaberry.fnt"));
        titleFont = new BitmapFont(Gdx.files.internal("font/WhitePeaberry.fnt"));
        highScores = new ArrayList<>();
        loadHighScores();
        UserName = "NAME";
        this.cam = new OrthographicCamera();
        this.cam = new OrthographicCamera();
        this.cam.setToOrtho(false, this.game.screenWidth, this.game.screenHeight);
        this.cam.rotate(new Vector3(0,0,1),8.5f);
        achmnts = new ArrayList<>();
        if (game.eventM.curDay == 8) {
            achmnts = game.eventM.checkForAchievements();
            inputName = true;
            backButton = new Button(new Texture("settings_gui/back_button.png"),game.screenWidth/2 - 200,game.screenHeight/8,5);
            backButton.Centre();
            backButton2 = new Button(new Texture("settings_gui/back_button.png"),game.screenWidth/2 + 200,game.screenHeight/8,5);
            backButton2.Centre();
            timer = 0.6f;

        } else {
            backButton = new Button(new Texture("settings_gui/back_button.png"),game.screenWidth/2,game.screenHeight/8,5);
            backButton.Centre();
        }
        Achieve = new Button(new Texture("counter_big.png"),0,0,4);



        if (achmnts.size() == 0) {AchievmentText = new TextBox("ACHIEVEMENTS:\nYour achievments will appear here at the end of the game!",Achieve.x+4,Achieve.y+Achieve.height,Achieve.width,20,font);}
        else {AchievmentText = new TextBox("ACHIEVEMENTS:",Achieve.x+4,Achieve.y+Achieve.height,Achieve.width,20,font);

        }


        calculateDimensions();
        calculatePositions();
        titleFont.getData().setScale(3.0f * game.scaleFactorX, 3.0f * game.scaleFactorY);
    }

    private void loadHighScores() {
        FileHandle file = Gdx.files.local("leaderboard.csv");
        if (file.exists()) {
            String[] scoreEntries = file.readString().split("\\r?\\n");
            for (int i = 1; i < scoreEntries.length; i++) {
                String[] parts = scoreEntries[i].split(",");
                if (parts.length == 2) {
                    highScores.add(new String[] { parts[0].trim(), parts[1].trim() });
                }
            }
            Collections.sort(highScores, new Comparator<String[]>() {
                @Override
                public int compare(String[] o1, String[] o2) {
                    return Integer.compare(Integer.parseInt(o2[1]), Integer.parseInt(o1[1]));
                }
            });
            highScores = highScores.size() > 10 ? highScores.subList(0, 10) : highScores; // Limit to top 10 scores
        }
    }

    private void calculateDimensions() {
        font.getData().setScale(1.5f * game.scaleFactorX, 1.5f * game.scaleFactorY);
    }

    private void calculatePositions() {
        titleY = game.screenHeight - 100;  // Position for the title


    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
        game.batch.setProjectionMatrix(game.defaultCamera.combined);

    }

    @Override
    public void render(float delta) {
        game.batch.setProjectionMatrix(cam.combined);
        ScreenUtils.clear(0.3f, 0.55f, 0.7f, 1);
        game.batch.begin();
        font.getData().setScale(2);
        titleFont.draw(game.batch, "Leaderboard", 0, titleY, game.screenWidth, Align.center, false);
        float y = titleY - 100; // Adjust starting position for scores to accommodate the title
        for (String[] scoreEntry : highScores) {
            String displayText = String.valueOf(highScores.indexOf(scoreEntry)+1) + String.format(". %06d",Integer.valueOf(scoreEntry[1])) +"     "+ scoreEntry[0];
            font.draw(game.batch, displayText, game.screenWidth/3, y, game.screenWidth, Align.topLeft, false);
            y -= font.getLineHeight(); // Move to the next line

        }



        if (game.eventM.curDay==8) {
            renderUserIn(y,12345);
            backButton2.render(game.batch);
            t+=delta;
            if (t>timer) {t=0; if (inputCap == '<') {  inputCap = ' ';} else {inputCap = '<';}}
        }
        backButton.render(game.batch);
        Achieve.render(game.batch);
        AchievmentText.render(game.batch);

        int count = 1;
        int yAch = (int) (AchievmentText.getY()-font.getLineHeight());
        for (AchievementType at : achmnts)
        {

            drawAchievementText(at,yAch);
            yAch -= font.getLineHeight()*3;

        }
        game.batch.end();
        font.getData().setScale(1);


    }


    public void drawAchievementText(AchievementType at,int y)
    {
        switch (at) {
            case NERD:
                font.draw(game.batch ,"NERD",Achieve.x,y,Achieve.width,Align.center,true);
                font.draw(game.batch ,"You study... too much",Achieve.x,y-font.getLineHeight(),Achieve.width,Align.center,true);
                break;
            case BRAWN:
                font.draw(game.batch ,"BRAINS AND BRAWNS",Achieve.x,y,Achieve.width,Align.center,true);
                font.draw(game.batch ,"You buff scientist you.",Achieve.x,y-font.getLineHeight(),Achieve.width,Align.center,true);
                break;
            case GLUTTON:
                font.draw(game.batch ,"GLUTTON GOURMET",Achieve.x,y,Achieve.width,Align.center,true);
                font.draw(game.batch ,"You have an exam soon you know?",Achieve.x,y-font.getLineHeight(),Achieve.width,Align.center,true);
                break;
            case ROUGH_SLEEPER:
                font.draw(game.batch ,"ROUGH SLEEPER",Achieve.x,y,Achieve.width,Align.center,true);
                font.draw(game.batch ,"The floor is pretty comfy to be fair.",Achieve.x,y-font.getLineHeight(),Achieve.width,Align.center,true);
                break;
            case TEACHPET:
                font.draw(game.batch ,"TEACHERS PET",Achieve.x,y,Achieve.width,Align.center,true);
                font.draw(game.batch ,"Friends with the lecturers are we?",Achieve.x,y-font.getLineHeight(),Achieve.width,Align.center,true);
                break;


        }
    }
    public void renderUserIn(float lnHeight,int score)
    {
        if (inputName){font.draw(game.batch,"     "+ String.format("%06d",Integer.valueOf(game.eventM.getScore())) +"     " + UserName + inputCap ,game.screenWidth/3, lnHeight-20, game.screenWidth, Align.topLeft, false);}

    }

    private InputStream formatScoreEntry(int score, String user) {
        InputStream stream = new ByteArrayInputStream(("\n" + user + ", " + score).getBytes(StandardCharsets.UTF_8));
        return stream; // Format as "Name: Score"
    }

    private void writeUpdatedLB()
    {
        FileHandle file = Gdx.files.local("leaderboard.csv");
        if (file.exists()) {
            file.write(formatScoreEntry(1,UserName),true);
            highScores.clear();
            String[] scoreEntries = file.readString().split("\\r?\\n");
            for (int i = 1; i < scoreEntries.length; i++) { // Skip the header
                String[] parts = scoreEntries[i].split(",");
                if (parts.length == 2) {
                    highScores.add(new String[] { parts[0].trim(), parts[1].trim() });
                }
            }
            // Sort the scores in descending order and take the top 10
            Collections.sort(highScores, new Comparator<String[]>() {
                @Override
                public int compare(String[] o1, String[] o2) {
                    int score1 = Integer.parseInt(o1[1]);
                    int score2 = Integer.parseInt(o2[1]);
                    return Integer.compare(score2, score1); // Compare in descending order
                }
            });
            highScores = highScores.size() > 10 ? highScores.subList(0, 10) : highScores; // Limit to top 10 scores
        }

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

        if (backButton.overlap(new Vector2(touchX,touchY),1)) {
            game.screenManager.setScreen(ScreenType.MAIN_MENU);
            game.audio.buttonClickedSoundActivate();
        }
        return true;
    }

    @Override
    public boolean keyDown(int i) {
        if (i == ENTER && !UserName.isEmpty()) {
            inputName = false;
            inputCap = '#';
            timer = 0;
            writeUpdatedLB();
            game.eventM.reset();
        }
        return false;

    }

    @Override
    public boolean keyUp(int i) {
        return false;
    }


    @Override
    public boolean keyTyped(char character) {
        if (inputName) {
            if (character == '\b' && !UserName.isEmpty()) { // Handles backspace
                UserName = UserName.substring(0, UserName.length() - 1);

            } else if ((Character.isLetter(character) || Character.isDigit(character)) && UserName.length() < 14) {
                UserName += character;
            }
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
        font.dispose();
    }

}
