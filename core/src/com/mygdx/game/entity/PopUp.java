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
    private static final int SYNTAX_ERROR = 1;
    private static final int CHARACTER_BLOCK = 2;
    private static final int CHARACTER_DEAD = 3;
    
    private Texture nextTexture,levelTexture, victoryTexture;
    private ImageButton errorButton,nextLevel,levelSelect;
    private TextureRegionDrawable errorTexture1, errorTexture2, errorTexture3;
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
        buttonWidth = Gdx.graphics.getWidth()*0.15f;
        buttonHeight = buttonWidth*aspectRatio;
        nextTexture = new Texture(Gdx.files.internal("ui/BUTTON-NEXT.png"));
        levelTexture = new Texture(Gdx.files.internal("ui/BUTTON-LEVEL.png"));
        victoryTexture = new Texture(Gdx.files.internal("ui/VICTORY PROMT.png"));
        errorTexture1 = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/errors/error1.png"))));
        errorTexture2 = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/errors/error2.png"))));
        errorTexture3 = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/errors/error3.png"))));
        stage = new Stage();

        errorButton = new ImageButton(errorTexture1);
        errorButton.setBounds(Gdx.graphics.getWidth()*0.05f, Gdx.graphics.getHeight()*0.1f,
            halfWidth*0.7f,(halfWidth*0.7f)/((float)errorTexture1.getRegion().getTexture().getWidth()/(float)errorTexture1.getRegion().getTexture().getHeight()));
        errorButton.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
    
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setError(false);
                errorButton.setVisible(false);
            }
        });
        
        nextLevel = new ImageButton(new TextureRegionDrawable(new TextureRegion(nextTexture)));
        nextLevel.setBounds(halfWidth+(buttonWidth*0.2f),Gdx.graphics.getHeight()*0.18f,buttonWidth,buttonHeight);
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
        levelSelect.setBounds(halfWidth-(buttonWidth*1.2f),Gdx.graphics.getHeight()*0.18f,buttonWidth,buttonHeight);
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
        stage.addActor(nextLevel);
        stage.addActor(levelSelect);
        stage.addActor(errorButton);
    }
    
    public void unShowAll(){
        nextLevel.setVisible(false);
        levelSelect.setVisible(false);
        errorButton.setVisible(false);
    }
    
    public void show(int i){
        switch(i){
            case SYNTAX_ERROR:
                errorButton.getStyle().imageUp = errorTexture1;
                errorButton.getStyle().imageDown = errorTexture1;
                break;
            case CHARACTER_BLOCK:
                errorButton.getStyle().imageUp = errorTexture2;
                errorButton.getStyle().imageDown = errorTexture2;
                break;
            case CHARACTER_DEAD:
                errorButton.getStyle().imageUp = errorTexture3;
                errorButton.getStyle().imageDown = errorTexture3;
                break;
        }
        errorButton.setVisible(true);
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
    
}
