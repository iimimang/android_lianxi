package com.example.adapterbuttonclick;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity implements ListItemClickHelp {

	private TextView mtv_show;
	private ListView mlv_show;
	private ArrayList<String> mdata = new ArrayList<String>();
	private ListItemClickAdapter madapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initLayout();
	}

	private void initLayout() {
		mdata.add("One");
		mdata.add("Two");
		mdata.add("Three");
		mdata.add("Four");
		mdata.add("Five");
		mdata.add("菠萝");
		mdata.add("苹果");
		mdata.add("葡萄");
		mdata.add("哈密瓜");
		mdata.add("香蕉");

		mtv_show = (TextView) this.findViewById(R.id.tv_show);
		mlv_show = (ListView) this.findViewById(R.id.lv_show);

		madapter = new ListItemClickAdapter(this, mdata, this);
		mlv_show.setAdapter(madapter);

		mlv_show.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View view,
					int position, long arg3) {
				mtv_show.setText(mdata.get(position));
			}
		});
	}

	@Override
	public void onClick(View item, View widget, int position, int which) {
		switch (which) {
		case R.id.ad_btn_one:
			mtv_show.setText(mdata.get(position) + "Btn one");
			break;
		case R.id.ad_btn_two:
			mtv_show.setText(mdata.get(position) + "Btn two");
			break;
		default:
			break;
		}
	}

}
