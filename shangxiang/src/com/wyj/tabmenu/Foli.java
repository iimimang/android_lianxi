package com.wyj.tabmenu;


import java.util.ArrayList;
import java.util.List;


import com.wyj.calendar.KCalendar;


import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wyj.calendar.KCalendar.OnCalendarClickListener;
import com.wyj.calendar.KCalendar.OnCalendarDateChangedListener;

public class Foli extends Activity
{
	
	String date = null;// 设置默认选中的日期  格式为 “2014-04-05” 标准DATE格式   

	Button bt;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.foli);
		PopupWindows();
	}
	
	public void PopupWindows() {

			LinearLayout ll_popup = (LinearLayout) findViewById(R.id.ll_popup);
			final TextView popupwindow_calendar_month = (TextView) findViewById(R.id.popupwindow_calendar_month);
			final KCalendar calendar = (KCalendar) findViewById(R.id.popupwindow_calendar);
			Button popupwindow_calendar_bt_enter = (Button) findViewById(R.id.popupwindow_calendar_bt_enter);

			popupwindow_calendar_month.setText(calendar.getCalendarYear() + "年"
					+ calendar.getCalendarMonth() + "月");

			if (null != date) {

				int years = Integer.parseInt(date.substring(0,
						date.indexOf("-")));
				int month = Integer.parseInt(date.substring(
						date.indexOf("-") + 1, date.lastIndexOf("-")));
				popupwindow_calendar_month.setText(years + "年" + month + "月");

				calendar.showCalendar(years, month);
				calendar.setCalendarDayBgColor(date,
						R.drawable.calendar_date_focused);				
			}
			
			List<String> list = new ArrayList<String>(); //设置标记列表
			list.add("2014-04-01");
			list.add("2014-04-02");
			calendar.addMarks(list, 0);

			//监听所选中的日期
			calendar.setOnCalendarClickListener(new OnCalendarClickListener() {

				public void onCalendarClick(int row, int col, String dateFormat) {
					int month = Integer.parseInt(dateFormat.substring(
							dateFormat.indexOf("-") + 1,
							dateFormat.lastIndexOf("-")));
					
					if (calendar.getCalendarMonth() - month == 1//跨年跳转
							|| calendar.getCalendarMonth() - month == -11) {
						calendar.lastMonth();
						
					} else if (month - calendar.getCalendarMonth() == 1 //跨年跳转
							|| month - calendar.getCalendarMonth() == -11) {
						calendar.nextMonth();
						
					} else {
						calendar.removeAllBgColor(); 
						calendar.setCalendarDayBgColor(dateFormat,
								R.drawable.calendar_date_focused);
						date = dateFormat;//最后返回给全局 date
					}
				}
			});

			//监听当前月份
			calendar.setOnCalendarDateChangedListener(new OnCalendarDateChangedListener() {
				public void onCalendarDateChanged(int year, int month) {
					popupwindow_calendar_month
							.setText(year + "年" + month + "月");
				}
			});
			
			//上月监听按钮
			RelativeLayout popupwindow_calendar_last_month = (RelativeLayout)findViewById(R.id.popupwindow_calendar_last_month);
			popupwindow_calendar_last_month
					.setOnClickListener(new OnClickListener() {

						public void onClick(View v) {
							calendar.lastMonth();
						}

					});
			
			//下月监听按钮
			RelativeLayout popupwindow_calendar_next_month = (RelativeLayout) findViewById(R.id.popupwindow_calendar_next_month);
			popupwindow_calendar_next_month
					.setOnClickListener(new OnClickListener() {

						public void onClick(View v) {
							calendar.nextMonth();
						}
					});
			
			
		}
	
	
	
}
