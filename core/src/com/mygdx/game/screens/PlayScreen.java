package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.mygdx.game.GameClient;
import com.mygdx.game.objects.*;

import java.util.HashMap;
import java.util.Map;

public class PlayScreen implements Screen {

    private final GameClient gameClient;
    private SpriteBatch batch;

    private float prevRotation;

    public Map<Integer, Enemy> enemies = new HashMap<>();
    public Map<String, BulletTeammate> teammateBullets = new HashMap<>();

    // Properties
    public static final float TEXTURE_SIZES_CONSTANT = 0.065f;

    public static final int PLAYER_X = 1190;
    public static final int PLAYER_Y = 910;

    public static final int PLAYER_WIDTH = (int) (771 * TEXTURE_SIZES_CONSTANT);
    public static final int PLAYER_HEIGHT = (int) (1054 * TEXTURE_SIZES_CONSTANT);

    public static final int BULLET_WIDTH = (int) (512 * TEXTURE_SIZES_CONSTANT);;
    public static final int BULLET_HEIGHT = (int) (512 * TEXTURE_SIZES_CONSTANT);;

    public static final int ZOMBIE_WIDTH = (int) (694 * TEXTURE_SIZES_CONSTANT);
    public static final int ZOMBIE_HEIGHT = (int) (1167 * TEXTURE_SIZES_CONSTANT);

    public static final int OCTOPUS_WIDTH = (int) (923 * TEXTURE_SIZES_CONSTANT);
    public static final int OCTOPUS_HEIGHT = (int) (986 * TEXTURE_SIZES_CONSTANT);



    // Textures
    private Texture playerTexture;
    private Texture zombieTexture;
    private Texture octopusTexture;
    private Texture bulletTexture;

    // Objects
    private Player player;
    private Bullet bullet;

    private OrthographicCamera camera;
    public static float cameraX, cameraY;

    // TiledMap
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private float tileWidth, tileHeight;
    private boolean collisionX;
    private boolean collisionY;
    private TiledMapTileLayer waterCollisionLayer;
    private TiledMapTileLayer buildingsCollisionLayer;

    public PlayScreen(GameClient gameClient) {
        this.gameClient = gameClient;

        // Textures
        playerTexture = new Texture("player1.png");
        zombieTexture = new Texture("zombie_enemy.png");
        octopusTexture = new Texture("octopus_enemy.png");
        bulletTexture = new Texture("bullet.png");

        // Objects
        player = new Player(playerTexture, PLAYER_X, PLAYER_Y, PLAYER_WIDTH, PLAYER_HEIGHT);
        bullet = new Bullet(bulletTexture, 0, 0, BULLET_WIDTH, BULLET_HEIGHT, player);

        for (String teammateNickname : gameClient.client.getTeammates().keySet()) {
            gameClient.client.getTeammates().put(teammateNickname, new Teammate(playerTexture, PLAYER_X, PLAYER_Y, PLAYER_WIDTH, PLAYER_HEIGHT));
        }

        camera = new OrthographicCamera(player.polygon.getX(), player.polygon.getY());
        camera.setToOrtho(false);

        // Assign new bullet object for every teammate.
        for (String teammateNickname : gameClient.client.getTeammates().keySet()) {
            this.teammateBullets.put(teammateNickname, new BulletTeammate(bulletTexture, 100, 100, 50, 50));
        }

        // TiledMap
        map = new TmxMapLoader().load("maps/samplemap.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        waterCollisionLayer = (TiledMapTileLayer) map.getLayers().get("water");
        buildingsCollisionLayer = (TiledMapTileLayer) map.getLayers().get("building");
        tileWidth = waterCollisionLayer.getTileWidth();
        tileHeight = waterCollisionLayer.getTileHeight();

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

        renderer.setView(camera);
        renderer.render();

        batch.begin(); // start

        // Previous player's position
        float prevPlayerX = player.polygon.getX();
        float prevPlayerY = player.polygon.getY();

        // Camera
        detectEdgeOfTheMap();
        camera.position.set(cameraX, cameraY, 0);
        camera.update();

        detectInput(); // send packet
        player.draw(batch, bullet, camera); // draw player

        detectCollision(prevPlayerX, prevPlayerY); // detect collision

        updateTeammatePosition(); // update teammates' positions
        updateEnemiesPosition(delta);
        updateBullet(delta);

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

    public void updateBullet(float delta) {
        // Update player's own bullet.
        bullet.shot(enemies, delta, gameClient, batch);
        bullet.draw(batch);

        // Render teammates' bullets.
        for (String teammateNickname : teammateBullets.keySet()) {
            if (gameClient.client.getTeammatesShot().containsKey(teammateNickname)
                    && gameClient.client.getTeammatesShot().get(teammateNickname)) {
                // If this teammate has made a shot.
                BulletTeammate bulletTeammate = teammateBullets.get(teammateNickname);

                // Render his bullet.
                bulletTeammate.renderShot(gameClient.client.getTeammates().get(teammateNickname).polygon, enemies, delta, batch);
                bulletTeammate.draw(batch);

                // If bullet was shot -> stop rendering it.
                if (!teammateBullets.get(teammateNickname).isShot) {
                    gameClient.client.getTeammatesShot().put(teammateNickname, false);
                }
            }
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
    public void updateEnemiesPosition(float delta) {
        for (int mobId : gameClient.client.getEnemiesData().keySet()) {

            // Mob data: posX, posY, type.
            float[] mobData = gameClient.client.getEnemiesData().get(mobId);

            if (!enemies.containsKey(mobId)) {
                // If such Enemy object was not created yet -> check Enemy type and create respective object.
                if (mobData[2] == 0.0) {
                    // If mob is zombie.
                    enemies.put(mobId, new Zombie(zombieTexture, mobData[0], mobData[1], ZOMBIE_WIDTH, ZOMBIE_HEIGHT, 5));
                }

                if (mobData[2] == 1.0) {
                    // If mob is octopus.
                    enemies.put(mobId, new Octopus(octopusTexture, mobData[0], mobData[1], OCTOPUS_WIDTH, OCTOPUS_HEIGHT, 5));
                }

                enemies.get(mobId).polygon.setPosition(mobData[0], mobData[1]);
                enemies.get(mobId).draw(batch, delta);
            }

            else {
                // If such mob already exists -> update its data.
                Enemy enemyToUpdate = enemies.get(mobId);
                enemyToUpdate.polygon.setPosition(mobData[0], mobData[1]);
                enemyToUpdate.draw(batch, delta);
            }
        }
    }

    /**
     * Check if the player's position is on a blocked cell.
     * If yes, change the player's position to the previous one.
     * @param prevPlayerX player's previous X position.
     * @param prevPlayerY player's previous Y position.
     */
    public void detectCollision(float prevPlayerX, float prevPlayerY) {

        // Water collision
        layerCollision(waterCollisionLayer);

        // Buildings collision
        layerCollision(buildingsCollisionLayer);

        // If collision is detected on X, teleport one step back on X
        if (collisionX) {
            player.polygon.setPosition(prevPlayerX, player.polygon.getY());
            collisionX = false;
        }

        // If collision is detected on Y, teleport one step back on Y
        if (collisionY) {
            player.polygon.setPosition(player.polygon.getX(), prevPlayerY);
            collisionY = false;
        }
    }

    /**
     * If the player is on the cell of the layer - assert a collision.
     *
     * Player:
     * -9--12--10-
     * 3        4
     * |        |
     * 5        6
     * |        |
     * 1        2
     * -7--11--8-
     *
     * @param layer TiledMap layer.
     */
    public void layerCollision(TiledMapTileLayer layer) {

        float playerX = player.polygon.getX();
        float playerY = player.polygon.getY();

        if ( // Horizontal collision

        // 1. lower left corner of the player
        layer.getCell((int) (playerX / tileWidth), (int) ((playerY + 2) / tileHeight)) != null ||

        // 2. lower right corner of the player
        layer.getCell((int) ((playerX + PLAYER_WIDTH) / tileWidth), (int) ((playerY + 2) / tileHeight)) != null ||

        // 3. upper left corner of the player
        layer.getCell((int) (playerX / tileWidth), (int) ((playerY + PLAYER_HEIGHT - 2) / tileHeight)) != null ||

        // 4. upper right corner of the player
        layer.getCell((int) ((playerX + PLAYER_WIDTH) / tileWidth), (int) (((playerY - 2) + PLAYER_HEIGHT) / tileHeight)) != null ||

        // 5. left center point of the player
        layer.getCell((int) (playerX / tileWidth), (int) ((playerY + PLAYER_HEIGHT / 2) / tileHeight)) != null ||

        // 6. right center point of the player
        layer.getCell((int) ((playerX + PLAYER_WIDTH) / tileWidth), (int) ((playerY + PLAYER_HEIGHT / 2) / tileHeight)) != null) {

            collisionX = true;
        }

        if ( // Vertical collision

        // 7. lower left corner of the player
        layer.getCell((int) ((playerX + 2) / tileWidth), (int) (playerY / tileHeight)) != null ||

        // 8. lower right corner of the player
        layer.getCell((int) (((playerX - 2) + PLAYER_WIDTH) / tileWidth), (int) (playerY / tileHeight)) != null ||

        // 9. upper left corner of the player
        layer.getCell((int) ((playerX + 2) / tileWidth), (int) ((playerY + PLAYER_HEIGHT) / tileHeight)) != null ||

        // 10. upper right corner of the player
        layer.getCell((int) (((playerX - 2) + PLAYER_WIDTH) / tileWidth), (int) ((playerY + PLAYER_HEIGHT) / tileHeight)) != null ||

        // 11. lower center point of the player
        layer.getCell((int) ((playerX + PLAYER_WIDTH / 2) / tileWidth), (int) (playerY / tileHeight)) != null ||

        // 12. upper center point of the player
        layer.getCell((int) ((playerX + PLAYER_WIDTH / 2) / tileWidth), (int) ((playerY + PLAYER_HEIGHT) / tileHeight)) != null) {

            collisionY = true;
        }
    }

    /**
     * When a player is at the edge of the map, stop following him with the camera.
     */
    public void detectEdgeOfTheMap() {

        // Stop camera horizontally
        if (player.polygon.getX() < 1230 && player.polygon.getX() > 590) {
            cameraX = player.polygon.getX() + 50;
        }

        // Stop camera vertically
        if (player.polygon.getY() < 1510 && player.polygon.getY() > 310) {
            cameraY = player.polygon.getY() + 50;
        }
    }

    @Override
    public void resize(int width, int height) { }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() { }

    @Override
    public void dispose() {

        // Clear memory when game is off
        batch.dispose();
        playerTexture.dispose();
        zombieTexture.dispose();
        octopusTexture.dispose();
        bulletTexture.dispose();
        gameClient.dispose();
        renderer.dispose();
        map.dispose();

    }
}
