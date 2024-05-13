package com.main.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.main.Main;
import com.main.map.GameMap;
import com.main.utils.CollisionHandler;

import java.util.Arrays;
import java.util.stream.Stream;

public class Student extends Entity {
    Main game;
    GameMap gameMap;
    OrthographicCamera camera;


    char dir; // Current direction of the player
    public static final float animation_speed = 0.1f; // speed that sprite will animate or frame duration
    int tileSize;

    private Vector2[] pathKey;
    int currentKey = 0;
    int nextKey = 1;
    float pathTime,pathDst;

    public float startX, startY;
    Texture sprSheet;
    public static int spriteX = 16;
    public static int spriteY = 16;


    Animation<TextureRegion> walkDownAnimation, walkRightAnimation, walkLeftAnimation, walkUpAnimation;
    Animation<TextureRegion> idleDownAnimation, idleRightAnimation, idleLeftAnimation, idleUpAnimation;

    public Student(GameMap gameMap,int startX, int startY)
    {
        this.gameMap = gameMap;



        tileSize = gameMap.getTileSize();
        this.collisionHandler = new CollisionHandler(gameMap.getMap(), tileSize, tileSize, spriteX, spriteY * 0.5f, 0.7f, 0.7f);
        this.collisionHandler.addCollisionLayers("Trees", "wall_1", "wall_2", "wall_3", "roof_1", "roof_2", "roof_3", "other", "lilipads");

        this.speed = 200;
        worldX = startX;
        worldY = startY;


        pathKey = new Vector2[] {new Vector2(0,0),new Vector2(0,0)};
        setPath(pathKey);

        sprSheet = new Texture("character/NPCS.png");

        walkDownAnimation = new Animation<>(animation_speed, getFrames(sprSheet,new int[] {0,1,2,1},0,16,16,false));
        walkLeftAnimation = new Animation<>(animation_speed, getFrames(sprSheet,new int[] {0,1,2,1},1,16,16,false));
        walkRightAnimation = new Animation<>(animation_speed, getFrames(sprSheet,new int[] {0,1,2,1},2,16,16,false));
        walkUpAnimation = new Animation<>(animation_speed, getFrames(sprSheet,new int[] {0,1,2,1},3,16,16,false));


    }


    private float calcPathDst()
    {
        float totalDst = 0;
        for (int i = 0; i < pathKey.length;i++)
        {
            if (i+1<pathKey.length) {totalDst += pathKey[i].dst(pathKey[i+1]);}
            else {totalDst += pathKey[i].dst(pathKey[0]);}
        }
        return totalDst;
    }

    public void update(float delta)
    {
        stateTime += delta;
        updatePath(delta);
    }
    private void updatePath(float delta)
    {
        //Gdx.app.log("X: ",Float.toString(worldX));
        //Gdx.app.log("Y: ",Float.toString(worldY));
        Gdx.app.log("dir: ", String.valueOf(dir));
        pathTime += delta*100;
        worldX = pathKey[currentKey].x + (pathKey[nextKey].x-pathKey[currentKey].x)*pathTime/(pathKey[currentKey].dst(pathKey[nextKey]));
        worldY = pathKey[currentKey].y + (pathKey[nextKey].y-pathKey[currentKey].y)*pathTime/(pathKey[currentKey].dst(pathKey[nextKey]));
        if (pathTime > (pathKey[currentKey].dst(pathKey[nextKey])))
        {
            if (++nextKey==pathKey.length) {nextKey = 0;}
            if (++currentKey==pathKey.length) {currentKey = 0;}
            pathTime = 0;
            setDir(pathKey[currentKey],pathKey[nextKey]);
        }


    }


    public void setPath(Vector2[] keyP){

        pathKey = keyP;
        pathDst = calcPathDst();
        setDir(pathKey[currentKey],pathKey[nextKey]);
    }
    public void setDir(Vector2 v1, Vector2 v2)
    {
        Vector2 res = new Vector2(v2.x-v1.x,v2.y-v1.y).nor();
        if(Math.abs(res.x) > Math.abs(res.y))
        {
            if (Math.round(res.x) == 1){
                dir = 'R';
                currentAnimation = walkRightAnimation;
            } else {
                dir = 'L';
                currentAnimation = walkLeftAnimation;
            }
        }
        else
        {
            if (Math.round(res.y) == 1){
                dir = 'U';
                currentAnimation = walkUpAnimation;
            } else {
                dir = 'D';
                currentAnimation = walkDownAnimation;
            }
        }

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
