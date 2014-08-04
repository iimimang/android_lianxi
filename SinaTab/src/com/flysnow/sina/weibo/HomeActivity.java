/**
 * 
 */
package com.flysnow.sina.weibo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

/**
 * 首页Activity
 * @author 
 * @since 2011-3-8
 */
public class HomeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_table);     
		
		Button login= (Button) findViewById(R.id.login);
		
		login.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EditText u = (EditText) findViewById(R.id.username);
				EditText p = (EditText) findViewById(R.id.password);
				EditText h = (EditText) findViewById(R.id.height);
				RadioButton s= (RadioButton) findViewById(R.id.sex_man);
				String username=u.toString();
				String passwd=p.toString();
				double  height= Double.parseDouble(h.getText().toString());  //转换为double类型 
				String sex;
				if(s.isChecked()){
					sex ="男性";
				}else{
					
					sex ="女性";
				}
				Intent intent=new Intent();
				intent.setClass(HomeActivity.this, MoreActivity.class);
				Bundle bu=new Bundle();
				bu.putString("username", username);
				bu.putString("passwd", passwd);
				bu.putString("sex", sex);
				bu.putDouble("height", height);
				intent.putExtras(bu);
				startActivity(intent);
			}});
		
	};
	
}
