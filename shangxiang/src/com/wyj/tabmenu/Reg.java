package com.wyj.tabmenu;


import com.wyj.app.MyApplication;
import com.wyj.app.RegularUtil;
import com.wyj.member_db.Member_model;

import android.app.Activity;

import android.content.Intent;

import android.os.Bundle;

import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class Reg extends Activity implements OnClickListener
{
	
	ImageView login_bak;
	Button reg_submit;
	TextView  username;
	TextView  passwd;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.reg);
			    
	    findViewById();
	    setListener();
	}
	
	private void findViewById() {
		login_bak=(ImageView) findViewById(R.id.back_login);
		reg_submit=(Button) findViewById(R.id.submit_button);
		username = (TextView) findViewById(R.id.regusername); 
		passwd = (TextView) findViewById(R.id.regpasswd);
		
	}

	private void setListener() {
		login_bak.setOnClickListener(this);
		reg_submit.setOnClickListener(this);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back_login:
			//finish();		
			Intent bak_login=new Intent();
			bak_login.setClass(Reg.this, Login.class);
			startActivity(bak_login);
			break;
		case R.id.submit_button:
			submitData();
			break;
		}

	}

	private void submitData() {

		String reg_username = username.getText().toString();
		String reg_passwd = passwd.getText().toString();
		if (!RegularUtil.checkPhone(this, reg_username)) {
			
			//设置焦点信息;  
			username.setFocusable(true);  
			username.setFocusableInTouchMode(true);  
			username.requestFocus();  
			username.requestFocusFromTouch();  
		} else if(!RegularUtil.checkPassword(this, reg_passwd)){
		
			//设置焦点信息;  
			passwd.setFocusable(true);  
			passwd.setFocusableInTouchMode(true);  
			passwd.requestFocus();  
			passwd.requestFocusFromTouch();  
			
		}else {
			String time = MyApplication.getInstances().getSystemDataATime();
			Member_model Member_models= new Member_model(this); 
			Member_models.Member_insert(reg_username, reg_passwd, time);
			RegularUtil.alert_msg(this, "注册成功");
			finish();			
		}
	}
	
	
	
	
}
