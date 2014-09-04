package com.notbook.ui;

import java.io.IOException;
import java.util.Calendar;

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
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
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
import com.notbook.app.AppManager;
import com.notbook.calendar.CalendarUtil22;

public class Weatherone extends BaseActivity {

	private Button weatheradd;
	private TextView city;
	public BMapManager mapManager;
	private int jindu = 0, weidu = 0;
	private ProgressDialog dia;
	private TextView todaywendu,wendu,fengxiang,date,difang,date1;
	private int year, month, day;
	private RelativeLayout one ;
	private String weather_1="",weather_2="",weather_3="",weather_4="",weather_5="";
	private String wendu_2="",wendu_3="",wendu_4="",wendu_5="";
	private ImageButton weather1,weather2,weather3,weather4,weather5; 
	private TextView date2,date3,date4,date5;
	private TextView wendu2,wendu3,wendu4,wendu5;
	private String week1="",week2="",week3="",week4="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weather);
		weatheradd  = (Button)findViewById(R.id.weatheradd);
		city  = (TextView)findViewById(R.id.city);
		todaywendu = (TextView) findViewById(R.id.todaywendu);
		wendu = (TextView) findViewById(R.id.wendu);
		wendu2 = (TextView) findViewById(R.id.wendu2);
		wendu3 = (TextView) findViewById(R.id.wendu3);
		wendu4 = (TextView) findViewById(R.id.wendu4);
		wendu5 = (TextView) findViewById(R.id.wendu5);
		
		fengxiang = (TextView) findViewById(R.id.fengxiang);
		date = (TextView) findViewById(R.id.date);
		date1 = (TextView) findViewById(R.id.date1);
		date2 = (TextView) findViewById(R.id.date2);
		date3 = (TextView) findViewById(R.id.date3);
		date4 = (TextView) findViewById(R.id.date4);
		date5 = (TextView) findViewById(R.id.date5);
		difang = (TextView) findViewById(R.id.difang);
		weather1 = (ImageButton) findViewById(R.id.weather1);
		weather2 = (ImageButton) findViewById(R.id.weather2);
		weather3 = (ImageButton) findViewById(R.id.weather3);
		weather4 = (ImageButton) findViewById(R.id.weather4);
		weather5 = (ImageButton) findViewById(R.id.weather5);
		findViewById(R.id.one).setBackgroundResource(background_photobg[num - 1]);
		findViewById(R.id.one1).setBackgroundResource(background_photobg1[num - 1]);
		weatheradd.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent it = new Intent(Weatherone.this,Weatheradd.class);
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
				city.setText("没有获取想要的位置");
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
				String citytext = kk.city;
				Log.i("log", citytext);
				city.setText(citytext);
				// mapManager.stop();
				
				mapManager.destroy();
				
				
				
				String url = "http://m.weather.com.cn/data/101010100.html";
				Log.i("log", "-------------url=" + url);
				new TextAsyncTask().execute(url);
				
				
				
				String url1 = "http://www.weather.com.cn/data/cityinfo/101010100.html";
				new TextAsyncTask1().execute(url1);
				String url2 = "http://www.weather.com.cn/data/sk/101010100.html";
				new TextAsyncTask2().execute(url2);
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
			dia = new ProgressDialog(Weatherone.this);
			dia.setMessage("正在加载...");
			dia.show();
			super.onPreExecute();
		}
		@Override
		protected String doInBackground(String... params) {
			return getResultByPost(params[0]);
		}
		
		@Override
		protected void onPostExecute(String result) {
			if (result == null) {
				Toast.makeText(getApplicationContext(), "网络连接失败...",0).show();
				
			} else {
				
				Log.i("log", "-------------result11111111111=" + result);
				JSONObject object,object1;
				try {
					dia.dismiss();
					object = new JSONObject(result);
					String  weatherinfo = object.getString("weatherinfo");  
					Log.i("log", "-------------weatherinfo=" + weatherinfo);
					object1 = new JSONObject(weatherinfo);
					String  mtodaywendu = object1.getString("temp1");  
					String  mdate = object1.getString("date_y");  
					weather_1 = object1.getString("weather1");  
					weather_2 = object1.getString("weather2");  
					weather_3 = object1.getString("weather3");  
					weather_4 = object1.getString("weather4");  
					weather_5 = object1.getString("weather5"); 
					wendu_2 = object1.getString("temp2");
					wendu_3 = object1.getString("temp3");
					wendu_4 = object1.getString("temp4");
					wendu_5 = object1.getString("temp5");
					Weather1(weather_1);
					Weather2(weather_2);
					Weather3(weather_3);
					Weather4(weather_4);
					Weather5(weather_5);
					
				    wendu.setText(mtodaywendu);
					wendu2.setText(wendu_2);
					wendu3.setText(wendu_3);
					wendu4.setText(wendu_4);
					wendu5.setText(wendu_5);
				    week1 = object1.getString("week");
				    Week(week1);
//				    date.setText(mdate);
				    Calendar calCalendar = Calendar.getInstance();
				    CalendarUtil22 calendarUtil = new CalendarUtil22(calCalendar);
				    date.setText("(" + calendarUtil.getDay().substring(5, 9)+ ")");
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
				Toast.makeText(getApplicationContext(), "网络连接失败...！",0).show();
			} else {
				
				Log.i("log", "-------------result2222222222222222=" + result);
				JSONObject object,object1;
				try {
					object = new JSONObject(result);
					String  weatherinfo = object.getString("weatherinfo");  
					Log.i("log", "-------------weatherinfo=" + weatherinfo);
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
				Toast.makeText(getApplicationContext(), "网络连接失败...！",0).show();
			} else {
				Log.i("log", "-------------result=3333333333333333" + result);
				JSONObject object,object1;
				try {
					object = new JSONObject(result);
					String  weatherinfo = object.getString("weatherinfo");  
					Log.i("log", "-------------weatherinfo=" + weatherinfo);
					object1 = new JSONObject(weatherinfo);
					String  mtodaywendu = object1.getString("temp");  
					String  mWD = object1.getString("WD");  
					String  mWS = object1.getString("WS");  
					String  mSD = object1.getString("SD");  
					todaywendu.setText(mtodaywendu+"°");
				    fengxiang.setText(mWD+mWS);
				    difang.setText("湿度"+mSD);
				    
				} catch (JSONException e) {
				e.printStackTrace();
				}
			}
			super.onPostExecute(result);
		}
	}

	private String getResultByPost(String url) {
		String result = null;
		try{
			HttpGet request=new HttpGet(url);
			HttpClient client=new DefaultHttpClient();
		    HttpResponse response=client.execute(request);
		   
		    HttpEntity entity = response.getEntity();
        	result = EntityUtils.toString(entity);
		    
		}catch(IOException e){
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
									AppManager.getAppManager().finishAllActivity();
								}
							}).show();
		}

		return super.onKeyDown(keyCode, event);
	}
	private String formatTime(int t) {
		return t >= 10 ? "" + t : "0" + t;
	}
	public void Week(String week){
		if(week1.equals("星期一")){
			date2.setText("周二");
			date3.setText("周三");
			date4.setText("周四");
			date5.setText("周五");
		}
		if(week1.equals("星期二")){
			date2.setText("周三");
			date3.setText("周四");
			date4.setText("周五");
			date5.setText("周六");
		}
		if(week1.equals("星期三")){
			date2.setText("周四");
			date3.setText("周五");
			date4.setText("周六");
			date5.setText("周日");
		}
		if(week1.equals("星期四")){
			date2.setText("周五");
			date3.setText("周六");
			date4.setText("周日");
			date5.setText("周一");
		}
		if(week1.equals("星期五")){
			date2.setText("周六");
			date3.setText("周日");
			date4.setText("周一");
			date5.setText("周二");
		}
		if(week1.equals("星期六")){
			date2.setText("周日");
			date3.setText("周一");
			date4.setText("周二");
			date5.setText("周三");
		}
		if(week1.equals("星期日")){
			date2.setText("周一");
			date3.setText("周二");
			date4.setText("周三");
			date5.setText("周四");
		}
		
	}
	public void Weather1(String weather){
		if(weather.indexOf("晴")>=0){
			weather1.setBackgroundResource(R.drawable.q0);
			findViewById(R.id.loadingBtn).setVisibility(View.VISIBLE);
		}
		else if(weather.indexOf("云")>=0){
			weather1.setBackgroundResource(R.drawable.q4);
			findViewById(R.id.sun).setVisibility(View.VISIBLE);
			findViewById(R.id.cloud).setVisibility(View.VISIBLE);
			findViewById(R.id.cloud_out).setVisibility(View.VISIBLE);
		}
		else if(weather.indexOf("雪")>=0){
			weather1.setBackgroundResource(R.drawable.q1);
		}
		else if(weather.indexOf("阴")>=0){
			weather1.setBackgroundResource(R.drawable.q2);
			findViewById(R.id.bigcloud).setVisibility(View.VISIBLE);
		}
		else if(weather.indexOf("雨")>=0){
			weather1.setBackgroundResource(R.drawable.q3);
			
		}
		
		
		
	}
	public void Weather2(String weather){
		if(weather.indexOf("晴")>=0){
			weather2.setBackgroundResource(R.drawable.q0);
		}
		else if(weather.indexOf("云")>=0){
			weather2.setBackgroundResource(R.drawable.q4);
		}
		else if(weather.indexOf("雪")>=0){
			weather2.setBackgroundResource(R.drawable.q1);
		}
		else if(weather.indexOf("阴")>=0){
			weather2.setBackgroundResource(R.drawable.q2);
		}
		else if(weather.indexOf("雨")>=0){
			weather2.setBackgroundResource(R.drawable.q3);
		}
		
		
		
	}
	public void Weather3(String weather){
		if(weather.indexOf("晴")>=0){
			weather3.setBackgroundResource(R.drawable.q0);
		}
		else if(weather.indexOf("云")>=0){
			weather3.setBackgroundResource(R.drawable.q4);
		}
		else if(weather.indexOf("雪")>=0){
			weather3.setBackgroundResource(R.drawable.q1);
		}
		else if(weather.indexOf("阴")>=0){
			weather3.setBackgroundResource(R.drawable.q2);
		}
		else if(weather.indexOf("雨")>=0){
			weather3.setBackgroundResource(R.drawable.q3);
		}
	}
	public void Weather4(String weather){
		if(weather.indexOf("晴")>=0){
			weather4.setBackgroundResource(R.drawable.q0);
		}
		else if(weather.indexOf("云")>=0){
			weather4.setBackgroundResource(R.drawable.q4);
		}
		else if(weather.indexOf("雪")>=0){
			weather4.setBackgroundResource(R.drawable.q1);
		}
		else if(weather.indexOf("阴")>=0){
			weather4.setBackgroundResource(R.drawable.q2);
		}
		else if(weather.indexOf("雨")>=0){
			weather4.setBackgroundResource(R.drawable.q3);
		}
	}
	public void Weather5(String weather){
		if(weather.indexOf("晴")>=0){
			weather5.setBackgroundResource(R.drawable.q0);
		}
		else if(weather.indexOf("云")>=0){
			weather5.setBackgroundResource(R.drawable.q4);
		}
		else if(weather.indexOf("雪")>=0){
			weather5.setBackgroundResource(R.drawable.q1);
		}
		else if(weather.indexOf("阴")>=0){
			weather5.setBackgroundResource(R.drawable.q2);
		}
		else if(weather.indexOf("雨")>=0){
			weather5.setBackgroundResource(R.drawable.q3);
		}
	}
}
