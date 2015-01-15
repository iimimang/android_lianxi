package com.app.note;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

import com.app.note.app.MyApplication;

public class StartActivity extends Activity implements OnClickListener {
	private Button loginButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		MyApplication.getInstances().addActivity(StartActivity.this);
		intview();
		setListener();
	}

	private void intview() {
		loginButton = (Button) findViewById(R.id.login_btn);
	}

	private void setListener() {
		loginButton.setOnClickListener(this);

	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_btn:
			finish();
			Intent intent = new Intent();
			intent.setClass(StartActivity.this, MainActivity.class);
			startActivity(intent);
			this.overridePendingTransition(R.anim.push_left_in,
					R.anim.push_left_out);
			break;

		}
	}
}
