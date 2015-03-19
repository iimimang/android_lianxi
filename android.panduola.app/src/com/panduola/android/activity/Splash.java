package com.panduola.android.activity;

import com.panduola.android.PanDuoLa;
import com.panduola.android.R;
import com.panduola.android.services.AutoLoginService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.TextView;

public class Splash extends Activity {

	@Override
	public void onCreate(Bundle sinha) {
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(sinha);
		setContentView(R.layout.splash);

		TextView viewVersion = (TextView) findViewById(R.id.version_number);
		viewVersion.setText("Version " + PanDuoLa.VERSION);

		if (!TextUtils.isEmpty(PanDuoLa.APP.getMobile()) && !TextUtils.isEmpty(PanDuoLa.APP.getPassword())) {
			Intent intent = new Intent(PanDuoLa.APP, AutoLoginService.class);
			startService(intent);
		} else {
			PanDuoLa.APP.Logout();
		}
		goHome();
	}

	private void goHome() {
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent intent = new Intent(Splash.this, Navigation.class);
				Splash.this.startActivity(intent);
				Splash.this.finish();
			}
		}, 1000);
	}
}