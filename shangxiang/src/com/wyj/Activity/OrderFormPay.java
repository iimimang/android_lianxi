package com.wyj.Activity;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.wyj.Activity.R;
import com.wyj.http.WebApiUrl;
import com.wyj.pipe.SinhaPipeClient;
import com.wyj.pipe.SinhaPipeMethod;
import com.wyj.pipe.Utils;
import com.wyj.popupwindow.MyPopupWindowsIncense;
import com.wyj.utils.StingUtil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;

import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class OrderFormPay extends Activity implements OnClickListener {

	private static String TAG = "OrderFormPay";

	private ImageView order_form_pay_back;
	private ProgressDialog pDialog = null;
	private Button pay_alipay_button;
	private Button pay_weixin_button;
	private Button pay_yinlian_button;

	private SinhaPipeClient httpClient;
	private SinhaPipeMethod httpMethod;
	private TextView orderinfo_ordernumber, orderinfo_orderpeople,
			order_baseinfo_simiao_input, order_baseinfo_xiangtype_input,
			order_baseinfo_fashi_input, order_baseinfo_date_input,order_wishcontent_input;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_form_pay);

		findViewById();
		setListener();
		this.httpClient = new SinhaPipeClient();
		Intent intens = this.getIntent();
		Bundle bu = intens.getExtras();
		String orderid = bu.getString("orderid");
		loadOrderInfo(orderid);
		

	}

	private void findViewById() {

		order_form_pay_back = (ImageView) findViewById(R.id.order_form_pay_back);
		pay_alipay_button = (Button) findViewById(R.id.order_form_pay_alipay_submit);
		pay_weixin_button = (Button) findViewById(R.id.order_form_pay_weixin_submit);
		pay_yinlian_button = (Button) findViewById(R.id.order_form_pay_yinlian_submit);
		
		orderinfo_ordernumber =(TextView) findViewById(R.id.orderinfo_ordernumber);
		orderinfo_orderpeople =(TextView) findViewById(R.id.orderinfo_orderpeople);
		order_baseinfo_simiao_input =(TextView) findViewById(R.id.order_baseinfo_simiao_input);
		order_baseinfo_xiangtype_input =(TextView) findViewById(R.id.order_baseinfo_xiangtype_input);
		order_baseinfo_fashi_input =(TextView) findViewById(R.id.order_baseinfo_fashi_input);
		order_baseinfo_date_input =(TextView) findViewById(R.id.order_baseinfo_date_input);
		order_wishcontent_input =(TextView) findViewById(R.id.order_wishcontent_input);
		
	}

	private void setListener() {

		order_form_pay_back.setOnClickListener(this);
		pay_alipay_button.setOnClickListener(this);
		pay_weixin_button.setOnClickListener(this);
		pay_yinlian_button.setOnClickListener(this);

	}

	private void loadOrderInfo(String orderid) {
		if (Utils.CheckNetwork()) {
			showLoading();
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("oid", orderid));
			this.httpClient.Config("get", WebApiUrl.Getgetorderinfo, params,
					true);
			this.httpMethod = new SinhaPipeMethod(this.httpClient,
					new SinhaPipeMethod.MethodCallback() {
						public void CallFinished(String error, Object result) {
							showLoading();
							if (null == error) {
								loadOrderInfo_ui((String) result);
							} else {
								int err = R.string.dialog_system_error_content;
								if (error == httpClient.ERR_TIME_OUT) {
									err = R.string.dialog_network_error_timeout;
								}
								if (error == httpClient.ERR_GET_ERR) {
									err = R.string.dialog_network_error_getdata;
								}
								Utils.ShowToast(OrderFormPay.this, err);
							}
						}
					});
			this.httpMethod.start();
		} else {
			Utils.ShowToast(OrderFormPay.this,
					R.string.dialog_network_check_content);
		}
	}

	private void loadOrderInfo_ui(String s) {
		if (null != s) {
			try {
				JSONObject result = new JSONObject(s);

				if (result.optString("code", "").equals("succeed")) {

					JSONObject Object = result.getJSONObject("orderinfo");
					
					String retime =StingUtil.get_date(Object.optString("retime", ""));

					//System.out.println(date);
					
					orderinfo_ordernumber.setText("订单号："+Object.optString("orderid", ""));
					orderinfo_orderpeople.setText(Object.optString("wishname", "")+"祈求"+Object.optString("wishtype", ""));
					order_baseinfo_simiao_input.setText(Object.optString("templename", ""));
					order_baseinfo_xiangtype_input.setText(Object.optString("wishgrade", ""));
					order_baseinfo_fashi_input.setText(Object.optString("buddhistname", ""));
					order_baseinfo_date_input.setText(retime);
					order_wishcontent_input.setText(Object.optString("wishtext", ""));
					
					
				} else {

				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	private void showLoading() {

		if (pDialog != null) {
			pDialog.dismiss();
			pDialog=null;
		} else {

			pDialog = new ProgressDialog(getParent().getParent());
			pDialog.setMessage("数据加载中。。。");
			pDialog.show();
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.order_form_pay_back:
			Intent bak_My_intent = new Intent(OrderFormPay.this,
					Wish.class);
			WishGroupTab.getInstance().switchActivity("Wish",
					bak_My_intent, -1, -1);
			break;
		case R.id.order_form_pay_alipay_submit:
			Intent intent2 = new Intent(OrderFormPay.this, OrderPaySucc.class);
			WishGroupTab.getInstance().switchActivity("OrderPaySucc", intent2,
					-1, -1);
			break;
		case R.id.order_form_pay_weixin_submit:
			Intent intent3 = new Intent(OrderFormPay.this, OrderPaySucc.class);
			WishGroupTab.getInstance().switchActivity("OrderPaySucc", intent3,
					-1, -1);
			break;
		case R.id.order_form_pay_yinlian_submit:
			Intent intent4 = new Intent(OrderFormPay.this, OrderPaySucc.class);
			WishGroupTab.getInstance().switchActivity("OrderPaySucc", intent4,
					-1, -1);
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

}
