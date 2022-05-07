package com.mygdx.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Enemy extends GameObject {

    private int hp;
    private BitmapFont font;

    public Enemy(Texture texture, float x, float y, float width, float height, int hp) {
        super(texture, x, y, width, height);
        this.hp = hp;
        font = new BitmapFont(Gdx.files.internal("fonts/red.fnt"));
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
