package com.main.tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.main.utils.AudioManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;


public class AudioManagerTest extends LibgdxUnitTest {
    @Mock
    Music bgMusic;
    @Mock
    Music upSound;
    @Mock
    Music downSound;
    @Mock
    Music buttonClickedSound;
    @Mock
    Music eatingSound;
    @InjectMocks
    AudioManager audioManager = new AudioManager();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testMusicVolUp() {
        int level1 = audioManager.getMusicLevel();
        audioManager.musicVolUp();
        int level2 = audioManager.getMusicLevel();
        Assertions.assertTrue(level1 <= level2 );

    }

    @Test
    void testMusicVolDown() {
        int level1 = audioManager.getMusicLevel();
        audioManager.musicVolDown();
        int level2 = audioManager.getMusicLevel();
        Assertions.assertTrue(level1 >= level2 );
    }

    @Test
    void testSoundVolUp() {
        int level1 = audioManager.getSoundLevel();
        audioManager.soundVolUp();
        int level2 = audioManager.getSoundLevel();
        Assertions.assertTrue(level1 <= level2 );
    }

    @Test
    void testSoundVolDown() {
        int level1 = audioManager.getSoundLevel();
        audioManager.soundVolDown();
        int level2 = audioManager.getSoundLevel();
        Assertions.assertTrue(level1 >= level2 );
    }

}