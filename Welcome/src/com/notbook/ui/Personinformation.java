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

public class Personinformation extends BaseActivity {

	private Button more_back ,personinforedit;
	private TextView name,job,email,address;
	private ProgressDialog dia;
	public static String id = "",tname = "",tjob="",temail="",taddress="",tphoto="";
	private ImageLoader imageLoader = null;
	private ImageView image ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.personinfor);
		findViewById(R.id.one).setBackgroundResource(background_photobg[num - 1]);
		findViewById(R.id.one1).setBackgroundResource(background_photobg1[num - 1]);
		more_back = (Button)findViewById(R.id.more_back);
		personinforedit = (Button)findViewById(R.id.personinforedit);
		name = (TextView)findViewById(R.id.name);
		job = (TextView)findViewById(R.id.job);
		email = (TextView)findViewById(R.id.email);
		address = (TextView)findViewById(R.id.address);
		image = (ImageView)findViewById(R.id.image);
		
		
		String url = com.notbook.app.Info.uri+"/getuserAction.php?userid="+Login.id;
		Log.d( "log", "-------------str"+url);
        new TextAsyncTask()
    	.execute(url);
		more_back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent it = new Intent(Personinformation.this,MainActivity1.class);
				startActivity(it);
				finish();
			}
		});
		personinforedit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent it = new Intent(Personinformation.this,Personinformationedit.class);
				startActivity(it);
				finish();
			}
		});
	}
	class TextAsyncTask extends AsyncTask<String, Integer, String> {

		@Override
		protected void onPreExecute() {
			dia = new ProgressDialog(Personinformation.this);
			dia.setMessage("正在加载...");
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
				dia.dismiss();
				Toast.makeText(Personinformation.this, "网络加载失败..", 0).show();
			} else {
				dia.dismiss();
				Log.d( "log", "-------------result"+result);
				
				JSONObject object,object1;
				try {
					
					object = new JSONObject(result);
					String share = object.getString("result"); 
					if(null != share && !share.equals("")){
						object1 = new JSONObject(share);
						id = object1.getString("id");   
						tname = object1.getString("uname"); 
						tjob = object1.getString("job"); 
						temail = object1.getString("uemail"); 
						taddress = object1.getString("uaddress"); 
						tphoto = object1.getString("uphoto"); 
						
						if(tname==null||tname.equals("null")){
							tname = "";
						}
						if(tjob==null||tjob.equals("null")){
							tjob = "";
						}
						if(temail==null||temail.equals("null")){
							temail = "";
						}
						if(taddress==null||taddress.equals("null")){
							taddress = "";
						}
						name.setText(tname);
						job.setText(tjob);
						email.setText(temail);
						address.setText(taddress);
						imageLoader = new ImageLoader(Personinformation.this);
						if(null != tphoto && !tphoto.equals("")){
							Log.d("log", "------tphoto="+tphoto);
							imageLoader.DisplayImage(tphoto, image, false);
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
