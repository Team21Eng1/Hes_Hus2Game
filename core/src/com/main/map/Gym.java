package com.main.map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.main.Main;
import com.main.entity.Entity;
import com.main.entity.Player;
import com.main.entity.Student;
import com.main.utils.ActivityType;
import com.main.utils.ScreenType;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.main.Main;
import com.main.entity.Entity;
import com.main.entity.Player;
import com.main.entity.Student;
import com.main.utils.ScreenType;

import java.io.File;
import java.util.ArrayList;

public class Gym extends GameMap{
    /**
     * Constructs a GameMap with an orthographic camera.
     *
     * @param camera   The camera used to view the map.
     * @param Filename
     */
    Student student;
    Player player;
    Main game;
    public Gym(Main game, OrthographicCamera camera) {
        super(game,camera, "map/interior_maps/cs.tmx");
        this.game = game;
        //setRoom(camera);
        //activityScreen = ScreenType.TYPING_MINI_GAME;
        activity = ActivityType.EXCERCISE;
    }

    public void setRoom(OrthographicCamera camera)
    {
//        player = new Player(game,this, camera,80,80);
//        player.camFollow = false;
//
//        student = new Student((GameMap) this, 50,50);
//        student.setPath(new Vector2[] {new Vector2(50,50),new Vector2(100,50),new Vector2(100,100)});
//        entities.add(student);
//
//        entities.add(player);
//        for (Entity e :entities) {
//            e.collisionHandler.clearCollisionLayers();
//            e.collisionHandler.addCollisionLayers("walls");
//        }
    }
    public void renderEntities(SpriteBatch batch){

//        for (Entity e :entities) {
//            batch.draw(e.getCurrentFrame(),e.worldX,e.worldY,e.getSpriteX(),e.getSpriteY());
//        }
    }
//    public void update(float delta)
//    {
//        for (Entity e :entities) {
//            e.update(delta);
//        }
//    }

    public boolean PlayerStudent(Entity e)
    {
        if (new Vector2(player.worldX,player.worldY).dst(e.worldX,e.worldY) < 20)
        {
            if (e == student)
            {
                activity = ActivityType.EXCERCISE;
                return true;
                }
        }
        return false;
    }

    public boolean interact()
    {

        return true;
    }




    public boolean playerDoor() {
        if (new Vector2(player.worldX,player.worldY).dst(40,40) < 30)
        {
            activityScreen = null;
            return true;
        }else return false;
    }
}

