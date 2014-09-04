package com.notbook.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.notbook.R;
import com.notbook.app.AppManager;

public class Celender extends BaseActivity {

	private Button eventdetail;
	private LinearLayout clalist;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.celender);
		eventdetail = (Button)findViewById(R.id.eventdetail);
		clalist = (LinearLayout)findViewById(R.id.clalist);
		RelativeLayout one = (RelativeLayout)findViewById(R.id.one);
		one.setBackgroundResource(background_photobg[num - 1]);
		eventdetail.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent it = new Intent(Celender.this,Eventlist.class);
				startActivity(it);
			}
		});
		
		clalist.setOnClickListener(new View.OnClickListener() {
			
			@Override  
			public void onClick(View v) {
				Intent it = new Intent(Celender.this,Celenderdetail.class);
				startActivity(it);
			}
		});
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			new AlertDialog.Builder(this)
					.setTitle("温馨提示")
					.setMessage("确认要退出吗？")
					.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
								}
							})
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									AppManager.getAppManager().finishAllActivity();
								}
							}).show();
		}

		return super.onKeyDown(keyCode, event);
	}
}
