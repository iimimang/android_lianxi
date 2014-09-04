package com.notbook.ui;

import com.example.notbook.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

/**
 * Ӧ�ó�����ҳ
 * 
 * @author zhangguoyue
 * @version 1.0
 * @created 2013-3-5
 */

public class MainActivity3 extends TabActivity implements OnClickListener{
	
	private TextView tv_1;
	private TabHost mTabHost;
	private SharedPreferences sp;
	private Editor editor;

	private android.widget.LinearLayout ll_main_tab0;
	private android.widget.LinearLayout ll_main_tab1;
	private android.widget.LinearLayout ll_main_tab2;  
	private android.widget.LinearLayout ll_main_tab3;
	private android.widget.LinearLayout ll_main_tab4;
	private android.widget.LinearLayout one;
	public static final String PREFERENCES_NAME = "survey";
	public int[] background_photobg1 = { R.drawable.title_2,
			R.drawable.title_1, R.drawable.title_3, R.drawable.topbackground, R.drawable.title_5, R.drawable.title_6};
	public int num = 5;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
         
        
		mTabHost = getTabHost();

		sp = getSharedPreferences("config", Context.MODE_PRIVATE);
		editor = sp.edit();
		TabSpec dy = mTabHost.newTabSpec("home").setIndicator("home")
				.setContent(new Intent(this, com.notbook.calendar.CalendarView.class));
		
		mTabHost.addTab(dy);
		
		
			TabSpec gz = mTabHost.newTabSpec("personCerter")
					.setIndicator("personCerter")
					.setContent(new Intent(this, ChuanYu.class));
			mTabHost.addTab(gz);
	
		

		TabSpec sc = mTabHost.newTabSpec("store").setIndicator("store")
				.setContent(new Intent(this, Home.class));
		mTabHost.addTab(sc);

		TabSpec zdy = mTabHost.newTabSpec("knowledge")
				.setIndicator("knowledge")
				.setContent(new Intent(this, Eventlist.class));
		mTabHost.addTab(zdy);

		TabSpec my = mTabHost.newTabSpec("more").setIndicator("more")
				.setContent(new Intent(this, More.class));
		mTabHost.addTab(my);
		
		
		
		initView();
		initEvent();

		// ѡ��Ĭ��tab
		// String tabId = sp.getString("tabId", "Plaza");
		mTabHost.setCurrentTabByTag("store");// 
		ll_main_tab2.setBackgroundResource(R.drawable.icon2_3);
		SharedPreferences preferences = getSharedPreferences(PREFERENCES_NAME,
				Activity.MODE_PRIVATE);
		num = preferences.getInt("zhuti", num);
		findViewById(R.id.one).setBackgroundResource(background_photobg1[num - 1]);
	}

	

	private void initView() {
		// TODO Auto-generated method stub

		ll_main_tab0 = (LinearLayout) findViewById(R.id.ll_main_tab0);
		ll_main_tab1 = (LinearLayout) findViewById(R.id.ll_main_tab1);
		ll_main_tab2 = (LinearLayout) findViewById(R.id.ll_main_tab2);
		ll_main_tab3 = (LinearLayout) findViewById(R.id.ll_main_tab3);
		ll_main_tab4 = (LinearLayout) findViewById(R.id.ll_main_tab4);

	}

	private void initEvent() {
		// TODO Auto-generated method stub

		ll_main_tab0.setOnClickListener(this);
		ll_main_tab1.setOnClickListener(this);
		ll_main_tab2.setOnClickListener(this);
		ll_main_tab3.setOnClickListener(this);
		ll_main_tab4.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {

		case R.id.ll_main_tab0:
			mTabHost.setCurrentTabByTag("home");
			changeBack(0);
			editor.putString("tabId", "home");
			editor.commit();
			break;
		case R.id.ll_main_tab1:
			mTabHost.setCurrentTabByTag("personCerter");
			changeBack(1);
			editor.putString("tabId", "personCerter");
			editor.commit();
			break;
		case R.id.ll_main_tab2:
			mTabHost.setCurrentTabByTag("store");
			changeBack(2);
			editor.putString("tabId", "store");
			editor.commit();
			break;
		case R.id.ll_main_tab3:

			mTabHost.setCurrentTabByTag("knowledge");
			changeBack(3);
			editor.putString("tabId", "knowledge");
			editor.commit();

			break;

		case R.id.ll_main_tab4:
			mTabHost.setCurrentTabByTag("more");
			changeBack(4);
			editor.putString("tabId", "more");
			editor.commit();
			break;

		default:
			break;
		}
	}

	private void changeBack(int i) {

		switch (i) {
		case 0:

			ll_main_tab0.setBackgroundResource(R.drawable.icon2_1);
			ll_main_tab1.setBackgroundResource(R.drawable.icon1_2);
			ll_main_tab2.setBackgroundResource(R.drawable.icon1_3);
			ll_main_tab3.setBackgroundResource(R.drawable.icon1_4);
			ll_main_tab4.setBackgroundResource(R.drawable.icon1_5);

			break;
		case 1:

			ll_main_tab0.setBackgroundResource(R.drawable.icon1_1);
			ll_main_tab1.setBackgroundResource(R.drawable.icon2_2);
			ll_main_tab2.setBackgroundResource(R.drawable.icon1_3);
			ll_main_tab3.setBackgroundResource(R.drawable.icon1_4);
			ll_main_tab4.setBackgroundResource(R.drawable.icon1_5);

			break;
		case 2:

			ll_main_tab0.setBackgroundResource(R.drawable.icon1_1);
			ll_main_tab1.setBackgroundResource(R.drawable.icon1_2);
			ll_main_tab2.setBackgroundResource(R.drawable.icon2_3);
			ll_main_tab3.setBackgroundResource(R.drawable.icon1_4);
			ll_main_tab4.setBackgroundResource(R.drawable.icon1_5);

			break;
		case 3:

			ll_main_tab0.setBackgroundResource(R.drawable.icon1_1);
			ll_main_tab1.setBackgroundResource(R.drawable.icon1_2);
			ll_main_tab2.setBackgroundResource(R.drawable.icon1_3);
			ll_main_tab3.setBackgroundResource(R.drawable.icon2_4);
			ll_main_tab4.setBackgroundResource(R.drawable.icon1_5);

			break;
		case 4:

			ll_main_tab0.setBackgroundResource(R.drawable.icon1_1);
			ll_main_tab1.setBackgroundResource(R.drawable.icon1_2);
			ll_main_tab2.setBackgroundResource(R.drawable.icon1_3);
			ll_main_tab3.setBackgroundResource(R.drawable.icon1_4);
			ll_main_tab4.setBackgroundResource(R.drawable.icon2_5);

			break;

		default:
			break;
		}

	}

}
