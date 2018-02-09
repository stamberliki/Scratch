package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.JsonValue;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.audioManager;
import com.mygdx.game.character_interface;
import com.mygdx.game.entity.PopUp;
import com.mygdx.game.entity.character;
import com.mygdx.game.entity.coin;
import com.mygdx.game.entity.conditions;
import com.mygdx.game.entity.enemy;
import com.mygdx.game.entity.gameData;
import com.mygdx.game.entity.gameException;
import com.mygdx.game.jsonParser;
import com.mygdx.game.tess_interface;

import java.util.List;

public class game implements Screen,GestureDetector.GestureListener,InputProcessor{
    private static final int SYNTAX_ERROR = 1;
    private static final int CHARACTER_BLOCK = 2;
    private static final int CHARACTER_DEAD = 3;
    
    private SpriteBatch batch;
    private com.mygdx.game.entity.character character;
    private character_interface hero;
    private Skin skin;
    private TextArea code;
    private Stage stage;
    private TextButton pictureBtn,runCode;
    private tess_interface tess;
    private MyGdxGame game;
    private float elapsedTime,currentZoom,mapCurrentZoom;
    private com.mygdx.game.entity.map Map;
    private com.mygdx.game.entity.coin[] coin;
    private OrthographicCamera camera;
    private InputMultiplexer inputMultiplexer;
    private com.mygdx.game.entity.conditions conditions;
    private PopUp popUp;
    private boolean error,isAlreadyRunning;
    private jsonParser                    jsonParser;
    private JsonValue                     res;
    private int                           currentLevel;
    private ShapeRenderer                 shapeRenderer;
    private Screen                        prevScreen;
    private ImageButton                   backBtn, muteButton;
    private Texture                       backTexture, uiBackground;
    private com.mygdx.game.entity.enemy[] enemy;
    private int                        errorMsg;
    private TiledMapRenderer              topLayer;
    private MapObjects                    objects;
    private List<Boolean>                 enemyDead;
    private gameData                      gameData;
    private Image goalImage, infoWindowImage;
    private Preferences audioPref;
    private Thread thread;
    
    private float screenWidth = Gdx.graphics.getWidth();
    private float screenHeight = Gdx.graphics.getHeight();
    private final StringBuilder build = new StringBuilder();
    private float aspectRatio = screenWidth/screenHeight;
    private float backWidth = screenWidth*0.1f;
    private float backHeight = screenHeight*0.1f;
    private float backX = screenWidth*0.03f;
    private float backY = screenHeight*0.95f-backHeight;
    private float buttonWidth = screenWidth*0.1f;
    private float buttonHeight = buttonWidth/aspectRatio;
    
    public game(tess_interface tess,MyGdxGame game,Screen prevScreen,int mapLevel){
        this.tess = tess;
        this.game = game;
        this.prevScreen = prevScreen;
        currentLevel = mapLevel;
        skin = game.getSkin();
        jsonParser = new jsonParser("levels.json");
        res = jsonParser.getJson(Integer.toString(mapLevel));
        Map = new com.mygdx.game.entity.map("level"+mapLevel+".tmx");
    }
    
    public void reInitialize(){
        conditions = new conditions(gameData);
        character.reInitialize(gameData.heroPosition[0]*16,gameData.heroPosition[1]*16);
        for (int x = 0; x != gameData.noOfCoin ; x++){
            coin[x].reInitialize(gameData.coinPositions[x][0]*16, gameData.coinPositions[x][1]*16);
        }
        for (int x = 0; x != gameData.noOfEnemy ; x++){
            enemy[x].reInitialize(gameData.enemyPositions[x][0]*16, gameData.enemyPositions[x][1]*16);
        }
    }
    
    public void newGame(int level){
        character.end();
        game.getAudioManager().getGameMusic().stop();
        this.dispose();
        game.setScreen(new game(tess,game,prevScreen,level));
    }
    
    public void levelSelect(){
        character.end();
        game.getAudioManager().getGameMusic().stop();
        game.getAudioManager().getMenuMusic().play();
        this.dispose();
        game.setScreen(new levelSelect(tess,game));
    }
    
    public com.mygdx.game.entity.enemy[] getEnemy() {
        return enemy;
    }
    
    public void setError(boolean error){
        this.error = error;
    }
    
    @Override
    public void show() {
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        code = new TextArea("",skin);
        stage = new Stage();
        elapsedTime = 0;
        gameData = new gameData(res);
        character = new character(gameData.heroPosition[0]*16, gameData.heroPosition[1]*16, this);
        hero = character;
        isAlreadyRunning = false;
        audioPref = game.getAudioManager().getPreferences();
        audioManager audioManager = game.getAudioManager();
        thread = new Thread();
        
        TextureRegionDrawable muteTexture = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/speaker_mute.png"))));
        TextureRegionDrawable unMuteTexture = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/speaker_unmute.png"))));
        muteButton = new ImageButton(unMuteTexture, null, muteTexture);
        
        audioManager.setGameMusic(gameData.musicName);
        audioManager.getGameMusic().setLooping(true);
        audioManager.getGameMusic().play();
        if (audioPref.getBoolean("menuAudioOn")){
            audioManager.getGameMusic().setVolume(1);
            muteButton.setChecked(false);
        }
        else{
            audioManager.getGameMusic().setVolume(0);
            muteButton.setChecked(true);
        }
        
        muteButton.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (audioPref.getBoolean("menuAudioOn")){
                    game.getAudioManager().getGameMusic().setVolume(0);
                    game.getAudioManager().getMenuMusic().setVolume(0);
                    audioPref.putBoolean("menuAudioOn",false);
                    muteButton.setChecked(true);
                }
                else{
                    game.getAudioManager().getGameMusic().setVolume(1);
                    game.getAudioManager().getMenuMusic().setVolume(1);
                    audioPref.putBoolean("menuAudioOn",true);
                    muteButton.setChecked(false);
                }
                audioPref.flush();
            }
        });
    
        backTexture = new Texture(Gdx.files.internal("ui/BUTTON-BACK.png"));
        
        backBtn = new ImageButton(new TextureRegionDrawable(new TextureRegion(backTexture)));
        backBtn.setBounds(backX,backY,backWidth,backHeight);
        backBtn.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.getAudioManager().getGameMusic().stop();
                game.getAudioManager().getMenuMusic().play();
                dispose();
                game.setScreen(prevScreen);
            }
        });
        
        coin = new coin[gameData.noOfCoin];
        for (int x = 0; x != gameData.noOfCoin ; x++){
            coin[x] = new coin(gameData.coinPositions[x][0]*16, gameData.coinPositions[x][1]*16);
        }
        
        enemy = new enemy[gameData.noOfEnemy];
        for (int x = 0; x != gameData.noOfEnemy ; x++){
            enemy[x] = new enemy(gameData.enemyPositions[x][0]*16, gameData.enemyPositions[x][1]*16,0,9,gameData.enemyDirection[x],gameData.enemyNames[x],skin);
        }
        character.setEnemyPositions(gameData.enemyPositions);
        character.setEnemyNames(gameData.enemyNames);
        character.setEnemyDirection(gameData.enemyDirection);
        
        code.setBounds((screenWidth/2)*1.30f,screenHeight*0.15f,(screenWidth/2)*0.65f,screenHeight*0.7f);
        code.setText(gameData.codes);
        code.setFocusTraversal(false);
        code.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char c) {
                if ( c == '\n'){
                    code.setMessageText(build.append("\n").toString());
                }
            }
        });
        code.getStyle().background.setLeftWidth(screenWidth*0.02f);
        code.getStyle().background.setRightWidth(screenWidth*0.02f);
        
        runCode = new TextButton("Run",skin);
        runCode.setBounds(((screenWidth)-(buttonWidth))*0.9f,screenHeight*0.03f,buttonWidth,buttonHeight);
        runCode.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (!character.isRunning){
                    character.isRunning = true;
                    error = false;
                    if(isAlreadyRunning){
                        reInitialize();
                        popUp.unShowAll();
                        thread.interrupt();
                    }
                    isAlreadyRunning = true;
                    thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if (tess.runCode(code.getText()+"\nhero.end();", hero)){  //run code
                                    Gdx.app.log("run code: ","run code ended");
                                    character.isRunning = false;
                                }
                            }
                            catch (gameException e){
                                Gdx.app.log("gameException error: ",e.getMessage());
                                if (e.getMessage().equals("Character is block")){
                                    errorMsg = CHARACTER_BLOCK;
                                }
                                else if (e.getMessage().equals("Character is dead")){
                                    errorMsg = CHARACTER_DEAD;
                                }
                                else{
                                    errorMsg = SYNTAX_ERROR;
                                }
                                error = true;
                                character.isRunning = false;
                            }
                            Gdx.app.log("thread: ","thread ended");
                        }
                    });
                    thread.start();
                }
            }
        });
        
        pictureBtn = new TextButton("OCR",skin);
        pictureBtn.setBounds((runCode.getX())-(buttonWidth)*1.1f,screenHeight*0.03f,buttonWidth,buttonHeight);
        pictureBtn.addListener(tess.setGallerySelect());
        
        uiBackground = new Texture(Gdx.files.internal("ui/ui_background.png"));
        Image image = new Image(uiBackground);
        image.setBounds((screenWidth/2)*1.25f,0,(screenWidth/2)*0.75f,screenHeight);
        
        //goal(help)
        Texture helpTexture = new Texture(Gdx.files.internal("ui/help.png"));
        Image helpImage = new Image(helpTexture);
        helpImage.setBounds(screenWidth-(screenHeight*0.12f), screenHeight-(screenHeight*0.1f),screenHeight*0.08f, screenHeight*0.08f);
        helpImage.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                goalImage.setVisible(true);
            }
        });
        
        Texture goalTexture = new Texture(Gdx.files.internal("ui/goals/goalLevel"+currentLevel+".png"));
        goalImage = new Image(goalTexture);
        float goalHeight = screenHeight*0.9f;
        float goalWidth = ((float)goalTexture.getWidth()/(float)goalTexture.getHeight())*goalHeight;
        goalImage.setBounds((screenWidth/2)-(goalWidth/2), screenHeight*0.05f, goalWidth, goalHeight);
        goalImage.setVisible(true);
        goalImage.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                goalImage.setVisible(false);
            }
        });
        
        //info
        Texture infoTexture = new Texture(Gdx.files.internal("ui/info.png"));
        Image infoImage = new Image(infoTexture);
        infoImage.setBounds((helpImage.getX()-helpImage.getWidth())*0.95f, helpImage.getY(),
            helpImage.getWidth(), helpImage.getHeight());
        infoImage.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                infoWindowImage.setVisible(true);
            }
        });
        
        muteButton.setBounds((infoImage.getX()-infoImage.getWidth())*0.95f,infoImage.getY(), infoImage.getWidth(), infoImage.getHeight());
        
        Texture infoWindowTexture = new Texture(Gdx.files.internal("ui/hints/hintLevel"+currentLevel+".png"));
        infoWindowImage = new Image(infoWindowTexture);
        float infoWindowHeight = Gdx.graphics.getHeight()*0.7f;
        float infoWindowWidth = infoWindowHeight*((float)infoWindowTexture.getWidth()/(float)infoWindowTexture.getHeight());
        infoWindowImage.setBounds(screenWidth/2-(infoWindowWidth/2), screenHeight/2-(infoWindowHeight/2),
            infoWindowWidth, infoWindowHeight);
        infoWindowImage.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                infoWindowImage.setVisible(false);
            }
        });
        infoWindowImage.setVisible(false);
        
        camera = new OrthographicCamera();
        camera.setToOrtho(false);
        if (Map.getMapWidth() > Map.getMapHeight()){
            camera.viewportHeight = Map.getMapHeight();
            camera.viewportWidth = camera.viewportHeight*aspectRatio;
        }
        else{
            camera.viewportWidth = Map.getMapWidth()+((Map.getMapWidth()/2)*0.75f);
            camera.viewportHeight = camera.viewportWidth/aspectRatio;
        }
        camera.position.set((character.getX() + camera.viewportWidth / 6) + 16, character.getY() + 16, 0);
        
        camera.update();
        
        currentZoom = camera.zoom;
        mapCurrentZoom = Map.getCamera().zoom;
        
        Map.setViewportHeight(camera.viewportHeight);
        Map.setViewportWidth(camera.viewportWidth);
        
        TiledMap tiledMap = new TiledMap();
        tiledMap.getLayers().add(Map.getMap().getLayers().get("top"));
        topLayer = new OrthogonalTiledMapRenderer(tiledMap);
        
        popUp = new PopUp(this,currentLevel);
        
        stage.addActor(image);
        stage.addActor(helpImage);
        stage.addActor(code);
        stage.addActor(pictureBtn);
        stage.addActor(runCode);
        stage.addActor(backBtn);
        stage.addActor(goalImage);
        stage.addActor(muteButton);
        stage.addActor(infoImage);
        stage.addActor(infoWindowImage);
        
        GestureDetector gd = new GestureDetector(this);
        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(popUp.getStage());
        inputMultiplexer.addProcessor(gd);
        inputMultiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(inputMultiplexer);
        
        conditions = new conditions(gameData);
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
        
        if (gameData.hasTiledAnimation)
            if ((int)elapsedTime%2>0)
                Map.getMap().getLayers().get("animation").setVisible(false);
            else
                Map.getMap().getLayers().get("animation").setVisible(true);
        
        Map.setX(camera.position.x);
        Map.setY(camera.position.y);
        Map.draw();
        
        batch.begin();
        
        // Gem
        for (com.mygdx.game.entity.coin coinLocation : coin){
            if (Intersector.overlaps(coinLocation.getHitBox(),character.getHitBox()) && !coinLocation.isCollide ){
                coinLocation.isCollide = true;
                conditions.coinReduce();
            }
            coinLocation.draw(batch,elapsedTime);
        }
        
        // Enemy
        for (com.mygdx.game.entity.enemy enemyLocation : enemy){
            if (character.isRunning){
                if (!enemyLocation.dead && !character.dead){
                    if (character.attack) {
                        if (Intersector.overlaps(enemyLocation.getHitBox(), character.getAttackHitBox())) {
                            if (character.getTarget().equals(enemyLocation.getName())){
                                enemyLocation.dead = true;
                                conditions.enemyReduce();
                            }
                            else if (Intersector.overlaps(enemyLocation.getAttackHitBox(), character.getHitBox())) {
                                character.dead = true;
                                conditions.characterDead = true;
                            }
                        }
                    }
                    else if (Intersector.overlaps(enemyLocation.getHitBox(), character.getHitBox())) {
                        character.dead = true;
                        conditions.characterDead = true;
                    }
                }
            }
            enemyLocation.draw(batch,elapsedTime);
        }
        
        //Blocked by obstacles
        if (!character.isBlocked) {
            objects = Map.getMap().getLayers().get("blockObj").getObjects();
            for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)) {
                Rectangle rectangle = rectangleObject.getRectangle();
                if (Intersector.overlaps(rectangle, character.getHitBox())) {
                    character.isBlocked = true;
                    error = true;
                    character.isRunning = false;
                }
            }
        }
        
        //finishBlock
        if (!conditions.characterFinish){
            objects = Map.getMap().getLayers().get("finishBlock").getObjects();
            for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)) {
                Rectangle rectangle = rectangleObject.getRectangle();
                if (Intersector.overlaps(rectangle, character.getHitBox())) {
                    conditions.setCharacterFinish(true);
                }
            }
        }
        
        character.draw(batch,elapsedTime);
        
        batch.end();
        
        topLayer.setView(Map.getCamera());
        topLayer.render();
        
        stage.draw();
        
        if (error) {
            if (conditions.isCharacterDead()){
                batch.begin();
                popUp.show(3);
                batch.end();
                popUp.getStage().draw();
            }
            else {
                batch.begin();
                popUp.show(errorMsg);
                batch.end();
                popUp.getStage().draw();
            }
        }
        if (conditions.isConditionsMeet() && !character.isRunning){
            character.isRunning = false;
            Gdx.input.setInputProcessor(popUp.getStage());
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA,GL20.GL_ONE_MINUS_SRC_ALPHA);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(0,0,0,0.6f);
            shapeRenderer.rect(0,0,screenWidth,screenHeight);
            shapeRenderer.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);
            batch.begin();
            popUp.show(batch,camera);
            batch.end();
            popUp.getStage().draw();
        }
        
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
        Map.getMap().dispose();
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
    
    private void checkEdgeTouch(){
        float halfWidth = camera.viewportWidth*camera.zoom/2;
        float halfHeight = camera.viewportHeight*camera.zoom/2;
        float specialRight = camera.viewportWidth*camera.zoom/2*0.25f;
        if (camera.position.x+(specialRight) > Map.getMapWidth()) camera.position.x = Map.getMapWidth()- specialRight; //right
        if (camera.position.y-(halfHeight) < 0) camera.position.y = halfHeight; //below
        if (camera.position.x-(halfWidth) < 0) camera.position.x = halfWidth; //left
        if (camera.position.y+(halfHeight) > Map.getMapHeight()) camera.position.y = Map.getMapHeight() - halfHeight;  //top
        camera.update();
    }
    
    @Override
    public boolean keyDown(int keycode) {
        return false;
    }
    
    @Override
    public boolean keyUp(int keycode) {
        return false;
    }
    
    @Override
    public boolean keyTyped(char character) {
        return false;
    }
    
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }
    
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        code.getOnscreenKeyboard().show(false);
        return false;
    }
    
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }
    
    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }
    
    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}