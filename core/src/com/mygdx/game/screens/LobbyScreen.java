package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.GameClient;
import com.mygdx.game.objects.Button;

public class LobbyScreen implements Screen {

    private final GameClient gameClient;
    private SpriteBatch batch;

    // Textures
    private Texture returnButtonTexture;
    private Texture returnButtonWhiteTexture;

    // Objects
    private Button returnButton;
    private Button returnButtonWhite;

    public LobbyScreen(GameClient gameClient) {
        this.gameClient = gameClient;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();

        // Textures
        returnButtonTexture = new Texture("button_place.png");
        returnButtonWhiteTexture = new Texture("button_place.png");

        // Button objects
        returnButton = new Button(returnButtonTexture, 60, 60, 100f, 100f);
        returnButtonWhite = new Button(returnButtonWhiteTexture, 60, 60, 100f, 100f);
    }

    @Override
    public void render(float delta) {

        // Update screen white background
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.graphics.setTitle("Lobby (" + Gdx.graphics.getFramesPerSecond() + "FPS)");

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
