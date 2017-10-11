package com.mygdx.game.entity;

public class conditions {
    private int noOfCoins;
    private boolean characterFinish;

    public conditions(int noOfCoins){
        this.noOfCoins = noOfCoins;
        characterFinish = false;
    }

    public void setCharacterFinish(boolean isFinish){ characterFinish = isFinish; }

    public void coinReduce(){noOfCoins--;}

    public boolean isConditionsMeet() {
        return noOfCoins == 0 && characterFinish;
    }

}
