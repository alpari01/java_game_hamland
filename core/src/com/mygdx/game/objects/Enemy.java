package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

public class Enemy extends GameObject {

    private int hp;

    private float time = 0;
    private final Random random = new Random();
    private final BitmapFont font = new BitmapFont();

    public Enemy(Texture texture, float x, float y, float width, float height, int hp) {
        super(texture, x, y, width, height);
        this.hp = hp;
        font.setColor(1, 0, 0, 1);
    }

    public void draw(SpriteBatch batch, float delta) {
        sprite.setPosition(polygon.getX(), polygon.getY()); // set Sprite position equal to Polygon position
        sprite.setRotation(polygon.getRotation()); // set Sprite rotation around the Polygon center

        // If the enemy is alive, he is drawn on the map and chases the player.
        if (isAlive()) {
            sprite.draw(batch);
            font.draw(batch, String.valueOf(hp), polygon.getX(), polygon.getY());
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
