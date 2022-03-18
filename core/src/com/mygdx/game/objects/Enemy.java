package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Polygon;

public class Enemy extends GameObject {

    private final double speed;
    private int hp;

    public Enemy(Texture texture, float x, float y, float width, float height, double speed, int hp) {
        super(texture, x, y, width, height);
        this.speed = speed;
        this.hp = hp;
    }

    public void followPlayer(Polygon playerPolygon) {
        if (polygon.getX() > playerPolygon.getX()) {
            polygon.setPosition((float) (polygon.getX() - speed), polygon.getY());
        }
        if (polygon.getX() < playerPolygon.getX()) {
            polygon.setPosition((float) (polygon.getX() + speed), polygon.getY());
        }
        if (polygon.getY() > playerPolygon.getY()) {
            polygon.setPosition(polygon.getX(), (float) (polygon.getY() - speed));
        }
        if (polygon.getY() < playerPolygon.getY()) {
            polygon.setPosition(polygon.getX(), (float) (polygon.getY() + speed));
        }
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }
}