package com.wyj.tabmenu;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class My extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my);
		
		ImageView login=(ImageView) findViewById(R.id.avatar);
		login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Intent intent=new Intent();
//				intent.setClass(My.this, Login.class);
//				startActivity(intent); 
				startActivityForResult(new Intent(My.this, Login.class), 1);
			}
		});	
		
	
	}
	 @Override
	
	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		 switch(requestCode){
          case 1:
        	 String result = data.getExtras().getString("username");//得到新Activity 关闭后返回的数据
    	     TextView user=(TextView) findViewById(R.id.member_center_username);
		     user.setText("aaaaaaaaa");
		     break;
          }
		 
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
