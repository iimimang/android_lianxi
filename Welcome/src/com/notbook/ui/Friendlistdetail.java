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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notbook.R;
import com.notbook.app.cache.ImageLoader;
import com.notbook.ui.Login.TextAsyncTask;

public class Friendlistdetail extends BaseActivity {

	private Button more_back ,personinforedit;
	private TextView name,job,email,address;
	private ProgressDialog dia;
	public static String id = "",tname = "",tjob="",temail="",taddress="",tphoto="";
	private ImageLoader imageLoader = null;
	private ImageView image ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friendlistdetail);
		findViewById(R.id.one).setBackgroundResource(background_photobg[num - 1]);
		findViewById(R.id.one1).setBackgroundResource(background_photobg1[num - 1]);
		more_back = (Button)findViewById(R.id.more_back);
		personinforedit = (Button)findViewById(R.id.personinforedit);
		name = (TextView)findViewById(R.id.name);
		job = (TextView)findViewById(R.id.job);
		email = (TextView)findViewById(R.id.email);
		address = (TextView)findViewById(R.id.address);
		image = (ImageView)findViewById(R.id.image);
		
		
		more_back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent it = new Intent(Friendlistdetail.this,FriendList.class);
				startActivity(it);
				finish();
			}
		});
		personinforedit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent it = new Intent(Friendlistdetail.this,Personinformationedit.class);
				startActivity(it);
				finish();
			}
		});
		Bundle b = getIntent().getExtras();
		if(null!=b && !b.equals("")){
			tname = b.getString("name"); 
			tjob = b.getString("job"); 
			temail = b.getString("email"); 
			taddress = b.getString("address"); 
			tphoto = b.getString("photo"); 
			
			name.setText(tname);
			job.setText(tjob);
			email.setText(temail);
			address.setText(taddress);
			imageLoader = new ImageLoader(Friendlistdetail.this);
			if(null != tphoto && !tphoto.equals("")){
				Log.d("log", "------tphoto="+tphoto);
				imageLoader.DisplayImage(tphoto, image, false);
			}
		}
		
	}
	
}
