package com.notbook.calendar;

/**
 * @author zhouxin@easier
 */

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;
import com.example.notbook.R;
import com.notbook.app.AppManager;
import com.notbook.photodb.Info;
import com.notbook.things.Meta;
import com.notbook.things.Thing;
import com.notbook.ui.BaseActivity;
import com.notbook.ui.Celender;
import com.notbook.ui.Celenderdetail;
import com.notbook.ui.Eventlist;
import com.notbook.ui.MainActivity4;

public class CalendarView extends BaseActivity implements OnTouchListener {
	
	/**
	 * 日历布局ID
	 */
	private static final int CAL_LAYOUT_ID = 55;
	// 判断手势用
	private static final int SWIPE_MIN_DISTANCE = 120;

	private static final int SWIPE_MAX_OFF_PATH = 250;

	private static final int SWIPE_THRESHOLD_VELOCITY = 200;
	public static String selectedDay = "";
	public static String CalendarViewday= "";
	static ContentResolver resolver ;
	Cursor c = null;
	public static List ss = new ArrayList();
	// 动画
	private Animation slideLeftIn;
	private Animation slideLeftOut;
	private Animation slideRightIn;
	private Animation slideRightOut;
	private ViewFlipper viewFlipper;
	GestureDetector mGesture = null;

	/**
	 * 今天按钮
	 */
	private Button mTodayBtn;

	/**
	 * 上一个月按钮
	 */
	private ImageView mPreMonthImg;

	/**
	 * 下一个月按钮
	 */
	private ImageView mNextMonthImg;

	/**
	 * 用于显示今天的日期
	 */
	private TextView mDayMessage;
	private TextView mDayMessage1;

	/**
	 * 用于装截日历的View
	 */
	private RelativeLayout mCalendarMainLayout;

	// 基本变量
	private Context mContext = CalendarView.this;
	/**
	 * 上一个月View
	 */
	private GridView firstGridView;

	/**
	 * 当前月View
	 */
	private GridView currentGridView;

	/**
	 * 下一个月View
	 */
	private GridView lastGridView;

	/**
	 * 当前显示的日历
	 */
	public static Calendar calStartDate = Calendar.getInstance();

	/**
	 * 选择的日历
	 */
	private Calendar calSelected = Calendar.getInstance();

	/**
	 * 今日
	 */
	private Calendar calToday = Calendar.getInstance();

	/**
	 * 当前界面展示的数据源
	 */
	private CalendarGridViewAdapter currentGridAdapter;

	/**
	 * 预装载上一个月展示的数据源
	 */
	private CalendarGridViewAdapter firstGridAdapter;

	/**
	 * 预装截下一个月展示的数据源
	 */
	private CalendarGridViewAdapter lastGridAdapter;
	//
	/**
	 * 当前视图月
	 */
	private int mMonthViewCurrentMonth = 0;

	/**
	 * 当前视图年
	 */
	private int mMonthViewCurrentYear = 0;

	/**
	 * 起始周
	 */
	private int iFirstDayOfWeek = Calendar.MONDAY;

	String str2 = "";
	int year = 0;
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		return mGesture.onTouchEvent(event);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 隐藏titlebar必须在得到view前社长
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 下面是隐藏titlebar并且全屏显示需要设置的
		// could also be done later
//		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.calendar_main);
		/*findViewById(R.id.one).setBackgroundResource(background_photobg[num - 1]);
		findViewById(R.id.one1).setBackgroundResource(background_photobg1[num - 1]);*/
		findViewById(R.id.main_layout).setBackgroundResource(background_photobg[num - 1]);
		findViewById(R.id.title_layout).setBackgroundResource(background_photobg1[num - 1]);
		Button eventdetail = (Button)findViewById(R.id.eventdetail);
		eventdetail.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent it = new Intent(com.notbook.calendar.CalendarView.this,MainActivity4.class);
				startActivity(it);
			}
		});
		
		initView();
		updateStartDateForMonth();

		generateContetView(mCalendarMainLayout);
		slideLeftIn = AnimationUtils.loadAnimation(this, R.anim.slide_left_in);
		slideLeftOut = AnimationUtils
				.loadAnimation(this, R.anim.slide_left_out);
		slideRightIn = AnimationUtils
				.loadAnimation(this, R.anim.slide_right_in);
		slideRightOut = AnimationUtils.loadAnimation(this,
				R.anim.slide_right_out);

		slideLeftIn.setAnimationListener(animationListener);
		slideLeftOut.setAnimationListener(animationListener);
		slideRightIn.setAnimationListener(animationListener);
		slideRightOut.setAnimationListener(animationListener);

		mGesture = new GestureDetector(this, new GestureListener());
	}

	/**
	 * 用于初始化控件
	 */
	private void initView() {

		mTodayBtn = (Button) findViewById(R.id.today_btn);
		// 默认显示当天，默认隐藏按钮 解决打开软件时显示按钮
		mTodayBtn.setVisibility(View.GONE);
		resolver = getContentResolver();
		c = resolver.query(com.notbook.photodb.Meta.CONTENT_URI, null, null, null, null);
		ss.clear();
		if( null != c && c.moveToFirst()){
			for(c.moveToFirst();!c.isAfterLast();c.moveToNext())  
			{  
			int nameColumn = c.getColumnIndex(com.notbook.photodb.Meta.TableMeta._DATE);  
			String name = c.getString(nameColumn);  
			ss.add(name);
			Log.i("log", "-----------图片："+name);
		} 

		}
		
        
		mDayMessage = (TextView) findViewById(R.id.day_message);
		mDayMessage1 = (TextView) findViewById(R.id.day_message1);
		mCalendarMainLayout = (RelativeLayout) findViewById(R.id.calendar_main);
		mPreMonthImg = (ImageView) findViewById(R.id.left_img);
		mNextMonthImg = (ImageView) findViewById(R.id.right_img);
		mTodayBtn.setOnClickListener(onTodayClickListener);
		mPreMonthImg.setOnClickListener(onPreMonthClickListener);
		mNextMonthImg.setOnClickListener(onNextMonthClickListener);
		
	}

	/**
	 * 用于加载到当前的日期的事件
	 */
	private View.OnClickListener onTodayClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			calStartDate = Calendar.getInstance();
			updateStartDateForMonth();
			generateContetView(mCalendarMainLayout);
//			今天按钮点击事件，点击跳转到今天后，隐藏今天按钮
			mTodayBtn.setVisibility(View.GONE);
		}
	};

	/**
	 * 用于加载上一个月日期的事件
	 */
	private View.OnClickListener onPreMonthClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			viewFlipper.setInAnimation(slideRightIn);
			viewFlipper.setOutAnimation(slideRightOut);
			viewFlipper.showPrevious();
			setPrevViewItem();
			setPrevViewItem1();
		}
	};

	/**
	 * 用于加载下一个月日期的事件
	 */
	private View.OnClickListener onNextMonthClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			viewFlipper.setInAnimation(slideLeftIn);
			viewFlipper.setOutAnimation(slideLeftOut);
			viewFlipper.showNext();
			setNextViewItem();
			setNextViewItem1();
		}
	};

	/**
	 * 主要用于生成发前展示的日历View
	 * 
	 * @param layout
	 *            将要用于去加载的布局
	 */
	private void generateContetView(RelativeLayout layout) {
		// 创建一个垂直的线性布局（整体内容）
//		viewFlipper手势动画
		viewFlipper = new ViewFlipper(this);
		viewFlipper.setId(CAL_LAYOUT_ID);
		calStartDate = getCalendarStartDate();
		CreateGirdView();
		RelativeLayout.LayoutParams params_cal = new RelativeLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		layout.addView(viewFlipper, params_cal);

//				填充日历下方颜色，否则显示黑色
		LinearLayout br = new LinearLayout(this);
		RelativeLayout.LayoutParams params_br = new RelativeLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		params_br.addRule(RelativeLayout.BELOW, CAL_LAYOUT_ID);
		layout.addView(br, params_br);
	}

	/**
	 * 用于创建当前将要用于展示的View
	 */
	private void CreateGirdView() {

		Calendar firstCalendar = Calendar.getInstance(); // 临时
		Calendar currentCalendar = Calendar.getInstance(); // 临时
		Calendar lastCalendar = Calendar.getInstance(); // 临时
		firstCalendar.setTime(calStartDate.getTime());
		currentCalendar.setTime(calStartDate.getTime());
		lastCalendar.setTime(calStartDate.getTime());

		firstGridView = new CalendarGridView(mContext);
		
		
		
        
        
		firstCalendar.add(Calendar.MONTH, -1);
		firstGridAdapter = new CalendarGridViewAdapter(this, firstCalendar);
		firstGridView.setAdapter(firstGridAdapter);// 设置菜单Adapter
		firstGridView.setId(CAL_LAYOUT_ID);
		firstGridView.setSelector(R.drawable.touming);
		currentGridView = new CalendarGridView(mContext);
		
		currentGridAdapter = new CalendarGridViewAdapter(this, currentCalendar);
		currentGridView.setAdapter(currentGridAdapter);// 设置菜单Adapter
		currentGridView.setId(CAL_LAYOUT_ID);
		currentGridView.setSelector(R.drawable.touming);
		lastGridView = new CalendarGridView(mContext);
		lastCalendar.add(Calendar.MONTH, 1);
		lastGridAdapter = new CalendarGridViewAdapter(this, lastCalendar);
		lastGridView.setAdapter(lastGridAdapter);// 设置菜单Adapter
		lastGridView.setId(CAL_LAYOUT_ID);
		lastGridView.setSelector(R.drawable.touming);
		currentGridView.setOnTouchListener(this);
		firstGridView.setOnTouchListener(this);
		lastGridView.setOnTouchListener(this);

		if (viewFlipper.getChildCount() != 0) {
			viewFlipper.removeAllViews();
		}

		viewFlipper.addView(currentGridView);
		viewFlipper.addView(lastGridView);
		viewFlipper.addView(firstGridView);
		String str = cyclical();
		String str1 = animalsYear();
		Calendar cal = Calendar.getInstance() ;
		CalendarUtil c = new CalendarUtil(cal);
		str2 = c.getmon();
		String s = calStartDate.get(Calendar.YEAR)
				+ "年"
				+ NumberHelper.LeftPad_Tow_Zero(calStartDate
						.get(Calendar.MONTH) + 1) + "月";
		mDayMessage.setText(s);
		mDayMessage1.setText(str+str1+"年"+str2);
	}

	/**
	 * 上一个月
	 */
	private void setPrevViewItem() {
		mMonthViewCurrentMonth--;// 当前选择月--
		// 如果当前月为负数的话显示上一年
		if (mMonthViewCurrentMonth == -1) {
			mMonthViewCurrentMonth = 11;
			mMonthViewCurrentYear--;
		}
		calStartDate.set(Calendar.DAY_OF_MONTH, 1); // 设置日为当月1日
		calStartDate.set(Calendar.MONTH, mMonthViewCurrentMonth); // 设置月
		calStartDate.set(Calendar.YEAR, mMonthViewCurrentYear); // 设置年
		
		
		
	}
	
	private void setPrevViewItem1() {
		if(str2.equals("一月")){
			year--;
		}
		
		
	
		
	}
	/**
	 * 下一个月
	 */
	private void setNextViewItem() {
		mMonthViewCurrentMonth++;
		if (mMonthViewCurrentMonth == 12) {
			mMonthViewCurrentMonth = 0;
			mMonthViewCurrentYear++;
		}
		calStartDate.set(Calendar.DAY_OF_MONTH, 1);
		calStartDate.set(Calendar.MONTH, mMonthViewCurrentMonth);
		calStartDate.set(Calendar.YEAR, mMonthViewCurrentYear);
		
	}
	private void setNextViewItem1() {
		if(str2.equals("十二月")){
			year++;
		}
		
	}
	/**
	 * 根据改变的日期更新日历 填充日历控件用
	 */
	private void updateStartDateForMonth() {
		calStartDate.set(Calendar.DATE, 1); // 设置成当月第一天
		mMonthViewCurrentMonth = calStartDate.get(Calendar.MONTH);// 得到当前日历显示的月
		mMonthViewCurrentYear = calStartDate.get(Calendar.YEAR);// 得到当前日历显示的年
		year = calStartDate.get(Calendar.YEAR);// 得到当前日历显示的年
		
		String s = calStartDate.get(Calendar.YEAR)
				+ "年"
				+ NumberHelper.LeftPad_Tow_Zero(calStartDate
						.get(Calendar.MONTH) + 1) + "月";
		mDayMessage.setText(s);
		String str = cyclical();
		String str1 = animalsYear();
		Calendar cal = Calendar.getInstance() ;
		CalendarUtil c = new CalendarUtil(cal);
		String str2 = c.getmon();
		mDayMessage1.setText(str+str1+"年"+str2);
		// 星期一是2 星期天是1 填充剩余天数
		int iDay = 0;
		int iFirstDayOfWeek = Calendar.MONDAY;
		int iStartDay = iFirstDayOfWeek;
		if (iStartDay == Calendar.MONDAY) {
			iDay = calStartDate.get(Calendar.DAY_OF_WEEK) - Calendar.MONDAY;
			if (iDay < 0)
				iDay = 6;
		}
		if (iStartDay == Calendar.SUNDAY) {
			iDay = calStartDate.get(Calendar.DAY_OF_WEEK) - Calendar.SUNDAY;
			if (iDay < 0)
				iDay = 6;
		}
		calStartDate.add(Calendar.DAY_OF_WEEK, -iDay);

	}

	/**
	 * 用于获取当前显示月份的时间
	 * 
	 * @return 当前显示月份的时间
	 */
	private Calendar getCalendarStartDate() {
		calToday.setTimeInMillis(System.currentTimeMillis());
		calToday.setFirstDayOfWeek(iFirstDayOfWeek);
		Date date = calToday.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String str = sdf.format(date);
		System.out.println(str + "calToday");

		if (calSelected.getTimeInMillis() == 0) {
			calStartDate.setTimeInMillis(System.currentTimeMillis());
			calStartDate.setFirstDayOfWeek(iFirstDayOfWeek);
		} else {
			calStartDate.setTimeInMillis(calSelected.getTimeInMillis());
			calStartDate.setFirstDayOfWeek(iFirstDayOfWeek);
		}

		return calStartDate;
	}

	AnimationListener animationListener = new AnimationListener() {
		@Override
		public void onAnimationStart(Animation animation) {
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
		}

		@Override
		public void onAnimationEnd(Animation animation) {
			// 当动画完成后调用
			CreateGirdView();
		}
	};

	class GestureListener extends SimpleOnGestureListener {
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			try {
				if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
					return false;
				// right to left swipe
				if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
						&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
					viewFlipper.setInAnimation(slideLeftIn);
					viewFlipper.setOutAnimation(slideLeftOut);
					viewFlipper.showNext();
					setNextViewItem();
					setNextViewItem1();
					return true;

				} else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
						&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
					viewFlipper.setInAnimation(slideRightIn);
					viewFlipper.setOutAnimation(slideRightOut);
					viewFlipper.showPrevious();
					setPrevViewItem();
					setPrevViewItem1();
					return true;

				}
			} catch (Exception e) {
				// nothing
			}
			return false;
		}

		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			// 得到当前选中的是第几个单元格
			int pos = currentGridView.pointToPosition((int) e.getX(),
					(int) e.getY());
			LinearLayout txtDay = (LinearLayout) currentGridView
					.findViewById(pos + 5000);
			if (txtDay != null) {
				if (txtDay.getTag() != null) {
					Date date = (Date) txtDay.getTag();
					calSelected.setTime(date);

					// 通过当天日期与点击的日期 来   判断是否显示 “今天” 按钮

					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//					得到当天的日期
					String today = sdf.format(calToday.getTime());
//					得到点击的日期
					selectedDay = sdf.format(calSelected.getTime());
					if (selectedDay.equals(today)) {
						mTodayBtn.setVisibility(View.GONE);
					} else {
						mTodayBtn.setVisibility(View.GONE);
					}
					System.out.println(calSelected.getTime() + "  calSelected");
					String s = calStartDate.get(Calendar.YEAR)
							+ "年"
							+ NumberHelper.LeftPad_Tow_Zero(calStartDate
									.get(Calendar.MONTH) + 1) + "月";
					mDayMessage.setText(s);
					String str = cyclical();
					String str1 = animalsYear();
					Calendar cal = Calendar.getInstance();
					CalendarUtil c = new CalendarUtil(cal);
					String str2 = c.getmon();  
					mDayMessage1.setText(str+str1+"年"+str2);
					currentGridAdapter.setSelectedDate(calSelected);
					currentGridAdapter.notifyDataSetChanged();
					firstGridAdapter.setSelectedDate(calSelected);
					firstGridAdapter.notifyDataSetChanged();

					lastGridAdapter.setSelectedDate(calSelected);
					lastGridAdapter.notifyDataSetChanged();
					
					
			        
//					通过日期跳转
					Intent intent = new Intent(CalendarView.this,
							Celenderdetail.class);
					intent.putExtra("day", selectedDay);
					CalendarViewday = selectedDay;
					startActivity(intent);
					Log.i("log", "日期："+selectedDay);
					
				}
			}

			Log.i("TEST", "onSingleTapUp -  pos=" + pos);

			return false;
		}
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			new AlertDialog.Builder(this)
					.setTitle("温馨提示")
					.setMessage("确认要退出吗？")
					.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
								}
							})
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									AppManager.getAppManager().finishAllActivity();
								}
							}).show();
		}

		return super.onKeyDown(keyCode, event);
	}
	// ====== 传回农历 y年的生肖
    public String animalsYear() {
        String[] Animals = new String[]{"鼠", "牛", "虎", "兔", "龙", "蛇",
                "马", "羊", "猴", "鸡", "狗", "猪"};
        return Animals[(year - 4) % 12];
    }

    // ====== 传入 月日的offset 传回干支, 0=甲子
    public static String cyclicalm(int num) {
        String[] Gan = new String[]{"甲", "乙", "丙", "丁", "戊", "己", "庚",
                "辛", "壬", "癸"};
        String[] Zhi = new String[]{"子", "丑", "寅", "卯", "辰", "巳", "午",
                "未", "申", "酉", "戌", "亥"};

        return (Gan[num % 10] + Zhi[num % 12]);
    }

    // ====== 传入 offset 传回干支, 0=甲子
    public String cyclical() {
        int num = year - 1900 + 36;
        return (cyclicalm(num));
    }
    @Override
    protected void onDestroy() {
    	// TODO Auto-generated method stub
    	c.close();
    	super.onDestroy();
    }
}
