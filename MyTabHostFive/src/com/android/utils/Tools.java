package com.android.utils;

import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Tools {

	public static Bitmap readBitmap(Context context, int id){
		 BitmapFactory.Options opt = new BitmapFactory.Options();
	     opt.inPreferredConfig=Bitmap.Config.RGB_565;//��ʾ16λλͼ 565�����Ӧ��ԭɫռ��λ��
	     
	     opt.inInputShareable=true;
	     opt.inPurgeable=true;//����ͼƬ���Ա�����
	     InputStream is = context.getResources().openRawResource(id);
   	 return   BitmapFactory.decodeStream(is, null, opt);
	     
	}
}
