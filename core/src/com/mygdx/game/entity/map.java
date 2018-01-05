package com.mygdx.game.entity;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class map {
    private TiledMap map;
    private TiledMapRenderer mapRenderer;
    private OrthographicCamera camera;

    public map(String mapStage){
        map = new TmxMapLoader().load(mapStage);
        camera = new OrthographicCamera();
        camera.setToOrtho(false,getMapWidth(),getMapHeight());
        camera.update();
        mapRenderer = new OrthogonalTiledMapRenderer(map);
    }

    public void draw(){
        camera.update();
        mapRenderer.setView(camera);
        mapRenderer.render();
    }

    public void setX(float x){camera.position.x = x;}

    public void setY(float y){camera.position.y = y;}

    public void setViewportWidth (float w){camera.viewportWidth = w;}

    public void setViewportHeight (float h){camera.viewportHeight = h;}

    public float getMapWidth(){return map.getProperties().get("width",Integer.class)*map.getProperties().get("tilewidth",Integer.class);}

    public float getMapHeight(){return map.getProperties().get("height",Integer.class)*map.getProperties().get("tileheight",Integer.class);}

    public OrthographicCamera getCamera(){return  camera;}

    public TiledMap getMap(){return map;}

    public TiledMapRenderer getMapRenderer(){return mapRenderer;}

}