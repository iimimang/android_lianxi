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
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Paint;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.style.BulletSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notbook.R;

public class Login extends BaseActivity {

	private Button close ,login,img1,img2;
	public static int NUM = 0;
	private TextView forgetpwd ,register;
	private EditText phone,password;
	public String mphone,mpassword;
	private ProgressDialog dia;
	public static String id = "",name = "",job="",email="",address="",uphone = "";
	private SharedPreferences sp;
	private Editor editor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		findViewById(R.id.one).setBackgroundResource(background_photobg[num - 1]);
		findViewById(R.id.one1).setBackgroundResource(background_photobg1[num - 1]);
		close = (Button)findViewById(R.id.close);
		login = (Button)findViewById(R.id.login);
		register = (TextView)findViewById(R.id.register);
		forgetpwd = (TextView)findViewById(R.id.forgetpwd);
		img1 = (Button)findViewById(R.id.img1);
		img2 = (Button)findViewById(R.id.img2);
		phone = (EditText)findViewById(R.id.phone);
		password = (EditText)findViewById(R.id.password);
		sp = getSharedPreferences("config_one", Context.MODE_PRIVATE);
		editor = sp.edit();
		mphone = sp.getString("username_1", "");
//		mpassword = sp.getString("userpassword_1", "");
		phone.setText(mphone);
		phone.setSelection(mphone.length());
//		password.setText(mpassword);
		close.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent it = new Intent(Login.this,MainActivity1.class);
				startActivity(it);
				finish();
			}
		});
		
		login.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				img1.setVisibility(View.INVISIBLE);
				img2.setVisibility(View.INVISIBLE);
				mphone = phone.getText().toString();
				mpassword = password.getText().toString();
				
				if(mphone.equals("")||mphone==null){
					Toast.makeText(Login.this, "手机号码为空！", 0).show();
					img1.setVisibility(View.VISIBLE);
					return ;
				}
				if(mphone.length()!=11){
					Toast.makeText(Login.this, "手机号码长度有误！", 0).show();
					img1.setVisibility(View.VISIBLE);
					return ;
				}
				if (!mphone.substring(0, 1).equals("1")) {
					Toast.makeText(Login.this, "手机号码开头必须为1", 0).show();
					img1.setVisibility(View.VISIBLE);
					return;
				}
				if(mpassword.equals("")||mpassword==null){
					Toast.makeText(Login.this, "密码为空！", 0).show();
					img2.setVisibility(View.VISIBLE);  
					return ;
				}
				if(mpassword.length()<6||mpassword.length()>16){
					Toast.makeText(Login.this, "密码长度有误！", 0).show();
					img2.setVisibility(View.VISIBLE);  
					return ;
				}
				
				String url = com.notbook.app.Info.uri+"/loginAction.php?username="+Uri.encode(mphone)+"&password="+Uri.encode(mpassword);
				Log.d( "log", "-------------str"+url);
		        new TextAsyncTask()
		    	.execute(url);
		        
				/*NUM = 1;
		        Intent it = new Intent(Login.this,MainActivity1.class);
				startActivity(it);*/
				
			}
		});
//		forgetpwd.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线
		forgetpwd.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent it = new Intent(Login.this,Forgetpwd.class);
				startActivity(it);
				finish();
			}
		});
		register.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent it = new Intent(Login.this,Register.class);
				startActivity(it);
			}
		});
	}
	class TextAsyncTask extends AsyncTask<String, Integer, String> {

		@Override
		protected void onPreExecute() {
			dia = new ProgressDialog(Login.this);
			dia.setMessage("正在登录...");
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
				Toast.makeText(Login.this, "网络加载失败..", 0).show();
				dia.dismiss();
			} else {
				
				Log.d( "log", "-------------result"+result);
				dia.dismiss();
				JSONObject object,object1;
				try {
					
					object = new JSONObject(result);
					String share = object.getString("result"); 
					String share1 = object.getString("responsecode"); 
					if(share1.equals("51")){
						Toast.makeText(getApplicationContext(), "非法登录", 0).show();
						return;
					}else{
						object1 = new JSONObject(share);
						id = object1.getString("id"); 
						name = object1.getString("uname"); 
						job = object1.getString("job"); 
						email = object1.getString("uemail"); 
						address = object1.getString("uaddress"); 
						uphone = object1.getString("uphone"); 
						Log.d("log", "222222222222222222222222222222222222222"+uphone);
						Bundle b = getIntent().getExtras();
						String b_name = "";
						if(null != b&&!b.equals("")){
							b_name = b.getString("name");
						}
						if(b_name.equals("login_friend")){
							Intent it = new Intent(Login.this,Friend.class);
							startActivity(it);
						}
						if(b_name.equals("login_friend1")){
							Intent it = new Intent(Login.this,MainActivity2.class);
							startActivity(it);
						}
						if(b_name.equals("login_friend2")){
							Intent it = new Intent(Login.this,MainActivity4.class);
							startActivity(it);
						}
						if(b_name.equals("more")){
							Intent it = new Intent(Login.this,MainActivity1.class);
							startActivity(it);
						}
						Login.this.finish();
						NUM = 1;
						Editor editor = sp.edit();
						editor.putString("username_1", mphone);
//						editor.putString("userpassword_1", mpassword);
						editor.commit();
						Toast.makeText(getApplicationContext(), "登录成功", 0).show();
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
