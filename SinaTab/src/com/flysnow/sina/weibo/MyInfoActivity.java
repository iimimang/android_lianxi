/**
 * 
 */
package com.flysnow.sina.weibo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * 我的资料Activity
 * @author 
 * @since 2011-3-8
 */
public class MyInfoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.list_activity);
		
		Button danxuan =(Button) findViewById(R.id.danxuan);
		
		danxuan.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				danxuan();
			}
		});
		
				
	}
	
	public void danxuan(){
		
		Intent intent=new Intent();
		intent.setClass(MyInfoActivity.this, Danxuan.class);	
		startActivity(intent);
	}
	
}
