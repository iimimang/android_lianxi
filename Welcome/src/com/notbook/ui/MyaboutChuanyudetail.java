package com.notbook.ui;

import java.io.IOException;
import java.lang.reflect.Type;
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

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notbook.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.notbook.app.cache.ImageLoader;
import com.notbook.entity.Thing;
import com.notbook.things.Meta;

public class MyaboutChuanyudetail extends BaseActivity {

	private Button iamgebutton1,iamgebutton2,iamgebutton3,iamgebutton4,iamgebutton5;
	private Button more_back ,more,shouqi,delete;
	private LinearLayout moreinformation ;
	private RelativeLayout receiveperson;
	private TextView leixing,time,mtime,rename,cname,text,chuanyupath,first,tone; 
	public static int NUM = 0;
	public String id = "";
	static ContentResolver resolver ;
	private TextView more01;
	private ImageView cphoto,imagebig;
	private ImageLoader mImageLoader ;
	private String toneurl = "";//录音路径
	private MediaPlayer mPlayer = null;
	private Button response ;//响应
	private Button ignore ;//忽略
	private List<com.notbook.entity.Thing> things = new ArrayList<com.notbook.entity.Thing>();
	private ScrollView scrollview ;
	private Boolean flag = true;
	private String photo = "";//图片
	String rename_num = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myaboutchuanyudetail);
		findViewById(R.id.one).setBackgroundResource(background_photobg[num - 1]);
		findViewById(R.id.one2).setBackgroundResource(background_photobg1[num - 1]);
		findViewById(R.id.one1).setBackgroundResource(background_photobg1[num - 1]);
		more_back = (Button)findViewById(R.id.more_back);
		moreinformation = (LinearLayout)findViewById(R.id.moreinformation);
		receiveperson = (RelativeLayout)findViewById(R.id.receiveperson);
		shouqi = (Button)findViewById(R.id.shouqi);
		delete = (Button)findViewById(R.id.delete);
		
		leixing = (TextView)findViewById(R.id.huiyi);
		time = (TextView)findViewById(R.id.date);
		mtime = (TextView)findViewById(R.id.tqtime);
		rename = (TextView)findViewById(R.id.rename);
		cname = (TextView)findViewById(R.id.cname);
		text = (TextView)findViewById(R.id.text);
		chuanyupath = (TextView)findViewById(R.id.chuanyupath);
		first = (TextView)findViewById(R.id.first);
		tone = (TextView)findViewById(R.id.tone);
		cphoto = (ImageView)findViewById(R.id.cphoto);
		more01 = (TextView)findViewById(R.id.more01);
		response = (Button)findViewById(R.id.response);
		ignore = (Button)findViewById(R.id.ignore);
		imagebig = (ImageView)findViewById(R.id.imagebig);
		scrollview = (ScrollView) findViewById(R.id.scrollview);
		resolver = getContentResolver();
		/*if(id!=null){
		id = getIntent().getStringExtra("id");
		Cursor c = getContentResolver().query(Meta.CONTENT_URI, null,
				"_id = ?", new String[] { id }, null);
		
		if (c != null) {
			c.moveToFirst();
			huiyi.setText(c.getString(c.getColumnIndex(Meta.TableMeta._LEIXING)));
			date.setText(c.getString(c.getColumnIndex(Meta.TableMeta._TIME)));
		}
		}*/
		Bundle extras = getIntent().getExtras();
		if(null != extras){
			leixing.setText(extras.getString("leixing").toString());
			time.setText(extras.getString("time").toString());
			mtime.setText(extras.getString("mtime").toString());
			rename_num = extras.getString("recname").toString();
			String str1[]  = rename_num.split("\\,");
			List<Thing> list = new ArrayList<Thing>();
			list.clear();
			for(int i=0;i<str1.length;i++){
				Thing thing = new Thing();
				String name = str1[i];
				thing.setCname(name);
				list.add(thing);
			}
			Log.d("log", "---------------------list="+list);
			
			rename.setText(extras.getString("recname").toString());
			if(list.size()<7){
				rename.setText(extras.getString("recname").toString());
			}else{
				rename.setText(extras.getString("recname").toString());
				receiveperson.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(MyaboutChuanyudetail.this, Sendperson.class);
						intent.putExtra("recnametext", rename_num);
						startActivity(intent);
					}
				});
			
//				receiveperson.setOnClickListener(new View.OnClickListener() {
//					@Override
//					public void onClick(View v) {
//						Intent intent = new Intent(MyaboutChuanyudetail.this, Receiveperson.class);
//						startActivity(intent);
//					}
//				});
			}
			
			cname.setText(extras.getString("cname").toString());
			text.setText(extras.getString("text").toString());
			first.setText(extras.getString("first").toString());
			tone.setText(extras.getString("tone").toString());
			mImageLoader = new ImageLoader(MyaboutChuanyudetail.this);
			photo = extras.getString("cphoto").toString();
			if(null!=photo&&!photo.equals("")){
				mImageLoader.DisplayImage(photo, cphoto, false);	 
			}
			toneurl = extras.getString("chuanyupath").toString();
			id = extras.getString("id").toString();
			
		}
		chuanyupath.setText("点击播放");
		if(null!=toneurl&&!toneurl.equals("")){
			chuanyupath.setBackgroundResource(R.drawable.yuyinbuttonone);
		}else{
			chuanyupath.setBackgroundResource(R.drawable.yuyingbutton);
		}
		chuanyupath.setOnClickListener(new OnClickListener() {
			boolean mStartRecording = true;

			@Override
			public void onClick(View v) {
				if(null!=toneurl&&!toneurl.equals("")){
					onPlay(mStartRecording);
					if (mStartRecording) {
						chuanyupath
								.setBackgroundResource(R.drawable.yuyinbuttonone);
						chuanyupath.setText("正在播放");
					} else {
//						readrecoder.setBackgroundResource(R.drawable.yuyingbutton);
						chuanyupath.setText("点击播放");
					}
					mStartRecording = !mStartRecording;
				}else{
					chuanyupath.setBackgroundResource(R.drawable.yuyingbutton);
				}
			}
		});
		more_back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MyaboutChuanyudetail.this, Myaboutchuanyulist.class);
				startActivity(intent);
				finish();
			}
		});
		more = (Button)findViewById(R.id.more);
		more.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				more.setVisibility(View.GONE);
				more01.setVisibility(View.GONE);
				moreinformation.setVisibility(View.VISIBLE);
			}
		});
		new TextAsyncTask3().execute(com.notbook.app.Info.uri+"/setChuanyuAction.php?uphone="+Login.uphone+"&id="+id);
		response.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				new TextAsyncTask1().execute(com.notbook.app.Info.uri+"/responsetAction.php?uphone="+Login.uphone+"&chuanyuId="+id);
				
			}
		});
		ignore.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				new TextAsyncTask2().execute(com.notbook.app.Info.uri+"/igonreAction.php?uphone="+Login.uphone+"&chuanyuId="+id);
				
			}
		});
		cphoto.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
//				//获取大图
//				new TextAsyncTask().execute(com.notbook.app.Info.uri+"/GetImageBig.php?chuanyuid="+id);
				if(flag == true){
					if(null!=photo&&!photo.equals("")){
						imagebig.setVisibility(View.VISIBLE);
						AnimationSet animationSet = new AnimationSet(true);
						ScaleAnimation translateAnimation = new ScaleAnimation(
								0.5f, 1f,
								0.5f, 1f,
								Animation.RELATIVE_TO_SELF, 0.5f,
								Animation.RELATIVE_TO_SELF, 0.5f);
						translateAnimation.setDuration(1000);
						animationSet.addAnimation(translateAnimation);
						imagebig.startAnimation(animationSet);
						scrollview.setVisibility(View.GONE);
						mImageLoader = new ImageLoader(MyaboutChuanyudetail.this);
						mImageLoader.DisplayImage(photo, imagebig, false);	
						
					}
					flag = false;
					
				}else{
					if(null!=photo&&!photo.equals("")){
						imagebig.setVisibility(View.GONE);
						scrollview.setVisibility(View.VISIBLE);
					}
					flag = true;
				}
			}
		});
		
		imagebig.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(null!=photo&&!photo.equals("")){
					imagebig.setVisibility(View.GONE);
					scrollview.setVisibility(View.VISIBLE);
				}
				flag = true;
			}
		});
		
		shouqi.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				more.setVisibility(View.VISIBLE);
				more01.setVisibility(View.VISIBLE);
				moreinformation.setVisibility(View.GONE);
			}
		});
		
		delete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(MyaboutChuanyudetail.this)
				.setTitle("温馨提示")
				.setMessage("是否删除这条互点?")
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
								/*int n = getContentResolver()
										.delete(Meta.CONTENT_URI,
												"_id=?",
												new String[] { id });
								if (n > 0) {
									Toast.makeText(getApplication(), "删除成功！", 0).show();
								}
								Intent intent = new Intent(MyaboutChuanyudetail.this, Myaboutchuanyulist.class);
								startActivity(intent);*/
								
								new TextAsyncTask().execute(com.notbook.app.Info.uri+"/DelPostChuanyuAction.php?uphone="+Login.uphone+"&id="+id);
							}
						}).show();
			}
		});
		init();
	}
	public void init(){
		iamgebutton1 = (Button) findViewById(R.id.iamgebutton1);
		iamgebutton1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MyaboutChuanyudetail.this, MainActivity0.class);
				startActivity(intent);
			}
		});
		iamgebutton2 = (Button) findViewById(R.id.iamgebutton2);
		iamgebutton2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MyaboutChuanyudetail.this, MainActivity2.class);
				startActivity(intent);
			}
		});
		iamgebutton3 = (Button) findViewById(R.id.iamgebutton3);
		iamgebutton3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MyaboutChuanyudetail.this, MainActivity3.class);
				startActivity(intent);
			}
		});
		iamgebutton4 = (Button) findViewById(R.id.iamgebutton4);
		iamgebutton4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MyaboutChuanyudetail.this, MainActivity4.class);
				startActivity(intent);
			}
		});
		iamgebutton5 = (Button) findViewById(R.id.iamgebutton5);
		iamgebutton5.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MyaboutChuanyudetail.this, MainActivity1.class);
				startActivity(intent);
			}
		});
	}
	private void onPlay(boolean start) {
		if (start) {
			startPlaying();
		} else {
			stopPlaying();
		}
	}

	private void startPlaying() {
		mPlayer = new MediaPlayer();
		try {
			mPlayer.setDataSource(toneurl);
			mPlayer.prepare();
			mPlayer.start();
		} catch (IOException e) {
		}
	}

	private void stopPlaying() {
//		mPlayer.release();
		mPlayer = null;
	}

	@Override
	public void onPause() {
		super.onPause();
		if (mPlayer != null) {
			mPlayer.release();
			mPlayer = null;
		}
	}
	class TextAsyncTask extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {
			return getResultByPost(params[0]);
		}

		@Override
		protected void onPostExecute(String result) {
			if (result == null) {
				Toast.makeText(MyaboutChuanyudetail.this, "网络加载失败..", 0).show();
			} else {
				Log.d("log", "---------------------result="+result);
				JSONObject object,object1;
				try {
					object = new JSONObject(result);
					int id = object.getInt("responsecode");  
						if(id == 10){
							Intent intent = new Intent(MyaboutChuanyudetail.this, Myaboutchuanyulist.class);
							startActivity(intent);
							Toast.makeText(getApplication(), "删除成功！", 0).show();
							MyaboutChuanyudetail.this.finish();
						}
						if(id == 28){
							Toast.makeText(getApplication(), "手机号码不存在(账号不存在)", 0).show();
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
				Toast.makeText(MyaboutChuanyudetail.this, "网络加载失败..", 0).show();
			} else {
				Log.d("log", "---------------------result="+result);
				JSONObject object,object1;
				try {
					object = new JSONObject(result);
					int id = object.getInt("responsecode");  
						if(id == 10){
							response.setBackgroundResource(R.drawable.response_1);
							ignore.setBackgroundResource(R.drawable.hulv);
							response.setEnabled(false);
							ignore.setEnabled(true);
						}	
						
					
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
				Toast.makeText(MyaboutChuanyudetail.this, "网络加载失败..", 0).show();
			} else {
				Log.d("log", "---------------------result="+result);
				JSONObject object,object1;
				try {
					object = new JSONObject(result);
					int id = object.getInt("responsecode");  
						if(id == 10){
							ignore.setBackgroundResource(R.drawable.ignore_1);
							response.setBackgroundResource(R.drawable.xiangying);
							ignore.setEnabled(false);
							response.setEnabled(true);
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
				Toast.makeText(MyaboutChuanyudetail.this, "网络加载失败..", 0).show();
			} else {
				Log.d("log", "---------------------result="+result);
				JSONObject object,object1;
				try {
					object = new JSONObject(result);
					String share = object.getString("result");  
					Gson gson = new Gson();
					Type type = new TypeToken<List<com.notbook.entity.Thing>>() {}.getType();
					
					things = gson.fromJson(share, type);
					if(null != things && things.size()>0){
						int posttype = things.get(0).getPosttype();
						if(posttype == 0){
							
						}else if(posttype == 1){
							response.setBackgroundResource(R.drawable.response_1);
							ignore.setBackgroundResource(R.drawable.hulv);
							response.setEnabled(false);
						}else if(posttype == 2){
							response.setBackgroundResource(R.drawable.xiangying);
							ignore.setBackgroundResource(R.drawable.ignore_1);
							ignore.setEnabled(false);
						}
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
