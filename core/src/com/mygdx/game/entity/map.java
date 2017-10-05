package com.mygdx.game.entity;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;

public class map {
    private TiledMap map;
    private TiledMapRenderer mapRenderer;
    private OrthographicCamera camera;
    private Rectangle[] edgeBox;

    public map(String mapStage){
        map = new TmxMapLoader().load(mapStage);
        camera = new OrthographicCamera();
        camera.setToOrtho(false,getMapWidth(),getMapHeight());
        camera.update();
        mapRenderer = new OrthogonalTiledMapRenderer(map);
        map.getLayers().get("top");
        edgeBox = new Rectangle[4];
        edgeBox[0] = new Rectangle(0,0,1,getMapHeight());
        edgeBox[1] = new Rectangle(0,0,getMapWidth(),1);
        edgeBox[2] = new Rectangle(0,getMapHeight(),getMapWidth(),1);
        edgeBox[3] = new Rectangle(getMapWidth(),0,1,getMapHeight());
    }

    public void draw(SpriteBatch batch){
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
}