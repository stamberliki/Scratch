package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.tess_interface;

import java.util.ArrayList;

public class levelSelect implements Screen {
    private SpriteBatch batch;
    private Skin skin;
    private tess_interface tess;
    private Game game;
    private Stage stage;
    private TextButton level1,level2,level3,level4,level5,level6,level7,level8;
    private Table levelTable;

    public levelSelect(tess_interface tess,Game game){
        this.tess = tess;
        this.game = game;
    }

    @Override
    public void show() {
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float buttonHeight = screenHeight*0.15f;
        float buttonWidth = screenWidth*0.8f;
//        float firstRow = 0;//(screenHeight/2)+(buttonHeight*0.50f);
//        float secondRow = 0;//(screenHeight/2)-(buttonHeight*1.50f);
//        float firstColumn = 0;//(screenWidth/2)-(buttonWidth*3.5f);
//        float secondColumn = 0;//firstColumn+(buttonWidth*2);
//        float thirdColumn = 0;//secondColumn+(buttonWidth*2);
//        float forthColumn = 0;//thirdColumn+(buttonWidth*2);
        batch = new SpriteBatch();
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        level1 = new TextButton("1",skin);
        level1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                game.setScreen(new game(tess,game,"level1"));
            }
        });

        level2 = new TextButton("2",skin);
        level2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                game.setScreen(new game(tess,game,"level2"));
            }
        });

        level3 = new TextButton("3",skin);
        level3.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                game.setScreen(new game(tess,game,"level1"));
            }
        });

        level4 = new TextButton("4",skin);
        level4.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                game.setScreen(new game(tess,game,"level1"));
            }
        });

        level5 = new TextButton("5",skin);
        level5.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                game.setScreen(new game(tess,game,"level1"));
            }
        });

        level6 = new TextButton("6",skin);
        level6.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                game.setScreen(new game(tess,game,"level1"));
            }
        });

        level7 = new TextButton("7",skin);
        level7.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                game.setScreen(new game(tess,game,"level1"));
            }
        });

        level8 = new TextButton("8",skin);
        level8.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                game.setScreen(new game(tess,game,"level1"));
            }
        });

        levelTable = new Table();
        levelTable.setBounds(0,0,screenWidth,buttonHeight*8*1.05f);
        Table scrollTable = new Table();
        scrollTable.setBounds(0,0,screenWidth,screenHeight);
        ArrayList<Button> buttonList = new ArrayList<Button>();
        buttonList.add(level1);
        buttonList.add(level2);
        buttonList.add(level3);
        buttonList.add(level4);
        buttonList.add(level5);
        buttonList.add(level6);
        buttonList.add(level7);
        buttonList.add(level8);

        for (Button list : buttonList) {
            levelTable.row();
            levelTable.add(list).expand().fill().pad(screenHeight*0.05f,0,0,0);
        }

        scrollTable.add(levelTable).expand().fill();

        ScrollPane scrollPane = new ScrollPane(scrollTable);
        scrollPane.setBounds(0,0,screenWidth,scrollTable.getHeight());

        stage = new Stage();
        stage.addActor(scrollPane);
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
