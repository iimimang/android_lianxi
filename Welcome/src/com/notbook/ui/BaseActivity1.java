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
public class BaseActivity1 extends Activity implements UncaughtExceptionHandler {

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

	

}
