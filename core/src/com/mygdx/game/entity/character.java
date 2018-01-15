package com.mygdx.game.entity;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.character_interface;

import java.util.List;

public class character implements character_interface {
    private com.badlogic.gdx.graphics.g2d.Animation <TextureRegion>
            downIdleAnimation,downWalkAnimation,upIdleAnimation,upWalkAnimation,rightIdleAnimation,
            rightWalkAnimation,leftIdleAnimation,leftWalkAnimation,
            attackTop, attackDown,attackLeft,attackRight,
            deadAnimation,state;
    private Texture characterTexture, attackTexture,deadTexture;
    private int x, y, nextX, nextY, prevX, prevY, steps;
    private float width,height,gameTime;
    private String currentCommand,enemyTarget;
    public boolean isRunning,isBlocked,attack;
    private Rectangle hitBox,attackHitBox;
    private int[][] enemyPositions;
    private String[] enemyNames,enemyDirection;
    private List<Boolean> enemyDead;
    private com.mygdx.game.screens.game game;
    
    public boolean dead;

    private static final float speed = 1;

    public character(int x, int y, com.mygdx.game.screens.game game){
        characterTexture = new Texture(Gdx.files.internal("characters.png"));
        attackTexture = new Texture(Gdx.files.internal("characterAttack.png"));
        deadTexture = new Texture(Gdx.files.internal("$dead.png"));
        TextureRegion[][] tmp = TextureRegion.split(characterTexture,characterTexture.getWidth()/12,characterTexture.getHeight()/8);
        downWalkAnimation = new Animation<TextureRegion>(1/3f, characterFrames(tmp,0));
        leftWalkAnimation = new Animation<TextureRegion>(1/3f, characterFrames(tmp,1));
        rightWalkAnimation = new Animation<TextureRegion>(1/3f, characterFrames(tmp,2));
        upWalkAnimation = new Animation<TextureRegion>(1/3f, characterFrames(tmp,3));
        downIdleAnimation = new Animation<TextureRegion>(1,tmp[0][4]);
        upIdleAnimation = new Animation<TextureRegion>(1,tmp[3][4]);
        leftIdleAnimation = new Animation<TextureRegion>(1,tmp[1][4]);
        rightIdleAnimation = new Animation<TextureRegion>(1,tmp[2][4]);
        TextureRegion[][] tmp2 = TextureRegion.split(attackTexture,attackTexture.getWidth()/9,attackTexture.getHeight()/4);
        attackTop = new Animation<TextureRegion>(1/9f,frames(tmp2,3));
        attackLeft = new Animation<TextureRegion>(1/9f,frames(tmp2,1));
        attackRight = new Animation<TextureRegion>(1/9f,frames(tmp2,2));
        attackDown = new Animation<TextureRegion>(1/9f,frames(tmp2,0));
        deadAnimation = new Animation<TextureRegion>(1,TextureRegion.split(deadTexture,deadTexture.getWidth()/3,deadTexture.getHeight()/4)[3][2]);

        state = downIdleAnimation;

        this.width = 32;
        this.height = 32;
        this.x = x;
        this.y = y;
        this.game = game;
        
        hitBox = new Rectangle(x+6,y,20,12);
        attackHitBox = new Rectangle(x,y,32,32);

        this.steps = 32*3;
        gameTime = 0;
        nextX = x;
        nextY = y;
        currentCommand = "down";
        enemyTarget = "";
        
        isRunning = false;
        isBlocked = false;
        attack = false;
        dead = false;
    }
    
    public void setEnemyPositions(int[][] list){this.enemyPositions = list;}
    
    public void setEnemyNames(String[] list){this.enemyNames = list;}
    
    public void setEnemyDirection(String[] enemyDirection) {
        this.enemyDirection = enemyDirection;
    }
    
    public void setEnemyDead(List<Boolean> list){enemyDead = list;}
    
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

    public void reInitialize(int x, int y){
        this.width = 32;
        this.height = 32;
        this.x = x;
        this.y = y;
        hitBox = new Rectangle(x+6,y,20,12);
        attackHitBox = new Rectangle(x,y,32,16);
        gameTime = 0;
        nextX = x;
        nextY = y;
        currentCommand = "down";
        enemyTarget = "";
        isBlocked = false;
        attack = false;
        dead = false;
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

    public Rectangle getAttackHitBox(){return  attackHitBox;}
    
    public String getTarget(){return enemyTarget;}

    public void run(){
        nextX = x;
        nextY = y;
    }

    public void draw(SpriteBatch batch,float time){
        gameTime += Gdx.graphics.getDeltaTime();
        render();
        hitBox.x = x;
        hitBox.y = y;
        if (attack)
            batch.draw(state.getKeyFrame(gameTime,false),x,y,width,height);
        else{
            attackHitBox.x = x;
            attackHitBox.y = y;
            batch.draw(state.getKeyFrame(gameTime,true),x,y,width,height);
        }
    }

    public void render(){
        if (isRunning){
            if (!isBlocked && !dead){
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
            if (x == nextX && y == nextY && !attack && !dead){
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
            if (attack){
                if (currentCommand.equals("up")){
                    attackHitBox.x = x;
                    attackHitBox.y = y+32;
                }
                else if (currentCommand.equals("down")){
                    attackHitBox.x = x;
                    attackHitBox.y = y-32;
                }
                else if (currentCommand.equals("left")){
                    attackHitBox.x = x-32;
                    attackHitBox.y = y;
                }
                else if (currentCommand.equals("right")){
                    attackHitBox.x = x+32;
                    attackHitBox.y = y;
                }
            }
            if (dead){
                state = deadAnimation;
            }
        }
    }
    
    double distance(float x, float y){
        return Math.sqrt(Math.pow((x - this.x), 2) + Math.pow((y - this.y), 2));
    }
    
    // CHARACTER MOVEMENTS
    
    
    public void moveUp(){
        gameTime = 0;
        nextY = y+steps;
        state = upWalkAnimation;
        currentCommand = "up";
        while (nextY!=y){}

    }

    public void moveDown(){
        gameTime = 0;
        nextY = y-steps;
        state = downWalkAnimation;
        currentCommand = "down";
        while (nextY!=y){}
    }

    public void moveRight(){
        gameTime = 0;
        nextX = x+steps;
        state = rightWalkAnimation;
        currentCommand = "right";
        while (nextX!=x){}
    }

    public void moveLeft(){
        gameTime = 0;
        nextX = x-steps;
        state = leftWalkAnimation;
        currentCommand = "left";
        while (nextX!=x){}
    }
    
    public void attack(String name){
        enemyTarget = name;
        prevX = x;
        prevY = y;
        int targetX=0,targetY=0;
        String direction="";
        for (int a = 0 ; a != enemyNames.length ; a++){
            if (enemyNames[a].equals(name)){
                targetX = enemyPositions[a][0]*16;
                targetY = enemyPositions[a][1]*16;
                direction = enemyDirection[a];
                break;
            }
        }
        
        if (direction.equals("left")){
            nextX = targetX-32;
            nextY = targetY;
            currentCommand = "right";
            state = rightWalkAnimation;
        }
        else if (direction.equals("right")){
            nextX = targetX+32;
            nextY = targetY;
            currentCommand = "left";
            state = leftWalkAnimation;
        }
        else if (direction.equals("up")){
            nextY = targetY+32;
            nextX = targetX;
            currentCommand = "down";
            state = downWalkAnimation;
        }
        else if (direction.equals("down")){
            nextY = targetY-32;
            nextX = targetX;
            currentCommand = "up";
            state = upWalkAnimation;
        }
        while (nextX!=x || nextY!=y){}
        attack = true;
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
        gameTime = 0;
        while (!state.isAnimationFinished(gameTime)){}
        if (currentCommand.equals("up")){
            state = downWalkAnimation;
        }
        else if(currentCommand.equals("right")){
            state = leftWalkAnimation;
        }
        else if(currentCommand.equals("down")){
            state = upWalkAnimation;
        }
        else if(currentCommand.equals("left")){
            state = rightWalkAnimation;
        }
        attack = false;
        nextX = prevX;
        nextY = prevY;
        while (nextX!=x || nextY!=y){}
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
    
    public String findNearestEnemy(){
        String nearestEnemy = null;
        double distance = distance(enemyPositions[0][0]*16, enemyPositions[0][1]*16);
        for (int x = 0 ; x != enemyPositions.length ; x++ ){
            if (distance(enemyPositions[x][0]*16, enemyPositions[x][1]*16) <= distance &&
                !(game.getEnemy()[x]).dead){
                nearestEnemy = enemyNames[x];
                distance = distance(enemyPositions[x][0]*16, enemyPositions[x][1]*16);
            }
        }
        return nearestEnemy;
    }
    
    public boolean enemy(){
        float range = 32*3;
        for (int x = 0 ; x != enemyPositions.length ; x++ ){
            if (Math.abs(this.x-(enemyPositions[x][0])*16) <= range &&
                Math.abs(y-(enemyPositions[x][1])*16) <= range &&
                !(game.getEnemy()[x]).dead){
                return true;
            }
        }
        return false;
    }
    
}
