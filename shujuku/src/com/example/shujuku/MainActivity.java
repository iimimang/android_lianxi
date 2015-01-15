package com.example.shujuku;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity  implements OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		 Ui_Init();
	}

	private void Ui_Init() {
		// TODO Auto-generated method stub
		ImageView img=(ImageView)findViewById(R.id.wangluoimage);
		Bitmap m=image.GetLocalOrNetBitmap("http://192.168.1.30/uploadfile/2014/0910/20140910021707849.jpg");
		img.setImageBitmap(m); 
		
		Button add_buttton=(Button)findViewById(R.id.add);
		Button list_buttton=(Button)findViewById(R.id.list);
		
		add_buttton.setOnClickListener(this);
		list_buttton.setOnClickListener(this);
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		
	}

}
