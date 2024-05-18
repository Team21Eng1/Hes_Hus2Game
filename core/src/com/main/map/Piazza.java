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

public class Piazza extends GameMap{
    /**
     * Constructs a GameMap with an orthographic camera.
     *
     * @param camera   The camera used to view the map.
     * @param Filename
     */
    Student student1,student2;
    Main game;

    public Piazza(Main game, OrthographicCamera camera) {
        super(game,camera, "map/interior_maps/piazza.tmx");
        this.game = game;
        setRoom(camera);

    }
    public void setActivity(String activity)
    {
        switch (activity) {
            case "eat":
                activityScreen = ScreenType.SNAKE_MINI_GAME;
                break;
            case "study":
                activityScreen = ScreenType.TYPING_MINI_GAME;
        }


    }
    public void setRoom(OrthographicCamera camera)
    {
        player = new Player(game, this, camera,80,20);
        player.camFollow = false;
        student1 = new Student((GameMap) this, 200,200);
        student1.setTextBox("eat?", 10,50, font);
        student2 = new Student((GameMap) this, 50,100);
        student2.setTextBox("study?", 10,50, font);
        entities.add(student1);
        entities.add(student2);


        entities.add(player);
        for (Entity e :entities) {
            e.collisionHandler.clearCollisionLayers();
            e.collisionHandler.addCollisionLayers("tables","kitchen");
        }
    }
    public void renderEntities(SpriteBatch batch){

        for (Entity e :entities) {
            batch.draw(e.getCurrentFrame(),e.worldX,e.worldY,e.getSpriteX(),e.getSpriteY());
            if (e instanceof Student && ((Student) e).canInteract)
            {
                ((Student) e).textBox.render(batch);
            }
        }
    }
    public boolean PlayerStudent(Entity e)
    {
        if (new Vector2(player.worldX,player.worldY).dst(e.worldX,e.worldY) < 20)
        {
            if (e == student1)
            {
                activity = ActivityType.EAT;
                return true;
            } else if (e==student2)
            {
                activity = ActivityType.STUDY;
                return true;
            }
        }
        return false;
    }
    public boolean interact()
    {
        if (playerDoor()){return playerDoor();}
        if (PlayerStudent(student1)){return PlayerStudent(student1);}
        if (PlayerStudent(student2)){return PlayerStudent(student2);}
        return false;
    }



    public boolean playerDoor()
    {
        if (new Vector2(player.worldX,player.worldY).dst(80,0) < 10)
        {
            activity = ActivityType.EXIT;
            return true;
        }else return false;
    }

    public void update(float delta)
    {

        for (Entity e :entities) {
            if (e instanceof Player)
            {
                if (!lockMovement){e.update(delta);}
            } else {
                e.update(delta);
            }

        }
    }


}
