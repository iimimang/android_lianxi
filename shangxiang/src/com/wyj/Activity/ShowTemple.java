package com.wyj.Activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.wyj.adapter.ListTempleAdapter;
import com.wyj.dataprocessing.AccessNetwork;
import com.wyj.dataprocessing.JsonToListHelper;
import com.wyj.dataprocessing.MyApplication;
import com.wyj.dataprocessing.RegularUtil;
import com.wyj.http.WebApiUrl;

public class ShowTemple extends Activity implements OnClickListener,
		OnCheckedChangeListener {

	private static String TAG = "ShowTemple";

	private ProgressDialog pDialog = null;
	private RadioButton radio_simiao;
	private RadioButton radio_fashi;
	private LinearLayout layoutHall;
	private LinearLayout layoutBuddhist;
	
	private Button show_temple_create_order_button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_temple);

		ImageView back_button = (ImageView) findViewById(R.id.show_temple_layout_back);
		back_button.setOnClickListener(this);
			
		findViewById();		
		// loadTemplate();
	}

	private void findViewById() {
		// TODO Auto-generated method stub
		radio_simiao = (RadioButton) findViewById(R.id.show_temple_radio_simiao);
		radio_simiao.setChecked(true);
		radio_simiao.setOnCheckedChangeListener(this);
		radio_fashi = (RadioButton) findViewById(R.id.show_temple_radio_fashi);
		radio_fashi.setOnCheckedChangeListener(this);
		layoutHall = (LinearLayout) findViewById(R.id.show_temple_simiao_description);
		layoutBuddhist = (LinearLayout) findViewById(R.id.show_temple_fashi_description);
		show_temple_create_order_button = (Button) findViewById(R.id.show_temple_create_order_button);
		show_temple_create_order_button.setOnClickListener(this);
	}

	private void loadTemplate() {
		// TODO Auto-generated method stub

		// 利用Handler更新UI
		final Handler Discover = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 0x123) {
					pDialog.dismiss();
					String backmsg = msg.obj.toString();
					JSONObject allresult;
					try {
						Log.i(TAG, "------返回信息" + backmsg);
						allresult = new JSONObject(backmsg);
						String code = allresult.getString("code");
						String data = allresult.getString("wishtemplelist");
						if (code.equals("succeed")) {
							Log.i(TAG, "------返回信息" + data);

						} else {

							RegularUtil.alert_msg(ShowTemple.this, "加载失败");

						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};

		pDialog = new ProgressDialog(this.getParent());
		pDialog.setMessage("请求中。。。");
		pDialog.show();
		new Thread(new AccessNetwork("GET", WebApiUrl.Getwishtemplelist, "",
				Discover)).start();
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.show_temple_layout_back:
			// 要跳转的Activity
			Intent intent = new Intent(ShowTemple.this, ListTemple.class);
			WishGroupTab.getInstance().switchActivity("ListTemple", intent, -1,
					-1);
			break;
		case R.id.show_temple_create_order_button:
			// 要跳转的Activity
			Intent intent2 = new Intent(ShowTemple.this, OrderForm.class);
			WishGroupTab.getInstance().switchActivity("OrderForm", intent2, -1,
					-1);
			break;
		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			WishGroupTab.getInstance().onKeyDown(keyCode, event);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		if (buttonView == radio_simiao && isChecked) {
			layoutHall.setVisibility(View.VISIBLE);
			layoutBuddhist.setVisibility(View.GONE);
		}
		if (buttonView == radio_fashi && isChecked) {
			layoutHall.setVisibility(View.GONE);
			layoutBuddhist.setVisibility(View.VISIBLE);
		}
	}

}
