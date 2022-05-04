package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.GameClient;
import com.mygdx.game.objects.Button;

public class LobbyScreen implements Screen {

    private final GameClient gameClient;
    private SpriteBatch batch;

    // Properties
    public static final float EXIT_BUTTON_WIDTH = 200f;
    public static final float EXIT_BUTTON_HEIGHT = (float) 1264 / 1383 * EXIT_BUTTON_WIDTH;
    public static final int EXIT_BUTTON_X = 175;
    public static final int EXIT_BUTTON_Y = 100;

    public static final float READY_BUTTON_WIDTH = 170f;
    public static final float READY_BUTTON_HEIGHT = (float) 808 / 1107 * READY_BUTTON_WIDTH;
    public static final int READY_BUTTON_X = 650;
    public static final int READY_BUTTON_Y = 120;

    // Textures
    private Texture exitButtonActiveTexture;
    private Texture exitButtonInactiveTexture;
    private Texture readyButtonActiveTexture;
    private Texture readyButtonInactiveTexture;
    private Texture backgroundTexture;

    // Objects
    private Button exitButtonActive;
    private Button exitButtonInactive;
    private Button readyButtonActive;
    private Button readyButtonInactive;

    public LobbyScreen(GameClient gameClient) {
        this.gameClient = gameClient;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();

        // Textures
        exitButtonActiveTexture = new Texture("buttons/exit_button_active.png");
        exitButtonInactiveTexture = new Texture("buttons/exit_button_inactive.png");
        readyButtonActiveTexture = new Texture("buttons/ready_button_active.png");
        readyButtonInactiveTexture = new Texture("buttons/ready_button_inactive.png");
        backgroundTexture = new Texture("background/background3.png");

        // Button objects
        exitButtonActive = new Button(exitButtonActiveTexture, EXIT_BUTTON_X, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
        exitButtonInactive = new Button(exitButtonInactiveTexture, EXIT_BUTTON_X, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
        readyButtonActive = new Button(readyButtonActiveTexture, READY_BUTTON_X, READY_BUTTON_Y, READY_BUTTON_WIDTH, READY_BUTTON_HEIGHT);
        readyButtonInactive = new Button(readyButtonInactiveTexture, READY_BUTTON_X, READY_BUTTON_Y, READY_BUTTON_WIDTH, READY_BUTTON_HEIGHT);
    }

    @Override
    public void render(float delta) {

        Gdx.graphics.setTitle("Lobby (" + Gdx.graphics.getFramesPerSecond() + "FPS)");

        batch.begin(); // start

        batch.draw(backgroundTexture, 0, 0, GameClient.WIDTH, GameClient.HEIGHT);

        drawExitButton();

        batch.end(); //end
    }

    /**
     * Draw buttons and check if there is a cursor on them.
     * If yes - change the button to the active one.
     * If the exit button is pressed - change the screen to the menu screen.
     * If the ready button is pressed - ...
     */
    public void drawExitButton() {

        // if mouse X-coordinate and Y-coordinate on the exit button
        if (Gdx.input.getX() > exitButtonActive.polygon.getX()
         && Gdx.input.getX() + 30 < exitButtonActive.polygon.getX() + EXIT_BUTTON_WIDTH
         && GameClient.HEIGHT - Gdx.input.getY() - 45 > exitButtonActive.polygon.getY()
         && GameClient.HEIGHT - Gdx.input.getY() + 30 < exitButtonActive.polygon.getY() + EXIT_BUTTON_HEIGHT) {

            exitButtonActive.draw(batch); // draw in color selected button

            // if click - set screen to MenuScreen
            if (Gdx.input.isTouched()) {
                gameClient.setScreen(new MenuScreen(gameClient));
            }

        } else {
            exitButtonInactive.draw(batch); // draw transparent exit button
        }

        // if mouse X-coordinate and Y-coordinate on the ready button
        if (Gdx.input.getX() > readyButtonActive.polygon.getX()
                && Gdx.input.getX() < readyButtonActive.polygon.getX() + READY_BUTTON_WIDTH
                && GameClient.HEIGHT - Gdx.input.getY() > readyButtonActive.polygon.getY()
                && GameClient.HEIGHT - Gdx.input.getY() + 22 < readyButtonActive.polygon.getY() + READY_BUTTON_HEIGHT) {

            readyButtonActive.draw(batch); // draw in color selected button

            // if click - set screen to PlayScreen
            if (Gdx.input.isTouched()) {
                gameClient.setScreen(new PlayScreen(gameClient));
            }

        } else {
            readyButtonInactive.draw(batch); // draw transparent ready button
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

        // Clear memory when game is off
        batch.dispose();
        exitButtonActiveTexture.dispose();
        exitButtonInactiveTexture.dispose();
        readyButtonActiveTexture.dispose();
        readyButtonInactiveTexture.dispose();
        backgroundTexture.dispose();
        gameClient.dispose();
    }
}
