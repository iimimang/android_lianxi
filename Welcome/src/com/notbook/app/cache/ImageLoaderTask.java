package com.notbook.app.cache;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.HashMap;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ImageLoaderTask extends AsyncTask<TaskParam, Void, Bitmap> {

	private TaskParam param;
	private final WeakReference<ImageView> imageViewReference; // ��ֹ�ڴ����
	private String path;
	private String name;
	private int type = 0;
	private View v ;

	public ImageLoaderTask(ImageView imageView,String path, String name) {
		imageViewReference = new WeakReference<ImageView>(imageView);
		this.path = path;
		this.name = name;
	}
	public ImageLoaderTask(ImageView imageView,String path, String name,View v) {
		imageViewReference = new WeakReference<ImageView>(imageView);
		this.path = path;
		this.name = name;
		this.v = v;
	}
	public ImageLoaderTask(ImageView imageView,String path, String name,int type) {
		imageViewReference = new WeakReference<ImageView>(imageView);
		this.path = path;
		this.name = name;
		this.type = type;
	}

	@Override
	protected Bitmap doInBackground(TaskParam... params) {

		param = params[0];
		Bitmap bit = null ;
		try {
			
			bit = loadImageFile(param.getFilename(), param.getAssetManager());
		} catch (Exception e) {
			// TODO: handle exception
		}
		return bit;
	}

	private Bitmap loadImageFile(String filename, AssetManager manager) {
		InputStream is = null;
		try {
			Bitmap bmp = null;
			bmp = BitmapCache.getInstance().getBitmap(filename,
					manager,this.name);
			return bmp;
		} catch (Exception e) {
			Log.e(this.getClass().getSimpleName(), "fetchDrawable failed", e);
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	protected void onPostExecute(Bitmap bitmap) {
//		if (isCancelled()) {
//			bitmap = null;
//		}

		if (imageViewReference != null) {
			ImageView imageView = imageViewReference.get();
			if (imageView != null) {
				if (bitmap != null) {
//					imageView.setImageBitmap(bitmap);
					if(type == 1){
						imageView.setImageBitmap(bitmap);
					}else{
						imageView.setBackgroundDrawable(new BitmapDrawable(bitmap));
					}
					

				}

			}
		}
		
	}
	
}