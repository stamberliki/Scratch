package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.model.Animation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.StringBuilder;
import com.sun.java_cup.internal.runtime.Scanner;

public class game implements Screen {
    SpriteBatch batch;
    character Char;
    Skin skin;
    TextArea code;
    Stage stage;
    TextButton pictureBtn,runCode;
    private  tess_interface tess;
    private Game game;
    private float elapsedTime = 0;
    private boolean run;
    private java.util.Scanner codeLineScaner;
    private TextureRegion currentState;

    public game(tess_interface tess,Game game){
        this.tess = tess;
        this.game = game;
        run = false;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        code = new TextArea("",skin);
        stage = new Stage();
        Char = new character(100,50);

        pictureBtn = new TextButton("OCR",skin);
        pictureBtn.setBounds(0,0,100,50);
        pictureBtn.addListener(tess.setGallerySelect());
        code.setBounds(Gdx.graphics.getWidth()/2,0,Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight());
        code.setFocusTraversal(false);
        code.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char c) {
                if ( c == '\n'){
                    code.setText(code.getText());
                    code.moveCursorLine(code.getLines()-1);
                }
            }

        });

        runCode = new TextButton("Run",skin);
        runCode.setBounds(100,0,100,50);
        runCode.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                run = true;
                Char.runCode(code.getText());
            }
        });

        Gdx.input.setInputProcessor(stage);
        stage.addActor(code);
        stage.addActor(pictureBtn);
        stage.addActor(runCode);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(17, 10, 10, -1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        elapsedTime += Gdx.graphics.getDeltaTime();

        if (run) {
            Char.render(elapsedTime);
            if (!Char.isRunning()){
                run = false;
            }
        }

        batch.begin();

        batch.draw(Char.currentState(elapsedTime,true),Char.getX(),Char.getY(),100,100);

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
        if (!tess.getCode().isEmpty()){
            code.setText(tess.getCode());
            tess.setCodeOCR("");
        }
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
