package com.main.tests;

import com.badlogic.gdx.Screen;
import com.main.Main;
import com.main.utils.ScreenManager;
import com.main.utils.ScreenType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

public class ScreenManagerTest {
    @Mock
    Main game;
    @Mock
    Map<ScreenType, Screen> screensInMemory;
    @Mock
    Screen curScreen;

    @InjectMocks
    ScreenManager screenManager = new ScreenManager(game);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testKeepInMemory() {
        when(screensInMemory.put(any(ScreenType.class), any(Screen.class))).thenReturn(null);

        screenManager.keepInMemory(ScreenType.MAIN_MENU);
    }

    @Test
    void testClearMemory() {
        when(screensInMemory.values()).thenReturn(List.of(null));

        screenManager.clearMemory();
        verify(screensInMemory).clear();
        verify(curScreen).dispose();
    }

    @Test
    void testSetScreen() {
        when(screensInMemory.containsKey(any(Object.class))).thenReturn(true);
        when(screensInMemory.get(any(Object.class))).thenReturn(null);

        screenManager.setScreen(ScreenType.MAIN_MENU, "args");
        verify(game).setScreen(any(Screen.class));
        verify(curScreen).dispose();
    }

    @Test
    void testResize() {
        when(screensInMemory.values()).thenReturn(List.of(null));

        screenManager.resize(0, 0);
        verify(curScreen).resize(anyInt(), anyInt());
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme