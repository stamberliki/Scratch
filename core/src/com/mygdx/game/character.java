package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Scanner;

/**
 * Created by Anony on 8/26/2017.
 */

public class character {
    private TextureAtlas downIldeAtlas,downWalkAtlas,upIldeAtlas,upWalkAtlas,sideIldeAtlas,sideWalkAtlas;
    private com.badlogic.gdx.graphics.g2d.Animation <TextureRegion> downIdleAnimation,downWalkAnimation,upIdleAnimation,upWalkAnimation,sideIdleAnimation,sideWalkAnimation;
    private Animation state;
    private int x,y,steps;
    private String currentCommand="",code;
    private static final float speed = 10;
    private Scanner codeScan;
    private boolean isRunning;

    public character(int x, int y){
        downIldeAtlas = new TextureAtlas(Gdx.files.internal("downIdle.atlas"));
        downWalkAtlas = new TextureAtlas(Gdx.files.internal("downWalk.atlas"));
        upIldeAtlas = new TextureAtlas(Gdx.files.internal("upIdle.atlas"));
        upWalkAtlas = new TextureAtlas(Gdx.files.internal("upWalk.atlas"));
        sideIldeAtlas = new TextureAtlas(Gdx.files.internal("sideIdle.atlas"));
        sideWalkAtlas = new TextureAtlas(Gdx.files.internal("sideWalk.atlas"));
        downIdleAnimation = new com.badlogic.gdx.graphics.g2d.Animation<TextureRegion>(1/30f, downIldeAtlas.getRegions());
        downWalkAnimation = new com.badlogic.gdx.graphics.g2d.Animation<TextureRegion>(1/30f, downWalkAtlas.getRegions());
        upIdleAnimation = new com.badlogic.gdx.graphics.g2d.Animation<TextureRegion>(1/30f, upIldeAtlas.getRegions());
        upWalkAnimation = new com.badlogic.gdx.graphics.g2d.Animation<TextureRegion>(1/30f, upWalkAtlas.getRegions());
        sideIdleAnimation = new com.badlogic.gdx.graphics.g2d.Animation<TextureRegion>(1/30f, sideIldeAtlas.getRegions());
        sideWalkAnimation = new com.badlogic.gdx.graphics.g2d.Animation<TextureRegion>(1/30f, sideWalkAtlas.getRegions());
        state = downIdleAnimation;
        isRunning = false;

        this.x = x;
        this.y = y;
        this.steps = 5;
    }

    public int getX(){
        return  x;
    }

    public int getY(){
        return  y;
    }

    public boolean isRunning() { return isRunning; }

    public TextureRegion currentState(float time,boolean bol){
        return (TextureRegion) state.getKeyFrame(time,bol);
    }

    public void render(float frames){
        if ( steps != 50){
            if (currentCommand.equals("up")){
                y++;
            }
            else if(currentCommand.equals("right")){
                x++;
            }
            else if(currentCommand.equals("down")){
                y--;
            }
            else if(currentCommand.equals("left")){
                x--;
            }
            steps++;
        }
        else{
            steps = 0;
            if ( codeScan.hasNextLine() ){
                movement(codeScan.nextLine());
            }
            else{
                isRunning = false;
                codeScan = null;
                code = "";
            }
        }
    }

    public void runCode(String code){
        this.code = code;
        codeScan = new Scanner(this.code);
        isRunning = true;
    }

    public void movement(String com){
        if (com.equals("up")){
            state = upIdleAnimation;
        }
        else if(com.equals("right")){
            state = sideIdleAnimation;
        }
        else if(com.equals("down")){
            state = downIdleAnimation;
        }
        else if(com.equals("left")){
            state = sideIdleAnimation;
        }
        currentCommand = com;
    }
}
