package com.main.tests;

import com.badlogic.gdx.graphics.Texture;
import com.main.Main;
import com.main.screens.MainSettingsScreen;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class MainSettingsScreenTest {
    @Mock
    Main game;
    @Mock
    Texture backButton;
    @Mock
    Texture settingsLabel;
    @Mock
    Texture musicUpButton;
    @Mock
    Texture musicDownButton;
    @Mock
    Texture musicLabel;
    @Mock
    Texture soundUpButton;
    @Mock
    Texture soundLabel;
    @Mock
    Texture soundDownButton;
    @Mock
    Texture musicBar;
    @Mock
    Texture soundBar;
    @Mock
    Texture boyButton;
    @Mock
    Texture girlButton;
    @InjectMocks
    MainSettingsScreen mainSettingsScreen;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testShow() throws Exception {
        mainSettingsScreen.show();
    }

    @Test
    public void testRender() throws Exception {
        mainSettingsScreen.render(0f);
    }

    @Test
    public void testKeyDown() throws Exception {
        boolean result = mainSettingsScreen.keyDown(0);
        Assert.assertEquals(true, result);
    }

    @Test
    public void testKeyUp() throws Exception {
        boolean result = mainSettingsScreen.keyUp(0);
        Assert.assertEquals(true, result);
    }

    @Test
    public void testKeyTyped() throws Exception {
        boolean result = mainSettingsScreen.keyTyped('a');
        Assert.assertEquals(true, result);
    }

    @Test
    public void testTouchDown() throws Exception {
        boolean result = mainSettingsScreen.touchDown(0, 0, 0, 0);
        verify(backButton).Texture(anyString());
        verify(backButton).dispose();
        verify(settingsLabel).Texture(anyString());
        verify(settingsLabel).dispose();
        verify(musicUpButton).Texture(anyString());
        verify(musicUpButton).dispose();
        verify(musicDownButton).Texture(anyString());
        verify(musicDownButton).dispose();
        verify(musicLabel).Texture(anyString());
        verify(musicLabel).dispose();
        verify(soundUpButton).Texture(anyString());
        verify(soundUpButton).dispose();
        verify(soundLabel).Texture(anyString());
        verify(soundLabel).dispose();
        verify(soundDownButton).Texture(anyString());
        verify(soundDownButton).dispose();
        verify(musicBar).Texture(anyString());
        verify(musicBar).dispose();
        verify(soundBar).Texture(anyString());
        verify(soundBar).dispose();
        verify(boyButton).Texture(anyString());
        verify(boyButton).dispose();
        verify(girlButton).Texture(anyString());
        verify(girlButton).dispose();
        Assert.assertEquals(true, result);
    }

    @Test
    public void testTouchUp() throws Exception {
        boolean result = mainSettingsScreen.touchUp(0, 0, 0, 0);
        Assert.assertEquals(true, result);
    }

    @Test
    public void testTouchCancelled() throws Exception {
        boolean result = mainSettingsScreen.touchCancelled(0, 0, 0, 0);
        Assert.assertEquals(true, result);
    }

    @Test
    public void testTouchDragged() throws Exception {
        boolean result = mainSettingsScreen.touchDragged(0, 0, 0);
        Assert.assertEquals(true, result);
    }

    @Test
    public void testMouseMoved() throws Exception {
        boolean result = mainSettingsScreen.mouseMoved(0, 0);
        Assert.assertEquals(true, result);
    }

    @Test
    public void testScrolled() throws Exception {
        boolean result = mainSettingsScreen.scrolled(0f, 0f);
        Assert.assertEquals(true, result);
    }

    @Test
    public void testResize() throws Exception {
        mainSettingsScreen.resize(0, 0);
    }

    @Test
    public void testPause() throws Exception {
        mainSettingsScreen.pause();
    }

    @Test
    public void testResume() throws Exception {
        mainSettingsScreen.resume();
    }

    @Test
    public void testHide() throws Exception {
        mainSettingsScreen.hide();
    }

    @Test
    public void testDispose() throws Exception {
        mainSettingsScreen.dispose();
        verify(backButton).dispose();
        verify(settingsLabel).dispose();
        verify(musicUpButton).dispose();
        verify(musicDownButton).dispose();
        verify(musicLabel).dispose();
        verify(soundUpButton).dispose();
        verify(soundLabel).dispose();
        verify(soundDownButton).dispose();
        verify(musicBar).dispose();
        verify(soundBar).dispose();
        verify(boyButton).dispose();
        verify(girlButton).dispose();
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme