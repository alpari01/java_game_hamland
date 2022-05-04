package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.mygdx.game.client.KryoClient;
import com.mygdx.game.screens.NicknameScreen;

public class GameClient extends Game {

	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;

	// Create new client object.
	public KryoClient client = new KryoClient();

	//Music
	private static MusicMaster music;
	private static boolean isMusicUp;
	private static boolean isSoundUp;

	@Override
	public void create() {
		music = new MusicMaster("sounds/music_main.mp3");
		// Sound and music are on by default.
		isMusicUp = true;
		isSoundUp = true;

		Screen menuScreen = new NicknameScreen(this);
		setScreen(menuScreen);
	}

	public MusicMaster getMusic() {
		return music;
	}

	public void setIsMusicUp(boolean state) {
		isMusicUp = state;
	}

	public boolean getIsMusicUp() {
		return isMusicUp;
	}

	public void setIsSoundUp(boolean state) {
		isSoundUp = state;
	}

	public boolean getIsSoundUp() {
		return isSoundUp;
	}
}
