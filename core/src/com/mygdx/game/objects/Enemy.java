package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Enemy extends GameObject {

    public static final int HP_WIDTH = 50;
    public static final int HP_HEIGHT = 5;

    // HP
    private final int maxHp;
    private int hp;
    private final Texture hpBar;
    private final Texture hpTexture;
    private final Texture hpEmptyTexture;

    public Enemy(Texture texture, float x, float y, float width, float height, int hp, int maxHp) {
        super(texture, x, y, width, height);
        this.maxHp = maxHp;
        this.hp = hp;

        // HP
        hpBar = new Texture("background/hp_bar.png");
        hpTexture = new Texture("background/hp.png");
        hpEmptyTexture = new Texture("background/hp_empty.png");
    }

    public void draw(SpriteBatch batch) {
        drawHP(batch);

        sprite.setPosition(polygon.getX(), polygon.getY()); // set Sprite position equal to Polygon position
        sprite.setRotation(polygon.getRotation()); // set Sprite rotation around the Polygon center

        // If the enemy is alive, he is drawn on the map and chases the player.
        if (isAlive()) {
            sprite.draw(batch);
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

    /**
     * Draw a health bar on top of the enemy that changes depending on the remaining health.
     * @param batch batch.
     */
    public void drawHP(SpriteBatch batch) {

        // Empty hp
        batch.draw(hpEmptyTexture,
                polygon.getX() + width / 2f - HP_WIDTH / 2f,
                polygon.getY() + height + 10,
                HP_WIDTH,
                HP_HEIGHT);

        // Hp
        batch.draw(hpTexture,
                polygon.getX() + width / 2f - HP_WIDTH / 2f,
                polygon.getY() + height + 10,
                HP_WIDTH * (1f / maxHp) * hp,
                HP_HEIGHT);

        // Hp bar
        batch.draw(hpBar,
                polygon.getX() + width / 2f - HP_WIDTH / 2f,
                polygon.getY() + height + 10,
                HP_WIDTH,
                HP_HEIGHT);
    }
}
