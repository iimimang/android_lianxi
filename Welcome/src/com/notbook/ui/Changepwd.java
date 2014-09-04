package com.notbook.ui;

import java.io.IOException;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.notbook.R;
import com.notbook.app.Info;
import com.notbook.ui.Login.TextAsyncTask;

public class Changepwd extends BaseActivity {

	private Button more_back ,pwdsave;
	private EditText password ,newpassword ,resetpwd;
	private String tpassword,tnewpassword,tresetpwd;
	private ProgressDialog dia;
	private String olderpassword = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.changpwd);
		findViewById(R.id.one).setBackgroundResource(background_photobg[num - 1]);
		findViewById(R.id.one1).setBackgroundResource(background_photobg1[num - 1]);
		more_back = (Button)findViewById(R.id.more_back);
		pwdsave = (Button)findViewById(R.id.pwdsave);
		password = (EditText)findViewById(R.id.password);
		newpassword = (EditText)findViewById(R.id.newpassword);
		resetpwd = (EditText)findViewById(R.id.resetpwd);
		String url = Info.uri+"/getuserAction.php?userid="+Login.id;
		Log.d( "log", "-------------str"+url);
        new TextAsyncTask1()
    	.execute(url);
		
		more_back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent it = new Intent(Changepwd.this,MainActivity1.class);
				startActivity(it);
				finish();
			}
		});
		pwdsave.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				tpassword = password.getText().toString();
				tnewpassword = newpassword.getText().toString();
				tresetpwd = resetpwd.getText().toString();
				
				if(tpassword.equals("")){
					Toast.makeText(Changepwd.this, "旧密码为不能为空！", 0).show();
					return;
				}
				if(!tpassword.equals(olderpassword)){
					Toast.makeText(Changepwd.this, "密码输入不正确！", 0).show();
					return;
				}
				if(tnewpassword.equals("")){
					Toast.makeText(Changepwd.this, "新密码为不能为空！", 0).show();
					return;
				}
				if(tresetpwd.equals("")){
					Toast.makeText(Changepwd.this, "确认密码为不能为空！", 0).show();
					return;
				}
				if(tnewpassword.length()>16||tnewpassword.length()<6){
					Toast.makeText(Changepwd.this, "密码长度有误", 0).show();
					return ;
				}
				if(tresetpwd.length()>16||tresetpwd.length()<6){
					Toast.makeText(Changepwd.this, "确认密码长度有误", 0).show();
					return ;
				}
				String url = Info.uri+"/editpwdAction.php?userid="+Login.id+"&password="+Uri.encode(tpassword)+"&newpassword="+Uri.encode(tnewpassword)+"&repassword="+Uri.encode(tresetpwd);
				Log.d( "log", "-------------str"+url);
		        new TextAsyncTask()
		    	.execute(url);
			}
		});
	}
	class TextAsyncTask extends AsyncTask<String, Integer, String> {

		@Override
		protected void onPreExecute() {
			dia = new ProgressDialog(Changepwd.this);
			dia.setMessage("正在修改...");
			dia.show();
			dia.setCanceledOnTouchOutside(false);
			super.onPreExecute();
		}
		
		@Override
		protected String doInBackground(String... params) {
			return getResultByPost(params[0]);
		}

		@Override
		protected void onPostExecute(String result) {
			if (result == null) {
				Toast.makeText(Changepwd.this, "网络加载失败..", 0).show();
				dia.dismiss();
			} else {
				
				Log.d( "log", "-------------result"+result);
				dia.dismiss();
				JSONObject object,object1;
				try {
					
					object = new JSONObject(result);
					String share1 = object.getString("responsecode"); 
					if(share1.equals("1")){
						Intent it = new Intent(Changepwd.this,Login.class);
						startActivity(it);
						Login.NUM = 0;
						Changepwd.this.finish();
						Toast.makeText(getApplicationContext(), "修改成功", 0).show();
					}else{
						Toast.makeText(getApplicationContext(), "非法修改", 0).show();
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
				Log.d( "log", "-------------result"+result);
				
				JSONObject object,object1;
				try {
					
					object = new JSONObject(result);
					String share = object.getString("result"); 
					object1 = new JSONObject(share);
					olderpassword = object1.getString("upwd");   
					Log.d( "log", "-------------olderpassword"+olderpassword);
				} catch (JSONException e) {
				e.printStackTrace();
				}
				
			}   
			super.onPostExecute(result);
		}
	}

	private String getResultByPost(String url) {
		String result = null;
		try{
			HttpGet request=new HttpGet(url);
			HttpClient client=new DefaultHttpClient();
		    HttpResponse response=client.execute(request);
		   
		    HttpEntity entity = response.getEntity();
        	result = EntityUtils.toString(entity);
        	
		}catch(IOException e){
			e.printStackTrace();
		}
		return result;
	}
}
