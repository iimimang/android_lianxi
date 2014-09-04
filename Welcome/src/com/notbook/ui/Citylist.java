package com.notbook.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notbook.R;
import com.notbook.db.Meta;


public class Citylist extends BaseActivity {

	private Button iamgebutton1,iamgebutton2,iamgebutton3,iamgebutton4,iamgebutton5,more_back;
	private Button inland,foreign;
	private LinearLayout guonei,guowai,dingweibeijing;
	private ListView list_diqu,diqu_list1;
	ContentResolver resolver;
	private String cityname = "";
	private static String province[] = new String[] { "北京", "上海", "天津", "重庆","河北",
			"山西", "内蒙古", "辽宁", "吉林", "黑龙江", "江苏", "浙江", "安徽", "福建", "江西", "山东",
			"河南", "湖北", "湖南", "广东", "广西", "海南", "四川", "贵州", "云南", "西藏", "陕西",
			"甘肃", "青海", "宁夏", "新疆", "香港", "澳门", "台湾"};
	private String province1[] = new String[] { "河北",
			"山西", "内蒙古", "辽宁", "吉林", "黑龙江", "江苏", "浙江", "安徽", "福建", "江西", "山东",
			"河南", "湖北", "湖南", "广东", "广西", "海南", "四川", "贵州", "云南", "西藏", "陕西",
			"甘肃", "青海", "宁夏", "新疆", "香港", "澳门", "台湾" };
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.citylist);
		findViewById(R.id.one).setBackgroundResource(background_photobg[num - 1]);
		findViewById(R.id.one2).setBackgroundResource(background_photobg1[num - 1]);
		findViewById(R.id.one1).setBackgroundResource(background_photobg1[num - 1]);
		
		more_back = (Button)findViewById(R.id.more_back);
		inland = (Button)findViewById(R.id.inland);
		foreign = (Button)findViewById(R.id.foreign);
		guonei = (LinearLayout)findViewById(R.id.guonei);
		guowai = (LinearLayout)findViewById(R.id.guowai);
		dingweibeijing = (LinearLayout)findViewById(R.id.dingweibeijing);
		list_diqu = (ListView) findViewById(R.id.diqu_list);
		diqu_list1 = (ListView) findViewById(R.id.diqu_list1);
		list_diqu.setAdapter(new MyAdapter());
		diqu_list1.setAdapter(new MyAdapter1());
		resolver = getContentResolver();   
		
		
		
		more_back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Citylist.this, Weatheradd.class);
				startActivity(intent);
				finish();
			}
		});
		inland.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				guonei.setVisibility(View.VISIBLE);
				guowai.setVisibility(View.GONE);
			}
		});
		
		dingweibeijing.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String url ="http://www.weather.com.cn/data/cityinfo/101010100.html";
				new TextAsyncTask().execute(url);
				
				
			}
		});
		foreign.setOnClickListener(new View.OnClickListener() {
	
			@Override
			public void onClick(View v) {
				guonei.setVisibility(View.GONE);
				guowai.setVisibility(View.VISIBLE);
			}
		});
		init();
	}
	// 自定义适配器---对应地区
		public class MyAdapter extends BaseAdapter {
			@Override
			public int getCount() {
				return province.length;
			}

			@Override
			public Object getItem(int position) {
				return province[position];
			}

			@Override
			public long getItemId(int position) {
				return position;
			}

			@Override
			public View getView(final int position, View convertView,ViewGroup parent) {

				ViewHolder holder;
						
				if(convertView == null) {
					convertView = LayoutInflater.from(Citylist.this).inflate(R.layout.zhaolvshi_diqu1, null);
					holder = new ViewHolder();
					holder.name = (TextView) convertView.findViewById(R.id.diqu_item);
					holder.item = (RelativeLayout) convertView.findViewById(R.id.diqu_layout);
						
					convertView.setTag(holder);
							
				} else {
					holder = (ViewHolder)convertView.getTag();
				}  
				holder.name.setText(province[position]);
				cityname = province[position];	
				
				holder.item.setOnClickListener(new OnClickListener() {
						
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if(Weatheradd.weathernum>4){
							Toast.makeText(Citylist.this, "最多5个城市", 0).show();
							return ;
						}else{
							for(int i=0;i<province.length;i++){
								if(province[position].equals("上海")){
									String url ="http://www.weather.com.cn/data/cityinfo/101020100.html";
									new TextAsyncTask().execute(url);
									return;
								}
								else if(province[position].equals("天津")){
									String url ="http://www.weather.com.cn/data/cityinfo/101030100.html";
									new TextAsyncTask().execute(url);
									return;
								}
								
								else if(province[position].equals("重庆")){
									String url ="http://www.weather.com.cn/data/cityinfo/101040100.html";
									new TextAsyncTask().execute(url);
									return;
								}
								else if(province[position].equals("北京")){
									String url ="http://www.weather.com.cn/data/cityinfo/101010100.html";
									new TextAsyncTask().execute(url);
									return;
								}
								else if(province[position].equals("香港")){
									String url ="http://www.weather.com.cn/data/cityinfo/101320101.html";
									new TextAsyncTask().execute(url);
									return;
								}
								else if(province[position].equals("澳门")){
									String url ="http://www.weather.com.cn/data/cityinfo/101330101.html";
									new TextAsyncTask().execute(url);
									return;
								}else{
									Intent intent = new Intent(Citylist.this, Citylistitem.class);
									intent.putExtra("province", province[position]);
									startActivity(intent);
									return;
								}
							}
						}
						
						
					}
				});	
					
				return convertView; 
			}
		}
		public class MyAdapter1 extends BaseAdapter {
			@Override
			public int getCount() {
				return province1.length;
			}

			@Override
			public Object getItem(int position) {
				return province1[position];
			}

			@Override
			public long getItemId(int position) {
				return position;
			}

			@Override
			public View getView(final int position, View convertView,ViewGroup parent) {

				ViewHolder holder;
						
				if(convertView == null) {
					convertView = LayoutInflater.from(Citylist.this).inflate(R.layout.zhaolvshi_diqu1, null);
					holder = new ViewHolder();
					holder.name = (TextView) convertView.findViewById(R.id.diqu_item);
					holder.item = (RelativeLayout) convertView.findViewById(R.id.diqu_layout);
						
					convertView.setTag(holder);
							
				} else {
					holder = (ViewHolder)convertView.getTag();
				}  
				holder.name.setText(province1[position]);
				cityname = province1[position];	
				holder.item.setOnClickListener(new OnClickListener() {
						
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						for(int i=0;i<province1.length;i++){
							if(province1[position].equals("台湾")){
								String url ="http://www.weather.com.cn/data/cityinfo/101010100.html";
								new TextAsyncTask().execute(url);
								return;
							}
						}
						
						
					}
				});	
					
				return convertView; 
			}
		}
		class TextAsyncTask extends AsyncTask<String, Integer, String> {
			@Override
			protected String doInBackground(String... params) {
				return getResultByPost(params[0]);
			}
			@Override
			protected void onPreExecute() {
				dia = new ProgressDialog(Citylist.this);
				dia.setMessage("选择城市中...");
				dia.show();
				dia.setCanceledOnTouchOutside(false);
				super.onPreExecute();
			}
			@Override
			protected void onPostExecute(String result) {
				if (result == null) {
					Toast.makeText(Citylist.this, "网络加载失败..", 0).show();
					dia.dismiss();
				} else {
					dia.dismiss();
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
						List<String> list = new ArrayList<String>();
						Cursor c = resolver.query(Meta.CONTENT_URI, null, null, null,
								null);
						if(c!=null){
							for(c.moveToFirst();!c.isAfterLast();c.moveToNext())  {  
								int nameColumn = c.getColumnIndex(com.notbook.db.Meta.TableMeta._CITY);  
								String citynum = c.getString(nameColumn);
								list.add(citynum);
							}
						}
//						Toast.makeText(getApplicationContext(), "已添加"+list, 0).show();
						for(int i = 0;i<list.size();i++){
							if(list.get(i).toString().equals(city)){
								Toast.makeText(getApplicationContext(), "已添加", 0).show();
								return;
							}
						}
						ContentValues values = new ContentValues();
						values.put(Meta.TableMeta._CITY, city);
						values.put(Meta.TableMeta._WEATHER, temp1+"~"+temp2 );
						values.put(Meta.TableMeta._TEMP, weather);
						Uri uri = resolver.insert(Meta.CONTENT_URI, values);
						if(uri != null){
							Log.i("log", "添加成功！");
							Intent intent = new Intent(Citylist.this, Weatheradd.class);
							intent.putExtra("province", cityname);
							startActivity(intent);
							Toast.makeText(getApplicationContext(), "添加成功", 0).show();
							Citylist.this.finish();
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
	public void init(){
		iamgebutton1 = (Button) findViewById(R.id.iamgebutton1);
		iamgebutton1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Citylist.this, MainActivity0.class);
				startActivity(intent);
			}
		});
		iamgebutton2 = (Button) findViewById(R.id.iamgebutton2);
		iamgebutton2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Citylist.this, MainActivity2.class);
				startActivity(intent);
			}
		});
		iamgebutton3 = (Button) findViewById(R.id.iamgebutton3);
		iamgebutton3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Citylist.this, MainActivity3.class);
				startActivity(intent);
			}
		});
		iamgebutton4 = (Button) findViewById(R.id.iamgebutton4);
		iamgebutton4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Citylist.this, MainActivity4.class);
				startActivity(intent);
			}
		});
		iamgebutton5 = (Button) findViewById(R.id.iamgebutton5);
		iamgebutton5.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Citylist.this, MainActivity1.class);
				startActivity(intent);
			}
		});
	}
	static class ViewHolder {
		private TextView name,name2;
		private LinearLayout item2;
		private RelativeLayout item;
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
				Intent intent = new Intent(Citylist.this, Weatheradd.class);
				startActivity(intent);
				finish();
		}

		return super.onKeyDown(keyCode, event);
	}	
}
