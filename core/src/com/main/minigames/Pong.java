package com.main.minigames;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.main.Main;
import com.main.screens.MainGameScreen;
import com.main.utils.ScreenManager;
import com.main.utils.ScreenType;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;



public class Pong implements Screen {
    private ShapeRenderer shapeRenderer;
    private Circle ball;
    private Rectangle paddle;
    private Vector2 ballVelocity;
    private int scoreLeft = 0;
    private int scoreRight =0;
    private static final float PADDLE_SPEED = 500;
    private static final float BALL_SPEED = 200;
    private float timeElapsed =0;
    private float increasedInterval = 10;
    private int score =0;
    private SpriteBatch batch;

    private Main game;
    private boolean gameOver;
    private float gameOverTimer;
    private static final float GAME_OVER_PAUSE = 2;
    private Texture backgroundImage;
    private Stage stage;
    private BitmapFont customFont, titleFont;
    private Window popupWindow;


    public Pong (Main game) {
        this.game = game;
    }
    @Override
    public void show() {
        customFont = new BitmapFont(Gdx.files.internal("font/WhitePeaberry.fnt"));
        batch = new SpriteBatch();

        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        paddle= new Rectangle(Gdx.graphics.getWidth()/ 2 - 50, 20, 140, 10);
        ball = new Circle(Gdx.graphics.getWidth()/ 2, Gdx.graphics.getHeight() /2,10);
        ballVelocity = new Vector2(BALL_SPEED, -BALL_SPEED);
        backgroundImage = new Texture("bg_space_seamless.png");

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

    private void setupPopup(){
        TextureRegionDrawable backgroundDrawable = new TextureRegionDrawable(new TextureRegion(new Texture("mini_games/howtoplay.png")));
        Window.WindowStyle windowStyle = new Window.WindowStyle();
        windowStyle.titleFont = customFont;
        windowStyle.titleFontColor = Color.WHITE;
        windowStyle.background = backgroundDrawable;

        popupWindow = new Window("HOW TO PLAY", windowStyle);
        popupWindow.getTitleLabel().setAlignment(Align.center);
        popupWindow.setSize(350,350);
        popupWindow.setPosition(Gdx.graphics.getWidth()/ 2 - 200, Gdx.graphics.getHeight()/2 - 200);
        popupWindow.align(Align.center);
        popupWindow.setModal(true);
        popupWindow.setVisible(true);

        Label instructions = new Label("Use LEFT and RIGHT arrow keys to move the paddle. Hit the ball with paddle. "+
                "Click to continue.", new Label.LabelStyle(customFont, Color.WHITE));
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

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                popupWindow.remove();
            }
        }, 5);
    }

    public void render(float delta) {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(backgroundImage, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.circle(ball.x, ball.y, ball.radius);
        shapeRenderer.rect(paddle.x, paddle.y, paddle.width, paddle.height);
        shapeRenderer.end();

        batch.begin();
        customFont.draw(batch ,"Score " + score, 10, Gdx.graphics.getHeight() - 10);
        customFont.draw(batch, "Use LEFT and RIGHT arrow keys to move the paddle",
                Gdx.graphics.getWidth() - 400, Gdx.graphics.getHeight() - 10);
        batch.end();

        update(delta);
        draw();
        stage.act(delta);
        stage.draw();
    }
    private void update(float delta) {
        if (gameOver) {
            if (gameOverTimer > 0) {
                gameOverTimer -= delta;
            }
            return;
        }
        timeElapsed += delta;
        if (timeElapsed >= increasedInterval) {
            increaseBallSpeed();
            timeElapsed = 0;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            paddle.x -= PADDLE_SPEED * delta;
            if (paddle.x < 0) {
                paddle.x = 0;
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            paddle.x += PADDLE_SPEED * delta;
            if (paddle.x + paddle.width > Gdx.graphics.getWidth()) {
                paddle.x = Gdx.graphics.getWidth() - paddle.width;
            }
        }
        ball.x += ballVelocity.x * delta;
        ball.y += ballVelocity.y * delta;

        if (ball.x < ball.radius || ball.x > Gdx.graphics.getWidth() - ball.radius) {
            ballVelocity.x = -ballVelocity.x;
        }
        if (ball.y > Gdx.graphics.getHeight() - ball.radius) {
            ballVelocity.y = -ballVelocity.y;
        }

        if (ball.y - ball.radius < paddle.y + paddle.height && ball.y - ball.radius > paddle.y) {
            if (ball.x > paddle.x && ball.x < paddle.x + paddle.width) {
                ballVelocity.y = -ballVelocity.y;
                ball.y = paddle.y + paddle.height + ball.radius;
                score++;
            }
        }
        if (ball.y - ball.radius < paddle.y) {
            resetBall();
            resetGame();
            game.screenManager.setScreen(ScreenType.GAME_SCREEN);

        }

    }

    private void increaseBallSpeed(){
        float speedFactor = 1.6f;
        ballVelocity.scl(speedFactor);
        }

    private void resetBall(){
        ball.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight() / 2);
        ballVelocity.set(BALL_SPEED, BALL_SPEED);
    }
    private void draw(){

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
        shapeRenderer.dispose();
        batch.dispose();
        backgroundImage.dispose();
        if (stage != null) {
            stage.dispose();
        }
    }

    public void resetGame() {

    }
}
