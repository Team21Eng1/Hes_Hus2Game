package com.main.minigames;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.Vector4;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.main.Main;
import com.main.entity.Entity;
import com.main.screens.MainGameScreen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import static javax.swing.JColorChooser.showDialog;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Align;
import com.main.utils.ActivityType;
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
    private Rectangle screen;
    private ShapeRenderer shapeRenderer;
    private static float tileSize = 45;

    float timer,t;
    private Texture foreGroundImage;
    private Entity foreground;

    private OrthographicCamera cam;


    public snakegame (Main game) {
        this.game = game;
    }

    @Override
    public void show() {
        customFont = new BitmapFont(Gdx.files.internal("font/WhitePeaberry.fnt"));
        batch = game.batch;
        shapeRenderer = new ShapeRenderer();




        backgroundTexture = new Texture("snakegame/snakegrass.png");
        headTexture = new Texture("snakegame/head_right.png");
        bodyTexture = new Texture("snakegame/body_horizontal.png");
        appleTexture = new Texture("snakegame/apple.png");
        screen = new Rectangle(0,0,tileSize*9,tileSize*16);
        snake =new Array<>();
        initializeSnake();

        this.cam = new OrthographicCamera();
        this.cam.setToOrtho(false, this.game.screenWidth, this.game.screenHeight);
        this.cam.rotate(new Vector3(0,0,1),8.5f);
        game.defaultCamera.rotate(new Vector3(0,0,1),0);
        cam.position.x = -276;
        cam.position.y = 274;
        cam.update();


        foreGroundImage = new Texture("gymmini/EatPhon.png");
        foreground = new Entity(0,0);
        foreground.currentAnimation= new Animation<>(0.4f,foreground.getFrames(foreGroundImage,0,3,0,1600,900,false));

        apple = new Rectangle();
        placeApple();

        timer = 0.1f;
        t=0;
    }
    private void initializeSnake(){
        snake.clear();
        snake.add(new Rectangle(screen.x + 4*tileSize, screen.y + 5*tileSize,tileSize,tileSize));
        snake.add(new Rectangle(screen.x + 3*tileSize, screen.y + 5*tileSize, tileSize, tileSize));
        snake.add(new Rectangle(screen.x + 2*tileSize, screen.y + 5*tileSize, tileSize, tileSize));
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
        popupWindow.setPosition(game.screenWidth / 2 - 200, game.screenHeight / 2 - 200);
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


        foreground.stateTime+=delta;
        t+=delta;

        if (t>timer) {
            moveSnake();
            t=0;
        }



        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        game.batch.setProjectionMatrix(cam.combined);
        shapeRenderer.setProjectionMatrix(cam.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(screen.x, screen.y, screen.width, screen.height);
        int col = 0;
        for (int y = 0;y<16;y++)
        {
            for (int x = 0; x<9;x++)
            {
                if (col == 0) {
                    shapeRenderer.setColor(Color.valueOf("4e1384"));
                    col++;
                } else {
                    col--;
                    shapeRenderer.setColor(Color.valueOf("3e2e9f"));
                }

                shapeRenderer.rect(screen.x + x*tileSize, screen.y+y*tileSize, tileSize, tileSize);
            }
//            if (col == 1) {col=0;} else {col=1;}
        }
        shapeRenderer.end();

        batch.begin();
        //batch.draw(backgroundTexture, 0, 0, game.screenWidth, game.screenHeight);
        for (int i = 0;i<snake.size;i++) {
            Texture texture = getSnakeTexture(i);
            batch.draw(texture, snake.get(i).x, snake.get(i).y,tileSize,tileSize);
        }
        batch.draw(appleTexture, apple.x, apple.y,tileSize,tileSize);
        customFont.draw(batch, "Score:" + score, 10, game.screenHeight - 10);
        customFont.draw(batch, "Use arrow keys to move the snake",
                game.screenWidth - 400, game.screenHeight - 10);

        batch.end();
        game.batch.setProjectionMatrix(game.defaultCamera.combined);
        batch.begin();
        batch.draw(foreground.getCurrentFrame(),0,0,game.screenWidth,game.screenHeight);
        batch.end();



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

    public Texture getSnakeTexture(int snakeIndex)
    {
        //up down left right
        Vector4 URDL = new Vector4(0,0,0,0);
        Rectangle last,cur,next;
        cur = snake.get(snakeIndex);
        if (snakeIndex == 0) {
            return headTexture;
        }
        else if (snakeIndex == snake.size-1) {
            //tail
            last = snake.get(snakeIndex-1);
            if (last.x > cur.x) { URDL.y = 1;}
            else if (last.x < cur.x){URDL.w = 1;}
            else{
                if (last.y > cur.y) { URDL.x = 1;}
                else {URDL.z = 1;}
            }
            if (URDL.x==1) {return new Texture("snakegame/tail_down.png");}
            if (URDL.y==1) {return new Texture("snakegame/tail_left.png");}
            if (URDL.z==1) {return new Texture("snakegame/tail_up.png");}
            if (URDL.w==1) {return new Texture("snakegame/tail_right.png");}
        }
        else
        {
            next = snake.get(snakeIndex+1);
            last = snake.get(snakeIndex-1);

            if (last.x > cur.x) { URDL.y = 1;}
            else if (last.x < cur.x){URDL.w = 1;}
            else{
                if (last.y > cur.y) { URDL.x = 1;}
                else {URDL.z = 1;}
            }
            if (next.x > cur.x) { URDL.y = 1;}
            else if (next.x < cur.x){URDL.w = 1;}
            else{
                if (next.y > cur.y) { URDL.x = 1;}
                else {URDL.z = 1;}
            }
            return getTextFromDir(URDL);
        }
        return getTextFromDir(URDL);

    }
    public Texture getTextFromDir(Vector4 URDL)
    {
        if ((URDL.x == 1)&&(URDL.y == 1)) return new Texture("snakegame/body_topright.png");
        if ((URDL.x == 1)&&(URDL.z == 1)) return new Texture("snakegame/body_vertical.png");
        if ((URDL.x == 1)&&(URDL.w == 1)) return new Texture("snakegame/body_topleft.png");
        if ((URDL.z == 1)&&(URDL.y == 1)) return new Texture("snakegame/body_bottomright.png");
        if ((URDL.z == 1)&&(URDL.w == 1)) return new Texture("snakegame/body_bottomleft.png");
        if ((URDL.y == 1)&&(URDL.w == 1)) return new Texture("snakegame/body_horizontal.png");
        return new Texture("snakegame/body_vertical.png");
    }


    public void moveSnake() {
        Rectangle head = new Rectangle(snake.first().x, snake.first().y, tileSize, tileSize);
        switch (snakeDirection) {
            case Input.Keys.LEFT:
                head.x -= tileSize;
                if (head.x < screen.x) head.x = screen.x+screen.width - tileSize;
                break;
            case Input.Keys.RIGHT:
                head.x += tileSize;
                if (head.x >= screen.x+screen.width) head.x = screen.x;
                break;
            case Input.Keys.UP:
                head.y += tileSize;
                if (head.y >= screen.y+screen.height) head.y = screen.y;
                break;
            case Input.Keys.DOWN:
                head.y -= tileSize;
                if (head.y < screen.y) head.y = screen.y+screen.height - tileSize;
                break;
        }
        if (checkCollision(head)) {
            game.eventM.logEvent(ActivityType.EAT,score);
            game.screenManager.setScreen(ScreenType.GAME_SCREEN);
            this.dispose();
        } else {
            snake.insert(0, head);

            if (head.overlaps(apple)) {
                score += 10;
                timer -=0.01f;
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
            int newX = (int) MathUtils.random(0,(screen.width)/tileSize);
            int newY = (int) MathUtils.random(0,(screen.height)/tileSize);
            newX*=tileSize;newY*=tileSize;
            apple.set(screen.x+newX, screen.y+newY, tileSize, tileSize);

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
        headTexture.dispose();
        bodyTexture.dispose();
        appleTexture.dispose();
        customFont.dispose();
    }
}
