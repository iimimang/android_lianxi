package com.notbook.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.example.notbook.R;
import com.notbook.entity.MPhone;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

public class ContactListAdapter extends BaseAdapter {
	private Map map = new HashMap();
	private static final String TAG = "ContactListAdapter";
	private ArrayList<MPhone> peoples;
	private Context context;
	private LayoutInflater inflater;
	private String number = "";
	public static String phone = "";
	public static String name = "";
	private ArrayList list = new ArrayList();
	public static StringBuilder sb = null;
	public static StringBuilder sb1 = null;

	public ContactListAdapter(ArrayList<MPhone> peoples, Context context) {
		this.peoples = peoples;
		this.context = context;
		inflater = (LayoutInflater) context
				.getSystemService(context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return peoples.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return peoples.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final MPhone bean = peoples.get(position);
		ViewHolder holder = null;
		if (null == convertView) {
			convertView = inflater.inflate(R.layout.share_list3, null);
			holder = new ViewHolder();
			holder.tv_name = (TextView) convertView.findViewById(R.id.name);
			holder.tv_number = (TextView) convertView.findViewById(R.id.number);
			holder.CheckBox001 = (CheckBox) convertView
					.findViewById(R.id.CheckBox001);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tv_name.setText(bean.getUname());

		sb = new StringBuilder();
		sb1 = new StringBuilder();
		holder.CheckBox001
				.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
					public void onCheckedChanged(CompoundButton btn,
							boolean value) {
						// value为CheckBox的值
						if (value == true) {
							bean.setCheckState(true);
						} else{
							bean.setCheckState(false);
						}
					}
				});

		holder.tv_number.setText(bean.getUphone());

		return convertView;
	}

	static class ViewHolder {
		TextView tv_name;
		TextView tv_number;
		CheckBox CheckBox001;
	}

}
