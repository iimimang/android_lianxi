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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
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

public class Friend1 extends BaseActivity {

	private Button iamgebutton1, iamgebutton2, iamgebutton3, iamgebutton4,
			iamgebutton5;
	private Button more_back, sender;

	Context mContext = null;
	private ProgressDialog dia;
	/** 获取库Phon表字段 **/
	private static final String[] PHONES_PROJECTION = new String[] {
			Phone.DISPLAY_NAME, Phone.NUMBER, Phone.CONTACT_ID };
	/** 联系人显示名称 **/
	private static final int PHONES_DISPLAY_NAME_INDEX = 0;
	/** 电话号码 **/
	private static final int PHONES_NUMBER_INDEX = 1;
	/** 联系人的ID **/
	private static final int PHONES_CONTACT_ID_INDEX = 3;
	/** 联系人名称 **/
	private ArrayList<String> mContactsName = new ArrayList<String>();
	/** 联系人头像 **/
	private ArrayList<String> mContactsNumber = new ArrayList<String>();
	ListView mListView = null;
	public static String url = "";
	ArrayList<MPhone> lists = new ArrayList<MPhone>();
	private EditText friendtext ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		setContentView(R.layout.friend);
		findViewById(R.id.one).setBackgroundResource(
				background_photobg[num - 1]);
		findViewById(R.id.one2).setBackgroundResource(
				background_photobg1[num - 1]);
		findViewById(R.id.one1).setBackgroundResource(
				background_photobg1[num - 1]);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); 
		more_back = (Button) findViewById(R.id.more_back);
		sender = (Button) findViewById(R.id.sender);
		mListView = (ListView) findViewById(R.id.findshare11);
		friendtext = (EditText) findViewById(R.id.friendtext);
		more_back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
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
					ArrayList<MPhone> input_check_list = new ArrayList<MPhone>();
					for (int i = 0; i < lists.size(); i++) {
						MPhone food = lists.get(i);
						String content = food.getUname();
						int index = content.indexOf(input_str);
						System.out.println("index::" + index);
						if (index >= 0) {
							input_check_list.add(food);
						}
					}
					ContactListAdapter yuer = new ContactListAdapter(input_check_list,Friend1.this);
					mListView.setAdapter(yuer);
					yuer.notifyDataSetChanged();
				} else {
					ContactListAdapter yuer = new ContactListAdapter(lists,Friend1.this);
					mListView.setAdapter(yuer);
					yuer.notifyDataSetChanged();
				}
			}
		});
		sender.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String mtime = "";

				if (Celenderdetail.mtime.equals("正点提醒")) {
					mtime = "0";
				}
				if (Celenderdetail.mtime.equals("提前5分钟")) {
					mtime = "5";
				}
				if (Celenderdetail.mtime.equals("提前10分钟")) {
					mtime = "10";
				}
				if (Celenderdetail.mtime.equals("提前一小时")) {
					mtime = "60";
				}
				if (Celenderdetail.mtime.equals("提前一天")) {
					mtime = "1440";

				}
				StringBuffer sbphone = new StringBuffer();
				StringBuffer sbname = new StringBuffer();
				String phone = "";
				String name = "";
				Boolean flag = false;
				if (null != lists && !lists.equals("")) {
					for (int i = 0; i < lists.size(); i++) {
						MPhone bean = lists.get(i);
						if (bean.getCheckState() == true) {
							name = sbname.append(bean.getUname() + ",")
									.toString();
							phone = sbphone.append(bean.getUphone() + ",")
									.toString();
							flag = bean.getCheckState();
						}
					}
				}
				if (flag == false) {
					Toast.makeText(Friend1.this, "请选择好友", 0).show();
					return;
				}

				name = name.substring(0, name.lastIndexOf(","));

				phone = phone.replaceAll(" ", "");
				phone = phone.substring(0, phone.lastIndexOf(","));
				Log.i("log", "***********************name=" + name);
				Log.i("log", "***********************phone=" + phone);
				url = com.notbook.app.Info.uri
						+ "/chuanyuAdGetAction.php?uphone="
						+ Uri.encode(Login.uphone) + "&ringtime="
						+ Uri.encode(Celenderdetail.time) + "&advtime="
						+ Uri.encode(mtime) + "&recuser=" + Uri.encode(phone)
						+ "&text=" + Uri.encode(Celenderdetail.jishi) + "&tone="
						+ Uri.encode(Celenderdetail.lingyin) + "&first="
						+ Uri.encode(Celenderdetail.youxian) + "&recname="
						+ Uri.encode(name) + "&ctype="
						+ Uri.encode(Celenderdetail.leixing) + "&cname="
						+ Uri.encode(Celenderdetail.zhuti);
				if (Celenderdetail.imageurl.equals("") || Celenderdetail.imageurl == null) {
					new TextAsyncTask().execute(url);
				} else {
					HttpMultipartPost task = new HttpMultipartPost();
					task.execute();
				}

			}
		});
		init();
		runOnUiThread(run);
	}

	public void init() {
		iamgebutton1 = (Button) findViewById(R.id.iamgebutton1);
		iamgebutton1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Friend1.this, MainActivity0.class);
				startActivity(intent);
			}
		});
		iamgebutton2 = (Button) findViewById(R.id.iamgebutton2);
		iamgebutton2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Friend1.this, MainActivity2.class);
				startActivity(intent);
			}
		});
		iamgebutton3 = (Button) findViewById(R.id.iamgebutton3);
		iamgebutton3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Friend1.this, MainActivity3.class);
				startActivity(intent);
			}
		});
		iamgebutton4 = (Button) findViewById(R.id.iamgebutton4);
		iamgebutton4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Friend1.this, MainActivity4.class);
				startActivity(intent);
			}
		});
		iamgebutton5 = (Button) findViewById(R.id.iamgebutton5);
		iamgebutton5.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Friend1.this, MainActivity1.class);
				startActivity(intent);
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		runOnUiThread(run);
	}

	private final Runnable run = new Runnable() {
		@Override
		public void run() {
			String url = Info.uri + "/FellowListAction.php?uphone="
					+ Login.uphone;

			new TextAsyncTask1().execute(url);
			/*
			 * lists = ContactInfoService
			 * .getInstance(Friend.this).getContact(); Log.i( "log",
			 * "-------------str11111111111111="+lists);
			 * mListView.setAdapter(new ContactListAdapter(lists, Friend.this));
			 */
		}
	};

	class TextAsyncTask extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {
			return getResultByPost(params[0]);
		}

		@Override
		protected void onPreExecute() {
			dia = new ProgressDialog(Friend1.this);
			dia.setMessage("正在发送...");
			dia.show();
			dia.setCanceledOnTouchOutside(false);
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(String result) {
			if (result == null) {
				Toast.makeText(Friend1.this, "网络加载失败..", 0).show();
				dia.dismiss();
			} else {
				Log.i("log", "-------------result" + result);
				dia.dismiss();
				JSONObject object, object1;
				try {

					object = new JSONObject(result);
					String share = object.getString("result");
					int share1 = object.getInt("responsecode");
					if (share1 == 10) {
						Intent intent = new Intent(Friend1.this,
								Hudianlist.class);
						startActivity(intent);
						Toast.makeText(Friend1.this, "发送成功", 0).show();
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
				Toast.makeText(Friend1.this, "网络加载失败..", 0).show();
			} else {
				Log.d("log", "---------------------result=" + result);
				JSONObject object;
				try {
					object = new JSONObject(result);
					String share = object.getString("result");
					Gson gson = new Gson();
					Type type = new TypeToken<List<MPhone>>() {
					}.getType();
					lists = gson.fromJson(share, type);
					if (null != lists && !lists.equals("")) {
						Log.d("log", "---------------------lists=" + lists);
						mListView.setAdapter(new ContactListAdapter(lists,
								Friend1.this));
					}else{
						Toast.makeText(Friend1.this, "请添加好友", 0).show();
						Intent intent = new Intent(Friend1.this, FriendAdd.class);
						startActivity(intent);
						finish();
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
		try {
			HttpGet request = new HttpGet(url);
			HttpClient client = new DefaultHttpClient();
			HttpResponse response = client.execute(request);

			HttpEntity entity = response.getEntity();
			result = EntityUtils.toString(entity);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	class HttpMultipartPost extends AsyncTask<HttpResponse, Integer, String> {
		ProgressDialog pd;
		long totalSize;
		Context context;

		@Override
		protected void onPreExecute() {
			dia = new ProgressDialog(Friend1.this);
			dia.setMessage("正在发送...");
			dia.show();
			dia.setCanceledOnTouchOutside(false);
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(HttpResponse... arg0) {

			HttpClient httpClient = new DefaultHttpClient();
			HttpContext httpContext = new BasicHttpContext();
			HttpPost httpPost;
			httpPost = new HttpPost(url);
			CustomMultiPartEntity multipartContent = new CustomMultiPartEntity(
					new ProgressListener() {
						@Override
						public void transferred(long num) {
							publishProgress((int) ((num / (float) totalSize) * 100));
						}
					});
			try {

				File file = new File(Celenderdetail.imageurl);
				File file1 = new File(Celenderdetail.luyin);
				multipartContent.addPart("images", new FileBody(file));
				multipartContent.addPart("musics", new FileBody(file1));
				totalSize = multipartContent.getContentLength();

				// Send it
				httpPost.setEntity(multipartContent);
				HttpResponse response = httpClient.execute(httpPost,
						httpContext);
				String serverResponse = EntityUtils.toString(response
						.getEntity());

				Log.i("log", "�ϴ����------------------" + serverResponse);
				dia.dismiss();
				Intent intent = new Intent(Friend1.this, Hudianlist.class);
				startActivity(intent);
				Toast.makeText(Friend1.this, "发送成功", 0).show();
				return serverResponse;
			}

			catch (Exception e) {
				System.out.println(e);
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(Integer... progress) {
		}

		@Override
		protected void onPostExecute(String ui) {

		}
	}
}
