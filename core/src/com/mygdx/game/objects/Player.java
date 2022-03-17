package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.control.PlayerControl;

import java.util.HashMap;
import java.util.Map;

public class Player extends GameObject {

    private PlayerControl playerControl;
    private Map<String, Teammate> teammates;

    public Player(Texture texture, float x, float y, float width, float height) {
        super(texture, x, y, width, height);
        playerControl = new PlayerControl(polygon);
        this.teammates = new HashMap<>();  // Store here all teammates.
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
        playerControl.handle();
    }

    /**
     * Add teammate to teammates hashmap.
     *
     * @param nickname nickname of the teammate
     * @param teammate teammate object to add
     */
    public void addTeammate(String nickname, Teammate teammate) {
        if (!teammates.containsKey(nickname)) teammates.put(nickname, teammate);
    }
}
