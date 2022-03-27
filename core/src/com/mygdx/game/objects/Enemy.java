package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Polygon;
import com.mygdx.game.GameClient;

import java.util.Random;

public class Enemy extends GameObject {

    private final double speed;
    private int hp;

    private float time = 0;
    private final Random random = new Random();

    public Enemy(Texture texture, float x, float y, float width, float height, double speed, int hp) {
        super(texture, x, y, width, height);
        this.speed = speed;
        this.hp = hp;
    }

    public void draw(SpriteBatch batch, float delta, Player player) {
        sprite.setPosition(polygon.getX(), polygon.getY()); // set Sprite position equal to Polygon position
        sprite.setRotation(polygon.getRotation()); // set Sprite rotation around the Polygon center
        if (isAlive()) {
            followPlayer(player.polygon);
            sprite.draw(batch);
        } else {
            time += delta;
            if (time > 0.5) {
                hp = 5;
                polygon.setPosition(random.nextInt(GameClient.WIDTH), random.nextInt(GameClient.HEIGHT));
                time = 0;
            }
        }
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
