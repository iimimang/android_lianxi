package com.wyj.Activity;

import java.util.Calendar;


import com.wyj.calendar.ChinaDate;
import com.wyj.calendar.KCalendar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wyj.calendar.KCalendar.OnCalendarClickListener;
import com.wyj.calendar.KCalendar.OnCalendarDateChangedListener;
import com.wyj.pipe.Cms;
import com.wyj.pipe.Utils;
import com.wyj.utils.StingUtil;
import com.wyj.Activity.R;

public class Foli extends MainActivity implements OnClickListener{

	String date = null;// 设置默认选中的日期 格式为 “2014-04-05” 标准DATE格式

	Button bt;

	private TextView date_infos_left, date_infos_yangli, date_infos_yinli,
			date_infos_foli_or_birthday,add_user_birthday;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.foli);
		findViewById();
		setAction();
		PopupWindows();
	}

	private void findViewById() {

		date_infos_left = (TextView) findViewById(R.id.date_infos_left);
		date_infos_yangli = (TextView) findViewById(R.id.date_infos_yangli);
		date_infos_yinli = (TextView) findViewById(R.id.date_infos_yinli);
		date_infos_foli_or_birthday = (TextView) findViewById(R.id.date_infos_foli_or_birthday);
		
		add_user_birthday=(TextView) findViewById(R.id.add_user_birthday);
		
		add_user_birthday.setOnClickListener(this);
	}

	private void setAction() {
		// TODO Auto-generated method stub
		final Calendar c = Calendar.getInstance();
		int mYear = c.get(Calendar.YEAR); // 获取当前年份
		int mMonth = c.get(Calendar.MONTH) + 1;// 获取当前月份
		int mDay = c.get(Calendar.DAY_OF_MONTH);// 获取当前月份的日期号码

		//Log.i("cccc", "------当前的日期----" + mYear + "---" + mMonth + "---" + mDay);

		date_infos_left.setText(mDay + "");
		date_infos_yangli.setText(ChinaDate.get_yangli_today());
		date_infos_yinli.setText(ChinaDate.get_yinli(mYear, mMonth, mDay));
		if (!ChinaDate.oneDayiswhat(mYear, mMonth, mDay).equals("")) {
			date_infos_foli_or_birthday.setText(ChinaDate.oneDayiswhat(mYear,
					mMonth, mDay));
		} else {
			date_infos_foli_or_birthday.setVisibility(View.GONE);
		}
	}
	
	private void set_action_time() {
		// TODO Auto-generated method stub
		
		String[] dates=StingUtil.split( date,"-");
		int mYear = Integer.valueOf(dates[0]).intValue(); // 获取当前年份
		int mMonth = Integer.valueOf(dates[1]).intValue();// 获取当前月份
		int mDay = Integer.valueOf(dates[2]).intValue();// 获取当前月份的日期号码
	//	Log.i("cccc", "------点击日历选中的日期----" + mMonth);
		int weekday=0;
		try {
			weekday = StingUtil.dayForWeek(date);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		date_infos_left.setText(mDay + "");
		date_infos_yangli.setText(ChinaDate.get_yangli(mYear, mMonth, mDay,weekday+""));
		date_infos_yinli.setText(ChinaDate.get_yinli(mYear, mMonth, mDay));
		if (!ChinaDate.oneDayiswhat(mYear, mMonth, mDay).equals("")) {
			date_infos_foli_or_birthday.setText(ChinaDate.oneDayiswhat(mYear,
					mMonth, mDay));
			date_infos_foli_or_birthday.setVisibility(View.VISIBLE);
		} else {
			date_infos_foli_or_birthday.setVisibility(View.GONE);
		}
	}

	public void PopupWindows() {

		LinearLayout ll_popup = (LinearLayout) findViewById(R.id.ll_popup);
		final TextView popupwindow_calendar_month = (TextView) findViewById(R.id.popupwindow_calendar_month);
		final KCalendar calendar = (KCalendar) findViewById(R.id.popupwindow_calendar);
		popupwindow_calendar_month.setText(calendar.getCalendarYear() + "年"
				+ calendar.getCalendarMonth() + "月");

		if (null != date) {

			int years = Integer.parseInt(date.substring(0, date.indexOf("-")));
			int month = Integer.parseInt(date.substring(date.indexOf("-") + 1,
					date.lastIndexOf("-")));
			popupwindow_calendar_month.setText(years + "年" + month + "月");

			calendar.showCalendar(years, month);
			calendar.setCalendarDayBgColor(date,
					R.drawable.calendar_date_focused);
		}

		// List<String> list = new ArrayList<String>(); // 设置标记列表
		// list.add("2014-04-01");
		// list.add("2014-04-02");
		// calendar.addMarks(list, 0);

		// 监听所选中的日期
		calendar.setOnCalendarClickListener(new OnCalendarClickListener() {

			@Override
			public void onCalendarClick(int row, int col, String dateFormat) {
				int month = Integer.parseInt(dateFormat.substring(
						dateFormat.indexOf("-") + 1,
						dateFormat.lastIndexOf("-")));

				if (calendar.getCalendarMonth() - month == 1// 跨年跳转
						|| calendar.getCalendarMonth() - month == -11) {
					calendar.lastMonth();

				} else if (month - calendar.getCalendarMonth() == 1 // 跨年跳转
						|| month - calendar.getCalendarMonth() == -11) {
					calendar.nextMonth();

				} else {
					
					calendar.removeAllBgColor();
					calendar.setCalendarDayBgColor(dateFormat,
							R.drawable.calendar_date_focused);
					date = dateFormat;// 最后返回给全局 date
					set_action_time();
				}
			}

		
		});

		// 监听当前月份
		calendar.setOnCalendarDateChangedListener(new OnCalendarDateChangedListener() {
			@Override
			public void onCalendarDateChanged(int year, int month) {
				popupwindow_calendar_month.setText(year + "年" + month + "月");
			}
		});

		// 上月监听按钮
		RelativeLayout popupwindow_calendar_last_month = (RelativeLayout) findViewById(R.id.popupwindow_calendar_last_month);
		popupwindow_calendar_last_month
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						calendar.lastMonth();
					}

				});

		// 下月监听按钮
		RelativeLayout popupwindow_calendar_next_month = (RelativeLayout) findViewById(R.id.popupwindow_calendar_next_month);
		popupwindow_calendar_next_month
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						calendar.nextMonth();
					}
				});

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.add_user_birthday:
			if(TextUtils.isEmpty(Cms.APP.getMemberId())){
				
				Utils.Dialog(getParent().getParent(), "提示", "请先登录！");
			} else {
				Intent intent = new Intent(Foli.this, AddBirthday.class);
				FoLiGroupTab.getInstance().switchActivity("AddBirthday", intent, -1,
						-1);
			}
			break;

		}
	}

}
