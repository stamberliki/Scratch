package com.mygdx.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.screens.game;

public class PopUp {
    private Texture closeTexture,nextTexture,levelTexture, victoryTexture, errorTexture;
    private ImageButton errorButton,nextLevel,levelSelect;
    private Label errorText;
    private int currentLevel;
    private Stage stage;

    private float aspectRatio;
    private float victoryWidth;
    private float victoryHeight;
    private float victoryX;
    private float victoryY;
    private float buttonWidth;
    private float buttonHeight;
    private float halfWidth;
    private float halfHeight;

    public PopUp(final game game, int level){
        currentLevel = level;
        aspectRatio = Gdx.graphics.getWidth() / Gdx.graphics.getHeight();
        halfWidth = Gdx.graphics.getWidth()/2;
        halfHeight = Gdx.graphics.getHeight()/2;
        buttonWidth = Gdx.graphics.getWidth()*0.1f;
        buttonHeight = buttonWidth*aspectRatio;
        nextTexture = new Texture(Gdx.files.internal("ui/BUTTON-NEXT.png"));
        levelTexture = new Texture(Gdx.files.internal("ui/BUTTON-LEVEL.png"));
        victoryTexture = new Texture(Gdx.files.internal("ui/VICTORY PROMT.png"));
        errorTexture = new Texture(Gdx.files.internal("ui/error.png"));
        stage = new Stage();

        errorButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(errorTexture)));
        errorButton.setBounds(Gdx.graphics.getWidth()*0.05f, Gdx.graphics.getHeight()*0.1f,halfWidth,halfHeight);
        errorButton.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
    
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setError(false);
            }
        });
        
        errorText = new Label(null,game.getSkin());
        errorText.setBounds(Gdx.graphics.getWidth()*0.1f, Gdx.graphics.getHeight()*0.1f,halfWidth/2,halfHeight/2); //to be fix
        errorText.setWrap(true);
        errorText.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
    
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setError(false);
            }
        });
        
        nextLevel = new ImageButton(new TextureRegionDrawable(new TextureRegion(nextTexture)));
        nextLevel.setBounds(halfWidth+(buttonWidth*0.2f),Gdx.graphics.getHeight()*0.2f-(buttonHeight),buttonWidth,buttonHeight);
        nextLevel.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.newGame(currentLevel+1);
            }
        });

        levelSelect = new ImageButton(new TextureRegionDrawable(new TextureRegion(levelTexture)));
        levelSelect.setBounds(halfWidth-(buttonWidth*1.2f),Gdx.graphics.getHeight()*0.2f-(buttonHeight),buttonWidth,buttonHeight);
        levelSelect.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.levelSelect();
            }
        });

        nextLevel.setVisible(false);
        levelSelect.setVisible(false);
        errorButton.setVisible(false);
        errorText.setVisible(false);
        stage.addActor(nextLevel);
        stage.addActor(levelSelect);
        stage.addActor(errorButton);
        stage.addActor(errorText);
    }

    public void show(String s){
        errorText.setText(s);
        errorButton.setVisible(true);
        errorText.setVisible(true);
    }

    public void show(SpriteBatch batch,Camera camera){
        victoryWidth = camera.viewportWidth*0.6f;
        victoryHeight =  camera.viewportHeight*0.6f;
        victoryX = camera.position.x-(camera.viewportWidth*0.3f);
        victoryY = camera.position.y-(camera.viewportHeight*0.3f);
        batch.draw(victoryTexture, victoryX, victoryY, victoryWidth, victoryHeight);
        nextLevel.setVisible(true);
        levelSelect.setVisible(true);
    }

    public Stage getStage(){return stage;}

    public ImageButton getErrorButton(){return errorButton;}
    
}
