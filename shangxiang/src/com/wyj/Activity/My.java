package com.wyj.Activity;

import com.wyj.dataprocessing.BitmapManager;
import com.wyj.dataprocessing.MyApplication;
import com.wyj.utils.FilePath;
import com.wyj.utils.Tools;
import com.wyj.Activity.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class My extends Activity implements OnClickListener {
	RelativeLayout action_login;
	TextView user;
	ImageView my_avatar_face;
	/* 头像名称 */
	private static final String IMAGE_FILE_NAME = MyApplication.getInstances()
			.getUserName() + "_faceImage.jpg";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my);
		findViewById();
		setListener();
		member_is_login();
	}

	private void findViewById() {

		my_avatar_face = (ImageView) findViewById(R.id.avatar);
		action_login = (RelativeLayout) findViewById(R.id.login_action);
		user = (TextView) findViewById(R.id.member_center_username);
	}

	private void setListener() {
		action_login.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_action:
			if (MyApplication.getInstances().getUserName() == "") {
				getParent().startActivityForResult(
						new Intent(My.this, Login.class), 1);

			} else {
				Intent intent = new Intent(My.this, User.class);
				UserGroupTab.getInstance().switchActivity("User", intent, -1,
						-1);
			}
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		Log.i("aaaa", "-------回来了My.java");
		member_is_login();
	}

	private void member_is_login() {

		String username = MyApplication.getInstances().getUserName();
		String avatar = MyApplication.getInstances().getHeadface();
		if (username != "") {
			user.setText(username);
			if (Tools.fileIsExists(FilePath.ROOT_DIRECTORY + IMAGE_FILE_NAME)) {
				Bitmap bitmap = BitmapFactory
						.decodeFile(FilePath.ROOT_DIRECTORY + IMAGE_FILE_NAME);
				my_avatar_face.setImageBitmap(bitmap);
			} else {
				BitmapManager.getInstance().loadBitmap(avatar, my_avatar_face,
						Tools.readBitmap(My.this, R.drawable.me));
			}

		} else {
			user.setText("立即登录");
		}
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
