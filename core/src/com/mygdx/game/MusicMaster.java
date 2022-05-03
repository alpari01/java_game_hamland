package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class MusicMaster implements Music {

    private Music music;

    /**
     * Constructor.
     *
     * @param path path to the music file (internal).
     */
    public MusicMaster(String path) {

        this.music = Gdx.audio.newMusic(Gdx.files.internal(path));
    }

    @Override
    public void play() {
        this.music.play();
    }

    @Override
    public void pause() {
        this.music.pause();
    }

    @Override
    public void stop() {
        this.music.stop();
    }

    @Override
    public boolean isPlaying() {
        return this.music.isPlaying();
    }

    @Override
    public void setLooping(boolean state) {
        this.music.setLooping(state);
    }

    @Override
    public boolean isLooping() {
        return this.music.isLooping();
    }

    @Override
    public void setVolume(float volume) {
        this.music.setVolume(volume);
    }

    @Override
    public float getVolume() {
        return this.music.getVolume();
    }

    @Override
    public void setPan(float pan, float volume) {

    }

    @Override
    public void setPosition(float position) {

    }

    @Override
    public float getPosition() {
        return 0;
    }

    @Override
    public void dispose() {
        this.music.dispose();
    }

    @Override
    public void setOnCompletionListener(OnCompletionListener listener) {

    }
}
