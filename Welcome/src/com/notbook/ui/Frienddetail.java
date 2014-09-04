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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notbook.R;
import com.notbook.app.CustomMultiPartEntity;
import com.notbook.app.Info;
import com.notbook.app.CustomMultiPartEntity.ProgressListener;
import com.notbook.app.cache.ImageLoader;
import com.notbook.ui.ContactListAdapter1.ViewHolder;


public class Frienddetail extends BaseActivity {

	private Button iamgebutton1, iamgebutton2, iamgebutton3, iamgebutton4,
			iamgebutton5;
	private Button more_back;
	private TextView name ;
	private TextView address ;
	private Button searchadd ;
	private String mphone ;//号码
	private ImageLoader imageLoader = null;
	private ImageButton ImageButton01 ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.frienddetail);
		findViewById(R.id.one).setBackgroundResource(background_photobg[num - 1]);
		findViewById(R.id.one2).setBackgroundResource(background_photobg1[num - 1]);
		findViewById(R.id.one1).setBackgroundResource(background_photobg1[num - 1]);
		more_back = (Button) findViewById(R.id.more_back);
		name = (TextView) findViewById(R.id.name);
		address = (TextView) findViewById(R.id.address);
		searchadd = (Button) findViewById(R.id.searchadd);
		ImageButton01 = (ImageButton) findViewById(R.id.ImageButton01);
		more_back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		init() ;
		Bundle b = getIntent().getExtras();
		if(null !=b && !b.equals("")){
			String mname = b.getString("name");
			String mphoto = b.getString("photo");
			String maddress = b.getString("address");
			mphone = b.getString("phone");
			if(null == mname ||mname.equals("null")){
				mname = "";
			}
			if(null == maddress ||maddress.equals("null")){
				maddress = "";
			}
			name.setText(mname);
			address.setText(maddress);
			imageLoader = new ImageLoader(Frienddetail.this);
			if(null != mphoto && !mphoto.equals("")){
				imageLoader.DisplayImage(mphoto, ImageButton01, false);
			}
		}
		searchadd.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String url = Info.uri+"/addFellowAction.php?uphone="+Login.uphone+"&fphone="+mphone;
				new TextAsyncTask().execute(url);
				Log.i("log", "-------------------url="+url);
			
			}
		});
		
		
	}

	public void init() {
		iamgebutton1 = (Button) findViewById(R.id.iamgebutton1);
		iamgebutton1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Frienddetail.this, MainActivity0.class);
				startActivity(intent);
			}
		});
		iamgebutton2 = (Button) findViewById(R.id.iamgebutton2);
		iamgebutton2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Frienddetail.this, MainActivity2.class);
				startActivity(intent);
			}
		});
		iamgebutton3 = (Button) findViewById(R.id.iamgebutton3);
		iamgebutton3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Frienddetail.this, MainActivity3.class);
				startActivity(intent);
			}
		});
		iamgebutton4 = (Button) findViewById(R.id.iamgebutton4);
		iamgebutton4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Frienddetail.this, MainActivity4.class);
				startActivity(intent);
			}
		});
		iamgebutton5 = (Button) findViewById(R.id.iamgebutton5);
		iamgebutton5.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Frienddetail.this, MainActivity1.class);
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
		protected void onPostExecute(String result) {
			if (result == null) {
			} else {
				Log.i("log", "-------------------result="+result);
				JSONObject object;
				try {
					object = new JSONObject(result);
					String share = object.getString("result"); 
					if(share.equals("true")){
						Intent intent = new Intent(Frienddetail.this, FriendList.class);
						startActivity(intent);
						Frienddetail.this.finish();
					}else{
						Toast.makeText(Frienddetail.this, "号码添加失败..", 0).show();
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
