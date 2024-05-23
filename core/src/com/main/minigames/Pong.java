package com.main.minigames;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
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
import com.main.entity.Entity;
import com.main.utils.ActivityType;
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
    private static float PADDLE_SPEED;
    private static final float BALL_SPEED = 200;
    private float timeElapsed =0;
    private float increasedInterval = 10;
    private int score =0;
    private SpriteBatch batch;

    private Main game;
    private boolean gameOver;
    private float gameOverTimer;
    private static final float GAME_OVER_PAUSE = 2;
    private Texture backgroundImage,foreGroundImage;
    private Stage stage;
    private BitmapFont customFont, titleFont;
    private Window popupWindow;

    private static final float GAME_DURATION = 20.0f; // 20 seconds
    private float timeRemaining;
    private boolean gameStarted = false;

    private Entity foreground;
    private Rectangle screen;
    private int angle;




    public Pong (Main game) {
        this.game = game;
    }
    @Override
    public void show() {
        customFont = new BitmapFont(Gdx.files.internal("font/WhitePeaberry.fnt"));
        batch = new SpriteBatch();
        screen = new Rectangle(520,230,920,440);
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        paddle= new Rectangle(Gdx.graphics.getWidth()/ 2 - 50, screen.y+10, 140, 10);
        ball = new Circle(Gdx.graphics.getWidth()/ 2, Gdx.graphics.getHeight() /2,10);

        PADDLE_SPEED = 500;
        if (Math.random() > 0.5f)
        {
            angle = (int) (120 + 35* Math.random());
        } else{
            angle = (int) (240 + -35* Math.random());
        }


        ballVelocity = new Vector2((float) (200*Math.sin(Math.toRadians(angle))), (float) (200*Math.cos(Math.toRadians(angle))));

        backgroundImage = new Texture("bg_space_seamless.png");
        foreGroundImage = new Texture("gymmini/LectureLap.png");
        foreground = new Entity(0,0);
        foreground.currentAnimation= new Animation<>(0.4f,foreground.getFrames(foreGroundImage,0,3,0,1600,900,false));

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        setupPopup();
        timeRemaining = GAME_DURATION;

        //setupPopup();


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

        Label instructions = new Label("Use LEFT and RIGHT arrow keys to move the paddle. Hit the ball with the paddle. " +
                "Click to start the game.", new Label.LabelStyle(customFont, Color.WHITE));
        instructions.setWrap(true);
        instructions.setAlignment(Align.center);
        popupWindow.add(instructions).expand().fill().center().width(280).pad(10);
        popupWindow.row();
        popupWindow.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameStarted = true;
                timeRemaining = GAME_DURATION; // Initialize the timer
                popupWindow.remove();
            }
        });
        stage.addActor(popupWindow);
    }

    public void render(float delta) {

        foreground.stateTime+=delta;


        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(backgroundImage, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.circle(ball.x, ball.y, ball.radius);
        shapeRenderer.rect(paddle.x, paddle.y, paddle.width, paddle.height);
        shapeRenderer.end();

        batch.begin();

        customFont.draw(batch, "Score: " + score, 10, Gdx.graphics.getHeight() - 10);
        customFont.draw(batch, "Time: " + (int) timeRemaining, Gdx.graphics.getWidth() / 2 - 20, Gdx.graphics.getHeight() - 10);

        batch.draw(foreground.getCurrentFrame(),0,0,game.screenWidth,game.screenHeight);
        batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.WHITE);
        //shapeRenderer.rect(screen.x, screen.y, screen.width, screen.height);
        shapeRenderer.end();
        batch.begin();
        customFont.draw(batch ,"Score " + score, 10, Gdx.graphics.getHeight() - 10);

        customFont.draw(batch, "Use LEFT and RIGHT arrow keys to move the paddle",
                Gdx.graphics.getWidth() - 400, Gdx.graphics.getHeight() - 10);
        batch.end();

        if (gameStarted) {
            update(delta);
        }

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

        if (!gameStarted) return;

        timeRemaining -= delta;
        if (timeRemaining <= 0) {
            gameOver = true;
            gameOverTimer = GAME_OVER_PAUSE;
            game.setScreen(new MainGameScreen(game));
            return;
        }
        timeElapsed += delta;
        if (timeElapsed >= increasedInterval) {
            increaseBallSpeed();
            timeElapsed = 0;
        }


        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {

            paddle.x -= PADDLE_SPEED * delta;
            if (paddle.x < screen.x) {
                paddle.x = screen.x;
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            paddle.x += PADDLE_SPEED * delta;
            if (paddle.x + paddle.width > screen.x+screen.width) {
                paddle.x = screen.x+screen.width - paddle.width;
            }
        }
        ball.x += ballVelocity.x * delta;
        ball.y += ballVelocity.y * delta;

        if (ball.x <= ball.radius+screen.x) {
            ballVelocity.x = Math.abs(ballVelocity.x);
            increaseBallSpeed(1.1f);
        } else if (ball.x >= screen.x+screen.width - ball.radius)
        {
            ballVelocity.x = -Math.abs(ballVelocity.x);
            increaseBallSpeed(1.1f);
        }
        if (ball.y >= screen.y+screen.height - ball.radius) {
            ballVelocity.y = -Math.abs(ballVelocity.y);
            increaseBallSpeed(1.1f);
        }

        if (ball.y - ball.radius <= paddle.y + paddle.height && ball.y - ball.radius >= paddle.y-ball.radius*3) {
            if (ball.x -ball.radius > paddle.x && ball.x + ball.radius < paddle.x + paddle.width) {
                ballVelocity.y = -ballVelocity.y;
                float hitPos = (ball.x - paddle.x)/paddle.width;
                ballVelocity.rotateDeg((float) (-30*(hitPos-0.5)));
                ball.y = paddle.y + paddle.height + ball.radius;
                increaseBallSpeed(1.2f);
                score++;
            }
        }



        if (ball.y - ball.radius < paddle.y- ball.radius*4) {
            score *= 10;
            game.eventM.logEvent(ActivityType.STUDY,score);
            game.screenManager.setScreen(ScreenType.GAME_SCREEN);

        }
    }



    private void increaseBallSpeed(float speedFactor){
        PADDLE_SPEED *= 1 + (speedFactor-1)/4;
        ballVelocity.scl(speedFactor);
        }

    private void resetBall(){
        ball.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight() / 2);
        int downAngle = (int) (90 + 90* Math.random());
        ballVelocity.set((float) (200*Math.sin(Math.toRadians(downAngle))), (float) (200*Math.sin(Math.toRadians(downAngle))));
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
