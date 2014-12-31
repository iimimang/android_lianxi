package com.wyj.Activity;


import com.wyj.dataprocessing.MyApplication;
import com.wyj.tabmenu.R;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.RelativeLayout;
import android.widget.TextView;

public class My extends Activity implements OnClickListener
{
	RelativeLayout  action_login;
	TextView user;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my);	
		findViewById();
		setListener();
		member_is_login();
	}
	
	private void member_is_login() {

		 String username=MyApplication.getInstances().getUserName();
		 if(username!=""){
			 user.setText(username); 
		 }else{
			 user.setText("立即登录"); 
		 }	  
	}
	
	private void findViewById() {

		 action_login= (RelativeLayout) findViewById(R.id.login_action); 
		 user=(TextView) findViewById(R.id.member_center_username);
	}

	private void setListener() {
		action_login.setOnClickListener(this);

	}
	
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_action:
			getParent().startActivityForResult(new Intent(My.this, Login.class), 1);
			break;		
		}
	}
	 @Override
	
	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		 
		 Log.i("aaaa","-------回来了My.java");
		 member_is_login();
		 //String result = data.getExtras().getString("username");//得到新Activity 关闭后返回的数据
    	// String username=MyApplication.getInstances().getUserName();
		// user.setText(username); 
	 }
	 
	 
	   @Override  
	    public void onBackPressed() {     
	    	
	    	new AlertDialog.Builder(My.this.getParent()).setTitle("确定要退出么？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					finish();
					System.exit(0);
				}
			}).setNegativeButton("不确定", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
				}
			}).create().show();
	    }  
}