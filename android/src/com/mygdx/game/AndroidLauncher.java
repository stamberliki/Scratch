package com.mygdx.game;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.googlecode.tesseract.android.TessBaseAPI;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static android.os.Environment.DIRECTORY_PICTURES;

public class AndroidLauncher extends AndroidApplication {
	private MyGdxGame game;
	private Activity ActivityCompat;
	private TessBaseAPI baseApi;
	private interface_implement tess;
	private splash textCode;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActivityCompat  = new Activity();
		baseApi = new TessBaseAPI();
		tess = new interface_implement(this,baseApi);
		game = new MyGdxGame(tess);
		textCode = new splash();
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		baseApi.init(getFilesDir().toString()+"/tesseract/", "eng");
		checkFile(new File(getFilesDir()+"/tesseract/tessdata"));
		initialize(game, config);
//		Log.e(" ",Environment.getExternalStoragePublicDirectory(DIRECTORY_PICTURES).toString());
	}

	private void copyFiles() {
		try {
			String filepath = getFilesDir() + "/tesseract/tessdata/eng.traineddata";
			android.content.res.AssetManager assetManager = getAssets();

			InputStream instream = assetManager.open("tessdata/eng.traineddata");
			OutputStream outstream = new FileOutputStream(filepath);

			byte[] buffer = new byte[1024];
			int read;
			while ((read = instream.read(buffer)) != -1) {
				outstream.write(buffer, 0, read);
			}
			outstream.flush();
			outstream.close();
			instream.close();
			assetManager.close();
			Log.e("","file creation finish");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void checkFile(File dir) {
		if (!dir.exists()&& dir.mkdirs()){
			copyFiles();
		}
		if(dir.exists()) {
			String datafilepath = getFilesDir()+ "/tesseract/tessdata/eng.traineddata";
			File datafile = new File(datafilepath);
			if (!datafile.exists()) {
				copyFiles();
			}
		}
	}

}