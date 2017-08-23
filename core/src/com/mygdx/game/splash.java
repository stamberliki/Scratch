package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Created by Anony on 7/28/2017.
 */

public class splash implements Screen {
    SpriteBatch batch;
    com.badlogic.gdx.graphics.g2d.Animation animation;
    Skin skin;
    Stage stage;
    Label splashText;
    private tess_interface tess;
    private Game game;

    public splash(tess_interface tess, Game game){
        this.tess = tess;
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        stage = new Stage();
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        splashText = new Label("splash screen '\n' tap to continue ",skin);
        splashText.setBounds(0,0,0,0);

        stage.addActor(splashText);

        InputMultiplexer inputMultiplexer = new InputMultiplexer();
//        inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(new InputAdapter(){
            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                game.setScreen(new mainMenu(tess,game));
                return true;
            }
        });
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(17, 10, 10, -1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        stage.draw();

        batch.end();
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
        this.batch.dispose();
        Gdx.input.setInputProcessor(null);
        this.stage.dispose();
    }

}
