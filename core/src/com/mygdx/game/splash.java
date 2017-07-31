package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Created by Anony on 7/28/2017.
 */

public class splash implements Screen {
    SpriteBatch batch;
    TextureAtlas charAtlas;
    com.badlogic.gdx.graphics.g2d.Animation animation;
    Skin skin;
    Stage stage;
    private tess_interface tess;

    public splash(tess_interface tess){
        this.tess = tess;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputAdapter(){
            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new game(tess));
                return true;
            }
        });
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
//        this.batch.dispose();
//        this.stage.dispose();
    }

}
