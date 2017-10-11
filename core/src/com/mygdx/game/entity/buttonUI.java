package com.mygdx.game.entity;

import com.badlogic.gdx.Game;
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
        skin.setBounds(x,y,width,height);
    }

    public void update(SpriteBatch batch, float x, float y){
        event(x,y);
        skin.draw(batch);
    }

    private boolean event(float x, float y){
        if (x > skin.getX() && x < skin.getX()+skin.getHeight()){
            if (y > skin.getY() && y > skin.getX()+skin.getHeight()){
                return true;
            }
        }
        return false;
    }

}
