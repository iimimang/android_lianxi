package com.wyj.Activity;

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

import com.wyj.dataprocessing.MyApplication;

public class ListTemple  extends Activity  implements OnClickListener {
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_temple);	
		findViewById();
		setListener();
	}
	

	
	private void findViewById() {

		
	}

	private void setListener() {

	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_action:
			
			break;		
		}
	}




}
