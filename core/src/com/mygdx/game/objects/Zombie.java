package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.Texture;

public class Zombie extends Enemy {

    public Zombie(Texture texture, float x, float y, float width, float height, double speed,  int hp) {
        super(texture, x, y, width, height, speed, hp);
    }
}
