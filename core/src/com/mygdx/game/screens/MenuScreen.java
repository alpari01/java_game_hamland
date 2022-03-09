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
        playButtonTexture = new Texture("play_button1.png");
        playButtonWhiteTexture = new Texture("play_button2.png");
        settingsButtonTexture = new Texture("tools_button1.png");
        settingsButtonWhiteTexture = new Texture("tools_button2.png");
        quitButtonTexture = new Texture("quit_button1.png");
        quitButtonWhiteTexture = new Texture("quit_button2.png");

        // Button objects with position in the center of the screen
        playButton = new Button(playButtonTexture, (float) GameClient.WIDTH / 2, (float) GameClient.HEIGHT / 2 + 200, 200f,162f);
        playButtonWhite = new Button(playButtonWhiteTexture, (float) GameClient.WIDTH / 2, (float) GameClient.HEIGHT / 2 + 200, 200f,162f);
        settingsButton = new Button(settingsButtonTexture, (float) GameClient.WIDTH / 2,(float) GameClient.HEIGHT / 2, 200f,162f);
        settingsButtonWhite = new Button(settingsButtonWhiteTexture, (float) GameClient.WIDTH / 2,(float) GameClient.HEIGHT / 2, 200f,162f);
        quitButton = new Button(quitButtonTexture, (float) GameClient.WIDTH / 2, (float) GameClient.HEIGHT / 2 - 200, 200f,162f);
        quitButtonWhite = new Button(quitButtonWhiteTexture, (float) GameClient.WIDTH / 2, (float) GameClient.HEIGHT / 2 - 200, 200f,162f);
    }

    @Override
    public void render(float delta) {

        // Update screen with white background
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.graphics.setTitle("Menu (" + Gdx.graphics.getFramesPerSecond() + "FPS)");

        batch.begin(); // start

        // if mouse X-coordinate and Y-coordinate on the left button
        if (Gdx.input.getX() > playButton.polygon.getX() && Gdx.input.getX() < playButton.polygon.getX() + 200f &&
                GameClient.HEIGHT - Gdx.input.getY() - 15 > playButton.polygon.getY() && GameClient.HEIGHT - Gdx.input.getY() + 25 < playButton.polygon.getY() + 162f) {

            playButton.draw(batch); // draw in color selected button

            // if click - set screen to PlayScreen
            if (Gdx.input.isTouched()) {
                gameClient.setScreen(new PlayScreen(gameClient));
            }

        } else {
            playButtonWhite.draw(batch); // draw transparent button
        }

        // if mouse X-coordinate and Y-coordinate on the center button
        if (Gdx.input.getX() > settingsButton.polygon.getX() && Gdx.input.getX() < settingsButton.polygon.getX() + 200f &&
                GameClient.HEIGHT - Gdx.input.getY() - 15 > settingsButton.polygon.getY() && GameClient.HEIGHT - Gdx.input.getY() + 25 < settingsButton.polygon.getY() + 162f) {

            settingsButton.draw(batch); // draw in color selected button

            // if click - set screen to SettingsScreen
            if (Gdx.input.isTouched()) {
                gameClient.setScreen(new SettingsScreen(gameClient));
            }

            // if mouse X-coordinate and Y-coordinate on the right button
        } else {
            settingsButtonWhite.draw(batch); // draw transparent button
        }

        if (Gdx.input.getX() > quitButton.polygon.getX() && Gdx.input.getX() < quitButton.polygon.getX() + 200f &&
                GameClient.HEIGHT - Gdx.input.getY() - 15 > quitButton.polygon.getY() && GameClient.HEIGHT - Gdx.input.getY() + 25 < quitButton.polygon.getY() + 162f) {

            quitButton.draw(batch); // draw in color selected button

            // if click - app exit
            if (Gdx.input.isTouched()) {
                Gdx.app.exit();
            }

        } else {
            quitButtonWhite.draw(batch); // draw transparent button
        }

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
