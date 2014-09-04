package com.notbook.ui;

import java.io.IOException;
import java.util.Calendar;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.notbook.R;
import com.notbook.app.Info;
import com.notbook.ui.Register.TextAsyncTask;
import com.notbook.ui.Register.TextAsyncTask1;

public class Forgetpwd extends BaseActivity {

	private Button more_back ,pwdsave ,yanzhengmabutton;
	private EditText phone,newpassword,resetpassword,vcode;
	private String mphone = "",mnewpassword = "",mresetpassword = "",mvcode="";
	private int i= 0;
	private static long   timenum = 0;
	private static long timenum1 = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.forgetpwd);
		findViewById(R.id.one).setBackgroundResource(background_photobg[num - 1]);
		findViewById(R.id.one1).setBackgroundResource(background_photobg1[num - 1]);
		more_back = (Button)findViewById(R.id.more_back);
		pwdsave = (Button)findViewById(R.id.pwdsave);
		yanzhengmabutton = (Button)findViewById(R.id.yanzhengmabutton);
		phone = (EditText)findViewById(R.id.phone);
		newpassword = (EditText)findViewById(R.id.newpassword);
		resetpassword = (EditText)findViewById(R.id.resetpassword);
		vcode = (EditText)findViewById(R.id.vcode);
		more_back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Forgetpwd.this, Login.class);
				startActivity(intent);
				finish();
			}
		});  
		yanzhengmabutton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mphone =phone.getText().toString();
				if(mphone.equals("")||mphone==null){
					Toast.makeText(getApplicationContext(), "手机号码不能为空", 0).show();
					return;
				}
				if(mphone.length()!=11){
					Toast.makeText(getApplicationContext(), "手机号码长度有误", 0).show();
					return;
				}
				if(!mphone.substring(0, 1).equals("1")){
					Toast.makeText(getApplicationContext(), "手机号码开头必须为1", 0).show();
					return ;
				}
				String url = Info.uri+"/forgetpwdmessAction.php?uphone="+Uri.encode(mphone);
				Log.d( "log", "-------------str"+url);
				Calendar c = Calendar.getInstance();
				if(i==0){
					new TextAsyncTask().execute(url);
					i++;
					timenum = c.getTimeInMillis();
				}else{
					timenum1 = c.getTimeInMillis();
					if((timenum1-timenum)<10*1000){
						Toast.makeText(getApplicationContext(), "30秒以后再点击", 0).show();
					}else{
						new TextAsyncTask().execute(url);
						i=0;
					}
				}
			}
		});  
		pwdsave.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mphone =phone.getText().toString();
				mnewpassword =newpassword.getText().toString();
				mresetpassword =resetpassword.getText().toString();
				mvcode =vcode.getText().toString();
				if(mphone.equals("")||mphone==null){
					Toast.makeText(getApplicationContext(), "手机号码不能为空", 0).show();
					return;
				}
				if(mphone.length()!=11){
					Toast.makeText(getApplicationContext(), "手机号码长度有误", 0).show();
					return;
				}
				if(!mphone.substring(0, 1).equals("1")){
					Toast.makeText(getApplicationContext(), "手机号码开头必须为1", 0).show();
					return ;
				}
				if(mvcode.equals("")||mvcode==null){
					Toast.makeText(getApplicationContext(), "验证码不能为空", 0).show();
					return;
				}
				if(mvcode.length()!=6){
					Toast.makeText(getApplicationContext(), "验证码长度有误", 0).show();
					return;
				}
				if(mnewpassword.equals("")||mnewpassword==null){
					Toast.makeText(getApplicationContext(), "新密码不能为空", 0).show();
					return;
				}
				if(mresetpassword.equals("")||mresetpassword==null){
					Toast.makeText(getApplicationContext(), "确认密码不能为空", 0).show();
					return;
				}
				if(mnewpassword.length()>16||mnewpassword.length()<6){
					Toast.makeText(getApplicationContext(), "密码长度有误", 0).show();
					return ;
				}
				if(mresetpassword.length()>16||mresetpassword.length()<6){
					Toast.makeText(getApplicationContext(), "重复密码长度有误", 0).show();
					return ;
				}
				if(!mresetpassword.equals(mnewpassword)){
					Toast.makeText(getApplicationContext(), "确认密码,必须和之前输入的一样", 0).show();
					return ;
				}
				String url = Info.uri+"/forgetpwdAction.php?uphone="+Uri.encode(mphone)+"&newpassword="
								+Uri.encode(mnewpassword)+"&repassword="+Uri.encode(mresetpassword)+"&vcode="+Uri.encode(mvcode);
				Log.d( "log", "-------------str"+url);
				new TextAsyncTask1().execute(url);
				
				
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
			dia = new ProgressDialog(Forgetpwd.this);
			dia.setMessage("正在获取...");
			dia.show();
			dia.setCanceledOnTouchOutside(false);
			super.onPreExecute();
		}
		@Override
		protected void onPostExecute(String result) {

			if (result == null) {
				Log.d("log", "----------------------------result=" + result);
				Toast.makeText(Forgetpwd.this, "网络加载失败..", 0).show();
				dia.dismiss();
			} else {
				Log.d("log", "----------------------------result=" + result);
				JSONObject object,object1;
				dia.dismiss();
				try {
					
					object = new JSONObject(result);
					int responsecode = object.getInt("responsecode"); 
					if(responsecode!=12){
						Toast.makeText(Forgetpwd.this, "查找失败", 0).show();
					}else{
						Toast.makeText(Forgetpwd.this, "发送成功，请稍候", 0).show();
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
				Log.d("log", "----------------------------result=" + result);
				Toast.makeText(Forgetpwd.this, "网络加载失败..", 0).show();
			} else {
				Log.d("log", "----------------------------result555555555555=" + result);
				JSONObject object,object1;
				try {
					
					object = new JSONObject(result);
					Log.d("log", "----------------------------object=" + object);
//					String share = object.getString("result"); 
					int responsecode = object.getInt("responsecode"); 
					if(responsecode == 12){
						Intent intent = new Intent(Forgetpwd.this, Login.class);
						startActivity(intent);
						Forgetpwd.this.finish();
						Toast.makeText(getApplicationContext(), "修改成功", 0).show();
					}
					if(responsecode == 111){
						Toast.makeText(getApplicationContext(), "验证码错误", 0).show();
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
