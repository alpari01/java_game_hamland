package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Polygon;

public abstract class GameObject {

    public Polygon bounds;
    public Sprite object;

    public GameObject(Texture texture, float x, float y, float width, float height) {
        object = new Sprite(texture);
        object.setSize(width, height);
        object.setOrigin(width / 2f, height / 2f);
        object.setPosition(x - width / 2f, y - height / 2f);

        bounds = new Polygon(new float[]{0f, 0f, width, 0f, width, height, 0f, height});
        bounds.setPosition(x - width / 2f, y - height / 2f);
        bounds.setOrigin(width / 2f, height / 2f);
    }

    public void draw(SpriteBatch batch) {
        object.setPosition(bounds.getX(), bounds.getY());
        object.setRotation(bounds.getRotation());
        object.draw(batch);
    }
}
