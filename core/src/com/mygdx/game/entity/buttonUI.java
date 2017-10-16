package com.mygdx.game.entity;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Created by Anony on 10/11/2017.
 */

public class buttonUI {
    private Sprite skin;

    public buttonUI(Texture texture, float x, float y,float width, float height){
        skin = new Sprite(texture);
        skin.setPosition(x,y);
        skin.setSize(width,height);
    }

    public void update(SpriteBatch batch, float x, float y){
        skin.draw(batch);
    }

    public boolean event(float x, float y){
        if (skin.getBoundingRectangle().contains(x,y+(skin.getHeight()*2)+(skin.getY()/2)-Gdx.graphics.getHeight())){
            return true;
        }
        return false;
    }

}
