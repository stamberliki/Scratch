package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.tess_interface;

public class splash implements Screen {
    SpriteBatch batch;
    Stage stage;
    private tess_interface tess;
    private MyGdxGame game;
    private Music bgmusic;
    private Texture splashScreen;
    private com.mygdx.game.audioManager audioManager;


    public splash(tess_interface tess, MyGdxGame game){
        this.tess = tess;
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        stage = new Stage();
        bgmusic = Gdx.audio.newMusic(Gdx.files.internal("audio/Sheep sound.mp3"));

        splashScreen = new Texture(Gdx.files.internal("SPLASH SCREEN.png"));

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                bgmusic.play();
            }
        },2);
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                game.setScreen(new mainMenu(tess,game));
            }
        },4);

    }

    @Override
    public void render(float delta) {
        float aspectRatio = (float) splashScreen.getHeight() / (float) splashScreen.getWidth();
        float splashWidth = Gdx.graphics.getWidth()*0.2f;
        float splashHeight = splashWidth*aspectRatio;
        Gdx.gl.glClearColor(164/255f,164/255f,164/255f,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        batch.draw(splashScreen,Gdx.graphics.getWidth()/2-(splashWidth/2),Gdx.graphics.getHeight()/2-(splashHeight/2),splashWidth,splashHeight);

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
        this.bgmusic.dispose();
    }

}
