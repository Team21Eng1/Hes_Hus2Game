package com.main.tests;

import com.badlogic.gdx.graphics.Texture;
import com.main.Main;
import com.main.screens.MainSettingsScreen;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

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
    MainSettingsScreen mainSettingsScreen = new MainSettingsScreen(game);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testShow() {
        mainSettingsScreen.show();
    }

    @Test
    void testRender() {
        mainSettingsScreen.render(0f);
    }

    @Test
    void testKeyDown() {
        boolean result = mainSettingsScreen.keyDown(0);
        Assertions.assertTrue(result);
    }

    @Test
    void testKeyUp() {
        boolean result = mainSettingsScreen.keyUp(0);
        Assertions.assertTrue(result);
    }

    @Test
    void testKeyTyped() {
        boolean result = mainSettingsScreen.keyTyped('a');
        Assertions.assertTrue(result);
    }
    @Test
    void testTouchUp() {
        boolean result = mainSettingsScreen.touchUp(0, 0, 0, 0);
        Assertions.assertTrue(result);
    }

    @Test
    void testTouchCancelled() {
        boolean result = mainSettingsScreen.touchCancelled(0, 0, 0, 0);
        Assertions.assertTrue(result);
    }

    @Test
    void testTouchDragged() {
        boolean result = mainSettingsScreen.touchDragged(0, 0, 0);
        Assertions.assertTrue(result);
    }

    @Test
    void testMouseMoved() {
        boolean result = mainSettingsScreen.mouseMoved(0, 0);
        Assertions.assertTrue(result);
    }

    @Test
    void testScrolled() {
        boolean result = mainSettingsScreen.scrolled(0f, 0f);
        Assertions.assertTrue(result);
    }

    @Test
    void testResize() {
        mainSettingsScreen.resize(0, 0);
    }

    @Test
    void testPause() {
        mainSettingsScreen.pause();
    }

    @Test
    void testResume() {
        mainSettingsScreen.resume();
    }

    @Test
    void testHide() {
        mainSettingsScreen.hide();
    }

    @Test
    void testDispose() {
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