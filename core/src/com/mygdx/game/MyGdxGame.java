package com.mygdx.game;

import com.badlogic.gdx.Game;

public class MyGdxGame extends Game {
	private tess_interface tess;
	private com.mygdx.game.screens.splash SplashScreen;

	public MyGdxGame(tess_interface tess){
		this.tess = tess;
		SplashScreen = new com.mygdx.game.screens.splash(tess,this);
	}

	@Override
	public void create() {
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

}