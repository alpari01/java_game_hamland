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

    private float time = 0;

    private int ammo = 30;

    private final BitmapFont font = new BitmapFont();

    private Texture ammoTexture = new Texture("ammo1.png");
    private Texture ammoWhiteTexture = new Texture("ammo2.png");

    public Bullet(Texture texture, float x, float y, float width, float height, Player player) {
        super(texture, x, y, width, height);
        this.player = player;
        font.setColor(0, 0, 0, 1);
        font.getData().setScale(2);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (isShot) {
            super.draw(batch);
        } else if (ammo > 0) {
            batch.draw(ammoTexture, player.polygon.getX() - 570, player.polygon.getY() - 250, 100, 100);
        }
        batch.draw(ammoWhiteTexture, player.polygon.getX() - 570, player.polygon.getY() - 250, 100, 100);
        font.draw(batch, "x" + ammo, player.polygon.getX() - 520, player.polygon.getY() - 250);
    }

    public void shot(Octopus octopus, Zombie zombie, float delta) {

        if (Gdx.input.isTouched() && !isShot && ammo > 0) {
            polygon.setPosition(player.polygon.getX() + polygon.getOriginX(), player.polygon.getY() + polygon.getOriginX());
            bulletRotation = player.polygon.getRotation() + 90;
            isShot = true;
            ammo--;
        }

        if (isShot) {
            time += delta;
            if (time > 0.5) {
                time = 0;
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
}

