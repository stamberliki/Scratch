package com.mygdx.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.character_interface;
import com.mygdx.game.codeParser;

public class character implements character_interface {
    private com.badlogic.gdx.graphics.g2d.Animation <TextureRegion>
            downIdleAnimation,downWalkAnimation,upIdleAnimation,upWalkAnimation,rightIdleAnimation,
            rightWalkAnimation,leftIdleAnimation,leftWalkAnimation,
            attackTop, attackDown,attackLeft,attackRight,
            state;
    private Texture characterTexture, attackTexture;
    private int x,y, nextX, nextY,steps;
    private float width,height,gameTime;
    private String currentCommand;
    private com.mygdx.game.codeParser codeParser;
    public boolean isRunning,isBlocked;
    private Rectangle hitBox;

    private static final float speed = 1;

    public character(int x, int y){
        characterTexture = new Texture(Gdx.files.internal("characters.png"));
        attackTexture = new Texture(Gdx.files.internal("characterAttack.png"));
        TextureRegion[][] tmp = TextureRegion.split(characterTexture,characterTexture.getWidth()/12,characterTexture.getHeight()/8);
        downWalkAnimation = new Animation<TextureRegion>(1/3f, characterFrames(tmp,0));
        leftWalkAnimation = new Animation<TextureRegion>(1/3f, characterFrames(tmp,1));
        rightWalkAnimation = new Animation<TextureRegion>(1/3f, characterFrames(tmp,2));
        upWalkAnimation = new Animation<TextureRegion>(1/3f, characterFrames(tmp,3));
        downIdleAnimation = new Animation<TextureRegion>(1,tmp[0][4]);
        upIdleAnimation = new Animation<TextureRegion>(1,tmp[3][4]);
        leftIdleAnimation = new Animation<TextureRegion>(1,tmp[1][4]);
        rightIdleAnimation = new Animation<TextureRegion>(1,tmp[2][4]);
        TextureRegion[][] tmp2 = TextureRegion.split(attackTexture,attackTexture.getWidth()/5,attackTexture.getHeight()/4);
        attackTop = new Animation<TextureRegion>(1/10f,frames(tmp2,3));
        attackLeft = new Animation<TextureRegion>(1/10f,frames(tmp2,1));
        attackRight = new Animation<TextureRegion>(1/10f,frames(tmp2,2));
        attackDown = new Animation<TextureRegion>(1/10f,frames(tmp2,0));

        state = attackDown;

        this.width = 32;
        this.height = 32;
        this.x = x;
        this.y = y;

        hitBox = new Rectangle(x,y,28,12);

        this.steps = 32*3;
        gameTime = 0;
        nextX = x;
        nextY = y;
        currentCommand = "";
        isRunning = false;
        isBlocked = false;
    }

    public void setCodeParser(codeParser codeParser){this.codeParser = codeParser;}

    private TextureRegion[] characterFrames(TextureRegion[][] tmp, int x){
        TextureRegion[] frames = new TextureRegion[2];
        int index = 0;
        for ( int i = 3 ; i < 6 ; i++){
            if (i!=4)
                frames[index++] = tmp[x][i];
        }
        return frames;
    }

    private TextureRegion[] frames(TextureRegion[][] tmp,int a){
        TextureRegion[] frames = new TextureRegion[5];
        for (int x = 0 ; x != 5 ; x++){
            frames[x] = tmp[a][x];
        }
        return frames;
    }

    public float getX(){
        return  x;
    }

    public float getY(){
        return  y;
    }

    public float getHeight(){return height;}

    public float getWidth(){return width;}

    public Rectangle getHitBox(){return hitBox;}

    public void run(){
        nextX = x;
        nextY = y;
        codeParser.nextLine();
    }

    public void draw(SpriteBatch batch,float time){
        gameTime += time;
        render();
        hitBox.x = x;
        hitBox.y = y;
        batch.draw(state.getKeyFrame(time,true),x,y,width,height);
    }

    public void render(){
        if (!isBlocked){
            if (y < nextY){//up
                y += speed;
            }
            if(x < nextX){//right
                x += speed;
            }
            if(y > nextY){//down
                y -= speed;
            }
            if(x > nextX){//left
                x -= speed;
            }
        }
        if (x == nextX && y == nextY){
            if (currentCommand.equals("up")){
                state = upIdleAnimation;
            }
            else if(currentCommand.equals("right")){
                state = rightIdleAnimation;
            }
            else if(currentCommand.equals("down")){
                state = downIdleAnimation;
            }
            else if(currentCommand.equals("left")){
                state = leftIdleAnimation;
            }
        }
    }


    public void moveUp(){
        nextY = y+steps;
        state = upWalkAnimation;
        currentCommand = "up";
        while (nextY!=y){}

    }

    public void moveDown(){
        nextY = y-steps;
        state = downWalkAnimation;
        currentCommand = "down";
        while (nextY!=y){}
    }

    public void moveRight(){
        nextX = x+steps;
        state = rightWalkAnimation;
        currentCommand = "right";
        while (nextX!=x){}
    }

    public void moveLeft(){
        nextX = x-steps;
        state = leftWalkAnimation;
        currentCommand = "left";
        while (nextX!=x){}
    }

    public void attack(String name){
        if (currentCommand.equals("up")){
            state = attackTop;
        }
        else if (currentCommand.equals("down")){
            state = attackDown;
        }
        else if (currentCommand.equals("left")){
            state = attackLeft;
        }
        else if (currentCommand.equals("right")){
            state = attackRight;
        }
        while (!state.isAnimationFinished(1/10f)){
            Gdx.app.log("","attack pass");
        }
        if (currentCommand.equals("up")){
            state = upIdleAnimation;
        }
        else if (currentCommand.equals("down")){
            state = downIdleAnimation;
        }
        else if (currentCommand.equals("left")){
            state = leftIdleAnimation;
        }
        else if (currentCommand.equals("right")){
            state = rightIdleAnimation;
        }
    }

}
