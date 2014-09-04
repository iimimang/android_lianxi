package com.notebook.service;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notbook.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.notbook.app.Info;
import com.notbook.entity.Thing;
import com.notbook.ui.ChuanYu;
import com.notbook.ui.MainActivity2;
import com.notbook.ui.Welcome;


public class ChatService extends Service {
	private static final int WRAP = WindowManager.LayoutParams.WRAP_CONTENT;
	/** 常规的顶级窗口,该顶级view下的部件可以继续操作 */
	public static final int TYPE_TOPLEVEL_WIN_NORMAL = 1;

	/** 遮罩式模式顶级窗口--该顶级view下的部件被遮住，不可以操作*/
	public static final int TYPE_TOPLEVEL_WIN_MODAL = 2;
	private static final int ID_MESSAGE = 3;
	
	public static Context context;
	
	private WindowManager mWin;
	
	public static final int NOTIFY_ID = 1;
	private int id = 0;
	private String url ;
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void onCreate() {
		super.onCreate();
		mWin = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
		url = Info.uri+"/sendCyuanyuAction.php" ;
		HttpMultipartPost task = new HttpMultipartPost();
		task.execute();
		/*new TextAsyncTask()
		.execute(Info.uri+"/androidpush.php");
	    Log.d("log","---------------------------------------123");*/
//		new DownloadTask();
	}
	/*private class DownloadTask implements Runnable {
		@Override
		public void run() {
			while (true) {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				handler.sendMessage(new Message());
			}
		}
	}
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			new TextAsyncTask()
			.execute(Info.uri+"/androidpush.php");
		    Log.d("log","---------------------------------------123");
		}

	};*/
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
			/*new TextAsyncTask()
			.execute("http://192.168.1.57/notebook/api/sendCyuanyuAction.php");*/
//			Log.d("log","---------------------------------------456");
		
		return super.onStartCommand(intent, flags, startId);
	}
	
	private void sendNotif(String title,String content){
		NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
		
		Notification notification = new Notification(R.drawable.gtd1, content, System.currentTimeMillis());
		Intent intent = new Intent(getBaseContext(),MainActivity2.class);
		intent.putExtra("id", ""+id);
		intent.putExtra("list", "");
		intent.putExtra("day", "");
		PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		notification.setLatestEventInfo(getApplicationContext(), "记事本", content, pendingIntent);
		notification.flags = Notification.FLAG_AUTO_CANCEL;//点击通知之后，取消状态栏图标
		manager.notify(NOTIFY_ID, notification);
	}
	
	private android.view.WindowManager.LayoutParams getWinLayParams(int type) {
		WindowManager.LayoutParams wmLp = new WindowManager.LayoutParams(WRAP, WRAP);
		//设置窗口底色透明
		wmLp.alpha = 0.5f;
		wmLp.dimAmount = 0.0f;
		wmLp.format = PixelFormat.TRANSPARENT;
		if (type == TYPE_TOPLEVEL_WIN_MODAL) {// 模式顶级窗口
			wmLp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;// 任何部件都在其之下，可以通过dimAmount(0~1.0)设置透明度?

		} else if (type == TYPE_TOPLEVEL_WIN_NORMAL) {// 常规顶级窗口
			wmLp.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE; // 不抢聚焦点
		}
		wmLp.flags = WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
		wmLp.flags =WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS; // allow window to extend outside of the screen

		wmLp.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT; // 系统提示类型
		wmLp.width = 533;// WindowManager.LayoutParams.MATCH_PARENT;
		wmLp.height = 104;// WindowManager.LayoutParams.MATCH_P.ARENT;
		wmLp.gravity = Gravity.RIGHT | Gravity.TOP;
		return wmLp;
	}
	private View getView(String title,String content){
		RelativeLayout mLayout = null;
		RelativeLayout.LayoutParams mLp = null;
		RelativeLayout.LayoutParams mLp1 = null;
		TextView mTx = null;
		if(mLayout == null){
			mLayout = new RelativeLayout(this);
		}
		if(mLp == null){
			mLp = new RelativeLayout.LayoutParams(WRAP, WRAP);
		}   
		mLayout.setLayoutParams(mLp);
		if(mTx == null){
			mTx = new TextView(this);
		}
		mTx.setId(ID_MESSAGE);
		mTx.setTextSize(25);
		mTx.setTextColor(Color.WHITE);
		mTx.setSingleLine(true);
		mTx.setEllipsize(TextUtils.TruncateAt.MARQUEE);
		mTx.setMarqueeRepeatLimit(6);
		mTx.setFocusable(true);
		if(mLp1 == null){
			mLp1 = new RelativeLayout.LayoutParams(WRAP, WRAP);
		}
		mLp1.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
		mTx.setPadding(10, 10, 10, 10);
		mLayout.addView(mTx, mLp1);
		return mLayout;
	}
	class TextAsyncTask extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {
			return getResultByPost(params[0]);
		}

		@Override
		protected void onPostExecute(String result) {

			if (result == null) {
			} else {
				Log.d("log", "---------------------result="+result);
				JSONObject object;
				try {
					object = new JSONObject(result);
					int id = object.getInt("responsecode");  
				   
						if(id == 106){
							return ;
						}else {
							String share = object.getString("result"); 
							Gson gson = new Gson();
							Type type = new TypeToken<List<Thing>>() {}.getType();
							List<Thing> l = gson.fromJson(share, type);
							Log.d("log", "---------------------l="+l);
							String content = l.get(0).getText();
							String title = l.get(0).getCname();
							sendNotif(title,content);
						}   
				    
					
				} catch (JSONException e) {
				e.printStackTrace();
				}
				
				
			}
			super.onPostExecute(result);
		}
	}

	// ʹ��HttpClient��Post��ʽ
	private String getResultByPost(String url) {

		Log.v("TAG","-----------�������url=="+url);
		String result = null;
		try {
			HttpGet request = new HttpGet(url);
			HttpClient client = new DefaultHttpClient();
			HttpResponse response = client.execute(request);

			HttpEntity entity = response.getEntity();
			result = EntityUtils.toString(entity);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	class HttpMultipartPost extends AsyncTask<HttpResponse, Integer, String> {
		ProgressDialog pd;
		long totalSize;
		Context context;
	 
		@Override
		protected void onPreExecute() {
		
		}

		@Override
		protected String doInBackground(HttpResponse... arg0) {
			HttpClient httpClient = new DefaultHttpClient();
			HttpContext httpContext = new BasicHttpContext();
			HttpPost httpPost ;
			String serverResponse ; 
			
			while(true){
				try {
						Thread.sleep(60*30*1000);
						httpPost = new HttpPost(url);
						Log.i("log","--------------------------------1"+url);
						HttpResponse response = httpClient.execute(httpPost, httpContext);
						serverResponse = EntityUtils.toString(response.getEntity());
						Log.i("log","上传结果"+serverResponse);
						JSONObject object = new JSONObject(serverResponse);
						int id = object.getInt("responsecode");  
					   
							if(id == 106){
								
							}else {
								String share = object.getString("result"); 
								Gson gson = new Gson();
								Type type = new TypeToken<List<Thing>>() {}.getType();
								List<Thing> l = gson.fromJson(share, type);
								Log.d("log", "---------------------l="+l);
								String title = l.get(0).getCname();
								String content = l.get(0).getText();
								sendNotif(title,content);
							}   
				}
	
				catch (Exception e) {
					System.out.println(e);
				}
				
			}
		}

		@Override
		protected void onProgressUpdate(Integer... progress) {
		}

		@Override
		protected void onPostExecute(String ui) {
			
		}
	}
}
