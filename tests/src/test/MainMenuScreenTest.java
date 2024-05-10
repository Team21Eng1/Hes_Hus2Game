package test;

import com.badlogic.gdx.graphics.Texture;
import com.main.Main;
import com.main.screens.MainMenuScreen;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testShow() {
        mainMenuScreen.show();
    }

    @Test
    void testRender() {
        mainMenuScreen.render(0f);
    }

    @Test
    void testKeyDown() {
        boolean result = mainMenuScreen.keyDown(0);
        Assertions.assertTrue(result);
    }

    @Test
    void testKeyUp() {
        boolean result = mainMenuScreen.keyUp(0);
        Assertions.assertTrue(result);
    }

    @Test
    void testKeyTyped() {
        boolean result = mainMenuScreen.keyTyped('a');
        Assertions.assertTrue(result);
    }

    @Test
    void testTouchDown() {
        boolean result = mainMenuScreen.touchDown(0, 0, 0, 0);
        verify(heslingtonHustleLabel).dispose();
        verify(playButton).dispose();
        verify(controlsButton).dispose();
        verify(settingsButton).dispose();
        verify(exitButton).dispose();
        Assertions.assertTrue(result);
    }

    @Test
    void testTouchUp() {
        boolean result = mainMenuScreen.touchUp(0, 0, 0, 0);
        Assertions.assertTrue(result);
    }

    @Test
    void testTouchCancelled() {
        boolean result = mainMenuScreen.touchCancelled(0, 0, 0, 0);
        Assertions.assertTrue(result);
    }

    @Test
    void testTouchDragged() {
        boolean result = mainMenuScreen.touchDragged(0, 0, 0);
        Assertions.assertTrue(result);
    }

    @Test
    void testMouseMoved() {
        boolean result = mainMenuScreen.mouseMoved(0, 0);
        Assertions.assertTrue(result);
    }

    @Test
    void testScrolled() {
        boolean result = mainMenuScreen.scrolled(0f, 0f);
        Assertions.assertTrue(result);
    }

    @Test
    void testResize() {
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
    void testPause() {
        mainMenuScreen.pause();
    }

    @Test
    void testResume() {
        mainMenuScreen.resume();
    }

    @Test
    void testHide() {
        mainMenuScreen.hide();
    }

    @Test
    void testDispose() {
        mainMenuScreen.dispose();
        verify(heslingtonHustleLabel).dispose();
        verify(playButton).dispose();
        verify(controlsButton).dispose();
        verify(settingsButton).dispose();
        verify(exitButton).dispose();
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme