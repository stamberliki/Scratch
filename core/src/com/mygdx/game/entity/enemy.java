package com.mygdx.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class enemy {
    private com.badlogic.gdx.graphics.g2d.Animation <TextureRegion>
            downWalkAnimation,upWalkAnimation,rightWalkAnimation,leftWalkAnimation,deadAnimation,state;
    private Texture enemyTexture, deathTexture;
    private int x,y, typeX,typeY;
    private float width,height;
    private String direction;
    private Rectangle hitBox,attackHitBox;
    private Label name;
    private Skin skin;

    public boolean isCollide,dead;

    public enemy(int x, int y, int typeX, int typeY, String direction,String name,Skin skin){
        this.typeX = typeX;
        this.typeY = typeY;
        this.direction = direction;
        this.skin = skin;
        enemyTexture = new Texture(Gdx.files.internal("characters.png"));
        deathTexture = new Texture(Gdx.files.internal("$dead.png"));
        TextureRegion[][] tmp = TextureRegion.split(enemyTexture,enemyTexture.getWidth()/12,enemyTexture.getHeight()/8);
        downWalkAnimation = new Animation<TextureRegion>(1/3f,frames(tmp,typeX,typeY));
        downWalkAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
        leftWalkAnimation = new Animation<TextureRegion>(1/3f,frames(tmp,typeX,typeY));
        leftWalkAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
        rightWalkAnimation = new Animation<TextureRegion>(1/3f,frames(tmp,typeX,typeY));
        rightWalkAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
        upWalkAnimation = new Animation<TextureRegion>(1/3f,frames(tmp,typeX,typeY));
        upWalkAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
        if (typeY==9 && typeX==0)
            deadAnimation = new Animation<TextureRegion>(1,TextureRegion.split(deathTexture,deathTexture.getWidth()/3,deathTexture.getHeight()/4)[2][0]);

        hitBox = new Rectangle(x,y,32,16);
        attackHitBox = new Rectangle(x,y,32,32);

        setState(this.direction);

        this.name = new Label(name,skin);
        this.name.setPosition(x,y+32);
        this.name.setFontScale(Gdx.graphics.getDensity()/2.5f);
        
        this.x = x;
        this.y = y;
        this.width = 32;
        this.height = 32;
        isCollide = false;
        dead = false;
    }

    public void reInitialize(int x,int y){
        hitBox = new Rectangle(x,y,28,12);
        setState(this.direction);
        this.x = x;
        this.y = y;
        this.width = 32;
        this.height = 32;
        isCollide = false;
        dead = false;
    }

    private TextureRegion[] frames(TextureRegion[][] tmp,int a,int b){
        TextureRegion[] frames = new TextureRegion[3];
        int index = 0;
        for ( int i = b ; i < b+3 ; i++){
            frames[index++] = tmp[a][i];
        }
        typeX++;
        return frames;
    }

    private void setState(String direction){
        if (direction.equals("up")){
            attackHitBox.x += 16;
            state = upWalkAnimation;
        }
        else if (direction.equals("down")){
            attackHitBox.x -= 16;
            state = downWalkAnimation;
        }
        else if (direction.equals("left")){
            attackHitBox.y -= 16;
            state = leftWalkAnimation;
        }
        else if (direction.equals("right")){
            attackHitBox.y += 16;
            state = rightWalkAnimation;
        }
    }

    public void draw(SpriteBatch batch, float time){
        if (dead)
            state = deadAnimation;
        
        hitBox.x = x;
        hitBox.y = y;
        batch.draw(state.getKeyFrame(time, true), x, y, width, height);
        name.draw(batch,time);
    }

    public Rectangle getHitBox(){return hitBox;}

    public Rectangle getAttackHitBox(){return attackHitBox;}
    
    public String getName(){return name.getName();}
}