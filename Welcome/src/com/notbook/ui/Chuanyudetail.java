package com.notbook.ui;

import java.io.IOException;
import java.util.ArrayList;

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
import com.example.notbook.R.color;
import com.notbook.app.cache.ImageLoader;
import com.notbook.entity.Thing;
import com.notbook.things.Meta;
import com.notbook.ui.ChuanYu.TextAsyncTask;

public class Chuanyudetail extends BaseActivity {

	private Button iamgebutton1,iamgebutton2,iamgebutton3,iamgebutton4,iamgebutton5;
	private Button more_back ,eventedit,delete,chuanyupath;
	private LinearLayout moreinformation ;
	private RelativeLayout receiveperson,more,shouqi;
	public static int NUM = 0;
	private TextView more01,leixing,time,mtime,rename,norename,cname,text,first,tone;
	private ImageView cphoto,imagebig;
	private ImageLoader mImageLoader ;
	private String toneurl = "";//录音路径
	private MediaPlayer mPlayer = null;
	private String photo = "";//图片路劲
	private String id = "";
	private String recnametext = "";//接收人
	private String ponsuser = "";//响应人
	private String recuser = ""; //总人数
	private String leixingtext = "";//类型
	private String timetext = "";//时间
	private String mtimetext = "";//提前时间
	private String cnametext = "";//主题
	private String texttext = "";//内容
	private String firsttext = "";//优先级
	private String tonetext = "";//语音
	private ArrayList<Thing> things2 = new ArrayList<Thing>();
	private ArrayList<Thing> things1 = new ArrayList<Thing>();
	private ArrayList<Thing> things = new ArrayList<Thing>();
	private ScrollView scrollview ;
	private Boolean flag = true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chuanyudetail);
		findViewById(R.id.one).setBackgroundResource(background_photobg[num - 1]);
		findViewById(R.id.one2).setBackgroundResource(background_photobg1[num - 1]);
		findViewById(R.id.one1).setBackgroundResource(background_photobg1[num - 1]);
		more_back = (Button)findViewById(R.id.more_back);
		moreinformation = (LinearLayout)findViewById(R.id.moreinformation);
		receiveperson = (RelativeLayout)findViewById(R.id.receiveperson);
		shouqi = (RelativeLayout)findViewById(R.id.shouqi);
		more = (RelativeLayout)findViewById(R.id.more);
		eventedit = (Button)findViewById(R.id.eventedit);
		delete = (Button)findViewById(R.id.delete);
		more01 = (TextView)findViewById(R.id.more01);
		leixing = (TextView)findViewById(R.id.leixing);
		time = (TextView)findViewById(R.id.time);
		mtime = (TextView)findViewById(R.id.mtime);
		rename = (TextView)findViewById(R.id.rename);
		norename = (TextView)findViewById(R.id.norename);
		cname = (TextView)findViewById(R.id.cname);
		text = (TextView)findViewById(R.id.text);
		chuanyupath = (Button)findViewById(R.id.chuanyupath);
		first = (TextView)findViewById(R.id.first);
		tone = (TextView)findViewById(R.id.tone);
		cphoto = (ImageView)findViewById(R.id.cphoto);
		imagebig = (ImageView)findViewById(R.id.imagebig);
		scrollview = (ScrollView) findViewById(R.id.scrollview);
		
		Bundle extras = getIntent().getExtras();
		if(null != extras&& !extras.equals("")){
			leixing.setText(extras.getString("leixing").toString());
			time.setText(extras.getString("time").toString());
			mtime.setText(extras.getString("mtime").toString());
			recnametext = extras.getString("recname").toString();
			ponsuser = extras.getString("ponsuser").toString();
			recuser = extras.getString("recuser").toString();
			recuser = recuser.substring(0,recuser.lastIndexOf(","));
			id = extras.getString("id").toString();
			Log.d("log", "---------------------id11111111111111111111="+id);
			Log.d("log", "---------------------recuser="+recuser);
			Log.d("log", "---------------------recnametext="+recnametext);
			Log.d("log", "---------------------ponsuser="+ponsuser);
//			ponsuser = ponsuser.substring(0, ponsuser.lastIndexOf(","));
			//接收人
			String str1[]  = recnametext.split("\\,");
			
			
			
			for(int i=0;i<str1.length;i++){
				Thing thing1 = new Thing();
				if(str1[i]==null||str1[i].equals("null")){
					str1[i] = "";
				}
				String name1 = str1[i];
				thing1.setRecname(name1);
				things1.add(thing1);
			}
			//响应人
			if(ponsuser.length()>0){
				String str[]  = ponsuser.split("\\,");
				for(int i=0;i<str.length;i++){
					Thing thing = new Thing();
					if(str[i]==null||str[i].equals("null")){
						str[i] = "";
					}
					String name = str[i];
					thing.setRecname(name);
					things.add(thing);
				}
			}
			
			//总人数
			String str2[]  = recuser.split("\\,");
		
			for(int i=0;i<str2.length;i++){
				Thing thing2 = new Thing();
				if(str2[i]==null||str2[i].equals("null")){
					str2[i] = "";
				}
				String name2 = str2[i];
				thing2.setRecname(name2);
				things2.add(thing2);
			}
			Log.d("log", "---------------------things.size()="+things.size());
			Log.d("log", "---------------------things1.size()="+things1.size());
			Log.d("log", "---------------------things2.size()="+things2.size());
			if(things1.size()>6){
				rename.setText(things.size()+"/"+things1.size());
				norename.setVisibility(View.GONE);
			}else{
				if(things.size()<=0){
					rename.setVisibility(View.GONE);
					norename.setText(recnametext);
				}else{
					StringBuffer s = new StringBuffer();
					StringBuffer s1 = new StringBuffer();
					String strtext = "";
					for(int i=0;i<things2.size();i++){
						for(int j=0;j<things.size();j++){
							Log.d("log", "---------------------things.get(j).getRecname()="+things.get(j).getRecname());
							Log.d("log", "---------------------things2.get(i).getRecname()="+things2.get(i).getRecname());
							if(things.get(j).getRecname().toString().trim().equals(things2.get(i).getRecname().toString().trim())){
								strtext = things1.get(i).getRecname().toString().trim();
								if(strtext==null||strtext.equals("null")){
									strtext = "";
								}
								strtext = s.append(strtext).append(",").toString();
								if(!strtext.equals("")&&strtext.length()>0){
									strtext = strtext.substring(0, strtext.lastIndexOf(","));
								}
							}
						}
					}
					rename.setVisibility(View.VISIBLE);
					rename.setText(strtext);
					
					String strtext2 = "";
					for(int i=0;i<things2.size();i++){
						for(int j=0;j<things.size();j++){
							if(!things.get(j).getRecname().toString().trim().equals(things2.get(i).getRecname().toString().trim())){
								strtext2 = things1.get(i).getRecname().toString().trim();
								strtext2 = s1.append(strtext2).append(",").toString();
								if(!strtext2.equals("")&&strtext2.length()>0){
									strtext2 = strtext2.substring(0, strtext2.lastIndexOf(","));
								}
							}
						}
					}
					norename.setText(strtext2);
				}
				
			}
			
			cname.setText(extras.getString("cname").toString());
			text.setText(extras.getString("text").toString());
			first.setText(extras.getString("first").toString());
			tone.setText(extras.getString("tone").toString());
			
			
			
			leixingtext = extras.get("leixing").toString();
			timetext = extras.get("time").toString();
			mtimetext = extras.get("mtime").toString();
			cnametext = extras.get("cname").toString();
			texttext = extras.get("text").toString();
			firsttext = extras.get("first").toString();
			tonetext = extras.get("tone").toString();
			
			
			photo = extras.get("cphoto").toString();
			if(null!=photo&&!photo.equals("")){
				mImageLoader = new ImageLoader(Chuanyudetail.this);
				mImageLoader.DisplayImage(photo, cphoto, false);	
			}
			toneurl = extras.getString("chuanyupath").toString();
			
			
		}
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
						mImageLoader = new ImageLoader(Chuanyudetail.this);
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
				Intent intent = new Intent(Chuanyudetail.this, Hudianlist.class);
				startActivity(intent);
				finish();
			}
		});
		
		more.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				more.setVisibility(View.GONE);
				more01.setVisibility(View.GONE);
				moreinformation.setVisibility(View.VISIBLE);
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
		receiveperson.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(things1.size()>6){
					Intent intent = new Intent(Chuanyudetail.this, Receiveperson.class);
					intent.putExtra("recnametext", recnametext);
					intent.putExtra("ponsuser", ponsuser);
					intent.putExtra("recuser", recuser);
					startActivity(intent);
				}
			}
		});
		delete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(Chuanyudetail.this)
				.setTitle("温馨提示")
				.setMessage("是否删除这条记事?")
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
								String url = com.notbook.app.Info.uri+"/deleteChuanyuAction.php?uphone="+Login.uphone+"&id="+id;
								new TextAsyncTask1().execute(url);
								Log.d("log", "---------------------url="+url);
								
								
							}
						}).show();
			}
		});
		eventedit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Chuanyudetail.this, Chuanyuadd.class);
				intent.putExtra("recnametext", recnametext);
				intent.putExtra("recuser", recuser);
				intent.putExtra("leixingtext", leixingtext);
				intent.putExtra("timetext", timetext);
				intent.putExtra("mtimetext", mtimetext);
				intent.putExtra("cnametext", cnametext);
				intent.putExtra("texttext", texttext);
				intent.putExtra("firsttext", firsttext);
				intent.putExtra("tonetext", tonetext);
				intent.putExtra("photo", photo);
				intent.putExtra("toneurl", toneurl);
				intent.putExtra("id", id);
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
				Intent intent = new Intent(Chuanyudetail.this, MainActivity0.class);
				startActivity(intent);
			}
		});
		iamgebutton2 = (Button) findViewById(R.id.iamgebutton2);
		iamgebutton2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Chuanyudetail.this, MainActivity2.class);
				startActivity(intent);
			}
		});
		iamgebutton3 = (Button) findViewById(R.id.iamgebutton3);
		iamgebutton3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Chuanyudetail.this, MainActivity3.class);
				startActivity(intent);
			}
		});
		iamgebutton4 = (Button) findViewById(R.id.iamgebutton4);
		iamgebutton4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Chuanyudetail.this, MainActivity4.class);
				startActivity(intent);
			}
		});
		iamgebutton5 = (Button) findViewById(R.id.iamgebutton5);
		iamgebutton5.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Chuanyudetail.this, MainActivity1.class);
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
		stopPlaying();
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
				JSONObject object,object1;
				try {
					object = new JSONObject(result);
					int id = object.getInt("responsecode");  
						if(id == 10){
							Intent intent = new Intent(Chuanyudetail.this, Hudianlist.class);
							startActivity(intent);
							Chuanyudetail.this.finish();
							Toast.makeText(getApplication(), "删除成功！", 0).show();	
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
	class TextAsyncTask extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {
			return getResultByPost(params[0]);
		}
		@Override
		protected void onPreExecute() {
			dia = new ProgressDialog(Chuanyudetail.this);
			dia.setMessage("正在加载...");
			dia.show();
			dia.setCanceledOnTouchOutside(false);
			super.onPreExecute();
		}
		@Override
		protected void onPostExecute(String result) {
			if (result == null) {
			} else {
				Log.d("log", "---------------------result="+result);
				JSONObject object,object1;
				dia.dismiss();
				try {
					object = new JSONObject(result);
					
					String share = object.getString("result");
					
					object1 = new JSONObject(share);
					
					String photo = object1.getString("photo");
					Log.d("log", "---------------------photo="+photo);
					mImageLoader = new ImageLoader(Chuanyudetail.this);
					mImageLoader.DisplayImage(photo, cphoto, false);
					
				} catch (JSONException e) {
				e.printStackTrace();
				}
				
				
			}
			super.onPostExecute(result);
		}
	}
}
