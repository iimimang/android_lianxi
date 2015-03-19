package com.panduola.android.activity;


import com.panduola.android.R;

import com.panduola.android.utils.Utils;


import android.app.Activity;

import android.os.Bundle;

import android.view.View;
import android.view.View.OnClickListener;

import android.widget.Button;


public class Register extends Activity implements OnClickListener {

	private Button buttonBack;


	@Override
	public void onCreate(Bundle sinha) {
		super.onCreate(sinha);
		setContentView(R.layout.register);

	}

	

	@Override
	public void onClick(View v) {
		Utils.hideKeyboard(this);
		if (v == this.buttonBack) {
			finish();
		}
	
	}

}