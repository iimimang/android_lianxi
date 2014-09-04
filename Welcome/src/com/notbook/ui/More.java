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

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.notbook.R;
import com.notbook.app.AppManager;
import com.notbook.app.Info;
import com.notebook.update.UpdateService;
import com.weibo.joechan.ShareIndexAty;

public class More extends BaseActivity {

	private RelativeLayout login, personinformation, changpwd, returnlogin,
			tuisongxiaoxi, banbengenxin, qingchuhuanchu, huanfu01, about01,
			manage,one;
	private int NUM = 0;
	ContentResolver resolver;
	private ProgressDialog dia;
	public static String appName = "updateAppNoteBook";
	String verSionName1 = "1.0";
	private LinearLayout title001 ;
	private RelativeLayout friendlist;
	private RelativeLayout share;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.more);
		findViewById(R.id.one).setBackgroundResource(background_photobg[num - 1]);
		resolver = getContentResolver();
		// verSionName1=getAppVersionName(More.this);
		login = (RelativeLayout) findViewById(R.id.login01);
		personinformation = (RelativeLayout) findViewById(R.id.personinformation);
		changpwd = (RelativeLayout) findViewById(R.id.changpwd);
		returnlogin = (RelativeLayout) findViewById(R.id.returnlogin);
		tuisongxiaoxi = (RelativeLayout) findViewById(R.id.tuisongxiaoxi);
		banbengenxin = (RelativeLayout) findViewById(R.id.banbengenxin);
		qingchuhuanchu = (RelativeLayout) findViewById(R.id.qingchuhuanchu);
		huanfu01 = (RelativeLayout) findViewById(R.id.huanfu01);
		about01 = (RelativeLayout) findViewById(R.id.about01);
		manage = (RelativeLayout) findViewById(R.id.manage);
		friendlist = (RelativeLayout) findViewById(R.id.friendlist);
		share = (RelativeLayout) findViewById(R.id.share);
		one = (RelativeLayout) findViewById(R.id.one);  
		login.setVisibility(View.VISIBLE);
		personinformation.setVisibility(View.GONE);
		changpwd.setVisibility(View.GONE);
		returnlogin.setVisibility(View.GONE);
		friendlist.setVisibility(View.GONE);
		share.setVisibility(View.GONE);
		one.setBackgroundResource(background_photobg[num - 1]);
		findViewById(R.id.one1).setBackgroundResource(background_photobg1[num - 1]);
		login.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent it = new Intent(More.this, Login.class);
				it.putExtra("name", "more");
				startActivity(it);
			}
		});
		dia = new ProgressDialog(More.this);
		returnlogin.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(More.this)
						.setTitle("温馨提示")
						.setMessage("确认要退出吗？")
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
										login.setVisibility(View.VISIBLE);
										personinformation
												.setVisibility(View.GONE);
										changpwd.setVisibility(View.GONE);
										returnlogin.setVisibility(View.GONE);
										friendlist.setVisibility(View.GONE);
										share.setVisibility(View.GONE);
										Login.NUM = 0;
										Toast.makeText(getApplicationContext(),
												"退出成功", 0).show();
									}
								}).show();

				/*
				 * Intent it = new Intent(More.this,MainActivity1.class);
				 * startActivity(it);
				 */
			}
		});
		tuisongxiaoxi.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent it = new Intent(More.this, Tuisongxiaoxi.class);
				startActivity(it);
			}
		});
		share.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent it = new Intent(More.this, ShareIndexAty.class);
				startActivity(it);
			}
		});
		friendlist.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent it = new Intent(More.this, FriendList.class);
				startActivity(it);
			}
		});
		banbengenxin.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(More.this)
						.setTitle("版本更新")
						.setMessage("确认要更新吗？")
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
										String url = Info.uri+"/versioncontrolAction.php?iname=android"
												+ verSionName1;
										System.out.println("log"
												+ "-------------url" + url);
										new TextAsyncTask().execute(url);
									}
								}).show();
			}
		});
		qingchuhuanchu.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(More.this)
						.setTitle("清除缓存")
						.setMessage("确认要清除吗？")
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
										int n1 = 0;
										n1 = resolver
												.delete(com.notbook.weatherdb.Meta.CONTENT_URI,
														null, null);
										int n = 0;
										n = resolver
												.delete(com.notbook.db.Meta.CONTENT_URI,
														null, null);
										if (n > 0 || n1 > 0) {
											
											dia.setMessage("正在清除...");
											dia.show();
											handler.sendMessage(new Message());
											Toast.makeText(
													getApplicationContext(),
													"清除成功", 0).show();

										}
										if (n == 0 || n1 ==0) {
											dia.setMessage("正在清除...");
											dia.show();
											handler.sendMessage(new Message());
											Toast.makeText(
													getApplicationContext(),
													"没有数据清除", 0).show();
										}
									}
								}).show();
			}
		});

		huanfu01.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent it = new Intent(More.this, Huanfu.class);
				startActivity(it);
			}
		});
		about01.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent it = new Intent(More.this, About.class);
				startActivity(it);
			}
		});
		personinformation.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent it = new Intent(More.this, Personinformation.class);
				startActivity(it);
			}
		});
		changpwd.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent it = new Intent(More.this, Changepwd.class);
				startActivity(it);
			}
		});
		manage.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent it = new Intent(More.this, Manager.class);
				startActivity(it);
			}
		});
		if (Login.NUM == 1) {
			login.setVisibility(View.GONE);
			personinformation.setVisibility(View.VISIBLE);
			changpwd.setVisibility(View.VISIBLE);
			returnlogin.setVisibility(View.VISIBLE);
			friendlist.setVisibility(View.VISIBLE);
			share.setVisibility(View.GONE);
			NUM = 1;
		}
		if (NUM == 1) {
			login.setVisibility(View.GONE);
			personinformation.setVisibility(View.VISIBLE);
			changpwd.setVisibility(View.VISIBLE);
			returnlogin.setVisibility(View.VISIBLE);
			friendlist.setVisibility(View.VISIBLE);
			share.setVisibility(View.GONE);
		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			new AlertDialog.Builder(this)
					.setTitle("温馨提示")
					.setMessage("确认要退出吗？")
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

									AppManager.getAppManager()
											.finishAllActivity();
								}
							}).show();
		}
		return super.onKeyDown(keyCode, event);
	}

	class TextAsyncTask extends AsyncTask<String, Integer, String> {
		@Override
		protected void onPreExecute() {
			dia = new ProgressDialog(More.this);
			dia.setMessage("正在加载...");
			dia.show();
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			return getResultByPost(params[0]);
		}

		@Override
		protected void onPostExecute(String result) {
			System.out.println("log" + "-------------result" + result);
			if (result == null) {
				Toast.makeText(More.this, "网络加载失败..", 0).show();
			} else {
				dia.dismiss();
				JSONObject object;
				try {
					object = new JSONObject(result);
					int responsecode = object.getInt("responsecode");
					System.out.println("====================" + responsecode);
					if (responsecode ==48) {
						Toast.makeText(More.this, "版本更新失败..", 0).show();
					} else if (responsecode == 49) {
						Intent intent = new Intent(More.this,
								UpdateService.class);
						String url1 = object.getString("resulturl");
						appName = object.getString("resultname");
						System.out.println("====================" + url1);
						intent.putExtra("Key_App_Name", appName);
						intent.putExtra("Key_Down_Url", url1);
						startService(intent);
						 Toast.makeText(More.this, "更新中..", 0).show();
					} else if (responsecode == 50) {
						Toast.makeText(More.this, "安卓暂无新版本..", 0).show();
					} else {
						Toast.makeText(More.this, "更新失败..", 0).show();
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
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
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
				dia.dismiss();
		}

	};
}
