package com.mygdx.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.screens.game;

public class PopUp {
    private Texture closeTexture,nextTexture,levelTexture,bgTexture;
    private ImageButton close,nextLevel,levelSelect;
    private int currentLevel;
    private Stage stage;

    private float aspectRatio;
    private float bgWidth;
    private float bgHeight;
    private float bgX;
    private float bgY;
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
//        closeTexture = new Texture(Gdx.files.internal(""));
        nextTexture = new Texture(Gdx.files.internal("ui/BUTTON-NEXT.png"));
        levelTexture = new Texture(Gdx.files.internal("ui/BUTTON-LEVEL.png"));
        bgTexture = new Texture(Gdx.files.internal("ui/VICTORY PROMT.png"));
        stage = new Stage();

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
        stage.addActor(nextLevel);
        stage.addActor(levelSelect);
    }

    public void show(SpriteBatch batch,Camera camera,int stat,String s){
        bgWidth = camera.viewportWidth*0.6f;
        bgHeight =  camera.viewportHeight*0.6f;
        bgX = camera.position.x-(camera.viewportWidth*0.3f);
        bgY = camera.position.y-(camera.viewportHeight*0.3f);

        if (stat == 1) {
            batch.draw(bgTexture, bgX, bgY, bgWidth, bgHeight);
            nextLevel.setVisible(true);
            levelSelect.setVisible(true);
        }
    }

    public Stage getStage(){return stage;}

}
