package com.mycalendar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;



import utils.DataUtils;
import utils.TimeUtils;
import data.DateInfo;
import adapter.CalendarAdapter;
import android.os.Bundle;
import android.app.Activity;

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
public class MainActivity extends Activity implements OnClickListener{

	/**
	 * 和viewpager相关变量
	 * */
	public MyViewPager viewPager = null;
	public MyPagerAdapter pagerAdapter = null;
	private int currPager = 500;	//当前的页数 (月历)
	private int cureetposition = 500;

	/**
	 * 和日历gridview相关变量
	 * */
	private GridView gridView = null;
	public CalendarAdapter adapter = null;
	private GridView currentView = null;
	public List<DateInfo> currList = null;
	public List<DateInfo> list = null;	//初始化 数据
	public int TodaySelect = 0;	//最后选择的那一天   日历和周历 的切换的基准天  
	public int lastSelected = 0; // 周历

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
	
	private ImageView foli_bottom_image222;
	private int calendar_type=1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		findbyid();
		initData();
		initView();
	}

	private void findbyid() {
		// TODO Auto-generated method stub
		
		foli_bottom_image222=(ImageView) findViewById(R.id.foli_bottom_images);
		
		foli_bottom_image222.setOnClickListener(this);
		date_add_layout = (LinearLayout) findViewById(R.id.date_add_layout);
		date_infos_yangli = (TextView) findViewById(R.id.date_infos_yangli);
		date_infos_yinli = (TextView) findViewById(R.id.date_infos_yinli);
		date_infos_foli = (TextView) findViewById(R.id.date_infos_foli);
		date_infos_left = (TextView) findViewById(R.id.date_infos_left);

		viewPager = (MyViewPager) findViewById(R.id.viewpager);
		
		showYearMonth = (TextView) findViewById(R.id.popupwindow_calendar_month);
		
		currentYear = TimeUtils.getCurrentYear();		// 初始化 默认年
		currentMonth = TimeUtils.getCurrentMonth();    // 初始化 默认月
		lastSelected=TodaySelect = TimeUtils.getCurrentDay();     // 初始化 默认天 当天
		
	}

	/**
	 * 初始化月历数据
	 * */
	private void initData() {
		
		String formatDate = TimeUtils.getFormatDate(currentYear, currentMonth);
		
		Log.i("dddd",
				"月历-------------到哪天了---------"  +formatDate+"------------"+calendar_type);
		
		try {
			list = TimeUtils.initCalendar(formatDate, currentMonth);
			
//			Log.i("dddd",
//					"月历------------数据--------"  +list.toString());
		} catch (Exception e) {
			Log.i("aaaa", "111111111");
			finish();
		}
	}

	/**
	 * 初始化月历view
	 * */
	private void initView() {
		
		int monthposition=TimeUtils.getmonthposition(currentYear, currentMonth,1);	//获得当前月份的位置
		pagerAdapter = new MyPagerAdapter();
		viewPager.setAdapter(pagerAdapter);
		viewPager.setCurrentItem(monthposition);
		viewPager.setPageMargin(0);
		
		Log.i("dddd",
				"月历-------------到哪天了---------"  + "------年--"
						+ currentYear + "-------月----"
						+ currentMonth + "----日----"
						+ lastSelected+"------------"+calendar_type+"----位置---"+monthposition);  
		
		showYearMonth.setText(String.format("%04d年%02d月", currentYear,
				currentMonth));
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			public void onPageScrollStateChanged(int arg0) {
				if (arg0 == 1) {
					// shader.setText("");
					// shader.setVisibility(View.VISIBLE);
				}
				if (arg0 == 0) {
					
				if(calendar_type==1){
					currentView = (GridView) viewPager.findViewById(currPager);
					if (currentView != null) {
						
						adapter = (CalendarAdapter) currentView.getAdapter();
						currList = adapter.getList();
							// 翻页设置当天
						 if(currentMonth==TimeUtils.getCurrentMonth() &&
						 currentYear==TimeUtils.getCurrentYear()){
							 
							 Log.i("dddd", "dang--"+currentMonth+"------s yue "+TimeUtils.getCurrentMonth());
							 
							 int todaypos = DataUtils.getDayFlag(currList,TodaySelect);
							 adapter.setTodayPosition(todaypos); //设置当天
						 }else{
							 
							 int pos = DataUtils.getDayFlag(currList, lastSelected);
							 adapter.setSelectedPosition(pos); // 翻页设置灰色背景
						 }

						 adapter.notifyDataSetInvalidated(); 
					} 

				}
					}
			} 

			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			} 

			public void onPageSelected(int position) {
				
				if(calendar_type==1){
					int year = TimeUtils.getTimeByPosition(position, currentYear,
							currentMonth, "year");
					int month = TimeUtils.getTimeByPosition(position, currentYear,
							currentMonth, "month");
					
					if(position!=500){		//不是当月 ， 每个月默认 1号 为  lastselect
						
						lastSelected=1;
					}else{
						lastSelected =TodaySelect;		//返回当月的时候   默认是今天    为了切换周历
					}
					currentYear = year;
					currentMonth = month;
					Log.i("dddd", "位置--"+position+"翻页月的时候---年"+currentYear+"----月"+currentMonth+"----最后选中的天"+lastSelected);
					showYearMonth.setText(String.format("%04d年%02d月", year, month));
					currPager = position;
				}
			} 
		});
	}



	/**
	 * 初始化日历的gridview
	 * */
	private GridView initCalendarView(int position) {

		int year = TimeUtils.getTimeByPosition(position, currentYear,
				currentMonth, "year");
		int month = TimeUtils.getTimeByPosition(position, currentYear,
				currentMonth, "month");
		String formatDate = TimeUtils.getFormatDate(year, month);

		try {

			list = TimeUtils.initCalendar(formatDate, month);
		} catch (Exception e) {
			Log.i("aaaa", "错的-----是------------" + formatDate
					+ "--------------" + month);
			finish();
		}
		gridView = new GridView(this);
		adapter = new CalendarAdapter(this, list);
		if (position == 500) {
			currList = list;
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
	 * 初始化周历数据------------------------------------------------------
	 * */
	private void initWeekData() {

		try {
			list = TimeUtils.initCalendarWeek(currentYear, currentMonth,lastSelected);
		} catch (Exception e) {
			Log.i("aaaa", "111111111");
			finish();
		}
	}
	

	/**
	 * 初始化view
	 * */
	private void initViewWeek() {
		
		int weekposition=TimeUtils.getweekposition(currentYear, currentMonth,lastSelected);//获得当前周的位置
		
		Log.i("dddd",
				"周到哪天了---------" + weekposition + "------年--"
						+ currentYear + "-------月----"
						+ currentMonth + "----日----"
						+ lastSelected+""); 
		
		pagerAdapter = new MyPagerAdapter(); 
		viewPager.setAdapter(pagerAdapter);
		viewPager.setCurrentItem(weekposition);
		viewPager.setPageMargin(0);
		showYearMonth.setText(String.format("%04d年%02d月", currentYear,
				currentMonth));
		
		GridView WeekcurrentView = (GridView) viewPager.findViewById(weekposition);
		if (WeekcurrentView != null) {
			CalendarAdapter adapter = (CalendarAdapter) WeekcurrentView.getAdapter();
			 List<DateInfo> currList = adapter.getList();
			 int pos = DataUtils.getDayFlag(currList, lastSelected); 
			 Log.i("dddd", "-周显示灰色的手指点中的天----- "+lastSelected);
			 adapter.setSelectedPosition(pos); // 翻页设置灰色背景
			 adapter.notifyDataSetInvalidated(); 
		}
		
		
		
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			public void onPageScrollStateChanged(int arg0) {
				if (arg0 == 1) {
					// shader.setText("");
					// shader.setVisibility(View.VISIBLE);
				}
				if (arg0 == 0) {
					
					if(calendar_type==2){
						currentView = (GridView) viewPager.findViewById(cureetposition);
						if (currentView != null) {
							adapter = (CalendarAdapter) currentView.getAdapter();
							currList = adapter.getList();							 
							// 翻页设置当天
							 if(currentMonth==TimeUtils.getCurrentMonth() &&
							 currentYear==TimeUtils.getCurrentYear() ){
								 
								 int todaypos = DataUtils.getWeekDayFlag(currList,TodaySelect);
								 if(todaypos!=-1){
									 adapter.setTodayPosition(todaypos); //设置当天 
								 }
								  Log.i("dddd", "-周显示黄色的今天----- "+TodaySelect+"------"+todaypos+"--info--");
								 
							 }										 
							adapter.notifyDataSetInvalidated();
						}
					}	
				}
			}

			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			public void onPageSelected(int position) {
				if(calendar_type==2){
					Date s = TimeUtils.getWeekTimeByPosition(position);
					SimpleDateFormat formatter=new SimpleDateFormat("yyyy年MM月"); 
					
					currentYear=s.getYear()+1900;
					currentMonth =s.getMonth()+1;
					lastSelected = s.getDate();
					Log.i("dddd",
							"周历翻页--------" + formatter.format(s) + "------年--"
									+ currentYear + "-------月----"
									+ currentMonth + "----日----"
									+ lastSelected+"---位置--"+position);  
					
					showYearMonth.setText(formatter.format(s));
					cureetposition = position;
				}
			}
		});
	}
 
	/**
	 * 初始化周历的gridview-------------------------------------------------
	 * */
	@SuppressWarnings("deprecation")
	private GridView initCalendarView_Week(int position) {

		Date currtinsert = TimeUtils.getWeekTimeByPosition(position);
			
		int year = currtinsert.getYear()+1900;
		int month = currtinsert.getMonth()+1;
		int day = currtinsert.getDate();
		Log.i("aaaa",
				"每次的位置是---------" + position + "------年--"
						+ year + "-------月----"
						+ month + "----日----"
						+ day+"");  

		try {

			 list = TimeUtils.initCalendarWeek(year, month,day);
		} catch (Exception e) {
			// Log.i("aaaa",
			// "错的-----是------------"+formatDate+"--------------"+month);
			finish();
		}
		
		gridView = new GridView(this);
		adapter = new CalendarAdapter(this, list);
		if (position == 500) {
			currList = list;
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
			if(calendar_type==2){
				
				Log.i("dddd", "周历适配-------------");
				gv = initCalendarView_Week(position);
				
				gv.setTag("22");
			}else{
				Log.i("dddd", "月历适配-------------");
				gv = initCalendarView(position);
				gv.setTag("11");
			}
			Log.i("aaaa", "执行-------");
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
			if(calendar_type==1){
				initWeekData();
				calendar_type=2;
				initViewWeek();
			}else{
				initData();
				calendar_type=1;
				initView();
			}
			break;
		} 
	}
}
