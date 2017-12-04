package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.jsonParser;
import com.mygdx.game.tess_interface;

import java.util.ArrayList;

public class levelSelect implements Screen {
    private SpriteBatch batch;
    private Skin skin;
    private tess_interface tess;
    private MyGdxGame game;
    private Stage stage;
    private ArrayList<Button> buttonList;
    private Texture backTexture,backgroundTexture,levelSelectTexture;
    private ImageButton backBtn,levelButton;
    private Label stageName,description;
    private Screen prevScreen,thisScreen;
    private com.mygdx.game.jsonParser jsonParser;
    private ShapeRenderer shapeRenderer;

    private float aspectRatio = (float)Gdx.graphics.getWidth()/(float)Gdx.graphics.getHeight();
    private float backWidth = Gdx.graphics.getWidth()*0.1f;
    private float backHeight = Gdx.graphics.getHeight()*0.1f;
    private float backX = Gdx.graphics.getWidth()*0.03f;
    private float backY = Gdx.graphics.getHeight()*0.95f-backHeight;
    private float levelWidth = Gdx.graphics.getWidth()*0.6f;
    private float levelHeight = Gdx.graphics.getHeight()*0.3f;

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
        jsonParser = new jsonParser("levelSelection.json");
        shapeRenderer = new ShapeRenderer();

        backgroundTexture = new Texture(Gdx.files.internal("bg.jpg"));
        backTexture = new Texture(Gdx.files.internal("ui/BUTTON-BACK.png"));
        levelSelectTexture = new Texture(Gdx.files.internal("ui/TEXT-LEVEL-SELECT.png"));

        backBtn = new ImageButton(new TextureRegionDrawable(new TextureRegion(backTexture)));
        backBtn.setBounds(backX,backY,backWidth,backHeight);
        backBtn.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                dispose();
                game.setScreen(prevScreen);
            }
        });

        for (int level = 0 ; level != 4 ; level++){
            final int finalLevel = level;
            levelButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/BUTTON-PLAY.png")))));
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
        int x = 1;
        for (Button buttons : buttonList) {
            Table levelTable = new Table();
            Image image = new Image(new Texture(Gdx.files.internal("ui/lvl"+ x +".png")));
            description = new Label(jsonParser.getJson(Integer.toString(x)).getString("description"),skin);
            stageName = new Label(jsonParser.getJson(Integer.toString(x)).getString("name"),skin);
            stageName.setFontScale(Gdx.graphics.getDensity()*1.2f);
            x++;
            levelTable.row();
            levelTable.add(stageName).expand().pad(0,0,levelHeight*0.1f,0).fill();
            levelTable.row();
            levelTable.add(image).width(levelWidth).height(levelHeight);
            levelTable.row();
            levelTable.add(buttons).height(levelHeight*0.15f).width(levelWidth*0.3f)
                    .pad(-(levelHeight*0.25f),0,levelHeight*0.1f,levelWidth*0.05f).right();
            levelTable.row();
            levelTable.add(description).pad(-(levelHeight*1.45f),(levelWidth*0.2f),0,0).expand();
            scrollTable.row();
            scrollTable.add(levelTable).expandX().fillX().pad(0,0,Gdx.graphics.getHeight()*0.1f,0);
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
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(backgroundTexture,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        batch.end();

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA,GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0,0,0,0.4f);
        shapeRenderer.rect(Gdx.graphics.getWidth()*0.15f,0,Gdx.graphics.getWidth()*0.7f,Gdx.graphics.getHeight());
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        batch.begin();
        batch.draw(levelSelectTexture, (Gdx.graphics.getWidth()/2)-(Gdx.graphics.getWidth()*0.6f/2),(Gdx.graphics.getHeight()*0.75f),
                Gdx.graphics.getWidth()*0.6f,Gdx.graphics.getHeight()*0.15f);
        batch.end();

        stage.act(Gdx.graphics.getDeltaTime());
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
