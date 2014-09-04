package com.notbook.ui;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.R.integer;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.notbook.R;
import com.notbook.app.Info;
import com.notbook.calendar.CalendarView;
import com.notbook.clock.MemorandAlarmService;
import com.notbook.things.Meta;
import com.notbook.ui.Friendtongxunluhaoma.HttpMultipartPost;

public class Eventadd extends BaseActivity {

	public static int broadFlag = 0;
	private Button iamgebutton1, iamgebutton2, iamgebutton3, iamgebutton4,
			iamgebutton5;
	private Button more_back, eventsave, time1, img1, img2, recoder;
	private LinearLayout moreinformation;
	private Date mDate;
	private static final String[] m = { "请选择", "正点提醒", "提前5分钟", "提前10分钟",
			"提前一小时", "提前一天" };
	private static final String[] youxianjilist = { "最高", "中等", "最低" };
	private static final String[] leixinglist = { "会议", "生日", "记事", "聚会" };
	private static final String[] ring = { "噔噔音", "紧急音", "提醒音", "信息提示音",
			"一寸光阴一寸金" };
	private Spinner spinner, gongzuoleixing, youxianji;
	private Spinner spinnerring;
	private String spinnerringtext = "信息提示音";
	private ArrayAdapter<String> adapter, adapter1, adapter2, adapter3;
	private EditText zhuti, jishi;
	private static String spinnertext = "", time1text = "",
			youxianjitext = "中等", zhutitext = "", jishitext = "", lingyingtext;
	public static int NUM = 1;
	public static String leixinglisttext = "";
	private Button zidingyibutton, lingying;
	private TextView more01;
	private TextView jishititle;// 记事标题
	private RelativeLayout more02, shouqi;
	ContentResolver resolver;
	private Context mContext = Eventadd.this;
	Calendar calendar = Calendar.getInstance();
	Integer hour = 0, minute = 0;
	// ����ѡ�����������Ӧ���жϱ��
	private static final int REQUEST_CODE_PICK_RINGTONE = 4;
	// ���������Uri���ַ���ʽ
	private String mRingtoneUri = null;
	private LinearLayout tanchu_act;
	private Button image01, image02, image03,recoderimage;
	private ImageButton image;
	public static final int NONE = 0;
	public static final int PHOTOHRAPH = 1;// 拍照
	public static final int PHOTOZOOM = 2; // 相册
	public static final int PHOTORESOULT = 3;// 结果
	public static final String IMAGE_UNSPECIFIED = "image/*";
	public  String filepath = "";
	private File mRecordDir;
	private Bitmap photo = null;
	ByteArrayOutputStream stream = null;

	// 录音
	private static final String LOG_TAG = "AudioRecordTest";
	public  String mFileName = "";
	private MediaRecorder mRecorder = null;
	private MediaPlayer mPlayer = null;
	private int eventaddnum = 1;
	private String fileName = "";
	private boolean isNeedAdd=false;
	// 构造方法
//	public Eventadd() {
//		mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
//		mFileName += "/event.aac";
//	}

	AlarmManager am;
	PendingIntent pendingIntent;
	private static int numone = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.eventadd);
		findViewById(R.id.one).setBackgroundResource(
				background_photobg[num - 1]);
		findViewById(R.id.one2).setBackgroundResource(
				background_photobg1[num - 1]);
		findViewById(R.id.one1).setBackgroundResource(
				background_photobg1[num - 1]);
		more_back = (Button) findViewById(R.id.more_back);
		moreinformation = (LinearLayout) findViewById(R.id.moreinformation);
		shouqi = (RelativeLayout) findViewById(R.id.shouqi);
		eventsave = (Button) findViewById(R.id.eventsave);
		time1 = (Button) findViewById(R.id.time1);
		img1 = (Button) findViewById(R.id.img1);
		img2 = (Button) findViewById(R.id.img2);
		recoder = (Button) findViewById(R.id.recoder);
		spinner = (Spinner) findViewById(R.id.spinner);
		spinnerring = (Spinner) findViewById(R.id.spinnerring);
		gongzuoleixing = (Spinner) findViewById(R.id.gongzuoleixing);
		youxianji = (Spinner) findViewById(R.id.youxianji);
		zidingyibutton = (Button) findViewById(R.id.zidingyibutton1);
//		lingying = (Button) findViewById(R.id.lingying);
		zhuti = (EditText) findViewById(R.id.zhuti);
		jishi = (EditText) findViewById(R.id.jishi);
		more01 = (TextView) findViewById(R.id.more01);
		jishititle = (TextView) findViewById(R.id.jishititle);
		more02 = (RelativeLayout) findViewById(R.id.more02);
		tanchu_act = (LinearLayout) findViewById(R.id.tanchu_act);
		image01 = (Button) findViewById(R.id.image01);
		image02 = (Button) findViewById(R.id.image02);
		image03 = (Button) findViewById(R.id.image03);
		recoderimage = (Button) findViewById(R.id.recoderimage);
		image = (ImageButton) findViewById(R.id.image001);
		resolver = getContentResolver();
		// 闹钟服务

		more_back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle b = getIntent().getExtras();
				String str = "";
				String day = "";
				
				if(null != b&& !b.equals("")){
					str = b.getString("list").toString();
					day = b.getString("day").toString();
					
				}
				if(str.equals("true")){
					Intent intent1 = new Intent(Eventadd.this, Celenderdetail.class);
					intent1.putExtra("day", day);
					startActivity(intent1);
					finish();  
				}else{
					if (Eventdetail.EventdetailNUM == 1) {
						Intent intent = new Intent(Eventadd.this, Eventdetail.class);
						startActivity(intent);
						finish();  
					} else {
						Intent intent = new Intent(Eventadd.this, MainActivity4.class);
						startActivity(intent);
						finish();
					}	
				}
				
				
			}
		});
		// 铃音
				// doPickRingtone();
				adapter3 = new ArrayAdapter<String>(Eventadd.this,
						android.R.layout.simple_spinner_item, ring);
				// 设置下拉列表的风格
				adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

				// 将adapter 添加到spinner中
				spinnerring.setAdapter(adapter3);

				// 添加事件Spinner事件监听
				spinnerring
						.setOnItemSelectedListener(new SpinnerSelectedListener4());

				// 设置默认值
				spinnerring.setVisibility(View.VISIBLE);

		// 录音
		recoder.setOnClickListener(new View.OnClickListener() {
			boolean mStartRecording = true;

			@Override
			public void onClick(View v) {
//				Toast.makeText(getApplicationContext(), "setOnClickListener", 1).show();
				if(!isNeedAdd)
				{
					//Toast.makeText(getApplicationContext(), "isNeedAdd="+isNeedAdd, 1).show();
//					onPlay(mStartRecording);
					if (mStartRecording) {
						startPlaying();
						recoder.setBackgroundResource(R.drawable.yuyinbuttonone);
						recoder.setText("正在播放");
					} else {
						stopPlaying();
						recoder.setBackgroundResource(R.drawable.yuyingbutton);
						recoder.setText("点击播放");
					}
					mStartRecording = !mStartRecording;
					
				}
				isNeedAdd=false;

			}
		});
		recoder.setOnTouchListener(onTouchListener1);
		recoder.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				startRecording();
				recoder.setBackgroundResource(R.drawable.yuyinbuttonone);
				
				return false;
			}
		});

		// 图片选择
		image.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (tanchu_act.getVisibility() == View.GONE) {
					tanchu_act.setVisibility(1);
					AnimationSet animationSet = new AnimationSet(true);
					TranslateAnimation translateAnimation = new TranslateAnimation(
							Animation.RELATIVE_TO_SELF, 0f,
							Animation.RELATIVE_TO_SELF, 0f,
							Animation.RELATIVE_TO_SELF, 1f,
							Animation.RELATIVE_TO_SELF, 0f);

					translateAnimation.setDuration(400);
					animationSet.addAnimation(translateAnimation);
					tanchu_act.startAnimation(animationSet);
				} else {
					AnimationSet animationSet = new AnimationSet(true);
					TranslateAnimation translateAnimation = new TranslateAnimation(
							Animation.RELATIVE_TO_SELF, 0f,
							Animation.RELATIVE_TO_SELF, 0f,
							Animation.RELATIVE_TO_SELF, 0f,
							Animation.RELATIVE_TO_SELF, 1f);
					translateAnimation.setDuration(400);
					animationSet.addAnimation(translateAnimation);
					tanchu_act.startAnimation(animationSet);
					tanchu_act.setVisibility(View.GONE);
				}

			}
		});
		// 取消
		image03.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// Toast.makeText(TanchuActivity.this, "gone", 0).show();
				AnimationSet animationSet = new AnimationSet(true);
				TranslateAnimation translateAnimation = new TranslateAnimation(
						Animation.RELATIVE_TO_SELF, 0f,
						Animation.RELATIVE_TO_SELF, 0f,
						Animation.RELATIVE_TO_SELF, 0f,
						Animation.RELATIVE_TO_SELF, 1f);
				translateAnimation.setDuration(400);
				animationSet.addAnimation(translateAnimation);
				tanchu_act.startAnimation(animationSet);
				tanchu_act.setVisibility(View.GONE);

			}
		});
		// 手机相册
		image01.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent1 = new Intent(Intent.ACTION_GET_CONTENT, null);
				intent1.setDataAndType(
						MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
						IMAGE_UNSPECIFIED);
				startActivityForResult(intent1, PHOTOZOOM);
				AnimationSet animationSet = new AnimationSet(true);
				TranslateAnimation translateAnimation = new TranslateAnimation(
						Animation.RELATIVE_TO_SELF, 0f,
						Animation.RELATIVE_TO_SELF, 0f,
						Animation.RELATIVE_TO_SELF, 0f,
						Animation.RELATIVE_TO_SELF, 1f);
				translateAnimation.setDuration(400);
				animationSet.addAnimation(translateAnimation);
				tanchu_act.startAnimation(animationSet);
				tanchu_act.setVisibility(View.GONE);

			}
		});
		// 系统拍照
		image02.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

				// 启动一个Activity，希望接收到返回结果
				startActivityForResult(intent2, PHOTOHRAPH);
				AnimationSet animationSet = new AnimationSet(true);
				TranslateAnimation translateAnimation = new TranslateAnimation(
						Animation.RELATIVE_TO_SELF, 0f,
						Animation.RELATIVE_TO_SELF, 0f,
						Animation.RELATIVE_TO_SELF, 0f,
						Animation.RELATIVE_TO_SELF, 1f);
				translateAnimation.setDuration(400);
				animationSet.addAnimation(translateAnimation);
				tanchu_act.startAnimation(animationSet);
				tanchu_act.setVisibility(View.GONE);

			}
		});
		zidingyibutton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Eventadd.this, Manager.class);
				startActivity(intent);
			}
		});

		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, m);
		// 设置下拉列表的风格
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// 将adapter 添加到spinner中
		spinner.setAdapter(adapter);

		// 添加事件Spinner事件监听
		spinner.setOnItemSelectedListener(new SpinnerSelectedListener());

		// 设置默认值
		spinner.setVisibility(View.VISIBLE);

		if (NUM == 1) {
			adapter1 = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_item, leixinglist);

		} else {
			adapter1 = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_item, Manager.list);
		}

		// 设置下拉列表的风格
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		gongzuoleixing.setAdapter(adapter1);

		// 添加事件Spinner事件监听
		gongzuoleixing
				.setOnItemSelectedListener(new SpinnerSelectedListener1());

		// 设置默认值
		gongzuoleixing.setVisibility(View.VISIBLE);

		adapter2 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, youxianjilist);
		// 设置下拉列表的风格
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		youxianji.setAdapter(adapter2);

		// 添加事件Spinner事件监听
		youxianji.setOnItemSelectedListener(new SpinnerSelectedListener2());

		// 设置默认值
		youxianji.setVisibility(View.VISIBLE);

		more02.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				more02.setVisibility(View.GONE);
				more01.setVisibility(View.GONE);
				more02.setVisibility(View.GONE);
				moreinformation.setVisibility(View.VISIBLE);
			}
		});
		shouqi.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				more02.setVisibility(View.VISIBLE);
				more01.setVisibility(View.VISIBLE);
				more02.setVisibility(View.VISIBLE);
				moreinformation.setVisibility(View.GONE);
			}
		});

		eventsave.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				img1.setVisibility(View.INVISIBLE);
				zhutitext = zhuti.getText().toString();
				jishitext = jishi.getText().toString();
				Log.d("log", leixinglisttext + "---" + spinnertext + "-------"
						+ time1text);

				if (time1text.equals("")) {
					Toast.makeText(getApplicationContext(), "请选择时间", 0).show();
					img1.setVisibility(View.VISIBLE);
					return;
				}
				if (spinnertext.equals("请选择")) {
					Toast.makeText(getApplicationContext(), "请提前时间", 0).show();
					img2.setVisibility(View.VISIBLE);
					return;
				}
				ContentValues values = new ContentValues();
				values.put(Meta.TableMeta._LEIXING, leixinglisttext);
				values.put(Meta.TableMeta._TIME, time1text);
				values.put(Meta.TableMeta._MTIME, spinnertext);
				values.put(Meta.TableMeta._ZHUTI, zhutitext);
				values.put(Meta.TableMeta._JISHI, jishitext);
				values.put(Meta.TableMeta._YOUXIAN, youxianjitext);
				values.put(Meta.TableMeta._LINGYIN, spinnerringtext);
//				values.put(Meta.TableMeta._LINGYIN, spinnerringtext);
				
				
				values.put(Meta.TableMeta._LUYIN, mFileName);
				String re_str = Eventlist.getTime(time1text);
				values.put(Meta.TableMeta._TIME_UIQUE, re_str);
				
				Calendar c1 = Calendar.getInstance();
				String time1 = 
						 c1.get(Calendar.YEAR)+"/"+Home.formatTime(c1.get(Calendar.MONTH) + 1) + "/" + 
						 Home.formatTime(c1.get(Calendar.DAY_OF_MONTH))+" "+
						 Home.formatTime(c1.get(Calendar.HOUR_OF_DAY))+":"+Home.formatTime(c1.get(Calendar.MINUTE)); 
				values.put(Meta.TableMeta._DATE, time1);
				String time = 
						 c1.get(Calendar.YEAR)+"-"+ 
						 Home.formatTime(c1.get(Calendar.MONTH) +1) + "-" + 
						 Home.formatTime(c1.get(Calendar.DAY_OF_MONTH)); 
				Log.i("log", "---------------------记事图片=" + time);
				Calendar cal = Calendar.getInstance();
//				long timeuique = cal.getTimeInMillis();
				if(null != mDate&&!mDate.equals("")){
					cal.set(mDate.getYear(),mDate.getMonth(), mDate.getDate(), mDate.getHours(), mDate.getMinutes());
				}
				Log.i("log", "---------------------firsttime=" + cal.getTimeInMillis());
				
				values.put(Meta.TableMeta._TIMEUIQUE, cal.getTimeInMillis());
				
				if (photo != null) {
					values.put(Meta.TableMeta._IMAGE, filepath);
				}
//				if (leixinglisttext.equals("会议")) {
//					leixinglisttext = "1";
//				}
//				if (leixinglisttext.equals("记事")) {
//					leixinglisttext = "2";
//				}
//				if (leixinglisttext.equals("生日")) {
//					leixinglisttext = "3";
//				}
//				if (leixinglisttext.equals("聚会")) {
//					leixinglisttext = "4";
//				}

				ContentValues values1 = new ContentValues();
				if(null!=CalendarView.selectedDay&&!CalendarView.selectedDay.equals("")){
					values1.put(com.notbook.photodb.Meta.TableMeta._DATE,
							CalendarView.selectedDay);
					values.put(Meta.TableMeta._SELECTTIME,CalendarView.selectedDay);
				}else{
					values1.put(com.notbook.photodb.Meta.TableMeta._DATE,
							time );
					values.put(Meta.TableMeta._SELECTTIME,time);
				}
				
				Log.i("log", "日期：" + CalendarView.selectedDay);
				long tid = 0;
				if (!Eventdetail.id.equals("")) {
					tid = resolver.update(Meta.CONTENT_URI, values, "_id=?",
							new String[] { Eventdetail.id });
					if (tid != 0) {
						Log.i("log", "更新成功！");
					}
					Eventdetail.id = "";
				} else {
					Uri uri = resolver.insert(Meta.CONTENT_URI, values);
					tid = ContentUris.parseId(uri);
					Log.i("log", "=====================uri========" + uri);
					if (uri != null) {
						
						Log.i("log", "添加成功！");
					}
					Uri uri1 = resolver.insert(
							com.notbook.photodb.Meta.CONTENT_URI, values1);
					Log.i("log", "=====================uri1========" + uri1);
					if (uri1 != null) {
						Log.i("log", "图片添加成功！");
					}
	
				}
				
				int nummiao = 0;
				if (spinnertext.equals("正点提醒")) {
					nummiao = 0;
				}
				if (spinnertext.equals("提前5分钟")) {
					nummiao = 5 * 1000 * 60;
				}
				if (spinnertext.equals("提前10分钟")) {
					nummiao = 10 * 1000 * 60;
				}
				if (spinnertext.equals("提前一小时")) {
					nummiao = 60 * 1000 * 60;
				}
				if (spinnertext.equals("提前一天")) {
					nummiao = 1440 * 1000 * 60;
				}
				Calendar c = Calendar.getInstance();
				if(null != mDate&&!mDate.equals("")){
					c.set(mDate.getYear(),mDate.getMonth(), mDate.getDate(), mDate.getHours(), mDate.getMinutes());
				}

				AlarmManager manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
				
				Intent intent = new Intent();
				intent.setAction(MemorandAlarmService.class.getName());
			
				
				if (Eventdetail.EventdetailNUM == 1) {
					Intent intent1 = new Intent(Eventadd.this,Eventdetail.class);
					intent.putExtra("id", Long.valueOf(tid));
					startActivity(intent1);
					Toast.makeText(getApplicationContext(), "修改成功", 0).show();
				} else {
					Bundle b = getIntent().getExtras();
					String str = "";
					String day = "";
					if(null != b&& !b.equals("")){
						str = b.getString("list").toString();
						day = b.getString("day").toString();
					}
					
					if(str.equals("true")){  
						Intent intent1 = new Intent(Eventadd.this, Celenderdetail.class);
						intent1.putExtra("id", tid);
						intent1.putExtra("day", day);
						startActivity(intent1);
					}else{
						Intent intent1 = new Intent(Eventadd.this, MainActivity4.class);
						intent.putExtra("id", tid);
						startActivity(intent1);
					}
					
					Toast.makeText(getApplicationContext(), "保存成功", 0).show();
				}	
				PendingIntent operation = PendingIntent.getService(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
				manager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis()-nummiao, operation);
				Log.d("log", "-------------c.getTimeInMillis()==" + c.getTimeInMillis());
				
				Eventadd.this.finish();
			}
		});
		time1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				View view = LayoutInflater.from(getApplicationContext())
						.inflate(R.layout.memorandum_settime, null);
				final DatePicker date = (DatePicker) view
						.findViewById(R.id.dateSet);
				final TimePicker time = (TimePicker) view
						.findViewById(R.id.timeSet);
				Bundle b = getIntent().getExtras();
				String str = "";
				String day = "";
				String year_1 = "";
				String month_1 = "";
				String day_1 = "";
				if(null != b&& !b.equals("")){
					str = b.getString("list").toString();
					day = b.getString("day").toString();
					if(str.equals("true")){
						String str1[] = day.split("\\-");
						
						for(int i=0;i<str1.length;i++){
							year_1 = str1[0];
							month_1 = str1[1];
							day_1 = str1[2];
						}
						date.updateDate(Integer.valueOf(year_1), Integer.valueOf(month_1)-1, Integer.valueOf(day_1));
						time.setCurrentHour(0);
						time.setCurrentMinute(0);
						  
						
					}
				}
				
				new AlertDialog.Builder(Eventadd.this)
						.setView(view)
						.setTitle("设置日期")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {

										mDate = new Date(date.getYear(), date.getMonth(), date.getDayOfMonth(),time.getCurrentHour(), time.getCurrentMinute());
										
										
										StringBuilder temp = new StringBuilder();
										temp.append(date.getYear())
												.append("/")
												.append(date.getMonth() + 1)
												.append("/")
												.append(date.getDayOfMonth())
												.append(" ")
												.append(time.getCurrentHour() > 9 ? time
														.getCurrentHour() : "0"
														+ time.getCurrentHour())
												.append(":")
												.append(time.getCurrentMinute() > 9 ? time
														.getCurrentMinute()
														: "0"
																+ time.getCurrentMinute())
												.append("");
										hour = time.getCurrentHour();
										minute = time.getCurrentMinute();
										time1.setText(temp.toString());
										time1text = temp.toString();
										calendar = Calendar.getInstance();
										calendar.setTimeInMillis(System
												.currentTimeMillis());
										calendar.set(Calendar.HOUR_OF_DAY,
												time.getCurrentHour());
										calendar.set(Calendar.MINUTE,
												time.getCurrentMinute());
										calendar.set(Calendar.SECOND, 0);
										calendar.set(Calendar.MILLISECOND, 0);

									}
								}).create().show();

			}
		});
		if (Eventdetail.EventdetailNUM == 1) {
			jishititle.setText("编辑记事");
			Cursor c = null;
			if (null != Eventdetail.Eventdetailid
					&& !Eventdetail.Eventdetailid.equals("")) {
				c = getContentResolver().query(Meta.CONTENT_URI, null,
						"_id = ?", new String[] { Eventdetail.Eventdetailid },
						null);
				Eventdetail.Eventdetailid = "";
			}

			if (c != null && c.moveToFirst()) {
				c.moveToFirst();

				time1.setText(c.getString(c
						.getColumnIndex(Meta.TableMeta._TIME)));// 时间
				zhuti.setText(c.getString(c
						.getColumnIndex(Meta.TableMeta._ZHUTI)));
//				String zhuti_text = c.getString(c.getColumnIndex(Meta.TableMeta._ZHUTI));
//				if(null!=zhuti_text&&!zhuti_text.equals("")&&zhuti_text.length()>0){
//					zhuti.setSelection(c.getString(c
//							.getColumnIndex(Meta.TableMeta._ZHUTI)).length());
//				}
				
				jishi.setText(c.getString(c
						.getColumnIndex(Meta.TableMeta._JISHI)));
//				String jishi_text = c.getString(c.getColumnIndex(Meta.TableMeta._JISHI));
//				if(null!=jishi_text&&!jishi_text.equals("")&&jishi_text.length()>0){
//					jishi.setSelection(c.getString(c
//							.getColumnIndex(Meta.TableMeta._JISHI)).length());
//				}
				
				time1text = c.getString(c.getColumnIndex(Meta.TableMeta._TIME));
				leixinglisttext = c.getString(c
						.getColumnIndex(Meta.TableMeta._LEIXING));// 类型
				if (NUM == 1) {
					for (int i = 0; i < leixinglist.length; i++) {
						if (leixinglist[i].equals(leixinglisttext)) {
							gongzuoleixing.setSelection(i);
						}
					}

				} else {
					for (int i = 0; i < Manager.list.size(); i++) {
						if (Manager.list.get(i).toString()
								.equals(leixinglisttext)) {
							gongzuoleixing.setSelection(i);
						}
					}
				}

				spinnertext = c.getString(c
						.getColumnIndex(Meta.TableMeta._MTIME));// 提前时间
				for (int i = 0; i < m.length; i++) {
					if (m[i].equals(spinnertext)) {
						spinner.setSelection(i);
					}
				}

				jishitext = c
						.getString(c.getColumnIndex(Meta.TableMeta._JISHI));
				zhutitext = c
						.getString(c.getColumnIndex(Meta.TableMeta._ZHUTI));
				byte[] picData = c.getBlob(c
						.getColumnIndex(Meta.TableMeta._IMAGE));// 图片
				if (picData != null) {
					try {
						ShowImg(c.getString(c
								.getColumnIndex(Meta.TableMeta._IMAGE)), image);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				youxianjitext = c.getString(c
						.getColumnIndex(Meta.TableMeta._YOUXIAN));// 优先
				for (int i = 0; i < youxianjilist.length; i++) {
					if (youxianjilist[i].equals(youxianjitext)) {
						youxianji.setSelection(i);
					}
				}
			}
			Eventdetail.EventdetailNUM = 0;
		} else {
			jishititle.setText("添加记事");
		}
		init();
	}

	// 使用数组形式操作
	class SpinnerSelectedListener implements OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			spinnertext = m[arg2];
		}

		public void onNothingSelected(AdapterView<?> arg0) {
		}
	}

	// 使用数组形式操作
	class SpinnerSelectedListener4 implements OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			spinnerringtext = ring[arg2];
			/*if (spinnerringtext.equals("噔噔音")) {
				startPlaying("dengdengying.mp3");
			} else if (spinnerringtext.equals("紧急音")) {

			} else if (spinnerringtext.equals("提醒音")) {

			} else if (spinnerringtext.equals("信息提示音")) {

			} else if (spinnerringtext.equals("一寸光阴一寸金")) {

			}*/
		}

		public void onNothingSelected(AdapterView<?> arg0) {
		}
	}

	// 使用数组形式操作
	class SpinnerSelectedListener1 implements OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			if (NUM == 1) {
				leixinglisttext = leixinglist[arg2];
			} else {
				leixinglisttext = (String) Manager.list.toArray()[arg2];
			}

		}

		public void onNothingSelected(AdapterView<?> arg0) {
		}
	}

	// 使用数组形式操作
	class SpinnerSelectedListener2 implements OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			youxianjitext = youxianjilist[arg2];
		}

		public void onNothingSelected(AdapterView<?> arg0) {
		}
	}

	public void init() {
		iamgebutton1 = (Button) findViewById(R.id.iamgebutton1);
		iamgebutton1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Eventadd.this, MainActivity0.class);
				startActivity(intent);
			}
		});
		iamgebutton2 = (Button) findViewById(R.id.iamgebutton2);
		iamgebutton2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Eventadd.this, MainActivity2.class);
				startActivity(intent);
			}
		});
		iamgebutton3 = (Button) findViewById(R.id.iamgebutton3);
		iamgebutton3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Eventadd.this, MainActivity3.class);
				startActivity(intent);
			}
		});
		iamgebutton4 = (Button) findViewById(R.id.iamgebutton4);
		iamgebutton4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Eventadd.this, MainActivity4.class);
				startActivity(intent);
			}
		});
		iamgebutton5 = (Button) findViewById(R.id.iamgebutton5);
		iamgebutton5.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Eventadd.this, MainActivity1.class);
				startActivity(intent);
			}
		});
	}

	public Ringtone getDefaultRingtone(int type) {

		return RingtoneManager.getRingtone(mContext,
				RingtoneManager.getActualDefaultRingtoneUri(mContext, type));

	}

	public Uri getDefaultRingtoneUri(int type) {

		return RingtoneManager.getActualDefaultRingtoneUri(mContext, type);

	}

	public List<Ringtone> getRingtoneList(int type) {

		List<Ringtone> resArr = new ArrayList<Ringtone>();

		RingtoneManager manager = new RingtoneManager(mContext);

		manager.setType(type);

		Cursor cursor = manager.getCursor();

		int count = cursor.getCount();

		for (int i = 0; i < count; i++) {

			resArr.add(manager.getRingtone(i));

		}

		return resArr;

	}

	public Ringtone getRingtone(int type, int pos) {

		RingtoneManager manager = new RingtoneManager(mContext);

		manager.setType(type);

		return manager.getRingtone(pos);

	}

	public List<String> getRingtoneTitleList(int type) {

		List<String> resArr = new ArrayList<String>();

		RingtoneManager manager = new RingtoneManager(mContext);

		manager.setType(type);

		Cursor cursor = manager.getCursor();

		if (cursor.moveToFirst()) {

			do {

				resArr.add(cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX));

			} while (cursor.moveToNext());

		}

		return resArr;

	}

	public String getRingtoneUriPath(int type, int pos, String def) {

		RingtoneManager manager = new RingtoneManager(mContext);

		manager.setType(type);

		Uri uri = manager.getRingtoneUri(pos);

		return uri == null ? def : uri.toString();

	}

	public Ringtone getRingtoneByUriPath(int type, String uriPath) {

		RingtoneManager manager = new RingtoneManager(mContext);

		manager.setType(type);

		Uri uri = Uri.parse(uriPath);

		return manager.getRingtone(mContext, uri);

	}

	private void doPickRingtone() {
		Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
		// Allow user to pick 'Default'
		intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, true);
		// Show only ringtones
		intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE,
				RingtoneManager.TYPE_RINGTONE);
		// Don't show 'Silent'
		intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, false);

		Uri ringtoneUri;
		if (mRingtoneUri != null) {
			ringtoneUri = Uri.parse(mRingtoneUri);
		} else {
			// Otherwise pick default ringtone Uri so that something is
			// selected.
			ringtoneUri = RingtoneManager
					.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
		}

		// Put checkmark next to the current ringtone for this contact
		intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI,
				ringtoneUri);

		// Launch!
		// startActivityForResult(intent, REQUEST_CODE_PICK_RINGTONE);
		startActivityForResult(intent, REQUEST_CODE_PICK_RINGTONE);
	}

	private void handleRingtonePicked(Uri pickedUri) {
		if (pickedUri == null || RingtoneManager.isDefault(pickedUri)) {
			mRingtoneUri = null;
		} else {
			mRingtoneUri = pickedUri.toString();
		}
		// get ringtone name and you can save mRingtoneUri for database.
		if (mRingtoneUri != null) {
			lingying.setText(RingtoneManager.getRingtone(this, pickedUri)
					.getTitle(this));
		} else {
			lingying.setText("默认铃音");
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == NONE)
			return;

		if (data == null)
			return;
//		if (resultCode != Activity.RESULT_OK) {
//			return;
//		}
//
//		switch (requestCode) {
//		case REQUEST_CODE_PICK_RINGTONE: {
//			Uri pickedUri = data
//					.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
//			handleRingtonePicked(pickedUri);
//			break;
//		}
//		}
		// 拍照
		if (requestCode == PHOTOHRAPH) {
			Log.d("log", "==========================PHOTOHRAPH=" + PHOTOHRAPH);
			/*
			 * String
			 * ph=Environment.getExternalStorageDirectory()+"/yashily/camera.jpg"
			 * ; File f=new File(ph);
			 * 
			 * //图片处理，压缩剪切 startPhotoZoom(Uri.fromFile(f));
			 */
			Bundle bundle = data.getExtras();

			Bitmap bitmap = (Bitmap) bundle.get("data");
			image.setImageBitmap(bitmap);
			saveMyBitmap2(bitmap, "up_pic.png");
		}
		// 读取相册缩放图片
		if (requestCode == PHOTOZOOM) {
			ContentResolver resolver = getContentResolver();
			// 获取图片

			Uri originalUri = data.getData(); // 获得图片的uri
			// 这里开始的第二部分，获取图片的路径：
			String[] proj = { MediaStore.Images.Media.DATA };

			Cursor cursor = managedQuery(originalUri, proj, null, null, null);
			// 按我个人理解 这个是获得用户选择的图片的索引值
			int column_index = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			// 最后根据索引值获取图片路径
			filepath = cursor.getString(column_index);
			Log.d("log", "-------------相册处理前filepath==" + filepath);
			startPhotoZoom(data.getData());
		}
		// 处理结果
		if (requestCode == PHOTORESOULT) {
			Bundle extras = data.getExtras();
			if (extras != null) {
				photo = extras.getParcelable("data");
				stream = new ByteArrayOutputStream();
				photo.compress(Bitmap.CompressFormat.PNG, 75, stream);// (0 -

				saveMyBitmap2(photo, "up_pic.jpg"); // 100)压缩文件
				image.setImageBitmap(photo);

				AnimationSet animationSet = new AnimationSet(true);
				TranslateAnimation translateAnimation = new TranslateAnimation(
						Animation.RELATIVE_TO_SELF, 0f,
						Animation.RELATIVE_TO_SELF, 0f,
						Animation.RELATIVE_TO_SELF, 0f,
						Animation.RELATIVE_TO_SELF, 1f);
				translateAnimation.setDuration(400);
				animationSet.addAnimation(translateAnimation);
				tanchu_act.startAnimation(animationSet);
				tanchu_act.setVisibility(View.GONE);
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	// 获取照片
	public File saveMyBitmap2(Bitmap bm, String filename) {
		File defaultDir = Environment.getExternalStorageDirectory();
		String path = defaultDir.getAbsolutePath() + File.separator
				+ "notebook" + File.separator;
		// 创建文件夹存放图片
		mRecordDir = new File(path);
		if (!mRecordDir.exists()) {
			mRecordDir.mkdirs();
		}
		File file = null;

		FileOutputStream fOut = null;
		file = new File(mRecordDir, filename);
		if (file.exists()) {
			file.delete();
		}
		try {

			file.createNewFile();
			fOut = new FileOutputStream(file);
			bm.compress(Bitmap.CompressFormat.PNG, 100, fOut);
			fOut.flush();
			fOut.close();

			filepath = file.getAbsolutePath();
			return file;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	public void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 100);
		intent.putExtra("outputY", 100);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, PHOTORESOULT);
	}

	private String format(int x) {
		String s = "" + x;
		if (s.length() == 1)
			s = "0" + s;
		return s;
	}
	Timer timer = new Timer();
	double i = 0;
	private void startRecording() {
		mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
		mFileName += "/event.aac";
		mRecorder = new MediaRecorder();
		mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		mRecorder.setOutputFile(mFileName);
		mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		try {
			mRecorder.prepare();
		} catch (IOException e) {
			Log.e(LOG_TAG, "prepare() failed");
		}
		mRecorder.start();
		
		
		recoderimage.setVisibility(View.VISIBLE);
		TimerTask task = new TimerTask() {
			@Override
			public void run() {   
//				i  = Eventadd.this.getAmplitude();
//				Log.i("log", "---------------------iiiiiiiiiiiiii=" + i);
				
//				new Thread(new Runnable() {
//					
//					@Override
//					public void run() {
//						// TODO Auto-generated method stub
//						updateDisplay(i);
//					}
//				}).start();
				handler.sendMessage(new Message());
				
			}
		};
		
		timer.schedule(task, 500 * 1,500); 
		
//		int i = mRecorder.getMaxAmplitude();
//		if((int)i == 1){
//			recoderimage.setBackgroundResource(R.drawable.recoder002);
//		}
//		if((int)i == 2){
//			recoderimage.setBackgroundResource(R.drawable.recoder003);
//		}
//		if((int)i == 3){
//			recoderimage.setBackgroundResource(R.drawable.recoder004);
//		}
//		if((int)i == 4){
//			recoderimage.setBackgroundResource(R.drawable.recoder005);
//		}
//		if((int)i == 5){
//			recoderimage.setBackgroundResource(R.drawable.recoder006);
//		}
//		if((int)i == 6){
//			recoderimage.setBackgroundResource(R.drawable.recoder007);
//		}
//		if((int)i == 7){
//			recoderimage.setBackgroundResource(R.drawable.recoder008);
//		}
	}

	private void stopRecording() {
		recoderimage.setVisibility(View.INVISIBLE);
		mRecorder.stop();
		mRecorder.release();
		mRecorder = null;
	}

	@Override
	public void onPause() {
		super.onPause();
		if (mRecorder != null) {
			recoderimage.setVisibility(View.INVISIBLE);
			mRecorder.release();
			mRecorder = null;
		}
		if (mPlayer != null) {
			recoderimage.setVisibility(View.INVISIBLE);
			mPlayer.release();
			mPlayer = null;
		}
	}

	private void onRecord(boolean start) {
		if (start) {
			startRecording();
		} else {
			stopRecording();
		}
	}

	public static void ShowImg(String uri, ImageView iv) throws IOException {
		FileInputStream fs = new FileInputStream(uri);
		BufferedInputStream bs = new BufferedInputStream(fs);
		Bitmap btp = BitmapFactory.decodeStream(bs);
		iv.setImageBitmap(btp);
		bs.close();
		fs.close();
		btp = null;
	}

	public String getFromAssets(String fileName) {
		String Result = "";
		try {
			InputStreamReader inputReader = new InputStreamReader(
					getResources().getAssets().open(fileName));
			BufferedReader bufReader = new BufferedReader(inputReader);
			String line = "";

			while ((line = bufReader.readLine()) != null)
				Result += line;
			return Result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Result;
	}

	private void onPlay(boolean start) {
		if (start) {
			startPlaying();
		} else {
			stopPlaying();
		}
	}
	private void onPlayStop() {	
			stopPlaying();
	}
	private void startPlaying() {
		mPlayer = new MediaPlayer();
		try {
			mPlayer.setDataSource(mFileName);
			mPlayer.prepare();
			mPlayer.start();
		} catch (IOException e) {
		}
	}

	private void stopPlaying() {
		mPlayer.release();
		mPlayer = null;
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			Eventadd.this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		
		switch (keyCode) {
		case KeyEvent.KEYCODE_VOLUME_UP:
			recoderimage.setVisibility(View.INVISIBLE);
		}
		
		
		return super.onKeyUp(keyCode, event);
	}

	private View.OnTouchListener onTouchListener1 = new View.OnTouchListener() {
		 @Override
		public boolean onTouch(View v, MotionEvent event) {
			if(event.getAction() == KeyEvent.ACTION_UP) {
				if(mRecorder!=null)
				{
//					timer.cancel();
					isNeedAdd = true;
					stopRecording();	
					recoder.setText("点击播放");
				}
				recoderimage.setVisibility(View.GONE);
				recoder.setBackgroundResource(R.drawable.yuyingbutton);
			}
		
		return false;
		}
	};
	
	public double getAmplitude(){
		if(mRecorder != null){
			return (mRecorder.getMaxAmplitude()/2700.0);
		}else{
			return 0;
		}
	}
	private void updateDisplay(double signalEMA) {
		
		switch ((int) signalEMA) {
		case 0:
		case 1:
			recoderimage.setBackgroundResource(R.drawable.recoder002);
			break;
		case 2:
		case 3:
			recoderimage.setBackgroundResource(R.drawable.recoder003);
			
			break;
		case 4:
		case 5:
			recoderimage.setBackgroundResource(R.drawable.recoder004);
			break;
		case 6:
		case 7:
			recoderimage.setBackgroundResource(R.drawable.recoder005);
			break;
		case 8:
		case 9:
			recoderimage.setBackgroundResource(R.drawable.recoder006);
			break;
		case 10:
		case 11:
			recoderimage.setBackgroundResource(R.drawable.recoder007);
			break;
		default:
			recoderimage.setBackgroundResource(R.drawable.recoder007);
			break;
		}
	}
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			i  = Eventadd.this.getAmplitude();
//			Log.i("log", "---------------------iiiiiiiiiiiiii=" + i);
			updateDisplay(i);
		}

	};
}
