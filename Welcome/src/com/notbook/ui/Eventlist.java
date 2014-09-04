package com.notbook.ui;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
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
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
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
import com.notbook.app.AppManager;
import com.notbook.app.CustomMultiPartEntity;
import com.notbook.app.Info;
import com.notbook.app.CustomMultiPartEntity.ProgressListener;
import com.notbook.calendar.CalendarView;
import com.notbook.things.Meta;
import com.notbook.things.Thing;
import com.notbook.ui.More.TextAsyncTask;

public class Eventlist extends BaseActivity {

	private Button iamgebutton1, iamgebutton2, iamgebutton3, iamgebutton4,
			iamgebutton5, eventadd, celender;
	private RelativeLayout eventdetail, eventdetail1, eventdetail2,
			eventdetail3;
	private Button chuanyu, chuanyu01, chuanyu02, chuanyu03;
	static ContentResolver resolver;
	private ListView findshare;
	private MyAdapter myadapter = null;
	private Cursor c = null;
	public static String leixing = "", time = "", mtime = "", zhuti = "",
			jishi = "", youxian = "", lingyin = "", imageurl = "", luyin = "",
			id = "";
	List<Map<String, String>> data = new ArrayList<Map<String, String>>();
	List<Map<String, String>> data1 = new ArrayList<Map<String, String>>();
	public static String Eventlistid = "";
	private Button more_back;// 同步
	private String firsttime = "";// 时间戳
	private String url = "";
	private String s1 = "";
	private static int id_num = 0;
	String map = "";
	String re_str = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.eventlist);
		findViewById(R.id.one).setBackgroundResource(
				background_photobg[num - 1]);

		findViewById(R.id.one1).setBackgroundResource(
				background_photobg1[num - 1]);
		eventadd = (Button) findViewById(R.id.eventadd);
		celender = (Button) findViewById(R.id.celender);
		more_back = (Button) findViewById(R.id.more_back);
		resolver = getContentResolver();
		findshare = (ListView) findViewById(R.id.findshare11);
		Eventdetail.id = "";
		Calendar cal = Calendar.getInstance();
		long timeuique = cal.getTimeInMillis();
		
		c = resolver.query(Meta.CONTENT_URI, null, "_time_uique>=?", new String[] { String.valueOf(timeuique) },null);
		if (null != c && c.moveToFirst()) {
			for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
				int nameColumn = c
						.getColumnIndex(com.notbook.things.Meta.TableMeta._LEIXING);
				leixing = c.getString(nameColumn);
				int nameColumn1 = c
						.getColumnIndex(com.notbook.things.Meta.TableMeta._TIME);
				time = c.getString(nameColumn1);
				mtime = c
						.getString(c
								.getColumnIndex(com.notbook.things.Meta.TableMeta._MTIME));
				zhuti = c
						.getString(c
								.getColumnIndex(com.notbook.things.Meta.TableMeta._ZHUTI));
				jishi = c
						.getString(c
								.getColumnIndex(com.notbook.things.Meta.TableMeta._JISHI));
				youxian = c
						.getString(c
								.getColumnIndex(com.notbook.things.Meta.TableMeta._YOUXIAN));
				lingyin = c
						.getString(c
								.getColumnIndex(com.notbook.things.Meta.TableMeta._LINGYIN));
				luyin = c
						.getString(c
								.getColumnIndex(com.notbook.things.Meta.TableMeta._LUYIN));
				id = c.getString(c
						.getColumnIndex(com.notbook.things.Meta.TableMeta._ID));
				firsttime = c
						.getString(c  
								.getColumnIndex(com.notbook.things.Meta.TableMeta._TIMEUIQUE));
				byte[] picData = c.getBlob(c
						.getColumnIndex(Meta.TableMeta._IMAGE));
				if (picData != null) {
					imageurl = c
							.getString(c
									.getColumnIndex(com.notbook.things.Meta.TableMeta._IMAGE));
				}

			    re_str = getTime(time);
				Log.i("log", "-----------leixing：" + leixing);
				Log.i("log", "-----------re_str：" + re_str);
				Map<String, String> item = new HashMap<String, String>();
				item.put("leixing", leixing);
				item.put("time", time);
				item.put("mtime", mtime);
				item.put("zhuti", zhuti);
				item.put("jishi", jishi);
				item.put("youxian", youxian);
				item.put("lingyin", lingyin);
				item.put("imageurl", imageurl);
				item.put("luyin", luyin);
				item.put("id", id);
				item.put("firsttime", firsttime);
				data.add(item);
			}
			Log.i("log", "-----------data：" + data);
		}
		
		myadapter = new MyAdapter(this, data);

		/*
		 * SimpleCursorAdapter sca = new SimpleCursorAdapter(this,
		 * R.layout.share_list1, c, new String[] { Meta.TableMeta._LEIXING,
		 * Meta.TableMeta._TIME }, new int[] { R.id.leixing, R.id.time});
		 */
		findshare.setAdapter(myadapter);
		findshare.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long id) {

				Intent it = new Intent(Eventlist.this, Eventdetail.class);
				it.putExtra("id", data.get(arg2).get("id").toString());
				Eventlistid = (id + 1) + "";
				it.putExtra("list", "false");
				it.putExtra("day", "");
				startActivity(it);
			}
		});
		eventadd.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Eventlist.this, Eventadd.class);
				startActivity(intent);
				Eventdetail.id = "";
				CalendarView.selectedDay = "";
			}
		});
		celender.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Eventlist.this, MainActivity0.class);
				startActivity(intent);
			}
		});
		// 同步
		more_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				StringBuilder sb = new StringBuilder();
				
				for (int i = 0; i < data.size(); i++) {
					map = data.get(i).get("firsttime").toString(); 
					map = sb.append(map).append(",").toString();

				}	
				
				if(null != map&&!map.equals("")){
					map = map.substring(0, map.lastIndexOf(","));
				}
				if (Login.NUM == 0) {
					Toast.makeText(getApplicationContext(), "请先登录！", 0).show();
					Intent intent = new Intent(Eventlist.this, Login.class);
					intent.putExtra("name", "login_friend2");
					startActivity(intent);
				} else{
					new AlertDialog.Builder(Eventlist.this)
					.setTitle("同步更新")
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
									String url = com.notbook.app.Info.uri
											+ "/synchroChuanyuAction.php?uphone="
											+ Login.uphone + "&uptime=" + map+"&type="+id_num+"";
									new TextAsyncTask().execute(url);
									id_num = 1;
								}
							}).show();
					
				}
				
			}
		});
	}

	private class MyAdapter extends BaseAdapter {
		private Context xontext;
		private List list;

		public MyAdapter() {

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
		public View getView(final int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = View.inflate(getApplicationContext(),
						R.layout.share_list1, null);
				holder.time = (TextView) convertView.findViewById(R.id.time);
				holder.leixing = (TextView) convertView
						.findViewById(R.id.leixing);
				holder.chuanyu03 = (Button) convertView
						.findViewById(R.id.chuanyu03);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			final Map map = (Map) list.get(position);
			holder.leixing.setText(map.get("leixing").toString());
			holder.time.setText(map.get("time").toString());

			holder.chuanyu03.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (Login.NUM == 0) {
						Intent intent = new Intent(Eventlist.this, Login.class);
						intent.putExtra("name", "login_friend");
						startActivity(intent);
						Toast.makeText(Eventlist.this, "请您登录", 0).show();
					} else {
						Intent intent = new Intent(Eventlist.this, Friend.class);
					
						
						leixing = map.get("leixing").toString();
						time = map.get("time").toString();
						mtime = map.get("mtime").toString();
						zhuti = map.get("zhuti").toString();
						jishi = map.get("jishi").toString();
						youxian = map.get("youxian").toString();
						lingyin = map.get("lingyin").toString();
						imageurl = map.get("imageurl").toString();
						luyin = map.get("luyin").toString();
						Log.i("log", "-------------leixing="+leixing);
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

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		c.close();
		super.onDestroy();
	}

	class TextAsyncTask extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {
			return getResultByPost(params[0]);
		}

		@Override
		protected void onPreExecute() {
			dia = new ProgressDialog(Eventlist.this);
			dia.setMessage("正在同步...");
			dia.show();
			dia.setCanceledOnTouchOutside(false);
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(String result) {
			if (result == null) {
				Toast.makeText(Eventlist.this, "网络加载失败..", 0).show();
				dia.dismiss();
			} else {
				Log.i("log", "-------------result" + result);
				dia.dismiss();
				JSONObject object;
				try {

					object = new JSONObject(result);
					String share = object.getString("serverNo");
					String serverYes = object.getString("serverYes");
					Log.i("log", "-------------serverYes=" + serverYes);
					if(null != serverYes && !serverYes.equals("")){
						Gson gson = new Gson();
						Type type = new TypeToken<List<Thing>>() {}.getType();
						
						List<Thing> thing = new ArrayList<Thing>();
						thing.clear();
						thing = gson.fromJson(serverYes, type);
						Log.i("log", "-------------thing=" + thing);
						if(null != thing && !thing.equals("")){
							data.clear();
							for(int i=0;i<thing.size();i++){
								ContentValues values = new ContentValues();
								values.put(Meta.TableMeta._LEIXING, thing.get(i).getType());//类型
								values.put(Meta.TableMeta._TIME, thing.get(i).getTime());//时间提醒
								values.put(Meta.TableMeta._MTIME, thing.get(i).getAlarmTime());//提前时间
								Calendar cal = Calendar.getInstance();
								long timeuique = cal.getTimeInMillis();
								values.put(Meta.TableMeta._TIMEUIQUE, timeuique);//时间戳
								Boolean flag = true;
								Uri uri = null;
								if(flag == true){
									uri = resolver.insert(Meta.CONTENT_URI, values);
									flag = false;
								}
								
								if (uri != null) {
									Log.i("log", "添加成功！");
								}
								c = resolver.query(Meta.CONTENT_URI, null, null, null, null);
								if (null != c && c.moveToFirst()) {
									for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
										leixing = c.getString(c.getColumnIndex(com.notbook.things.Meta.TableMeta._LEIXING));
										time = c.getString(c.getColumnIndex(com.notbook.things.Meta.TableMeta._TIME));
										mtime = c.getString(c.getColumnIndex(com.notbook.things.Meta.TableMeta._MTIME));
										zhuti = c.getString(c.getColumnIndex(com.notbook.things.Meta.TableMeta._ZHUTI));
										jishi = c
												.getString(c
														.getColumnIndex(com.notbook.things.Meta.TableMeta._JISHI));
										youxian = c
												.getString(c
														.getColumnIndex(com.notbook.things.Meta.TableMeta._YOUXIAN));
										lingyin = c
												.getString(c
														.getColumnIndex(com.notbook.things.Meta.TableMeta._LINGYIN));
										luyin = c
												.getString(c
														.getColumnIndex(com.notbook.things.Meta.TableMeta._LUYIN));
										id = c.getString(c
												.getColumnIndex(com.notbook.things.Meta.TableMeta._ID));
										firsttime = c
												.getString(c
														.getColumnIndex(com.notbook.things.Meta.TableMeta._TIMEUIQUE));
										byte[] picData = c.getBlob(c
												.getColumnIndex(Meta.TableMeta._IMAGE));
										if (picData != null) {
											imageurl = c
													.getString(c
															.getColumnIndex(com.notbook.things.Meta.TableMeta._IMAGE));
										}
										Map<String, String> item = new HashMap<String, String>();
										item.put("leixing", leixing);
										item.put("time", time);
										item.put("mtime", mtime);
										item.put("zhuti", zhuti);
										item.put("jishi", jishi);
										item.put("youxian", youxian);
										item.put("lingyin", lingyin);
										item.put("imageurl", imageurl);
										item.put("luyin", luyin);
										item.put("id", id);
										item.put("firsttime", firsttime);
										data.add(item);
									}
								}
								Log.i("log", "-----------data：" + data);
								myadapter = new MyAdapter(Eventlist.this, data);
								findshare.setAdapter(myadapter);
								myadapter.notifyDataSetChanged();
							}
						}
					}
					
					
					String str1[] = share.split("\\,");

					ArrayList<Thing> things1 = new ArrayList<Thing>();
					things1.clear();
					for (int i = 0; i < str1.length; i++) {
						Thing thing1 = new Thing();
						String name1 = str1[i];
						thing1.setTimeuique(name1);
						things1.add(thing1);
					}
					List<Thing> data1 = new ArrayList<Thing>();
					data1.clear();
					for (int i = 0; i < things1.size(); i++) {
						String time = things1.get(i).getTimeuique().toString();
						c = resolver.query(Meta.CONTENT_URI, null,
								"_timeuique = ?", new String[] { time }, null);
						
						if (null != c && c.moveToFirst()) {
							
							for (c.moveToFirst(); !c.isAfterLast(); c
									.moveToNext()) {
								int nameColumn = c
										.getColumnIndex(com.notbook.things.Meta.TableMeta._LEIXING);
								leixing = c.getString(nameColumn);
								int nameColumn1 = c
										.getColumnIndex(com.notbook.things.Meta.TableMeta._TIME);
								time = c.getString(nameColumn1);
								mtime = c
										.getString(c
												.getColumnIndex(com.notbook.things.Meta.TableMeta._MTIME));
								zhuti = c
										.getString(c
												.getColumnIndex(com.notbook.things.Meta.TableMeta._ZHUTI));
								jishi = c
										.getString(c
												.getColumnIndex(com.notbook.things.Meta.TableMeta._JISHI));
								youxian = c
										.getString(c
												.getColumnIndex(com.notbook.things.Meta.TableMeta._YOUXIAN));
								lingyin = c
										.getString(c
												.getColumnIndex(com.notbook.things.Meta.TableMeta._LINGYIN));
								luyin = c
										.getString(c
												.getColumnIndex(com.notbook.things.Meta.TableMeta._LUYIN));
								id = c.getString(c
										.getColumnIndex(com.notbook.things.Meta.TableMeta._ID));
								firsttime = c
										.getString(c
												.getColumnIndex(com.notbook.things.Meta.TableMeta._TIMEUIQUE));
								byte[] picData = c.getBlob(c
										.getColumnIndex(Meta.TableMeta._IMAGE));
								if (picData != null) {
									imageurl = c
											.getString(c
													.getColumnIndex(com.notbook.things.Meta.TableMeta._IMAGE));
								}
								Thing item = new Thing();
								item.setType(leixing);
								item.setTime(time);
								item.setAlarmTime(mtime);
								item.setSubject(zhuti);
								item.setWord(jishi);
								item.setPriority(youxian);
								item.setWarnUrl(lingyin);
								item.setPicUrl(imageurl);
								item.setVoiceUrl(luyin);
								item.setTimeuique(firsttime);
								item.setId(Integer.valueOf(id));
								data1.add(item);
							}   
							c.close();  
						}
					}
					Gson gson = new Gson();
					s1 = gson.toJson(data1).toString();

					Log.i("log", "-----------s1：" + s1);
					url = com.notbook.app.Info.uri
							+ "/ADsynchroAddAction.php?uphone=" + Login.uphone
							;
					new Thread(new Runnable() {
						@Override
						public void run() {
								handler.sendMessage(new Message());
						}
					}).start();

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			super.onPostExecute(result);
		}
	}
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			
			Log.i("log", "-----------url：" + url);
//			new TextAsyncTask1().execute(url);
			HttpMultipartPost task = new HttpMultipartPost();
			task.execute();
		}

	};
	class TextAsyncTask1 extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {
			return getResultByPost(params[0]);
		}

		@Override
		protected void onPostExecute(String result) {
			if (result == null) {
			} else {
				Log.i("log", "-------------result" + result);
				JSONObject object;
				try {
					object = new JSONObject(result);
					/*String share = object.getString("result");
					if(share.equals("true")){
						Toast.makeText(Eventlist.this, "同步成功！", 0).show();
					}*/
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
	class HttpMultipartPost extends AsyncTask<HttpResponse, Integer, String> {
		ProgressDialog pd;
		long totalSize;
		Context context;

		@Override
		protected String doInBackground(HttpResponse... arg0) {

			HttpClient httpClient = new DefaultHttpClient();
			HttpContext httpContext = new BasicHttpContext();
			HttpPost httpPost;
			httpPost = new HttpPost(url);
			CustomMultiPartEntity multipartContent = new CustomMultiPartEntity(new ProgressListener() {
				@Override
				public void transferred(long num) {
					publishProgress((int) ((num / (float) totalSize) * 100));
				}
			});
			try {
				Log.i("log", "------------------ssssssssssssssssss=" + s1);
				s1 = URLEncoder.encode(s1);
				multipartContent.addPart("type", new StringBody(s1));
				Log.i("log", "------------------ssssssssssssssssss=" + s1);
				totalSize = multipartContent.getContentLength();
				
//				// 绑定到请求 Entry
//				StringEntity se = new StringEntity(s1.toString()); 
//				httpPost.setEntity(se);
//				// 发送请求
//				HttpResponse httpResponse = new DefaultHttpClient().execute(httpPost);
//				// 得到应答的字符串，这也是一个 JSON 格式保存的数据
//				String serverResponse = EntityUtils.toString(httpResponse.getEntity(),"utf-8");
				
				// Send it   
				httpPost.setEntity(multipartContent);
				HttpResponse response = httpClient.execute(httpPost,
						httpContext);
				String serverResponse = EntityUtils.toString(response
						.getEntity(),"utf-8");
				Log.i("log", "------------------serverResponse=" + serverResponse);
				return serverResponse;
			}

			catch (Exception e) {
				System.out.println(e);
			}
			return null;
		}

	}
	public static String getTime(String user_time) {
		String re_time = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		Date d;
		try {

		d = sdf.parse(user_time);
		long l = d.getTime();
		String str = String.valueOf(l);
		re_time = str.substring(0, 10);

		} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
		return re_time;
	}
}
