package com.mygdx.game.entity;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.screens.game;

public class PopUp {
    private Sprite show;
    private Texture closeTexture,nextTexture,levelTexture,bgTexture;
    private ImageButton close,nextLevel,levelSelect;
    private ShapeRenderer shapeRenderer;
    private Camera camera;
    private game game;
    private int currentLevel;

    private float aspectRatio;
    private float bgWidth;
    private float bgHeight;
    private float bgX;
    private float bgY;
    private float buttonWidth;
    private float buttonHeight;

    public PopUp(Camera camera, Stage stage, final game game, int level){
        currentLevel = level;
        this.game = game;
//        closeTexture = new Texture(Gdx.files.internal(""));
        nextTexture = new Texture(Gdx.files.internal("ui/BUTTON-NEXT.png"));
        levelTexture = new Texture(Gdx.files.internal("ui/BUTTON-LEVEL.png"));
        bgTexture = new Texture(Gdx.files.internal("ui/VICTORY PROMT.png"));
//        shapeRenderer = new ShapeRenderer();
//        shapeRenderer.rect(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        nextLevel = new ImageButton(new TextureRegionDrawable(new TextureRegion(nextTexture)));
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
        aspectRatio = Gdx.graphics.getWidth() / Gdx.graphics.getHeight();
        bgWidth = camera.viewportWidth*0.6f;
        bgHeight =  camera.viewportHeight*0.6f;
        bgX = camera.position.x-(camera.viewportWidth*0.3f);
        bgY = camera.position.y-(camera.viewportHeight*0.3f);
        buttonWidth = bgWidth*0.3f;
        buttonHeight = buttonWidth*aspectRatio;

        if (stat == 1) {
            nextLevel.setVisible(true);
            levelSelect.setVisible(true);
            levelSelect.setBounds(bgX+(bgWidth/2)-(buttonWidth/2),bgY-(buttonHeight/2),buttonWidth,buttonHeight);
            nextLevel.setBounds(bgX+(bgWidth/2)+(buttonWidth*1.5f),bgY-(buttonHeight/2),buttonWidth,buttonHeight);
            batch.draw(bgTexture, bgX, bgY, bgWidth, bgHeight);
        }
    }

}
