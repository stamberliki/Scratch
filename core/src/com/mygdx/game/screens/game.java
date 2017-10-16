package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.JsonValue;
import com.mygdx.game.character_interface;
import com.mygdx.game.entity.PopUp;
import com.mygdx.game.entity.character;
import com.mygdx.game.entity.coin;
import com.mygdx.game.entity.conditions;
import com.mygdx.game.entity.coords;
import com.mygdx.game.jsonParser;
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
    private float elapsedTime,currentZoom,mapCurrentZoom;
    private com.mygdx.game.entity.map Map;
    private com.mygdx.game.entity.coin[] coin;
    private OrthographicCamera camera;
    private InputMultiplexer inputMultiplexer;
    private com.mygdx.game.entity.conditions conditions;
    private PopUp errorPopUp;
    private boolean error;
    private jsonParser jsonParser;
    private JsonValue res;
    private com.mygdx.game.entity.coords coords;
    private Music bgmusic;
    private int currentLevel;

    private final StringBuilder build = new StringBuilder();

    public game(tess_interface tess,Game game,Skin skin,int mapLevel){
        this.tess = tess;
        this.game = game;
        this.skin = skin;
        currentLevel = mapLevel;

        elapsedTime = 0;
        jsonParser = new jsonParser();
        res = jsonParser.getJson(Integer.toString(mapLevel));
        coords = new coords(res);
        Map = new com.mygdx.game.entity.map("level"+mapLevel+".tmx");
    }

    public void newGame(int level){
        this.dispose();
        game.setScreen(new game(tess,game,skin,level));
    }

    public void levelSelect(){
        this.dispose();
        game.setScreen(new levelSelect(tess,game,skin));
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        code = new TextArea("",skin);
        stage = new Stage();
        character = new character(coords.heroPosition[0]*16,coords.heroPosition[1]*16);
        hero = character;

        coin = new coin[coords.noOfCoin];
        for (int x = 0 ; x != coords.noOfCoin ; x++){
            coin[x] = new coin(coords.coinPositions[x][0]*16,coords.coinPositions[x][1]*16);
        }

        pictureBtn = new TextButton("OCR",skin);
        pictureBtn.setBounds(0,0,100,50);
        pictureBtn.addListener(tess.setGallerySelect());

        code.setBounds((Gdx.graphics.getWidth()/2)*1.25f,0,(Gdx.graphics.getWidth()/2)*0.75f,Gdx.graphics.getHeight());
        code.setFocusTraversal(false);
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
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (!character.isRunning) {
                    character.isRunning=true;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if (tess.runCode("hero.moveRight();hero.moveRight();hero.moveDown();hero.moveRight();", hero)){
                                    character.isRunning = false;
                                }
                            } catch (Exception e) {
                                float popUpWidth = Gdx.graphics.getWidth()*0.6f;
                                float popUpHeight = Gdx.graphics.getHeight()*0.6f;

                                error = true;
                            }
                        }
                    }).start();
                }
            }
        });

        camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        camera.setToOrtho(false);
        float mapAspectRatio = Map.getMapWidth() / Map.getMapHeight();
        camera.viewportHeight = Map.getMapHeight();
        camera.viewportWidth = camera.viewportHeight * mapAspectRatio;
        camera.position.set((character.getX() + camera.viewportWidth / 6) + 16, character.getY() + 16, 0);

        if(res.getBoolean("hasLimitBoundary")){
            if(res.getBoolean("hasLimitBoundary")){
                if (res.getInt("limitBoundaryX")*16*currentZoom < Map.getMapWidth() ){
                    camera.zoom = res.getInt("limitBoundaryX")*16/camera.viewportWidth;
                    currentZoom = camera.zoom;
                    Map.getCamera().zoom = currentZoom;
                    mapCurrentZoom = currentZoom;
                }
                if(res.getInt("limitBoundaryY")*16*currentZoom < Map.getMapHeight()){
                    camera.zoom = res.getInt("limitBoundaryY")*16/camera.viewportHeight;
                    currentZoom = camera.zoom;
                    Map.getCamera().zoom = currentZoom;
                    mapCurrentZoom = currentZoom;
                }
            }
        }

        camera.update();

        currentZoom = camera.zoom;
        mapCurrentZoom = Map.getCamera().zoom;

        Map.setViewportHeight(camera.viewportHeight);
        Map.setViewportWidth(camera.viewportWidth);

        stage.addActor(code);
        stage.addActor(pictureBtn);
        stage.addActor(runCode);

        errorPopUp = new PopUp(camera,stage,this,currentLevel);

        GestureDetector gd = new GestureDetector(this);
        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(gd);
        Gdx.input.setInputProcessor(inputMultiplexer);

        conditions = new conditions(0);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(17, 10, 10, -1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        elapsedTime += Gdx.graphics.getDeltaTime();



        if(character.isRunning ) {
            camera.position.set((character.getX() + camera.viewportWidth*currentZoom / 6) + 16, character.getY() + 16, 0);
        }

        checkEdgeTouch();
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

        MapObjects objects = Map.getMap().getLayers().get("blockObj").getObjects();
        for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)) {
            Rectangle rectangle = rectangleObject.getRectangle();
            if (Intersector.overlaps(rectangle, character.getHitBox())) {
                character.isBlocked = true;
            }
        }

        objects = Map.getMap().getLayers().get("finishBlock").getObjects();
        for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)) {
            Rectangle rectangle = rectangleObject.getRectangle();
            if (Intersector.overlaps(rectangle, character.getHitBox())) {
                conditions.setCharacterFinish(true);
            }
        }

        character.draw(batch,elapsedTime);

        batch.end();

        stage.draw();

       batch.begin();

        if (error) {
            errorPopUp.show(batch,camera,0,"");
            error = false;
        }
        if (conditions.isConditionsMeet()){
            errorPopUp.show(batch,camera,1,"");
        }
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
        return true;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        camera.translate(-deltaX*currentZoom,deltaY*currentZoom);
        camera.update();
        Map.getCamera().translate(-deltaX*currentZoom,deltaY*currentZoom);
        Map.getCamera().update();
        checkEdgeTouch();
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return true;
    }

    @Override
    public void pinchStop() {

    }

    public void checkEdgeTouch(){
        float halfWidth = camera.viewportWidth*camera.zoom/2;
        float halfHeight = camera.viewportHeight*camera.zoom/2;
        float specialRight = camera.viewportWidth*camera.zoom/6;
        if(res.getBoolean("hasLimitBoundary")){
            float boundaryY = ((res.getInt("limitBoundaryY")*16)*camera.zoom)/2;
            float boundaryX = ((res.getInt("limitBoundaryX")*16)*camera.zoom)/6;
            if (camera.position.y > boundaryX) camera.position.y = boundaryY;
            if (camera.position.x+(boundaryX) > res.getInt("limitBoundaryX")*16) camera.position.x = res.getInt("limitBoundaryX")*16-boundaryX;
        }
        else{
            if (camera.position.x+(specialRight) > Map.getMapWidth()) camera.position.x = Map.getMapWidth()- specialRight; //right
            if (camera.position.y-(halfHeight) < 0) camera.position.y = halfHeight; //below
        }
        if (camera.position.x-(halfWidth) < 0) camera.position.x = halfWidth; //left
        if (camera.position.y+(halfHeight) > Map.getMapHeight()) camera.position.y = Map.getMapHeight() - halfHeight;  //top
        camera.update();
    }

}
