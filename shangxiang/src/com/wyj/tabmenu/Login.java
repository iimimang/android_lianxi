package com.wyj.tabmenu;


import com.wyj.app.RegularUtil;
import com.wyj.member_db.Member_model;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class Login extends Activity implements OnClickListener
{
	
	ImageView logo;
	Button reg;
	Button login_submit;
	TextView  username;
	TextView  passwd;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		// 去除标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login);
		findViewById() ;	
		setListener() ;
	}
	
	private void findViewById() {
		logo=(ImageView) findViewById(R.id.logo);
		reg=(Button) findViewById(R.id.reg_button);	
		login_submit=(Button) findViewById(R.id.login_button);
		username = (TextView) findViewById(R.id.login_username); 
		passwd = (TextView) findViewById(R.id.login_passwd);
	}

	private void setListener() {
		logo.setOnClickListener(this);
		reg.setOnClickListener(this);
		login_submit.setOnClickListener(this);
	}
	
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.logo:
			//finish();		
			Intent intent=new Intent();
			intent.setClass(Login.this, TabMenu.class);
			startActivity(intent);
			break;
		case R.id.reg_button:
			Intent reg_intent=new Intent();
			reg_intent.setClass(Login.this, Reg.class);
			startActivity(reg_intent);
			break;
		case R.id.login_button:
			login_submit();
			break;
		}
		

	}
	
//	 @Override
//		public boolean onKeyDown(int keyCode, KeyEvent event) {
//			
//			//Log.i("TAG","----"+keyCode);
//			switch (keyCode) {
//			case KeyEvent.KEYCODE_BACK:
//				finish();
//				Intent reg_intentss=new Intent();
//				reg_intentss.setClass(Login.this, TabMenu.class);
//				Bundle bu=new Bundle();// 这个组件 存值
//				bu.putInt("tab_key", 2);
//				reg_intentss.putExtras(bu);
//				startActivity(reg_intentss);
//					
//				return false;
//			default:
//				break;
//			}
//			
//			return super.onKeyDown(keyCode, event);
//		}
	 
	private void login_submit() {
		// TODO Auto-generated method stub
		
		String reg_username = username.getText().toString();
		String reg_passwd = passwd.getText().toString();
		Member_model Member_models= new Member_model(this); 
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
			
		} else if(!Member_models.checkMember(reg_username, reg_passwd)){
		
			RegularUtil.alert_msg(this, "帐号和密码不匹配！");
			
			
		}else {
			
			RegularUtil.alert_msg(this, "登录成功！");
			Intent login_intent=new Intent();
			login_intent.setClass(Login.this, My.class);
			Bundle bu=new Bundle();// 这个组件 存值
			bu.putString("username", reg_username);
			login_intent.putExtras(bu);
			//设置返回数据
			Login.this.setResult(1, login_intent);
            //关闭Activity
			Login.this.finish();
					
		}
		
	}
	
	
}
