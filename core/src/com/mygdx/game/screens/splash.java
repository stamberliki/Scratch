package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.tess_interface;

public class splash implements Screen {
    SpriteBatch batch;
    Skin skin;
    Stage stage;
    Label splashText;
    private tess_interface tess;
    private Game game;
    private Music bgmusic;

    public splash(tess_interface tess, Game game){
        this.tess = tess;
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        stage = new Stage();
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        skin.getFont("default-font").getData().setScale(Gdx.graphics.getDensity()/1.6f);

        splashText = new Label("splash screen \n tap to continue ",skin);
        splashText.setBounds(Gdx.graphics.getWidth()/2-100/2,Gdx.graphics.getHeight()/2-100/2,300,100);

        stage.addActor(splashText);

        bgmusic = Gdx.audio.newMusic(Gdx.files.internal("audio/menu.ogg"));
        bgmusic.play();
        bgmusic.setLooping(true);

        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(new InputAdapter(){
            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                game.setScreen(new mainMenu(tess,game,skin));
                return true;
            }
        });
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(179/255f,141/255f,36/255f,8);
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
