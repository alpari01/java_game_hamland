package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.Texture;

public class Player extends GameObject {

    private final String ip;

    /**
     * Constructor for all objects on the screen.
     *
     * @param x       X-coordinate.
     * @param y       Y-coordinate.
     * @param width   object width.
     * @param height  object height.
     * @param ip      player IP address.
     */
    public Player(float x, float y, float width, float height, String ip) {
        super(x, y, width, height);
        this.ip = ip;
    }
}
