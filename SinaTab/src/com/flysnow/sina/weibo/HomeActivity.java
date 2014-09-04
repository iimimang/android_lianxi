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
import android.widget.Toast;

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
				try {
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
				}catch(Exception e){
					// TODO: handle exception
					Toast.makeText(HomeActivity.this,"errorrrrr", Toast.LENGTH_LONG).show();
				}
				
			}});
		
	};
	
										//返回数据
	protected void onActivityResult(int requestCode, int resultCode,Intent data) {
			// TODO Auto-generated method stub
			super.onActivityResult(requestCode, resultCode, data);
			RadioButton radiobutton_Man= (RadioButton) findViewById(R.id.sex_man);
			RadioButton radiobutton_Woman= (RadioButton) findViewById(R.id.sex_woman);
			EditText edit_height = (EditText) findViewById(R.id.height);
		switch (resultCode) {
			case RESULT_OK:
			/* 取得来自Activity2 的数据，并显示于画面上*/
			Bundle bunde = data.getExtras();
			String sex = bunde.getString("sex");
			double height = bunde.getDouble("height");
			edit_height.setText("" + height);
			if (sex.equals("男性")) {
			radiobutton_Man.setChecked(true);
			} else {
			radiobutton_Woman.setChecked(true);
			} 
			break;
			
			default:
			break;
			}
	}
	
	
}