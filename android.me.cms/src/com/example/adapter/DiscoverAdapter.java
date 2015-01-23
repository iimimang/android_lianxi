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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class DiscoverAdapter extends BaseAdapter implements OnClickListener {
	private Context context;
	public JSONArray data = new JSONArray();

	public static class ListItem {
		public ImageButton avatar;
		public TextView name;
		public TextView desc;
		public TextView content;
		public TextView blessed;
		public Button blessit;
	}

	public DiscoverAdapter(Context context) {
		this.context = context;
	}

	@Override
	public int getCount() {
		return this.data.length();
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
			convertView = LayoutInflater.from(this.context).inflate(R.layout.discover_listview_item, null);
			listItem = new ListItem();
			listItem.avatar = (ImageButton) convertView.findViewById(R.id.discover_listitem_avatar_button);
			listItem.name = (TextView) convertView.findViewById(R.id.discover_listitem_name_text);
			listItem.desc = (TextView) convertView.findViewById(R.id.discover_listitem_desc_text);
			listItem.content = (TextView) convertView.findViewById(R.id.discover_listitem_content_text);
			listItem.blessed = (TextView) convertView.findViewById(R.id.discover_listitem_blessed_text);
			listItem.blessit = (Button) convertView.findViewById(R.id.discover_listitem_blessit_button);
			convertView.setOnClickListener(this);
			convertView.setTag(listItem);
		} else {
			listItem = (ListItem) convertView.getTag();
		}
		JSONObject item = this.data.optJSONObject(position);
		listItem.name.setText(item.optString("name", "").trim());
		listItem.desc.setText(item.optString("date", "").trim());
		listItem.content.setText(item.optString("content", "").trim());
		listItem.blessed.setText(item.optString("blessed", "").trim());
		listItem.blessit.setOnClickListener(this);
		return convertView;
	}

	@Override
	public void onClick(View v) {
		Message msg = ShangXiang.discoverHandler.obtainMessage(0, "");
		ShangXiang.discoverHandler.sendMessage(msg);
	}
}