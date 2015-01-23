package com.wyj.Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.wyj.Activity.R;

import com.wyj.popupwindow.MyPopupWindows;
import com.wyj.popupwindow.MyPopupWindowsCity;
import com.wyj.popupwindow.MyPopupWindowsDate;
import com.wyj.popupwindow.MyPopupWindowsIncense;
import com.wyj.select.AbstractWheel;
import com.wyj.select.AbstractWheelTextAdapter;
import com.wyj.select.OnWheelChangedListener;
import com.wyj.select.OnWheelScrollListener;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;

import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class OrderForm extends Activity implements OnClickListener {

	private static String TAG = "OrderForm";

	private ImageView order_form_back;
	private ProgressDialog pDialog = null;
	private Button order_form_submit;
	private TextView buttonShowContentSelect;
	private LinearLayout create_order_select_location_layout;
	private Button hide_wish_content;
	private ScrollView ScrollView_form;
	private EditText order_form_layout_wish_content_input;

	private TextView order_form_layout_wish_address_input;
	private TextView order_form_layout_wish_date_input;
	private TextView order_form_layout_wish_xiangtype_input;
	private boolean scrolling = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_form);

		findViewById();
		setListener();

	}

	private void findViewById() {

		order_form_back = (ImageView) findViewById(R.id.order_form_back);
		order_form_submit = (Button) findViewById(R.id.order_form_submit);
		buttonShowContentSelect = (TextView) findViewById(R.id.order_form_layout_wish_content_head_right); // 显示祝福语的点击按钮
		ScrollView_form = (ScrollView) findViewById(R.id.order_form_ScrollView);
		order_form_layout_wish_content_input = (EditText) findViewById(R.id.order_form_layout_wish_content_input);

		order_form_layout_wish_address_input = (TextView) findViewById(R.id.order_form_layout_wish_address_input);
		order_form_layout_wish_date_input =(TextView) findViewById(R.id.order_form_layout_wish_date_input);
		order_form_layout_wish_xiangtype_input =(TextView) findViewById(R.id.order_form_layout_wish_xiangtype_input);
	}

	private void setListener() {
		buttonShowContentSelect.setOnClickListener(this);
		order_form_back.setOnClickListener(this);
		order_form_submit.setOnClickListener(this);
		order_form_layout_wish_address_input.setOnClickListener(this);
		order_form_layout_wish_date_input.setOnClickListener(this);
		order_form_layout_wish_xiangtype_input.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.order_form_back:
			Intent bak_My_intent = new Intent(OrderForm.this, ListTemple.class);
			WishGroupTab.getInstance().switchActivity("ListTemple",
					bak_My_intent, -1, -1);
			break;
		case R.id.order_form_submit:
			Intent intent1 = new Intent(OrderForm.this, OrderFormDetail.class);
			WishGroupTab.getInstance().switchActivity("OrderFormDetail",
					intent1, -1, -1);
			break;
		case R.id.order_form_layout_wish_content_head_right:
			String[] aaa = new String[] { "阿发是否阿发是否阿发是否", "332324323",
					"的反反复复发射点发", "巴巴爸爸" };
			MyPopupWindows daochang_select_widow = new MyPopupWindows(
					OrderForm.this, order_form_layout_wish_content_input,
					getParent().getParent(), aaa);
			break;
		case R.id.order_form_layout_wish_address_input:

			JSONArray data;
			try {
				data = new JSONArray(
						"[{\"name\":\"请选择\",\"sub\":[{\"name\":\"请选择\"}],\"type\":1},{\"name\":\"北京\",\"sub\":[{\"name\":\"请选择\"},{\"name\":\"东城区\"},{\"name\":\"西城区\"},{\"name\":\"崇文区\"},{\"name\":\"其他\"}],\"type\":0},{\"name\":\"海外\",\"sub\":[{\"name\":\"请选择\"},{\"name\":\"西城区\"},{\"name\":\"崇文区\"},{\"name\":\"其他\"}],\"type\":0},{\"name\":\"其他\",\"sub\":[{\"name\":\"请选择\"}],\"type\":0}]");

				MyPopupWindowsCity city_select_widow = new MyPopupWindowsCity(
						OrderForm.this, order_form_layout_wish_address_input,
						getParent().getParent(), data);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;
		case R.id.order_form_layout_wish_date_input:

			MyPopupWindowsDate date_select_widow = new MyPopupWindowsDate(
					OrderForm.this, order_form_layout_wish_date_input,
					getParent().getParent());

			break;
		case R.id.order_form_layout_wish_xiangtype_input:
			
			String[] bbb = new String[] { "头柱香   ￥800", "开光香   ￥200", "高香   ￥90", "清香   ￥19" };
			MyPopupWindowsIncense incense_select_widow = new MyPopupWindowsIncense(
				OrderForm.this, order_form_layout_wish_xiangtype_input,
				getParent().getParent(),bbb);

		break;

		}
	}

	private void background_remove_focus() {
		// TODO Auto-generated method stub
		WindowManager.LayoutParams lp = getParent().getParent().getWindow()
				.getAttributes();
		lp.alpha = 0.7f;
		getParent().getParent().getWindow().setAttributes(lp);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			WishGroupTab.getInstance().onKeyDown(keyCode, event);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
