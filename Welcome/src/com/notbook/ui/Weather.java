package com.notbook.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Gallery;
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
import com.netbook.snow.SnowView;
import com.notbook.app.AppManager;
import com.notbook.app.ApplicationTrans;
import com.notbook.calendar.CalendarUtil22;
import com.notbook.db.Info;
import com.notbook.entity.CityClass;
import com.notbook.entity.MPhone;
import com.notbook.typedb.Meta;
import com.notbook.ui.Citylist.TextAsyncTask;
import com.notbook.ui.Home.MyLocationListener;
import com.notbook.ui.Home.MyMKGeneralListener;
import com.notbook.ui.Home.RefreshHandler;
import com.notbook.ui.Manager.ViewHolder;
import com.notbook.weather_db.DBManager;

public class Weather extends BaseActivity {

	private Button weatheradd;
	private TextView city;
	private String citytext = "";
	public BMapManager mapManager;
	private Button more_back;
	private int jindu = 0, weidu = 0;
	private ProgressDialog dia;
	private TextView todaywendu,wendu,fengxiang,date,difang,date1;
	private int year, month, day;
	private RelativeLayout one ;
	String weather_1="",weather_2="",weather_3="",weather_4="",weather_5="";//天气
	private String wendu_1="",wendu_2="",wendu_3="",wendu_4="",wendu_5="";//温度
	private ImageButton weather1,weather2,weather3,weather4,weather5; //天气控件
	private TextView date2,date3,date4,date5;//温度控件
	private TextView wendu2,wendu3,wendu4,wendu5;
	private String week1="",week2="",week3="",week4="";
	Object  mtodaywendu = "";  
	Object  mWD = "";  
	Object  mWS = "";  
	Object  mSD = "";
	private Gallery weatherlist;
	static ContentResolver resolver;
	Cursor c ;
	private List<Info> list = new ArrayList<Info>();
	List<Map<String,String>> data = new ArrayList<Map<String,String>>();
	private int positionnum = 0;
	private SnowView snow = null;
	public DBManager dbHelper;
	private SQLiteDatabase database;
	public static ArrayList<CityClass> CITY;
	private Button iamgebutton1,iamgebutton2,iamgebutton3,iamgebutton4,iamgebutton5;
	List lists=new ArrayList();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weatherone);
		findViewById(R.id.one).setBackgroundResource(background_photobg[num - 1]);
		findViewById(R.id.one1).setBackgroundResource(background_photobg1[num - 1]);
		findViewById(R.id.one2).setBackgroundResource(background_photobg1[num - 1]);
		weatherlist = (Gallery)findViewById(R.id.weatherlist);
		weatheradd  = (Button)findViewById(R.id.weatheradd);
		more_back  = (Button)findViewById(R.id.more_back);
		city  = (TextView)findViewById(R.id.city);
		init();
		resolver = getContentResolver();
//		if(city.getText().toString().equals("未添加城市")){
//			Intent it = new Intent(Weather.this,Weatheradd.class);
//			startActivity(it);
//			finish();
//		}
		more_back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Weather.this, MainActivity3.class);
				startActivity(intent);
				Weather.this.finish();
			}
		});
		
		
		c = resolver.query(com.notbook.db.Meta.CONTENT_URI, null, null, null,"_id asc limit 5");
		if(c!=null&&c.moveToFirst()){
			for(c.moveToFirst();!c.isAfterLast();c.moveToNext())  {  
				int nameColumn = c.getColumnIndex(com.notbook.db.Meta.TableMeta._CITY);  
				String city = c.getString(nameColumn);
				String id = c.getString(c.getColumnIndex(com.notbook.db.Meta.TableMeta._ID));
				Info in = new Info();
				in.setCity(city);
				in.setId(id);
				list.add(in);
			}  
		}else{
			Info in = new Info();
			in.setCity("自动定位");
			list.add(in);
		}
		
		WeatherAdater wa = new  WeatherAdater(Weather.this,list);
		weatherlist.setAdapter(wa);  
		weatherlist.setSelected(true);  
		weatherlist.setSelection(Weatheradd.weatheradd_id);
		
		
//		weatherlist.setOnItemClickListener(
//                new OnItemClickListener(){
//                        @Override
//                        public void onItemClick(AdapterView parent, View v, int position, long id) {
//                                // TODO Auto-generated method stub
//                        	/*city.setText(list.get(position).getCity().toString());
//                        	positionnum = position ;*/
//                        	/*Intent it = new Intent(Weather.this,Eventlist.class);
//            				startActivity(it);*/
//                        }        
//                }
//		);    
		weatherlist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				positionnum = position ;
				Log.i("log", "-------------positionnum=" + positionnum);
				
				city.setText(list.get(positionnum).getCity());
				if(list.get(positionnum).getCity().equals("自动定位")){
					city.setText("北京");
				}	
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
		//Log.i("log", "-------------list=" + list);
		
		
		weatheradd.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent it = new Intent(Weather.this,Weatheradd.class);
				startActivity(it);
			}
		});  
		
		
	}
	class DongTaiHolder {
		TextView city;
		TextView todaywendu;
		TextView fengxiang;
		TextView date;
		TextView difang;
		TextView date1;
		TextView date2;
		TextView date3;
		TextView date4;
		TextView date5;
		TextView wendu1;
		TextView wendu2;
		TextView wendu3;
		TextView wendu4;
		TextView wendu5;
		ImageButton weather1;
		ImageButton weather2;
		ImageButton weather3;
		ImageButton weather4;
		ImageButton weather5;
		RelativeLayout weatherdetail;
	}
	private class WeatherAdater extends BaseAdapter {
		private Context xontext;
		private List<Info> list;
		public WeatherAdater(Context xontext, List<Info> list) {
			this.xontext = xontext;
			this.list = list;
			for(int i=0;i<list.size();i++){
				lists.add("0");
			}
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}
		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
		}
		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}
		DongTaiHolder holder = null;
		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			
			if (convertView == null) {
				holder = new DongTaiHolder();
				convertView = LayoutInflater.from(xontext).inflate(R.layout.weatheradater, null);
//				Log.i("log", "-------------position=" + position);
				Log.i("log", "-------------cityName=" + list.get(position).getCity());
				Log.i("log", "-------------cityId=" + list.get(position).getId());	
				holder.todaywendu = (TextView) convertView.findViewById(R.id.todaywendu);
				holder.wendu1 = (TextView) convertView.findViewById(R.id.wendu);
				holder.wendu2 = (TextView) convertView.findViewById(R.id.wendu2);
				holder.wendu3 = (TextView) convertView.findViewById(R.id.wendu3);
				holder.wendu4 = (TextView) convertView.findViewById(R.id.wendu4);
				holder.wendu5 = (TextView) convertView.findViewById(R.id.wendu5);
				
				holder.fengxiang = (TextView) convertView.findViewById(R.id.fengxiang);
				holder.date = (TextView) convertView.findViewById(R.id.date);
				holder.date1 = (TextView) convertView.findViewById(R.id.date1);
				holder.date2 = (TextView) convertView.findViewById(R.id.date2);
				holder.date3 = (TextView) convertView.findViewById(R.id.date3);
				holder.date4 = (TextView) convertView.findViewById(R.id.date4);
				holder.date5 = (TextView) convertView.findViewById(R.id.date5);
				holder.difang = (TextView) convertView.findViewById(R.id.difang);
				holder.weather1 = (ImageButton) convertView.findViewById(R.id.weather1);
				holder.weather2 = (ImageButton) convertView.findViewById(R.id.weather2);
				holder.weather3 = (ImageButton) convertView.findViewById(R.id.weather3);
				holder.weather4 = (ImageButton) convertView.findViewById(R.id.weather4);
				holder.weather5 = (ImageButton) convertView.findViewById(R.id.weather5);
				holder.weatherdetail = (RelativeLayout) convertView.findViewById(R.id.weatherdetail);
				Animation anim = AnimationUtils.loadAnimation(Weather.this,
						R.anim.round_loading);
				convertView.findViewById(R.id.loadingBtn).startAnimation(anim);

				// 多云
				Animation cloud_anim = AnimationUtils.loadAnimation(Weather.this,
						R.anim.push_up_in);
				Animation cloud_out_anim = AnimationUtils.loadAnimation(Weather.this,
						R.anim.push_out_in);
				convertView.findViewById(R.id.cloud_out).startAnimation(cloud_anim);
				convertView.findViewById(R.id.cloud).startAnimation(cloud_out_anim);
				

				// 阴天
				Animation cloudanim = AnimationUtils.loadAnimation(Weather.this,
						R.anim.push_outin);
				convertView.findViewById(R.id.bigcloud).startAnimation(cloudanim);
				
				Boolean flag = false;
				if(flag = true){
					// 获得雪花视图,并加载雪花图片到内存
					snow = (SnowView) convertView.findViewById(R.id.snow);
				}
				
				
				convertView.setTag(holder);
			} else {

				holder = (DongTaiHolder) convertView.getTag();
				
			}
			
//			if(list.get(position).getCity().equals("自动定位")){
//				city.setText("北京");
//			}	
			if(list.get(position).getCity().equals("自动定位")){
				String url = "http://m.weather.com.cn/data/101010100.html";
				Log.i("log", "-------------url=" + url);
				new TextAsyncTask(holder).execute(url);
				String url1 = "http://www.weather.com.cn/data/cityinfo/101010100.html";
//				new TextAsyncTask1(holder).execute(url);
				String url2 = "http://www.weather.com.cn/data/sk/101010100.html";
				new TextAsyncTask2(holder).execute(url2);
			}
			else if(list.get(position).getCity().equals("上海")){
				String url = "http://m.weather.com.cn/data/101020100.html";
				Log.i("log", "-------------url=" + url);
				new TextAsyncTask(holder).execute(url);
				String url1 = "http://www.weather.com.cn/data/cityinfo/101020100.html";
//				new TextAsyncTask1(holder).execute(url1);
				String url2 = "http://www.weather.com.cn/data/sk/101020100.html";
				new TextAsyncTask2(holder).execute(url2);
			}
			else if(list.get(position).getCity().equals("天津")){
				String url = "http://m.weather.com.cn/data/101030100.html";
				Log.i("log", "-------------url=" + url);
				new TextAsyncTask(holder).execute(url);
				String url1 = "http://www.weather.com.cn/data/cityinfo/101030100.html";
//				new TextAsyncTask1(holder).execute(url1);
				String url2 = "http://www.weather.com.cn/data/sk/101030100.html";
				new TextAsyncTask2(holder).execute(url2);

			}
			else if(list.get(position).getCity().equals("重庆")){
				String url = "http://m.weather.com.cn/data/101040100.html";
				Log.i("log", "-------------url=" + url);
				new TextAsyncTask(holder).execute(url);
				String url1 = "http://www.weather.com.cn/data/cityinfo/101040100.html";
//				new TextAsyncTask1(holder).execute(url1);
				String url2 = "http://www.weather.com.cn/data/sk/101040100.html";
				new TextAsyncTask2(holder).execute(url2);
			}
			else if(list.get(position).getCity().equals("香港")){
				String url = "http://m.weather.com.cn/data/101320101.html";
				Log.i("log", "-------------url=" + url);
				new TextAsyncTask(holder).execute(url);
				String url1 = "http://www.weather.com.cn/data/cityinfo/101320101.html";
//				new TextAsyncTask1(holder).execute(url1);
				String url2 = "http://www.weather.com.cn/data/sk/101320101.html";
				new TextAsyncTask2(holder).execute(url2);
			}
			else if(list.get(position).getCity().equals("澳门")){
				String url = "http://m.weather.com.cn/data/101330101.html";
				Log.i("log", "-------------url=" + url);
				new TextAsyncTask(holder).execute(url);
				String url1 = "http://www.weather.com.cn/data/cityinfo/101330101.html";
//				new TextAsyncTask1(holder).execute(url1);
				String url2 = "http://www.weather.com.cn/data/sk/101330101.html";
				new TextAsyncTask2(holder).execute(url2);
			}else{
				dbHelper = new DBManager(Weather.this);
				dbHelper.openDatabase();
				dbHelper.closeDatabase();
				database = SQLiteDatabase.openOrCreateDatabase(DBManager.DB_PATH +"/"+ DBManager.DB_NAME,null);
				CITY = getCity();
				String code = "";
				for(int i= 0 ;i<CITY.size();i++){
					if(CITY.get(i).getName().indexOf(list.get(position).getCity())>=0){
						if(lists.get(position).toString().equals("0"))
						{
							code = CITY.get(i).getCity_num();
							lists.set(position, "1");
							
						}
						
					}
				}
				String url = "http://m.weather.com.cn/data/"+code+".html";
				Log.i("log", "-------------url=" + url);
				
				new TextAsyncTask(holder).execute(url);
				String url1 = "http://www.weather.com.cn/data/cityinfo/"+code+".html";
//				new TextAsyncTask1(holder).execute(url1);
				String url2 = "http://www.weather.com.cn/data/sk/"+code+".html";
				new TextAsyncTask2(holder).execute(url2);
				database.close();
				
				
			}
			
			
			return convertView;
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
//				city.setText(citytext);
				// mapManager.stop();
				
				mapManager.destroy();
				
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
		
		private DongTaiHolder holder;
		public TextAsyncTask() {
		}
		public TextAsyncTask(DongTaiHolder holder) {
			this.holder = holder;
			// TODO Auto-generated constructor stub
		}
		@Override
		protected String doInBackground(String... params) {
			return getResultByPost(params[0]);
		}
		/*@Override
		protected void onPreExecute() {
			dia = new ProgressDialog(Weather.this);
			dia.setMessage("正在加载...");
			dia.show();
			dia.setCanceledOnTouchOutside(false);
			super.onPreExecute();
		}*/
		@Override
		protected void onPostExecute(String result) {

			if (result == null) {
				Toast.makeText(getApplicationContext(), "网络连接失败...",0).show();
			} else {
				Log.i("log", "-------------result11111111111=" + result);
				JSONObject object,object1;
				try {
					
					object = new JSONObject(result);
					String  weatherinfo = object.getString("weatherinfo");  
					Log.i("log", "-------------weatherinfo=" + weatherinfo);
					object1 = new JSONObject(weatherinfo);
					
					String  mdate = object1.getString("date_y");  
					weather_1 = object1.getString("weather1");  
					weather_2 = object1.getString("weather2");  
					weather_3 = object1.getString("weather3");  
					weather_4 = object1.getString("weather4");  
					weather_5 = object1.getString("weather5"); 
					wendu_1 = object1.getString("temp1");  
					wendu_2 = object1.getString("temp2");
					wendu_3 = object1.getString("temp3");
					wendu_4 = object1.getString("temp4");
					wendu_5 = object1.getString("temp5");
					Weather1(holder, weather_1);
					Weather2(holder, weather_2);
					Weather3(holder, weather_3);
					Weather4(holder, weather_4);
					Weather5(holder, weather_5);
//					city.setText(object1.getString("city"));
					holder.wendu1.setText(wendu_1);
					holder.wendu2.setText(wendu_2);
					holder.wendu3.setText(wendu_3);
					holder.wendu4.setText(wendu_4);
					holder.wendu5.setText(wendu_5);
				    week1 = object1.getString("week");
				    Week(holder, week1);
//				    date.setText(mdate);
				    Calendar calCalendar = Calendar.getInstance();
				    CalendarUtil22 calendarUtil = new CalendarUtil22(calCalendar);
				    holder.date.setText("(" + calendarUtil.getDay().substring(5, 9)+ ")");
				    Calendar c = Calendar.getInstance();
					year = c.get(Calendar.YEAR);
					month = c.get(Calendar.MONTH) + 1;
					day = c.get(Calendar.DAY_OF_MONTH);
					String time = formatTime(c.get(Calendar.MONTH) + 1) + "/" + 
							 	  formatTime(c.get(Calendar.DAY_OF_MONTH)); 
					holder.date1.setText(time);
				} catch (JSONException e) {
				e.printStackTrace();
				}
			}
			super.onPostExecute(result);
		}
	}
	
	class TextAsyncTask2 extends AsyncTask<String, Integer, String> {
		private DongTaiHolder holder;
		public TextAsyncTask2() {
		}
		public TextAsyncTask2(DongTaiHolder holder) {
			this.holder = holder;
			// TODO Auto-generated constructor stub
		}
		@Override
		protected String doInBackground(String... params) {
			return getResultByPost(params[0]);
		}
		
		@Override
		protected void onPostExecute(String result) {
			if (result == null) {
			} else {
				Log.i("log", "-------------result=3333333333333333" + result);
				JSONObject object,object1;
				try {
					object = new JSONObject(result);
					String  weatherinfo = object.getString("weatherinfo");  
					Log.i("log", "-------------weatherinfo=" + weatherinfo);
					object1 = new JSONObject(weatherinfo);
					mtodaywendu = object1.get("temp");  
					mWD = object1.get("WD");  
					mWS = object1.get("WS");  
					mSD = object1.get("SD");  
					Log.i("log", "-------------mtodaywendu=" + mtodaywendu);
//					todaywendu = (TextView) findViewById(R.id.todaywendu);
					holder.todaywendu.setText(mtodaywendu+"°");   
//					fengxiang = (TextView) findViewById(R.id.fengxiang);
					holder.fengxiang.setText(String.valueOf(mWD)+String.valueOf(mWS));
//				    difang = (TextView) findViewById(R.id.difang);
					holder.difang.setText("湿度"+mSD);
				    
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
	
	private String formatTime(int t) {
		return t >= 10 ? "" + t : "0" + t;
	}
	public void Week(DongTaiHolder holder, String week){
		if(week1.equals("星期一")){
			holder.date2.setText("周二");
			holder.date3.setText("周三");
			holder.date4.setText("周四");
			holder.date5.setText("周五");
		}
		if(week1.equals("星期二")){
			holder.date2.setText("周三");
			holder.date3.setText("周四");
			holder.date4.setText("周五");
			holder.date5.setText("周六");
		}
		if(week1.equals("星期三")){
			holder.date2.setText("周四");
			holder.date3.setText("周五");
			holder.date4.setText("周六");
			holder.date5.setText("周日");
		}
		if(week1.equals("星期四")){
			holder.date2.setText("周五");
			holder.date3.setText("周六");
			holder.date4.setText("周日");
			holder.date5.setText("周一");
		}
		if(week1.equals("星期五")){
			holder.date2.setText("周六");
			holder.date3.setText("周日");
			holder.date4.setText("周一");
			holder.date5.setText("周二");
		}
		if(week1.equals("星期六")){
			holder.date2.setText("周日");
			holder.date3.setText("周一");
			holder.date4.setText("周二");
			holder.date5.setText("周三");
		}
		if(week1.equals("星期日")){
			holder.date2.setText("周一");
			holder.date3.setText("周二");
			holder.date4.setText("周三");
			holder.date5.setText("周四");
		}
		
	}
	public void Weather1(DongTaiHolder holder, String weather){
		if(weather.indexOf("晴")>=0){
			holder.weather1.setBackgroundResource(R.drawable.q0);
//			findViewById(R.id.loadingBtn).setVisibility(View.VISIBLE);
		}
		else if(weather.indexOf("云")>=0){
			holder.weather1.setBackgroundResource(R.drawable.q4);
//			findViewById(R.id.sun).setVisibility(View.VISIBLE);
//			findViewById(R.id.cloud).setVisibility(View.VISIBLE);
//			findViewById(R.id.cloud_out).setVisibility(View.VISIBLE);
		}
		else if(weather.indexOf("雪")>=0){
			holder.weather1.setBackgroundResource(R.drawable.q1);
//			snow.LoadSnowImage();
//			snow.setVisibility(View.VISIBLE);
//			// 获取当前屏幕的高和宽   
//			DisplayMetrics dm = new DisplayMetrics();
//			getWindowManager().getDefaultDisplay().getMetrics(dm);
//			snow.SetView(300, dm.widthPixels);
//			// 更新当前雪花
//			snow.addRandomSnow();
//			
//			mRedrawHandler.sleep(600);
		}
		else if(weather.indexOf("阴")>=0){
			holder.weather1.setBackgroundResource(R.drawable.q2);
//			findViewById(R.id.bigcloud).setVisibility(View.VISIBLE);
		}
		else if(weather.indexOf("雨")>=0){
			holder.weather1.setBackgroundResource(R.drawable.q3);
//			snow.LoadRainImage();//雨
//			snow.setVisibility(View.VISIBLE);
//			DisplayMetrics dm = new DisplayMetrics();
//			getWindowManager().getDefaultDisplay().getMetrics(dm);
//			snow.SetView(300, dm.widthPixels);
//			snow.addRandomRain();
//			
//			mRedrawHandler.sleep(600);
		}
	}
	public void Weather2(DongTaiHolder holder, String weather){
		if(weather.indexOf("晴")>=0){
			holder.weather2.setBackgroundResource(R.drawable.q0);
		}
		else if(weather.indexOf("云")>=0){
			holder.weather2.setBackgroundResource(R.drawable.q4);
		}
		else if(weather.indexOf("雪")>=0){
			holder.weather2.setBackgroundResource(R.drawable.q1);
		}
		else if(weather.indexOf("阴")>=0){
			holder.weather2.setBackgroundResource(R.drawable.q2);
		}
		else if(weather.indexOf("雨")>=0){
			holder.weather2.setBackgroundResource(R.drawable.q3);
		}
	}
	public void Weather3(DongTaiHolder holder, String weather){
		if(weather.indexOf("晴")>=0){
			holder.weather3.setBackgroundResource(R.drawable.q0);
		}
		else if(weather.indexOf("云")>=0){
			holder.weather3.setBackgroundResource(R.drawable.q4);
		}
		else if(weather.indexOf("雪")>=0){
			holder.weather3.setBackgroundResource(R.drawable.q1);
		}
		else if(weather.indexOf("阴")>=0){
			holder.weather3.setBackgroundResource(R.drawable.q2);
		}
		else if(weather.indexOf("雨")>=0){
			holder.weather3.setBackgroundResource(R.drawable.q3);
		}
	}
	public void Weather4(DongTaiHolder holder, String weather){
		if(weather.indexOf("晴")>=0){
			holder.weather4.setBackgroundResource(R.drawable.q0);
		}
		else if(weather.indexOf("云")>=0){
			holder.weather4.setBackgroundResource(R.drawable.q4);
		}
		else if(weather.indexOf("雪")>=0){
			holder.weather4.setBackgroundResource(R.drawable.q1);
		}
		else if(weather.indexOf("阴")>=0){
			holder.weather4.setBackgroundResource(R.drawable.q2);
		}
		else if(weather.indexOf("雨")>=0){
			holder.weather4.setBackgroundResource(R.drawable.q3);
		}
	}
	public void Weather5(DongTaiHolder holder, String weather){
		if(weather.indexOf("晴")>=0){
			holder.weather5.setBackgroundResource(R.drawable.q0);
		}
		else if(weather.indexOf("云")>=0){
			holder.weather5.setBackgroundResource(R.drawable.q4);
		}
		else if(weather.indexOf("雪")>=0){
			holder.weather5.setBackgroundResource(R.drawable.q1);
		}
		else if(weather.indexOf("阴")>=0){
			holder.weather5.setBackgroundResource(R.drawable.q2);
		}
		else if(weather.indexOf("雨")>=0){
			holder.weather5.setBackgroundResource(R.drawable.q3);
		}
	}
	public void update() {
		snow.addRandomRain();
//		snow.addRandomSnow();
		
		mRedrawHandler.sleep(600);
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
private ArrayList<CityClass> getCity() {
        
        Cursor cur = database.rawQuery("SELECT name,_id,city_num,province_id FROM citys ",null);
         
        if(cur !=null) {
            int NUM_CITY = cur.getCount();
            ArrayList<CityClass> taxicity =new ArrayList<CityClass>(NUM_CITY);
            if(cur.moveToFirst()) {
                do{
                	String id = cur.getString(cur.getColumnIndex("_id"));
                    String name = cur.getString(cur.getColumnIndex("name"));
                    String city_num = cur.getString(cur.getColumnIndex("city_num"));
                    String province_id = cur.getString(cur.getColumnIndex("province_id"));
                    CityClass city_1 = new CityClass();
//                    System.out.println(name); //额外添加一句，把select到的信息输出到Logcat
                    city_1.setCity_num(city_num);
                    city_1.setName(name);
                    city_1.set_id(id);
                    city_1.setProvince_id(province_id);
                    taxicity.add(city_1);
                    database.close();
                }while(cur.moveToNext());
            }
            return taxicity;
        }else{
            return null;
        }
    }
	public void init(){
		iamgebutton1 = (Button) findViewById(R.id.iamgebutton1);
		iamgebutton1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Weather.this, MainActivity0.class);
				startActivity(intent);
			}
		});
		iamgebutton2 = (Button) findViewById(R.id.iamgebutton2);
		iamgebutton2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Weather.this, MainActivity2.class);
				startActivity(intent);
			}
		});
		iamgebutton3 = (Button) findViewById(R.id.iamgebutton3);
		iamgebutton3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Weather.this, MainActivity3.class);
				startActivity(intent);
			}
		});
		iamgebutton4 = (Button) findViewById(R.id.iamgebutton4);
		iamgebutton4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Weather.this, MainActivity4.class);
				startActivity(intent);
			}
		});
		iamgebutton5 = (Button) findViewById(R.id.iamgebutton5);
		iamgebutton5.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Weather.this, MainActivity1.class);
				startActivity(intent);
			}
		});
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			Intent intent = new Intent(Weather.this, MainActivity3.class);
			startActivity(intent);
			finish();
		}

		return super.onKeyDown(keyCode, event);
	}
}
