package com.main.map;

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

public class Accom extends GameMap{
    /**
     * Constructs a GameMap with an orthographic camera.
     *
     * @param camera   The camera used to view the map.
     * @param Filename
     */
    Student student;
    Player player;
    Main game;
    public Accom(Main game, OrthographicCamera camera) {
        super(game,camera, "map/interior_maps/goodrickeaccom .tmx");
        this.game = game;
        setRoom(camera);
        activityScreen = null;

    }

    public void setRoom(OrthographicCamera camera)
    {
        player = new Player(game,this, camera);
        player.camFollow = false;
        student = new Student((GameMap) this, 50,50);
        student.setPath(new Vector2[] {new Vector2(50,50),new Vector2(100,50),new Vector2(100,100)});
        entities.add(student);

        entities.add(player);
        for (Entity e :entities) {
            e.collisionHandler.clearCollisionLayers();
            e.collisionHandler.addCollisionLayers("floor");
        }
    }
    public void renderEntities(SpriteBatch batch){

        for (Entity e :entities) {
            batch.draw(e.getCurrentFrame(),e.worldX,e.worldY,e.getSpriteX(),e.getSpriteY());
        }
    }
    public void update(float delta)
    {
        for (Entity e :entities) {
            e.update(delta);
        }
    }


}
