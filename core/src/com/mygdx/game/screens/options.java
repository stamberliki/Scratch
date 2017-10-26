package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class options implements Screen{
    private ImageButton backBtn;
    private Texture backTexture;
    private SpriteBatch batch;
    private Stage stage;
    private Screen prevScreen;
    private Game game;

    private float backWidth = Gdx.graphics.getWidth()*0.1f;
    private float backHeight = Gdx.graphics.getHeight()*0.1f;
    private float backX = Gdx.graphics.getWidth()*0.03f;
    private float backY = Gdx.graphics.getHeight()*0.95f-backHeight;

    public options(Screen prevScreen,Game game){
        this.prevScreen = prevScreen;
        this.game = game;
    }

    @Override
    public void show() {
        stage = new Stage();
        batch = new SpriteBatch();
        backTexture = new Texture(Gdx.files.internal("ui/BUTTON-PLAY.png"));

        backBtn = new ImageButton(new TextureRegionDrawable(new TextureRegion(backTexture)));
        backBtn.setBounds(backX,backY,backWidth,backHeight);
        backBtn.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(prevScreen);
            }
        });

        stage.addActor(backBtn);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(179/255f,141/255f,36/255f,8);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        batch.end();
        stage.act();
        stage.draw();

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
        stage.dispose();
        batch.dispose();
    }
}
