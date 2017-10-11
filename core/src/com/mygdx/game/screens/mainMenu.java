package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.tess_interface;


public class mainMenu implements Screen {
    private SpriteBatch batch;
    private Skin skin,uiSkin;
    private Stage stage;
    private tess_interface tess;
    private Game game;
    private Texture playTexture,optionsTexture;
    private Table table;
    private com.mygdx.game.entity.buttonUI play,options;

    public mainMenu(tess_interface tess, Game game, Skin skin){
        this.tess = tess;
        this.game = game;
        this.skin = skin;
    }

    @Override
    public void show() {
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();
        float aspectRatio = screenWidth/screenHeight;
        float buttonWidth = screenWidth*0.2f;
        float buttonHeight = buttonWidth*aspectRatio;

        playTexture = new Texture(Gdx.files.internal("ui/BUTTON-PLAY.png"));
        optionsTexture = new Texture(Gdx.files.internal("ui/BUTTON-SETTING.png"));
        batch = new SpriteBatch();
        stage = new Stage();

        play = new com.mygdx.game.entity.buttonUI(playTexture,screenWidth*0.5f-(buttonWidth*1.2f),screenHeight*0.1f,
                buttonWidth,buttonHeight);
        options = new com.mygdx.game.entity.buttonUI(optionsTexture,screenWidth*0.5f+(buttonWidth*0.2f),screenHeight*0.1f,
                buttonWidth,buttonHeight);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(17, 10, 10, -1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        play.update(batch,Gdx.input.getX(),Gdx.input.getY());
        options.update(batch,Gdx.input.getX(),Gdx.input.getY());

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
