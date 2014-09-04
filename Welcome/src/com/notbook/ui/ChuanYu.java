package com.notbook.ui;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.notbook.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.notbook.app.AppManager;
import com.notbook.entity.Thing;
import com.notbook.ui.Hudianlist.TextAsyncTask;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class ChuanYu extends BaseActivity {

	private Button mychuanyu,myaboutchuanyu,myattention,button01,button02,button03;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chuanyu);
		findViewById(R.id.one).setBackgroundResource(background_photobg[num - 1]);
		findViewById(R.id.one1).setBackgroundResource(background_photobg1[num - 1]);
		mychuanyu = (Button)findViewById(R.id.mychuanyu);
		myaboutchuanyu = (Button)findViewById(R.id.myaboutchuanyu);
		myattention = (Button)findViewById(R.id.myattention);
		button01 = (Button)findViewById(R.id.button01);
		button02 = (Button)findViewById(R.id.button02);
		button03 = (Button)findViewById(R.id.button03);
		//获取当前的获取条数
		new TextAsyncTask().execute(com.notbook.app.Info.uri+"/NumLookAction.php?uphone="+Login.uphone);
		mychuanyu.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(Login.NUM==0){
					Intent intent = new Intent(ChuanYu.this, Login.class);
					intent.putExtra("name", "login_friend1");
					startActivity(intent);
					Toast.makeText(ChuanYu.this, "请您登录", 0).show();
				}else{
					Intent intent = new Intent(ChuanYu.this, Hudianlist.class);
					startActivity(intent);
				}
			}
		});
		myaboutchuanyu.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(Login.NUM==0){
					Intent intent = new Intent(ChuanYu.this, Login.class);
					intent.putExtra("name", "login_friend1");
					startActivity(intent);
					Toast.makeText(ChuanYu.this, "请您登录", 0).show();
				}else{
					Intent intent = new Intent(ChuanYu.this, Myaboutchuanyulist.class);
					startActivity(intent);
				}
				
			}
		});
		myattention.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(Login.NUM==0){
					Intent intent = new Intent(ChuanYu.this, Login.class);
					intent.putExtra("name", "login_friend1");
					startActivity(intent);
					Toast.makeText(ChuanYu.this, "请您登录", 0).show();
				}else{
					Intent intent = new Intent(ChuanYu.this, Myattention.class);
					startActivity(intent);
				}
				
			}
		});
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
	class TextAsyncTask extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {
			return getResultByPost(params[0]);
		}

		@Override
		protected void onPostExecute(String result) {
//			onLoad();
			if (result == null) {
			} else {
				Log.d("log", "---------------------result="+result);
				JSONObject object,object1;
				try {
					object = new JSONObject(result);
					int id = object.getInt("responsecode");  
						if(id == 10){
							String share = object.optString("result"); 
							object1 = new JSONObject(share);
							String lookpost = object1.getString("lookpost"); 
							String lookrecuser = object1.getString("lookrecuser");
							String project = object1.getString("project");
							button01.setText(lookpost);
							button02.setText(lookrecuser);
							button03.setText(project);
							if(Integer.valueOf(lookpost)>0){
								button01.setVisibility(View.VISIBLE);
							}
							if(Integer.valueOf(lookpost)>99){
								button01.setText("99+");
							}
							if(Integer.valueOf(lookrecuser)>0){
								button02.setVisibility(View.VISIBLE);
							}
							if(Integer.valueOf(lookrecuser)>99){  
								button02.setText("99+");
							}
							if(Integer.valueOf(project)>0){
								button03.setVisibility(View.VISIBLE);
							}
							if(Integer.valueOf(project)>99){
								button03.setText("99+");
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
