package com.mygdx.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class Bullet extends GameObject {

    // The player who owns the bullets
    private final Player player;

    public boolean isShot;
    private float bulletRotation;

    private float timeShot = 0;
    private float timeReload = 0;

    private int ammo = 30;
    private int ammoTotal = 60;

    private boolean isReload;

    // Fonts
    private final BitmapFont ammoFont = new BitmapFont();
    private final BitmapFont reloadFont = new BitmapFont();

    // Textures
    private Texture ammoTexture = new Texture("ammo1.png");
    private Texture ammoWhiteTexture = new Texture("ammo2.png");

    public Bullet(Texture texture, float x, float y, float width, float height, Player player) {
        super(texture, x, y, width, height);
        this.player = player;

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

    /**
     * Shoot a bullet by pressing the left mouse button.
     * @param octopus octopus
     * @param zombie zombie
     * @param delta delta time.
     */
    public void shot(Octopus octopus, Zombie zombie, float delta) {

        // If it's not reloading now, you can shoot.
        if (!isReload) {

            // When the mouse is clicked, sets the initial position and rotation of the bullet.
            if (Gdx.input.isTouched() && !isShot && ammo > 0) {
                polygon.setPosition(player.polygon.getX() + polygon.getOriginX(), player.polygon.getY() + polygon.getOriginX());
                bulletRotation = player.polygon.getRotation() + 90;
                isShot = true;
                ammo--;
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
        }

        // If a bullet hits a mob, the mob loses one health point.
        // The position of the bullet is replaced by a very large one that it did not damage.
        if (isShot && polygon.getX() > octopus.polygon.getX() && polygon.getX() < octopus.polygon.getX() + 100f &&
                polygon.getY() > octopus.polygon.getY() && polygon.getY() < octopus.polygon.getY() + 100f) {
            polygon.setPosition(9999999, 9999999);
            octopus.setHp(octopus.getHp() - 1);
        }

        // Same for another type of mob.
        if (isShot && polygon.getX() > zombie.polygon.getX() && polygon.getX() < zombie.polygon.getX() + 100f &&
                polygon.getY() > zombie.polygon.getY() && polygon.getY() < zombie.polygon.getY() + 100f) {
            polygon.setPosition(9999999, 9999999);
            zombie.setHp(zombie.getHp() - 1);
        }
    }

    public void setReload(boolean reload) {
        isReload = reload;
    }
}
