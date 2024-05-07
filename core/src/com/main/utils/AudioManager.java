package com.main.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class AudioManager {

    private Music bgMusic,upSound,downSound;
    private Music buttonClickedSound,eatingSound;
    private int soundLevel = 4;
    // Music levels is quarters
    private int musicLevel = 4;

    private final float musicCap = 0.1f;
    /**
     * Constructor for GameMusic. Initializes and starts playing the game's background music at the maximum volume level.
     */
    public AudioManager() {

        //music
        bgMusic = Gdx.audio.newMusic(Gdx.files.internal("music_loop/Ludum Dare 30 - 01.ogg"));
        bgMusic.play();
        bgMusic.setVolume(musicCap); // Set volume to 100%
        bgMusic.setLooping(true);

        //sound effects
        upSound = Gdx.audio.newMusic(Gdx.files.internal("sfx/high_note.mp3"));
        downSound = Gdx.audio.newMusic(Gdx.files.internal("sfx/low_note.mp3"));
        buttonClickedSound = Gdx.audio.newMusic(Gdx.files.internal("sfx/button_press.mp3"));
        eatingSound = Gdx.audio.newMusic(Gdx.files.internal("sfx/eating_sound.wav"));
    }

    /**
     * Returns the current music volume level.
     * @return The current music volume level.
     */
    public int getMusicLevel(){
        return this.musicLevel;
    }
    public int getSoundLevel(){
        return this.soundLevel;
    }

    /**
     * Increments the music volume level by one, if it is not already at the maximum level. Adjusts the music playback volume accordingly.
     */
    public void musicVolUp() {
        if (musicLevel <= 3){ // Check if volume is not already at maximum
            musicLevel = musicLevel + 1;
            float floatingMusicLevel = (float) musicLevel;
            bgMusic.setVolume(floatingMusicLevel*25/100*musicCap);
        }
    }

    /**
     * Decrements the music volume level by one, if it is not already at the minimum level. Adjusts the music playback volume accordingly.
     */
    public void musicVolDown() {
        if (this.musicLevel >= 1){ // Check if volume is not already at minimum
            musicLevel = musicLevel - 1;
            float floatingMusicLevel = (float) musicLevel;
            bgMusic.setVolume(floatingMusicLevel*25/100*musicCap);
        }
    }

    public void soundVolUp() {
        if (soundLevel <= 3){ // Check if volume is not already at maximum
            soundLevel = soundLevel + 1;
            float floatingMusicLevel = (float) soundLevel;
            upSound.setVolume(floatingMusicLevel*25/100);
            downSound.setVolume(floatingMusicLevel*25/100);
            buttonClickedSound.setVolume(floatingMusicLevel*25/100);
            eatingSound.setVolume(floatingMusicLevel*25/100);
        }
    }

    /**
     * Decrements the music volume level by one, if it is not already at the minimum level. Adjusts the music playback volume accordingly.
     */
    public void soundVolDown() {
        if (this.soundLevel >= 1) { // Check if volume is not already at minimum
            soundLevel = soundLevel - 1;
            float floatingMusicLevel = (float) soundLevel;
            upSound.setVolume(floatingMusicLevel*25/100);
            downSound.setVolume(floatingMusicLevel*25/100);
            buttonClickedSound.setVolume(floatingMusicLevel*25/100);
        }
    }

    public void upSoundActivate(){
        if (upSound.isPlaying()){
            upSound.stop();
        }
        upSound.play();
    }

    /**
     * Plays the sound effect for decreasing the volume. Stops the sound if it is already playing before restarting it.
     */
    public void downSoundActivate(){
        if (downSound.isPlaying()){
            downSound.stop();
            downSound.play();
        }
        downSound.play();
    }

    /**
     * Plays the sound effect for a button click. Stops the sound if it is already playing before restarting it.
     */
    public void buttonClickedSoundActivate(){
        if (buttonClickedSound.isPlaying()){
            buttonClickedSound.stop();
        }
        buttonClickedSound.play();
    }

    /**
     * Plays the sound effect for increasing the volume. Stops the sound if it is already playing before restarting it.
     */
    public void eatingSoundActivate(){
        if (eatingSound.isPlaying()){
            eatingSound.stop();
        }
        eatingSound.play();
    }
}
