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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notbook.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.notbook.entity.MPhone;
import com.notbook.entity.Xiangqing;
import com.notbook.ui.ContactListAdapter.ViewHolder;
import com.notbook.ui.Hudianlist.TextAsyncTask;
import com.notbook.ui.Myaboutchuanyulist.TextAsyncTask3;

public class Myattention extends BaseActivity {

	private Button more_back;
	private Button iamgebutton1, iamgebutton2, iamgebutton3, iamgebutton4,
			iamgebutton5;
	private Button industry, project;
	private ListView hangyelist = null;
	private ListView xiangmulist = null;
	private List<Xiangqing> xiangqing = new ArrayList<Xiangqing>();
	public static List<Xiangqing> namelist = new ArrayList<Xiangqing>();
	public static List<Xiangqing> namelist1 = new ArrayList<Xiangqing>();
	private List<Xiangqing> xiangqing1 = new ArrayList<Xiangqing>();
	public static List list = new ArrayList();
	private LinearLayout LinearLayout001,LinearLayout002;
	public static int Myattentionnum = 0;
	public static int Myattentionnum1 = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myattention);
		findViewById(R.id.one).setBackgroundResource(background_photobg[num - 1]);
		findViewById(R.id.one2).setBackgroundResource(background_photobg1[num - 1]);
		findViewById(R.id.one1).setBackgroundResource(background_photobg1[num - 1]);
		more_back = (Button) findViewById(R.id.more_back);
		industry = (Button) findViewById(R.id.industry);
		project = (Button) findViewById(R.id.project);
		hangyelist = (ListView)findViewById(R.id.hangyelist);
		xiangmulist = (ListView)findViewById(R.id.xiangmulist);
		LinearLayout001 = (LinearLayout)findViewById(R.id.LinearLayout001);
		LinearLayout002 = (LinearLayout)findViewById(R.id.LinearLayout002);
		
		//关注行业和项目
		new TextAsyncTask3().execute(com.notbook.app.Info.uri+"/projectLookAction.php?uphone="+Login.uphone);
		
		
		more_back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Myattention.this,MainActivity2.class);
				startActivity(intent);
				finish();
			}
		});
		LinearLayout001.setVisibility(View.VISIBLE);
		LinearLayout002.setVisibility(View.GONE);
		industry.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				LinearLayout002.setVisibility(View.GONE);
				LinearLayout001.setVisibility(View.VISIBLE);
				hangyelist.setVisibility(View.VISIBLE);
				xiangmulist.setVisibility(View.GONE);
				industry.setBackgroundResource(R.drawable.hangyebackground);
				project.setBackgroundResource(R.drawable.touming);
			}
		});
		project.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				LinearLayout002.setVisibility(View.VISIBLE);
				LinearLayout001.setVisibility(View.GONE);
				hangyelist.setVisibility(View.GONE);
				xiangmulist.setVisibility(View.VISIBLE);
				industry.setBackgroundResource(R.drawable.touming);
				project.setBackgroundResource(R.drawable.xingmubackground);
			}
		});
		//我关注的项目
		new TextAsyncTask().execute(com.notbook.app.Info.uri+"/myprojectFollowAction.php?uphone="+Login.uphone);
		
		//我关注的行业
		new TextAsyncTask1().execute(com.notbook.app.Info.uri+"/mytradeFollowAction.php?uphone="+Login.uphone);
				
		
		
		init();
	}
	class TextAsyncTask extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {
			return getResultByPost(params[0]);
		}
		@Override
		protected void onPreExecute() {
			dia = new ProgressDialog(Myattention.this);
			dia.setMessage("正在加载...");
			dia.show();
			dia.setCanceledOnTouchOutside(false);
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(String result) {
			if (result == null) {
				Toast.makeText(Myattention.this, "网络加载失败..", 0).show();
				dia.dismiss();
			} else {
				dia.dismiss();
				Log.d("log", "---------------------result="+result);
				JSONObject object,object1,strlist,strlist1;
				try {
					object = new JSONObject(result);
					int id = object.getInt("responsecode");  
						if(id == 101){
							Toast.makeText(Myattention.this, "没有数据", 0).show();
						}else {
							
							String share = object.getString("result"); 
							object1 = new JSONObject(share);
							String listtrue = object1.getString("true"); 
							
							
							String listfalse = object1.getString("false"); 
							
							
//							namelist1.clear();
							xiangqing1.clear();
							if(null!=listfalse&&!listfalse.equals("null")&&listfalse.length()>0){
								JSONArray object2 = new JSONArray(listfalse);
								for(int i=0;i<object2.length();i++){
									List tempL=new ArrayList();
									String str = object2.get(i).toString();
									strlist = new JSONObject(str);
									String list = strlist.getString("list");
									if(null!=list&&!list.equals("null")&&list.length()>0){
										JSONArray object3 = new JSONArray(list);
										String strtext = object3.get(0).toString();
										strlist1 = new JSONObject(strtext);
										String name = strlist1.getString("name");
										String time = strlist1.getString("foundTime");
										String text = strlist1.getString("text");
										String idname = strlist1.getString("id");
										Xiangqing xq = new Xiangqing();
										xq.setId(idname);
										xq.setProjectName(name);
										xq.setFoundTime(time);
										xq.setText(text);
										tempL.add(xq);
									}
									String projectName = strlist.getString("projectName");
									String hangyeid = strlist.getString("id");
									Xiangqing xq = new Xiangqing();
									xq.setProjectName(projectName);
									xq.setId(hangyeid);
									xq.setList(tempL);
									xiangqing1.add(xq);
									
								}
								
							}
							if(null!=listtrue&&!listtrue.equals("null")&&listtrue.length()>0){
								
								JSONArray object2 = new JSONArray(listtrue);
								for(int i=0;i<object2.length();i++){
									List tempL=new ArrayList();
									String str = object2.get(i).toString();
									strlist = new JSONObject(str);
									String list = strlist.getString("list");
									String projectName = strlist.getString("projectName");
									String hangyeid = strlist.getString("id");
									if(null!=list&&!list.equals("null")&&list.length()>0){
										JSONArray object3 = new JSONArray(list);
										String strtext = object3.get(0).toString();
										strlist1 = new JSONObject(strtext);
										String name = strlist1.getString("name");
										String time = strlist1.getString("foundTime");
										String text = strlist1.getString("text");
										String idname = strlist1.getString("id");
										Xiangqing xq = new Xiangqing();
										xq.setId(idname);
										xq.setProjectName(name);
										xq.setFoundTime(time);
										xq.setText(text);
										tempL.add(xq);
									}
									Xiangqing xq = new Xiangqing();
									xq.setProjectName(projectName);
									xq.setId(hangyeid);
									xq.setList(tempL);
									xiangqing1.add(xq);
									
								}
								
							}
							HangyelistAdater hla = new HangyelistAdater(xiangqing1,Myattention.this);
							xiangmulist.setAdapter(hla);
							hla.notifyDataSetChanged();
							xiangmulist.setOnItemClickListener(new AdapterView.OnItemClickListener() {

								@Override
								public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
										long id) {
//									if(Myattentionnum1 == 1){
										Intent it = new Intent(Myattention.this, Myattentionlist.class);
										Myattentionlist.xqdetailBean1=xiangqing1.get(arg2);
										it.putExtra("id", "1");
										startActivity(it);
//									}else{
//										Toast.makeText(getApplicationContext(), "未关注", 0).show();
//									}
								}
							});
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
				Log.d("log", "---------------------result="+result);
				JSONObject object,object1,strlist,strlist1;
				try {
					object = new JSONObject(result);
					int id = object.getInt("responsecode");  
						if(id == 101){
							Toast.makeText(Myattention.this, "没有数据", 0).show();
						}else {
							
							String share = object.getString("result"); 
							Log.d("log", "---------------------share="+share);
							object1 = new JSONObject(share);
							String listtrue = object1.getString("true"); 
							Log.d("log", "---------------------listtrue="+listtrue);
							
							
							String listfalse = object1.getString("false"); 
							Log.d("log", "---------------------listfalse="+listfalse);
							
							
							
							xiangqing.clear();
							if(null!=listfalse&&!listfalse.equals("null")&&listfalse.length()>0){
								JSONArray object2 = new JSONArray(listfalse);
								for(int i=0;i<object2.length();i++){
									//namelist.clear();
									List tempL=new ArrayList();
									String str = object2.get(i).toString();
									Log.d("log", "---------------------str="+str);
									strlist = new JSONObject(str);
									String list = strlist.getString("list");
									Log.d("log", "---------------------list="+list);
									if(null!=list&&!list.equals("null")&&list.length()>0){
										JSONArray object3 = new JSONArray(list);
										for(int k=0;k<object3.length();k++)
										{
											
											String strtext = object3.get(k).toString();
											strlist1 = new JSONObject(strtext);
											String name = strlist1.getString("name");
											String time = strlist1.getString("foundTime");
											String text = strlist1.getString("text");
											String idname = strlist1.getString("id");
											Xiangqing xq = new Xiangqing();
											xq.setId(idname);
											xq.setProjectName(name);
											xq.setFoundTime(time);
											xq.setText(text);
											tempL.add(xq);
										}
										
									}
									Log.d("log", "---------------------namelist="+namelist);
									
									
									String tradeName = strlist.getString("tradeName");
									String hangyeid = strlist.getString("id");
									Xiangqing xq = new Xiangqing();
									xq.setProjectName(tradeName);
									xq.setId(hangyeid);
									xq.setList(tempL);
									xiangqing.add(xq);
									
								}
								
							}
							if(null!=listtrue&&!listtrue.equals("null")&&listtrue.length()>0){
								JSONArray object2 = new JSONArray(listtrue);
								for(int i=0;i<object2.length();i++){
									List tempL=new ArrayList();
									String str = object2.get(i).toString();
									strlist = new JSONObject(str);
									String list = strlist.getString("list");
									if(null!=list&&!list.equals("null")&&list.length()>0){
										JSONArray object3 = new JSONArray(list);
										String strtext = object3.get(0).toString();
										strlist1 = new JSONObject(strtext);
										String name = strlist1.getString("name");
										String time = strlist1.getString("foundTime");
										String text = strlist1.getString("text");
										String idname = strlist1.getString("id");
										Xiangqing tempbean = new Xiangqing();
										tempbean.setId(idname);
										tempbean.setProjectName(name);
										tempbean.setFoundTime(time);
										tempbean.setText(text);
										tempL.add(tempbean);
									}
									String tradeName = strlist.getString("tradeName");
									String hangyeid = strlist.getString("id");
									Xiangqing xq = new Xiangqing();
									xq.setProjectName(tradeName);
									xq.setId(hangyeid);
									xq.setList(tempL);
									xiangqing.add(xq);
				
								}
								
							}
							Log.d("log", "---------------------xiangqing="+xiangqing);
							XiangmulistAdater xla = new XiangmulistAdater(xiangqing,Myattention.this);
							hangyelist.setAdapter(xla);
							xla.notifyDataSetChanged();
							hangyelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {

								@Override
								public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
										long id) {
//									for(int i=0;i<xiangqing.size();i++){
//										if(xiangqing.get(i).getNum()==0){
							    			Intent it = new Intent(Myattention.this, Myattentionlist.class);
											Myattentionlist.xqdetailBean=xiangqing.get(arg2);
											it.putExtra("id", "2");
											startActivity(it);
//							    		}else{
//							    			Toast.makeText(getApplicationContext(), "未关注", 0).show();
//							    		}
//									}
								}
							});
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
	public void init() {
		iamgebutton1 = (Button) findViewById(R.id.iamgebutton1);
		iamgebutton1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Myattention.this,
						MainActivity0.class);
				startActivity(intent);
			}
		});
		iamgebutton2 = (Button) findViewById(R.id.iamgebutton2);
		iamgebutton2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Myattention.this,
						MainActivity2.class);
				startActivity(intent);
			}
		});
		iamgebutton3 = (Button) findViewById(R.id.iamgebutton3);
		iamgebutton3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Myattention.this,
						MainActivity3.class);
				startActivity(intent);
			}
		});
		iamgebutton4 = (Button) findViewById(R.id.iamgebutton4);
		iamgebutton4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Myattention.this,
						MainActivity4.class);
				startActivity(intent);
			}
		});
		iamgebutton5 = (Button) findViewById(R.id.iamgebutton5);
		iamgebutton5.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Myattention.this,
						MainActivity1.class);
				startActivity(intent);
			}
		});
	}
	public class HangyelistAdater extends BaseAdapter {
		private List<Xiangqing> xq ;
		private Context context ;
		private ViewHolder holder = null; 
		public HangyelistAdater(List<Xiangqing> xq, Context context) {
			this.xq = xq;
			this.context = context;
		}

		@Override
		public int getCount() {
			return xq.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return xq.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}
		
		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			
			if (null == convertView) {
				convertView = LayoutInflater.from(context).inflate(R.layout.share_list8, null);
				holder = new ViewHolder();
				holder.contact = (TextView) convertView.findViewById(R.id.contact);
				holder.guanzhu = (Button) convertView.findViewById(R.id.guanzhu);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.guanzhu.setVisibility(View.GONE);
			holder.contact.setText(xq.get(position).getProjectName().toString());
			holder.guanzhu.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					holder.guanzhu.setBackgroundResource(R.drawable.guanzhu);
					if(xq.get(position).getNum()==0){
						//关注
						new TextAsyncTask11(holder).execute(com.notbook.app.Info.uri+"/projectFollowAction.php?uphone="+Login.uphone+"&proId="+xq.get(position).getId());
						xq.get(position).setNum(1);
					}else{
						//取消
						new TextAsyncTask11(holder).execute(com.notbook.app.Info.uri+"/cancelProjectction.php?uphone="+Login.uphone+"&id="+xq.get(position).getId());
						xq.get(position).setNum(0);
					}
				}
			});
			return convertView;
		}
	}
	public class XiangmulistAdater extends BaseAdapter {
		private List<Xiangqing> xq ;
		private Context context ;
		private ViewHolder holder = null;
		public XiangmulistAdater(List<Xiangqing> xq, Context context) {
			this.xq = xq;
			this.context = context;
		}

		@Override
		public int getCount() {
			return xq.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return xq.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			
			if (null == convertView) {
				convertView = LayoutInflater.from(context).inflate(R.layout.share_list8, null);
				holder = new ViewHolder();
				holder.contact = (TextView) convertView.findViewById(R.id.contact);
				holder.guanzhu = (Button) convertView.findViewById(R.id.guanzhu);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
//			new TextAsyncTask11(holder).execute(com.notbook.app.Info.uri+"/tradeFollowAction.php?uphone="+Login.uphone+"&proId="+xq.get(position).getId());
//			new TextAsyncTask11(holder).execute(com.notbook.app.Info.uri+"/cancelTradeAction.php?uphone="+Login.uphone+"&id="+xq.get(position).getId());
			holder.contact.setText(xq.get(position).getProjectName().toString());
			holder.guanzhu.setVisibility(View.GONE);
			holder.guanzhu.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
//					Toast.makeText(getApplicationContext(), xq.get(position).getProjectName().toString(), 0).show();
//					if(position==0){
//						holder.guanzhu.setBackgroundResource(R.drawable.cancel);
//					}
//					if(Myattentionnum == 0){
//						holder.guanzhu.setBackgroundResource(R.drawable.cancel);
//						Myattentionnum = 1;
//					}else{
//						holder.guanzhu.setBackgroundResource(R.drawable.guanzhu);
//						Myattentionnum = 0;
//					}  
					holder.guanzhu.setBackgroundResource(R.drawable.guanzhu);
					holder.guanzhu.setVisibility(View.GONE);
					if(xq.get(position).getNum()==0){
						//关注
						new TextAsyncTask11(holder).execute(com.notbook.app.Info.uri+"/tradeFollowAction.php?uphone="+Login.uphone+"&proId="+xq.get(position).getId());
						xq.get(position).setNum(1);
					}else{
						//取消
						new TextAsyncTask11(holder).execute(com.notbook.app.Info.uri+"/cancelTradeAction.php?uphone="+Login.uphone+"&id="+xq.get(position).getId());
						xq.get(position).setNum(0);
					}
				}
			});
			return convertView;
		}
	}
	static class ViewHolder {
		TextView contact;
		Button guanzhu;
	}
	class TextAsyncTask11 extends AsyncTask<String, Integer, String> {
		private ViewHolder holder = null;
		@Override
		protected String doInBackground(String... params) {
			return getResultByPost(params[0]);
		}
		

		public TextAsyncTask11(ViewHolder holder) {
			this.holder = holder;
		}


		@Override
		protected void onPostExecute(String result) {
			if (result == null) {
			} else {
				Log.d("log", "---------------------result="+result);
				JSONObject object,object1,strlist,strlist1;
				try {
					object = new JSONObject(result);
					int id = object.getInt("responsecode");  
						
				    if(id == 104){
				    	holder.guanzhu.setBackgroundResource(R.drawable.cancel);
//				    	for(int i=0;i<xiangqing.size();i++){
//				    		if(xiangqing.get(i).getNum()==0){
//				    			holder.guanzhu.setBackgroundResource(R.drawable.guanzhu);
//				    		}else{
//				    			holder.guanzhu.setBackgroundResource(R.drawable.cancel);
//				    		}
//				    	}
				    	
				    }else{
				    	
				    }
					
				} catch (JSONException e) {
				e.printStackTrace();
				}
				
				
			}
			super.onPostExecute(result);
		}
	}
}
