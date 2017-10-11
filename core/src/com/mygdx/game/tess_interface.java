package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public interface tess_interface  {
    ClickListener setGallerySelect();
    void setCodeOCR(String code);
    String getCode();
    boolean runCode(String code,Object o) throws Exception;
}
