package com.notbook.ui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.notbook.R;
import com.notbook.app.CustomMultiPartEntity;
import com.notbook.app.Info;
import com.notbook.app.CustomMultiPartEntity.ProgressListener;
import com.notbook.ui.Personinformationedit.HttpMultipartPost;
import com.notbook.ui.Weather.DongTaiHolder;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ContactListAdapter3 extends BaseAdapter {
	int flag=0;
	private List<ContactBean> peoples;
	List list=new ArrayList();
	private Context context;
	private String number = "";
	public static String phone = "";
	public static String name = "";
	public static StringBuilder sb = null;
	public static StringBuilder sb1 = null;
	public ArrayList<ContactBean> things1 = null;
	private String share ="";
	private ViewHolder holder = null;
	private String beanname = null ;
	private String url = "";
	public ContactListAdapter3(List<ContactBean> peoples, Context context) {
	
		super();
		this.peoples = peoples;
		this.context = context;
		for(int i=0;i<peoples.size();i++){
			list.add("0");
		}
		peoples = new ArrayList<ContactBean>();
	}
	
	@Override
	public int getCount() {
		return peoples.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return peoples.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	class ViewHolder {
		TextView tv_name = null;
		TextView tv_number = null;
		ImageButton image = null;
		RelativeLayout contact = null ;
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ContactBean bean = peoples.get(position);
		if (null == convertView) {
			convertView = LayoutInflater.from(context).inflate(R.layout.share_list6, null);
			holder = new ViewHolder();
			holder.tv_name = (TextView) convertView.findViewById(R.id.name);
			holder.image = (ImageButton) convertView.findViewById(R.id.image);
			holder.tv_number = (TextView) convertView.findViewById(R.id.number01);
			holder.contact = (RelativeLayout) convertView.findViewById(R.id.contact);
			convertView.setTag(holder);
		} else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		if(list.get(position).toString().equals("0"))
		{
			list.set(position, "1");
			
		}
		holder.tv_name.setText(peoples.get(position).getContactName());
//		beanname = bean.getContactPhone().replaceAll(" ", "");
		
		if(null!=FriendAdd.things1&&!FriendAdd.things1.equals("")&&FriendAdd.things1.size()>0){
			if(FriendAdd.things1.get(position).getContactid().equals("1")){
				holder.tv_number.setText("添加");
				holder.image.setVisibility(View.VISIBLE);
				
				holder.contact.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						beanname = bean.getContactPhone().replaceAll(" ", "");
						
						url = Info.uri+"/addFellowAction.php?uphone="+Login.uphone;
						HttpMultipartPost task = new HttpMultipartPost(holder);
						task.execute();
						Toast.makeText(context, "添加成功", 0).show();
					}
				});
			}
			if(FriendAdd.things1.get(position).getContactid().equals("0")){
				holder.tv_number.setText("邀请");
				holder.image.setVisibility(View.INVISIBLE);
				holder.contact.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						beanname = bean.getContactPhone().replaceAll(" ", "");
						Log.i("log", "-----------------beanname="+beanname);
						String url = Info.uri+"/inviteAction.php?uphone="+Login.uphone+"&fphone="+beanname;
						new TextAsyncTask().execute(url);
					}
				});
			}
			if(FriendAdd.things1.get(position).getContactid().equals("2")){
				holder.tv_number.setText("已添加");
				holder.tv_number.setFocusable(false);
				holder.image.setVisibility(View.INVISIBLE);
			}
		}
		
	
		return convertView;
	}

	
	class HttpMultipartPost extends AsyncTask<HttpResponse, Integer, String> {
		ProgressDialog pd;
		long totalSize;
		Context context;

		private ViewHolder holder = null;
		
		public HttpMultipartPost(ViewHolder holder) {
			this.holder = holder;
			// TODO Auto-generated constructor stub
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
				

				multipartContent.addPart("fphone", new StringBody(beanname));
				totalSize = multipartContent.getContentLength();

				// Send it
				httpPost.setEntity(multipartContent);
				HttpResponse response = httpClient.execute(httpPost,
						httpContext);
				String serverResponse = EntityUtils.toString(response
						.getEntity());

				Log.i("log", "------------------serverResponse=" + serverResponse);
				holder.tv_number.setText("已添加");
				Intent intent = new Intent(context, Friendtongxunluhaoma.class);
				context.startActivity(intent);
				return serverResponse;
			}

			catch (Exception e) {
				System.out.println(e);
			}
			return null;
		}

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
				Log.i( "log", "-------------result"+result);
				JSONObject object,object1;
				try {
					
					object = new JSONObject(result);
					int share1 = object.getInt("responsecode"); 
					
					if(share1==10){
						if(flag == 0){
							Toast.makeText(context, "邀请成功", 0).show();
						}
						
					}else{
						Toast.makeText(context, "邀请失败", 0).show();
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
