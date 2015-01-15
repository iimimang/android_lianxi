package cn.chinat2t.cloud.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import cn.chinat2t.cloud.utils.CtLog;
import cn.chinat2t.cloud.utils.FilePath;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

public class BitmapManager {
	private static HashMap<String, SoftReference<Bitmap>> cache; // ͼƬ�����ǵ��м��ع���ͬ��ͼƬ��ʱ�򣬿��Կ����ظ�ʹ�ã�����ͬһ���˵�ͷ��
	private static ExecutorService pool; // �̶��̳߳���Ĳ����߳��������Է�ֹ�û��ڿ��ٻ����б��ʱ�򣬲�ִ���Ѿ�����ȥ�ļ����̡߳�
	private static Map<ImageView, String> imageViews;
	private Bitmap defaultBmp;
	private static BitmapManager instance = null;

	static {
		cache = new HashMap<String, SoftReference<Bitmap>>();
		pool = Executors.newFixedThreadPool(5); // �̶��̳߳�
		imageViews = Collections
				.synchronizedMap(new WeakHashMap<ImageView, String>());
	}

	private BitmapManager() {
	}

	private BitmapManager(Bitmap def) {
		this.defaultBmp = def;
	}

	public static BitmapManager getInstance() {
		if (instance == null) {
			instance = new BitmapManager();
		}
		return instance;
	}

	/**
	 * ����Ĭ��ͼƬ
	 * 
	 * @param bmp
	 */
	public void setDefaultBmp(Bitmap bmp) {
		defaultBmp = bmp;
	}

	/**
	 * ����ͼƬ
	 * 
	 * @param url
	 * @param imageView
	 */
	public void loadBitmap(String url, ImageView imageView) {
		loadBitmap(url, imageView, this.defaultBmp, 0, 0);
	}

	/**
	 * ����ͼƬ-�����ü���ʧ�ܺ���ʾ��Ĭ��ͼƬ
	 * 
	 * @param url
	 * @param imageView
	 * @param defaultBmp
	 */
	public void loadBitmap(String url, ImageView imageView, Bitmap defaultBmp) {
		loadBitmap(url, imageView, defaultBmp, 0, 0);
	}

    //Բ�Ǵ���   
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx)  
    {  
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(),  
                Config.ARGB_8888);  
        Canvas canvas = new Canvas(output);  
           
        final int color = 0xff424242;  
        final Paint paint = new Paint();  
        final Rect rect = new Rect(0,0, bitmap.getWidth(), bitmap.getHeight());  
        final RectF rectF = new RectF(rect);  
           
        paint.setAntiAlias(true);  
        canvas.drawARGB(0, 0, 0, 0);  
        paint.setColor(color);  
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);  
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));  
        canvas.drawBitmap(bitmap, rect, rect, paint);  
           
        return output;  
    }  

    /**
     * ����Բ��ͼƬ
     * 
     */
    
    public void loadRoundBitmap(String url, ImageView imageView, Bitmap defaultBmp,
			int width, int height) {
		CtLog.d("image", "url = "+url);
		imageViews.put(imageView, url);
		Bitmap bitmap = getBitmapFromCache(url);
        
		if (bitmap != null) {
			// ��ʾ����ͼƬ
			bitmap=getRoundedCornerBitmap(bitmap, 1.5f);
			imageView.setImageBitmap(bitmap);
		} else {
			// ����SD���е�ͼƬ��
			String filename = FilePath.getInstance().getFileName(url);
			FilePath.getInstance().isExists(FilePath.PORTRAIT);
			String filepath = FilePath.PORTRAIT + "/" + filename;
			File file = new File(filepath);
			if (file.exists()) {
				// ��ʾSD���е�ͼƬ��
				try {
					FileInputStream fis = new FileInputStream(file);
					BitmapFactory.Options options = new BitmapFactory.Options();
					options.inSampleSize = 2;
					Bitmap bmp = BitmapFactory.decodeStream(fis,null,options);
					if(bmp == null){
						if(defaultBmp != null){
							defaultBmp=getRoundedCornerBitmap(defaultBmp, 1.5f);
							imageView.setImageBitmap(defaultBmp);
						}
					}else {
						bmp=getRoundedCornerBitmap(bmp, 1.5f);
						imageView.setImageBitmap(bmp);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				// �̼߳�������ͼƬ
				defaultBmp=getRoundedCornerBitmap(defaultBmp, 1.5f);
				imageView.setImageBitmap(defaultBmp);
				queueJob(url, file, imageView);
			}
		}
	}
    
	
	/**
	 * ����ͼƬ-��ָ����ʾͼƬ�ĸ߿�
	 * ����ͼƬ��url��ַ���ȴ�ͼƬ������������Ƿ��ѻ���������û�У��ٴ�SD����ͼƬ�����ļ��в��ң������û�У������Ǽ�������ͼƬ
	 * 
	 * @param url
	 * @param imageView
	 * @param width
	 * @param height
	 */
	public void loadBitmap(String url, ImageView imageView, Bitmap defaultBmp,
			int width, int height) {
		CtLog.d("image", "url = "+url);
		imageViews.put(imageView, url);
		Bitmap bitmap = getBitmapFromCache(url);

		if (bitmap != null) {
			// ��ʾ����ͼƬ
			imageView.setImageBitmap(bitmap);
		} else {
			// ����SD���е�ͼƬ��
			String filename = FilePath.getInstance().getFileName(url);
			FilePath.getInstance().isExists(FilePath.PORTRAIT);
			String filepath = FilePath.PORTRAIT + "/" + filename;
			File file = new File(filepath);
			if (file.exists()) {
				// ��ʾSD���е�ͼƬ��
				try {
					FileInputStream fis = new FileInputStream(file);
					BitmapFactory.Options options = new BitmapFactory.Options();
					options.inSampleSize = 2;
					Bitmap bmp = BitmapFactory.decodeStream(fis,null,options);
					if(bmp == null){
						if(defaultBmp != null){
							imageView.setImageBitmap(defaultBmp);
						}
					}else {
						imageView.setImageBitmap(bmp);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				// �̼߳�������ͼƬ
				imageView.setImageBitmap(defaultBmp);
				queueJob(url, file, imageView);
			}
		}
	}
	
	/**
	 * ����url��ȡͼƬ
	 * @param url
	 * @return
	 */
	public Bitmap getBitmapByUrl(String url){
		Bitmap bitmap = null;
		bitmap = getBitmapFromCache(url);
		if(bitmap == null){
			// ����SD���е�ͼƬ��
			String filename = FilePath.getInstance().getFileName(url);
			FilePath.getInstance().isExists(FilePath.PICTURE);
			String filepath = FilePath.PICTURE + "/" + filename;
			File file = new File(filepath);
			if (file.exists()) {
				// ��ʾSD���е�ͼƬ��
				try {
					FileInputStream fis = new FileInputStream(file);
					BitmapFactory.Options options = new BitmapFactory.Options();
					options.inSampleSize = 2;
					bitmap = BitmapFactory.decodeStream(fis,null,options);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				// �̼߳�������ͼƬ
				bitmap = downloadBitmap(url,file);
			}
		}
		return bitmap;
	}

	/**
	 * �ӻ����л�ȡͼƬ
	 * 
	 * @param url
	 */
	private Bitmap getBitmapFromCache(String url) {
		Bitmap bitmap = null;
		if (cache.containsKey(url)) {
			bitmap = cache.get(url).get();
		}
		return bitmap;
	}

	/**
	 * �������м���ͼƬ
	 * 
	 * @param url
	 * @param imageView
	 * @param width
	 * @param height
	 */
	public void queueJob(final String url, final File file,
			final ImageView imageView) {
		/* Create handler in UI thread. */
		final Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				String tag = imageViews.get(imageView);
				if (tag != null && tag.equals(url)) {
					if (msg.obj != null) {
						imageView.setImageBitmap((Bitmap) msg.obj);
					}
				}
			}
		};

		new Thread(new Runnable() {
			public void run() {
				Message message = Message.obtain();
				message.obj = downloadBitmap(url, file);
				handler.sendMessage(message);
			}
		}).start();
		
//		pool.execute(new Runnable() {
//			public void run() {
//				Message message = Message.obtain();
//				message.obj = downloadBitmap(url, file);
//				handler.sendMessage(message);
//			}
//		});
	}

	public File downloadBitmapFile(String path, File file) {
		Bitmap bitmap = null;
		try {
			URL url = new URL(path);  
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
			conn.setConnectTimeout(5000);  
			conn.setRequestMethod("GET");  
		    conn.setDoInput(true);  
			if (conn.getResponseCode() == 200) {  
		        InputStream is = conn.getInputStream();  
			   FileOutputStream fos = new FileOutputStream(file);  

			                byte[] buffer = new byte[1024];  

			                int len = 0;  

			                 while ((len = is.read(buffer)) != -1) {  

			                     fos.write(buffer, 0, len);  

			                 }  

			                is.close();  

			                fos.close();  

			
			
			// http����ͼƬ
//			HttpGet httpGet = new HttpGet(path);
//			httpGet.setHeader("Content-Type","application/json;charset=UTF-8");
//			HttpClient httpclient = new DefaultHttpClient();
//			HttpParams params = httpclient.getParams();
//			HttpConnectionParams.setConnectionTimeout(params, 60 * 1000);
//			HttpConnectionParams.setSoTimeout(params, 30 * 1000);
//			HttpResponse httpresponse = httpclient.execute(httpGet);
//			CtLog.d("client", "getStatusCode = "+httpresponse.getStatusLine().getStatusCode());
//			if (httpresponse.getStatusLine().getStatusCode() == 200) {
//				HttpEntity entity = httpresponse.getEntity();
//				InputStream is = entity.getContent();
//				FileOutputStream fos = new FileOutputStream(file);
//				byte[] buffer = new byte[1024];
//				int len = 0;
//				while ((len = is.read(buffer)) != -1) {
//					fos.write(buffer, 0, len);
//				}
//				is.close();
//				fos.close();
				return file;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return file;
	}
	/**
	 * ����ͼƬ-��ָ����ʾͼƬ�ĸ߿�
	 * 
	 * @param url
	 * @param width
	 * @param height
	 */
	private Bitmap downloadBitmap(String path, File file) {
		Bitmap bitmap = null;
		try {
			// http����ͼƬ
			HttpGet httpGet = new HttpGet(path);
			httpGet.setHeader("Content-Type","application/json;charset=UTF-8");
			HttpClient httpclient = new DefaultHttpClient();
			HttpParams params = httpclient.getParams();
			HttpConnectionParams.setConnectionTimeout(params, 60 * 1000);
			HttpConnectionParams.setSoTimeout(params, 30 * 1000);
			HttpResponse httpresponse = httpclient.execute(httpGet);
			CtLog.d("client", "getStatusCode = "+httpresponse.getStatusLine().getStatusCode());
			if (httpresponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = httpresponse.getEntity();
				InputStream is = entity.getContent();
				FileOutputStream fos = new FileOutputStream(file);
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = is.read(buffer)) != -1) {
					fos.write(buffer, 0, len);
				}
				is.close();
				fos.close();
				// ����һ��URI����
				FileInputStream fa = new FileInputStream(file.toString());
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = 2;
				bitmap = BitmapFactory.decodeStream(fa,null,options);

				// ���뻺��
				cache.put(path, new SoftReference<Bitmap>(bitmap));
				return bitmap;
			}
			
//			URL url = new URL(path);
//			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//			conn.setConnectTimeout(10 * 1000);
//			conn.setRequestMethod("GET");
//			conn.setDoInput(true);
//			if (conn.getResponseCode() == 200) {
//
//				InputStream is = conn.getInputStream();
//				FileOutputStream fos = new FileOutputStream(file);
//				byte[] buffer = new byte[1024];
//				int len = 0;
//				while ((len = is.read(buffer)) != -1) {
//					fos.write(buffer, 0, len);
//				}
//				is.close();
//				fos.close();
//				// ����һ��URI����
//				FileInputStream fa = new FileInputStream(file.toString());
//				bitmap = BitmapFactory.decodeStream(fa);
//
//				// ���뻺��
//				cache.put(path, new SoftReference<Bitmap>(bitmap));
//				return bitmap;
//			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;
	}
}
