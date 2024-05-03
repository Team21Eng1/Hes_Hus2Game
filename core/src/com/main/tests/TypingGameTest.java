package com.main.tests;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.main.Main;
import com.main.screens.TypingGame;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class TypingGameTest {
    @Mock
    Main game;
    @Mock
    Texture guessButton;
    @Mock
    BitmapFont displayText;
    @Mock
    Texture title;
    @InjectMocks
    TypingGame typingGame;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testPlayGame() throws Exception {
        typingGame.playGame();
    }

    @Test
    public void testMakeUserGuess() throws Exception {
        typingGame.makeUserGuess();
    }

    @Test
    public void testDelay() throws Exception {
        typingGame.delay(0, null);
    }

    @Test
    public void testShow() throws Exception {
        typingGame.show();
    }

    @Test
    public void testRender() throws Exception {
        when(displayText.draw(any(Batch.class), any(CharSequence.class), anyFloat(), anyFloat(), anyFloat(), anyInt(), anyBoolean())).thenReturn(new GlyphLayout());

        typingGame.render(0f);
    }

    @Test
    public void testGenerateNumber() throws Exception {
        int result = typingGame.generateNumber();
        Assert.assertEquals(0, result);
    }

    @Test
    public void testResize() throws Exception {
        when(guessButton.getWidth()).thenReturn(0);
        when(guessButton.getHeight()).thenReturn(0);
        when(displayText.getData()).thenReturn(new BitmapFont.BitmapFontData(new FileHandle("fileName"), true));
        when(title.getWidth()).thenReturn(0);
        when(title.getHeight()).thenReturn(0);

        typingGame.resize(0, 0);
    }

    @Test
    public void testPause() throws Exception {
        typingGame.pause();
    }

    @Test
    public void testResume() throws Exception {
        typingGame.resume();
    }

    @Test
    public void testHide() throws Exception {
        typingGame.hide();
    }

    @Test
    public void testDispose() throws Exception {
        typingGame.dispose();
        verify(guessButton).dispose();
        verify(displayText).dispose();
        verify(title).dispose();
    }

    @Test
    public void testKeyDown() throws Exception {
        boolean result = typingGame.keyDown(0);
        Assert.assertEquals(true, result);
    }

    @Test
    public void testKeyUp() throws Exception {
        boolean result = typingGame.keyUp(0);
        Assert.assertEquals(true, result);
    }

    @Test
    public void testKeyTyped() throws Exception {
        boolean result = typingGame.keyTyped('a');
        Assert.assertEquals(true, result);
    }

    @Test
    public void testTouchDown() throws Exception {
        boolean result = typingGame.touchDown(0, 0, 0, 0);
        Assert.assertEquals(true, result);
    }

    @Test
    public void testTouchUp() throws Exception {
        boolean result = typingGame.touchUp(0, 0, 0, 0);
        Assert.assertEquals(true, result);
    }

    @Test
    public void testTouchCancelled() throws Exception {
        boolean result = typingGame.touchCancelled(0, 0, 0, 0);
        Assert.assertEquals(true, result);
    }

    @Test
    public void testTouchDragged() throws Exception {
        boolean result = typingGame.touchDragged(0, 0, 0);
        Assert.assertEquals(true, result);
    }

    @Test
    public void testMouseMoved() throws Exception {
        boolean result = typingGame.mouseMoved(0, 0);
        Assert.assertEquals(true, result);
    }

    @Test
    public void testScrolled() throws Exception {
        boolean result = typingGame.scrolled(0f, 0f);
        Assert.assertEquals(true, result);
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme