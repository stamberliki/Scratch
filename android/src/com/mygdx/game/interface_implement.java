
package com.mygdx.game;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.googlecode.tesseract.android.TessBaseAPI;
import com.mygdx.game.entity.gameException;

import bsh.EvalError;
import bsh.Interpreter;
import bsh.TargetError;

public class interface_implement extends AndroidLauncher implements tess_interface {
    private AndroidApplication app;
    private TessBaseAPI tess;
    private String code;
    private Interpreter interpreter;

    public interface_implement(AndroidApplication p){
        this.tess = new TessBaseAPI();
        app = p;
        code = "";
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public ClickListener setGallerySelect() {
            ClickListener ret = new ClickListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    return true;
                }
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    try {
                        if (ContextCompat.checkSelfPermission(app.getContext(),Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                            Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            app.startActivityForResult(i, 1);
                        } else {
                            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) app.getContext(), Manifest.permission.CAMERA)) {
                                new AlertDialog.Builder(app.getContext())
                                        .setMessage("This app needs permission to use The phone Camera in order to activate the Scanner")
                                        .setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                ActivityCompat.requestPermissions((Activity) app.getContext(), new String[]{Manifest.permission.CAMERA}, 1);
                                            }
                                        }).show();
                            } else {
                                ActivityCompat.requestPermissions((Activity) app.getContext(), new String[]{Manifest.permission.CAMERA}, 1);
                            }
                        }
                    }catch (Exception ex){
                        app.log(" ",ex.getMessage());
                    }

                }

            };
        return ret;
    }

    @Override
    public void setCodeOCR(String code){
        this.code = code;
    }

    @Override
    public String getCode(){return code;}
    
    @Override
    public boolean runCode(String code, Object o) throws gameException{
        interpreter = new Interpreter();
        try{
        interpreter.set("hero", o);
        interpreter.eval(code);
        }
        catch (TargetError e){
            throw new gameException((e.getTarget().getMessage()));
        }
        catch (Exception e){
            throw new gameException(e);
        }
        return true;
    }
    
}
