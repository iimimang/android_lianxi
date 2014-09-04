package com.notbook.ui;

import java.io.IOException;
import java.lang.reflect.Type;
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

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notbook.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.notbook.entity.Thing;
import com.notbook.ui.ChuanYu.TextAsyncTask;

public class Hudianlist extends BaseActivity {

	private Button iamgebutton1,iamgebutton2,iamgebutton3,iamgebutton4,iamgebutton5;
	private Button more_back ,add,chuanyu,chuanyu01;
	private RelativeLayout chuanyudetail,chuanyudetail1;
	static ContentResolver resolver ;
	private ListView findshare;
	private MyAdapter myadapter = null;
	private Cursor c = null;
	private ProgressDialog dia;
	List<Map<String,String>> data = new ArrayList<Map<String,String>>();
	private List<Thing> things = new ArrayList<Thing>();
	public static String leixing="",time="",mtime="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hudianlist);
		findViewById(R.id.one).setBackgroundResource(background_photobg[num - 1]);
		findViewById(R.id.one2).setBackgroundResource(background_photobg1[num - 1]);
		findViewById(R.id.one1).setBackgroundResource(background_photobg1[num - 1]);
		more_back = (Button)findViewById(R.id.more_back);
		add = (Button)findViewById(R.id.add);
		//取消我发送的
		new TextAsyncTask3().execute(com.notbook.app.Info.uri+"/cuanyuLookAction.php?uphone="+Login.uphone);
		resolver = getContentResolver();
		findshare = (ListView) findViewById(R.id.findshare11);
		
//		c = resolver.query(Meta.CONTENT_URI, null, null, null, null);
		/*SimpleCursorAdapter sca = new SimpleCursorAdapter(this,
				R.layout.share_list1, c, new String[] {
						Meta.TableMeta._LEIXING, Meta.TableMeta._TIME },
				new int[] { R.id.leixing, R.id.time});
		findshare.setAdapter(sca);*/
//		for(c.moveToFirst();!c.isAfterLast();c.moveToNext())  {  
//			int nameColumn = c.getColumnIndex(com.notbook.things.Meta.TableMeta._LEIXING);  
//			String leixing = c.getString(nameColumn);  
//			int nameColumn1 = c.getColumnIndex(com.notbook.things.Meta.TableMeta._TIME);  
//			String time = c.getString(nameColumn1);  
//			Log.i("log", "-----------leixing："+leixing);
//			Map<String,String> item = new HashMap<String, String>();
//			item.put("leixing", leixing);		
//			item.put("time", time);		
//			data.add(item);
//			Log.i("log", "-----------data："+data);
//		} 
		
		new TextAsyncTask().execute(com.notbook.app.Info.uri+"/mychuanyuAction.php?uphone="+Login.uphone);
//		myadapter = new MyAdapter(this,data);
		
		
		more_back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Hudianlist.this, MainActivity2.class);
				startActivity(intent);
				finish();
			}
		});
		add.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Hudianlist.this, Eventadd.class);
				startActivity(intent);
			}
		});
		init();
	}
	public void init(){
		iamgebutton1 = (Button) findViewById(R.id.iamgebutton1);
		iamgebutton1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Hudianlist.this, MainActivity0.class);
				startActivity(intent);
			}
		});
		iamgebutton2 = (Button) findViewById(R.id.iamgebutton2);
		iamgebutton2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Hudianlist.this, MainActivity2.class);
				startActivity(intent);
			}
		});
		iamgebutton3 = (Button) findViewById(R.id.iamgebutton3);
		iamgebutton3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Hudianlist.this, MainActivity3.class);
				startActivity(intent);
			}
		});
		iamgebutton4 = (Button) findViewById(R.id.iamgebutton4);
		iamgebutton4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Hudianlist.this, MainActivity4.class);
				startActivity(intent);
			}
		});
		iamgebutton5 = (Button) findViewById(R.id.iamgebutton5);
		iamgebutton5.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Hudianlist.this, MainActivity1.class);
				startActivity(intent);
			}
		});
	}
	class TextAsyncTask extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {
			return getResultByPost(params[0]);
		}
		@Override
		protected void onPreExecute() {
			dia = new ProgressDialog(Hudianlist.this);
			dia.setMessage("正在加载...");
			dia.show();
			dia.setCanceledOnTouchOutside(false);
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(String result) {
//			onLoad();
			if (result == null) {
				Toast.makeText(Hudianlist.this, "网络加载失败..", 0).show();
				dia.dismiss();
			} else {
				dia.dismiss();
				Log.d("log", "---------------------result="+result);
				JSONObject object;
				try {
					object = new JSONObject(result);
					int id = object.getInt("responsecode");  
						if(id == 0){
							Toast.makeText(Hudianlist.this, "没有数据", 0).show();
						}else {
							
							String share = object.optString("result"); 
							if(null != share && !share.equals("")){
								Gson gson = new Gson();
								Type type = new TypeToken<List<Thing>>() {}.getType();
								things = gson.fromJson(share, type);
								Log.d("log", "---------------------things="+things);
								if(null != things && !things.equals("")){  
									ChuanyuListAdapter hlada = new ChuanyuListAdapter(Hudianlist.this, things);
									findshare.setAdapter(hlada);
									findshare.setOnItemClickListener(new AdapterView.OnItemClickListener() {

										@Override
										public void onItemClick(AdapterView<?> arg0, View arg1,
												int arg2, long id) {
											Thing hotel = (Thing) things.get(arg2);
											Intent intent = new Intent(Hudianlist.this, Chuanyudetail.class);
											intent.putExtra("leixing", hotel.getCtype());//类型
											intent.putExtra("time", hotel.getRingtime());//时间提醒
											intent.putExtra("mtime", hotel.getAdvtime());//提前时间
											intent.putExtra("recname", hotel.getRecname());//接收人
											intent.putExtra("cname", hotel.getCname());//主题
											intent.putExtra("text", hotel.getText());//内容
											
											intent.putExtra("ponsuser", hotel.getPonsuser());//响应人
											intent.putExtra("recuser", hotel.getRecuser());//总人数
											
											if(null!= hotel.getCphoto()&&!hotel.getCphoto().equals("")){
												intent.putExtra("cphoto", hotel.getCphoto());//图片
											}else{
												intent.putExtra("cphoto", "");//图片
											}
											if(null!= hotel.getChuanyupath()&&!hotel.getChuanyupath().equals("")){
												intent.putExtra("chuanyupath", hotel.getChuanyupath());//语音
											}else{
												intent.putExtra("chuanyupath", "");//语音
											}
											intent.putExtra("first", hotel.getFirst());//优先级
											intent.putExtra("tone", hotel.getTone());//铃音
											intent.putExtra("id", hotel.getId());//id
											startActivity(intent);
										}
									});
								}else{
									Toast.makeText(Hudianlist.this, "没有数据", 0).show();
								}
								
							}
							
						}   
				    
					
				} catch (JSONException e) {
				e.printStackTrace();
				}
				
				
			}
			super.onPostExecute(result);
		}
	}
	class TextAsyncTask3 extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {
			return getResultByPost(params[0]);
		}

		@Override
		protected void onPostExecute(String result) {
			if (result == null) {
				
			} else {
				Log.d("log", "---------------------result="+result);
				JSONObject object,object1;
				try {
					object = new JSONObject(result);
					int id = object.getInt("responsecode");  
						if(id == 10){
							
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
			return c.getCount();
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
						R.layout.share_list1, null);
				holder.time=(TextView)convertView.findViewById(R.id.time);
				holder.leixing =(TextView)convertView.findViewById(R.id.leixing);
				holder.chuanyu03=(Button)convertView.findViewById(R.id.chuanyu03);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			Map map = (Map)list.get(position);
			if(null!=map.get("leixing").toString()&&!map.get("leixing").toString().equals("null")){
				holder.leixing.setText(map.get("leixing").toString());
			}
			holder.time.setText(map.get("time").toString());
			holder.chuanyu03.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if(Login.NUM==0){
						Intent intent = new Intent(Hudianlist.this, Login.class);
						startActivity(intent);
						Toast.makeText(Hudianlist.this, "请您登录", 0).show();
					}else{
						Intent intent = new Intent(Hudianlist.this, Friend.class);
						startActivity(intent);
					}
				}
			});
			return convertView;
		}
	}
	static class ViewHolder {
		TextView leixing;
		TextView time;
		Button chuanyu03;
	}
	
	
	
	private class ChuanyuListAdapter extends BaseAdapter {
		private Context xontext;
		private List<Thing> list;
		
		

		public ChuanyuListAdapter(Context xontext, List<Thing> list) {
			super();
			this.xontext = xontext;
			this.list = list;    
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
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
						R.layout.share_list5, null);
				holder.time=(TextView)convertView.findViewById(R.id.time);
				holder.leixing =(TextView)convertView.findViewById(R.id.leixing);
//				holder.chuanyu03=(Button)convertView.findViewById(R.id.chuanyu03);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.leixing.setText(list.get(position).getCtype());
			holder.time.setText(list.get(position).getAdvtime());
			
			
			leixing = list.get(position).getCtype();
			time = list.get(position).getUptime();
			mtime = list.get(position).getAdvtime();
			
//			holder.chuanyu03.setOnClickListener(new OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					if(Login.NUM==0){
//						Intent intent = new Intent(Hudianlist.this, Login.class);
//						startActivity(intent);
//					}else{
//						Intent intent = new Intent(Hudianlist.this, Friend.class);
//						startActivity(intent);
//					}
//				}
//			});
			return convertView;
		}
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			Intent intent = new Intent(Hudianlist.this, MainActivity2.class);
			startActivity(intent);
			finish();
		}

		return super.onKeyDown(keyCode, event);
	}
}
