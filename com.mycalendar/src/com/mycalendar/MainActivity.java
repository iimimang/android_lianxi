package com.mycalendar;


import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import utils.DataUtils;
import utils.Lunar;
import utils.StingUtil;
import utils.TimeUtils;
import data.DateInfo;
import adapter.CalendarAdapter;
import android.os.Bundle;
import android.app.Activity;
import android.content.res.AssetManager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;

import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 主界面
 * */
public class MainActivity extends Activity implements OnClickListener {

	/**
	 * 和viewpager相关变量
	 * */
	public MyViewPager viewPager = null;
	public MyPagerAdapter pagerAdapter = null;
	private int currPager = 500; // 当前的页数 (月历)

	/**
	 * 和日历gridview相关变量
	 * */
	private GridView gridView = null;
	public CalendarAdapter adapter = null;
	private GridView currentView = null;
	public List<DateInfo> currList = null; // 当前view的 数据
	public List<DateInfo> list = null; // 初始化 数据
	public int TodaySelect = 0; // 今天的日子
	public int lastSelected = 0; // 最后选择的那一天 日历和周历 的切换的基准天
	public int lastSelectedGray = 0;
	/**
	 * 显示年月
	 * */
	public TextView showYearMonth = null;

	/**
	 * 第一个页面的年月
	 * */
	private int currentYear;
	private int currentMonth;

	public TextView date_infos_yangli;
	public TextView date_infos_yinli;
	public TextView date_infos_foli;
	public TextView date_infos_left;
	public LinearLayout date_add_layout;

	public ImageView foli_bottom_images; // 佛历 图片
	private int calendar_type = 1; // 周历 和月历 的类别区分

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		findbyid();
		initData();
		initView();
		update_date_view();
	}

	private void findbyid() {
		// TODO Auto-generated method stub

		foli_bottom_images = (ImageView) findViewById(R.id.foli_bottom_images);
		foli_bottom_images.setOnClickListener(this);
		date_add_layout = (LinearLayout) findViewById(R.id.date_add_layout);
		date_infos_yangli = (TextView) findViewById(R.id.date_infos_yangli);
		date_infos_yinli = (TextView) findViewById(R.id.date_infos_yinli);
		date_infos_foli = (TextView) findViewById(R.id.date_infos_foli);
		date_infos_left = (TextView) findViewById(R.id.date_infos_left);

		viewPager = (MyViewPager) findViewById(R.id.viewpager);
		viewPager.setPageMargin(0);

		showYearMonth = (TextView) findViewById(R.id.popupwindow_calendar_month);

		currentYear = TimeUtils.getCurrentYear(); // 初始化 默认年
		currentMonth = TimeUtils.getCurrentMonth(); // 初始化 默认月
		lastSelected = TodaySelect = TimeUtils.getCurrentDay(); // 初始化 默认天 当天

	}

	private void update_date_view() {

		DateInfo currentdata = new DateInfo();
		Lunar Lunarinfo = TimeUtils.get_nongli_info(currentYear, currentMonth,
				lastSelected);
		currentdata.setDate(lastSelected);
		currentdata.setYear(currentYear);
		currentdata.setMonth(currentMonth);

		currentdata.setNongliInfo(Lunarinfo.getLunarMonthString() + "月"
				+ Lunarinfo.getLunarDayString()); // 农历日期 info
		currentdata.setNonglinumber(TimeUtils.frontCompWithZore(
				Lunarinfo.getLunarMonth(), 2)
				+ TimeUtils.frontCompWithZore(Lunarinfo.getLunarDay(), 2));

		if (Lunarinfo.isSFestival()) {
			currentdata.setNongliDate(StingUtil.toLength(
					Lunarinfo.getSFestivalName(), 12));
			currentdata.setHoliday(true);
			currentdata.setHoliday(Lunarinfo.getSFestivalName());
		} else {
			if (Lunarinfo.isLFestival()
					&& Lunarinfo.getLunarMonthString().substring(0, 1)
							.equals("闰") == false) {
				currentdata.setNongliDate(StingUtil.toLength(
						Lunarinfo.getLFestivalName(), 12));
				currentdata.setHoliday(true);
				currentdata.setHoliday(Lunarinfo.getLFestivalName());
			} else {
				if (Lunarinfo.getLunarDayString().equals("初一")) {
					currentdata.setNongliDate(Lunarinfo.getLunarMonthString()
							+ "月");
				} else {
					currentdata.setNongliDate(Lunarinfo.getLunarDayString());
				}
				currentdata.setHoliday(false);
			}
		}

		date_infos_yangli.setText(String.format("%04d年%02d月%02d日",
				currentdata.getYear(), currentdata.getMonth(),
				currentdata.getDate()));
		date_infos_yinli.setText("农历" + currentdata.getNongliInfo());

		if (currentdata.isHoliday()) {
			date_add_layout.setVisibility(View.VISIBLE);
			date_infos_foli.setText(currentdata.getHoliday());
		} else {
			date_add_layout.setVisibility(View.GONE);
		}
		date_infos_left.setText(currentdata.getDate() + "");
		
		AssetManager  assetManager=this.getAssets();  
		
		
        try {  
            InputStream in=assetManager.open(currentdata.getNonglinumber() + ".jpg");  
            Bitmap bmp=BitmapFactory.decodeStream(in);  
            foli_bottom_images.setImageBitmap(bmp);  
        } catch (Exception e) {  
            // TODO: handle exception  
        	Log.i("eeee", "-----找不到图了");
        	try {
				InputStream in = assetManager.open("0000.jpg");
				Bitmap bmp=BitmapFactory.decodeStream(in);  
	             foli_bottom_images.setImageBitmap(bmp); 
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}  
         }
         
	}

	/**
	 * 初始（转化）化月历数据
	 * */
	private void initData() {

		String formatDate = TimeUtils.getFormatDate(currentYear, currentMonth);

		Log.i("dddd", "月历数据---------" + formatDate + "------------"
				+ calendar_type);
		try {
			list = TimeUtils.initCalendar(formatDate, currentMonth);

		} catch (Exception e) {

			finish();
		}
	}

	/**
	 * 转化周历数据------------------------------------------------------
	 * */
	private void initWeekData() {
		Log.i("dddd", "周历数据---------" + currentMonth + "------------"
				+ calendar_type);
		try {
			list = TimeUtils.initCalendarWeek(currentYear, currentMonth,
					lastSelected);
		} catch (Exception e) {
			finish();
		}
	}

	/**
	 * 初始化月历 和 周 历 view
	 * */
	private void initView() {

		Log.i("dddd", "切换布局哪天了---------------年--" + currentYear
				+ "-------月----" + currentMonth + "----日----" + lastSelected
				+ "");

		if (calendar_type == 1) {

			int monthposition = TimeUtils.getmonthposition(currentYear,
					currentMonth, 1); // 获得当前月份的位置
			pagerAdapter = new MyPagerAdapter();
			viewPager.setAdapter(pagerAdapter);
			viewPager.setCurrentItem(monthposition);

			GridView MonthcurrentView = (GridView) viewPager
					.findViewById(monthposition);

			if (MonthcurrentView != null) {
				CalendarAdapter adapter = (CalendarAdapter) MonthcurrentView
						.getAdapter();
				Log.i("cccc", "initView发生了什么------------" + monthposition
						+ "----");
				currList = adapter.getList(); // 从周历切换回月历的时候要把 当前的 currList 替换掉
												// 不然 是之前周历的currList
			}
		} else {

			int weekposition = TimeUtils.getweekposition(currentYear,
					currentMonth, lastSelected);// 获得当前周的位置
			pagerAdapter = new MyPagerAdapter();
			viewPager.setAdapter(pagerAdapter);
			viewPager.setCurrentItem(weekposition);

			GridView WeekcurrentView = (GridView) viewPager
					.findViewById(weekposition);
			if (WeekcurrentView != null) {
				CalendarAdapter adapter = (CalendarAdapter) WeekcurrentView
						.getAdapter();

				currList = adapter.getList();
				int pos = DataUtils.getDayFlag(currList, lastSelectedGray);
				Log.i("dddd", "-周显示灰色的手指点中的天----- " + lastSelectedGray);
				adapter.setSelectedPosition(pos); // 翻页设置灰色背景
				adapter.notifyDataSetInvalidated();
			}
		}
		// 显示头部的 当前的 年和 月
		showYearMonth.setText(String.format("%04d年%02d月", currentYear,
				currentMonth));

		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			public void onPageScrollStateChanged(int arg0) {
				if (arg0 == 1) {
					// shader.setText("");
					// shader.setVisibility(View.VISIBLE);
				}
				if (arg0 == 0) {

					currentView = (GridView) viewPager.findViewById(currPager);
					if (currentView != null) {
						adapter = (CalendarAdapter) currentView.getAdapter();
						currList = adapter.getList();
						if (calendar_type == 1) {
							// 翻页设置当天
							if (currPager == 500) {

								int todaypos = DataUtils.getDayFlag(currList,
										TodaySelect);
								adapter.setTodayPosition(todaypos); // 设置当天
							} else {
								int pos = DataUtils.getDayFlag(currList,
										lastSelected);
								adapter.setSelectedPosition(pos); // 翻页设置灰色背景
							}
							update_date_view();
						} else {
							// 翻页设置当天
							if (currPager == 500) {
								Log.i("dddd", "-周翻页布局更改----- ");
								int todaypos = DataUtils.getWeekDayFlag(
										currList, TodaySelect);
								if (todaypos != -1) {
									adapter.setTodayPosition(todaypos); // 设置当天
								}
								Log.i("dddd", "-周显示黄色的今天----- " + TodaySelect
										+ "------" + todaypos + "--info--");
							}
						}
						adapter.notifyDataSetInvalidated();
					}
				}
			}

			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@SuppressWarnings("deprecation")
			public void onPageSelected(int position) {
				Log.i("eeee", "-onPageSelected---翻页到了第几页--" + position + "---");

				if (calendar_type == 1) {
					int year = TimeUtils.getTimeByPosition(position,
							currentYear, currentMonth, "year");
					int month = TimeUtils.getTimeByPosition(position,
							currentYear, currentMonth, "month");

					if (position != 500) { // 不是当月 ， 每个月默认 1号 为 lastselect
						lastSelectedGray = lastSelected = 1;
					} else {
						lastSelected = TodaySelect; // 返回当月的时候 默认是今天 为了切换周历
					}

					currentYear = year;
					currentMonth = month;
					Log.i("dddd", "位置--" + position + "翻页月的时候---年"
							+ currentYear + "----月" + currentMonth
							+ "----最后选中的天" + lastSelected);

				} else {

					Date s = TimeUtils.getWeekTimeByPosition(position);
					// SimpleDateFormat formatter=new
					// SimpleDateFormat("yyyy年MM月");
					currentYear = s.getYear() + 1900;
					currentMonth = s.getMonth() + 1;
					lastSelected = s.getDate();
					Log.i("dddd", "周历翻页--------------年--" + currentYear
							+ "-------月----" + currentMonth + "----日----"
							+ lastSelected + "---位置--" + position);

					// showYearMonth.setText(formatter.format(s));
				}

				currPager = position;

				showYearMonth.setText(String.format("%04d年%02d月", currentYear,
						currentMonth));

			}
		});
	}

	/**
	 * 初始化日历的gridview
	 * */
	@SuppressWarnings("deprecation")
	private GridView initCalendarView(int position) {

		if (calendar_type == 1) { // 月历 根据位置 输出 数据

			int year = TimeUtils.getTimeByPosition(position, currentYear,
					currentMonth, "year");
			int month = TimeUtils.getTimeByPosition(position, currentYear,
					currentMonth, "month");
			String formatDate = TimeUtils.getFormatDate(year, month);

			try {

				list = TimeUtils.initCalendar(formatDate, month);
			} catch (Exception e) {
				finish();
			}

		} else { // 周历 根据位置 输出 数据

			Date currtinsert = TimeUtils.getWeekTimeByPosition(position);
			int year = currtinsert.getYear() + 1900;
			int month = currtinsert.getMonth() + 1;
			int day = currtinsert.getDate();
			Log.i("aaaa", "每次的位置是---------" + position + "------年--" + year
					+ "-------月----" + month + "----日----" + day + "");

			try {

				list = TimeUtils.initCalendarWeek(year, month, day);
			} catch (Exception e) {

				finish();
			}

		}

		gridView = new GridView(this);
		adapter = new CalendarAdapter(this, list);
		if (position == 500 && currPager == 500) { // 为了防止 currList 在翻页到 501 和
													// 502时候 发生变化 那么点击事件
													// 临近的前后俩月都将是500 的那个月
			currList = list;
			Log.i("cccc", "发生了什么------------" + currPager);
			int pos = DataUtils.getDayFlag(list, TimeUtils.getCurrentDay());
			adapter.setTodayPosition(pos); // 设置当天
		}
		gridView.setAdapter(adapter);
		gridView.setNumColumns(7);
		gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		gridView.setGravity(Gravity.CENTER);
		gridView.setOnItemClickListener(new OnItemClickListenerImpl(adapter,
				this));
		return gridView;
	}

	/**
	 * viewpager的适配器，从第500页开始，最多支持0-1000页
	 * */
	private class MyPagerAdapter extends PagerAdapter {

		@Override
		public void setPrimaryItem(ViewGroup container, int position,
				Object object) {
			currentView = (GridView) object;
			adapter = (CalendarAdapter) currentView.getAdapter();
		}

		@Override
		public int getCount() {
			return 1000;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			GridView gv;

			gv = initCalendarView(position);
			if (calendar_type == 2) {
				Log.i("dddd", "周历适配-------------");
				gv.setTag("22");
			} else {
				Log.i("dddd", "月历适配-------------");
				gv.setTag("11");
			}
			gv.setId(position);
			container.addView(gv);
			return gv;
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.foli_bottom_images:

			Log.i("cccc", "点击-------------");
			if (calendar_type == 1) {
				initWeekData();
				calendar_type = 2;
				initView();
			} else {
				initData();
				calendar_type = 1;
				initView();
			}
			break;
		}
	}
}
