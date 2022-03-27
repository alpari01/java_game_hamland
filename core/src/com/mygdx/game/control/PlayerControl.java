package com.mygdx.game.control;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Polygon;
import com.mygdx.game.GameClient;

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

        // ROTATE TOWARDS MOUSE CURSOR
        polygon.setRotation((float) getMouseCursorAngle() - 90);
    }

    /**
     * Return the angle of the mouse cursor based on the position of the player
     * @return positive angle in deg.
     */
    public double getMouseCursorAngle() {
        double result = 0;

        // difference between player position and mouse cursor position
        float x = Gdx.input.getX() - (float) GameClient.WIDTH / 2;
        float y = Math.abs(Gdx.input.getY() - GameClient.HEIGHT) - (float) GameClient.HEIGHT / 2;

        // arc-tangent of the modulus of the ratio of coordinates (in deg)
        double angle = Math.abs(Math.atan(y / x) * 180 / Math.PI);

        // change the angle based on the quarter in which the mouse cursor is located
        if (x >= 0 && y > 0) result = angle;
        else if (x > 0 && y <= 0) result = 360 - angle;
        else if (x < 0 && y >= 0) result = 180 - angle;
        else if (x <= 0 && y < 0) result = 180 + angle;

        return result;
    }
}
