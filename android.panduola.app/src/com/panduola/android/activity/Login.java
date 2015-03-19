package com.panduola.android.activity;


import com.panduola.android.R;
import com.panduola.android.pipe.SinhaPipeClient;
import com.panduola.android.pipe.SinhaPipeMethod;
import com.panduola.android.utils.Utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

public class Login extends Activity implements OnClickListener, OnTouchListener {
	private SinhaPipeClient httpClient;
	private SinhaPipeMethod httpMethod;
	private RelativeLayout layoutLoading;
	private ScrollView viewMain;
	private ImageView viewLogo;
	private Button buttonClose;
	private EditText inputMobile;
	private EditText inputPassword;
	private Button buttonForgetPassword;
	private Button buttonRegister;
	private Button buttonLogin;
	private LinearLayout layoutOtherLogin;
	private Button buttonWechatLogin;
	private Button buttonWeiboLogin;
	private boolean showLoading = false;
	private boolean isSubmiting = false;
	private int keyboardHeight = 0;


	@Override
	public void onCreate(Bundle sinha) {
		super.onCreate(sinha);
		setContentView(R.layout.login);

		this.httpClient = new SinhaPipeClient();
	

		this.layoutLoading = (RelativeLayout) findViewById(R.id.loading);
		this.viewMain = (ScrollView) findViewById(R.id.login_main_layout);
		this.viewMain.setOnTouchListener(this);
		this.viewLogo = (ImageView) findViewById(R.id.logo);
		this.buttonClose = (Button) findViewById(R.id.login_close_button);
		this.buttonClose.setOnClickListener(this);
		this.inputMobile = (EditText) findViewById(R.id.login_mobile_input);
		this.inputPassword = (EditText) findViewById(R.id.login_password_input);
		this.buttonForgetPassword = (Button) findViewById(R.id.login_forget_password_button);
		this.buttonForgetPassword.setOnClickListener(this);
		this.buttonRegister = (Button) findViewById(R.id.login_register_button);
		this.buttonRegister.setOnClickListener(this);
		this.buttonLogin = (Button) findViewById(R.id.login_submit_button);
		this.buttonLogin.setOnClickListener(this);
		this.layoutOtherLogin = (LinearLayout) findViewById(R.id.login_other_login_layout);
		this.buttonWechatLogin = (Button) findViewById(R.id.login_wechat_button);
		this.buttonWechatLogin.setOnClickListener(this);
		this.buttonWeiboLogin = (Button) findViewById(R.id.login_weibo_button);
		this.buttonWeiboLogin.setOnClickListener(this);

		final View viewRoot = findViewById(R.id.login_layout);
		viewRoot.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				int intDiffHeight = viewRoot.getRootView().getHeight() - viewRoot.getHeight();
				if (keyboardHeight == intDiffHeight) {
					return;
				}
				keyboardHeight = intDiffHeight;
				if (intDiffHeight > 100) {
					buttonClose.setVisibility(View.GONE);
					viewLogo.setVisibility(View.GONE);
					layoutOtherLogin.setVisibility(View.GONE);
				} else {
					buttonClose.setVisibility(View.VISIBLE);
					viewLogo.setVisibility(View.VISIBLE);
					layoutOtherLogin.setVisibility(View.VISIBLE);
				}
			}
		});
	}

	private void showLoading() {
		Utils.animView(this.layoutLoading, !this.showLoading);
		this.showLoading = !this.showLoading;
	}



	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
//		switch (resultCode) {
//		case RESULT_OK:
//			switch (requestCode) {
//			case 99:
//				finish();
//				break;
//			default:
//				
//				break;
//			}
//			break;
//		default:
//			break;
//		}
	}

	
	
	@Override
	public void onClick(View v) {
		Utils.hideKeyboard(this);
		if (v == this.buttonClose) {
			finish();
		}
		
		if (v == this.buttonRegister) {
			Intent intent = new Intent(this, Register.class);
			this.startActivityForResult(intent, 99);
		}
		if (v == this.buttonForgetPassword) {
		//	Intent intent = new Intent(this, ForgetPassword.class);
		//	this.startActivity(intent);
		}
		
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		onClick(v);
		return false;
	}
}