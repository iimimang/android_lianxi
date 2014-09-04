package com.notbook.ui;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.GeoPoint;
import com.baidu.mapapi.MKAddrInfo;
import com.baidu.mapapi.MKBusLineResult;
import com.baidu.mapapi.MKDrivingRouteResult;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.MKGeocoderAddressComponent;
import com.baidu.mapapi.MKPoiResult;
import com.baidu.mapapi.MKSearch;
import com.baidu.mapapi.MKSearchListener;
import com.baidu.mapapi.MKSuggestionResult;
import com.baidu.mapapi.MKTransitRouteResult;
import com.baidu.mapapi.MKWalkingRouteResult;
import com.example.notbook.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.netbook.snow.SnowView;
import com.notbook.app.AppManager;
import com.notbook.app.DayStyle;
import com.notbook.app.Info;
import com.notbook.calendar.CalendarUtil22;
import com.notbook.things.Meta;
import com.notbook.ui.More.TextAsyncTask;
import com.notebook.update.UpdateService;

public class Home extends BaseActivity {
	public TextView tv1, tv2;
	public BMapManager mapManager;
	private LinearLayout eventlist;
	private RelativeLayout weatherdetail;
	private TextView shijianlist, shijiandetail01, shijiandetail02,
			shijiandetail03, shijiandetail04, shijiandetail05;
	private int jindu = 0, weidu = 0;
	private TextView todaywendu, wendu, wendu1, fengxiang, shidu, date, date1;
	private TextView wendu2,wendu3,wendu4,wendu5;
	private ProgressDialog dia; 
	static ContentResolver resolver;
	private ListView findshare_1;
	SnowView snow = null;
	private int year, month, day;
	String verSionName1 = "1.0";
	public static String appName = "updateAppNoteBook";
	private static int gengxinnum = 1;
	
	public String weather="";
	private String weather1="",weather2="",weather3="",weather4="",weather5="";
	private ImageButton weather_1,weather_2,weather_3,weather_4,weather_5;
	private String date_1="",date_2="",date_3="",date_4="";
	private TextView date111,date2,date3,date4;
	private String wendu_1="",wendu_2="",wendu_3="",wendu_4="";
	private String week1="",week2="",week3="",week4="",city="";
	public int rainnum = 0,snownum = 0;
	ContentValues values = new ContentValues();
	private int homenum = 1;
	private Cursor c = null;
	private int eventlist_num = 0;
	private TextView myaboutmessage ; //与我相关的传语信息
	private TextView name ; 		  //发送人
	private TextView time ;           //时间
	private ImageButton youjiantou ;
	private List<com.notbook.entity.Thing> things = new ArrayList<com.notbook.entity.Thing>();
	private int home_flag = 0;
	private MyHandler myHandler;
	private Boolean flag1 = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		LinearLayout one = (LinearLayout)findViewById(R.id.one);
		one.setBackgroundResource(background_photobg[num - 1]);
		
		
		eventlist = (LinearLayout) findViewById(R.id.eventlist);
		weatherdetail = (RelativeLayout) findViewById(R.id.weatherdetail1);
		shijianlist = (TextView) findViewById(R.id.shijianlist);

		tv2 = (TextView) findViewById(R.id.difang);
		todaywendu = (TextView) findViewById(R.id.todaywendu);
		myaboutmessage = (TextView) findViewById(R.id.myaboutmessage);
		name = (TextView) findViewById(R.id.name);
		time = (TextView) findViewById(R.id.time);
		youjiantou = (ImageButton) findViewById(R.id.youjiantou);
		wendu = (TextView) findViewById(R.id.wendu);
		wendu1 = (TextView) findViewById(R.id.wendu1);
		wendu2 = (TextView) findViewById(R.id.wendu2);
		wendu3 = (TextView) findViewById(R.id.wendu3);
		
		wendu4 = (TextView) findViewById(R.id.wendu4);
		wendu5 = (TextView) findViewById(R.id.wendu5);
		fengxiang = (TextView) findViewById(R.id.fengxiang);
		shidu = (TextView) findViewById(R.id.shidu);
		date = (TextView) findViewById(R.id.date);
		date1 = (TextView) findViewById(R.id.date1);
		date111 = (TextView) findViewById(R.id.date111);
		date2 = (TextView) findViewById(R.id.date2);
		date3 = (TextView) findViewById(R.id.date3);
		date4 = (TextView) findViewById(R.id.date4);
		weather_1 = (ImageButton) findViewById(R.id.weather_1);
		weather_2 = (ImageButton) findViewById(R.id.weather_2);
		weather_3 = (ImageButton) findViewById(R.id.weather_3);
		weather_4 = (ImageButton) findViewById(R.id.weather_4);
		weather_5 = (ImageButton) findViewById(R.id.weather_5);
		resolver = getContentResolver();
		findshare_1 = (ListView) findViewById(R.id.findshare_1);
		if(Login.NUM!=0){													

			new TextAsyncTask3().execute(com.notbook.app.Info.uri+"/setMychuanyuAction.php?uphone="+Login.uphone+"&type=1");
		}
		if(flag1 = false){
			String url1 ="http://www.weather.com.cn/data/cityinfo/101010100.html";
			new TextAsyncTask().execute(url1);
			flag1 = true ;  
		}
		Calendar cal = Calendar.getInstance();
		long timeuique = cal.getTimeInMillis();
		c = resolver.query(Meta.CONTENT_URI, null, "_time_uique>=?", new String[] { String.valueOf(timeuique) },
				"_id desc limit 5");

		SimpleCursorAdapter sca = new SimpleCursorAdapter(this,
				R.layout.share_list2, c, new String[] {
						Meta.TableMeta._LEIXING, Meta.TableMeta._TIME, Meta.TableMeta._JISHI },
				new int[] { R.id.leixing, R.id.time, R.id.content });
		
		findshare_1.setAdapter(sca);
		
		findshare_1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long id) {

				Intent it = new Intent(Home.this, Eventdetail.class);
				it.putExtra("id", id + "");
				Log.i("log", "-----------id3333333333333333333333："+id);
				it.putExtra("list", "back");
				it.putExtra("day", "");
				startActivity(it);
			}
		});
		if(gengxinnum == 1){
			String url = Info.uri+"/versioncontrolAction.php?iname=android"
					+ verSionName1;
			System.out.println("log"
					+ "-------------url" + url);
			new TextAsyncTask5().execute(url);
			gengxinnum = 0;
		}
		if(eventlist_num == 0){
			myaboutmessage.setText("快来发起互点给您的好友吧");
			eventlist.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent it = new Intent(Home.this, MainActivity4.class);
					startActivity(it);
				}
			});
		}
		weatherdetail.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent it = new Intent(Home.this, Weather.class);
				startActivity(it);
			}
		});
		shijianlist.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent it = new Intent(Home.this, MainActivity4.class);
				startActivity(it);
			}
		});
		mapManager = new BMapManager(this);
		mapManager.init("EDB67AD764D300895C95ABA02A4DDC58D5485CCD",
				new MyMKGeneralListener());
		// 设置通知间隔:iMaxSecond - 最大通知间隔,单位:秒;iMinSecond - 最小通知间隔,单位:秒
		mapManager.getLocationManager().setNotifyInternal(10, 5);

		mapManager.getLocationManager().requestLocationUpdates(
				new MyLocationListener());
		mapManager.start();

		
		
		// 晴天
		Animation anim = AnimationUtils.loadAnimation(this,
				R.anim.round_loading);
		findViewById(R.id.loadingBtn).startAnimation(anim);

		// 多云
		Animation cloud_anim = AnimationUtils.loadAnimation(this,
				R.anim.push_up_in);
		Animation cloud_out_anim = AnimationUtils.loadAnimation(this,
				R.anim.push_out_in);
		findViewById(R.id.cloud_out).startAnimation(cloud_anim);
		findViewById(R.id.cloud).startAnimation(cloud_out_anim);
		

		// 阴天
		Animation cloudanim = AnimationUtils.loadAnimation(this,
				R.anim.push_outin);
		findViewById(R.id.bigcloud).startAnimation(cloudanim);
		
		Log.i("log", "-------------rainnum=" + rainnum);
		Log.i("log", "-------------snownum=" + snownum);
		Boolean flag = false;
		if(flag = true){
			// 获得雪花视图,并加载雪花图片到内存
			snow = (SnowView) findViewById(R.id.snow);
				
		}
		
	}

	class MyLocationListener implements com.baidu.mapapi.LocationListener {

		@Override
		public void onLocationChanged(Location arg0) {
			if (arg0 == null) {
				return;
			}
			jindu = (int) (arg0.getLatitude() * 1000000);
			weidu = (int) (arg0.getLongitude() * 1000000);
			// tv1.setText("经度：" + jindu + ",纬度：" + weidu);
			Log.i("log", "经度：" + jindu + ",纬度：" + weidu);

			MKSearch search = new MKSearch();
			search.init(mapManager, new MyMKSearchListener());
			search.reverseGeocode(new GeoPoint(jindu, weidu));
		}

	}

	class MyMKSearchListener implements MKSearchListener {

		@Override
		public void onGetAddrResult(MKAddrInfo arg0, int arg1) {
			if (arg0 == null) {
				tv2.setText("没有获取想要的位置");
				Log.i("log", "没有获取想要的位置");

			} else {
				GeoPoint point = arg0.geoPt;
				// tv2.setText("地址：" + arg0.strAddr + ",坐标：" +
				// point.getLatitudeE6() + "," + point.getLongitudeE6());
				Log.i("log",
						"地址：" + arg0.hashCode() + ",坐标："
								+ point.getLatitudeE6() + ","
								+ point.getLongitudeE6());
				MKGeocoderAddressComponent kk = arg0.addressComponents;
				String city = kk.city;
				Log.i("log", city);
				city.replaceAll("", "市");
//				tv2.setText(city);
				// mapManager.stop();

				mapManager.destroy();
				if(homenum ==1){
					String url = "http://m.weather.com.cn/data/101010100.html";
					Log.i("log", "-------------url" + url);
					new TextAsyncTask().execute(url);

					String url1 = "http://www.weather.com.cn/data/cityinfo/101010100.html";
					new TextAsyncTask1().execute(url1);
					String url2 = "http://www.weather.com.cn/data/sk/101010100.html";
					new TextAsyncTask2().execute(url2);
					homenum = 0;
				}else{
					Cursor c = resolver.query(com.notbook.weatherdb.Meta.CONTENT_URI, null, null, null, null);
					if(c!=null&&c.moveToFirst()){
						for(c.moveToFirst();!c.isAfterLast();c.moveToNext())  {  
							Weather1(c.getString(c.getColumnIndex(com.notbook.weatherdb.Meta.TableMeta._WEATHER1)));  
							Weather2(c.getString(c.getColumnIndex(com.notbook.weatherdb.Meta.TableMeta._WEATHER2)));
							Weather3(c.getString(c.getColumnIndex(com.notbook.weatherdb.Meta.TableMeta._WEATHER3)));
							Weather4(c.getString(c.getColumnIndex(com.notbook.weatherdb.Meta.TableMeta._WEATHER4)));
							Weather5(c.getString(c.getColumnIndex(com.notbook.weatherdb.Meta.TableMeta._WEATHER5)));
							Week(c.getString(c.getColumnIndex(com.notbook.weatherdb.Meta.TableMeta._WEEK1)));
							wendu.setText(c.getString(c.getColumnIndex(com.notbook.weatherdb.Meta.TableMeta._TEMP1)));
							wendu1.setText(c.getString(c.getColumnIndex(com.notbook.weatherdb.Meta.TableMeta._DATE3)));
							wendu2.setText(c.getString(c.getColumnIndex(com.notbook.weatherdb.Meta.TableMeta._TEMP2)));
							wendu3.setText(c.getString(c.getColumnIndex(com.notbook.weatherdb.Meta.TableMeta._TEMP3)));
							wendu4.setText(c.getString(c.getColumnIndex(com.notbook.weatherdb.Meta.TableMeta._TEMP4)));
							wendu5.setText(c.getString(c.getColumnIndex(com.notbook.weatherdb.Meta.TableMeta._TEMP5)));
							tv2.setText(c.getString(c.getColumnIndex(com.notbook.weatherdb.Meta.TableMeta._CITY)));
							date.setText(c.getString(c.getColumnIndex(com.notbook.weatherdb.Meta.TableMeta._DATE1)));
							date1.setText(c.getString(c.getColumnIndex(com.notbook.weatherdb.Meta.TableMeta._DATE2)));
							todaywendu.setText(c.getString(c.getColumnIndex(com.notbook.weatherdb.Meta.TableMeta._TODAYWENDU)));
							fengxiang.setText(c.getString(c.getColumnIndex(com.notbook.weatherdb.Meta.TableMeta._DATE4)));
							shidu.setText(c.getString(c.getColumnIndex(com.notbook.weatherdb.Meta.TableMeta._SHIDU)));
							
						} 
					}
					
				}
				
				
				
				
			}
		}

		@Override
		public void onGetBusDetailResult(MKBusLineResult arg0, int arg1) {

		}

		@Override
		public void onGetDrivingRouteResult(MKDrivingRouteResult arg0, int arg1) {

		}

		@Override
		public void onGetPoiResult(MKPoiResult arg0, int arg1, int arg2) {

		}

		@Override
		public void onGetSuggestionResult(MKSuggestionResult arg0, int arg1) {

		}

		@Override
		public void onGetTransitRouteResult(MKTransitRouteResult arg0, int arg1) {

		}

		@Override
		public void onGetWalkingRouteResult(MKWalkingRouteResult arg0, int arg1) {

		}

	}

	// 常用事件监听，用来处理通常的网络错误，授权验证错误等
	class MyMKGeneralListener implements MKGeneralListener {

		@Override
		public void onGetNetworkState(int arg0) {

		}

		@Override
		public void onGetPermissionState(int arg0) {

		}

	}

	class TextAsyncTask extends AsyncTask<String, Integer, String> {
		@Override
		protected void onPreExecute() {
			dia = new ProgressDialog(Home.this);
			dia.setMessage("正在加载...");
			dia.show();
			dia.setCanceledOnTouchOutside(false);
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			return getResultByPost(params[0]);
		}

		@Override
		protected void onPostExecute(String result) {
			if (result == null) {
				Toast.makeText(Home.this, "网络加载失败..", 0).show();
				dia.dismiss();
			} else {
				dia.dismiss();
				Log.i("log", "-------------result111111111=" + result);
				JSONObject object, object1;
				try {

					object = new JSONObject(result);
					String weatherinfo = object.getString("weatherinfo");
					Log.i("log", "-------------weatherinfo=11111111111" + weatherinfo);
					object1 = new JSONObject(weatherinfo);
					String mtodaywendu = object1.getString("temp1");
					String mdate = object1.getString("date_y");
					mtodaywendu.replaceAll("-", "~");
					weather1 = object1.getString("weather1");
					weather2 = object1.getString("weather2");
					weather3 = object1.getString("weather3");
					weather4 = object1.getString("weather4");
					weather5 = object1.getString("weather5");
					wendu_1 = object1.getString("temp2");
					wendu_2 = object1.getString("temp3");
					wendu_3 = object1.getString("temp4");
					wendu_4 = object1.getString("temp5");
					week1 = object1.getString("week");
					city = object1.getString("city");
					
					Week(week1);
					
					
					
					wendu2.setText(wendu_1);
					wendu3.setText(wendu_2);
					wendu4.setText(wendu_3);
					wendu5.setText(wendu_4);
					tv2.setText(city);
					Weather1(weather1);
					Weather2(weather2);
					Weather3(weather3);
					Weather4(weather4);
					Weather5(weather5);
					String start_s=mtodaywendu.substring(0,mtodaywendu.lastIndexOf("~"));
					String mid_s=mtodaywendu.substring(mtodaywendu.indexOf("~"),mtodaywendu.lastIndexOf("℃"));
					String end_s=mtodaywendu.substring(mtodaywendu.lastIndexOf("℃"));
					mid_s = mid_s+end_s;
					mid_s = mid_s.substring(1,mid_s.lastIndexOf("℃"));
					/*Log.i("log", "-------------start_s=" + start_s);
					Log.i("log", "-------------mid_s=" + mid_s);
					Log.i("log", "-------------end_s=" + end_s);*/
					wendu.setText(start_s+"~");
					wendu1.setText(mid_s+"℃");
					Calendar calCalendar = Calendar.getInstance();
					CalendarUtil22 calendarUtil = new CalendarUtil22(calCalendar);
					date.setText("(" + calendarUtil.getDay().substring(5, 9)
							+ ")");
					Calendar c = Calendar.getInstance();
					year = c.get(Calendar.YEAR);
					month = c.get(Calendar.MONTH) + 1;
					day = c.get(Calendar.DAY_OF_MONTH);
					String time = /*
							 * c.get(Calendar.YEAR)+"-"+ //�õ���
							 */formatTime(c.get(Calendar.MONTH) + 1) + "/" + // month��һ
							 formatTime(c.get(Calendar.DAY_OF_MONTH))/*
																 * +" "+ //��
																 * formatTime(c
																 * .get(Calendar
																 * .HOUR_OF_DAY))+":"+
																 * //ʱ
																 * formatTime(c.get(Calendar
																 * .MINUTE))+":"+ //��
																 * formatTime
																 * (c.get(Calendar
																 * .SECOND))
																 */; // ��
					date1.setText(time);
					Log.i("log", "-------------weather1=" + weather1);
					Log.i("log", "-------------weather2=" + weather2);
					Log.i("log", "-------------weather3=" + weather3);
					Log.i("log", "-------------weather4=" + weather4);
					Log.i("log", "-------------weather5=" + weather5);
					Log.i("log", "-------------wendu=" + start_s+"~");
					Log.i("log", "-------------wendu1=" + mid_s+"℃");
					Log.i("log", "-------------wendu_1=" + wendu_1);
					Log.i("log", "-------------wendu_2=" + wendu_2);
					Log.i("log", "-------------wendu_3=" + wendu_3);
					Log.i("log", "-------------wendu_4=" + wendu_4);
					Log.i("log", "-------------week1=" + week1);
					Log.i("log", "-------------date=" + "(" + calendarUtil.getDay().substring(5, 9)+ ")");
					Log.i("log", "-------------date1=" + time);
					Log.i("log", "-------------city=" + city);
					values.put(com.notbook.weatherdb.Meta.TableMeta._WEATHER1, weather1);
					values.put(com.notbook.weatherdb.Meta.TableMeta._WEATHER2, weather2);
					values.put(com.notbook.weatherdb.Meta.TableMeta._WEATHER3, weather3);
					values.put(com.notbook.weatherdb.Meta.TableMeta._WEATHER4, weather4);
					values.put(com.notbook.weatherdb.Meta.TableMeta._WEATHER5, weather5);
					values.put(com.notbook.weatherdb.Meta.TableMeta._TEMP1, start_s+"~");
					values.put(com.notbook.weatherdb.Meta.TableMeta._DATE3, mid_s+"℃");
					values.put(com.notbook.weatherdb.Meta.TableMeta._TEMP2, wendu_1);
					values.put(com.notbook.weatherdb.Meta.TableMeta._TEMP3, wendu_2);
					values.put(com.notbook.weatherdb.Meta.TableMeta._TEMP4, wendu_3);
					values.put(com.notbook.weatherdb.Meta.TableMeta._TEMP5, wendu_4);
					values.put(com.notbook.weatherdb.Meta.TableMeta._CITY, city);
					values.put(com.notbook.weatherdb.Meta.TableMeta._DATE1, "(" + calendarUtil.getDay().substring(5, 9)+ ")");
					values.put(com.notbook.weatherdb.Meta.TableMeta._DATE2, time);
					Uri uri = resolver.insert(com.notbook.weatherdb.Meta.CONTENT_URI, values);
					Log.i("log", "=====================uri========" + uri);
					if (uri != null) {
						Log.i("log", "添加成功222222222222222222222222");
					}
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			super.onPostExecute(result);
		}
	}

	class TextAsyncTask1 extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {
			return getResultByPost(params[0]);
		}

		@Override
		protected void onPostExecute(String result) {
			if (result == null) {
			} else {

				Log.i("log", "-------------result22222222222=" + result);
				JSONObject object, object1;
				try {
					object = new JSONObject(result);
					String weatherinfo = object.getString("weatherinfo");
					Log.i("log", "-------------weatherinfo22222222222=" + weatherinfo);
					object1 = new JSONObject(weatherinfo);

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			super.onPostExecute(result);
		}
	}

	class TextAsyncTask2 extends AsyncTask<String, Integer, String> {
		@Override
		protected String doInBackground(String... params) {
			return getResultByPost(params[0]);
		}

		@Override
		protected void onPostExecute(String result) {
			if (result == null) {
			} else {
				Log.i("log", "-------------result333333333333=" + result);
				JSONObject object, object1;
				try {
					object = new JSONObject(result);
					String weatherinfo = object.getString("weatherinfo");
					Log.i("log", "-------------weatherinfo3333333333333=" + weatherinfo);
					object1 = new JSONObject(weatherinfo);
					String mtodaywendu = object1.getString("temp");
					String mWD = object1.getString("WD");
					String mWS = object1.getString("WS");
					String mSD = object1.getString("SD");
					todaywendu.setText(mtodaywendu +"°");
					fengxiang.setText(mWD + mWS);
					shidu.setText("湿度" + mSD);
					
					/*Log.i("log", "-------------todaywendu=" + mtodaywendu +"°");
					Log.i("log", "-------------fengxiang=" + mWD + mWS);
					Log.i("log", "-------------shidu=" + "湿度" + mSD);*/
					values.put(com.notbook.weatherdb.Meta.TableMeta._DATE4, mWD + mWS);
					values.put(com.notbook.weatherdb.Meta.TableMeta._TODAYWENDU, mtodaywendu +"°" );
					values.put(com.notbook.weatherdb.Meta.TableMeta._SHIDU, "湿度" + mSD);
					Uri uri = resolver.insert(com.notbook.weatherdb.Meta.CONTENT_URI, values);
					Log.i("log", "=====================uri========" + uri);
					if (uri != null) {
						Log.i("log", "添加成功111111111111111111");
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			super.onPostExecute(result);
		}
	}

	public String getResultByPost(String url) {
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

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			new AlertDialog.Builder(this)
					.setTitle("温馨提示")
					.setMessage("确认要退出吗？")
					.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
								}
							})
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									AppManager.getAppManager()
											.finishAllActivity();
								}
							}).show();
		}

		return super.onKeyDown(keyCode, event);
	}
	/*
	 * 负责做界面更新工作 ，实现下雪
	 */
	public RefreshHandler mRedrawHandler = new RefreshHandler();

	class RefreshHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			//snow.addRandomSnow();
			snow.invalidate();
			sleep(100);
		}
		public void sleep(long delayMillis) {
			this.removeMessages(0);
			sendMessageDelayed(obtainMessage(0), delayMillis);
		}
	};

	/**
	 * Handles the basic update loop, checking to see if we are in the running
	 * state, determining if a move should be made, updating the snake's
	 * location.
	 */
	public void update() {
		snow.addRandomRain();
//		snow.addRandomSnow();
		
		mRedrawHandler.sleep(600);
	}
	public static String formatTime(int t) {
		return t >= 10 ? "" + t : "0" + t;
	}
	class TextAsyncTask5 extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {
			return getResultByPost(params[0]);
		}

		@Override
		protected void onPostExecute(String result) {
			System.out.println("log" + "-------------result" + result);
			if (result == null) {
//				Toast.makeText(Home.this, "网络加载失败..", 0).show();
			} else {
				final JSONObject object;
				try {
					object = new JSONObject(result);
					int responsecode = object.getInt("responsecode");
					System.out.println("====================" + responsecode);
					if (responsecode ==48) {
//						Toast.makeText(Home.this, "版本更新失败..", 0).show();
					} else if (responsecode == 49) {
						new AlertDialog.Builder(Home.this)
						.setTitle("版本更新")
						.setMessage("确认要更新吗？")
						.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
									}
								})
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int whichButton) {
										Intent intent = new Intent(Home.this,
												UpdateService.class);
										String url1;
										try {
											url1 = object.getString("resulturl");
											appName = object.getString("resultname");
											System.out.println("====================" + url1);
											intent.putExtra("Key_App_Name", appName);
											intent.putExtra("Key_Down_Url", url1);
											startService(intent);
											Toast.makeText(Home.this, "更新中..", 0).show();
										} catch (JSONException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										
									}
								}).show();
						
					} else if (responsecode == 50) {
//						Toast.makeText(Home.this, "安卓暂无新版本..", 0).show();
					} else {
//						Toast.makeText(Home.this, "更新失败..", 0).show();
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	
	public void Weather1(String weather){
		Log.i("log", "-------------weather.indexOf=" + weather.indexOf("晴"));
		if(weather.indexOf("晴")>=0){
			weather_1.setBackgroundResource(R.drawable.q0);
			findViewById(R.id.loadingBtn).setVisibility(View.VISIBLE);
		}
		else if(weather.indexOf("云")>=0){
			weather_1.setBackgroundResource(R.drawable.q4);
			findViewById(R.id.sun).setVisibility(View.VISIBLE);
			findViewById(R.id.cloud).setVisibility(View.VISIBLE);
			findViewById(R.id.cloud_out).setVisibility(View.VISIBLE);
		}
		else if(weather.indexOf("雪")>=0){
			weather_1.setBackgroundResource(R.drawable.q1);
			snownum = 1;
			snow.LoadSnowImage();
			
			// 获取当前屏幕的高和宽   
			DisplayMetrics dm = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(dm);
			snow.SetView(300, dm.widthPixels);
			// 更新当前雪花
//			snow.addRandomRain();
			snow.addRandomSnow();
			
			mRedrawHandler.sleep(600);
		}
		else if(weather.indexOf("阴")>=0){
			weather_1.setBackgroundResource(R.drawable.q2);
			findViewById(R.id.bigcloud).setVisibility(View.VISIBLE);
		}
		else if(weather.indexOf("雨")>=0){
			weather_1.setBackgroundResource(R.drawable.q3);
			rainnum = 1;
			snow.LoadRainImage();//雨
			snow.setVisibility(View.VISIBLE);
			DisplayMetrics dm = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(dm);
			snow.SetView(300, dm.widthPixels);
			snow.addRandomRain();
//			snow.addRandomSnow();
			
			mRedrawHandler.sleep(600);
		}
		
		
		
	}
	public void Weather2(String weather){
		if(weather.indexOf("晴")>=0){
			weather_2.setBackgroundResource(R.drawable.q0);
		}
		else if(weather.indexOf("云")>=0){
			weather_2.setBackgroundResource(R.drawable.q4);
		}
		else if(weather.indexOf("雪")>=0){
			weather_2.setBackgroundResource(R.drawable.q1);
		}
		
		else if(weather.indexOf("阴")>=0){
			weather_2.setBackgroundResource(R.drawable.q2);
		}
		else if(weather.indexOf("雨")>=0){
			weather_2.setBackgroundResource(R.drawable.q3);
		}
		
		
	}
	public void Weather3(String weather){
		if(weather.indexOf("晴")>=0){
			weather_3.setBackgroundResource(R.drawable.q0);
		}
		else if(weather.indexOf("云")>=0){
			weather_3.setBackgroundResource(R.drawable.q4);
		}
		else if(weather.indexOf("雪")>=0){
			weather_3.setBackgroundResource(R.drawable.q1);
		}
		
		else if(weather.indexOf("阴")>=0){
			weather_3.setBackgroundResource(R.drawable.q2);
		}
		else if(weather.indexOf("雨")>=0){
			weather_3.setBackgroundResource(R.drawable.q3);
		}
	}
	public void Weather4(String weather){
		if(weather.indexOf("晴")>=0){  
			
			weather_4.setBackgroundResource(R.drawable.q0);
		}
		else if(weather.indexOf("云")>=0){
			weather_4.setBackgroundResource(R.drawable.q4);
		}
		else if(weather.indexOf("雪")>=0){
			weather_4.setBackgroundResource(R.drawable.q1);
		}
		
		else if(weather.indexOf("阴")>=0){
			weather_4.setBackgroundResource(R.drawable.q2);
		}
		else if(weather.indexOf("雨")>=0){
			weather_4.setBackgroundResource(R.drawable.q3);
		}
	}
	public void Weather5(String weather){
		if(weather.indexOf("晴")>=0){
			weather_5.setBackgroundResource(R.drawable.q0);
		}
		else if(weather.indexOf("云")>=0){
			weather_5.setBackgroundResource(R.drawable.q4);
		}
		else if(weather.indexOf("雪")>=0){
			weather_5.setBackgroundResource(R.drawable.q1);
		}
		
		else if(weather.indexOf("阴")>=0){
			weather_5.setBackgroundResource(R.drawable.q2);
		}
		else if(weather.indexOf("雨")>=0){
			weather_5.setBackgroundResource(R.drawable.q3);
		}
	}
	public void Week(String week){
		if(week1.equals("星期一")){
			date111.setText("周二");
			date2.setText("周三");
			date3.setText("周四");
			date4.setText("周五");
		}
		if(week1.equals("星期二")){
			date111.setText("周三");
			date2.setText("周四");
			date3.setText("周五");
			date4.setText("周六");
		}
		if(week1.equals("星期三")){
			date111.setText("周四");
			date2.setText("周五");
			date3.setText("周六");
			date4.setText("周日");
		}
		if(week1.equals("星期四")){
			date111.setText("周五");
			date2.setText("周六");
			date3.setText("周日");
			date4.setText("周一");
		}
		if(week1.equals("星期五")){
			date111.setText("周六");
			date2.setText("周日");
			date3.setText("周一");
			date4.setText("周二");
		}
		if(week1.equals("星期六")){
			date111.setText("周日");
			date2.setText("周一");
			date3.setText("周二");
			date4.setText("周三");
		}
		if(week1.equals("星期日")){
			date111.setText("周一");
			date2.setText("周二");
			date3.setText("周三");
			date4.setText("周四");
		}
		
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		c.close();
		super.onDestroy();
	}
	class TextAsyncTask3 extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {
			return getResultByPost(params[0]);
		}

		@Override
		protected void onPostExecute(String result) {
//			onLoad();
			if (result == null) {
			} else {
				Log.d("log", "---------------------result="+result);
				JSONObject object;
				try {
					object = new JSONObject(result);
					int id = object.getInt("responsecode");  
						if(id == 0){
							Toast.makeText(Home.this, "没有数据", 0).show();
						}else {
							
							String share = object.getString("result"); 
								Gson gson = new Gson();
								Type type = new TypeToken<List<com.notbook.entity.Thing>>() {}.getType();
								things = gson.fromJson(share, type);
								if(null != things && things.size()>0){
									myHandler = new MyHandler();
									Timer timer = new Timer();
									TimerTask task = new TimerTask() {
										@Override
										public void run() {
												myHandler.sendMessage(new Message());
										}
									};
									timer.schedule(task, 1000 * 1); 
								}
						}   
					
				} catch (JSONException e) {
				e.printStackTrace();
				} 
				
				
			}
			super.onPostExecute(result);
		}
	}
	class MyHandler extends Handler {
        public MyHandler() {
        }

        public MyHandler(Looper L) {
            super(L);
        }

        // 子类必须重写此方法,接受数据
        @Override
        public void handleMessage(Message msg) {
	        	String  leixing = things.get(home_flag).getCtype();
				String  nametext = things.get(home_flag).getUname();
				String  timetext = things.get(home_flag).getRingtime();
				youjiantou.setVisibility(View.GONE);
				name.setVisibility(View.VISIBLE);
				time.setVisibility(View.VISIBLE);
				myaboutmessage.setText(leixing+"        ");
				if(nametext==null||nametext.equals("null")){
					nametext = "";
				}
				name.setText(nametext+"        ");
				time.setText(timetext);
				eventlist_num = 1;
				if(eventlist_num == 1){
					eventlist.setOnClickListener(new View.OnClickListener() {
	
						@Override
						public void onClick(View v) {
							
							Intent intent = new Intent(Home.this, MyaboutChuanyudetail.class);
							intent.putExtra("leixing", things.get(home_flag).getCtype());//类型
							intent.putExtra("time", things.get(home_flag).getRingtime());//时间提醒
							intent.putExtra("mtime", things.get(home_flag).getAdvtime());//提前时间
							intent.putExtra("recname", things.get(home_flag).getRecname());//接收人
							intent.putExtra("cname", things.get(home_flag).getCname());//主题
							intent.putExtra("text", things.get(home_flag).getText());//内容
							if(null!= things.get(home_flag).getCphoto()&&!things.get(home_flag).getCphoto().equals("")){
								intent.putExtra("cphoto", things.get(home_flag).getCphoto());//图片
							}else{
								intent.putExtra("cphoto", "");//图片
							}
							if(null!= things.get(home_flag).getChuanyupath()&&!things.get(home_flag).getChuanyupath().equals("")){
								intent.putExtra("chuanyupath", things.get(home_flag).getChuanyupath());//语音
							}else{
								intent.putExtra("chuanyupath", "");//语音
							}
							intent.putExtra("first", things.get(home_flag).getFirst());//优先级
							intent.putExtra("tone", things.get(home_flag).getTone());//铃音
							intent.putExtra("id", things.get(home_flag).getId());//id
							startActivity(intent);
						}
					});
				}
				
				
				home_flag++;
				myHandler = new MyHandler();
				Timer timer = new Timer();
				TimerTask task = new TimerTask() {
					@Override
					public void run() {
							myHandler.sendMessage(new Message());
						
						
					}
				};
				timer.schedule(task, 1000 * 2); 
				if(things.size()==home_flag){
					home_flag = 0;
					
				}
        	}

    }
	class TextAsyncTask_city extends AsyncTask<String, Integer, String> {
		@Override
		protected String doInBackground(String... params) {
			return getResultByPost(params[0]);
		}
		
		@Override
		protected void onPostExecute(String result) {
			if (result == null) {
				Toast.makeText(Home.this, "网络加载失败..", 0).show();
			} else {
				Log.i("log", "-------------result=" + result);
				JSONObject object,object1;
				try {
					object = new JSONObject(result);
					String  weatherinfo = object.getString("weatherinfo");  
					Log.i("log", "-------------weatherinfo=" + weatherinfo);
					object1 = new JSONObject(weatherinfo);
					String  city = object1.getString("city");  
					String  temp1 = object1.getString("temp1");  
					String  temp2 = object1.getString("temp2");  
					String  weather = object1.getString("weather"); 
					if(city.equals("北京")){
						city = "自动定位";
					}
					ContentValues values = new ContentValues();
					values.put(com.notbook.db.Meta.TableMeta._CITY, city);
					values.put(com.notbook.db.Meta.TableMeta._WEATHER, temp1+"~"+temp2 );
					values.put(com.notbook.db.Meta.TableMeta._TEMP, weather);
					Uri uri = resolver.insert(com.notbook.db.Meta.CONTENT_URI, values);
					if(uri != null){
						Log.i("log", "添加成功！");
					}
					
				} catch (JSONException e) {
				e.printStackTrace();
				}
			}
			super.onPostExecute(result);
		}
	}
}
