package test;

import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.main.utils.CollisionHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testAddCollisionLayers() {
        when(tiledMap.getLayers()).thenReturn(new MapLayers());
        when(collisionLayers.add(any(TiledMapTileLayer.class))).thenReturn(true);

        collisionHandler.addCollisionLayers("args");
    }

    @Test
    void testIsTouching() {
        when(tiledMap.getLayers()).thenReturn(new MapLayers());

        boolean result = collisionHandler.isTouching("layerName", new Rectangle(0f, 0f, 0f, 0f));
        Assertions.assertTrue(result);
    }

    @Test
    void testGetSideHit() {
        Vector2 result = collisionHandler.getSideHit(0f, 0f, new Rectangle(0f, 0f, 0f, 0f), 0);
        Assertions.assertEquals(new Vector2(0f, 0f), result);
    }

    @Test
    void testTileToRect() {
        Rectangle result = collisionHandler.tileToRect(0, 0);
        Assertions.assertEquals(new Rectangle(0f, 0f, 0f, 0f), result);
    }

    @Test
    void testGetDirection() {
        int result = collisionHandler.getDirection(0f, 0f, 0f, 0f);
        Assertions.assertEquals(0, result);
    }

    @Test
    void testCollidingSide() {
        Vector2 result = collisionHandler.collidingSide(new Rectangle(0f, 0f, 0f, 0f), new Rectangle(0f, 0f, 0f, 0f), 0);
        Assertions.assertEquals(new Vector2(0f, 0f), result);
    }

    @Test
    void testAdjustPosStep() {
        Vector2 result = collisionHandler.adjustPosStep(0f, 0f, 0f, 0f);
        Assertions.assertEquals(new Vector2(0f, 0f), result);
    }

    @Test
    void testAdjustPos() {
        Vector2 result = collisionHandler.adjustPos(0f, 0f, 0f, 0f);
        Assertions.assertEquals(new Vector2(0f, 0f), result);
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme