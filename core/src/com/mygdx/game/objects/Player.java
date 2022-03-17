package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.control.PlayerControl;

import java.util.HashMap;
import java.util.Map;

public class Player extends GameObject {

    private PlayerControl playerControl;

    public Player(Texture texture, float x, float y, float width, float height) {
        super(texture, x, y, width, height);
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
        playerControl.handle();
    }
}
