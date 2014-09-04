package com.notbook.clock;

import java.io.IOException;

import com.example.notbook.R;
import com.notbook.things.Meta;
import com.notbook.ui.Eventdetail;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Binder;
import android.os.IBinder;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class MemorandAlarmService extends Service {
	private static final int WRAP = WindowManager.LayoutParams.WRAP_CONTENT;
//	private static final int FILL = WindowManager.LayoutParams.FILL_PARENT;
	/** 常规的顶级窗口,该顶级view下的部件可以继续操作 */
	public static final int TYPE_TOPLEVEL_WIN_NORMAL = 1;

	/** 遮罩式模式顶级窗口--该顶级view下的部件被遮住，不可以操作*/
	public static final int TYPE_TOPLEVEL_WIN_MODAL = 2;
	private static final int ID_MESSAGE = 3;
	
	public static Context context;
	
	private WindowManager mWin;
	
	public static final int NOTIFY_ID = 1;
	private int id = 0;

	private String lingyin = "";
	@Override
	public IBinder onBind(Intent intent) {
		return new Binder();
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		mWin = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		id = (int) intent.getLongExtra("id", 0);
		System.out.println("kai shi ti xing................."+id);
		String title = null;
		String content = null;
		int isAlarmed = 0;
		int isEnabled = 0;
		Cursor c = getContentResolver().query(Meta.CONTENT_URI, null, "_id=?", new String[]{""+id}, null);
		if(c != null&&c.moveToFirst()){
			c.moveToFirst();
			title = c.getString(c.getColumnIndex(Meta.TableMeta._LEIXING));
			content = c.getString(c.getColumnIndex(Meta.TableMeta._TIME));
			lingyin = c.getString(c.getColumnIndex(Meta.TableMeta._LINGYIN));
			try {
				alertMusic(title, content);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//发送通知
			sendNotif(title,content);
			c.close();
		}
		
		return super.onStartCommand(intent, flags, startId);
	}
	
	private void sendNotif(String title,String content){
		NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
		
		Notification notification = new Notification(R.drawable.gtd1, "您有事情该做了！！！", System.currentTimeMillis());
		Intent intent = new Intent(getBaseContext(),Eventdetail.class);
		intent.putExtra("id", ""+id);
		intent.putExtra("list", "");
		intent.putExtra("day", "");
		PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		notification.setLatestEventInfo(getApplicationContext(), title, content, pendingIntent);
		notification.flags = Notification.FLAG_AUTO_CANCEL;//点击通知之后，取消状态栏图标
		manager.notify(NOTIFY_ID, notification);
//		MemorandAlarmService.this.stopService(intent);
	}
	
	private void alertMusic(String title,String content) throws IllegalArgumentException, IllegalStateException, IOException{
		MediaPlayer mp = null;
		if(null!=lingyin){
			if(lingyin.equals("噔噔音")){
				mp = MediaPlayer.create(getApplicationContext(), R.raw.dengdengying);
			}
			if(lingyin.equals("紧急音")){
				mp = MediaPlayer.create(getApplicationContext(), R.raw.jingjiying);
			}
			if(lingyin.equals("提醒音")){
				mp = MediaPlayer.create(getApplicationContext(), R.raw.tixingying);
			}
			if(lingyin.equals("信息提示音")){
				mp = MediaPlayer.create(getApplicationContext(), R.raw.xinxiying);
			}
			if(lingyin.equals("一寸光阴一寸金")){
				mp = MediaPlayer.create(getApplicationContext(), R.raw.yicunguangyin);
			}
		}else{
			mp = MediaPlayer.create(getApplicationContext(), R.raw.dengdengying);
		}
		
		
		mp.start();
		
		mp.setOnCompletionListener(new OnCompletionListener() {
			
			@Override
			public void onCompletion(MediaPlayer mp) {
				// TODO Auto-generated method stub
				mp.release();
				mp = null;
			}
		});
		
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
//		mLp1.addRule(RelativeLayout.ALIGN_PARENT_TOP);
//		mLp1.topMargin = 50;
		mLp1.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
		mTx.setPadding(10, 10, 10, 10);
		//与父控件顶部对齐,据顶部0px,并水平居中
		mLayout.addView(mTx, mLp1);
		return mLayout;
	}

}
