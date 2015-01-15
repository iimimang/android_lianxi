package com.example.calendargridview;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class CalendarActivity extends Activity implements OnItemClickListener, OnClickListener{
    public static final int SNAP_VELOCITY = 200;
	private int year,month,day;
	private List<OrderEntity> list = new ArrayList<OrderEntity>();
	private OrderDetailAdapter adapter;
	private Context context = this;
	private GridView order_girdview;
	private int lastposition;
	private ImageView bt_next;
	private ImageView bt_last;
	private TextView order_title;
	private TextView tv_content;
	private float xDown;
	private float xUp;
	private VelocityTracker mVelocityTracker;
	private int screenWidth;
	private TextView title;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_detail);
		initView();
		setListener();
		initTime();
		order_title.setText(year+"年"+month+"月");
		tv_content.setText("此图为您"+month+"月全部的订单详情");
		initList(year, month, day,1);
		adapter = new OrderDetailAdapter(context , list);
		order_girdview.setAdapter(adapter);
	}
	
	/**
	 * 获取当前日期
	 * @author XVE
	 */
	@SuppressLint("SimpleDateFormat")
	private void initTime() {
		// TODO Auto-generated method stub
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");       
		Date curDate = new Date(System.currentTimeMillis());//获取当前时间       
		String str = formatter.format(curDate); 
		String s1[] = str.split("-");
		year = Integer.parseInt(s1[0]);
		month = Integer.parseInt(s1[1]);
		day = Integer.parseInt(s1[2]);
	}

	private void setListener() {
		// TODO Auto-generated method stub
		order_girdview.setOnItemClickListener(this);
		bt_last.setOnClickListener(this);
		bt_next.setOnClickListener(this);
	}

	private void initList(int year, int month, int day,int init) {
		// TODO Auto-generated method stub
		list.clear();
		int days = getDays(year, month);
		int lastdays;
		ChinaDate china = new ChinaDate();
		if(month == 1) {lastdays = getDays(year-1, 12);}
		else{lastdays = getDays(year, month-1);}
		int first = dayForWeek(year+"-"+month+"-01");
		if(first!=7)
		{
			for(int i = lastdays-first+1;i<=lastdays;i++){
				OrderEntity entity = new OrderEntity();
				entity.setDate(i+"");
				entity.setBgcolor(0);
				entity.setContentnum(0);
				entity.setChina(china.oneDay(year, month-1, i));
				list.add(entity);
			}
		}
		
		for(int i = 1;i<=days;i++){
			OrderEntity entity = new OrderEntity();
			entity.setDate(i+"");
			entity.setBgcolor(1);
			entity.setContentnum(i%4);
			String[] str = {"熱門活動1","熱門活動2","熱門活動3"};
			entity.setContent(str);
			entity.setChina(china.oneDay(year, month, i));
			if(init == 1&&i == day) {entity.setBgcolor(2);
			lastposition = list.size();
			}
			list.add(entity);
		}
		if((first+days)%7!=0){
			for(int i = 1;i<=7-(first+days)%7;i++){
				OrderEntity entity = new OrderEntity();
				entity.setDate(i+"");
				entity.setContentnum(0);
				entity.setBgcolor(0);
				entity.setChina(china.oneDay(year, month+1, i));
				list.add(entity);
			}
		}
//		tv_date.setText(year+"年"+month+"月"+day+"日:");
	}

	private void initView() {
		// TODO Auto-generated method stub
		order_girdview = (GridView) findViewById(R.id.order_gridview);
		tv_content = (TextView) findViewById(R.id.tv_order);
		order_title = (TextView) findViewById(R.id.order_title);
		bt_next = (ImageView) findViewById(R.id.order_next);
		bt_last = (ImageView) findViewById(R.id.order_last);
		title = (TextView) findViewById(R.id.title_Text);
		title.setText("自定义日历GridView");
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		screenWidth = dm.widthPixels;
	} 


	/**
	 * 判断当前年月是有多少天
	 * @param year month
	 * @return days 天数
	 * @author XVE
	 */
	private static int getDays(int year, int month) {
		int days = 0;
		boolean isLeapYear = false;
		if ((year % 4 == 0) && ((year % 100 != 0) || (year % 400 == 0))) {
			isLeapYear = true;
		} else {
			isLeapYear = false;
		}
		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			days = 31;
			break;
		case 2:
			if (isLeapYear) {
				days = 29;
			} else {
				days = 28;
			}
			break;
		case 4:
		case 6:
		case 9:
		case 11:
			days = 30;
			break;
		}
		return days;
	}

	/**
	 * 判断当前日期是星期几
	 * @param pTime 要判断的时间
	 * @return dayForWeek 判断结果
	 * @author XVE
	 */
	@SuppressLint("SimpleDateFormat")
	private static int dayForWeek(String pTime) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(format.parse(pTime));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int dayForWeek = 0;
		if (c.get(Calendar.DAY_OF_WEEK) == 1) {
			dayForWeek = 7;
		} else {
			dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		}
		return dayForWeek;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		// TODO Auto-generated method stub
		day = Integer.parseInt(list.get(position).getDate());
		if(position<6&&day>position) return;
		if(day<7&&position>13) return;
		list.get(lastposition).setBgcolor(1);
		list.get(position).setBgcolor(2);
		String str = day+"日\n农历"+list.get(position).getChina();
		String[] s = list.get(position).getContent();
		switch (list.get(position).getContentnum()) {
		case 0:
			break;
		case 1:
			break;
		case 2:
			str +="\n"+s[1];
			break;
		case 3:
			str +="\n"+s[1]+"\n"+s[2];
			break;
		}
		showToast(year+"年"+month+"月"+str);
		order_girdview.setSelection(lastposition);
		order_girdview.setSelection(position);
		adapter.notifyDataSetChanged();
		lastposition = position;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.order_last:
				LastMonth();
			break;
		case R.id.order_next:
				NextMonth();
			break;
		}
	}

	public void NextMonth() {
		// TODO Auto-generated method stub
		if(month == 12){year+=1;month = 1;}
		else month = month + 1;
		initList(year, month, day,0);
		adapter.notifyDataSetChanged();
		order_title.setText(year+"年"+month+"月");
		tv_content.setText("此图为您"+month+"月全部的订单详情");
	}

	public void LastMonth() {
		// TODO Auto-generated method stub
		if(month == 1){year-=1;month = 12;}
		else month = month - 1;
		initList(year, month, day,0);
		adapter.notifyDataSetChanged();
		order_title.setText(year+"年"+month+"月");
		tv_content.setText("此图为您"+month+"月全部的订单详情");
	}

	
	public boolean DoIt() {
		// TODO Auto-generated method stub
		return xUp - xDown > screenWidth/ 3 || getScrollVelocity() > SNAP_VELOCITY;
	}

	/**
	 * 创建VelocityTracker对象，并将触摸事件加入到VelocityTracker当中。
	 * 
	 * @author XVE
	 */
	public void createVelocityTracker(MotionEvent event) {
		if (mVelocityTracker == null) {
			mVelocityTracker = VelocityTracker.obtain();
		}
		mVelocityTracker.addMovement(event);
	}

	private int getScrollVelocity() {
		mVelocityTracker.computeCurrentVelocity(200);
		int velocity = (int) mVelocityTracker.getXVelocity();
		return Math.abs(velocity);
	}
	public void recycleVelocityTracker() {
		mVelocityTracker.recycle();
		mVelocityTracker = null;
	}
	
	public static boolean isEmpty(String value) {
		boolean result = false;
		if (value == null || value.trim().length() == 0
				|| value.equalsIgnoreCase("null")) {
			result = true;
		}
		return result;
	}

	public void showToast(String message) {
		if (CalendarActivity.this != null && !isEmpty(message))
			Toast.makeText(CalendarActivity.this.getApplicationContext(), message,
					Toast.LENGTH_SHORT).show();
	}
}
