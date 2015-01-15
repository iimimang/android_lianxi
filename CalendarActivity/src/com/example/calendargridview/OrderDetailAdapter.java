package com.example.calendargridview;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class OrderDetailAdapter extends BaseAdapter {
	protected LayoutInflater inflater;
	ViewHolder viewHolder = null;
	private List<OrderEntity> list;
	private Context context;

	public OrderDetailAdapter(Context context,List<OrderEntity> list) {
		this.list = list;
		this.context = context;
		this.inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView != null && convertView.getId() == R.id.order_gridview) {
			viewHolder = (ViewHolder) convertView.getTag();
		} else {
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.order_item, null);
			convertView.setFocusable(false);
			viewHolder.date = (TextView) convertView
					.findViewById(R.id.g_order_date);
			viewHolder.content1 = (TextView) convertView
					.findViewById(R.id.g_order_content1);
			viewHolder.content2 = (TextView) convertView
					.findViewById(R.id.g_order_content2);
			viewHolder.content3 = (TextView) convertView
					.findViewById(R.id.g_order_content3);
		}

		/* 控件添加内容 */

		viewHolder.date.setText(list.get(position)
				.getDate());
		if(list.get(position).getBgcolor() ==0){
			viewHolder.date.setTextColor(context.getResources().getColor(R.color.bt_pl_press));
			convertView.setBackgroundColor(context.getResources().getColor(R.color.bg_order));
		}else if(list.get(position).getBgcolor() ==1){
			viewHolder.date.setTextColor(context.getResources().getColor(R.color.black_color));
			convertView.setBackgroundColor(context.getResources().getColor(R.color.white));
		}else if(list.get(position).getBgcolor() ==2){
			viewHolder.date.setTextColor(context.getResources().getColor(R.color.black_color));
			convertView.setBackgroundColor(context.getResources().getColor(R.color.title_bg));
		}
		if(list.get(position).getContentnum()==0){
			viewHolder.content2.setVisibility(View.INVISIBLE);
			viewHolder.content3.setVisibility(View.INVISIBLE);
		}else if(list.get(position).getContentnum()==1){
			String[] str = list.get(position).getContent();
			viewHolder.content2.setVisibility(View.INVISIBLE);
			viewHolder.content3.setVisibility(View.INVISIBLE);
		}else if(list.get(position).getContentnum()==2){
			String[] str = list.get(position).getContent();
			viewHolder.content2.setText(str[1]);
			viewHolder.content3.setVisibility(View.INVISIBLE);
		}else if(list.get(position).getContentnum()==3){
			String[] str = list.get(position).getContent();
			viewHolder.content2.setText(str[1]);
			viewHolder.content3.setText(str[2]);
		}
		viewHolder.content1.setText(list.get(position)
				.getChina());
		return convertView;
	}

	private final class ViewHolder {
		private TextView date, content1,content2,content3;

	}

}
