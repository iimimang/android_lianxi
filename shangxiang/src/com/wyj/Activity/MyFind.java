package com.wyj.Activity;


import com.wyj.Activity.R;

import android.app.Activity;

import android.content.Intent;

import android.os.Bundle;

import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.ImageView;
import android.widget.LinearLayout;


public class MyFind extends Activity implements OnClickListener {

	private ImageView back_my;
	private LinearLayout my_bless_order_list, formy_bless_order_list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_find);
		findViewById();
		setListener();

	}

	private void findViewById() {

		back_my = (ImageView) findViewById(R.id.my_find_bless_back);
		my_bless_order_list = (LinearLayout) findViewById(R.id.my_bless_order_list);
		formy_bless_order_list = (LinearLayout) findViewById(R.id.formy_bless_order_list);
	}

	private void setListener() {
		back_my.setOnClickListener(this);
		my_bless_order_list.setOnClickListener(this);
		formy_bless_order_list.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.my_find_bless_back:

			Intent intent = new Intent(MyFind.this, My.class);
			UserGroupTab.getInstance().switchActivity("My", intent, -1, -1);

			break;

		case R.id.my_bless_order_list:

			break;

		case R.id.formy_bless_order_list:

			break;
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
