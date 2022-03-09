package com.mygdx.game.control;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Polygon;

public class PlayerControl {

    private final Polygon polygon;

    public PlayerControl(Polygon polygon){
        this.polygon = polygon;
    }

    /**
     * Change the position of the player according to the pressed button.
     */
    public void handle() {

        // RIGHT
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            polygon.setPosition(polygon.getX() + 1, polygon.getY());
        }

        // LEFT
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            polygon.setPosition(polygon.getX() - 1, polygon.getY());
        }

        // UP
        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
            polygon.setPosition(polygon.getX(), polygon.getY() + 1);
        }

        // DOWN
        if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            polygon.setPosition(polygon.getX(), polygon.getY() - 1);
        }
    }
}
