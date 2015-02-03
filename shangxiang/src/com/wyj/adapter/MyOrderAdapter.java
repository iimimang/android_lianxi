package com.wyj.adapter;

import java.util.List;
import java.util.Map;

import com.wyj.Activity.OrderForm;
import com.wyj.Activity.OrderFormDetail;
import com.wyj.Activity.OrderFormPay;
import com.wyj.Activity.R;
import com.wyj.Activity.TabMenu;
import com.wyj.Activity.WishGroupTab;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MyOrderAdapter extends BaseAdapter implements OnClickListener {
	private Context context;
	private List<Map<String, Object>> mData;
	private  Activity parents;

	public static class ListItem {

		public TextView myorder_info_detail;
		public TextView myorder_info_number;
		public TextView myorder_info_date;
		public TextView myorder_info_status;
	}

	public MyOrderAdapter(Context context, List<Map<String, Object>> data, Activity activity) {
		this.context = context;
		this.mData = data;
		this.parents=activity;
	}

	@Override
	public int getCount() {
		return this.mData.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ListItem listItem;

		if (null == convertView) {
			convertView = LayoutInflater.from(this.context).inflate(
					R.layout.myorder_list_items, null);
			listItem = new ListItem();

			listItem.myorder_info_detail = (TextView) convertView
					.findViewById(R.id.myorder_info_detail);
			listItem.myorder_info_number = (TextView) convertView
					.findViewById(R.id.myorder_info_number);
			listItem.myorder_info_date = (TextView) convertView
					.findViewById(R.id.myorder_info_date);
			listItem.myorder_info_status = (TextView) convertView
					.findViewById(R.id.myorder_info_status);

			convertView.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					 
					TabMenu mainactivity = (TabMenu) parents; // 查找父级的父级
					mainactivity.setCurrentActivity(1);
					Intent intent1 = new Intent(
							context,
							OrderFormDetail.class);
					 Bundle bu=new Bundle();
					 bu.putString("orderid",(String) mData.get(position)
								.get("orderid"));
					 intent1.putExtras(bu);
					// context.startActivity(intent1);
					 WishGroupTab.getInstance()
							.switchActivity("OrderFormDetail",
									intent1, -1, -1);// 接口请求
				}
			});

			convertView.setTag(listItem);
		} else {
			listItem = (ListItem) convertView.getTag();
		}
		
		
		String ismultitemple=(String) this.mData.get(position).get("ismultitemple");	//是否三庙奇拜
		if(ismultitemple.equals("1")){
			listItem.myorder_info_detail.setText((String) this.mData.get(position)
					.get("templename"));
		}else{
			listItem.myorder_info_detail.setText((String) this.mData.get(position)
					.get("templename")
					+ "("
					+ (String) this.mData.get(position).get("buddhistname")
					+ ")"
					+ "代祈福"
					
					+ (String) this.mData.get(position).get("wishtype"));
		}
		
		
		listItem.myorder_info_number.setText("订单号："
				+ (String) this.mData.get(position).get("orderid"));
		listItem.myorder_info_date.setText((String) this.mData.get(position)
				.get("buddhadate"));
		listItem.myorder_info_status.setText((String) this.mData.get(position)
				.get("status"));

		return convertView;
	}

	@Override
	public void onClick(View v) {

		//
		// switch (v.getId()) {
		// case R.id.list_temple_order_button:
		// Intent intent2 = new Intent(context, OrderForm.class);
		// WishGroupTab.getInstance().switchActivity("OrderForm", intent2, -1,
		// -1);
		// break;
		//
		// }
	}
}