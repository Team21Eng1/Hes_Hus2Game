package com.main.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.main.Main;
import com.main.gui.TextBox;
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

    Texture sprSheet;
    public static int spriteX = 16;
    public static int spriteY = 16;
    public boolean canInteract,idle;
    String disText;
    public TextBox textBox;


    Animation<TextureRegion> walkDownAnimation, walkRightAnimation, walkLeftAnimation, walkUpAnimation;
    Animation<TextureRegion> idleDownAnimation, idleRightAnimation, idleLeftAnimation, idleUpAnimation;

    public Student(GameMap gameMap,int startX, int startY)
    {
        super(startX,startY);
        this.gameMap = gameMap;

        idle = true;


        tileSize = gameMap.getTileSize();
        this.collisionHandler = new CollisionHandler(gameMap.getMap(), tileSize, tileSize, spriteX, spriteY * 0.5f, 0.7f, 0.7f);
        this.collisionHandler.addCollisionLayers("Trees", "wall_1", "wall_2", "wall_3", "roof_1", "roof_2", "roof_3", "other", "lilipads");

        this.speed = 200;
        this.worldX = startX;
        this.worldY = startY;
        Gdx.app.log("hi",String.valueOf(worldX));


        sprSheet = new Texture("character/NPCS.png");
        int row = 0;
        int[] frames = new int[] {0,1,2,1};
        int skin = (int) Math.round(Math.random()*4);
        switch (skin){
            case 0:
                row = 4;
                break;
            case 1:
                frames = new int[] {3,4,5,4};
                break;
            case 2:
                break;
            case 3:
                frames = new int[] {3,4,5,4};
                row = 4;
                break;
            case 4:
                row = 4;
        }

        walkDownAnimation = new Animation<>(animation_speed, getFrames(sprSheet,frames,row + 0,16,16,false));
        walkLeftAnimation = new Animation<>(animation_speed, getFrames(sprSheet,frames,row + 1,16,16,false));
        walkRightAnimation = new Animation<>(animation_speed, getFrames(sprSheet,frames,row + 2,16,16,false));
        walkUpAnimation = new Animation<>(animation_speed, getFrames(sprSheet,frames,row + 3,16,16,false));
        currentAnimation = walkDownAnimation;

    }
    public void setTextBox(String text, int height, int width, BitmapFont font)
    {
        canInteract = true;
        textBox = new TextBox(text,(int)worldX,(int)worldY+ 20,height,width,font);
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

        if(!idle){updatePath(delta);}
        if (canInteract) {textBox.setPosition((int)worldX,(int)worldY+spriteY+ 30);}

    }
    private void updatePath(float delta)
    {
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
        idle = false;
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
