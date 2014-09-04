package com.notbook.ui;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
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
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.notbook.R;
import com.notbook.app.CustomMultiPartEntity;
import com.notbook.app.StringUtil;
import com.notbook.app.CustomMultiPartEntity.ProgressListener;
import com.notbook.app.Info;


public class Friendtongxunluhaoma extends BaseActivity {

	private Button iamgebutton1, iamgebutton2, iamgebutton3, iamgebutton4,
			iamgebutton5;
	private Button more_back;
	Context mContext = null;
	private EditText friendtongxunlutext ;
	private ListView mListView = null;
	private ListView mListView1 = null;
	private ListView mListView2= null;
	public static ArrayList<ContactBean> things1 = null;
	ArrayList<ContactBean> lists = new ArrayList<ContactBean>();
	ArrayList<ContactBean> list = new ArrayList<ContactBean>();
	ArrayList<ContactBean> list1 = new ArrayList<ContactBean>();
	ArrayList<ContactBean> list2 = new ArrayList<ContactBean>();
	ArrayList<ContactBean> list3 = new ArrayList<ContactBean>();
	String name = "";
	String phone = "";
	String id = "";
	String url = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		setContentView(R.layout.friendtongxunluhaoma);
		findViewById(R.id.one).setBackgroundResource(background_photobg[num - 1]);
		findViewById(R.id.one2).setBackgroundResource(background_photobg1[num - 1]);
		findViewById(R.id.one1).setBackgroundResource(background_photobg1[num - 1]);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); 
		more_back = (Button) findViewById(R.id.more_back);
		friendtongxunlutext = (EditText) findViewById(R.id.friendtongxunlutext);
		mListView = (ListView) findViewById(R.id.findshare11);
		mListView1 = (ListView) findViewById(R.id.findshare22);
		mListView2 = (ListView) findViewById(R.id.findshare33);
		more_back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Friendtongxunluhaoma.this, FriendAdd.class);
				intent.putExtra("name", "FriendAdd2");
				startActivity(intent);
				Friendtongxunluhaoma.this.finish();
			}
		});
		
		
		
		lists = ContactInfoService
				.getInstance(Friendtongxunluhaoma.this).getContact();
		
		
		
		
		
		for(int i=0;i<lists.size();i++){
			for(int j=0;j<FriendAdd.things1.size();j++){
				if(lists.get(i).getContactPhone().equals(FriendAdd.things1.get(j).getContactPhone())){
					id = FriendAdd.things1.get(j).getContactid();
					lists.get(i).setContactid(id);
				}
				
				
			}	
			
		}
		Log.i( "log", "-------------lists="+lists);
		Log.i( "log", "-------------FriendAdd.things1="+FriendAdd.things1);
		for(int i=0;i<lists.size();i++){
			if(null==lists.get(i).getContactid()){
				lists.get(i).setContactid("0");
			}
			if(lists.get(i).getContactid().equals("0")){
				ContactBean cb = new ContactBean();
				cb.setContactid(lists.get(i).getContactid());
				cb.setContactName(lists.get(i).getContactName());
				cb.setContactPhone(lists.get(i).getContactPhone());
				cb.setContactHomePhone(lists.get(i).getContactHomePhone());
				cb.setCheckState(lists.get(i).getCheckState());
				list1.add(cb);
			}	
			
		}
		Log.i( "log", "-------------list1="+list1);
		for(int i=0;i<lists.size();i++){
			if(null==lists.get(i).getContactid()){
				lists.get(i).setContactid("0");
			}
			if(lists.get(i).getContactid().equals("1")){
				ContactBean cb = new ContactBean();
					cb.setContactid(lists.get(i).getContactid());
					cb.setContactName(lists.get(i).getContactName());
					cb.setContactPhone(lists.get(i).getContactPhone());
					cb.setContactHomePhone(lists.get(i).getContactHomePhone());
				cb.setCheckState(lists.get(i).getCheckState());
				list.add(cb);
			}	
			
		}
		Log.i( "log", "-------------list="+list);
	
		for(int i=0;i<lists.size();i++){
			if(null==lists.get(i).getContactid()){
				lists.get(i).setContactid("0");
			}
			if(lists.get(i).getContactid().equals("2")){
				ContactBean cb = new ContactBean();
				cb.setContactid(lists.get(i).getContactid());
				cb.setContactName(lists.get(i).getContactName());
				cb.setContactPhone(lists.get(i).getContactPhone());
				cb.setContactHomePhone(lists.get(i).getContactHomePhone());
				cb.setCheckState(lists.get(i).getCheckState());
				list2.add(cb);
			}	
			
		}
		Log.i( "log", "-------------list2="+list2);
		
		list3.addAll(list);
		list3.addAll(list1);
		list3.addAll(list2);
		
		Log.i( "log", "-------------list3="+list3);
		ContactListAdapter1 yuer = new ContactListAdapter1(
				list3,Friendtongxunluhaoma.this);
		mListView.setAdapter(yuer);
		yuer.notifyDataSetChanged();
		
		/*ArrayList<ContactBean> input_check_list = new ArrayList<ContactBean>();
		for (int i = 0; i < lists.size(); i++) {
			ContactBean food = lists.get(i);
			String content = food.getContactid();
			int index = content.indexOf("0");
			if (index >= 0) {
				input_check_list.add(food);
			}
		}
		ContactListAdapter1 yuer = new ContactListAdapter1(
				input_check_list,Friendtongxunluhaoma.this);
		mListView.setAdapter(yuer);
		yuer.notifyDataSetChanged();
		
		
		ArrayList<ContactBean> input_check_list1 = new ArrayList<ContactBean>();
		for (int i = 0; i < lists.size(); i++) {
			ContactBean food = lists.get(i);
			String content = food.getContactid();
			int index = content.indexOf("1");
			if (index >= 0) {
				input_check_list1.add(food);
			}
		}
		ContactListAdapter2 yuer1 = new ContactListAdapter2(
				input_check_list1,Friendtongxunluhaoma.this);
		mListView1.setAdapter(yuer1);
		yuer1.notifyDataSetChanged();*/
		
		
		
		/*ArrayList<ContactBean> input_check_list2 = new ArrayList<ContactBean>();
		for (int i = 0; i < lists.size(); i++) {
			ContactBean food = lists.get(i);
			String content = food.getContactid();
			int index = content.indexOf("2");
			if (index >= 0) {
				input_check_list2.add(food);
			}
		}
		ContactListAdapter3 yuer2 = new ContactListAdapter3(
				input_check_list2,Friendtongxunluhaoma.this);
		mListView2.setAdapter(yuer2);
		yuer2.notifyDataSetChanged();*/
		
		
		
		String searchStr = friendtongxunlutext.getText().toString();
		friendtongxunlutext.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				String input_str = s.toString();
				System.out.println("input_str::" + input_str);
				if (input_str != null && !"".equals(input_str)
						&& input_str.length() > 0) {
					ArrayList<ContactBean> input_check_list = new ArrayList<ContactBean>();
					for (int i = 0; i < lists.size(); i++) {
						ContactBean food = lists.get(i);
						String content = food.getContactName();
						String uphone = food.getContactPhone();
						int index = StringUtil.ignoreIndexOf(content, input_str);
						int index1 = uphone.indexOf(input_str);
						System.out.println("index::" + index);
						if (index >= 0) {
							input_check_list.add(food);
						}
						if (index1 >= 0) {
							input_check_list.add(food);
						}
					}
					ContactListAdapter1 yuer = new ContactListAdapter1(
							input_check_list,Friendtongxunluhaoma.this);
					mListView.setAdapter(yuer);
					yuer.notifyDataSetChanged();
				} else {
					ContactListAdapter1 yuer = new ContactListAdapter1(
							list3,Friendtongxunluhaoma.this);
					mListView.setAdapter(yuer);
					yuer.notifyDataSetChanged();
				}
			}
		});
		
		
		
//		StringBuilder sb = new StringBuilder();;
//		for(int i=0;i<lists.size();i++){
//			name = lists.get(i).getContactName();
//			phone = lists.get(i).getContactPhone();
////			phone = phone.replaceAll(" ", "");
//			phone = sb.append(phone).append(",").toString();
//		}
//		phone = phone.substring(0, phone.lastIndexOf(","));
//		
//		/*String url = Info.uri+"/searchFellowAction.php?uphone="+Login.uphone+"&fphone="+phone;
//	
//		new TextAsyncTask().execute(url);*/
//		
//		handler.sendMessage(new Message());
		
		
//		mListView.setAdapter(new ContactListAdapter1(lists, Friendtongxunluhaoma.this));
		init();
	}

	public void init() {
		iamgebutton1 = (Button) findViewById(R.id.iamgebutton1);
		iamgebutton1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Friendtongxunluhaoma.this, MainActivity0.class);
				startActivity(intent);
			}
		});
		iamgebutton2 = (Button) findViewById(R.id.iamgebutton2);
		iamgebutton2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Friendtongxunluhaoma.this, MainActivity2.class);
				startActivity(intent);
			}
		});
		iamgebutton3 = (Button) findViewById(R.id.iamgebutton3);
		iamgebutton3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Friendtongxunluhaoma.this, MainActivity3.class);
				startActivity(intent);
			}
		});
		iamgebutton4 = (Button) findViewById(R.id.iamgebutton4);
		iamgebutton4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Friendtongxunluhaoma.this, MainActivity4.class);
				startActivity(intent);
			}
		});
		iamgebutton5 = (Button) findViewById(R.id.iamgebutton5);
		iamgebutton5.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Friendtongxunluhaoma.this, MainActivity1.class);
				startActivity(intent);
			}
		});
	}
	
	private final Runnable run = new Runnable() {
		@Override
		public void run() {
			
		}

		
	};
	class TextAsyncTask extends AsyncTask<String, Integer, String> {

		
		
		@Override
		protected String doInBackground(String... params) {
			return getResultByPost(params[0]);
		}
		
		@Override
		protected void onPostExecute(String result) {
			if (result == null) {
			} else {
				Log.i( "log", "-------------result"+result);
				JSONObject object,object1;
				try {
					
					object = new JSONObject(result);
					String share = object.getString("result"); 
					int share1 = object.getInt("responsecode"); 
					things1 = new ArrayList<ContactBean>();
					String str1[]  = share.split("\\,");
					for(int i=0;i<str1.length;i++){
						ContactBean con = new ContactBean();
						String name1 = str1[i];
						con.setContactid(name1);
						things1.add(con);
					}
					
					ContactListAdapter1 con= new ContactListAdapter1(list3, Friendtongxunluhaoma.this);
					mListView.setAdapter(con);
					con.notifyDataSetInvalidated();
					con.notifyDataSetChanged();
					
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
	class HttpMultipartPost extends AsyncTask<HttpResponse, Integer, String> {
		ProgressDialog pd;
		long totalSize;
		Context context;

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
//					int share1 = object.getInt("responsecode"); 
					things1 = new ArrayList<ContactBean>();
					String str1[]  = share.split("\\,");
					for(int i=0;i<str1.length;i++){
						ContactBean con = new ContactBean();
						String name1 = str1[i];
						con.setContactid(name1);
						things1.add(con);
					}
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
}
