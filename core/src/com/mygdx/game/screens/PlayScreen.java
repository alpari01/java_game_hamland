package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.GameClient;
import com.mygdx.game.client.KryoClient;
import com.mygdx.game.objects.Player;
import com.mygdx.game.objects.Teammate;

public class PlayScreen implements Screen {

    private final GameClient gameClient;
    private SpriteBatch batch;

    // Properties
    public static final int PLAYER_X = 100;
    public static final int PLAYER_Y = 100;
    public static final int PLAYER_WIDTH = 100;
    public static final int PLAYER_HEIGHT = 100;

    // Textures
    private Texture playerTexture;

    // Objects
    private Player player;
    private Teammate teammate;

    public PlayScreen(GameClient gameClient) {
        this.gameClient = gameClient;

        // Textures
        playerTexture = new Texture("player1.png");

        // Objects
        player = new Player(playerTexture, PLAYER_X, PLAYER_Y, PLAYER_WIDTH, PLAYER_HEIGHT);
        teammate = new Teammate(playerTexture, PLAYER_X, PLAYER_Y, PLAYER_WIDTH, PLAYER_HEIGHT);
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {

        // Update screen white background
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.graphics.setTitle("Play (" + Gdx.graphics.getFramesPerSecond() + "FPS)");

        batch.begin(); // start

        detectInput(); // send packet
        player.draw(batch); // draw player

        updateTeammatePosition(); // update teammate position
        teammate.draw(batch); // draw teammate

        batch.end(); // end
    }

    /**
     * Send a packet with the player's coordinates to the server, if one of the control buttons is pressed.
     */
    private void detectInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP) ||
                Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT) ||
                Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN) ||
                Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            gameClient.client.sendPlayerMovementInformation(player.polygon.getX(), player.polygon.getY());
        }
    }

    /**
     * Change the position of another player (works in the loop).
     */
    public void updateTeammatePosition() {
        teammate.polygon.setPosition(KryoClient.teammatePositionX, KryoClient.teammatePositionY);
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
        playerTexture.dispose();
        gameClient.dispose();
    }
}
