package com.mygdx.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.mygdx.game.GameClient;

import java.util.Map;

public class Bullet extends GameObject {

    private final Player player;

    // Shot
    public boolean isShot;
    private float bulletRotation;
    private float timeShot = 0;

    // Reload + ammo
    private float timeReload = 0;
    private boolean isReload;
    private int ammo = 30;
    private int ammoTotal = 60;

    // Explosion
    private boolean isHit;
    private float timeHit = 0;
    private int explosionTextureIndex = 0;
    private float x = 0, y = 0;

    // Fonts
    private final BitmapFont ammoFont = new BitmapFont();
    private final BitmapFont reloadFont = new BitmapFont();

    // Textures
    private final Texture ammoTexture = new Texture("ammo1.png");
    private final Texture ammoWhiteTexture = new Texture("ammo2.png");
    private final TextureAtlas explosionAtlas;
    private final TextureRegion[] textureRegions;

    public Bullet(Texture texture, float x, float y, float width, float height, Player player) {
        super(texture, x, y, width, height);
        this.player = player;

        // Explosion textures
        explosionAtlas = new TextureAtlas("explosion.atlas");
        textureRegions = new TextureRegion[40];
        for (int i = 0; i < 40; i++) {
            textureRegions[i] = explosionAtlas.findRegion("exp" + (i + 1));
        }

        // Font settings
        ammoFont.setColor(0, 0, 0, 1); // color
        ammoFont.getData().setScale(2); // size
        ammoFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear); // filter

        reloadFont.setColor(1, 0, 0, 1); // color
        reloadFont.getData().setScale(2); // size
        reloadFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear); // filter
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (isShot) {
            super.draw(batch);
        } else if (ammo > 0 && !isReload) {
            batch.draw(ammoTexture, player.polygon.getX() - 570, player.polygon.getY() - 250, 100, 100);
        }
        batch.draw(ammoWhiteTexture, player.polygon.getX() - 570, player.polygon.getY() - 250, 100, 100);
        ammoFont.draw(batch, ammo + " / " + ammoTotal, player.polygon.getX() - 560, player.polygon.getY() - 260);

        if (isReload) {
            reloadFont.draw(batch, "reload: " + (3 - (int) Math.floor(timeReload)), player.polygon.getX() - 570, player.polygon.getY() - 200);
        }
    }

    public void shot(Map<Integer, Enemy> enemyList, float delta, GameClient gameClient, SpriteBatch batch) {

        // If it's not reloading now, you can shoot.
        if (!isReload) {

            // When the mouse is clicked, sets the initial position and rotation of the bullet.
            if (Gdx.input.isTouched() && !isShot && ammo > 0) {
                polygon.setPosition(player.polygon.getX() + polygon.getOriginX(), player.polygon.getY() + polygon.getOriginX());
                bulletRotation = player.polygon.getRotation() + 90;
                isShot = true;
                ammo--;
                gameClient.client.sendPacketBulletShot(this.bulletRotation);
            }

        // If reloading starts, a timer is set for 3 seconds.
        } else {

            timeReload += delta;
            if (timeReload > 3) {
                timeReload = 0;
                isReload = false;

                // Adds from the total number of bullets to the main.
                if (ammoTotal >= 30 - ammo) {
                    ammoTotal -= (30 - ammo);
                    ammo = 30;
                } else {
                    ammo += ammoTotal;
                    ammoTotal = 0;
                }
            }
        }

        // Delay between each shot.
        if (isShot) {
            timeShot += delta;
            if (timeShot > 0.5) {
                timeShot = 0;
                isShot = false;
            }
        }

        // Sets the main trajectory of the bullet after pressing the left mouse button.
        if (isShot) {
            polygon.setPosition(polygon.getX() + 20 * MathUtils.cosDeg(bulletRotation),
                    polygon.getY() + 20 * MathUtils.sinDeg(bulletRotation));
            polygon.setRotation(bulletRotation);

            // Check bullet hit enemy.
            for (Enemy enemy : enemyList.values()) {
                if (polygon.getX() > enemy.polygon.getX() && polygon.getX() < enemy.polygon.getX() + 100f
                    && polygon.getY() > enemy.polygon.getY() && polygon.getY() < enemy.polygon.getY() + 100f) {
                    enemy.setHp(enemy.getHp() - 1);
                    explosionTextureIndex = 0;
                    x = polygon.getX();
                    y = polygon.getY();
                    isHit = true;
                    polygon.setPosition(9999999, 9999999);
                }
            }
        }

        // Draws explosion animation
        if (isHit) {
            if (explosionTextureIndex == 40) {
                isHit = false;
                explosionTextureIndex = 0;
            }
            timeHit += delta;
            batch.draw(textureRegions[explosionTextureIndex], x - 64, y - 64);
            if (timeHit > 0.01) {
                explosionTextureIndex++;
                timeHit = 0;
            }
        }
    }

    public void setReload(boolean reload) {
        isReload = reload;
    }
}
