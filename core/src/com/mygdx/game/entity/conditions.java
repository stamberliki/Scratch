package com.mygdx.game.entity;

public class conditions {
    public int noOfCoins;
    public int noOfEnemies;
    public boolean characterFinish,characterDead;

    public conditions(gameData gameData){
        this.noOfCoins = gameData.noOfCoin;
        this.noOfEnemies = gameData.noOfEnemy;
        characterFinish = false;
        characterDead = false;
        characterFinish = gameData.hasNoFinishBlock;
    }

    public void setCharacterFinish(boolean isFinish){ characterFinish = isFinish; }

    public void setCharacterDead(boolean isDead){characterDead = isDead;}

    public void coinReduce(){noOfCoins-=1;}

    public void enemyReduce(){noOfEnemies-=1;}

    public boolean isConditionsMeet() {
        return noOfCoins == 0 && characterFinish && noOfEnemies == 0;
    }

    public boolean isCharacterDead(){return characterDead;}

}
