package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

public class jsonParser {
    private JsonValue res;


    public jsonParser(String file){
        res = new JsonReader().parse(Gdx.files.internal(file));
    }

    public JsonValue getJson(String level){
        return res.get(level);
    }

}
