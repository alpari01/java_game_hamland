package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.GameClient;
import com.mygdx.game.objects.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class PlayScreen implements Screen {

    private final GameClient gameClient;
    private SpriteBatch batch;

    private float prevRotation;

    public Map<Integer, Enemy> enemies = new HashMap<>();

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
//    private Zombie zombie;
    private Octopus octopus;
    private Bullet bullet;

    private float time = 0;
    private Random random;

    private OrthographicCamera camera;

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
//        zombie = new Zombie(zombieTexture, 1000, 600, 100, 100, 5);
//        octopus = new Octopus(octopusTexture, 1000, 100, 100, 100, 5);
        bullet = new Bullet(bulletTexture, 100, 100, 50, 50);

        for (String teammateNickname : gameClient.client.getTeammates().keySet()) {
            gameClient.client.getTeammates().put(teammateNickname, new Teammate(playerTexture, PLAYER_X, PLAYER_Y, PLAYER_WIDTH, PLAYER_HEIGHT));
        }

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

        camera.position.set(player.polygon.getX(), player.polygon.getY(), 0);
        camera.update();

        detectInput(); // send packet
        player.draw(batch); // draw player

        bullet.shot(player.polygon, enemies, delta);
        bullet.draw(batch);

//        octopus.draw(batch, delta, player);
//        zombie.draw(batch, delta, player);

        updateTeammatePosition(); // update teammates' positions
        updateEnemiesPosition();

        batch.setProjectionMatrix(camera.combined);

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

    /**
     * Change the positions of all enemies (works in the loop).
     */
    public void updateEnemiesPosition() {
        for (int mobId : gameClient.client.getEnemiesData().keySet()) {

            // Mob data: posX, posY, type.
            float[] mobData = gameClient.client.getEnemiesData().get(mobId);

            if (!enemies.containsKey(mobId)) {
                // If such Enemy object was not created yet -> check Enemy type and create respective object.
                if (mobData[2] == 0.0) {
                    // If mob is zombie.
                    enemies.put(mobId, new Zombie(zombieTexture, mobData[0], mobData[1], 100, 100, 5));
                }

                if (mobData[2] == 1.0) {
                    // If mob is octopus.
                    enemies.put(mobId, new Octopus(octopusTexture, mobData[0], mobData[1], 100, 100, 5));
                }

                enemies.get(mobId).polygon.setPosition(mobData[0], mobData[1]);
                enemies.get(mobId).draw(batch);
            }

            else {
                // If such mob already exists -> update its data.
                Enemy enemyToUpdate = enemies.get(mobId);
                enemyToUpdate.polygon.setPosition(mobData[0], mobData[1]);
                enemyToUpdate.draw(batch);
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
