package com.notbook.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.notbook.R;

public class Tuisongxiaoxi extends BaseActivity {

	private Button more_back ,button1;
	private RelativeLayout xiaoxituisong ; 
	private int flag = 1 ;
	private SharedPreferences sp;
	private Editor editor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tuisongxiaoxi);
		findViewById(R.id.one).setBackgroundResource(background_photobg[num - 1]);
		findViewById(R.id.one1).setBackgroundResource(background_photobg1[num - 1]);
		more_back = (Button)findViewById(R.id.more_back);
		button1 = (Button)findViewById(R.id.button1);
		xiaoxituisong = (RelativeLayout)findViewById(R.id.xiaoxituisong);
		
		more_back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		sp = getSharedPreferences("config12", Context.MODE_PRIVATE);
		Log.d("log", "---------------------------flag=" + flag);
		editor = sp.edit();
		flag = sp.getInt("flag11", 0);
		Log.d("log", "---------------------------flag+=" + flag);
		if (flag == 1||flag == 0) {
			button1.setBackgroundResource(R.drawable.on);
		} else {
			button1.setBackgroundResource(R.drawable.off);
		}
		xiaoxituisong.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (flag == 1) {
					button1.setBackgroundResource(R.drawable.off);
					flag = 2;
					editor.putInt("flag11", flag);
					editor.commit();
					Log.d("log", "---------------------------flag=" + flag);
					Intent intent1 = new Intent(Tuisongxiaoxi.this, com.notebook.service.ChatService.class);
					stopService(intent1);
					
				} else {
					button1.setBackgroundResource(R.drawable.on);
					flag = 1;
					editor.putInt("flag11", flag);
					editor.commit();
					Log.d("log", "---------------------------flag=" + flag);
					Intent intent1 = new Intent(Tuisongxiaoxi.this, com.notebook.service.ChatService.class);
					startService(intent1);
				}
			}
		});
		button1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (flag == 1) {
					button1.setBackgroundResource(R.drawable.off);
					flag = 2;
					editor.putInt("flag11", flag);
					editor.commit();
					Log.d("log", "---------------------------flag=" + flag);
				} else {
					button1.setBackgroundResource(R.drawable.on);
					flag = 1;
					editor.putInt("flag11", flag);
					editor.commit();
					Log.d("log", "---------------------------flag=" + flag);
				}
			}
		});
		
	}
}
