package tests;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.main.Main;
import com.main.screens.TypingGame;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class TypingGameTest {
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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPlayGame() {
        typingGame.playGame();
    }

    @Test
    void testMakeUserGuess() {
        typingGame.makeUserGuess();
    }

    @Test
    void testDelay() {
        typingGame.delay(0, null);
    }

    @Test
    void testShow() {
        typingGame.show();
    }

    @Test
    void testRender() {
        when(displayText.draw(any(Batch.class), any(CharSequence.class), anyFloat(), anyFloat(), anyFloat(), anyInt(), anyBoolean())).thenReturn(new GlyphLayout());

        typingGame.render(0f);
    }

    @Test
    void testGenerateNumber() {
        int result = typingGame.generateNumber();
        Assertions.assertEquals(0, result);
    }

    @Test
    void testResize() {
        when(guessButton.getWidth()).thenReturn(0);
        when(guessButton.getHeight()).thenReturn(0);
        when(displayText.getData()).thenReturn(new BitmapFont.BitmapFontData(new FileHandle("fileName"), true));
        when(title.getWidth()).thenReturn(0);
        when(title.getHeight()).thenReturn(0);

        typingGame.resize(0, 0);
    }

    @Test
    void testPause() {
        typingGame.pause();
    }

    @Test
    void testResume() {
        typingGame.resume();
    }

    @Test
    void testHide() {
        typingGame.hide();
    }

    @Test
    void testDispose() {
        typingGame.dispose();
        verify(guessButton).dispose();
        verify(displayText).dispose();
        verify(title).dispose();
    }

    @Test
    void testKeyDown() {
        boolean result = typingGame.keyDown(0);
        Assertions.assertTrue(result);
    }

    @Test
    void testKeyUp() {
        boolean result = typingGame.keyUp(0);
        Assertions.assertTrue(result);
    }

    @Test
    void testKeyTyped() {
        boolean result = typingGame.keyTyped('a');
        Assertions.assertTrue(result);
    }

    @Test
    void testTouchDown() {
        boolean result = typingGame.touchDown(0, 0, 0, 0);
        Assertions.assertTrue(result);
    }

    @Test
    void testTouchUp() {
        boolean result = typingGame.touchUp(0, 0, 0, 0);
        Assertions.assertTrue(result);
    }

    @Test
    void testTouchCancelled() {
        boolean result = typingGame.touchCancelled(0, 0, 0, 0);
        Assertions.assertTrue(result);
    }

    @Test
    void testTouchDragged() {
        boolean result = typingGame.touchDragged(0, 0, 0);
        Assertions.assertTrue(result);
    }

    @Test
    void testMouseMoved() {
        boolean result = typingGame.mouseMoved(0, 0);
        Assertions.assertTrue(result);
    }

    @Test
    void testScrolled() {
        boolean result = typingGame.scrolled(0f, 0f);
        Assertions.assertTrue(result);
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme