package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.character_interface;
import com.mygdx.game.entity.character;
import com.mygdx.game.entity.coin;
import com.mygdx.game.tess_interface;

public class game implements Screen,GestureDetector.GestureListener {
    private SpriteBatch batch;
    private com.mygdx.game.entity.character character;
    private character_interface hero;
    private Skin skin;
    private TextArea code;
    private Stage stage;
    private TextButton pictureBtn,runCode;
    private tess_interface tess;
    private Game game;
    private float elapsedTime;
    private com.mygdx.game.entity.map Map;
    private com.mygdx.game.entity.coin[] coin;
    private OrthographicCamera camera;
    private InputMultiplexer inputMultiplexer;

    private final StringBuilder build = new StringBuilder();

    public game(tess_interface tess,Game game,String mapLevel){
        this.tess = tess;
        this.game = game;
        Map = new com.mygdx.game.entity.map(mapLevel+".tmx");
        elapsedTime = 0;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        code = new TextArea("",skin);
        stage = new Stage();
        character = new character(9*16,9*16);
        coin = new coin[2];
        hero = character;

        pictureBtn = new TextButton("OCR",skin);
        pictureBtn.setBounds(0,0,100,50);
        pictureBtn.addListener(tess.setGallerySelect());

        code.setBounds((Gdx.graphics.getWidth()/2)*1.25f,0,(Gdx.graphics.getWidth()/2)*0.75f,Gdx.graphics.getHeight());
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
                            tess.runCode("hero.moveUp();", hero);
                        }
                    }).start();
                }
            }
        });

        coin[0] = new coin(9*16,10*16);
        coin[1] = new coin(9*16,11*16);
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

        GestureDetector gd = new GestureDetector(this);
        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(gd);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(17, 10, 10, -1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        elapsedTime += Gdx.graphics.getDeltaTime();

        camera.position.set((character.getX()+camera.viewportWidth/6)+16, character.getY()+16,0);
        camera.update();

        batch.setProjectionMatrix(camera.combined);

        Map.setX(camera.position.x);
        Map.setY(camera.position.y);

        Map.draw(batch);

        batch.begin();

        for (com.mygdx.game.entity.coin coinLocation : coin){
            if (Intersector.overlaps(coinLocation.getHitBox(),character.getHitBox())){
                coinLocation.isCollide = true;
            }
            coinLocation.draw(batch,elapsedTime);
        }

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

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        camera.translate(-deltaX,deltaY,0);
        camera.update();
        return true;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        camera.zoom = (initialDistance/distance)*0.5f;
        camera.update();
        return true;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }
}
