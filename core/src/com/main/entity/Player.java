package com.main.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.main.map.GameMap;
import com.main.Main;
import com.main.utils.CollisionHandler;

import java.util.ArrayList;

/**
 * The Player class represents the character in the game, handling movement, collision,
 * and animations.
 */
public class Player extends Entity implements Disposable {
    Main game;
    GameMap gameMap;
    OrthographicCamera camera;
    public boolean camFollow = true;

    char dir; // Current direction of the player
    public static final float animation_speed = 0.12f; // speed that sprite will animate or frame duration

    int tileSize;

    public float startX, startY;

    Texture idleSheet, walkSheet;

    Animation<TextureRegion> walkDownAnimation, walkRightAnimation, walkLeftAnimation, walkUpAnimation;
    Animation<TextureRegion> idleDownAnimation, idleRightAnimation, idleLeftAnimation, idleUpAnimation;
    public static int spriteX = 15;
    public static int spriteY = 23;

    /**
     * Constructs a new Player instance.
     *
     * @param game The main game object.
     * @param gameMap The game map for collision detection and boundaries.
     * @param camera The camera to follow the player.
     */
    public Player(Main game, GameMap gameMap, OrthographicCamera camera) {
        this.game = game;
        this.gameMap = gameMap;
        this.camera = camera;


        tileSize = gameMap.getTileSize();
        this.collisionHandler = new CollisionHandler(gameMap.getMap(), tileSize, tileSize, spriteX,  spriteY *0.5f, 0.7f, 0.7f);
        this.collisionHandler.addCollisionLayers("Trees", "wall_1", "wall_2", "wall_3", "roof_1", "roof_2", "roof_3", "other", "lilipads");
        //this.settingsScreen = settingsScreen;

        this.speed = 200;
        startX = (float) game.screenWidth /2 - (float) game.screenHeight /2;
        startY = 500;
        worldX = startX;
        worldY = startY;

        dir = 'D';
        updateGender();
    }

    /**
     * Updates the player's position, animations, and handles collision.
     *
     * @param delta Time since last frame in seconds.
     */
    public void update(float delta) {


        boolean isMoving = false;
        currentAnimation.setFrameDuration(animation_speed);
        // Determine if the player is moving diagonally
        boolean isMovingDiagonally = ((Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) ||
                (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S))) &&
                ((Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) ||
                        (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)));
        // Calculate the normalised speed for diagonal movement
        double normalizedSpeed = speed;
        if (isMovingDiagonally) {
            normalizedSpeed = (speed / Math.sqrt(2)) * 1.07; // Adjust speed for diagonal movement
        }
        // shift key doubles player speed
        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT)) {
            normalizedSpeed *= 1.5;
            currentAnimation.setFrameDuration(0.08f);// Increase speed if shift is pressed
        }

        float targX = worldX;
        float targY = worldY;

        // checks movement and updates animation, adjusts speed with delta time
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            targY = worldY + (float) (normalizedSpeed * Gdx.graphics.getDeltaTime());
            currentAnimation = walkUpAnimation;
            dir = 'U';
            isMoving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            targY = worldY - (float) (normalizedSpeed * Gdx.graphics.getDeltaTime());
            currentAnimation = walkDownAnimation;
            dir = 'D';
            isMoving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            targX = worldX - (float) (normalizedSpeed * Gdx.graphics.getDeltaTime());
            currentAnimation = walkLeftAnimation;
            dir = 'L';
            isMoving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            targX = worldX + (float) (normalizedSpeed * Gdx.graphics.getDeltaTime());
            currentAnimation = walkRightAnimation;
            dir = 'R';
            isMoving = true;
        }

        // player doesn't walk beyond the map
        if (targX + spriteX > gameMap.getWidth()){
            targX = gameMap.getWidth() - spriteX;
        }
        else if (targX < 0){
            targX = 0;
        }
        if (targY + spriteY > gameMap.getHeight()){
            targY = gameMap.getHeight() - spriteY;
        }
        else if (targY < 0){
            targY = 0;
        }

        // this will switch sprite sheet to idle sprite sheet when player is not moving
        if (!isMoving) {
            if (currentAnimation == walkDownAnimation) currentAnimation = idleDownAnimation;
            else if (currentAnimation == walkRightAnimation) currentAnimation = idleRightAnimation;
            else if (currentAnimation == walkLeftAnimation) currentAnimation = idleLeftAnimation;
            else if (currentAnimation == walkUpAnimation) currentAnimation = idleUpAnimation;
        }

        Vector2 newPos = collisionHandler.adjustPos(worldX, worldY, targX, targY);
        worldX = newPos.x;
        worldY = newPos.y;


        stateTime += delta;
        if (camFollow) camUpdate();




    }
    public void camUpdate()
    {

        float camX = worldX + spriteY /2f;
        float camY = worldY + spriteY /2f;
        camera.position.set(camX, camY, 0);
        // this will make sure the camera follows the player
        if (camX + camera.viewportWidth/2f > gameMap.getWidth()) {
            camera.position.set(gameMap.getWidth() - camera.viewportWidth/2f, camera.position.y, 0);
        }
        else if (camX - camera.viewportWidth/2f < 0){
            camera.position.set(camera.viewportWidth/2f, camera.position.y, 0);
        }
        if (camY + camera.viewportHeight/2f > gameMap.getHeight()) {
            camera.position.set(camera.position.x, gameMap.getHeight() - camera.viewportHeight/2f, 0);
        }
        else if (camY - camera.viewportHeight/2f < 0){
            camera.position.set(camera.position.x, camera.viewportHeight/2f, 0);
        }

        camera.update();
    }


    /**
     * Sets the player's position to the specified coordinates.
     *
     * @param newX The new X coordinate.
     * @param newY The new Y coordinate.
     */
    public void setPos(float newX, float newY) {
        worldX = newX;
        worldY = newY;
    }

    /**
     * Updates the player's gender by changing the TextureRegions' internal path using
     * the player's choice in the settings menu.
     * Then updates corresponding textures and animations.
     *
     * This functionality should be segregated into its own class to reduce overheads and
     * processing delay.
     */
    public void updateGender(){
        if (idleSheet != null) {idleSheet.dispose();}
        if (walkSheet != null) {walkSheet.dispose();}
//        if (game.gameData.getGender()) {
//            idleSheet = new Texture("character/boy_idle.png");
//            walkSheet = new Texture("character/boy_walk.png");
//        } else {
//            idleSheet = new Texture("character/girl_idle.png");
//            walkSheet = new Texture("character/girl_walk.png");
//        }
        Texture pSheet = new Texture("character/player.png");
        //TextureRegion[][] idleSpriteSheet = TextureRegion.split(idleSheet, spriteX, spriteY); // Splits the sprite sheet up by its frames
        //TextureRegion[][] walkSpriteSheet = TextureRegion.split(walkSheet, spriteX, spriteY); // Splits the sprite sheet up by its frames

        walkDownAnimation = new Animation<>(animation_speed, getFrames(pSheet,0,5,3,15,23,33,25,17,20,false)); // First row for down
        walkLeftAnimation = new Animation<>(animation_speed, getFrames(pSheet,0,5,4,15,23,33,25,17,20,true)); // Second row for left
        walkRightAnimation = new Animation<>(animation_speed, getFrames(pSheet,0,5,4,15,23,33,25,17,20,false)); // Third row for right
        walkUpAnimation = new Animation<>(animation_speed, getFrames(pSheet,0,5,5,15,23,33,25,17,20,false)); // Fourth row for up

        idleDownAnimation = new Animation<>(animation_speed, getFrames(pSheet,0,5,0,15,23,33,25,17,20,false));
        idleLeftAnimation = new Animation<>(animation_speed, getFrames(pSheet,0,5,1,15,23,33,25,17,20,true));
        idleRightAnimation = new Animation<>(animation_speed, getFrames(pSheet,0,5,1,15,23,33,25,17,20,false));
        idleUpAnimation = new Animation<>(animation_speed, getFrames(pSheet,0,5,2,15,23,33,25,17,20,false));

        setDirection(dir);
    }



    public void setDirection(char dir){
        this.dir = dir;
        switch (dir){
            case 'D':
                currentAnimation = idleDownAnimation;
                break;
            case 'L':
                currentAnimation = idleLeftAnimation;
                break;
            case 'R':
                currentAnimation = idleRightAnimation;
                break;
            case 'U':
                currentAnimation = idleUpAnimation;
                break;
        }
    }

    /**
     * Gets the current animation frame for the player based on the state time.
     *
     * @return The current TextureRegion of the player's animation.
     */


    public CollisionHandler getCollisionHandler(){
        return collisionHandler;
    }

    public Rectangle getHitBox(){
        return new Rectangle(worldX, worldY, spriteX, spriteY);
    }

    public void dispose(){
        idleSheet.dispose();
        walkSheet.dispose();
    }
    @Override
    public int getSpriteX()
    {
        return spriteX;
    }
    @Override
    public int getSpriteY()
    {
        return spriteY;
    }
}
