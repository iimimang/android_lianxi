package com.notbook.ui;

import java.io.IOException;
import java.util.ArrayList;
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
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notbook.R;
import com.notbook.app.AppManager;
import com.notbook.db.Meta;
import com.notbook.ui.Citylist.TextAsyncTask;



public class Weatheradd extends BaseActivity {

	private Button iamgebutton1, iamgebutton2, iamgebutton3, iamgebutton4,
			iamgebutton5;
	private Button more_back, cityadd;
	static ContentResolver resolver;
	private GridView findshare;
	static String id = null;
	public static int weathernum = 1;
	public static int weatheradd_id = 0;
	private SimpleCursorAdapter sca = null;
	private String city = "";
	private String weather = "";
	private String temp = "";
	List<Map<String,String>> data = new ArrayList<Map<String,String>>();
	private MyAdapter myadapter = null;
	public int positionid = 0;
	private Cursor c = null;
	private Boolean flag = false;
	private Boolean flag1 = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weatheradd);
		findViewById(R.id.one).setBackgroundResource(
				background_photobg[num - 1]);
		findViewById(R.id.one2).setBackgroundResource(
				background_photobg1[num - 1]);
		findViewById(R.id.one1).setBackgroundResource(
				background_photobg1[num - 1]);
		more_back = (Button) findViewById(R.id.more_back);
		cityadd = (Button) findViewById(R.id.cityadd);
		/*if(flag1 = false){
			String url ="http://www.weather.com.cn/data/cityinfo/101010100.html";
			new TextAsyncTask().execute(url);
			flag1 = true ;  
		}*/
		
		resolver = getContentResolver();
		findshare = (GridView) findViewById(R.id.findshare);
		c = resolver.query(Meta.CONTENT_URI, null, null, null,
				"_id asc limit 10");
//		Toast.makeText(getApplication(),"删除成功！"+c.getCount(), 0).show();
		weathernum = c.getCount();
		if(null != c && c.moveToFirst()){
			for(c.moveToFirst();!c.isAfterLast();c.moveToNext())  {  
//			sca = new SimpleCursorAdapter(this,
//					R.layout.share_list, c, new String[] { Meta.TableMeta._CITY,
//							Meta.TableMeta._WEATHER, Meta.TableMeta._TEMP },
//					new int[] { R.id.city, R.id.weather, R.id.temp });
				city = c.getString(c.getColumnIndex(Meta.TableMeta._CITY));  
				weather = c.getString(c.getColumnIndex(Meta.TableMeta._WEATHER));  
				temp = c.getString(c.getColumnIndex(Meta.TableMeta._TEMP)); 
				id = c.getString(c.getColumnIndex(Meta.TableMeta._ID));
				Map<String,String> item = new HashMap<String, String>();
				item.put("city", city);		
				item.put("weather", weather);		
				item.put("temp", temp);	
				item.put("id", id);	
				data.add(item);
				
			}
			
		}else{
			String url ="http://www.weather.com.cn/data/cityinfo/101010100.html";
			new TextAsyncTask().execute(url);
			flag = true;
		}
			Log.i("log", "-------------------------data="+data);
		c.close();	
		myadapter = new MyAdapter(this,data);
		findshare.setAdapter(myadapter);
		
		findshare.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int arg2, long id) {
				Intent intent = new Intent(Weatheradd.this, Weather.class);
				weatheradd_id = arg2;
				startActivity(intent);
			}
		});
		
		findshare.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					final int poisition, final long arg3) {
				Log.i("log", "-------------------------arg3="+arg3);
				new AlertDialog.Builder(Weatheradd.this)
						.setTitle("温馨提示")
						.setMessage("确定删除\"城市\"?")
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

										if(((Map)data.get(poisition)).get("city").toString().equals("自动定位")){
											Toast.makeText(getApplication(),
													"自动定位不能删除", 0).show();
										}else{
											int n = getContentResolver().delete(
													Meta.CONTENT_URI, "_id=?",
													new String[] { ((Map)data.get(poisition)).get("id").toString() });
											if (n > 0) {
												Toast.makeText(getApplication(),
														"删除成功！", 0).show();
											}
											myadapter.notifyDataSetChanged();
											Intent intent = new Intent(
													Weatheradd.this,
													Weatheradd.class);
											startActivity(intent);
											Weatheradd.this.finish();
										}
									}
								}).show();

				return true;
			}
		});
		more_back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				if(flag == false){
					Intent intent = new Intent(Weatheradd.this, Weather.class);
					startActivity(intent);
					finish();
//				}else{
//					Intent intent = new Intent(Weatheradd.this, MainActivity3.class);
//					startActivity(intent);
//					finish();
//				}
				
			}
		});
		cityadd.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Weatheradd.this, Citylist.class);
				startActivity(intent);
				finish();
			}
		});
		init();
	}

	public void init() {
		iamgebutton1 = (Button) findViewById(R.id.iamgebutton1);
		iamgebutton1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Weatheradd.this, MainActivity0.class);
				startActivity(intent);
			}
		});
		iamgebutton2 = (Button) findViewById(R.id.iamgebutton2);
		iamgebutton2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Weatheradd.this, MainActivity2.class);
				startActivity(intent);
			}
		});
		iamgebutton3 = (Button) findViewById(R.id.iamgebutton3);
		iamgebutton3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Weatheradd.this, MainActivity3.class);
				startActivity(intent);
			}
		});
		iamgebutton4 = (Button) findViewById(R.id.iamgebutton4);
		iamgebutton4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Weatheradd.this, MainActivity4.class);
				startActivity(intent);
			}
		});
		iamgebutton5 = (Button) findViewById(R.id.iamgebutton5);
		iamgebutton5.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Weatheradd.this, MainActivity1.class);
				startActivity(intent);
			}
		});
	}
	private class MyAdapter extends BaseAdapter {
		private Context xontext;
		private List list;
		
		
		public MyAdapter(){
			
		}

		public MyAdapter(Context xontext, List list) {
			super();
			this.xontext = xontext;
			this.list = list;    
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return data.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = View.inflate(getApplicationContext(),
						R.layout.share_list, null);
				holder.city=(TextView)convertView.findViewById(R.id.city);
				holder.temp=(TextView)convertView.findViewById(R.id.temp);
				holder.weather =(TextView)convertView.findViewById(R.id.weather);
				
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			positionid = position;
			Map map = (Map)list.get(position);
			holder.city.setText(map.get("city").toString());
			String image  = map.get("temp").toString();
			
			if(image.indexOf("晴")>=0){
				holder.temp.setBackgroundResource(R.drawable.q0);
			}
			else if(image.indexOf("云")>=0){
				holder.temp.setBackgroundResource(R.drawable.q4);
			}
			else if(image.indexOf("雪")>=0){
				holder.temp.setBackgroundResource(R.drawable.q1);
			}
			else if(image.indexOf("阴")>=0){
				holder.temp.setBackgroundResource(R.drawable.q2);
			}
			else if(image.indexOf("雨")>=0){
				holder.temp.setBackgroundResource(R.drawable.q3);
			}
			holder.weather.setText(map.get("weather").toString());
			
			return convertView;
		}
	}
	static class ViewHolder {
		TextView city;
		TextView weather;
		TextView temp;
	}
	@Override
	protected void onPause() {
		c.close();
		
		super.onPause();
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
				Intent intent = new Intent(Weatheradd.this, Weather.class);
				startActivity(intent);
				finish();
		}

		return super.onKeyDown(keyCode, event);
	}	
	class TextAsyncTask extends AsyncTask<String, Integer, String> {
		@Override
		protected String doInBackground(String... params) {
			return getResultByPost(params[0]);
		}
		
		@Override
		protected void onPostExecute(String result) {
			if (result == null) {
				Toast.makeText(Weatheradd.this, "网络加载失败..", 0).show();
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
					values.put(Meta.TableMeta._CITY, city);
					values.put(Meta.TableMeta._WEATHER, temp1+"~"+temp2 );
					values.put(Meta.TableMeta._TEMP, weather);
					Uri uri = resolver.insert(Meta.CONTENT_URI, values);
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
	private String getResultByPost(String url) {
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
	
}
