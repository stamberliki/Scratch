package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class MyGdxGame extends Game {
	private tess_interface tess;
	private com.mygdx.game.screens.splash SplashScreen;
	private audioManager audioManager;
	private Skin skin;

	public MyGdxGame(tess_interface tess){
		this.tess = tess;
		SplashScreen = new com.mygdx.game.screens.splash(tess,this);
	}

	@Override
	public void create() {
		audioManager = new audioManager();
		skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
		skin.getFont("default-font").getData().setScale(Gdx.graphics.getDensity()/1.6f);
		setScreen(SplashScreen);
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width,height);
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}

	@Override
	public void dispose () {
		super.dispose();
	}

	public Skin getSkin(){return skin;}

	public audioManager getAudioManager(){return audioManager;}

}