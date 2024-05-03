package com.main.tests;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.main.Main;
import com.main.screens.MainControlScreen;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({MainControlScreen.class})
@PowerMockIgnore("javax.management.*")
public class MainControlScreenTest {
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
    MainControlScreen mainControlScreen;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testShow() throws Exception {
        mainControlScreen.show();
    }

    @Test
    public void testRender() throws Exception {
        when(font.draw(any(Batch.class), any(CharSequence.class), anyFloat(), anyFloat())).thenReturn(new GlyphLayout());
        when(font.draw(any(Batch.class), any(CharSequence.class), anyFloat(), anyFloat(), anyFloat(), anyInt(), anyBoolean())).thenReturn(new GlyphLayout());
        mainControlScreen.render(0f);
    }

    @Test
    public void testTouchDown() throws Exception {
        boolean result = mainControlScreen.touchDown(0, 0, 0, 0);
        Assert.assertEquals(true, result);

    }

    @Test
    public void testKeyDown() throws Exception {
        boolean result = mainControlScreen.keyDown(0);
        Assert.assertEquals(true, result);

    }

    @Test
    public void testKeyUp() throws Exception {
        boolean result = mainControlScreen.keyUp(0);
        Assert.assertEquals(true, result);

    }

    @Test
    public void testKeyTyped() throws Exception {
        boolean result = mainControlScreen.keyTyped('a');
        Assert.assertEquals(true, result);

    }

    @Test
    public void testTouchUp() throws Exception {
        boolean result = mainControlScreen.touchUp(0, 0, 0, 0);
        Assert.assertEquals(true, result);

    }

    @Test
    public void testTouchCancelled() throws Exception {
        boolean result = mainControlScreen.touchCancelled(0, 0, 0, 0);
        Assert.assertEquals(true, result);

    }

    @Test
    public void testTouchDragged() throws Exception {
        boolean result = mainControlScreen.touchDragged(0, 0, 0);
        Assert.assertEquals(true, result);

    }

    @Test
    public void testMouseMoved() throws Exception {
        boolean result = mainControlScreen.mouseMoved(0, 0);
        Assert.assertEquals(true, result);

    }

    @Test
    public void testScrolled() throws Exception {
        boolean result = mainControlScreen.scrolled(0f, 0f);
        Assert.assertEquals(true, result);

    }

    @Test
    public void testResize() throws Exception {
        when(font.getData()).thenReturn(new BitmapFont.BitmapFontData(new FileHandle("fileName"), true));
        mainControlScreen.resize(0, 0);
    }

    @Test
    public void testPause() throws Exception {
        mainControlScreen.pause();
    }

    @Test
    public void testResume() throws Exception {
        mainControlScreen.resume();
    }

    @Test
    public void testHide() throws Exception {
        mainControlScreen.hide();
    }

    @Test
    public void testDispose() throws Exception {
        mainControlScreen.dispose();
        verify(font).dispose();
        verify(backButton).dispose();
        verify(controlLabel).dispose();
        verify(controls).dispose();
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme