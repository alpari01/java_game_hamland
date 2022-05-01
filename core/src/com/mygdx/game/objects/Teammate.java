package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.Texture;

public class Teammate extends GameObject {

    private int hp;

    public Teammate(Texture texture, float x, float y, float width, float height) {
        super(texture, x, y, width, height);
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
}
