package com.mygdx.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;

import java.util.Map;

public class Bullet extends GameObject {

    public boolean isShot;
    private float bulletRotation;

    private float time = 0;

    public Bullet(Texture texture, float x, float y, float width, float height) {
        super(texture, x, y, width, height);
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (isShot) {
            super.draw(batch);
        }
    }

    public void shot(Polygon playerPolygon, Map<Integer, Enemy> enemyList, float delta) {

        if (Gdx.input.isTouched() && !isShot) {
            polygon.setPosition(playerPolygon.getX() + polygon.getOriginX(), playerPolygon.getY() + polygon.getOriginX());
            bulletRotation = playerPolygon.getRotation() + 90;
            isShot = true;
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

            // Check bullet hit enemy.
            for (Enemy enemy : enemyList.values()) {
                if (polygon.getX() > enemy.polygon.getX() && polygon.getX() < enemy.polygon.getX() + 100f
                    && polygon.getY() > enemy.polygon.getY() && polygon.getY() < enemy.polygon.getY() + 100f) {
                    // If enemy hit.
                    polygon.setPosition(9999999, 9999999);
                    enemy.setHp(enemy.getHp() - 1);
                }
            }
        }

//        if (isShot && polygon.getX() > octopus.polygon.getX() && polygon.getX() < octopus.polygon.getX() + 100f &&
//                polygon.getY() > octopus.polygon.getY() && polygon.getY() < octopus.polygon.getY() + 100f) {
//            polygon.setPosition(9999999, 9999999);
////            octopus.setHp(octopus.getHp() - 1);
//        }
    }
}

