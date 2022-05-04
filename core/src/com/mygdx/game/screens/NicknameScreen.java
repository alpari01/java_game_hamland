package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.GameClient;
import com.mygdx.game.client.KryoClient;
import com.mygdx.game.objects.Button;

public class NicknameScreen implements Screen, Input.TextInputListener {

    private final GameClient gameClient;
    private SpriteBatch batch;

    // Properties
    public static final float ENTER_NAME_BUTTON_X = (float) GameClient.WIDTH / 2;
    public static final float ENTER_NAME_BUTTON_Y = (float) GameClient.HEIGHT / 2 - 150;
    public static final float ENTER_NAME_BUTTON_WIDTH = 200f;
    public static final float ENTER_NAME_BUTTON_HEIGHT = (float) 817 / 1116 * ENTER_NAME_BUTTON_WIDTH;

    public static final int WELCOME_X = GameClient.WIDTH / 2 - 180;
    public static final int WELCOME_Y = GameClient.HEIGHT / 2 - 15;
    public static final int WELCOME_WIDTH = 385;
    public static final int WELCOME_HEIGHT = 305;

    // Textures
    private Texture playButtonTexture;
    private Texture playButtonWhiteTexture;
    private Texture backgroundTexture;
    private Texture welcomeTexture;

    // Objects
    private Button playButton;
    private Button playButtonWhite;

    // Text input window
    private String nickname;
    public static boolean isWindowOpened;

    public NicknameScreen(GameClient gameClient) {
        this.gameClient = gameClient;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();

        // Start the music.
        gameClient.getMusic().setLooping(true);
        gameClient.getMusic().play();

        // Textures
        playButtonTexture = new Texture("enter_name_active.png");
        playButtonWhiteTexture = new Texture("enter_name_inactive.png");
        backgroundTexture = new Texture("background.png");
        welcomeTexture = new Texture("welcome.png");

        // Button objects
        playButton = new Button(playButtonTexture, ENTER_NAME_BUTTON_X, ENTER_NAME_BUTTON_Y, ENTER_NAME_BUTTON_WIDTH, ENTER_NAME_BUTTON_HEIGHT);
        playButtonWhite = new Button(playButtonWhiteTexture, ENTER_NAME_BUTTON_X,ENTER_NAME_BUTTON_Y, ENTER_NAME_BUTTON_WIDTH,ENTER_NAME_BUTTON_HEIGHT);
    }

    @Override
    public void render(float delta) {

        Gdx.graphics.setTitle("Nickname (" + Gdx.graphics.getFramesPerSecond() + "FPS)");

        batch.begin(); // start

        batch.draw(backgroundTexture, 0, 0, GameClient.WIDTH, GameClient.HEIGHT);
        batch.draw(welcomeTexture, WELCOME_X, WELCOME_Y, WELCOME_WIDTH, WELCOME_HEIGHT);

        // if mouse X-coordinate and Y-coordinate on the button
        if (Gdx.input.getX() > playButton.polygon.getX() && Gdx.input.getX() < playButton.polygon.getX() + ENTER_NAME_BUTTON_WIDTH &&
                GameClient.HEIGHT - Gdx.input.getY() - 15 > playButton.polygon.getY() && GameClient.HEIGHT - Gdx.input.getY() + 20 < playButton.polygon.getY() + ENTER_NAME_BUTTON_HEIGHT) {
            playButton.draw(batch); // draw in color selected button

            // if click - open text input window
            if (Gdx.input.justTouched() && !isWindowOpened) {
                isWindowOpened = true;
                Gdx.input.getTextInput(this, "Enter your name", "", "name");

                if (!gameClient.client.isToServerConnected) gameClient.client.connectToServer();
            }

        } else {
            playButtonWhite.draw(batch); // draw transparent buttons
        }

        // If nickname is entered
        if (nickname != null) {
            if (nickname.trim().length() == 0) {
                // Check if nickname is not 'spaces only'.
                System.out.println("Nickname cannot contain only 'spaces'.");
                isWindowOpened = false;
            } else if (nickname.trim().length() < 3) {
                // Check if nickname is longer that 3 characters.
                System.out.println("Nickname must be longer than 2 characters.");
                isWindowOpened = false;
            }

            // Send a request to the server to check if player's nickname is unique.
            else gameClient.client.sendPacketCheckNickname(nickname);
        }

        // If player's nickname is unique -> proceed and show menu screen.
        if (KryoClient.isNicknameUnique) {
            gameClient.setScreen(new MenuScreen(gameClient));

            // When client has connected to the server -> send packet to discover if there are any players on the server already.
            gameClient.client.sendPacketRequestAllPlayersConnected();
        }

        if (!KryoClient.isNicknameUnique) {
            nickname = null;
        }

        batch.end(); //end
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
        playButtonTexture.dispose();
        playButtonWhiteTexture.dispose();
        backgroundTexture.dispose();
        welcomeTexture.dispose();
        gameClient.dispose();
    }

    @Override
    public void input(String text) {
        this.nickname = text;
    }

    @Override
    public void canceled() {
        isWindowOpened = false;
    }
}
