package com.wyj.Activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.wyj.adapter.ListTempleAdapter;
import com.wyj.dataprocessing.AccessNetwork;
import com.wyj.dataprocessing.JsonToListHelper;
import com.wyj.dataprocessing.MyApplication;
import com.wyj.dataprocessing.RegularUtil;
import com.wyj.http.WebApiUrl;
import com.wyj.utils.FilePath;
import com.wyj.utils.Tools;
import com.wyj.Activity.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class UserInfo extends Activity implements OnClickListener {

	EditText user_info_truename_input;
	TextView user_info_sex_input;
	EditText user_info_address_input;
	Button user_info_submit;
	private static String TAG = "UserInfo";

	private ImageView userinfo_edit_back;
	private ProgressDialog pDialog = null;

	private int user_sex;
	private String user_truename;
	private String user_address;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.userinfo_edit);

		findViewById();
		setListener();

	}

	private void findViewById() {

		user_info_truename_input = (EditText) findViewById(R.id.user_info_truename_input);
		user_info_sex_input = (TextView) findViewById(R.id.user_info_sex_input);
		user_info_address_input = (EditText) findViewById(R.id.user_info_address_input);

		if (MyApplication.getInstances().getSex() == 1) {
			user_info_sex_input.setText("男");
		} else if (MyApplication.getInstances().getSex() == 2) {
			user_info_sex_input.setText("女");
		} else {
			user_info_sex_input.setText("未填写");
			Resources resource = (Resources) getBaseContext().getResources();
			ColorStateList csl = (ColorStateList) resource
					.getColorStateList(R.color.text_hint);
			user_info_sex_input.setTextColor(csl);
		}
		user_info_truename_input.setText((MyApplication.getInstances()
				.getTruename() != "") ? (MyApplication.getInstances()
				.getTruename()) : "");
		user_info_address_input
				.setText((MyApplication.getInstances().getArea() != "") ? (MyApplication
						.getInstances().getArea()) : "");

		user_info_submit = (Button) findViewById(R.id.user_info_submit);
		userinfo_edit_back = (ImageView) findViewById(R.id.userinfo_edit_back);
	}

	private void setListener() {
		user_info_submit.setOnClickListener(this);
		userinfo_edit_back.setOnClickListener(this);
		user_info_sex_input.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.user_info_submit:

			submitData();
			break;
		case R.id.userinfo_edit_back:
			Intent bak_My_intent = new Intent(UserInfo.this, User.class);
			UserGroupTab.getInstance().switchActivity("User", bak_My_intent,
					-1, -1);
			break;
		case R.id.user_info_sex_input:
			sex_select();
			break;

		}
	}

	private void submitData() {

		String truename = user_info_truename_input.getText().toString();
		String sexslect = user_info_sex_input.getText().toString();
		int sex = 0;
		if (sexslect.equals("男")) {
			sex = 1;
		} else if (sexslect.equals("女")) {
			sex = 2;
		}

		String address = user_info_address_input.getText().toString();

		if (!RegularUtil.checkTrueName(this, truename)) {

			// 设置焦点信息;
			user_info_truename_input.setFocusable(true);
			user_info_truename_input.setFocusableInTouchMode(true);
			user_info_truename_input.requestFocus();
			user_info_truename_input.requestFocusFromTouch();
		} else if (!RegularUtil.checkAddress(this, address)) {

			// 设置焦点信息;
			user_info_address_input.setFocusable(true);
			user_info_address_input.setFocusableInTouchMode(true);
			user_info_address_input.requestFocus();
			user_info_address_input.requestFocusFromTouch();

		} else {

			server_api_userinfo(truename, sex, address, MyApplication
					.getInstances().getUserName()); // 接口请求
		}

	}

	private void server_api_userinfo(String truename, int sex, String address,
			String username) {
		// TODO Auto-generated method stub
		// 利用Handler更新UI
		final Handler h = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 0x123) {

					pDialog.dismiss();
					String backmsg = msg.obj.toString();
					Map<String, Object> resmsg = new HashMap<String, Object>();
					resmsg = JsonToListHelper.jsontoCode(backmsg);
					if (resmsg.get("code").equals("succeed")) {
						RegularUtil.alert_msg(UserInfo.this, "修改成功");
						update_app_userinfo();
						Intent bak_My_intent = new Intent(UserInfo.this,
								User.class);
						UserGroupTab.getInstance().switchActivity("User",
								bak_My_intent, -1, -1);
					} else {

						RegularUtil.alert_msg(UserInfo.this,
								"修改失败，" + resmsg.get("msg"));
					}

					Log.i(TAG, "------线程返回信息" + resmsg.toString());
				}
			}
		};
		String params = "mobile=" + username + "&truename=" + truename
				+ "&area=" + address + "&sex=" + sex;
		user_address = address;
		user_sex = sex;
		user_truename = truename;
		pDialog = new ProgressDialog(UserInfo.this.getParent());
		pDialog.setMessage("请求中。。。");
		pDialog.show();
		new Thread(new AccessNetwork("POST", WebApiUrl.MEMBERINFO, params, h))
				.start();

	}

	protected void update_app_userinfo() {
		// TODO Auto-generated method stub

		MyApplication.getInstances().setSex(user_sex);// 设置用户ID
		MyApplication.getInstances().setArea(user_address);// 设置用户名
		MyApplication.getInstances().setTruename(user_truename);// 设置用户名
	}

	private void sex_select() {
		Log.i(TAG, "------弹窗了没有");
		// TODO Auto-generated method stub
		final String[] arrayFruit = new String[] { "男", "女" };

		Dialog alertDialog = new AlertDialog.Builder(UserInfo.this.getParent())
				.setItems(arrayFruit, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						user_info_sex_input.setText(arrayFruit[which]);

					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				}).create();
		alertDialog.show();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			UserGroupTab.getInstance().onKeyDown(keyCode, event);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}