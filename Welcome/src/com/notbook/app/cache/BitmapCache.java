package com.notbook.app.cache;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.net.URL;
import java.util.Hashtable;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.widget.Button;
/**
 * 防止溢出
 *
 */
public class BitmapCache {
	static private BitmapCache cache;
	private Hashtable<String, BtimapRef> bitmapRefs;
	private ReferenceQueue<Bitmap> q;
	String SDPATH = "/sdcard" + File.separator+"Yashily"+ File.separator+"image"+File.separator;
	private class BtimapRef extends SoftReference<Bitmap> {
		private String _key = "";

		public BtimapRef(Bitmap bmp, ReferenceQueue<Bitmap> q, String key) {
			super(bmp, q);
			_key = key;
		}
	}

	private BitmapCache() {
		bitmapRefs = new Hashtable<String, BtimapRef>();
		q = new ReferenceQueue<Bitmap>();

	}

	/**
	 * 取得缓存器实�?
	 */
	public static BitmapCache getInstance() {
		if (cache == null) {
			cache = new BitmapCache();
		}
		return cache;

	}

	/**
	 * 以软引用的方式对�?��Bitmap对象的实例进行引用并保存该引�?
	 */
	private void addCacheBitmap(Bitmap bmp, String key) {
		cleanCache();// 清除垃圾引用
		BtimapRef ref = new BtimapRef(bmp, q, key);
		bitmapRefs.put(key, ref);
	}

	/**
	 * 依据�?��定的文件名获取图�?
	 */
	public Bitmap getBitmap(String filename, AssetManager assetManager, String name) {

		Bitmap bitmapImage = null;
		// 缓存中是否有诤�itmap实例的软引用，�b果有，从软引用中取得�?
		if (bitmapRefs.containsKey(filename)) {
			BtimapRef ref = (BtimapRef) bitmapRefs.get(filename);
			bitmapImage = (Bitmap) ref.get();
		}
		if (bitmapImage == null) {
			
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inTempStorage = new byte[16 * 1024];

			// bitmapImage = BitmapFactory.decodeFile(filename, options);
			BufferedInputStream buf;
			try {
				File sdcardDir = new File(SDPATH+File.separator+name);
				if(sdcardDir.exists()){
					if(name!=null&&!name.equals("")){
						bitmapImage = decodeFile(sdcardDir);
					}
				}else{
					sdcardDir.getParentFile().mkdirs();
					URL url;
					url = new URL(filename);
					InputStream input = url.openStream();
					try {
						bitmapImage = BitmapFactory.decodeStream(input);
					} catch (Exception e) {
					     options.inJustDecodeBounds = false; 
					     options.inSampleSize = 2;   //width��hight��Ϊԭ4��2��һ
					     bitmapImage =BitmapFactory.decodeStream(input,null,options); 
					}
					try {
					BufferedOutputStream bos;
					bos = new BufferedOutputStream(new FileOutputStream(sdcardDir));
					bitmapImage.compress(Bitmap.CompressFormat.JPEG, 80, bos);
					bos.flush();
	                bos.close();
	            	
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				bitmapImage = getRoundedCornerBitmap(bitmapImage,(float)10);
				this.addCacheBitmap(bitmapImage, filename);
			} catch (IOException e) {

				e.printStackTrace();
			}

		}
		return bitmapImage;
	}
	public Bitmap getBitmap(String filename) {

		Bitmap bitmapImage = null;
		if (bitmapRefs.containsKey(filename)) {
			BtimapRef ref = (BtimapRef) bitmapRefs.get(filename);
			bitmapImage = (Bitmap) ref.get();
		}
		return bitmapImage;
	}
	private Bitmap decodeFile(File f){
	    Bitmap b = null;
	    try {
	        //Decode image size
	        BitmapFactory.Options o = new BitmapFactory.Options();

	        FileInputStream fis = new FileInputStream(f);
	        
	        
	        o.inJustDecodeBounds = false; 
		     o.inSampleSize = 2;   //width��hight��Ϊԭ4��2��һ
	        
	        BitmapFactory.decodeStream(fis, null, o);
	        fis.close();

	        int scale = 1;
	       

	        //Decode with inSampleSize
	        BitmapFactory.Options o2 = new BitmapFactory.Options();
	        o2.inSampleSize = scale;
	        fis = new FileInputStream(f);
	        b = BitmapFactory.decodeStream(fis, null, o2);
	        fis.close();
	    } catch (FileNotFoundException e) {
	    } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return b;
	}
	public synchronized static Bitmap getRoundedCornerBitmap(Bitmap bitmap,float roundPx){ 
		        Bitmap output = null;
		        if(bitmap!=null){
		        output = Bitmap.createBitmap(bitmap.getWidth(), bitmap 
	
		                .getHeight(), Config.ARGB_8888); 
	
		        Canvas canvas = new Canvas(output); 
		
		    
		
		        final int color = 0xff424242; 
		
		        final Paint paint = new Paint(); 
		
		        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()); 
		
		        final RectF rectF = new RectF(rect); 
		
		    
		
		        paint.setAntiAlias(true); 
		
		        canvas.drawARGB(0, 0, 0, 0); 
		
		        paint.setColor(color); 
		
		        canvas.drawRoundRect(rectF, roundPx, roundPx, paint); 
		
		    
		
		        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN)); 
		
		        canvas.drawBitmap(bitmap, rect, rect, paint); 
		        }
		    
		        return output; 
		
		    } 


	private void cleanCache() {
		BtimapRef ref = null;
		while ((ref = (BtimapRef) q.poll()) != null) {
			bitmapRefs.remove(ref._key);
		}
	}

	public void clearCache() {
		cleanCache();
		bitmapRefs.clear();
		System.gc();
		System.runFinalization();
	}

}
