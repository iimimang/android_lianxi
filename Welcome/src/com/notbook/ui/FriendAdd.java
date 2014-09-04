package com.notbook.ui;

import java.io.File;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
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
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.notbook.R;
import com.notbook.app.CustomMultiPartEntity;
import com.notbook.app.Info;
import com.notbook.app.CustomMultiPartEntity.ProgressListener;
import com.notbook.ui.Friendtongxunluhaoma.HttpMultipartPost;


public class FriendAdd extends BaseActivity {

	private Button iamgebutton1, iamgebutton2, iamgebutton3, iamgebutton4,
			iamgebutton5;
	private Button more_back, sender;
	Context mContext = null;
	private LinearLayout tongxxunlu ; 
	private LinearLayout souhaoma ; 
	ArrayList<ContactBean> lists = null;
	ContactInfoService cin = null;
	public static ArrayList<ContactBean> things1 = new ArrayList<ContactBean>();
	String name = "";
	String phone = "";
	String str = "";
	String url = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		setContentView(R.layout.friendadd);
		findViewById(R.id.one).setBackgroundResource(background_photobg[num - 1]);
		findViewById(R.id.one2).setBackgroundResource(background_photobg1[num - 1]);
		findViewById(R.id.one1).setBackgroundResource(background_photobg1[num - 1]);
		more_back = (Button) findViewById(R.id.more_back);
		tongxxunlu = (LinearLayout) findViewById(R.id.tongxxunlu);
		souhaoma = (LinearLayout) findViewById(R.id.souhaoma);
		more_back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle b = getIntent().getExtras();
				if(null!=b&&!b.equals("")){
					String name = b.getString("name");
					if(name.equals("FriendAdd1")){
						Intent intent = new Intent(FriendAdd.this, Friend.class);
						startActivity(intent);
						finish();
					}
					if(name.equals("FriendAdd2")){
						Intent intent = new Intent(FriendAdd.this, FriendList.class);
						startActivity(intent);
						finish();
					}
				}
				
				
				
				
			}
		});
		souhaoma.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(FriendAdd.this, Friendhaoma.class);
				startActivity(intent);
			}
		});
		tongxxunlu.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(FriendAdd.this, Friendtongxunluhaoma.class);
				startActivity(intent);
			}
		});
		cin = new ContactInfoService(mContext);
		lists = new ArrayList<ContactBean>();
		lists = cin.getContact();

		StringBuilder sb = new StringBuilder();
		for(int i=0;i<lists.size();i++){
			name = lists.get(i).getContactName();
			phone = lists.get(i).getContactPhone();   
//			if(phone.contains(" "))
//			{
//				phone = phone.replace(" ", "");
//			}
			phone = sb.append(phone).append(",").toString();
		}
		if(!phone.equals("")&&phone.length()>1){
			phone = phone.substring(0, phone.lastIndexOf(","));
		}
		
		/*String url = Info.uri+"/searchFellowAction.php?uphone="+Login.uphone+"&fphone="+phone;
	
		new TextAsyncTask().execute(url);*/
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				handler.sendMessage(new Message());
			}
		}).start();
		
		
		init();
	}

	public void init() {
		iamgebutton1 = (Button) findViewById(R.id.iamgebutton1);
		iamgebutton1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(FriendAdd.this, MainActivity0.class);
				startActivity(intent);
			}
		});
		iamgebutton2 = (Button) findViewById(R.id.iamgebutton2);
		iamgebutton2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(FriendAdd.this, MainActivity2.class);
				startActivity(intent);
			}
		});
		iamgebutton3 = (Button) findViewById(R.id.iamgebutton3);
		iamgebutton3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(FriendAdd.this, MainActivity3.class);
				startActivity(intent);
			}
		});
		iamgebutton4 = (Button) findViewById(R.id.iamgebutton4);
		iamgebutton4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(FriendAdd.this, MainActivity4.class);
				startActivity(intent);
			}
		});
		iamgebutton5 = (Button) findViewById(R.id.iamgebutton5);
		iamgebutton5.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(FriendAdd.this, MainActivity1.class);
				startActivity(intent);
			}
		});
	}

	class HttpMultipartPost extends AsyncTask<HttpResponse, Integer, String> {
		ProgressDialog pd;
		long totalSize;
		Context context;
		@Override
		protected void onPreExecute() {
			dia = new ProgressDialog(FriendAdd.this);
			dia.setMessage("正在加载...");
			dia.show();
			dia.setCanceledOnTouchOutside(false);
			super.onPreExecute();
		}
		@Override
		protected String doInBackground(HttpResponse... arg0) {

			HttpClient httpClient = new DefaultHttpClient();
			HttpContext httpContext = new BasicHttpContext();
			HttpPost httpPost;
			Log.i("log", "-------------------url="+url);
			httpPost = new HttpPost(url);
			
			CustomMultiPartEntity multipartContent = new CustomMultiPartEntity(new ProgressListener() {
				@Override
				public void transferred(long num) {
					publishProgress((int) ((num / (float) totalSize) * 100));
				}
			});
			try {
				
				Log.i("log", "-------------------phone="+phone);
				multipartContent.addPart("fphone", new StringBody(phone));
				totalSize = multipartContent.getContentLength();

				// Send it
				httpPost.setEntity(multipartContent);
				HttpResponse response = httpClient.execute(httpPost,
						httpContext);
				String serverResponse = EntityUtils.toString(response
						.getEntity());

				Log.i("log", "------------------serverResponse=" + serverResponse);
				
				
				JSONObject object,object1;
				try {
					object = new JSONObject(serverResponse);
					String share = object.getString("result"); 
					Log.i("log", "------------------share=" + share);
					object1 = new JSONObject(share);
					lists = ContactInfoService.getInstance(FriendAdd.this).getContact();
					String mphone  = "";
					for(int i=0;i<lists.size();i++){
						mphone = lists.get(i).getContactPhone();
//						if(mphone.contains(" ")){
//							mphone = mphone.replace(" ", "");
//						}
						Log.i("log", "------------------mphone=" + mphone);
						
					}
					StringBuilder sb = new StringBuilder();
					StringBuilder sb1 = new StringBuilder();
					String values = "";
					String str = "";
					for(int i= 0;i<object1.length();i++){
						str = object1.names().getString(i);
						values = object1.getString(str);
						Log.i("log", "------------------str=" + str);
						Log.i("log", "------------------values=" + values);
						values = sb.append(values).append(",").toString();
						str = sb1.append(str).append(",").toString();
					}
					if(!values.equals("")&&values.length()>0){
						values = values.substring(0, values.lastIndexOf(","));
					}
					if(!str.equals("")&&str.length()>0){
						str = str.substring(0, str.lastIndexOf(","));
					}
					String str1[]  = values.split("\\,");
					String str2[]  = str.split("\\,");
					things1.clear();
					for(int i=0;i<str1.length;i++){
						ContactBean con = new ContactBean();
						String name1 = str1[i];
						String phone1 = str2[i];
						con.setContactid(name1);
						con.setContactPhone(phone1);
						things1.add(con);
					}
					dia.dismiss();
					Log.i("log", "------------------things1=" + things1);
					
				} catch (JSONException e) {
				e.printStackTrace();
				}
				
				return serverResponse;
			}

			catch (Exception e) {
				System.out.println(e);
			}
			return null;
		}

	}
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			url = Info.uri+"/searchFellowAction.php?uphone="+Login.uphone;
			HttpMultipartPost task = new HttpMultipartPost();
			task.execute();
		}

	};
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			FriendAdd.this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}
}
