package com.notbook.ui;

import java.io.File;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemLongClickListener;

import com.example.notbook.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.notbook.app.AppManager;
import com.notbook.app.CustomMultiPartEntity;
import com.notbook.app.Info;
import com.notbook.app.CustomMultiPartEntity.ProgressListener;
import com.notbook.app.StringUtil;
import com.notbook.app.cache.ImageLoader;
import com.notbook.db.Meta;
import com.notbook.entity.MPhone;
import com.notbook.entity.User;
import com.notbook.ui.Myaboutchuanyulist.ViewHolder;


public class FriendList extends BaseActivity {

	private Button iamgebutton1, iamgebutton2, iamgebutton3, iamgebutton4,
			iamgebutton5;
	private Button more_back, sender;
	private ListView mListView ; 
	Context mContext = null;
	private List<MPhone> things = new ArrayList<MPhone>();
	private ArrayList<MPhone> input_check_list = new ArrayList<MPhone>();
	private EditText friendtext ;
	private FriendListAdapter hlada = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		setContentView(R.layout.friendlist);
		findViewById(R.id.one).setBackgroundResource(background_photobg[num - 1]);
		findViewById(R.id.one2).setBackgroundResource(background_photobg1[num - 1]);
		findViewById(R.id.one1).setBackgroundResource(background_photobg1[num - 1]);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); 
		more_back = (Button) findViewById(R.id.more_back);
		sender = (Button) findViewById(R.id.sender);
		mListView = (ListView) findViewById(R.id.findshare11);
		friendtext = (EditText) findViewById(R.id.friendtext);
		
		
		String url = Info.uri+"/FellowListAction.php?uphone="+Login.uphone;
		
		new TextAsyncTask().execute(url);
		more_back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(FriendList.this, MainActivity1.class);
				startActivity(intent);
				FriendList.this.finish();
			}
		});
		
		sender.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(FriendList.this, FriendAdd.class);
				intent.putExtra("name", "FriendAdd2");
				startActivity(intent);
			}
		});
		friendtext.addTextChangedListener(new TextWatcher() {
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
					input_check_list = new ArrayList<MPhone>();
					
					for (int i = 0; i < things.size(); i++) {
						MPhone food = things.get(i);
						String content = "(?i)"+food.getUname();
						String uphone = food.getUphone();
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
					FriendListAdapter yuer = new FriendListAdapter(FriendList.this,input_check_list);
					mListView.setAdapter(yuer);
					yuer.notifyDataSetChanged();
					mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
								long id) {

							Intent it = new Intent(FriendList.this, Friendlistdetail.class);
							it.putExtra("name", input_check_list.get(arg2).getUname());
							it.putExtra("photo", input_check_list.get(arg2).getUphoto());
							it.putExtra("address",  input_check_list.get(arg2).getUaddress());
							it.putExtra("email",  input_check_list.get(arg2).getUemail());
							it.putExtra("job",  input_check_list.get(arg2).getJob());
							startActivity(it);
							FriendList.this.finish();
						}
					});
				} else {
					FriendListAdapter yuer = new FriendListAdapter(FriendList.this,things);
					mListView.setAdapter(yuer);
					yuer.notifyDataSetChanged();
					mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
								long id) {

							Intent it = new Intent(FriendList.this, Friendlistdetail.class);
							it.putExtra("name", things.get(arg2).getUname());
							it.putExtra("photo", things.get(arg2).getUphoto());
							it.putExtra("address",  things.get(arg2).getUaddress());
							it.putExtra("email",  things.get(arg2).getUemail());
							it.putExtra("job",  things.get(arg2).getJob());
							startActivity(it);
							FriendList.this.finish();
						}
					});
					
				}
			}
		});
		init();
	}

	public void init() {
		iamgebutton1 = (Button) findViewById(R.id.iamgebutton1);
		iamgebutton1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(FriendList.this, MainActivity0.class);
				startActivity(intent);
			}
		});
		iamgebutton2 = (Button) findViewById(R.id.iamgebutton2);
		iamgebutton2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(FriendList.this, MainActivity2.class);
				startActivity(intent);
			}
		});
		iamgebutton3 = (Button) findViewById(R.id.iamgebutton3);
		iamgebutton3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(FriendList.this, MainActivity3.class);
				startActivity(intent);
			}
		});
		iamgebutton4 = (Button) findViewById(R.id.iamgebutton4);
		iamgebutton4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(FriendList.this, MainActivity4.class);
				startActivity(intent);
			}
		});
		iamgebutton5 = (Button) findViewById(R.id.iamgebutton5);
		iamgebutton5.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(FriendList.this, MainActivity1.class);
				startActivity(intent);
			}
		});
	}
	@Override
	protected void onResume() {
		super.onResume();
		
	}
	class TextAsyncTask extends AsyncTask<String, Integer, String> {

		
		
		@Override
		protected String doInBackground(String... params) {
			return getResultByPost(params[0]);
		}
		@Override
		protected void onPreExecute() {
			dia = new ProgressDialog(FriendList.this);
			dia.setMessage("正在加载...");
			dia.show();
			dia.setCanceledOnTouchOutside(false);
			super.onPreExecute();
		}
		@Override
		protected void onPostExecute(String result) {
			if (result == null) {
				Toast.makeText(FriendList.this, "网络加载失败..", 0).show();
				dia.dismiss();
			} else {
				dia.dismiss();
				Log.d("log", "---------------------result="+result);
				JSONObject object;
				try {
					object = new JSONObject(result);
					String share = object.getString("result"); 
					Gson gson = new Gson();
					Type type = new TypeToken<List<MPhone>>() {}.getType();
					things = gson.fromJson(share, type);
					if(null != things&&!things.equals("")){
						Log.d("log", "---------------------things1111111111111="+things);
						hlada = new FriendListAdapter(FriendList.this, things);
						mListView.setAdapter(hlada);  
						mListView.setOnItemLongClickListener(new OnItemLongClickListener() {

							@Override
							public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
									final int poisition, final long arg3) {
								new AlertDialog.Builder(FriendList.this)
										.setTitle("温馨提示")
										.setMessage("确定删除好友?")
										.setNegativeButton("取消",
												new DialogInterface.OnClickListener() {
													public void onClick(DialogInterface dialog,
															int which) {
													}
												})
										.setPositiveButton("确定",
												new DialogInterface.OnClickListener() {
													public void onClick(DialogInterface dialog,
															int whichButton) {
														String url = Info.uri+"/DelFellowAction.php?uphone="+Login.uphone+"&fphone="+things.get(poisition).getUphone();
														
														new TextAsyncTask1().execute(url);
															Log.d("log", "---------------------url="+url);
														
															
														
														
														
													}
												}).show();

								return true;
							}
						});
						hlada.notifyDataSetChanged();
						
						mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
									long id) {

								Intent it = new Intent(FriendList.this, Friendlistdetail.class);
								it.putExtra("name", things.get(arg2).getUname());
								it.putExtra("photo", things.get(arg2).getUphoto());
								it.putExtra("address",  things.get(arg2).getUaddress());
								it.putExtra("email",  things.get(arg2).getUemail());
								it.putExtra("job",  things.get(arg2).getJob());
								startActivity(it);
								FriendList.this.finish();
							}
						});
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
				Toast.makeText(FriendList.this, "网络加载失败..", 0).show();
			} else {
				Log.d("log", "---------------------result="+result);
				JSONObject object;
				try {
					object = new JSONObject(result);
					int share1 = object.getInt("responsecode"); 
					String str = object.getString("msg"); 
					if(share1 == 10){
						Toast.makeText(getApplication(),"删除成功", 0).show();
						Intent intent = new Intent(FriendList.this, FriendList.class);
						startActivity(intent);
						hlada.notifyDataSetChanged();
						FriendList.this.finish();
					}
					if(share1 == 113){
						Toast.makeText(getApplication(),str, 0).show();
						
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
	class FriendListAdapter extends BaseAdapter {
		private Context xontext;
		private List<MPhone> things;
		private ImageLoader imageLoader = null;
		public FriendListAdapter(Context xontext, List<MPhone> things) {
			this.xontext = xontext;
			this.things = things;
			imageLoader = new ImageLoader(xontext);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return things.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = LayoutInflater.from(xontext).inflate(R.layout.share_list7, null);
				holder.friendname=(TextView)convertView.findViewById(R.id.friendname);
				holder.image =(ImageView)convertView.findViewById(R.id.image);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.friendname.setText(things.get(position).getUname());
			if(null != things.get(position).getUphoto() && !things.get(position).getUphoto().equals("")){
				imageLoader.DisplayImage(things.get(position).getUphoto(), holder.image, false);
			}
			
			return convertView;
		}
	}
	static class ViewHolder {
		TextView friendname;
		ImageView image;
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			Intent intent = new Intent(FriendList.this, MainActivity1.class);
			startActivity(intent);
			FriendList.this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}
	public static int ignoreIndexOf(String subject,String search,int soffset){  
        //当被查找字符串或查找子字符串为空时，抛出空指针异常。  
        if (subject == null || search == null) {  
            throw new NullPointerException("输入的参数为空");  
        }  
        if(soffset>=subject.length() && search.equals("")){  
            return subject.length();  
        }  
        for (int i = soffset; i < subject.length(); i++) {  
            if(subject.regionMatches(true, i, search, 0, search.length())){  
                return i;  
            }  
        }  
        return -1;  
    }  
}
