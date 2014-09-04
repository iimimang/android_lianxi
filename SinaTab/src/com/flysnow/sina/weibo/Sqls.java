/**
 * 
 */
package com.flysnow.sina.weibo;

import com.flysnow.sina.dbcontrol.DBHelper;
import com.flysnow.sina.dbcontrol.TestSqlLite;
import com.flysnow.sina.dbcontrol.Userdb;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;



/**
 * sql操作 Activity
 * @author 
 * @since 2011-3-8
 */
public class Sqls extends Activity implements Button.OnClickListener {

	

		@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		setLayoutShow(R.layout.sqls);
			
		Button add= (Button) findViewById(R.id.Button_add);
		Button create= (Button) findViewById(R.id.Button_create);
		Button select= (Button) findViewById(R.id.Button_select);
		add.setOnClickListener(this);
		create.setOnClickListener(this);
		select.setOnClickListener(this);
	}
	

	
	public void onClick(View v){
		
		
		
	 switch (v.getId()) {
		
		case R.id.Button_create:
			
		
			
			break;
			
		case R.id.Button_add:
			
			Userdb sqllites=new Userdb();
			try {
				sqllites.insert();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.i("aaaa","失败\n");
			}
			
			break;
		case R.id.Button_select:
			
			break;

		default:
			break;
		}
		
	}	
		
		
		 
	/*设置主屏
	*/
	private void setLayoutShow(int layoutID) {
		// TODO Auto-generated method stub
		setContentView(layoutID);/*设置当前主屏布局*/
	}	 
	
	
	
	private void NoteDebug(String showString){
		/*显示Toast提示*/
		Toast.makeText(this,showString, Toast.LENGTH_SHORT).show();
	}
		
		
		
	}