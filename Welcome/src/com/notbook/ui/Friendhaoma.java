package com.notbook.ui;

import java.io.File;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.notbook.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.notbook.app.CustomMultiPartEntity;
import com.notbook.app.Info;
import com.notbook.app.CustomMultiPartEntity.ProgressListener;
import com.notbook.entity.MPhone;
import com.notbook.ui.FriendList.FriendListAdapter;
import com.notbook.ui.FriendList.TextAsyncTask;


public class Friendhaoma extends BaseActivity {

	private Button iamgebutton1, iamgebutton2, iamgebutton3, iamgebutton4,
			iamgebutton5;
	private Button more_back, sender;
	Context mContext = null;
	private EditText friendphone ;
	private List<MPhone> things = new ArrayList<MPhone>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		setContentView(R.layout.friendhaoma);
		findViewById(R.id.one).setBackgroundResource(background_photobg[num - 1]);
		findViewById(R.id.one2).setBackgroundResource(background_photobg1[num - 1]);
		findViewById(R.id.one1).setBackgroundResource(background_photobg1[num - 1]);
		more_back = (Button) findViewById(R.id.more_back);
		friendphone = (EditText) findViewById(R.id.friendphone);
		sender = (Button) findViewById(R.id.sender);
		more_back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		sender.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String mphone = friendphone.getText().toString().trim();
				if (mphone.equals("") || mphone == null) {
					Toast.makeText(Friendhaoma.this, "手机号码为空", 0).show();
					return;
				}
				if (mphone.length() != 11) {
					Toast.makeText(Friendhaoma.this, "手机号码长度有误", 0).show();
					return;
				}
				if (!mphone.substring(0, 1).equals("1")) {
					Toast.makeText(Friendhaoma.this, "手机号码开头必须为1", 0).show();
					return;
				}
				
				String url = Info.uri+"/searchAction.php?uphone="+Login.uphone+"&fphone="+mphone;
				
				new TextAsyncTask().execute(url);
				
				
				
			}
		});
		
		init();
	}

	public void init() {
		iamgebutton1 = (Button) findViewById(R.id.iamgebutton1);
		iamgebutton1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Friendhaoma.this, MainActivity0.class);
				startActivity(intent);
			}
		});
		iamgebutton2 = (Button) findViewById(R.id.iamgebutton2);
		iamgebutton2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Friendhaoma.this, MainActivity2.class);
				startActivity(intent);
			}
		});
		iamgebutton3 = (Button) findViewById(R.id.iamgebutton3);
		iamgebutton3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Friendhaoma.this, MainActivity3.class);
				startActivity(intent);
			}
		});
		iamgebutton4 = (Button) findViewById(R.id.iamgebutton4);
		iamgebutton4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Friendhaoma.this, MainActivity4.class);
				startActivity(intent);
			}
		});
		iamgebutton5 = (Button) findViewById(R.id.iamgebutton5);
		iamgebutton5.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Friendhaoma.this, MainActivity1.class);
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
			dia = new ProgressDialog(Friendhaoma.this);
			dia.setMessage("正在搜索...");
			dia.show();
			dia.setCanceledOnTouchOutside(false);
			super.onPreExecute();
		}
		@Override
		protected void onPostExecute(String result) {
			if (result == null) {
				Toast.makeText(Friendhaoma.this, "网络加载失败..", 0).show();
				dia.dismiss();
			} else {
				dia.dismiss();
				Log.d("log", "---------------------result="+result);
				JSONObject object,object1;
				try {
					object = new JSONObject(result);
					int  code = object.getInt("responsecode"); 
					if(code == 10){
						String share = object.getString("result"); 
						if(share.equals("null")||share==null){
							Toast.makeText(Friendhaoma.this, "号码搜索失败..", 0).show();
						}else{
							object1 = new JSONObject(share);
							String phone = object1.getString("uphone"); 
							String address = object1.getString("uaddress"); 
							String photo = object1.getString("uphoto"); 
							String name = object1.getString("uname"); 
							Intent intent = new Intent(Friendhaoma.this, Frienddetail.class);
							intent.putExtra("name", name);
							intent.putExtra("photo", photo);
							intent.putExtra("address", address);
							intent.putExtra("phone", phone);
							startActivity(intent);
						}
						
						
						
					}
					if(code == 110){
						Toast.makeText(Friendhaoma.this, "已经添加", 0).show();
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
