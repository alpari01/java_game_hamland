package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.GameClient;
import com.mygdx.game.objects.Button;

public class SettingsScreen implements Screen {

    private final GameClient gameClient;
    private SpriteBatch batch;

    // Textures
    private Texture returnButtonTexture;
    private Texture returnButtonWhiteTexture;
    private Texture soundUpButtonTexture;
    private Texture soundMuteButtonTexture;
    private Texture musicUpButtonTexture;
    private Texture musicMuteButtonTexture;

    // Objects
    private Button returnButton;
    private Button returnButtonWhite;
    private Button soundUpButton;
    private Button soundMuteButton;
    private Button musicUpButton;
    private Button musicMuteButton;

    // Music
    private boolean isMusicUp = true;
    private boolean isSoundUp = true;

    public SettingsScreen(GameClient gameClient) {
        this.gameClient = gameClient;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();

        // Textures
        returnButtonTexture = new Texture("button_place.png");
        returnButtonWhiteTexture = new Texture("button_place.png");
        soundUpButtonTexture = new Texture("sound_up.png");
        soundMuteButtonTexture = new Texture("sound_mute.png");
        musicUpButtonTexture = new Texture("music_up.png");
        musicMuteButtonTexture = new Texture("music_mute.png");

        // Button objects
        returnButton = new Button(returnButtonTexture, 60, 60, 100f, 100f);
        returnButtonWhite = new Button(returnButtonWhiteTexture, 60, 60, 100f, 100f);
        soundUpButton = new Button(soundUpButtonTexture, (float) GameClient.WIDTH / 2 - 100,(float) GameClient.HEIGHT / 2, 100f,100f);
        soundMuteButton = new Button(soundMuteButtonTexture, (float) GameClient.WIDTH / 2 - 100,(float) GameClient.HEIGHT / 2, 100f,100f);
        musicUpButton = new Button(musicUpButtonTexture, (float) GameClient.WIDTH / 2 + 100,(float) GameClient.HEIGHT / 2, 100f,100f);
        musicMuteButton = new Button(musicMuteButtonTexture, (float) GameClient.WIDTH / 2 + 100,(float) GameClient.HEIGHT / 2, 100f,100f);
    }

    @Override
    public void render(float delta) {

        // Update screen white background
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.graphics.setTitle("Settings (" + Gdx.graphics.getFramesPerSecond() + "FPS)");

        batch.begin(); // start

            // if mouse X-coordinate and Y-coordinate on the return button
        if (Gdx.input.getX() > returnButton.polygon.getX() && Gdx.input.getX() < returnButton.polygon.getX() + 100f &&
                GameClient.HEIGHT - Gdx.input.getY() > returnButton.polygon.getY() && GameClient.HEIGHT - Gdx.input.getY() < returnButton.polygon.getY() + 100f) {
            returnButton.draw(batch); // draw in color selected button

            // if click - set screen to MenuScreen
            if (Gdx.input.isTouched()) {
                gameClient.setScreen(new MenuScreen(gameClient));
            }
        }

            // if mouse X-coordinate and Y-coordinate on the sound button
        if (Gdx.input.getX() > soundUpButton.polygon.getX() && Gdx.input.getX() < soundUpButton.polygon.getX() + 100f &&
                GameClient.HEIGHT - Gdx.input.getY() > soundUpButton.polygon.getY() && GameClient.HEIGHT - Gdx.input.getY() < soundUpButton.polygon.getY() + 100f) {

            // if click - change boolean value
            if (Gdx.input.justTouched()) {
                isSoundUp = !isSoundUp;
            }
        }

            // if mouse X-coordinate and Y-coordinate on the music button
        if (Gdx.input.getX() > musicUpButton.polygon.getX() && Gdx.input.getX() < musicUpButton.polygon.getX() + 100f &&
                GameClient.HEIGHT - Gdx.input.getY() > musicUpButton.polygon.getY() && GameClient.HEIGHT - Gdx.input.getY() < musicUpButton.polygon.getY() + 100f) {

            // if click - change boolean value
            if (Gdx.input.justTouched()) {
                isMusicUp = !isMusicUp;
            }
        }

        // draw sound/music buttons
        if (isSoundUp) {
            soundUpButton.draw(batch);
        } else {
            soundMuteButton.draw(batch);
        }

        if (isMusicUp) {
            musicUpButton.draw(batch);
        } else {
            musicMuteButton.draw(batch);
        }

        // draw transparent return button
        returnButtonWhite.draw(batch);

        batch.end(); //end

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

        // Clear memory when game is off
        batch.dispose();
        returnButtonTexture.dispose();
        returnButtonWhiteTexture.dispose();
    }
}
