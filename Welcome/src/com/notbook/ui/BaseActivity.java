package com.notbook.ui;

import java.lang.Thread.UncaughtExceptionHandler;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.example.notbook.R;
import com.notbook.app.AppManager;

/**
 * Ӧ�ó���Activity�Ļ���
 * 
 * @author zhangguoyue
 * @version 1.0
 * @created 2013-3-5
 */
public class BaseActivity extends Activity implements UncaughtExceptionHandler {

	InputMethodManager imm;
	public static final String PREFERENCES_NAME = "survey";
	public int[] background_photobg = { R.drawable.aa3,
			R.drawable.aa2, R.drawable.aa1, R.drawable.homepage1, R.drawable.darkbluepresent, R.drawable.darkgraypresent};
	public int[] background_photobg1 = { R.drawable.title,
			R.drawable.title, R.drawable.title, R.drawable.topbackground, R.drawable.title, R.drawable.title};
	public int num = 5;
	public ProgressDialog dia;
	private Thread.UncaughtExceptionHandler defaultExceptionHandler; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SharedPreferences preferences = getSharedPreferences(PREFERENCES_NAME,
				Activity.MODE_PRIVATE);
		num = preferences.getInt("zhuti", num);
		imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		// ���Activity����ջ
		AppManager.getAppManager().addActivity(this);
		   defaultExceptionHandler  = Thread.getDefaultUncaughtExceptionHandler();  
		   
		   Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
			   public void uncaughtException(Thread thread, Throwable ex) {
			   //任意一个线程异常后统一的处理
				   AppManager.getAppManager().finishAllActivity();
			   }
			   });
	}
	
	@Override 
    public void uncaughtException(Thread thread, Throwable exception) { 
        // TODO Auto-generated method stub 
		AppManager.getAppManager().finishAllActivity();
    }
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.d("log", "nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn");
		// ����Activity&�Ӷ�ջ���Ƴ�
		AppManager.getAppManager().finishActivity(this);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			System.out.println("down");
			if (BaseActivity.this.getCurrentFocus() != null) {
				if (BaseActivity.this.getCurrentFocus().getWindowToken() != null) {
					imm.hideSoftInputFromWindow(BaseActivity.this
							.getCurrentFocus().getWindowToken(),
							InputMethodManager.HIDE_NOT_ALWAYS);
				}
			}
		}
		return super.onTouchEvent(event);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			// 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
			View v = getCurrentFocus();
			if (isShouldHideInput(v, ev)) {
				hideSoftInput(v.getWindowToken());
			}
		}
		return super.dispatchTouchEvent(ev);
	}

	private boolean isShouldHideInput(View v, MotionEvent event) {
		if (v != null && (v instanceof EditText)) {
			int[] l = { 0, 0 };
			v.getLocationInWindow(l);

			int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
					+ v.getWidth();
			if (event.getX() > left && event.getX() < right
					&& event.getY() > top && event.getY() < bottom) {
				// 点击EditText的事件，忽略它。
				return false;
			} else {
				return true;
			}
		}
		return false;
	}

	private void hideSoftInput(IBinder token) {
		if (token != null) {
			InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			im.hideSoftInputFromWindow(token,
					InputMethodManager.HIDE_NOT_ALWAYS);
		}

	}

}
