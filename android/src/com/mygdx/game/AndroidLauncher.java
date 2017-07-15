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

public class AndroidLauncher extends AndroidApplication implements tess_interface {
	private MyGdxGame game;
	private Activity ActivityCompat;
	private TessBaseAPI baseApi;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActivityCompat  = new Activity();
		checkFile(new File(getFilesDir()+"/tesseract/tessdata"));
		game = new MyGdxGame();
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
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

	@Override
	public void TessBaseAPI() {
		baseApi = new TessBaseAPI();
		baseApi.init(getFilesDir().toString()+"/tesseract/", "eng");
	}

	@TargetApi(Build.VERSION_CODES.M)
	@Override
	public ClickListener setGallerySelect() {
		ClickListener event = new ClickListener(){
			@Override
			public boolean handle(Event e){
				if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(getContext(),Manifest.permission.CAMERA)) {
					Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					startActivityForResult(i, 1);
				} else {
					if (ActivityCompat.shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
						new AlertDialog.Builder(getContext())
								.setMessage("This app needs permission to use The phone Camera in order to activate the Scanner")
								.setPositiveButton("Allow", new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int which) {
										ActivityCompat.requestPermissions(new String[]{Manifest.permission.CAMERA}, 1);
									}
								}).show();
					} else {
						ActivityCompat.requestPermissions(new String[]{Manifest.permission.CAMERA}, 1);
					}
				}
				Log.e("btn","Btn ok");
				return true;
			}
		};
		return event;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
		super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

		switch (requestCode) {
			case 1:
				if (resultCode ==  RESULT_OK  ) {
					Uri selectedImage = imageReturnedIntent.getData();
					String[] filePathColumn = {MediaStore.Images.Media.DATA};
					Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
					cursor.moveToFirst();
					if (cursor.moveToFirst()) {
						int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
						String filePath = cursor.getString(columnIndex);
						Bitmap bitmap = BitmapFactory.decodeFile(filePath);
						try {
							baseApi.setImage(bitmap.copy(Bitmap.Config.ARGB_8888,true));
							String recognizedText = baseApi.getUTF8Text();
							game.setCodeFromOCR(recognizedText);
							Log.e("result: ",recognizedText);
						}catch (Exception e){
							Log.e(" ",e.toString());
						}
					}
					cursor.close();
				}
				break;
		}
	}
}