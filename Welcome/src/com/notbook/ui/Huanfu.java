package com.notbook.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.notbook.R;
import com.notbook.app.AppManager;

public class Huanfu extends BaseActivity {

	private Button more_back;
	private RelativeLayout background1, background2, background3, background4,background5, background6,one;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.huanfu);
		more_back = (Button) findViewById(R.id.more_back);
		background1 = (RelativeLayout) findViewById(R.id.background1);
		one = (RelativeLayout) findViewById(R.id.one);
		background2 = (RelativeLayout) findViewById(R.id.background2);
		background3 = (RelativeLayout) findViewById(R.id.background3);
		background4 = (RelativeLayout) findViewById(R.id.background4);
		background5 = (RelativeLayout) findViewById(R.id.background5);
		background6 = (RelativeLayout) findViewById(R.id.background6);
		one.setBackgroundResource(background_photobg[num - 1]);
		findViewById(R.id.titlecolor).setBackgroundResource(background_photobg1[num - 1]);
		if(num==1){
			background1.setBackgroundResource(R.drawable.brownblock1);
			background2.setBackgroundResource(R.drawable.lanse1);
			background3.setBackgroundResource(R.drawable.shense2);
			background4.setBackgroundResource(R.drawable.moren1);
			background5.setBackgroundResource(R.drawable.blurnormal1);
			background6.setBackgroundResource(R.drawable.blurnormal2);
		}
		if(num == 2){
			background2.setBackgroundResource(R.drawable.lanse2);
			background1.setBackgroundResource(R.drawable.brownblock);
			background3.setBackgroundResource(R.drawable.shense2);
			background4.setBackgroundResource(R.drawable.moren1);
			background5.setBackgroundResource(R.drawable.blurnormal1);
			background6.setBackgroundResource(R.drawable.blurnormal2);
		}
		if(num == 3){
			background3.setBackgroundResource(R.drawable.shense1);
			background2.setBackgroundResource(R.drawable.lanse1);
			background1.setBackgroundResource(R.drawable.brownblock);
			background4.setBackgroundResource(R.drawable.moren1);
			background5.setBackgroundResource(R.drawable.blurnormal1);
			background6.setBackgroundResource(R.drawable.blurnormal2);
		}
		if(num == 4){
			background4.setBackgroundResource(R.drawable.moren2);
			background3.setBackgroundResource(R.drawable.shense2);
			background2.setBackgroundResource(R.drawable.lanse1);
			background1.setBackgroundResource(R.drawable.brownblock);
			background5.setBackgroundResource(R.drawable.blurnormal1);
			background6.setBackgroundResource(R.drawable.blurnormal2);
		}
		if(num == 5){
			background4.setBackgroundResource(R.drawable.moren1);
			background3.setBackgroundResource(R.drawable.shense2);
			background2.setBackgroundResource(R.drawable.lanse1);
			background1.setBackgroundResource(R.drawable.brownblock);
			background5.setBackgroundResource(R.drawable.blurselected1);
			background6.setBackgroundResource(R.drawable.blurnormal2);
		}
		if(num == 6){
			background4.setBackgroundResource(R.drawable.moren1);
			background3.setBackgroundResource(R.drawable.shense2);
			background2.setBackgroundResource(R.drawable.lanse1);
			background1.setBackgroundResource(R.drawable.brownblock);
			background5.setBackgroundResource(R.drawable.blurnormal1);
			background6.setBackgroundResource(R.drawable.blurselected2);
		}
		more_back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent it = new Intent(Huanfu.this,MainActivity1.class);
				startActivity(it);
				finish();
			}
		});
		background1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				one.setBackgroundResource(R.drawable.aa3);
				background4.setBackgroundResource(R.drawable.moren1);
				background1.setBackgroundResource(R.drawable.brownblock1);
				background2.setBackgroundResource(R.drawable.lanse1);
				background3.setBackgroundResource(R.drawable.shense2);
				background5.setBackgroundResource(R.drawable.blurnormal1);
				background6.setBackgroundResource(R.drawable.blurnormal2);
				findViewById(R.id.titlecolor).setBackgroundResource(R.drawable.title);
				// 获得SharedPreferences对象
				SharedPreferences MyPreferences = getSharedPreferences(
						PREFERENCES_NAME, Activity.MODE_PRIVATE);
				// 获得SharedPreferences.Editor
				SharedPreferences.Editor editor = MyPreferences.edit();
				editor.putInt("zhuti", 1);
				editor.commit();
			}
		});
		background2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				one.setBackgroundResource(R.drawable.aa2);
				background2.setBackgroundResource(R.drawable.lanse2);
				background4.setBackgroundResource(R.drawable.moren1);
				background1.setBackgroundResource(R.drawable.brownblock);
				background3.setBackgroundResource(R.drawable.shense2);
				background5.setBackgroundResource(R.drawable.blurnormal1);
				background6.setBackgroundResource(R.drawable.blurnormal2);
				findViewById(R.id.titlecolor).setBackgroundResource(R.drawable.title);
				// 获得SharedPreferences对象
				SharedPreferences MyPreferences = getSharedPreferences(
						PREFERENCES_NAME, Activity.MODE_PRIVATE);
				// 获得SharedPreferences.Editor
				SharedPreferences.Editor editor = MyPreferences.edit();
				editor.putInt("zhuti", 2);
				editor.commit();
			}
		});
		background3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				one.setBackgroundResource(R.drawable.aa1);
				background3.setBackgroundResource(R.drawable.shense1);
				background2.setBackgroundResource(R.drawable.lanse1);
				background4.setBackgroundResource(R.drawable.moren1);
				background1.setBackgroundResource(R.drawable.brownblock);
				background5.setBackgroundResource(R.drawable.blurnormal1);
				background6.setBackgroundResource(R.drawable.blurnormal2);
				findViewById(R.id.titlecolor).setBackgroundResource(R.drawable.title);
				// 获得SharedPreferences对象
				SharedPreferences MyPreferences = getSharedPreferences(
						PREFERENCES_NAME, Activity.MODE_PRIVATE);
				// 获得SharedPreferences.Editor
				SharedPreferences.Editor editor = MyPreferences.edit();
				editor.putInt("zhuti", 3);
				editor.commit();
			}
		});
		background4.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				one.setBackgroundResource(R.drawable.homepage1);
				background3.setBackgroundResource(R.drawable.shense2);
				background2.setBackgroundResource(R.drawable.lanse1);
				background4.setBackgroundResource(R.drawable.moren2);
				background1.setBackgroundResource(R.drawable.brownblock);
				background5.setBackgroundResource(R.drawable.blurnormal1);
				background6.setBackgroundResource(R.drawable.blurnormal2);
				findViewById(R.id.titlecolor).setBackgroundResource(R.drawable.topbackground);
				// 获得SharedPreferences对象
				SharedPreferences MyPreferences = getSharedPreferences(
						PREFERENCES_NAME, Activity.MODE_PRIVATE);
				// 获得SharedPreferences.Editor
				SharedPreferences.Editor editor = MyPreferences.edit();
				editor.putInt("zhuti", 4);
				editor.commit();
			}
		});
		background5.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				one.setBackgroundResource(R.drawable.darkbluepresent);
				background3.setBackgroundResource(R.drawable.shense2);
				background2.setBackgroundResource(R.drawable.lanse1);
				background4.setBackgroundResource(R.drawable.moren1);
				background1.setBackgroundResource(R.drawable.brownblock);
				background5.setBackgroundResource(R.drawable.blurselected1);
				background6.setBackgroundResource(R.drawable.blurnormal2);
				findViewById(R.id.titlecolor).setBackgroundResource(R.drawable.title);
				// 获得SharedPreferences对象
				SharedPreferences MyPreferences = getSharedPreferences(
						PREFERENCES_NAME, Activity.MODE_PRIVATE);
				// 获得SharedPreferences.Editor
				SharedPreferences.Editor editor = MyPreferences.edit();
				editor.putInt("zhuti", 5);
				editor.commit();
			}
		});
		background6.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				one.setBackgroundResource(R.drawable.darkgraypresent);
				background3.setBackgroundResource(R.drawable.shense2);
				background2.setBackgroundResource(R.drawable.lanse1);
				background4.setBackgroundResource(R.drawable.moren1);
				background1.setBackgroundResource(R.drawable.brownblock);
				background5.setBackgroundResource(R.drawable.blurnormal1);
				background6.setBackgroundResource(R.drawable.blurselected2);
				findViewById(R.id.titlecolor).setBackgroundResource(R.drawable.title);
				// 获得SharedPreferences对象
				SharedPreferences MyPreferences = getSharedPreferences(
						PREFERENCES_NAME, Activity.MODE_PRIVATE);
				// 获得SharedPreferences.Editor
				SharedPreferences.Editor editor = MyPreferences.edit();
				editor.putInt("zhuti", 6);
				editor.commit();
			}
		});

	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			Intent it = new Intent(Huanfu.this,MainActivity1.class);
			startActivity(it);
			finish();
		}

		return super.onKeyDown(keyCode, event);
	}
}
