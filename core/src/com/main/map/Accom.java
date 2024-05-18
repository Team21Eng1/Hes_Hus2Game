package com.main.map;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.main.Main;
import com.main.entity.Entity;
import com.main.entity.Player;
import com.main.entity.Student;
import com.main.utils.ActivityType;
import com.main.utils.ScreenType;

import java.io.File;
import java.util.ArrayList;

public class Accom extends GameMap{
    /**
     * Constructs a GameMap with an orthographic camera.
     *
     * @param camera   The camera used to view the map.
     * @param Filename
     */
    Student student;
    Main game;
    public Accom(Main game, OrthographicCamera camera) {
        super(game,camera, "map/interior_maps/goodrickeaccom .tmx");
        this.game = game;
        setRoom(camera);
        activityScreen = null;

    }

    public void setRoom(OrthographicCamera camera)
    {
        player = new Player(game,this, camera,80,20);
        player.camFollow = false;
        student = new Student(this, 50,50);
        student.setPath(new Vector2[] {new Vector2(50,50),new Vector2(100,50),new Vector2(100,100)});
        entities.add(student);

        entities.add(player);
        for (Entity e :entities) {
            e.collisionHandler.clearCollisionLayers();
            e.collisionHandler.addCollisionLayers("collision");
        }
    }
    public void renderEntities(SpriteBatch batch){

        for (Entity e :entities) {
            batch.draw(e.getCurrentFrame(),e.worldX,e.worldY,e.getSpriteX(),e.getSpriteY());
        }
    }

    public boolean interact()
    {
        if (playerDoor()){return playerDoor();}
        return false;
    }



    public boolean playerDoor()
    {
        if (new Vector2(player.worldX,player.worldY).dst(40,0) < 10)
        {
            activity = ActivityType.EXCERCISE;
            return true;
        }else return false;
    }
    public boolean playerBed()
    {
        if (new Vector2(player.worldX,player.worldY).dst(80,112) < 20)
        {
            activityScreen = null;
            return true;
        }else return false;
    }

    public void update(float delta)
    {
        for (Entity e :entities) {
            e.update(delta);
        }
    }


}
