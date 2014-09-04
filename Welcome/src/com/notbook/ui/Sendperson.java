package com.notbook.ui;

import java.util.ArrayList;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.notbook.R;
import com.notbook.entity.Thing;

public class Sendperson extends BaseActivity {

	private Button more_back ;
	private String recnametext = "";//接收人
	private TextView sendperson ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sendperson);
		findViewById(R.id.one).setBackgroundResource(background_photobg[num - 1]);
		findViewById(R.id.one1).setBackgroundResource(background_photobg1[num - 1]);
		more_back = (Button)findViewById(R.id.more_back);
		sendperson = (TextView)findViewById(R.id.sendperson);
		more_back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		Bundle bundle = getIntent().getExtras();
		if(null != bundle && !bundle.equals("")){
			recnametext = bundle.getString("recnametext");
			sendperson.setText(recnametext);
			
			
		}
	}
}
