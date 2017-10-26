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
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.tess_interface;

import java.util.ArrayList;

public class levelSelect implements Screen {
    private SpriteBatch batch;
    private Skin skin;
    private tess_interface tess;
    private MyGdxGame game;
    private Stage stage;
    private TextButton levelButton;
    private ArrayList<Button> buttonList;
    private Texture backTexture;
    private ImageButton backBtn;
    private Screen prevScreen,thisScreen;

    private float backWidth = Gdx.graphics.getWidth()*0.1f;
    private float backHeight = Gdx.graphics.getHeight()*0.1f;
    private float backX = Gdx.graphics.getWidth()*0.03f;
    private float backY = Gdx.graphics.getHeight()*0.95f-backHeight;

    public levelSelect(tess_interface tess,MyGdxGame game,Screen prevScreen){
        this.tess = tess;
        this.game = game;
        this.prevScreen = prevScreen;
    }

    public levelSelect(tess_interface tess,MyGdxGame game){
        this.tess = tess;
        this.game = game;
    }

    @Override
    public void show() {
        thisScreen = this;
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        batch = new SpriteBatch();
        buttonList = new ArrayList<Button>();
        skin = game.getSkin();

        backTexture = new Texture(Gdx.files.internal("ui/BUTTON-PLAY.png"));

        backBtn = new ImageButton(new TextureRegionDrawable(new TextureRegion(backTexture)));
        backBtn.setBounds(backX,backY,backWidth,backHeight);
        backBtn.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(prevScreen);
            }
        });

        for (int level = 0 ; level != 8 ; level++){
            final int finalLevel = level;
            levelButton = new TextButton(Integer.toString(level+1),skin);
            levelButton.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    game.getAudioManager().getMenuMusic().pause();
                    dispose();
                    game.setScreen(new game(tess,game,thisScreen,finalLevel+1));
                }
            });
            buttonList.add(levelButton);
        }

        Table scrollTable = new Table();
        for (Button list : buttonList) {
            Table levelTable = new Table();
            levelTable.row();
            levelTable.add(list).expand().fill().pad(screenHeight*0.08f,0,screenHeight*0.02f,0);
            scrollTable.row();
            scrollTable.add(levelTable).expandX().fillX();
        }

        ScrollPane scrollPane = new ScrollPane(scrollTable);
        scrollPane.setBounds(screenWidth*0.2f,0,screenWidth*0.6f,screenHeight*0.7f);
        scrollPane.setFlingTime(0.1f);

        stage = new Stage();
        stage.addActor(scrollPane);
        stage.addActor(backBtn);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(179/255f,141/255f,36/255f,8);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        stage.act(Gdx.graphics.getDeltaTime());
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
