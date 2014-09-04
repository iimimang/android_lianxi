package com.notbook.ui;

import java.util.Timer;
import java.util.TimerTask;

import com.example.notbook.R;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Window;

public class Welcome extends BaseActivity {
	private SharedPreferences sp;
	private Editor editor;
	Intent it;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.welcome);
		Intent intent = new Intent(this, com.notebook.service.ChatService.class);
		startService(intent);
		sp = getSharedPreferences("config", Context.MODE_PRIVATE);
		editor = sp.edit();
		String flag = sp.getString("login_flag", "false");

		if (flag.equals("true")) {
			it = new Intent(getApplicationContext(), MainActivity3.class);
		} else {
			editor.putString("login_flag", "true");
			it = new Intent(this, com.notbook.ui.SwitchActivity.class); 
			editor.commit();

		}
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				startActivity(it); 
				Welcome.this.finish();
			}
		};

		timer.schedule(task, 1000 * 1); 

	}

}
