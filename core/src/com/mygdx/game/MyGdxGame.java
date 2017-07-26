package com.mygdx.game;

import com.badlogic.gdx.Game;

public class MyGdxGame extends Game {
	private  tess_interface tess;

	public MyGdxGame(tess_interface tess){
		this.tess = tess;
	}

	@Override
	public void create() {
		setScreen(new splash(tess));
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