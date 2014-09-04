package com.notbook.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.notbook.R;

public class Manageradd extends BaseActivity {

	private Button more_back ,confirm;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.manageadd);
		findViewById(R.id.one).setBackgroundResource(background_photobg[num - 1]);
		more_back = (Button)findViewById(R.id.more_back);
		confirm = (Button)findViewById(R.id.confirm);
		more_back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		confirm.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent it = new Intent(Manageradd.this,Manager.class);
				startActivity(it);
				Manageradd.this.finish();
				Toast.makeText(getApplicationContext(), "添加成功", 0).show();
			}
		});
	}
}
