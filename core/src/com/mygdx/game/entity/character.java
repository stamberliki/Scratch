package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class character implements character_interface{
    private com.badlogic.gdx.graphics.g2d.Animation <TextureRegion>
            downIdleAnimation,downWalkAnimation,upIdleAnimation,upWalkAnimation,rightIdleAnimation,
            rightWalkAnimation,leftIdleAnimation,leftWalkAnimation, state;
    private Texture characterTexture;
    private int x,y, nextX, nextY,steps;
    private float width,height;
    private String currentCommand;
    private codeParser codeParser;
    private boolean isRunning;

    private static final float speed = 1;

    public character(int x, int y){
        characterTexture = new Texture(Gdx.files.internal("characters.png"));
        TextureRegion[][] tmp = TextureRegion.split(characterTexture,characterTexture.getWidth()/12,characterTexture.getHeight()/8);
        downWalkAnimation = new Animation<TextureRegion>(1/3f,frames(tmp,0));
        leftWalkAnimation = new Animation<TextureRegion>(1/3f,frames(tmp,1));
        rightWalkAnimation = new Animation<TextureRegion>(1/3f,frames(tmp,2));
        upWalkAnimation = new Animation<TextureRegion>(1/3f,frames(tmp,3));
        downIdleAnimation = new Animation<TextureRegion>(1,downWalkAnimation.getKeyFrames()[1]);
        upIdleAnimation = new Animation<TextureRegion>(1,upWalkAnimation.getKeyFrames()[1]);
        leftIdleAnimation = new Animation<TextureRegion>(1,leftWalkAnimation.getKeyFrames()[1]);
        rightIdleAnimation = new Animation<TextureRegion>(1,rightWalkAnimation.getKeyFrames()[1]);
        state = downIdleAnimation;

        this.width = 32;
        this.height = 32;
        this.x = x;
        this.y = y;
        this.steps = 32;
        nextX = x;
        nextY = y;
        currentCommand = "";
        isRunning = false;
    }

    public void setCodeParser(codeParser codeParser){this.codeParser = codeParser;}

    private TextureRegion[] frames(TextureRegion[][] tmp,int x){
        TextureRegion[] frames = new TextureRegion[3];
        int index = 0;
        for ( int i = 3 ; i < 6 ; i++){
            frames[index++] = tmp[x][i];
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

    public void setRunning(boolean bol){isRunning = bol;}

    public boolean isRunning(){return isRunning;}

    public void run(){
        nextX = x;
        nextY = y;
        codeParser.nextLine();
    }

    public void draw(SpriteBatch batch,float time){
        render();
        batch.draw(state.getKeyFrame(time,true),x,y,width,height);
    }

    public void render(){
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
        if (x == nextX && y == nextY){
            isRunning = false;
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

}
