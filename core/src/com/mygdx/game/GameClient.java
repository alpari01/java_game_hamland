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

	//Music and sounds.
	private static MusicMaster music;
	private static MusicMaster soundBulletShot;
	private static MusicMaster soundDamageTaken;
	private static MusicMaster soundExplosion;
	private static boolean isMusicUp;
	private static boolean isSoundUp;

	@Override
	public void create() {
		music = new MusicMaster("sounds/music_main.mp3");
		soundBulletShot = new MusicMaster("sounds/sound_shot.wav");
//		soundDamageTaken = new MusicMaster("sounds/sound_damage_taken.wav");
		soundExplosion = new MusicMaster("sounds/sound_explosion.wav");
		// Sound and music are on by default.
		isMusicUp = true;
		isSoundUp = true;

		Screen menuScreen = new NicknameScreen(this);
		setScreen(menuScreen);
	}

	public MusicMaster getMusic() {
		return music;
	}

	public MusicMaster getSoundBulletShot() {
		return soundBulletShot;
	}

	public MusicMaster getSoundDamageTaken() {
		return soundDamageTaken;
	}

	public MusicMaster getSoundExplosion() {
		return soundExplosion;
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
