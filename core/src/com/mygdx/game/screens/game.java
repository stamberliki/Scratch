package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class game implements Screen {
    private SpriteBatch batch;
    private character character;
    private character_interface hero;
    private Skin skin;
    private TextArea code;
    private Stage stage;
    private TextButton pictureBtn,runCode;
    private tess_interface tess;
    private Game game;
    private float elapsedTime = 0;
    private map Map;
    private codeParser codeParser;
    private OrthographicCamera camera;
    final StringBuilder build = new StringBuilder();

    public game(tess_interface tess,Game game){
        this.tess = tess;
        this.game = game;
        Map = new map("level1.tmx");
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        code = new TextArea("",skin);
        stage = new Stage();
        character = new character(9*16,9*16);
        hero = character;
        codeParser = new codeParser(character);
        character.setCodeParser(codeParser);

        pictureBtn = new TextButton("OCR",skin);
        pictureBtn.setBounds(0,0,100,50);
        pictureBtn.addListener(tess.setGallerySelect());

        code.setBounds(Gdx.graphics.getWidth()/2,0,Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight());
        code.setFocusTraversal(false);
        code.setScale(200);
        code.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char c) {
                if ( c == '\n'){
                    code.setMessageText(build.append("\n").toString());
                }
            }
        });

        runCode = new TextButton("Run",skin);
        runCode.setBounds(100,0,100,50);
        runCode.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (!character.isRunning()) {
                    character.setRunning(true);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            tess.runCode(code.getText(), hero);
                        }
                    }).start();
                }
            }
        });

        camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        camera.setToOrtho(false);
        camera.viewportWidth = Map.getMapWidth()*0.8f;
        camera.viewportHeight = Map.getMapHeight()*0.8f;
        camera.update();

        Map.setViewportHeight(camera.viewportHeight);
        Map.setViewportWidth(camera.viewportWidth);

        stage.addActor(code);
        stage.addActor(pictureBtn);
        stage.addActor(runCode);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(17, 10, 10, -1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        elapsedTime += Gdx.graphics.getDeltaTime();

        camera.position.set((character.getX()+camera.viewportWidth/4)+16, character.getY()+16,0);
        camera.update();

        batch.setProjectionMatrix(camera.combined);

        Map.setX(camera.position.x);
        Map.setY(camera.position.y);

        Map.draw(batch);

        batch.begin();

        character.draw(batch,elapsedTime);

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
