package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.screens.PlayScreen;

public class Teammate extends GameObject {

    public static final int BLOOD_TEXTURE_WIDTH = 200;
    public static final int BLOOD_TEXTURE_HEIGHT = 200;

    private int hp;
    private boolean isDamaged;

    // Blood animation
    private float timer = 0;
    private int bloodTextureIndex = 0;
    private final TextureRegion[] textureRegions;

    public Teammate(Texture texture, float x, float y, float width, float height) {
        super(texture, x, y, width, height);

        // Blood textures
        TextureAtlas bloodAtlas = new TextureAtlas("animations/blood.atlas");
        textureRegions = new TextureRegion[16];
        for (int i = 0; i < 16; i++) {
            TextureRegion textureRegion = bloodAtlas.findRegion("image" + i);
            textureRegions[i] = textureRegion;
        }
    }

    public void draw(SpriteBatch batch, float delta) {
        super.draw(batch);
        damageAnimation(delta, batch);
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getHp() {
        return this.hp;
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public void setDamaged(boolean damaged) {
        isDamaged = damaged;
    }

    /**
     * Draw blood animation.
     * @param delta delta.
     * @param batch batch.
     */
    public void damageAnimation(float delta, SpriteBatch batch) {
        if (isDamaged) {
            if (bloodTextureIndex == 16) {
                isDamaged = false;
                bloodTextureIndex = 0;
            }
            timer += delta;
            batch.draw(textureRegions[bloodTextureIndex],
                    polygon.getX() - (float) BLOOD_TEXTURE_WIDTH / 2 + (float) PlayScreen.PLAYER_WIDTH / 2,
                    polygon.getY() - (float) BLOOD_TEXTURE_HEIGHT / 2 + (float) PlayScreen.PLAYER_HEIGHT / 2,
                    BLOOD_TEXTURE_WIDTH, BLOOD_TEXTURE_HEIGHT);
            if (timer > 0.02) {
                bloodTextureIndex++;
                timer = 0;
            }
        }
    }
}
