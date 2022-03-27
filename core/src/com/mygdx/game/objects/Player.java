package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.control.PlayerControl;

public class Player extends GameObject {

    private PlayerControl playerControl;

    public Player(Texture texture, float x, float y, float width, float height) {
        super(texture, x, y, width, height);
        playerControl = new PlayerControl(polygon);
    }

    public void draw(SpriteBatch batch, Bullet bullet) {
        super.draw(batch);
        playerControl.handle(bullet);
    }
}
