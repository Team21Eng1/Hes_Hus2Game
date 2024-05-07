package tests;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.main.Main;
import com.main.entity.Player;
import com.main.map.GameMap;
import com.main.utils.CollisionHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.*;

class MainGameScreenTest {
    @Mock
    Color shader;
    @Mock
    Player player;
    @Mock
    BitmapFont font;
    @Mock
    BitmapFont popupFont;
    @Mock
    BitmapFont durationFont;
    @Mock
    GameMap gameMap;
    @Mock
    OrthographicCamera camera;
    @Mock
    ShapeRenderer shapeRenderer;
    @Mock
    Main game;
    @Mock
    Texture menuButton;
    @Mock
    Texture popupMenu;
    @Mock
    Texture durationUpButton;
    @Mock
    Texture durationDownButton;
    @Mock
    Texture menuBackButton;
    @Mock
    Texture menuStudyButton;
    @Mock
    Texture menuSleepButton;
    @Mock
    Texture menuGoButton;
    @Mock
    Texture durationMenuBackground;
    @Mock
    Texture counterBackground;
    @Mock
    Texture energyBar;
    @Mock
    List<String> activities;
    @InjectMocks
    MainGameScreen mainGameScreen;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testRender() {
        when(player.getCurrentFrame()).thenReturn(new TextureRegion(new Texture(new FileHandle("fileName"), Pixmap.Format.Alpha, true), 0, 0, 0, 0));
        when(player.getCollisionHandler()).thenReturn(new CollisionHandler(null, 0, 0, 0f, 0f, 0f, 0f));
        when(player.getHitBox()).thenReturn(new Rectangle(0f, 0f, 0f, 0f));
        when(font.draw(any(Batch.class), any(CharSequence.class), anyFloat(), anyFloat())).thenReturn(new GlyphLayout());
        when(font.draw(any(Batch.class), any(CharSequence.class), anyFloat(), anyFloat(), anyFloat(), anyInt(), anyBoolean())).thenReturn(new GlyphLayout());
        when(font.getDescent()).thenReturn(0f);
        when(popupFont.draw(any(Batch.class), any(CharSequence.class), anyFloat(), anyFloat())).thenReturn(new GlyphLayout());
        when(popupFont.draw(any(Batch.class), any(CharSequence.class), anyFloat(), anyFloat(), anyFloat(), anyInt(), anyBoolean())).thenReturn(new GlyphLayout());
        when(popupFont.getDescent()).thenReturn(0f);
        when(durationFont.draw(any(Batch.class), any(CharSequence.class), anyFloat(), anyFloat())).thenReturn(new GlyphLayout());
        when(durationFont.draw(any(Batch.class), any(CharSequence.class), anyFloat(), anyFloat(), anyFloat(), anyInt(), anyBoolean())).thenReturn(new GlyphLayout());
        when(durationFont.getDescent()).thenReturn(0f);
        when(gameMap.getWidth()).thenReturn(0);
        when(gameMap.getHeight()).thenReturn(0);
        when(camera.project(any(Vector3.class))).thenReturn(new Vector3(0f, 0f, 0f));

        mainGameScreen.render(0f);
        verify(player).update(anyFloat());
        verify(player).setPos(anyFloat(), anyFloat());
        verify(player).setDirection(anyChar());
        verify(gameMap).render();
        verify(gameMap).update(anyFloat());
        verify(shapeRenderer).setColor(anyFloat(), anyFloat(), anyFloat(), anyFloat());
        verify(shapeRenderer).setProjectionMatrix(any(Matrix4.class));
        verify(shapeRenderer).begin(any(ShapeRenderer.ShapeType.class));
        verify(shapeRenderer).rect(anyFloat(), anyFloat(), anyFloat(), anyFloat());
        verify(shapeRenderer).end();
        verify(menuButton).Texture(anyString());
        verify(menuButton).dispose();
        verify(popupMenu).Texture(anyString());
        verify(popupMenu).dispose();
        verify(durationUpButton).Texture(anyString());
        verify(durationUpButton).dispose();
        verify(durationDownButton).Texture(anyString());
        verify(durationDownButton).dispose();
        verify(menuBackButton).Texture(anyString());
        verify(menuBackButton).dispose();
        verify(menuStudyButton).Texture(anyString());
        verify(menuStudyButton).dispose();
        verify(menuSleepButton).Texture(anyString());
        verify(menuSleepButton).dispose();
        verify(menuGoButton).Texture(anyString());
        verify(menuGoButton).dispose();
        verify(durationMenuBackground).Texture(anyString());
        verify(durationMenuBackground).dispose();
        verify(counterBackground).Texture(anyString());
        verify(counterBackground).dispose();
        verify(energyBar).Texture(anyString());
        verify(energyBar).dispose();
    }

    @Test
    void testShow() {
        mainGameScreen.show();
        verify(player).updateGender();
    }

    @Test
    void testSetEnergyBar() {
        Texture result = mainGameScreen.setEnergyBar();
        verify(menuButton).Texture(anyString());
        verify(popupMenu).Texture(anyString());
        verify(durationUpButton).Texture(anyString());
        verify(durationDownButton).Texture(anyString());
        verify(menuBackButton).Texture(anyString());
        verify(menuStudyButton).Texture(anyString());
        verify(menuSleepButton).Texture(anyString());
        verify(menuGoButton).Texture(anyString());
        verify(durationMenuBackground).Texture(anyString());
        verify(counterBackground).Texture(anyString());
        verify(energyBar).Texture(anyString());
        Assertions.assertEquals(new Texture(new FileHandle("fileName"), Pixmap.Format.Alpha, true), result);
    }

    @Test
    void testTouchDown() {
        when(camera.project(any(Vector3.class))).thenReturn(new Vector3(0f, 0f, 0f));
        when(activities.add(anyString())).thenReturn(true);

        boolean result = mainGameScreen.touchDown(0, 0, 0, 0);
        verify(menuButton).Texture(anyString());
        verify(menuButton).dispose();
        verify(popupMenu).Texture(anyString());
        verify(popupMenu).dispose();
        verify(durationUpButton).Texture(anyString());
        verify(durationUpButton).dispose();
        verify(durationDownButton).Texture(anyString());
        verify(durationDownButton).dispose();
        verify(menuBackButton).Texture(anyString());
        verify(menuBackButton).dispose();
        verify(menuStudyButton).Texture(anyString());
        verify(menuStudyButton).dispose();
        verify(menuSleepButton).Texture(anyString());
        verify(menuSleepButton).dispose();
        verify(menuGoButton).Texture(anyString());
        verify(menuGoButton).dispose();
        verify(durationMenuBackground).Texture(anyString());
        verify(durationMenuBackground).dispose();
        verify(counterBackground).Texture(anyString());
        verify(counterBackground).dispose();
        verify(energyBar).Texture(anyString());
        verify(energyBar).dispose();
        Assertions.assertTrue(result);
    }

    @Test
    void testResize() {
        when(font.getData()).thenReturn(new BitmapFont.BitmapFontData(new FileHandle("fileName"), true));
        when(popupFont.getData()).thenReturn(new BitmapFont.BitmapFontData(new FileHandle("fileName"), true));
        when(durationFont.getData()).thenReturn(new BitmapFont.BitmapFontData(new FileHandle("fileName"), true));

        mainGameScreen.resize(0, 0);
    }

    @Test
    void testPause() {
        mainGameScreen.pause();
    }

    @Test
    void testResume() {
        mainGameScreen.resume();
    }

    @Test
    void testHide() {
        mainGameScreen.hide();
    }

    @Test
    void testDispose() {
        mainGameScreen.dispose();
        verify(player).dispose();
        verify(font).dispose();
        verify(popupFont).dispose();
        verify(durationFont).dispose();
        verify(shapeRenderer).dispose();
        verify(menuButton).dispose();
        verify(popupMenu).dispose();
        verify(durationUpButton).dispose();
        verify(durationDownButton).dispose();
        verify(menuBackButton).dispose();
        verify(menuStudyButton).dispose();
        verify(menuSleepButton).dispose();
        verify(menuGoButton).dispose();
        verify(durationMenuBackground).dispose();
        verify(counterBackground).dispose();
        verify(energyBar).dispose();
    }

    @Test
    void testKeyDown() {
        boolean result = mainGameScreen.keyDown(0);
        Assertions.assertTrue(result);
    }

    @Test
    void testKeyUp() {
        boolean result = mainGameScreen.keyUp(0);
        Assertions.assertTrue(result);
    }

    @Test
    void testKeyTyped() {
        boolean result = mainGameScreen.keyTyped('a');
        Assertions.assertTrue(result);
    }

    @Test
    void testTouchUp() {
        boolean result = mainGameScreen.touchUp(0, 0, 0, 0);
        Assertions.assertTrue(result);
    }

    @Test
    void testTouchCancelled() {
        boolean result = mainGameScreen.touchCancelled(0, 0, 0, 0);
        Assertions.assertTrue(result);
    }

    @Test
    void testTouchDragged() {
        boolean result = mainGameScreen.touchDragged(0, 0, 0);
        Assertions.assertTrue(result);
    }

    @Test
    void testMouseMoved() {
        boolean result = mainGameScreen.mouseMoved(0, 0);
        Assertions.assertTrue(result);
    }

    @Test
    void testScrolled() {
        boolean result = mainGameScreen.scrolled(0f, 0f);
        Assertions.assertTrue(result);
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme