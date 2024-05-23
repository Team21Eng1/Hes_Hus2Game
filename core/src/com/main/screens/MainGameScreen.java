package com.main.screens;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.main.Main;
import com.main.entity.Player;

import com.main.entity.Student;
import com.main.map.*;

import com.main.utils.CollisionHandler;
import com.main.utils.ScreenType;

import java.util.ArrayList;
import java.util.List;
import com.main.gui.GUI;

/**
 * The MainGameScreen class is responsible for rendering and updating all the game elements
 * including the player, game world, UI, and handling user input during the main gameplay phase.
 */
public class MainGameScreen implements Screen, InputProcessor {

    // Final attributes
    private final Color shader;
    private final float zoom = 3f;
    private final Player player;
    public final BitmapFont font;
    private final GameMap gameMap;
    private final OrthographicCamera camera, roomCam, guiCam;
    private final ShapeRenderer shapeRenderer;
    private final Main game;
    private float FadePerc;


    // Non-final attributes
    private String activity;
    private int duration;

    private boolean fadeOut, lockMovement;
    private GUI gui;


    private List<String> activities = new ArrayList<>();


    private Student student1,student2,student3;
    private boolean space;
    private float energyMax, energy;
    public GameMap roomMap,csRoom,piaRoom,accomRoom,gymRoom;

    public int score;


    /**
     * Constructs the main game screen with necessary game components.
     * Initializes game map, player, camera, UI elements, and sets the initial game state.
     *
     * @param game The main game application instance.
     */
    public MainGameScreen(Main game) {
        this.game = game;
        this.shader = new Color(0.5f, 0.5f, 0.5f, 1);
        // Initialize non-final attributes
        this.duration = 1;

        this.fadeOut = this.lockMovement = false;

        // Setting up the game
        this.camera = new OrthographicCamera();
        this.roomCam = new OrthographicCamera();
        this.guiCam = new OrthographicCamera();

        this.gameMap = new GameMap(this.game,this.camera,"map/MainMap.tmx");
        this.player = new Player(this.game, this.gameMap, this.camera,1500,600);
        this.gui = new GUI(this.game,this.guiCam,this.zoom);


        this.student1 = new Student(this.gameMap,1500,600);
        this.student1.setPath(new Vector2[] {new Vector2(1500,600),new Vector2(1600,600),new Vector2(1600,500),new Vector2(1700,800)});
        this.student2 = new Student(this.gameMap,1650,1400);
        this.student2.setPath(new Vector2[] {new Vector2(1650,1400),new Vector2(1660,650),new Vector2(1080,650),new Vector2(1080,930)});
        this.student3 = new Student(this.gameMap,1500,600);


        this.font = new BitmapFont(Gdx.files.internal("font/WhitePeaberry.fnt"));
        this.shapeRenderer = new ShapeRenderer();


        this.player.setPos(1389, 635);
        this.camera.setToOrtho(false, this.game.screenWidth / this.zoom, this.game.screenHeight / this.zoom);
        this.roomCam.setToOrtho(false, this.game.screenWidth / this.zoom, this.game.screenHeight / this.zoom);
        this.guiCam.setToOrtho(false, this.game.screenWidth / this.zoom, this.game.screenHeight / this.zoom);
        this.camera.update();
        this.roomCam.update();
        this.guiCam.update();
        this.shapeRenderer.setProjectionMatrix(camera.combined);

        roomMap = null;

        csRoom = new CS(this.game,this.roomCam);
        accomRoom = new Accom(this.game,this.roomCam);
        piaRoom = new Piazza(this.game,this.roomCam);
        gymRoom = new Gym(this.game,this.roomCam);

        space = false;

        this.energyMax = 100f;
        this.energy = 100f;

        this.FadePerc = 0;
        fadeOut = false;
    }
    public void updateEnergy(float delta)
    {
        Player locPlay;
        if (roomMap != null && roomMap.showing) {locPlay = roomMap.player;}
        else {locPlay = player;}
        if(locPlay==null){locPlay=player;}
        if (locPlay.isMoving && !lockMovement)
        {
            energy -= locPlay.normalizedSpeed * delta*0.01f;
        }
        if (energy<=0)
        {
            energy = 70;
            resetDay();
            player.setPos(1389, 635);
            //pass out...
        }
        gui.energyFill.setFill(energy/energyMax);
    }

    @Override
    public void render(float delta) {
        if (!lockMovement) player.update(delta);
        game.eventM.updateTime(delta);
        updateEnergy(delta);

        ScreenUtils.clear(0, 0, 1, 1);
        gameMap.update(delta);
        gameMap.render();

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();

        drawRoom(delta,game.batch);
        updateEntities(delta);
        drawEntities();

        game.batch.end();
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        if (fadeOut)
        {
            shapeRenderer.setProjectionMatrix(roomCam.combined);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            lockMovement = true;
            FadePerc += delta;


            shapeRenderer.setColor(new Color(0,0,0,FadePerc));
            shapeRenderer.rect(-1000,-1000,5000, 5000);
            if (FadePerc > 1)
            {
                FinishReset();
                camera.update();
                fadeOut=false;
                FadePerc = 0;
            }
            shapeRenderer.end();

        }

        gui.update(delta);
        //gui.counterText.text = String.format("Recreation Activities done: \nStudy hours: " + player.worldX + "\nMeals Eaten: " + player.worldY, dayNum, timeElapsed );
        gui.render(game.batch);
    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
    }

    /**
     * Renders the game world elements including the map and player.
     * @param delta The time elapsed since the last frame.
     */
    public void drawRoom(float delta,SpriteBatch batch)
    {
        updateRoom(delta);
        if (roomMap != null && roomMap.showing)
        {
            Gdx.gl.glEnable(GL20.GL_BLEND);
            shapeRenderer.setProjectionMatrix(camera.combined);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(0, 0, 0, 1); // Adjust alpha for darkness
            shapeRenderer.rect(0, 0, gameMap.getWidth(), gameMap.getHeight());
            shapeRenderer.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);

            roomCam.position.x = roomMap.getWidth()/2;
            roomCam.position.y = roomMap.getHeight()/2;

            roomMap.update(delta);
            roomMap.render();


            roomMap.renderEntities(batch);
        }

    }
    public void freeze()
    {
        if (roomMap!=null && roomMap.showing==true){ roomMap.freeze = true;} else {lockMovement = true;}

        game.eventM.freezeTime = true;

    }
    public void unfreeze()
    {
        if (roomMap!=null && roomMap.showing==true){ roomMap.freeze = false;} else {lockMovement = false;}

        game.eventM.freezeTime = false;

    }

    public void updateRoom(float delta)
    {
        if (getDoorTouching() == "")
        {
            roomMap = null;
        }
        else if (getDoorTouching() != "" && roomMap == null)
        {
            switch (getDoorTouching()){
                case "Comp_sci_door":
                    roomMap = csRoom;
                    break;
                case "Piazza_door":
                    roomMap = piaRoom;
                    break;
                case "Gym_door":
                    roomMap = gymRoom;
                    break;
                case "Goodricke_door":
                    roomMap = accomRoom;
                    break;
            }
        }


        if (roomMap != null && !roomMap.showing && space && !(roomMap instanceof Gym))
        {
            roomMap.showing = true;
            lockMovement = true;
            space = false;

        } else if ((roomMap != null && roomMap.showing) || (roomMap instanceof Gym))
        {
            if (roomMap.interact() && space)
            {
                switch(roomMap.activity)
                {
                    case EXIT:
                        game.screenManager.setScreen(ScreenType.GAME_SCREEN);

                        roomMap.showing = false;
                        unfreeze();
                        lockMovement = false;
                        break;
                    case SLEEP:
                        if (Integer.valueOf(game.eventM.hours) > 19)
                        {
                            game.eventM.curDay++;
                            resetDay();
                        }
                        else {
                            //popup or summate
                            gui.popup = gui.sleep;
                            gui.popup.showing = true;
                            freeze();

                        }

                        break;
                    case EAT:
                        energy+=30;
                        game.eventM.updateHours(1);
                        if (energy > energyMax) energy = energyMax;
                        game.screenManager.setScreen(ScreenType.SNAKE_MINI_GAME);
                        roomMap.showing = false;
                        lockMovement = false;
                        break;
                    case STUDY:
                        gui.durationPopupMenu.setPopText("Study");
                        gui.durationPopupMenu.showing = true;
                        roomMap.lockMovement = true;
                        break;
                    case EXCERCISE:
                        if (energy < 20)
                        {
                            gui.popup = gui.energyP;
                            gui.popup.showing = true;
                            freeze();
                        } else {
                            energy-=20;
                            game.eventM.updateHours(1);
                            game.screenManager.setScreen(ScreenType.GYM, 1);
                        }
                        break;
                    case NONE:
                        if (energy < 20){
                            gui.popup = gui.energyP;
                            gui.popup.showing = true;
                            freeze();
                        } else {
                            energy-=20;
                            game.eventM.updateHours(2);
                            game.screenManager.setScreen(ScreenType.PONG_MINI_GAME,1);
                        }

                        break;

                }

                space = false;

            }
        }
    }




    private void drawEntities()
    {
        player.PS.render(game.batch);
        game.batch.draw(player.getCurrentFrame(), player.worldX, player.worldY, Player.spriteX, Player.spriteY);
        game.batch.draw(student1.getCurrentFrame(),student1.worldX,student1.worldY,Student.spriteX,Student.spriteY);
        game.batch.draw(student2.getCurrentFrame(),student2.worldX,student2.worldY,Student.spriteX,Student.spriteY);
        game.batch.draw(student3.getCurrentFrame(),student3.worldX,student3.worldY,Student.spriteX,Student.spriteY);
    }
    private void updateEntities(float delta)
    {
        student1.update(delta);
        student2.update(delta);
        student3.update(delta);
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
    private void resetDay(){
        fadeOut = true;
        freeze();
        game.eventM.nextDay();
        if (game.eventM.curDay == 8)
        {
            score = game.eventM.getScore();
            Gdx.app.log("score: ", String.valueOf(score));
            Gdx.app.log("Acts: ", String.valueOf(activities));
            game.screenManager.setScreen(ScreenType.LEADERBOARD);

        }

    }
    private void FinishReset()
    {
        if (roomMap instanceof Accom) {
            energy = energyMax;
        }
        unfreeze();
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
        //gui.popup.showing=true;

        Vector2 mouse = new Vector2(touchX,game.screenHeight - touchY);
        if (gui.menuButton.overlap(mouse,zoom)) {
            game.audio.buttonClickedSoundActivate();
            game.screenManager.setScreen(ScreenType.MAIN_MENU);
        }
        if (gui.durationPopupMenu.showing)
        {
            if(gui.durationPopupMenu.durUpB.overlap(mouse,zoom)){
                game.audio.buttonClickedSoundActivate();
                gui.durationPopupMenu.changeDur(1);
                //duration up
                return true;
            } else if (gui.durationPopupMenu.durDownB.overlap(mouse,zoom))
            {
                game.audio.buttonClickedSoundActivate();
                gui.durationPopupMenu.changeDur(-1);
                //duration down
                return true;
            } else if (gui.durationPopupMenu.BackB.overlap(mouse,zoom))
            {
                game.audio.buttonClickedSoundActivate();
                //close menu
                roomMap.lockMovement=false;
                gui.durationPopupMenu.showing = false;
                return true;
            }else if (gui.durationPopupMenu.GoB.overlap(mouse,zoom))
            {
                game.audio.buttonClickedSoundActivate();
                //start activity
                if (energy < 10*gui.durationPopupMenu.getDuration())
                {
                    gui.popup = gui.energyP;
                    gui.popup.showing = true;
                    freeze();
                    gui.durationPopupMenu.showing = false;

                } else {
                    energy-=10*gui.durationPopupMenu.getDuration();
                    game.eventM.updateHours(gui.durationPopupMenu.getDuration());
                    game.screenManager.setScreen(ScreenType.TYPING_MINI_GAME, gui.durationPopupMenu.getDuration());
                    gui.durationPopupMenu.showing = false;
                    roomMap.lockMovement = false;
                }
                return true;
            }
        }
        return true;
    }

    @Override
    public void resize(int i, int i1) {

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
        player.dispose();
        font.dispose();
        roomMap = new CS(game,camera);
        roomMap.dispose();
        csRoom.dispose();
        piaRoom.dispose();
        accomRoom.dispose();
        gymRoom.dispose();
    }

    @Override
    public boolean keyDown(int i) {
        if (i == Input.Keys.SPACE) {

            if (gui.popup.showing)
            {
                gui.popup.showing = false;
                unfreeze();
            } else {space = true;}
        }


        return false;
    }

    @Override
    public boolean keyUp(int i) {
        if (i == Input.Keys.SPACE) {
            space = false;

        }

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
