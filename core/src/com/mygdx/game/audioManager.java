package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;

public class audioManager {
    private Preferences preferences;
    private Music menuMusic,gameMusic;

    public audioManager(){
        preferences = Gdx.app.getPreferences("AudioPref");

        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/menu.ogg"));
        menuMusic.setLooping(true);
        preferences.putBoolean("menuAudioOn",true);

        preferences.flush();
    }

    public Preferences getPreferences(){return preferences;}

    public Music getMenuMusic(){return menuMusic;}

    public Music getGameMusic(){return gameMusic;}

    public void setGameMusic(String name){
        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/"+name));
    }

}
