package com.mygdx.game;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.googlecode.tesseract.android.TessBaseAPI;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import bsh.Interpreter;

import static android.os.Environment.DIRECTORY_PICTURES;

public class AndroidLauncher extends AndroidApplication {
	private MyGdxGame game;
	private TessBaseAPI baseApi;
	private interface_implement tess;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		tess = new interface_implement(this);
		game = new MyGdxGame(tess);
		checkFile(new File(getFilesDir()+"/tesseract/tessdata"));

		initialize(game, config);
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
	protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {

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
							baseApi = new TessBaseAPI();
							baseApi.init(getFilesDir().toString()+"/tesseract/", "eng");
							baseApi.setImage(bitmap.copy(Bitmap.Config.ARGB_8888,true));
							tess.setCodeOCR(baseApi.getUTF8Text());
							baseApi.end();
					}
					cursor.close();
				}
				break;
		}
	}
}