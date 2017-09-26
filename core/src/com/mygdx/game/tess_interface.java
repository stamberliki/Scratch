package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public interface tess_interface  {
    ClickListener setGallerySelect();
    void setCodeOCR(String code);
    String getCode();
    void runCode(String code,Object o);
}
