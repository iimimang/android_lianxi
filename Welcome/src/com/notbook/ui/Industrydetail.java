package com.notbook.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.notbook.R;

public class Industrydetail extends BaseActivity {

	private Button more_back ;
	private TextView content ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.industrydetail);
		findViewById(R.id.one).setBackgroundResource(background_photobg[num - 1]);
		findViewById(R.id.one1).setBackgroundResource(background_photobg1[num - 1]);
		more_back = (Button)findViewById(R.id.more_back);
		content = (TextView)findViewById(R.id.content);
		
		more_back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		Bundle b = getIntent().getExtras();
		if(null!=b&&!b.equals("")){
			String text = b.getString("text");
			content.setText("        "+text);
		}
	}
}
