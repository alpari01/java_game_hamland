package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.GameClient;
import com.mygdx.game.client.KryoClient;
import com.mygdx.game.objects.Bullet;
import com.mygdx.game.objects.Octopus;
import com.mygdx.game.objects.Player;
import com.mygdx.game.objects.Teammate;
import com.mygdx.game.objects.Zombie;

import java.util.Random;

public class PlayScreen implements Screen {

    private final GameClient gameClient;
    private SpriteBatch batch;

    private float prevRotation;

    // Properties
    public static final int PLAYER_X = 100;
    public static final int PLAYER_Y = 100;
    public static final int PLAYER_WIDTH = 100;
    public static final int PLAYER_HEIGHT = 100;

    // Textures
    private Texture playerTexture;
    private Texture zombieTexture;
    private Texture octopusTexture;
    private Texture bulletTexture;

    // Objects
    private Player player;
    private Teammate teammate;
    private Zombie zombie;
    private Octopus octopus;
    private Bullet bullet;

    private float time = 0;
    private Random random;

    public PlayScreen(GameClient gameClient) {
        this.gameClient = gameClient;

        random = new Random();

        // Textures
        playerTexture = new Texture("player1.png");
        zombieTexture = new Texture("zombie_enemy.png");
        octopusTexture = new Texture("octopus_enemy.png");
        bulletTexture = new Texture("game_enemy.png");

        // Objects
        player = new Player(playerTexture, PLAYER_X, PLAYER_Y, PLAYER_WIDTH, PLAYER_HEIGHT);
        teammate = new Teammate(playerTexture, PLAYER_X, PLAYER_Y, PLAYER_WIDTH, PLAYER_HEIGHT);
        zombie = new Zombie(zombieTexture, 1000, 600, 100, 100, 0.5, 5);
        octopus = new Octopus(octopusTexture, 1000, 100, 100, 100, 0.3, 5);
        bullet = new Bullet(bulletTexture, 100, 100, 50, 50);
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

        bullet.shot(player.polygon, octopus, zombie);
        if (bullet.isShot) {
            bullet.draw(batch);
        }

        if (octopus.isAlive()) {
            octopus.followPlayer(player.polygon);
            octopus.draw(batch);
        } else {
            time += delta;
            if (time > 0.5) {
                octopus.setHp(5);
                octopus.polygon.setPosition(random.nextInt(GameClient.WIDTH), random.nextInt(GameClient.HEIGHT));
                time = 0;
            }
        }

        if (zombie.isAlive()) {
            zombie.followPlayer(player.polygon);
            zombie.draw(batch);
        } else {
            time += delta;
            if (time > 0.5) {
                zombie.setHp(5);
                zombie.polygon.setPosition(random.nextInt(GameClient.WIDTH), random.nextInt(GameClient.HEIGHT));
                time = 0;
            }
        }

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
        teammate.polygon.setPosition(KryoClient.teammatePositionX, KryoClient.teammatePositionY);
        teammate.polygon.setRotation(KryoClient.teammateRotation);
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
