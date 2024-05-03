package com.main.tests;

import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.main.utils.CollisionHandler;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

public class CollisionHandlerTest {
    @Mock
    TiledMap tiledMap;
    @Mock
    ArrayList<TiledMapTileLayer> collisionLayers;
    @InjectMocks
    CollisionHandler collisionHandler;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddCollisionLayers() throws Exception {
        when(tiledMap.getLayers()).thenReturn(new MapLayers());
        when(collisionLayers.add(any(TiledMapTileLayer.class))).thenReturn(true);

        collisionHandler.addCollisionLayers("args");
    }

    @Test
    public void testIsTouching() throws Exception {
        when(tiledMap.getLayers()).thenReturn(new MapLayers());

        boolean result = collisionHandler.isTouching("layerName", new Rectangle(0f, 0f, 0f, 0f));
        Assert.assertEquals(true, result);
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
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme