package com.example.adapterbuttonclick;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class ListItemClickAdapter extends BaseAdapter {
	private Context contxet;
	private ArrayList<String> list;
	private ListItemClickHelp callback;
	private LayoutInflater mInflater;

	public ListItemClickAdapter(Context contxet, ArrayList<String> list,
			ListItemClickHelp callback) {
		this.contxet = contxet;
		this.list = list;
		this.callback = callback;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, final ViewGroup parent) {
		mInflater = (LayoutInflater) contxet
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(
					R.layout.block_list_item, null);
			holder = new ViewHolder();
			holder.mAd_tv_show = (TextView) convertView
					.findViewById(R.id.ad_tv_show);
			holder.mAd_btn_one = (Button) convertView
					.findViewById(R.id.ad_btn_one);
			holder.mAd_btn_two = (Button) convertView
					.findViewById(R.id.ad_btn_two);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.mAd_tv_show.setText(list.get(position));

		final View view = convertView;
		final int p = position;
		final int one = holder.mAd_btn_one.getId();
		holder.mAd_btn_one.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				callback.onClick(view, parent, p, one);
			}
		});

		final int two = holder.mAd_btn_two.getId();
		holder.mAd_btn_two.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				callback.onClick(view, parent, p, two);
			}
		});

		return convertView;
	}

	public static class ViewHolder {
		TextView mAd_tv_show;
		Button mAd_btn_one;
		Button mAd_btn_two;
	}
}
