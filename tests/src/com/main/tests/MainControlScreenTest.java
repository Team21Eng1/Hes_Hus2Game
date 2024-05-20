package com.main.tests;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.main.Main;
import com.main.screens.MainControlScreen;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

public class MainControlScreenTest extends LibgdxUnitTest{
    @Mock
    Main game;
    @Mock
    BitmapFont font;
    @Mock
    Texture backButton;
    @Mock
    Texture controlLabel;
    @Mock
    Texture controls;
    @InjectMocks
    MainControlScreen mainControlScreen = new MainControlScreen(game);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testShow() {
        mainControlScreen.show();
    }

    @Test
    void testRender() {
        when(font.draw(any(Batch.class), any(CharSequence.class), anyFloat(), anyFloat())).thenReturn(new GlyphLayout());
        when(font.draw(any(Batch.class), any(CharSequence.class), anyFloat(), anyFloat(), anyFloat(), anyInt(), anyBoolean())).thenReturn(new GlyphLayout());

        mainControlScreen.render(0f);
    }

    @Test
    void testTouchDown() {
        boolean result = mainControlScreen.touchDown(0, 0, 0, 0);
        Assertions.assertTrue(result);
    }

    @Test
    void testKeyDown() {
        boolean result = mainControlScreen.keyDown(0);
        Assertions.assertTrue(result);
    }

    @Test
    void testKeyUp() {
        boolean result = mainControlScreen.keyUp(0);
        Assertions.assertTrue(result);
    }

    @Test
    void testKeyTyped() {
        boolean result = mainControlScreen.keyTyped('a');
        Assertions.assertTrue(result);
    }

    @Test
    void testTouchUp() {
        boolean result = mainControlScreen.touchUp(0, 0, 0, 0);
        Assertions.assertTrue(result);
    }

    @Test
    void testTouchCancelled() {
        boolean result = mainControlScreen.touchCancelled(0, 0, 0, 0);
        Assertions.assertTrue(result);
    }

    @Test
    void testTouchDragged() {
        boolean result = mainControlScreen.touchDragged(0, 0, 0);
        Assertions.assertTrue(result);
    }

    @Test
    void testMouseMoved() {
        boolean result = mainControlScreen.mouseMoved(0, 0);
        Assertions.assertTrue(result);
    }

    @Test
    void testScrolled() {
        boolean result = mainControlScreen.scrolled(0f, 0f);
        Assertions.assertTrue(result);
    }

    @Test
    void testResize() {
        when(font.getData()).thenReturn(new BitmapFont.BitmapFontData(new FileHandle("fileName"), true));

        mainControlScreen.resize(0, 0);
    }

    @Test
    void testPause() {
        mainControlScreen.pause();
    }

    @Test
    void testResume() {
        mainControlScreen.resume();
    }

    @Test
    void testHide() {
        mainControlScreen.hide();
    }

    @Test
    void testDispose() {
        mainControlScreen.dispose();
        verify(font).dispose();
        verify(backButton).dispose();
        verify(controlLabel).dispose();
        verify(controls).dispose();
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme