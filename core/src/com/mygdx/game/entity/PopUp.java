package com.mygdx.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class PopUp {
    private Sprite show;
    private Texture texture;
    private Button close,nextLevel,levelSelect;

    public PopUp(){
        texture = new Texture(Gdx.files.internal("ui/BUTTON-PLAY.png"));
        Button.ButtonStyle style = new Button.ButtonStyle();
        style.down = new TextureRegionDrawable(new TextureRegion(texture));

    }


}
