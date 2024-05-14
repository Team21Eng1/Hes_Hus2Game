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
import com.main.utils.ScreenType;

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

        apple = new Rectangle();
        placeApple();

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        setupPopup();


        Texture exitTexture = new Texture("menu_gui/exit_button.png");
        ImageButton exitButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(exitTexture)));
        exitButton.getImage().setScale(3f);
        exitButton.setPosition(Gdx.graphics.getWidth() - exitButton.getWidth() - 65,30);


        exitButton.addListener(new ClickListener(){
            public void clicked (InputEvent event, float x, float y){
                game.screenManager.setScreen(ScreenType.GAME_SCREEN);
            }
        });
        stage.addActor(exitButton);
    }
    private void initializeSnake(){
        snake.clear();
        snake.add(new Rectangle(160,100,20,20));
        snake.add(new Rectangle(140, 100, 20, 20));
        snake.add(new Rectangle(120, 100, 20, 20));
    }


    private void setupPopup(){
        TextureRegionDrawable backgroundDrawable = new TextureRegionDrawable(new TextureRegion(new Texture("mini_games/howtoplay.png")));
        Window.WindowStyle windowStyle = new Window.WindowStyle();
        windowStyle.titleFont = customFont;
        windowStyle.titleFontColor = Color.WHITE;
        windowStyle.background = backgroundDrawable;

        popupWindow = new Window("HOW TO PLAY", windowStyle);
        popupWindow.getTitleLabel().setAlignment(Align.center);
        popupWindow.setSize(350, 350);
        popupWindow.setPosition(Gdx.graphics.getWidth() / 2 - 200, Gdx.graphics.getHeight() / 2 - 200);
        popupWindow.align(Align.center);
        popupWindow.setModal(true);
        popupWindow.setVisible(true);

        Label instructions = new Label("Use arrow keys to control the snake. Try to eat as many apples as possible without hitting yourself.  " +
                "CLick to continue.", new Label.LabelStyle(customFont, Color.WHITE));
        instructions.setWrap(true);
        instructions.setAlignment(Align.center);

        popupWindow.add(instructions).expand().fill().center().width(280).pad(10);
        popupWindow.row();

        popupWindow.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                popupWindow.remove();
            }
        });
        stage.addActor(popupWindow);

        Timer.schedule(new Timer.Task(){
            @Override
            public void run() {
                popupWindow.remove();
            }
        }, 5);
    }



    @Override
    public void render(float delta) {
        handleInput();
        if (System.currentTimeMillis() - lastUpdateTime > moveTime * 1000) {
            moveSnake();
            lastUpdateTime = System.currentTimeMillis();
        }

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        for (Rectangle bodyPart : snake) {
            Texture texture = bodyPart.equals(snake.first()) ? headTexture : bodyTexture;
            batch.draw(texture, bodyPart.x, bodyPart.y);
        }
        batch.draw(appleTexture, apple.x, apple.y);
        customFont.draw(batch, "Score:" + score, 10, Gdx.graphics.getHeight() - 10);
        customFont.draw(batch, "Use arrow keys to move the snake",
                Gdx.graphics.getWidth() - 400, Gdx.graphics.getHeight() - 10);

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
            game.screenManager.setScreen(ScreenType.GAME_SCREEN);
        } else {
            snake.insert(0, head);

            if (head.overlaps(apple)) {
                score += 10;
                placeApple();
            } else {
                snake.pop();
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

    private void placeApple() {
        if (apple == null) {
            apple = new Rectangle();
        }

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
