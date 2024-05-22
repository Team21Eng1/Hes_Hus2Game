package com.main.minigames;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.main.Main;
import com.main.screens.MainGameScreen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import static javax.swing.JColorChooser.showDialog;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Align;

import java.security.AlgorithmConstraints;

public class snakegame implements Screen {
    private final Main game;
    private SpriteBatch batch;
    private Texture headTexture, bodyTexture,appleTexture;
    private Array<Rectangle> snake;
    private Rectangle apple;
    private long lastUpdateTime;
    private float moveTime = 0.05f;
    private int snakeDirection = Input.Keys.RIGHT;
    private int score;
    private BitmapFont customFont, titleFont;
    private Texture backgroundTexture;
    private Stage stage;
    private Skin skin;
    private Window popupWindow;
    private long startTime;
    private float difficultyIncrementInterval = 5.0f; // Increase difficulty every 5 seconds
    private long lastDifficultyIncrementTime;
    private Array<Rectangle> apples;
    private static final int NUM_APPLES = 5;
    private boolean gameStarted = false;


    public snakegame (Main game) {
        this.game = game;
    }

    @Override
    public void show() {
        customFont = new BitmapFont(Gdx.files.internal("font/WhitePeaberry.fnt"));
        batch = new SpriteBatch();
        backgroundTexture = new Texture("snakegame/snakegrass.png");
        headTexture = new Texture("snakegame/head_right.png");
        bodyTexture = new Texture("snakegame/body_horizontal.png");
        appleTexture = new Texture("snakegame/apple.png");
        snake =new Array<>();
        initializeSnake();
        apples = new Array<>();
        placeApples();
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        setupPopup();
        startTime = TimeUtils.millis();
        lastDifficultyIncrementTime = startTime;
    }

    private void placeApples() {
        apples.clear();
        for (int i = 0; i < NUM_APPLES; i++) {
            Rectangle apple = new Rectangle();
            boolean placed = false;
            while (!placed) {
                int newX = MathUtils.random(0, Gdx.graphics.getWidth() / 20 - 1) * 20;
                int newY = MathUtils.random(0, Gdx.graphics.getHeight() / 20 - 1) * 20;
                apple.set(newX, newY, 20, 20);
                placed = true;
                for (Rectangle segment : snake) {
                    if (segment.overlaps(apple)) {
                        placed = false;
                        break;
                    }
                }
            }
            apples.add(apple);
        }
    }

    private void initializeSnake(){
        snake.clear();
        snake.add(new Rectangle(160,100,20,20));
        snake.add(new Rectangle(140, 100, 20, 20));
        snake.add(new Rectangle(120, 100, 20, 20));
    }


    private void setupPopup() {
        TextureRegionDrawable backgroundDrawable = new TextureRegionDrawable(new TextureRegion(new Texture("mini_games/howtoplay.png")));
        Window.WindowStyle windowStyle = new Window.WindowStyle();
        windowStyle.titleFont = customFont;
        windowStyle.titleFontColor = Color.WHITE;
        windowStyle.background = backgroundDrawable;

        popupWindow = new Window("HOW TO PLAY", windowStyle);
        popupWindow.getTitleLabel().setAlignment(Align.center);
        popupWindow.setSize(350, 350);
        popupWindow.setPosition(Gdx.graphics.getWidth() / 2 - 175, Gdx.graphics.getHeight() / 2 - 175);
        popupWindow.align(Align.center);
        popupWindow.setModal(true);
        popupWindow.setVisible(true);

        Label instructions = new Label("Use arrow keys to control the snake. Try to eat as many apples as possible in 25 seconds. " +
                "Click to continue.", new Label.LabelStyle(customFont, Color.WHITE));
        instructions.setWrap(true);
        instructions.setAlignment(Align.center);

        popupWindow.add(instructions).expand().fill().center().width(280).pad(10);
        popupWindow.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameStarted = true;
                startTime = TimeUtils.millis(); // Reset the start time when the game starts
                popupWindow.remove();
            }
        });
        stage.addActor(popupWindow);
    }
    @Override
    public void render(float delta) {
        if (gameStarted) {
            handleInput();
            if (System.currentTimeMillis() - lastUpdateTime > moveTime * 1000) {
                moveSnake();
                lastUpdateTime = System.currentTimeMillis();
            }

            float elapsedTime = (TimeUtils.millis() - startTime) / 1000.0f;
            if (elapsedTime > 25) {
                game.setScreen(new MainGameScreen(game));
            }

            if (elapsedTime - (lastDifficultyIncrementTime / 1000.0f) > difficultyIncrementInterval) {
                moveTime *= 0.9; // Increase speed by reducing moveTime by 10%
                lastDifficultyIncrementTime = TimeUtils.millis(); // Reset the difficulty increment timer
            }
        }

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        for (Rectangle bodyPart : snake) {
            Texture texture = bodyPart.equals(snake.first()) ? headTexture : bodyTexture;
            batch.draw(texture, bodyPart.x, bodyPart.y);
        }
        for (Rectangle apple : apples) {
            batch.draw(appleTexture, apple.x, apple.y);
        }
        customFont.draw(batch, "Score:" + score, 10, Gdx.graphics.getHeight() - 10);
        customFont.draw(batch, "Use arrow keys to move the snake",
                Gdx.graphics.getWidth() - 400, Gdx.graphics.getHeight() - 10);

        if (gameStarted) {
            float elapsedTime = (TimeUtils.millis() - startTime) / 1000.0f;
            customFont.draw(batch, "Time: " + (30 - (int) elapsedTime), Gdx.graphics.getWidth() / 2 - 50, Gdx.graphics.getHeight() - 10);
        } else {
            customFont.draw(batch, "Time: 25", Gdx.graphics.getWidth() / 2 - 50, Gdx.graphics.getHeight() - 10);
        }
        batch.end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }
    private void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && snakeDirection != Input.Keys.RIGHT) {
            snakeDirection = Input.Keys.LEFT;
            headTexture = new Texture("snakegame/head_left.png");
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && snakeDirection != Input.Keys.LEFT) {
            snakeDirection = Input.Keys.RIGHT;
            headTexture = new Texture("snakegame/head_right.png");
        } else if (Gdx.input.isKeyPressed(Input.Keys.UP) && snakeDirection != Input.Keys.DOWN) {
            snakeDirection = Input.Keys.UP;
            headTexture = new Texture("snakegame/head_up.png");
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && snakeDirection != Input.Keys.UP) {
            snakeDirection = Input.Keys.DOWN;
            headTexture = new Texture("snakegame/head_down.png");
        }

    }
    public void moveSnake() {
        Rectangle head = new Rectangle(snake.first().x, snake.first().y, 20, 20);
        switch (snakeDirection) {
            case Input.Keys.LEFT:
                head.x -= 20;
                if (head.x < 0) head.x = Gdx.graphics.getWidth() - 20;
                break;
            case Input.Keys.RIGHT:
                head.x += 20;
                if (head.x >= Gdx.graphics.getWidth()) head.x = 0;
                break;
            case Input.Keys.UP:
                head.y += 20;
                if (head.y >= Gdx.graphics.getHeight()) head.y = 0;
                break;
            case Input.Keys.DOWN:
                head.y -= 20;
                if (head.y < 0) head.y = Gdx.graphics.getHeight() - 20;
                break;
        }
        if (checkCollision(head)) {
            game.setScreen(new MainGameScreen(game));
        } else {
            snake.insert(0, head);

            boolean ateApple = false;
            for (int i = 0; i < apples.size; i++) {
                if (head.overlaps(apples.get(i))) {
                    score += 10;
                    apples.removeIndex(i);
                    ateApple = true;
                    break;
                }
            }
            if (!ateApple) {
                snake.pop();
            }
            if (apples.size == 0) {
                placeApples();
            }
        }
    }

    private boolean checkCollision(Rectangle head){
        for (int i = 1; i < snake.size; i++) {
            if (head.overlaps(snake.get(i))) {
                return true;
            }
        }
        return false;
    }


    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);

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
        backgroundTexture.dispose();
        batch.dispose();
        headTexture.dispose();
        bodyTexture.dispose();
        appleTexture.dispose();
        customFont.dispose();
        stage.dispose();
    }
}
