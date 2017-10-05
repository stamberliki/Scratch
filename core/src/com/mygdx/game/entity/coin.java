package com.mygdx.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;


public class coin {
    private Texture coinTexture;
    private Animation<TextureRegion> coinAnimation;
    private int x,y,prevX,prevY;
    private float width,height;
    private Rectangle hitBox;
    public boolean isCollide;

    public coin(int x, int y){
        coinTexture = new Texture(Gdx.files.internal("B.png"));
        TextureRegion[][] tmp = TextureRegion.split(coinTexture,coinTexture.getWidth()/16,coinTexture.getHeight()/16);
        coinAnimation = new Animation<TextureRegion>(1,tmp[3][8]);
        hitBox = new Rectangle(0,0,32,32);
        hitBox.x = x;
        hitBox.y = y;
        this.x = x;
        this.y = y;
        prevX = x;
        prevY = y;
        width = 32;
        height = 32;
        isCollide = false;
    }

    public Rectangle getHitBox(){return hitBox;}

    public void draw(SpriteBatch batch,float time){
        if (y < prevY+32)
            batch.draw(coinAnimation.getKeyFrame(time,true),x,y,width,height);
        if (isCollide && y < prevY+32){ y += 1*time;}
    }

}
