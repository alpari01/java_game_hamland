package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.GameClient;
import com.mygdx.game.objects.Button;

public class MenuScreen implements Screen {

    private final GameClient gameClient;
    private SpriteBatch batch;

    // Textures
    private Texture playButtonTexture;
    private Texture playButtonWhiteTexture;
    private Texture settingsButtonTexture;
    private Texture settingsButtonWhiteTexture;
    private Texture quitButtonTexture;
    private Texture quitButtonWhiteTexture;

    // Objects
    private Button playButton;
    private Button playButtonWhite;
    private Button settingsButton;
    private Button settingsButtonWhite;
    private Button quitButton;
    private Button quitButtonWhite;

    public MenuScreen(GameClient gameClient) {
        this.gameClient = gameClient;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();

        // Textures
        playButtonTexture = new Texture("play_button_active.png");
        playButtonWhiteTexture = new Texture("play_button_inactive.png");
        settingsButtonTexture = new Texture("settings_button_active.png");
        settingsButtonWhiteTexture = new Texture("settings_button_inactive.png");
        quitButtonTexture = new Texture("quit_button_active.png");
        quitButtonWhiteTexture = new Texture("quit_button_inactive.png");

        // Button objects with position in the center of the screen
        playButton = new Button(playButtonTexture, (float) GameClient.WIDTH / 2 - 200,(float) GameClient.HEIGHT / 2, 100f,100f);
        playButtonWhite = new Button(playButtonWhiteTexture, (float) GameClient.WIDTH / 2 - 200,(float) GameClient.HEIGHT / 2, 100f,100f);
        settingsButton = new Button(settingsButtonTexture, (float) GameClient.WIDTH / 2,(float) GameClient.HEIGHT / 2, 100f,100f);
        settingsButtonWhite = new Button(settingsButtonWhiteTexture, (float) GameClient.WIDTH / 2,(float) GameClient.HEIGHT / 2, 100f,100f);
        quitButton = new Button(quitButtonTexture, (float) GameClient.WIDTH / 2 + 200,(float) GameClient.HEIGHT / 2, 100f,100f);
        quitButtonWhite = new Button(quitButtonWhiteTexture, (float) GameClient.WIDTH / 2 + 200,(float) GameClient.HEIGHT / 2, 100f,100f);
    }

    @Override
    public void render(float delta) {

        // Update screen white background
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.graphics.setTitle("Menu (" + Gdx.graphics.getFramesPerSecond() + "FPS)");

        batch.begin(); // start

            // if mouse X-coordinate and Y-coordinate on the left button
        if (Gdx.input.getX() > playButton.polygon.getX() && Gdx.input.getX() < playButton.polygon.getX() + 100f &&
                Gdx.input.getY() > playButton.polygon.getY() && Gdx.input.getY() < playButton.polygon.getY() + 100f) {
            playButton.draw(batch); // draw in color selected button

            // if mouse X-coordinate and Y-coordinate on the center button
        } else if (Gdx.input.getX() > settingsButton.polygon.getX() && Gdx.input.getX() < settingsButton.polygon.getX() + 100f &&
                Gdx.input.getY() > settingsButton.polygon.getY() && Gdx.input.getY() < settingsButton.polygon.getY() + 100f) {
            settingsButton.draw(batch); // draw in color selected button

            // if mouse X-coordinate and Y-coordinate on the right button
        } else if (Gdx.input.getX() > quitButton.polygon.getX() && Gdx.input.getX() < quitButton.polygon.getX() + 100f &&
                Gdx.input.getY() > quitButton.polygon.getY() && Gdx.input.getY() < quitButton.polygon.getY() + 100f) {
            quitButton.draw(batch); // draw in color selected button

            // if click - app exit
            if (Gdx.input.isTouched()) {
                Gdx.app.exit();
            }
        }

        // draw transparent buttons
        playButtonWhite.draw(batch);
        settingsButtonWhite.draw(batch);
        quitButtonWhite.draw(batch);

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
        playButtonTexture.dispose();
        playButtonWhiteTexture.dispose();
        settingsButtonTexture.dispose();
        settingsButtonWhiteTexture.dispose();
        quitButtonTexture.dispose();
        quitButtonWhiteTexture.dispose();
    }
}
