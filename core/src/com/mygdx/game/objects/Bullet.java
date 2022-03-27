package com.mygdx.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class Bullet extends GameObject {

    private final Player player;

    public boolean isShot;
    private float bulletRotation;

    private float timeShot = 0;
    private float timeReload = 0;

    private int ammo = 30;
    private int ammoTotal = 60;
    private boolean isReload;

    private final BitmapFont ammoFont = new BitmapFont();
    private final BitmapFont reloadFont = new BitmapFont();

    private Texture ammoTexture = new Texture("ammo1.png");
    private Texture ammoWhiteTexture = new Texture("ammo2.png");

    public Bullet(Texture texture, float x, float y, float width, float height, Player player) {
        super(texture, x, y, width, height);
        this.player = player;
        ammoFont.setColor(0, 0, 0, 1);
        ammoFont.getData().setScale(2);
        ammoFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        reloadFont.setColor(1, 0, 0, 1);
        reloadFont.getData().setScale(2);
        reloadFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
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

    public void shot(Octopus octopus, Zombie zombie, float delta) {

        if (!isReload) {

            if (Gdx.input.isTouched() && !isShot && ammo > 0) {
                polygon.setPosition(player.polygon.getX() + polygon.getOriginX(), player.polygon.getY() + polygon.getOriginX());
                bulletRotation = player.polygon.getRotation() + 90;
                isShot = true;
                ammo--;
            }

        } else {
            timeReload += delta;

            if (timeReload > 3) {
                timeReload = 0;
                isReload = false;
                if (ammoTotal >= 30 - ammo) {
                    ammoTotal -= (30 - ammo);
                    ammo = 30;
                } else {
                    ammo += ammoTotal;
                    ammoTotal = 0;
                }
            }
        }

        if (isShot) {
            timeShot += delta;
            if (timeShot > 0.5) {
                timeShot = 0;
                isShot = false;
            }
        }

        if (isShot) {
            polygon.setPosition(polygon.getX() + 20 * MathUtils.cosDeg(bulletRotation),
                    polygon.getY() + 20 * MathUtils.sinDeg(bulletRotation));
            polygon.setRotation(bulletRotation);
        }

        if (isShot && polygon.getX() > octopus.polygon.getX() && polygon.getX() < octopus.polygon.getX() + 100f &&
                polygon.getY() > octopus.polygon.getY() && polygon.getY() < octopus.polygon.getY() + 100f) {
            polygon.setPosition(9999999, 9999999);
            octopus.setHp(octopus.getHp() - 1);
        }

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

