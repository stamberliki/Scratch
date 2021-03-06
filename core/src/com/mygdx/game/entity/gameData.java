package com.mygdx.game.entity;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonValue;

public class gameData {
    public int noOfCoin, noOfEnemy;
    public int[][] coinPositions,enemyPositions;
    public int[] heroPosition;
    public String[] enemyNames,enemyDirection;
    public String musicName, codes;
    public boolean hasTiledAnimation, hasNoFinishBlock;

    public gameData(JsonValue res){
        noOfCoin = res.getInt("noOfCoin");
        noOfEnemy = res.getInt("noOfEnemy");
        coinPositions = new int[noOfCoin][2];
        enemyPositions = new int[noOfEnemy][2];
        enemyNames = res.get("enemyNames").asStringArray();
        enemyDirection = res.get("enemyDirection").asStringArray();
        musicName = res.getString("audio");
        codes = res.getString("code");
        hasTiledAnimation = res.getBoolean("hasTiledAnimation");
        hasNoFinishBlock = res.getBoolean("hasNoFinishBlock");
        heroPosition = res.get("heroPosition").asIntArray();
        int a = 0;
        for ( JsonValue x: res.get("coinPositions") ){
            coinPositions[a++] = x.asIntArray();
        }
        a = 0;
        for ( JsonValue x: res.get("enemyPositions") ){
            enemyPositions[a++] = x.asIntArray();
        }
    }


}
