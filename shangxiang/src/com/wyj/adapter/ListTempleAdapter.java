package com.wyj.adapter;

import org.json.JSONArray;
import org.json.JSONObject;


import com.wyj.Activity.R;
import com.wyj.Activity.ShowTemple;
import com.wyj.Activity.Wish;
import com.wyj.Activity.WishGroupTab;
import com.wyj.dataprocessing.BitmapManager;
import com.wyj.http.WebApiUrl;
import com.wyj.utils.Tools;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class ListTempleAdapter extends BaseAdapter implements OnClickListener {
	private Context context;
	public JSONArray data = new JSONArray();

	public static class ListItem {
		public ImageView thumbHall;
		public ImageView thumbMaster;
		public TextView nameHall;
		public TextView nameMaster;
		public Button createOrder;
	}

	public ListTempleAdapter(Context context,JSONArray data) {
		this.context = context;
		this.data=data;
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
			convertView = LayoutInflater.from(this.context).inflate(R.layout.list_temple_listview_item, null);
			listItem = new ListItem();
			listItem.thumbHall = (ImageView) convertView.findViewById(R.id.list_temlple_image_1);
			listItem.thumbMaster = (ImageView) convertView.findViewById(R.id.list_temlple_image_2);
			listItem.nameHall = (TextView) convertView.findViewById(R.id.list_temple_buddhist_name_text);
			listItem.nameMaster = (TextView) convertView.findViewById(R.id.list_temple_hall_name_text);
			listItem.createOrder = (Button) convertView.findViewById(R.id.list_temple_order_button);
			convertView.setOnClickListener(this);
			convertView.setTag(listItem);
		} else {
			listItem = (ListItem) convertView.getTag();
		}
		JSONObject item = this.data.optJSONObject(position);
		listItem.nameMaster.setText(item.optString("templename", "")+"("+item.optString("province", "")+")");
		listItem.nameHall.setText(item.optString("buddhistname", ""));
		
	BitmapManager.getInstance().loadBitmap(item.optString("headface", ""), listItem.thumbMaster, Tools.readBitmap(this.context, R.drawable.temp2));
	BitmapManager.getInstance().loadBitmap(item.optString("path", ""), listItem.thumbHall, Tools.readBitmap(this.context, R.drawable.temp1));

		listItem.createOrder.setOnClickListener(this);
		listItem.thumbHall.setOnClickListener(this);
		listItem.thumbMaster.setOnClickListener(this);
		return convertView;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.list_temlple_image_1:
			Intent intent = new Intent(context, ShowTemple.class);
	        WishGroupTab.getInstance().switchActivity("ShowTemple",intent,-1,-1);
			break;		
		}
	}
}