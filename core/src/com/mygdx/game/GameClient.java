package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.mygdx.game.screens.NicknameScreen;

public class GameClient extends Game {

	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;

	@Override
	public void create() {
		Screen menuScreen = new NicknameScreen(this);
		setScreen(menuScreen);
	}
}
