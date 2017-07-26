package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class splash implements Screen {
    SpriteBatch batch;
    Texture img;
    TextureAtlas charAtlas;
    com.badlogic.gdx.graphics.g2d.Animation animation;
    Skin skin;
    TextArea code;
    Stage stage;
    TextButton pictureBtn;
    private  tess_interface tess;

    public splash(tess_interface tess){
        this.tess = tess;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        charAtlas = new TextureAtlas(Gdx.files.internal("character.atlas"));
        animation = new com.badlogic.gdx.graphics.g2d.Animation(1/10f, charAtlas.getRegions());
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        code = new TextArea("",skin);
        pictureBtn = new TextButton("OCR",skin);
        pictureBtn.setHeight(50);
        pictureBtn.setWidth(300);
        pictureBtn.setY(300);
        pictureBtn.setX(0);
        pictureBtn.addListener(tess.setGallerySelect());
        code.setWidth(500);
        code.setHeight(300);
        code.setX(0);
        code.setY(0);
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        stage.addActor(code);
        stage.addActor(pictureBtn);
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

    }


    public void setCodeFromOCR(String codeOCR){
        code.setText(codeOCR);
    }
    public void setInputListener(InputListener event){ pictureBtn.addListener(event); }

}
