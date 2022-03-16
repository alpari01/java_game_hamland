package com.mygdx.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;

public class Bullet extends GameObject {

    public boolean isShot;
    private float bulletRotation;

    public Bullet(Texture texture, float x, float y, float width, float height) {
        super(texture, x, y, width, height);
    }

    public void shot(Polygon playerPolygon, Octopus octopus, Zombie zombie) {

        if (Gdx.input.justTouched() && !isShot) {
            bulletRotation = playerPolygon.getRotation() + 90;
            isShot = true;
        }

        if (isShot) {
            polygon.setPosition(polygon.getX() + 20 * MathUtils.cosDeg(bulletRotation),
                                polygon.getY() + 20 * MathUtils.sinDeg(bulletRotation));
            polygon.setRotation(bulletRotation);
        }

        if (!isShot || polygon.getX() > 1280 || polygon.getX() < 0 || polygon.getY() > 720 || polygon.getY() < 0) {
            polygon.setPosition(playerPolygon.getX() + 50, playerPolygon.getY() + 50);
            isShot = false;
        }

        if (polygon.getX() > octopus.polygon.getX() && polygon.getX() < octopus.polygon.getX() + 100f &&
        polygon.getY() > octopus.polygon.getY() && polygon.getY() < octopus.polygon.getY() + 100f) {
            isShot = false;
            octopus.setHp(octopus.getHp() - 1);
        }

        if (polygon.getX() > zombie.polygon.getX() && polygon.getX() < zombie.polygon.getX() + 100f &&
        polygon.getY() > zombie.polygon.getY() && polygon.getY() < zombie.polygon.getY() + 100f) {
            polygon.setPosition(playerPolygon.getX() + 50, playerPolygon.getY() + 50);
            isShot = false;
            zombie.setHp(zombie.getHp() - 1);
        }
    }
}

