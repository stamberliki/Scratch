package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class levelSelect implements Screen {
    private SpriteBatch batch;
    private Skin skin;
    private tess_interface tess;
    private Game game;
    private TextButton level1;

    public levelSelect(tess_interface tess,Game game){
        this.tess = tess;
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        level1 = new TextButton("1",skin);
        level1.setBounds(10,10,10,10);
    }

    @Override
    public void render(float delta) {

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

    }
}
