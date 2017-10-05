package com.mygdx.game.entity;

/**
 * Created by Anony on 10/5/2017.
 */

public class conditions {
    private int noOfCoins;
    private boolean characterFinish;

    public conditions(int noOfCoins){
        this.noOfCoins = noOfCoins;
        characterFinish = false;
    }

    public void setCharacterFinish(boolean isFinish){ characterFinish = isFinish; }

    public boolean isConditionsMeet(){
        if (noOfCoins == 0 && characterFinish){
            return  true;
        }
        return false;
    }

}
