package com.notbook.calendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.example.notbook.R;
import com.notbook.things.Meta;

import android.R.integer;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class CalendarGridViewAdapter extends BaseAdapter {

    private Calendar calStartDate = Calendar.getInstance();// 当前显示的日历
    private Calendar calSelected = Calendar.getInstance(); // 选择的日历
    static ContentResolver resolver;
    public void setSelectedDate(Calendar cal) {
        calSelected = cal;
    }

    private Calendar calToday = Calendar.getInstance(); // 今日
    private int iMonthViewCurrentMonth = 0; // 当前视图月

    public static String CalendarGridViewAdapter_1 = "";
    // 根据改变的日期更新日历
    // 填充日历控件用
    private void UpdateStartDateForMonth() {
        calStartDate.set(Calendar.DATE, 1); // 设置成当月第一天
        iMonthViewCurrentMonth = calStartDate.get(Calendar.MONTH);// 得到当前日历显示的月

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

        calStartDate.add(Calendar.DAY_OF_MONTH, -1);// 周日第一位

    }

    ArrayList<java.util.Date> titles;

    private ArrayList<java.util.Date> getDates() {

        UpdateStartDateForMonth();

        ArrayList<java.util.Date> alArrayList = new ArrayList<java.util.Date>();

        for (int i = 1; i <= 42; i++) {
            alArrayList.add(calStartDate.getTime());
            calStartDate.add(Calendar.DAY_OF_MONTH, 1);
        }

        return alArrayList;
    }

    private Activity activity;
    Resources resources;

    // construct
    public CalendarGridViewAdapter(Activity a, Calendar cal) {
        calStartDate = cal;
        activity = a;
        resources = activity.getResources();
        titles = getDates();
    }

    public CalendarGridViewAdapter(Activity a) {
        activity = a;
        resources = activity.getResources();
    }


    @Override
    public int getCount() {
        return titles.size();
    }

    @Override
    public Object getItem(int position) {
        return titles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout iv = new LinearLayout(activity);
        iv.setId(position + 5000);
        LinearLayout imageLayout = new LinearLayout(activity);
        imageLayout.setOrientation(0);
        iv.setGravity(Gravity.CENTER);
        iv.setOrientation(1);
//        iv.setLayoutParams(new GridView.LayoutParams(LayoutParams.FILL_PARENT,300/6));
        Date myDate = (Date) getItem(position);
        Calendar calCalendar = Calendar.getInstance();
        calCalendar.setTime(myDate);
        WindowManager windowManager = ((Activity) activity).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        int i0 = display.getHeight();
        int j0 = display.getHeight();
       
        final int iMonth = calCalendar.get(Calendar.MONTH);
        final int iDay = calCalendar.get(Calendar.DAY_OF_WEEK);


        // 判断周六周日
        if (iDay == 7) {
            // 周六
//            iv.setBackgroundColor(resources.getColor(R.color.text_6));
        } else if (iDay == 1) {
            // 周日
//            iv.setBackgroundColor(resources.getColor(R.color.text_7));
            
        } else {

        }
        // 判断周六周日结束

        TextView txtToDay = new TextView(activity);// 老黄历
        txtToDay.setGravity(Gravity.CENTER_HORIZONTAL);
        txtToDay.setTextSize(10);
        CalendarUtil calendarUtil = new CalendarUtil(calCalendar);
        if (equalsDate(calToday.getTime(), myDate)) {
            // 当前日期
        	iv.setBackgroundResource(R.drawable.cal_2);//加图片
            txtToDay.setText(calendarUtil.toString());
        } else {
            txtToDay.setText(calendarUtil.toString());
        }
        CalendarGridViewAdapter_1 =  calendarUtil.toString();
        // 设置背景颜色
        if (equalsDate(calSelected.getTime(), myDate)) {
            // 选择的
//            iv.setBackgroundResource(R.drawable.cal_2);//加图片
//            iv.setBackgroundColor(resources.getColor(R.color.calendar_background));
        } else {
            if (equalsDate(calToday.getTime(), myDate)) {
                // 当前日期
            	Paint paint=new Paint();
            	Canvas canvas = new Canvas();
            	paint.setColor(resources.getColor(R.color.cyan));
            	paint.setStyle(Paint.Style.STROKE);
            	canvas.drawCircle(30, 30, 20, paint);
//                iv.setBackgroundColor(resources.getColor(R.color.calendar_zhe_day));
            	
                iv.setBackgroundResource(R.drawable.cal_2);//加图片
            }
        }
        // 设置背景颜色结束

        // 日期开始
        TextView txtDay = new TextView(activity);// 日期
        txtDay.setGravity(Gravity.CENTER_HORIZONTAL);
        txtDay.setTextSize(20);
        // 判断是否是当前月
        if (iMonth == iMonthViewCurrentMonth) {
            txtToDay.setTextColor(resources.getColor(R.color.ToDayText));
            txtDay.setTextColor(resources.getColor(R.color.Text));
        } else {
            txtDay.setTextColor(resources.getColor(R.color.noMonth));
            txtToDay.setTextColor(resources.getColor(R.color.noMonth));
        }

        int day = myDate.getDate(); // 日期
        txtDay.setText(String.valueOf(day));
        txtDay.setId(position + 500);
        iv.setTag(myDate);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                50, i0/25);
        iv.addView(txtDay, lp);

        LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(
                50, i0/25);
        iv.addView(txtToDay, lp1);
        /*Cursor c = resolver.query(com.notbook.photodb.Meta.CONTENT_URI, null, null, null, null);
        Log.i("log", "-----------图片："+c);*/
        List ss = CalendarView.ss;
//        Log.i("log", "-----------图片111111111111："+ss);
        for(int i=0 ;i<ss.size();i++){
        	String name = ss.get(i).toString();  
        	if(!name.equals("")){
        		String year = name.substring(0, 4);
            	String month = name.substring(5, 7);
            	String date = name.substring(8, 10);
//            	String jishinum = name.substring(10, 11);
    			Log.i("log", "-----------图片111111111111："+year+"+++"+month+"+++"+date);
    				if((iMonth+1)==Integer.parseInt(month)){
    					if(day==Integer.parseInt(date)){
//    						iv.setBackgroundResource(R.drawable.touming);
    						iv.setBackgroundResource(R.drawable.cal_3);//会议
//    						if(jishinum.equals("1")){
//    							iv.setBackgroundResource(R.drawable.cal_3);//会议
//    						}
//    						if(jishinum.equals("2")){
//    							iv.setBackgroundResource(R.drawable.cal_3);//记事
//    						}
//    						if(jishinum.equals("3")){
//    							iv.setBackgroundResource(R.drawable.cal_3);//生日
//    						}
//    						if(jishinum.equals("4")){
//    							iv.setBackgroundResource(R.drawable.cal_3);//聚会
//    						}
    						if (equalsDate(calToday.getTime(), myDate)) {
    			                // 当前日期
    			            	Paint paint=new Paint();
    			            	Canvas canvas = new Canvas();
    			            	paint.setColor(resources.getColor(R.color.cyan));
    			            	paint.setStyle(Paint.Style.STROKE);
    			            	canvas.drawCircle(30, 30, 20, paint);
//    			                iv.setBackgroundColor(resources.getColor(R.color.calendar_zhe_day));
    			                iv.setBackgroundResource(R.drawable.cal_1);//加图片
    			            }
    					}
    					
    				}
        	}
        	
//			Log.i("log", "-----------图片111111111111："+name);
        }
//        if(nian ){
        // 判断是否是当前月
        /*if (iMonth == 5) {
        	if(day == 5)
        	{
        		iv.setBackgroundResource(R.drawable.a1);
        	}
        }*/
//        }
        return iv;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    private Boolean equalsDate(Date date1, Date date2) {

        if (date1.getYear() == date2.getYear()
                && date1.getMonth() == date2.getMonth()
                && date1.getDate() == date2.getDate()) {
            return true;
        } else {
            return false;
        }

    }

}
