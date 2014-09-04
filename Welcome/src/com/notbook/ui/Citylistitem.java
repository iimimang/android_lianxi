package com.notbook.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notbook.R;
import com.notbook.db.Meta;
import com.notbook.entity.CityClass;
import com.notbook.ui.Citylist.MyAdapter;
import com.notbook.ui.Citylist.TextAsyncTask;
import com.notbook.ui.Citylist.ViewHolder;
import com.notbook.weather_db.DBManager;

public class Citylistitem extends BaseActivity {

	private TextView province;
	private String taiwai[] = { "台北", "高雄" };
	private String taiwaicode[] = { "101340101", "101340201" };
	private ListView citylistitem;
	private String cityname = "";
	public static int citycode = 0;
	ContentResolver resolver;
	public DBManager dbHelper;
	private SQLiteDatabase database;
	public static ArrayList<CityClass> CITY;
	public static ArrayList<CityClass> city;
	public static ArrayList<CityClass> code;
	
	private String provincetext = "";
	private String id = "";
	private String codeid = "";
	private String name = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.citylistitem);
		province = (TextView) findViewById(R.id.province);
		citylistitem = (ListView) findViewById(R.id.citylistitem);
		findViewById(R.id.one).setBackgroundResource(
				background_photobg[num - 1]);
		resolver = getContentResolver();

		Bundle bundle = getIntent().getExtras();
		provincetext = bundle.getString("province");
		province.setText(provincetext);
		MyAdapter ma = new MyAdapter();
	/*	if ("台湾".equals(provincetext)) {
			citylistitem.setAdapter(ma);
		}*/
		dbHelper = new DBManager(this);
		dbHelper.openDatabase();
		dbHelper.closeDatabase();
		database = SQLiteDatabase.openOrCreateDatabase(DBManager.DB_PATH +"/"+ DBManager.DB_NAME,null);
		
		city = getCity1();
		for(int i= 0 ;i<city.size();i++){
			if(city.get(i).getName().equals(provincetext)){
				id = city.get(i).get_id();
			}
		}
		CITY = getCity();
		citylistitem.setAdapter(ma);
		/*for(int i= 0 ;i<CITY.size();i++){
			CityClass city_1 = new CityClass();
			CityClass city_2 = new CityClass();
			codeid =  CITY.get(i).getCity_num();
			city_1.setCity_num(codeid);
			city.add(city_1);
			name =  CITY.get(i).getName();
			city_2.setName(name);
			code.add(city_2);
		}
		Log.i("log", "-------------city="+city );
		Log.i("log", "-------------code="+code );*/
		database.close();
	}
	private ArrayList<CityClass> getCity() {
        
        Cursor cur = database.rawQuery("SELECT name,_id,city_num,province_id FROM citys where province_id ="+(Integer.valueOf(id)-1),null);
         
        if(cur !=null) {
            int NUM_CITY = cur.getCount();
            ArrayList<CityClass> taxicity =new ArrayList<CityClass>(NUM_CITY);
            if(cur.moveToFirst()) {
                do{
                	String id = cur.getString(cur.getColumnIndex("_id"));
                    String name = cur.getString(cur.getColumnIndex("name"));
                    String city_num = cur.getString(cur.getColumnIndex("city_num"));
                    String province_id = cur.getString(cur.getColumnIndex("province_id"));
                    CityClass city = new CityClass();
                    System.out.println(name); //额外添加一句，把select到的信息输出到Logcat
                    city.setCity_num(city_num);
                    city.setName(name);
                    city.set_id(id);
                    city.setProvince_id(province_id);
                    taxicity.add(city);
                    database.close();
                }while(cur.moveToNext());
            }
            return taxicity;
        }else{
            return null;
        }
        
    }
	
	private ArrayList<CityClass> getCity1() {
        
        Cursor cur = database.rawQuery("SELECT name,_id FROM provinces",null);
         
        if(cur !=null) {
            int NUM_CITY = cur.getCount();
            ArrayList<CityClass> taxicity =new ArrayList<CityClass>(NUM_CITY);
            if(cur.moveToFirst()) {
                do{
                	String id = cur.getString(cur.getColumnIndex("_id"));
                    String name = cur.getString(cur.getColumnIndex("name"));
                    CityClass city = new CityClass();
                    System.out.println(name+"--"+id); //额外添加一句，把select到的信息输出到Logcat
                    city.setName(name);
                    city.set_id(id);
                    taxicity.add(city);
                }while(cur.moveToNext());
            }
            return taxicity;
        }else{
            return null;
        }
    }
	private ArrayList<CityClass> getCity2() {
        
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
                    CityClass city = new CityClass();
//                    System.out.println(name); //额外添加一句，把select到的信息输出到Logcat
                    city.setCity_num(city_num);
                    city.setName(name);
                    city.set_id(id);
                    city.setProvince_id(province_id);
                    taxicity.add(city);
                }while(cur.moveToNext());
            }
            return taxicity;
        }else{
            return null;
        }
    }
	public class MyAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			return CITY.size();
		}

		@Override
		public Object getItem(int position) {
			return CITY.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {

			ViewHolder holder;

			if (convertView == null) {
				convertView = LayoutInflater.from(Citylistitem.this).inflate(
						R.layout.zhaolvshi_diqu, null);
				holder = new ViewHolder();
				holder.name = (TextView) convertView
						.findViewById(R.id.diqu_item);
				holder.item = (RelativeLayout) convertView
						.findViewById(R.id.diqu_layout);
				convertView.setTag(holder);

			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			cityname = CITY.get(position).getName();
			
			citycode = Integer.valueOf(CITY.get(position).getCity_num());
			holder.name.setText(cityname);
			holder.item.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					if(Weatheradd.weathernum>4){
						Toast.makeText(Citylistitem.this, "最多5个城市", 0).show();
						return ;
					}else{
						String url = "http://www.weather.com.cn/data/cityinfo/"
								+ CITY.get(position).getCity_num() + ".html";
						new TextAsyncTask().execute(url);
					}
					
					
				}
			});
			return convertView;  
		}
	}

	static class ViewHolder {
		private TextView name;
		private RelativeLayout item;
	}

	class TextAsyncTask extends AsyncTask<String, Integer, String> {
		@Override
		protected String doInBackground(String... params) {
			return getResultByPost(params[0]);
		}
		@Override
		protected void onPreExecute() {
			dia = new ProgressDialog(Citylistitem.this);
			dia.setMessage("选择城市中...");
			dia.show();
			dia.setCanceledOnTouchOutside(false);
			super.onPreExecute();
		}
		@Override
		protected void onPostExecute(String result) {
			if (result == null) {
				Toast.makeText(Citylistitem.this, "网络加载失败..", 0).show();
				dia.dismiss();
			} else {
				dia.dismiss();
				Log.i("log", "-------------result=" + result);
				JSONObject object, object1;
				try {
					object = new JSONObject(result);
					String weatherinfo = object.getString("weatherinfo");
					Log.i("log", "-------------weatherinfo=" + weatherinfo);
					object1 = new JSONObject(weatherinfo);
					String city = object1.getString("city");
					String temp1 = object1.getString("temp1");
					String temp2 = object1.getString("temp2");
					String weather = object1.getString("weather");
					List<String> list = new ArrayList<String>();
					Cursor c = resolver.query(Meta.CONTENT_URI, null, null,
							null, null);
					if (c != null) {
						for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
							int nameColumn = c
									.getColumnIndex(com.notbook.db.Meta.TableMeta._CITY);
							String citynum = c.getString(nameColumn);
							list.add(citynum);
						}
					}
					for (int i = 0; i < list.size(); i++) {
						if (list.get(i).toString().equals(city)) {
							Toast.makeText(getApplicationContext(), "已添加", 0)
									.show();
							return;
						}
					}
					ContentValues values = new ContentValues();
					values.put(Meta.TableMeta._CITY, city);
					values.put(Meta.TableMeta._WEATHER, temp1 + "~" + temp2);
					values.put(Meta.TableMeta._TEMP, weather);
					Uri uri = resolver.insert(Meta.CONTENT_URI, values);
					if (uri != null) {
						Log.i("log", "添加成功！");
						Intent intent = new Intent(Citylistitem.this,
								Weatheradd.class);
						intent.putExtra("province", city);
						startActivity(intent);
						Citylistitem.this.finish();
						Toast.makeText(getApplicationContext(), "添加成功", 0)
								.show();
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
