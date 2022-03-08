package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.GameClient;
import com.mygdx.game.objects.Player;

public class PlayScreen implements Screen {

    private final GameClient gameClient;
    private SpriteBatch batch;

    private Texture playerTexture;
    private Player player;

    public PlayScreen(GameClient gameClient) {
        this.gameClient = gameClient;

        playerTexture = new Texture("hamster1.png");
        player = new Player(playerTexture, 100, 100, 100, 100);
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.graphics.setTitle("Play (" + Gdx.graphics.getFramesPerSecond() + "FPS)");

        batch.begin();

        detectInput();
        player.draw(batch);

        batch.end();
    }

    private void detectInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
//            clientConnection.sendPlayerInformation(0, movementSpeed, "up", health);
            player.polygon.setPosition(player.polygon.getX(), player.polygon.getY() + 1);
            System.out.println("up");
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
//            clientConnection.sendPlayerInformation(-movementSpeed, 0, "left", health);
            player.polygon.setPosition(player.polygon.getX() - 1, player.polygon.getY());
            System.out.println("left");
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
//            clientConnection.sendPlayerInformation(0, -movementSpeed, "down", health);
            player.polygon.setPosition(player.polygon.getX(), player.polygon.getY() - 1);
            System.out.println("down");
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
//            clientConnection.sendPlayerInformation(movementSpeed, 0, "right", health);
            player.polygon.setPosition(player.polygon.getX() + 1, player.polygon.getY());
            System.out.println("right");
        }
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
        playerTexture.dispose();
    }
}
