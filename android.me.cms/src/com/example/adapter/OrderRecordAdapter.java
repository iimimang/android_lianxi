package com.example.adapter;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.cms.R;
import com.example.cms.ShangXiang;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class OrderRecordAdapter extends BaseAdapter implements OnClickListener {
	private Context context;
	public JSONArray data = new JSONArray();

	public static class ListItem {
		public TextView title;
		public TextView desc;
		public TextView status;
	}

	public OrderRecordAdapter(Context context) {
		this.context = context;
	}

	@Override
	public int getCount() {
		return data.length();
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
	public View getView(int position, View convertView, ViewGroup parent) {
		ListItem listItem;
		if (null == convertView) {
			convertView = LayoutInflater.from(this.context).inflate(R.layout.order_recrod_listview_item, null);
			listItem = new ListItem();
			listItem.title = (TextView) convertView.findViewById(R.id.order_record_listitem_title_text);
			listItem.desc = (TextView) convertView.findViewById(R.id.order_record_listitem_desc_text);
			listItem.status = (TextView) convertView.findViewById(R.id.order_record_listitem_status_text);
			convertView.setOnClickListener(this);
			convertView.setTag(listItem);
		} else {
			listItem = (ListItem) convertView.getTag();
		}
		JSONObject item = data.optJSONObject(position);
		listItem.title.setText(item.optString("title", ""));
		listItem.desc.setText(item.optString("date", ""));
		if (1 == item.optInt("status", 1)) {
			listItem.status.setText(R.string.status_finish);
		} else {
			listItem.status.setText(R.string.status_processing);
			listItem.status.setTextColor(this.context.getResources().getColor(R.color.text_title));
		}
		return convertView;
	}

	@Override
	public void onClick(View v) {
		Message msg = ShangXiang.orderRecordHandler.obtainMessage(0, "");
		ShangXiang.orderRecordHandler.sendMessage(msg);
	}
}