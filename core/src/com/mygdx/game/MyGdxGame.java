package com.mygdx.game;

import com.badlogic.gdx.Game;

public class MyGdxGame extends Game {


	@Override
	public void create() {
		setScreen(new splash());
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

