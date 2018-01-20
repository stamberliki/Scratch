package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.MyGdxGame;

public class options implements Screen{
    private ImageButton backBtn, muteButton;
    private Texture backTexture, backgroundTexture, uiBackground;
    private SpriteBatch batch;
    private Stage       stage;
    private Screen      prevScreen;
    private MyGdxGame   game;
    private Preferences audioPref;
    private TextureRegionDrawable muteTexture, unMuteTexture;
    private Label backgroundMusic;
    
    private int screenWidth = Gdx.graphics.getWidth();
    private int screenHeight = Gdx.graphics.getHeight();
    private float backWidth = screenWidth*0.1f;
    private float backHeight = screenHeight*0.1f;
    private float backX = screenWidth*0.03f;
    private float backY = screenHeight*0.95f-backHeight;
    private float buttonWidth = screenWidth*0.1f;
    private float buttonHeight = screenHeight*0.1f;

    public options(Screen prevScreen,MyGdxGame game){
        this.prevScreen = prevScreen;
        this.game = game;
    }

    @Override
    public void show() {
        stage = new Stage();
        batch = new SpriteBatch();
        backTexture = new Texture(Gdx.files.internal("ui/BUTTON-BACK.png"));
        backgroundTexture = new Texture(Gdx.files.internal("bg.jpg"));
        uiBackground = new Texture(Gdx.files.internal("ui/ui_background.png"));
        muteTexture = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/speaker_mute.png"))));
        unMuteTexture = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/speaker_unmute.png"))));
        audioPref =  game.getAudioManager().getPreferences();
        backgroundMusic = new Label("Background Music", game.getSkin());
        
        muteButton = new ImageButton(unMuteTexture, null, muteTexture);
        muteButton.setBounds(screenWidth*0.55f,screenHeight/2, buttonWidth, buttonHeight);
        muteButton.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
    
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (audioPref.getBoolean("menuAudioOn")){
                    game.getAudioManager().getMenuMusic().setVolume(0);
                    audioPref.putBoolean("menuAudioOn",false);
                    muteButton.setChecked(true);
                }
                else{
                    game.getAudioManager().getMenuMusic().setVolume(1);
                    audioPref.putBoolean("menuAudioOn",true);
                    muteButton.setChecked(false);
                }
                audioPref.flush();
            }
        });
    
        backgroundMusic.setPosition(screenWidth*0.35f, (muteButton.getX()+muteButton.getHeight())/2);
        backgroundMusic.setFontScale(Gdx.graphics.getDensity()*0.75f);
        
        backBtn = new ImageButton(new TextureRegionDrawable(new TextureRegion(backTexture)));
        backBtn.setBounds(backX,backY,backWidth,backHeight);
        backBtn.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                dispose();
                game.setScreen(prevScreen);
            }
        });

        stage.addActor(backBtn);
        stage.addActor(muteButton);
        stage.addActor(backgroundMusic);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(179/255f,141/255f,36/255f,8);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
    
        batch.draw(backgroundTexture,0,0,screenWidth,screenHeight);
        batch.draw(uiBackground,screenWidth*0.3f,screenHeight*0.2f,screenWidth*0.4f,screenHeight*0.6f);
        
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
