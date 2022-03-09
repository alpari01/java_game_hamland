package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.GameClient;
import com.mygdx.game.client.KryoClient;
import com.mygdx.game.objects.Button;

public class NicknameScreen implements Screen, Input.TextInputListener {

    // Textures
    private Texture playButtonTexture;
    private Texture playButtonWhiteTexture;

    // Objects
    private Button playButton;
    private Button playButtonWhite;

    private final GameClient gameClient;
    private SpriteBatch batch;

    // Text input window
    private String nickname;
    private boolean isWindowOpened;

    public NicknameScreen(GameClient gameClient) {
        this.gameClient = gameClient;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();

        // Textures
        playButtonTexture = new Texture("button_place.png");
        playButtonWhiteTexture = new Texture("button_place.png");

        // Button objects with position in the center of the screen
        playButton = new Button(playButtonTexture, (float) GameClient.WIDTH / 2,(float) GameClient.HEIGHT / 2 - 150, 100f,100f);
        playButtonWhite = new Button(playButtonWhiteTexture, (float) GameClient.WIDTH / 2,(float) GameClient.HEIGHT / 2 - 150, 100f,100f);

    }

    @Override
    public void render(float delta) {

        // Update screen with white background
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.graphics.setTitle("Nickname (" + Gdx.graphics.getFramesPerSecond() + "FPS)");

        batch.begin(); // start

        // if mouse X-coordinate and Y-coordinate on the button
        if (Gdx.input.getX() > playButton.polygon.getX() && Gdx.input.getX() < playButton.polygon.getX() + 100f &&
                GameClient.HEIGHT - Gdx.input.getY() > playButton.polygon.getY() && GameClient.HEIGHT - Gdx.input.getY() < playButton.polygon.getY() + 100f) {
            playButton.draw(batch); // draw in color selected button

            // if click - open text input window
            if (Gdx.input.justTouched() && !isWindowOpened) {
                isWindowOpened = true;
                Gdx.input.getTextInput(this, "Enter your name", "", "name");

                if (!gameClient.client.isToServerConnected) gameClient.client.connectToServer();
            }
        }

        // If nickname is entered
        if (nickname != null) {
            // Send a request to the server to check if player's nickname is unique.
            gameClient.client.sendPacketCheckNickname(nickname);
        }

        // If player's nickname is unique -> proceed and show menu screen.
        if (KryoClient.isNicknameUnique)
            gameClient.setScreen(new MenuScreen(gameClient));

        if (!KryoClient.isNicknameUnique) {
            nickname = null;
            isWindowOpened = false;
        }

        // draw transparent buttons
        playButtonWhite.draw(batch);

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

    }

    @Override
    public void input(String text) {
        this.nickname = text;
    }

    @Override
    public void canceled() {
        isWindowOpened = false;
    }
}
