package com.notbook.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.notbook.R;

public class About extends BaseActivity {

	private Button more_back ;
	static int i = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		findViewById(R.id.one1).setBackgroundResource(background_photobg1[num - 1]);
		more_back = (Button)findViewById(R.id.more_back);
		RelativeLayout one = (RelativeLayout)findViewById(R.id.one);
		findViewById(R.id.one).setBackgroundResource(background_photobg[num - 1]);
		findViewById(R.id.one1).setBackgroundResource(background_photobg1[num - 1]);
		
		more_back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(About.this, MainActivity1.class);
				startActivity(intent);
				finish();
			}
		});
		findViewById(R.id.one).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				i = i+1;
				if(i == 20){
					Toast.makeText(getApplicationContext(), "zhang guo yue is love with you", 0).show();
					i = 0;
				}
			}
		});
		
		
	}
}
