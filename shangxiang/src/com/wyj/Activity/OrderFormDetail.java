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

public class OrderFormDetail extends Activity implements OnClickListener {

	private static String TAG = "OrderFormDetail";

	private ImageView order_form_detail_back;
	private ProgressDialog pDialog = null;
	private Button detail_button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_form_detail);

		findViewById();
		setListener();

	}

	private void findViewById() {

		order_form_detail_back = (ImageView) findViewById(R.id.order_form_detail_back);
		detail_button =(Button) findViewById(R.id.order_form_detail_submit);
	}

	private void setListener() {

		order_form_detail_back.setOnClickListener(this);
		detail_button.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.order_form_detail_back:
			Intent bak_My_intent = new Intent(OrderFormDetail.this, ListTemple.class);
			WishGroupTab.getInstance().switchActivity("ListTemple", bak_My_intent,
					-1, -1);
			break;
		case R.id.order_form_detail_submit:
			Intent intent2 = new Intent(OrderFormDetail.this, OrderFormPay.class);
			WishGroupTab.getInstance().switchActivity("OrderFormPay", intent2,
					-1, -1);
			break;

		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			WishGroupTab.getInstance().onKeyDown(keyCode, event);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}