package com.main.tests;

import com.badlogic.gdx.graphics.Texture;
import com.main.Main;
import com.main.screens.MainMenuScreen;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class MainMenuScreenTest {
    @Mock
    Main game;
    @Mock
    Texture heslingtonHustleLabel;
    @Mock
    Texture playButton;
    @Mock
    Texture controlsButton;
    @Mock
    Texture settingsButton;
    @Mock
    Texture exitButton;
    @InjectMocks
    MainMenuScreen mainMenuScreen;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testShow() throws Exception {
        mainMenuScreen.show();
    }

    @Test
    public void testRender() throws Exception {
        mainMenuScreen.render(0f);
    }

    @Test
    public void testKeyDown() throws Exception {
        boolean result = mainMenuScreen.keyDown(0);
        Assert.assertEquals(true, result);
    }

    @Test
    public void testKeyUp() throws Exception {
        boolean result = mainMenuScreen.keyUp(0);
        Assert.assertEquals(true, result);
    }

    @Test
    public void testKeyTyped() throws Exception {
        boolean result = mainMenuScreen.keyTyped('a');
        Assert.assertEquals(true, result);
    }

    @Test
    public void testTouchDown() throws Exception {
        boolean result = mainMenuScreen.touchDown(0, 0, 0, 0);
        verify(heslingtonHustleLabel).dispose();
        verify(playButton).dispose();
        verify(controlsButton).dispose();
        verify(settingsButton).dispose();
        verify(exitButton).dispose();
        Assert.assertEquals(true, result);
    }

    @Test
    public void testTouchUp() throws Exception {
        boolean result = mainMenuScreen.touchUp(0, 0, 0, 0);
        Assert.assertEquals(true, result);
    }

    @Test
    public void testTouchCancelled() throws Exception {
        boolean result = mainMenuScreen.touchCancelled(0, 0, 0, 0);
        Assert.assertEquals(true, result);
    }

    @Test
    public void testTouchDragged() throws Exception {
        boolean result = mainMenuScreen.touchDragged(0, 0, 0);
        Assert.assertEquals(true, result);
    }

    @Test
    public void testMouseMoved() throws Exception {
        boolean result = mainMenuScreen.mouseMoved(0, 0);
        Assert.assertEquals(true, result);
    }

    @Test
    public void testScrolled() throws Exception {
        boolean result = mainMenuScreen.scrolled(0f, 0f);
        Assert.assertEquals(true, result);
    }

    @Test
    public void testResize() throws Exception {
        when(heslingtonHustleLabel.getWidth()).thenReturn(0);
        when(heslingtonHustleLabel.getHeight()).thenReturn(0);
        when(playButton.getWidth()).thenReturn(0);
        when(playButton.getHeight()).thenReturn(0);
        when(controlsButton.getWidth()).thenReturn(0);
        when(controlsButton.getHeight()).thenReturn(0);
        when(settingsButton.getWidth()).thenReturn(0);
        when(settingsButton.getHeight()).thenReturn(0);
        when(exitButton.getWidth()).thenReturn(0);
        when(exitButton.getHeight()).thenReturn(0);

        mainMenuScreen.resize(0, 0);
    }

    @Test
    public void testPause() throws Exception {
        mainMenuScreen.pause();
    }

    @Test
    public void testResume() throws Exception {
        mainMenuScreen.resume();
    }

    @Test
    public void testHide() throws Exception {
        mainMenuScreen.hide();
    }

    @Test
    public void testDispose() throws Exception {
        mainMenuScreen.dispose();
        verify(heslingtonHustleLabel).dispose();
        verify(playButton).dispose();
        verify(controlsButton).dispose();
        verify(settingsButton).dispose();
        verify(exitButton).dispose();
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme