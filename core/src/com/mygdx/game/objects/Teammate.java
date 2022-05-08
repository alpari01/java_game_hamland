package com.mygdx.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.screens.PlayScreen;

import java.util.Locale;

public class Teammate extends GameObject {

    public static final int BLOOD_TEXTURE_WIDTH = 200;
    public static final int BLOOD_TEXTURE_HEIGHT = 200;
    public static final int HP_WIDTH = 50;
    public static final int HP_HEIGHT = 5;

    private final String nickname;

    // HP
    private int hp;
    private boolean isDamaged;
    private final Texture hpBar;
    private final Texture hpTexture;
    private final Texture hpEmptyTexture;

    // Font
    private final BitmapFont font;
    private final GlyphLayout glyphLayout;

    // Blood animation
    private float timer = 0;
    private int bloodTextureIndex = 0;
    private final TextureRegion[] textureRegions;

    public Teammate(Texture texture, float x, float y, float width, float height, String nickname) {
        super(texture, x, y, width, height);

        this.nickname = nickname;

        // Font
        font = new BitmapFont(Gdx.files.internal("fonts/nickname.fnt"));
        glyphLayout = new GlyphLayout();

        // Blood textures
        TextureAtlas bloodAtlas = new TextureAtlas("animations/blood.atlas");
        textureRegions = new TextureRegion[16];
        for (int i = 0; i < 16; i++) {
            TextureRegion textureRegion = bloodAtlas.findRegion("image" + i);
            textureRegions[i] = textureRegion;
        }

        // HP
        hpBar = new Texture("background/hp_bar.png");
        hpTexture = new Texture("background/hp_player.png");
        hpEmptyTexture = new Texture("background/hp_empty.png");
    }

    public void draw(SpriteBatch batch, float delta) {
        super.draw(batch);
        drawNickname(batch);
        drawHP(batch);
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

    /**
     * Write the teammate's nickname above the character.
     * @param batch batch.
     */
    public void drawNickname(SpriteBatch batch) {
        glyphLayout.setText(font, "- " + nickname.toUpperCase(Locale.ROOT) + " -");
        font.draw(batch, glyphLayout,
                polygon.getX() + PlayScreen.PLAYER_WIDTH / 2f - glyphLayout.width / 2f,
                polygon.getY() + PlayScreen.PLAYER_HEIGHT + 35);
    }

    /**
     * Draw a health bar on top of the character that changes depending on the remaining health.
     * @param batch batch.
     */
    public void drawHP(SpriteBatch batch) {

        // Empty hp
        batch.draw(hpEmptyTexture,
                polygon.getX() + PlayScreen.PLAYER_WIDTH / 2f - HP_WIDTH / 2f,
                polygon.getY() + PlayScreen.PLAYER_HEIGHT + 10,
                HP_WIDTH,
                HP_HEIGHT);

        // Hp
        batch.draw(hpTexture,
                polygon.getX() + PlayScreen.PLAYER_WIDTH / 2f - HP_WIDTH / 2f,
                polygon.getY() + PlayScreen.PLAYER_HEIGHT + 10,
                HP_WIDTH * (1f / 3f) * hp,
                HP_HEIGHT);

        // Hp bar
        batch.draw(hpBar,
                polygon.getX() + PlayScreen.PLAYER_WIDTH / 2f - HP_WIDTH / 2f,
                polygon.getY() + PlayScreen.PLAYER_HEIGHT + 10,
                HP_WIDTH,
                HP_HEIGHT);
    }
}
