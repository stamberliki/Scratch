package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;


public class mainMenu implements Screen {
    SpriteBatch batch;
    TextureAtlas charAtlas;
    com.badlogic.gdx.graphics.g2d.Animation animation;
    Skin skin;
    Stage stage;
    TextButton gameStart,options,help;
    private tess_interface tess;
    private Game game;

    private static final int buttonWidth = 300;
    private static final int buttonHeight = 50;


    public mainMenu(tess_interface tess, Game game){
        this.tess = tess;
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        stage = new Stage();

        gameStart = new TextButton("Play",skin);
        gameStart.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                dispose();
                game.setScreen(new game(tess));
            }
        });
        gameStart.setBounds(Gdx.graphics.getWidth()/2 - buttonWidth/2,
                Gdx.graphics.getHeight()/2 - buttonHeight/2 + buttonHeight*2,
                buttonWidth,buttonHeight);

        options = new TextButton("Options",skin);
        options.setBounds(Gdx.graphics.getWidth()/2 - buttonWidth/2,
                Gdx.graphics.getHeight()/2 - buttonHeight/2,
                buttonWidth,buttonHeight);

        help = new TextButton("Help",skin);
        help.setBounds(Gdx.graphics.getWidth()/2 - buttonWidth/2,
                Gdx.graphics.getHeight()/2 - buttonHeight/2 - buttonHeight*2,
                buttonWidth,buttonHeight);

        stage.addActor(gameStart);
        stage.addActor(options);
        stage.addActor(help);
        Gdx.input.setInputProcessor(stage);
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
        this.stage.dispose();
    }

}
