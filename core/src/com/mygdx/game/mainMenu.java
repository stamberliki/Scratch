package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;


public class mainMenu implements Screen {
    SpriteBatch batch;
    TextureAtlas charAtlas;
    com.badlogic.gdx.graphics.g2d.Animation animation;
    Skin skin;
    Stage stage;

    @Override
    public void show() {
        batch = new SpriteBatch();
        charAtlas = new TextureAtlas(Gdx.files.internal("character.atlas"));
        animation = new com.badlogic.gdx.graphics.g2d.Animation(1/10f, charAtlas.getRegions());
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        stage = new Stage();
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
