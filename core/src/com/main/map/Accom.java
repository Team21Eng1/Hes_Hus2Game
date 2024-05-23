package com.main.map;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
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
        freeze = false;
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
            e.collisionHandler.addCollisionLayers("Collision");
        }
    }
    public void renderEntities(SpriteBatch batch){

        for (Entity e :entities) {
            batch.draw(e.getCurrentFrame(),e.worldX,e.worldY,e.getSpriteX(),e.getSpriteY());
        }
        if (playerBed())
        {
            font.draw(batch,"Sleep?",50,200,100, Align.center,true);
        }
    }

    public boolean interact()
    {
        if (playerDoor()){return playerDoor();}
        if (playerBed()){return playerBed();}
        return false;
    }



    public boolean playerDoor()
    {
        if (new Vector2(player.worldX,player.worldY).dst(50,15) < 25)
        {
            activity = ActivityType.EXIT;
            return true;
        }else return false;
    }
    public boolean playerBed()
    {
        if (new Vector2(player.worldX,player.worldY).dst(40,112) < 40)
        {
            activity = ActivityType.SLEEP;
            return true;
        }else return false;
    }

    public void update(float delta)
    {
        for (Entity e :entities) {

            if (e instanceof Player)
            {
                if (!freeze) e.update(delta);

            } else {e.update(delta);}
        }
    }


}
