package com.mygdx.game.entity;

import com.badlogic.gdx.utils.JsonValue;

public class gameData {
    public int noOfCoin;
    public int[][] coinPositions;
    public int[] heroPosition;
    public String musicName;

    public gameData(JsonValue res){
        noOfCoin = res.getInt("noOfCoin");
        coinPositions = new int[noOfCoin][2];
        res.iterator();
        heroPosition = res.get("heroPosition").asIntArray();
        int a = 0;
        for ( JsonValue x: res.get("coinPositions") ){
            coinPositions[a++] = x.asIntArray();
        }
        musicName = res.getString("audio");
    }


}
