package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.tess_interface;


public class mainMenu implements Screen{
    private SpriteBatch batch;
    private Skin skin;
    private Stage stage;
    private tess_interface tess;
    private MyGdxGame game;
    private Texture backgroundTexture,titleTexture;
    private ImageButton play,options;
    private Screen thisScreen;
    private Preferences audioPref;

    private int screenWidth = Gdx.graphics.getWidth();
    private int screenHeight = Gdx.graphics.getHeight();
    private float aspectRatio = screenWidth/screenHeight;
    private float buttonWidth = screenWidth*0.2f;
    private float buttonHeight = buttonWidth*aspectRatio;
    private float titleHeight = screenHeight*0.4f;
    private float titleWidth = titleHeight;

    public mainMenu(tess_interface tess, MyGdxGame game){
        this.tess = tess;
        this.game = game;
        this.skin = game.getSkin();
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        stage = new Stage();
        thisScreen = this;
        audioPref = game.getAudioManager().getPreferences();

        backgroundTexture = new Texture(Gdx.files.internal("bg.jpg"));

        play = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/BUTTON-PLAY.png")))));
        play.setBounds(screenWidth/2-(buttonWidth/2),screenHeight*0.15f,
                buttonWidth,buttonHeight);
        play.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new levelSelect(tess,game,thisScreen));
            }
        });

        options = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/BUTTON-SETTINGS.png")))));
        options.setBounds(screenWidth*0.01f,screenHeight*0.90f,
                screenWidth*0.08f,screenHeight*0.08f);
        options.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new options(thisScreen,game));
                hide();
            }
        });

        titleTexture = new Texture(Gdx.files.internal("ui/JUANDER.png"));

        if (!audioPref.getBoolean("menuAudioOn")){
            game.getAudioManager().getMenuMusic().play();
            audioPref.putBoolean("menuAudioOn",true);
            audioPref.flush();
        }

        stage.addActor(play);
        stage.addActor(options);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(179/255f,141/255f,36/255f,8);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        float titleRatio = titleTexture.getWidth()/titleTexture.getHeight();

        batch.begin();

        batch.draw(backgroundTexture,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        batch.draw(titleTexture,screenWidth/2-(titleWidth*titleRatio/2),screenHeight*0.40f,
                titleWidth*titleRatio,titleHeight);

        batch.end();
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
        this.batch.dispose();
        this.stage.dispose();
    }

}
