package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Created by Anony on 7/14/2017.
 */

public interface tess_interface  {
    ClickListener setGallerySelect();
    void setCodeOCR(String code);
    String getCode();
}
