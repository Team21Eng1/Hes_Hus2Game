package tests;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.main.Main;
import com.main.screens.EndScreen;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class EndScreenTest {
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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testRender() {
        when(font.draw(any(Batch.class), any(CharSequence.class), anyFloat(), anyFloat(), anyFloat(), anyInt(), anyBoolean())).thenReturn(new GlyphLayout());

        endScreen.render(0f);
    }

    @Test
    void testKeyDown() {
        boolean result = endScreen.keyDown(0);
        Assertions.assertTrue(result);
    }

    @Test
    void testKeyUp() {
        boolean result = endScreen.keyUp(0);
        Assertions.assertTrue(result);
    }

    @Test
    void testKeyTyped() {
        boolean result = endScreen.keyTyped('a');
        Assertions.assertTrue(result);
    }

    @Test
    void testTouchDown() {
        boolean result = endScreen.touchDown(0, 0, 0, 0);
        verify(game).setup();
        verify(playAgainButton).dispose();
        verify(exitButton).dispose();
        verify(font).dispose();
        Assertions.assertTrue(result);
    }

    @Test
    void testTouchUp() {
        boolean result = endScreen.touchUp(0, 0, 0, 0);
        Assertions.assertTrue(result);
    }

    @Test
    void testTouchCancelled() {
        boolean result = endScreen.touchCancelled(0, 0, 0, 0);
        Assertions.assertTrue(result);
    }

    @Test
    void testTouchDragged() {
        boolean result = endScreen.touchDragged(0, 0, 0);
        Assertions.assertTrue(result);
    }

    @Test
    void testMouseMoved() {
        boolean result = endScreen.mouseMoved(0, 0);
        Assertions.assertTrue(result);
    }

    @Test
    void testScrolled() {
        boolean result = endScreen.scrolled(0f, 0f);
        Assertions.assertTrue(result);
    }

    @Test
    void testShow() {
        endScreen.show();
    }

    @Test
    void testResize() {
        when(playAgainButton.getWidth()).thenReturn(0);
        when(playAgainButton.getHeight()).thenReturn(0);
        when(exitButton.getWidth()).thenReturn(0);
        when(exitButton.getHeight()).thenReturn(0);
        when(font.getData()).thenReturn(new BitmapFont.BitmapFontData(new FileHandle("fileName"), true));

        endScreen.resize(0, 0);
    }

    @Test
    void testPause() {
        endScreen.pause();
    }

    @Test
    void testResume() {
        endScreen.resume();
    }

    @Test
    void testHide() {
        endScreen.hide();
    }

    @Test
    void testDispose() {
        endScreen.dispose();
        verify(playAgainButton).dispose();
        verify(exitButton).dispose();
        verify(font).dispose();
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme