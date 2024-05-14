package com.main.tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.main.utils.AudioManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class AudioManagerTest {
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
        audioManager.musicVolUp();
        verify(bgMusic).setVolume(anyFloat());
        verify(upSound).setVolume(anyFloat());
        verify(downSound).setVolume(anyFloat());
        verify(buttonClickedSound).setVolume(anyFloat());
        verify(eatingSound).setVolume(anyFloat());
    }

    @Test
    void testMusicVolDown() {
        audioManager.musicVolDown();
        verify(bgMusic).setVolume(anyFloat());
        verify(upSound).setVolume(anyFloat());
        verify(downSound).setVolume(anyFloat());
        verify(buttonClickedSound).setVolume(anyFloat());
        verify(eatingSound).setVolume(anyFloat());
    }

    @Test
    void testSoundVolUp() {
        audioManager.soundVolUp();
        verify(bgMusic).setVolume(anyFloat());
        verify(upSound).setVolume(anyFloat());
        verify(downSound).setVolume(anyFloat());
        verify(buttonClickedSound).setVolume(anyFloat());
        verify(eatingSound).setVolume(anyFloat());
    }

    @Test
    void testSoundVolDown() {
        audioManager.soundVolDown();
        verify(bgMusic).setVolume(anyFloat());
        verify(upSound).setVolume(anyFloat());
        verify(downSound).setVolume(anyFloat());
        verify(buttonClickedSound).setVolume(anyFloat());
        verify(eatingSound).setVolume(anyFloat());
    }

    @Test
    void testUpSoundActivate() {
        when(bgMusic.isPlaying()).thenReturn(true);
        when(upSound.isPlaying()).thenReturn(true);
        when(downSound.isPlaying()).thenReturn(true);
        when(buttonClickedSound.isPlaying()).thenReturn(true);
        when(eatingSound.isPlaying()).thenReturn(true);

        audioManager.upSoundActivate();
        verify(bgMusic).play();
        verify(bgMusic).stop();
        verify(upSound).play();
        verify(upSound).stop();
        verify(downSound).play();
        verify(downSound).stop();
        verify(buttonClickedSound).play();
        verify(buttonClickedSound).stop();
        verify(eatingSound).play();
        verify(eatingSound).stop();
    }

    @Test
    void testDownSoundActivate() {
        when(bgMusic.isPlaying()).thenReturn(true);
        when(upSound.isPlaying()).thenReturn(true);
        when(downSound.isPlaying()).thenReturn(true);
        when(buttonClickedSound.isPlaying()).thenReturn(true);
        when(eatingSound.isPlaying()).thenReturn(true);

        audioManager.downSoundActivate();
        verify(bgMusic).play();
        verify(bgMusic).stop();
        verify(upSound).play();
        verify(upSound).stop();
        verify(downSound).play();
        verify(downSound).stop();
        verify(buttonClickedSound).play();
        verify(buttonClickedSound).stop();
        verify(eatingSound).play();
        verify(eatingSound).stop();
    }

    @Test
    void testButtonClickedSoundActivate() {
        when(bgMusic.isPlaying()).thenReturn(true);
        when(upSound.isPlaying()).thenReturn(true);
        when(downSound.isPlaying()).thenReturn(true);
        when(buttonClickedSound.isPlaying()).thenReturn(true);
        when(eatingSound.isPlaying()).thenReturn(true);

        audioManager.buttonClickedSoundActivate();
        verify(bgMusic).play();
        verify(bgMusic).stop();
        verify(upSound).play();
        verify(upSound).stop();
        verify(downSound).play();
        verify(downSound).stop();
        verify(buttonClickedSound).play();
        verify(buttonClickedSound).stop();
        verify(eatingSound).play();
        verify(eatingSound).stop();
    }

    @Test
    void testEatingSoundActivate() {
        when(bgMusic.isPlaying()).thenReturn(true);
        when(upSound.isPlaying()).thenReturn(true);
        when(downSound.isPlaying()).thenReturn(true);
        when(buttonClickedSound.isPlaying()).thenReturn(true);
        when(eatingSound.isPlaying()).thenReturn(true);

        audioManager.eatingSoundActivate();
        verify(bgMusic).play();
        verify(bgMusic).stop();
        verify(upSound).play();
        verify(upSound).stop();
        verify(downSound).play();
        verify(downSound).stop();
        verify(buttonClickedSound).play();
        verify(buttonClickedSound).stop();
        verify(eatingSound).play();
        verify(eatingSound).stop();
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme