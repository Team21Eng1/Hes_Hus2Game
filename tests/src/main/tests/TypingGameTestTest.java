package tests;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.main.Main;
import com.main.screens.TypingGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class TypingGameTestTest {
    @Mock
    Main game;
    @Mock
    Texture guessButton;
    @Mock
    BitmapFont displayText;
    @Mock
    Texture title;
    @Mock
    TypingGame typingGame;
    @InjectMocks
    TypingGameTest typingGameTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testSetUp() {
        typingGameTest.setUp();
    }

    @Test
    void testTestPlayGame() {
        typingGameTest.testPlayGame();
        verify(typingGame).playGame();
    }

    @Test
    void testTestMakeUserGuess() {
        typingGameTest.testMakeUserGuess();
        verify(typingGame).makeUserGuess();
    }

    @Test
    void testTestDelay() {
        typingGameTest.testDelay();
        verify(typingGame).delay(anyInt(), any(Runnable.class));
    }

    @Test
    void testTestShow() {
        typingGameTest.testShow();
        verify(typingGame).show();
    }

    @Test
    void testTestRender() {
        when(displayText.draw(any(Batch.class), any(CharSequence.class), anyFloat(), anyFloat(), anyFloat(), anyInt(), anyBoolean())).thenReturn(new GlyphLayout());

        typingGameTest.testRender();
        verify(typingGame).render(anyFloat());
    }

    @Test
    void testTestGenerateNumber() {
        when(typingGame.generateNumber()).thenReturn(0);

        typingGameTest.testGenerateNumber();
    }

    @Test
    void testTestResize() {
        when(guessButton.getWidth()).thenReturn(0);
        when(guessButton.getHeight()).thenReturn(0);
        when(displayText.getData()).thenReturn(new BitmapFont.BitmapFontData(new FileHandle("fileName"), true));
        when(title.getWidth()).thenReturn(0);
        when(title.getHeight()).thenReturn(0);

        typingGameTest.testResize();
        verify(typingGame).resize(anyInt(), anyInt());
    }

    @Test
    void testTestPause() {
        typingGameTest.testPause();
        verify(typingGame).pause();
    }

    @Test
    void testTestResume() {
        typingGameTest.testResume();
        verify(typingGame).resume();
    }

    @Test
    void testTestHide() {
        typingGameTest.testHide();
        verify(typingGame).hide();
    }

    @Test
    void testTestDispose() {
        typingGameTest.testDispose();
        verify(typingGame).dispose();
    }

    @Test
    void testTestKeyDown() {
        when(typingGame.keyDown(anyInt())).thenReturn(true);

        typingGameTest.testKeyDown();
    }

    @Test
    void testTestKeyUp() {
        when(typingGame.keyUp(anyInt())).thenReturn(true);

        typingGameTest.testKeyUp();
    }

    @Test
    void testTestKeyTyped() {
        when(typingGame.keyTyped(anyChar())).thenReturn(true);

        typingGameTest.testKeyTyped();
    }

    @Test
    void testTestTouchDown() {
        when(typingGame.touchDown(anyInt(), anyInt(), anyInt(), anyInt())).thenReturn(true);

        typingGameTest.testTouchDown();
    }

    @Test
    void testTestTouchUp() {
        when(typingGame.touchUp(anyInt(), anyInt(), anyInt(), anyInt())).thenReturn(true);

        typingGameTest.testTouchUp();
    }

    @Test
    void testTestTouchCancelled() {
        when(typingGame.touchCancelled(anyInt(), anyInt(), anyInt(), anyInt())).thenReturn(true);

        typingGameTest.testTouchCancelled();
    }

    @Test
    void testTestTouchDragged() {
        when(typingGame.touchDragged(anyInt(), anyInt(), anyInt())).thenReturn(true);

        typingGameTest.testTouchDragged();
    }

    @Test
    void testTestMouseMoved() {
        when(typingGame.mouseMoved(anyInt(), anyInt())).thenReturn(true);

        typingGameTest.testMouseMoved();
    }

    @Test
    void testTestScrolled() {
        when(typingGame.scrolled(anyFloat(), anyFloat())).thenReturn(true);

        typingGameTest.testScrolled();
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme