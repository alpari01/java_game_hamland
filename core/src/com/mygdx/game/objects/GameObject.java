package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Polygon;

public abstract class GameObject {

    public Polygon polygon;
    public Sprite sprite;

    /**
     * Constructor for all objects on the screen.
     * @param texture object texture.
     * @param x X-coordinate.
     * @param y Y-coordinate.
     * @param width object width.
     * @param height object height.
     */
    public GameObject(Texture texture, float x, float y, float width, float height) {

        // Create a Sprite with given texture
        this.sprite = new Sprite(texture);
        this.sprite.setSize(width, height); // resize the texture
        this.sprite.setOrigin(width / 2f, height / 2f); // set texture center
        this.sprite.setPosition(x - width / 2f, y - height / 2f); // set texture position

        // Create a Polygon with given vertices
        this.polygon = new Polygon(new float[]{0f, 0f, width, 0f, width, height, 0f, height});
        this.polygon.setOrigin(width / 2f, height / 2f); // set polygon center
        this.polygon.setPosition(x - width / 2f, y - height / 2f); // set polygon position

    }

    /**
     * Draw an object on the batch.
     * @param batch batch.
     */
    public void draw(SpriteBatch batch) {
        sprite.setPosition(polygon.getX(), polygon.getY()); // set Sprite position equal to Polygon position
        sprite.setRotation(polygon.getRotation()); // set Sprite rotation around the Polygon center
        sprite.draw(batch); // draw an object on the batch
    }

    public void setSpriteDeadPlayer() {
        this.sprite.setColor(0.5f, 0.5f, 0.5f, 0.5f);
    }
}
