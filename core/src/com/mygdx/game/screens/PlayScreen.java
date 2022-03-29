package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.GameClient;
import com.mygdx.game.objects.Bullet;
import com.mygdx.game.objects.Octopus;
import com.mygdx.game.objects.Player;
import com.mygdx.game.objects.Teammate;
import com.mygdx.game.objects.Zombie;

public class PlayScreen implements Screen {

    private final GameClient gameClient;
    private SpriteBatch batch;

    private float prevRotation;

    // Properties
    public static final int PLAYER_X = 0;
    public static final int PLAYER_Y = 0;
    public static final int PLAYER_WIDTH = 100;
    public static final int PLAYER_HEIGHT = 100;

    // Textures
    private final Texture playerTexture;
    private final Texture zombieTexture;
    private final Texture octopusTexture;
    private final Texture bulletTexture;

    // Objects
    private final Player player;
    private final Zombie zombie;
    private final Octopus octopus;
    private final Bullet bullet;

    // Camera
    private final OrthographicCamera camera;

    public PlayScreen(GameClient gameClient) {
        this.gameClient = gameClient;

        // Textures
        playerTexture = new Texture("player1.png");
        zombieTexture = new Texture("zombie_enemy.png");
        octopusTexture = new Texture("octopus_enemy.png");
        bulletTexture = new Texture("bullet.png");

        // Objects
        player = new Player(playerTexture, PLAYER_X, PLAYER_Y, PLAYER_WIDTH, PLAYER_HEIGHT);
        zombie = new Zombie(zombieTexture, 500, 300, 100, 100, 0.5, 5);
        octopus = new Octopus(octopusTexture, 500, 50, 100, 100, 0.3, 5);
        bullet = new Bullet(bulletTexture, 100, 100, 50, 50, player);

        for (String teammateNickname : gameClient.client.getTeammates().keySet()) {
            gameClient.client.getTeammates().put(teammateNickname, new Teammate(playerTexture, PLAYER_X, PLAYER_Y, PLAYER_WIDTH, PLAYER_HEIGHT));
        }

        // Camera
        camera = new OrthographicCamera(player.polygon.getX(), player.polygon.getY());
        camera.setToOrtho(false);
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

        camera.position.set(player.polygon.getX() + 50, player.polygon.getY() + 50, 0); // set camera position
        camera.update();

        detectInput(); // send packet
        player.draw(batch, bullet); // draw player

        octopus.draw(batch, delta, player);
        zombie.draw(batch, delta, player);

        bullet.shot(octopus, zombie, delta, batch); // shot bullet
        bullet.draw(batch); //draw bullet

        updateTeammatePosition(); // update teammates' positions

        batch.setProjectionMatrix(camera.combined);  // camera

        batch.end(); // end
    }

    /**
     * Send a packet with the player's coordinates to the server, if one of the control buttons is pressed.
     */
    private void detectInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP) ||
                Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT) ||
                Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN) ||
                Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT) ||
                player.polygon.getRotation() != prevRotation) {
            prevRotation = player.polygon.getRotation();
            gameClient.client.sendPlayerMovementInformation(player.polygon.getX(), player.polygon.getY(), player.polygon.getRotation());
        }
    }

    /**
     * Change the position of another player (works in the loop).
     */
    public void updateTeammatePosition() {
        for (String teammateNickname : gameClient.client.getTeammates().keySet()) {
            if (gameClient.client.getTeammates().get(teammateNickname) != null) {
                gameClient.client.getTeammates().get(teammateNickname).draw(batch);
            }
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

        // Clear memory when game is off
        batch.dispose();
        playerTexture.dispose();
        zombieTexture.dispose();
        octopusTexture.dispose();
        bulletTexture.dispose();
        gameClient.dispose();
    }
}
