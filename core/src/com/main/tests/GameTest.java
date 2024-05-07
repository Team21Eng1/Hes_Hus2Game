package com.main.tests;

import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.main.Main;
import com.main.screens.MainGameScreen;
import com.main.utils.CollisionHandler;
import com.main.utils.EventManager;
import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    TiledMap tiledMap = new TiledMap();
    ArrayList<TiledMapTileLayer> collisionLayers = new ArrayList<>();
    // this needs default colision handler features
    CollisionHandler collisionHandler;
    MainGameScreen mockMainGameScreen;


    EventManager mockEventManager = new EventManager();
    @Test
    public void testKeyDown() throws Exception {
        boolean result = mockMainGameScreen.keyDown(0);
        Assert.assertTrue(result);
    }

    @Test
    public void testKeyUp() throws Exception {
        boolean result = mockMainGameScreen.keyUp(0);
        Assert.assertTrue(result);
    }

    @Test
    public void testKeyTyped() throws Exception {
        boolean result = mockMainGameScreen.keyTyped('a');
        Assert.assertTrue(result);
    }

    @Test
    public void testTouchUp() throws Exception {
        boolean result = mockMainGameScreen.touchUp(0, 0, 0, 0);
        Assert.assertTrue(result);
    }

    @Test
    public void testTouchCancelled() throws Exception {
        boolean result = mockMainGameScreen.touchCancelled(0, 0, 0, 0);
        Assert.assertTrue(result);
    }
    @Test
    public void testIsTouching() throws Exception {
        when(tiledMap.getLayers()).thenReturn(new MapLayers());

        boolean result = collisionHandler.isTouching("layerName", new Rectangle(0f, 0f, 0f, 0f));
        Assert.assertTrue(result);
    }

    @Test
    public void testGetSideHit() throws Exception {
        Vector2 result = collisionHandler.getSideHit(0f, 0f, new Rectangle(0f, 0f, 0f, 0f), 0);
        Assert.assertEquals(new Vector2(0f, 0f), result);
    }

    @Test
    public void testTileToRect() throws Exception {
        Rectangle result = collisionHandler.tileToRect(0, 0);
        Assert.assertEquals(new Rectangle(0f, 0f, 0f, 0f), result);
    }

    @Test
    public void testGetDirection() throws Exception {
        int result = collisionHandler.getDirection(0f, 0f, 0f, 0f);
        Assert.assertEquals(0, result);
    }

    @Test
    public void testCollidingSide() throws Exception {
        Vector2 result = collisionHandler.collidingSide(new Rectangle(0f, 0f, 0f, 0f), new Rectangle(0f, 0f, 0f, 0f), 0);
        Assert.assertEquals(new Vector2(0f, 0f), result);
    }

    @Test
    public void testAdjustPosStep() throws Exception {
        Vector2 result = collisionHandler.adjustPosStep(0f, 0f, 0f, 0f);
        Assert.assertEquals(new Vector2(0f, 0f), result);
    }

    @Test
    public void testAdjustPos() throws Exception {
        Vector2 result = collisionHandler.adjustPos(0f, 0f, 0f, 0f);
        Assert.assertEquals(new Vector2(0f, 0f), result);
    }
    @Test
    public void EnergyManagement(){
        // this is a test for if energy decreases when events occur
        mockMainGameScreen.touchDown();



    }

    @Test
    public void TimeManagement(){
        //This is a test for if time passes as required

    }

    @Test
    public void StudyCatchUp(){
        // this is a test for the required study catch up feature, if you do not sttudy one day you are forced to study for 2 days

    }

    @Test
    public void InteractionValidation(){
        // this checks if built in interaction works

    }

    @Test
    public void ScoreCalculation(){
        // this checks if the score is calculated
        List<String> testWeek = new ArrayList<>();
        assertTrue(mockEventManager.getScore(testString) > 0);

    }

    @Test
    public void LeaderboardValidation(){


    }

    @Test
    public void StreakAchievments(){
        List<List<String>> testString = new ArrayList<>();
        String eventTitle = "a";
        List<String> eventTitle1 = List.of("a", "b");
        List<String> eventTitle2 = List.of("a", "c");
        List<String> eventTitle3 = List.of("a", "c");

        List<List<String>> testString1 = List.of( eventTitle1, eventTitle2, eventTitle3);

        List<Integer> test1 = new List<Int>();
        assertEquals(mockEventManager.getStreak(testString, eventTitle), test1);
        //this checks if a system for streak achievments exists
    }

}
