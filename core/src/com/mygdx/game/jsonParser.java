package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

public class jsonParser {
    private JsonValue res;

    public jsonParser(){
        res = new JsonReader().parse(Gdx.files.internal("levels.json"));
    }

    public JsonValue getJson(String level){return res.get(level);}

}
