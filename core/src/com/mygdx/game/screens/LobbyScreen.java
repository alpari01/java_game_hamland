package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.GameClient;
import com.mygdx.game.objects.Button;

public class LobbyScreen implements Screen {

    private final GameClient gameClient;
    private SpriteBatch batch;

    // Properties
    public static final int EXIT_BUTTON_X = 175;
    public static final int EXIT_BUTTON_Y = 100;
    public static final float EXIT_BUTTON_WIDTH = 200f;
    public static final float EXIT_BUTTON_HEIGHT = (float) 1264 / 1383 * EXIT_BUTTON_WIDTH;

    // Textures
    private Texture exitButtonTexture;
    private Texture exitButtonWhiteTexture;
    private Texture backgroundTexture;

    // Objects
    private Button exitButton;
    private Button exitButtonWhite;

    public LobbyScreen(GameClient gameClient) {
        this.gameClient = gameClient;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();

        // Textures
        exitButtonTexture = new Texture("exit_button_active.png");
        exitButtonWhiteTexture = new Texture("exit_button_inactive.png");
        backgroundTexture = new Texture("background.png");

        // Button objects
        exitButton = new Button(exitButtonTexture, EXIT_BUTTON_X, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
        exitButtonWhite = new Button(exitButtonWhiteTexture, EXIT_BUTTON_X, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
    }

    @Override
    public void render(float delta) {

        Gdx.graphics.setTitle("Lobby (" + Gdx.graphics.getFramesPerSecond() + "FPS)");

        batch.begin(); // start

        batch.draw(backgroundTexture, 0, 0, GameClient.WIDTH, GameClient.HEIGHT);

        // if mouse X-coordinate and Y-coordinate on the exit button
        if (Gdx.input.getX() > exitButton.polygon.getX() && Gdx.input.getX() + 30 < exitButton.polygon.getX() + EXIT_BUTTON_WIDTH &&
                GameClient.HEIGHT - Gdx.input.getY() - 45 > exitButton.polygon.getY() && GameClient.HEIGHT - Gdx.input.getY() + 30 < exitButton.polygon.getY() + EXIT_BUTTON_HEIGHT) {
            exitButton.draw(batch); // draw in color selected button

            // if click - set screen to MenuScreen
            if (Gdx.input.isTouched()) {
                gameClient.setScreen(new MenuScreen(gameClient));
            }

        } else {
            exitButtonWhite.draw(batch); // draw transparent exit button
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
        exitButtonTexture.dispose();
        exitButtonWhiteTexture.dispose();
        backgroundTexture.dispose();
        gameClient.dispose();
    }
}
