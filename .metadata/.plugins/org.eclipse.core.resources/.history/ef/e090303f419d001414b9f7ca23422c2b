package com.wyj.Activity;

import com.wyj.Activity.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;

import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class OrderForm extends Activity implements OnClickListener {

	private static String TAG = "OrderForm";

	private ImageView order_form_back;
	private ProgressDialog pDialog = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_form);

		findViewById();
		setListener();

	}

	private void findViewById() {

		order_form_back = (ImageView) findViewById(R.id.order_form_back);
	}

	private void setListener() {

		order_form_back.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.order_form_back:
			Intent bak_My_intent = new Intent(OrderForm.this, User.class);
			UserGroupTab.getInstance().switchActivity("User", bak_My_intent,
					-1, -1);
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
