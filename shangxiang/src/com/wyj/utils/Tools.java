package com.wyj.utils;

import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Tools {

	public static Bitmap readBitmap(Context context, int id){
		 BitmapFactory.Options opt = new BitmapFactory.Options();
	     opt.inPreferredConfig=Bitmap.Config.RGB_565;//表示16位位图 565代表对应三原色占的位数
	     
	     opt.inInputShareable=true;
	     opt.inPurgeable=true;//设置图片可以被回收
	     InputStream is = context.getResources().openRawResource(id);
	     
	     return   BitmapFactory.decodeStream(is, null, opt);
	     
	}
}
