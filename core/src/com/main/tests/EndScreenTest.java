package com.main.tests;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.main.Main;
import com.main.screens.EndScreen;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class EndScreenTest {
    @Mock
    Main game;
    @Mock
    Texture playAgainButton;
    @Mock
    Texture exitButton;
    @Mock
    BitmapFont font;
    @InjectMocks
    EndScreen endScreen;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRender() throws Exception {
        when(font.draw(any(Batch.class), any(CharSequence.class), anyFloat(), anyFloat(), anyFloat(), anyInt(), anyBoolean())).thenReturn(new GlyphLayout());

        endScreen.render(0f);
    }

    @Test
    public void testKeyDown() throws Exception {
        boolean result = endScreen.keyDown(0);
        Assert.assertEquals(true, result);
    }

    @Test
    public void testKeyUp() throws Exception {
        boolean result = endScreen.keyUp(0);
        Assert.assertEquals(true, result);
    }

    @Test
    public void testKeyTyped() throws Exception {
        boolean result = endScreen.keyTyped('a');
        Assert.assertEquals(true, result);
    }

    @Test
    public void testTouchDown() throws Exception {
        boolean result = endScreen.touchDown(0, 0, 0, 0);
        verify(game).setup();
        verify(playAgainButton).dispose();
        verify(exitButton).dispose();
        verify(font).dispose();
        Assert.assertEquals(true, result);
    }

    @Test
    public void testTouchUp() throws Exception {
        boolean result = endScreen.touchUp(0, 0, 0, 0);
        Assert.assertEquals(true, result);
    }

    @Test
    public void testTouchCancelled() throws Exception {
        boolean result = endScreen.touchCancelled(0, 0, 0, 0);
        Assert.assertEquals(true, result);
    }

    @Test
    public void testTouchDragged() throws Exception {
        boolean result = endScreen.touchDragged(0, 0, 0);
        Assert.assertEquals(true, result);
    }

    @Test
    public void testMouseMoved() throws Exception {
        boolean result = endScreen.mouseMoved(0, 0);
        Assert.assertEquals(true, result);
    }

    @Test
    public void testScrolled() throws Exception {
        boolean result = endScreen.scrolled(0f, 0f);
        Assert.assertEquals(true, result);
    }

    @Test
    public void testShow() throws Exception {
        endScreen.show();
    }

    @Test
    public void testResize() throws Exception {
        when(playAgainButton.getWidth()).thenReturn(0);
        when(playAgainButton.getHeight()).thenReturn(0);
        when(exitButton.getWidth()).thenReturn(0);
        when(exitButton.getHeight()).thenReturn(0);
        when(font.getData()).thenReturn(new BitmapFont.BitmapFontData(new FileHandle("fileName"), true));

        endScreen.resize(0, 0);
    }

    @Test
    public void testPause() throws Exception {
        endScreen.pause();
    }

    @Test
    public void testResume() throws Exception {
        endScreen.resume();
    }

    @Test
    public void testHide() throws Exception {
        endScreen.hide();
    }

    @Test
    public void testDispose() throws Exception {
        endScreen.dispose();
        verify(playAgainButton).dispose();
        verify(exitButton).dispose();
        verify(font).dispose();
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme