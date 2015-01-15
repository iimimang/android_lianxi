package com.wyj.Activity;

import com.wyj.dataprocessing.MyApplication;
import com.wyj.Activity.R;

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
import android.widget.ImageButton;

public class Wish extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_wish);

		((ImageButton) findViewById(R.id.home_desire_0_button))
				.setOnClickListener(this);
		((ImageButton) findViewById(R.id.home_desire_1_button))
				.setOnClickListener(this);
		((ImageButton) findViewById(R.id.home_desire_2_button))
				.setOnClickListener(this);
		((ImageButton) findViewById(R.id.home_desire_3_button))
				.setOnClickListener(this);
		((ImageButton) findViewById(R.id.home_desire_4_button))
				.setOnClickListener(this);
		((ImageButton) findViewById(R.id.home_desire_5_button))
				.setOnClickListener(this);
		((ImageButton) findViewById(R.id.home_desire_6_button))
				.setOnClickListener(this);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.home_desire_0_button:
			break;
		case R.id.home_desire_1_button:
			break;
		case R.id.home_desire_2_button:
			break;
		case R.id.home_desire_3_button:
			break;
		case R.id.home_desire_4_button:
			break;
		case R.id.home_desire_5_button:
			break;
		case R.id.home_desire_6_button:
			break;
		default:
			break;
		}

		Intent intent = new Intent(this, ListTemple.class);
		WishGroupTab.getInstance().switchActivity("ListTemple", intent, -1, -1);
	}

}
