package com.mycalendar;

import data.DateInfo;
import adapter.CalendarAdapter;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class OnItemClickListenerImpl implements OnItemClickListener {
	
	private CalendarAdapter adapter = null;
	private MainActivity activity = null;
	public OnItemClickListenerImpl(CalendarAdapter adapter, MainActivity activity) {
		this.adapter = adapter;
		this.activity = activity;
	}
	
	public void onItemClick(AdapterView<?> gridView, View view, int position, long id) {
		if (activity.currList.get(position).isThisMonth() == false) {
			return;
		}
		
		DateInfo selectdata=activity.currList.get(position);
		
		Log.i("aaaa",selectdata.getDate()+"------"+ selectdata.getNongliDate()+"------"+selectdata.getDate());
		
		activity.lastSelected=selectdata.getDate(); //手指选的 灰色的 那天---------------
		
		activity.date_infos_yangli.setText(String.format("%04d年%02d月%02d日", selectdata.getYear(), selectdata.getMonth(),selectdata.getDate()));
		
		activity.date_infos_yinli.setText("农历"+selectdata.getNongliInfo()); 
		
		if(selectdata.isHoliday()){
			activity.date_add_layout.setVisibility(View.VISIBLE);
			activity.date_infos_foli.setText(selectdata.getHoliday());
		}else{
			activity.date_add_layout.setVisibility(View.GONE);
		}
		activity.date_infos_left.setText(selectdata.getDate()+"");
		
		adapter.setSelectedPosition(position);  
		adapter.notifyDataSetInvalidated(); 
		activity.lastSelected = activity.currList.get(position).getDate(); 
	}
	
}
