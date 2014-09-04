package com.notbook.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import android.widget.LinearLayout.LayoutParams;

import com.example.notbook.R;
import com.notbook.app.AppManager;
import com.notbook.calendar.CalendarUtil;
import com.notbook.calendar.CalendarView;
import com.notbook.calendar.GregorianUtil;
import com.notbook.calendar.Lunar;
import com.notbook.calendar.NumberHelper;
import com.notbook.calendar.SolarTermsUtil;
import com.notbook.calendar.StringUtil;
import com.notbook.things.Meta;

public class Celenderdetail extends BaseActivity implements OnTouchListener{

	private Button iamgebutton1,iamgebutton2,iamgebutton3,iamgebutton4,iamgebutton5,eventadd,celender;
	private Button more_back;
	static ContentResolver resolver ;
	private ListView findshare;
	private MyAdapter myadapter = null;
	private Cursor c = null;
	public static String leixing = "",time = "",mtime = "",zhuti = "",jishi = "",youxian = "",lingyin = "",imageurl = "",luyin = "",id="",datetext="";
	List<Map<String,String>> data = new ArrayList<Map<String,String>>();
	public static String Eventlistid = "";
	private TextView time_text;
	private TextView celenderday;
	private TextView celendermonth;
	private TextView celenderweek;
	private TextView celenderjiyue;//纪月记日
	final String[] Gan = new String[]{"甲", "乙", "丙", "丁", "戊", "己", "庚","辛", "壬", "癸"};
    final String[] Zhi = new String[]{"子", "丑", "寅", "卯", "辰", "巳", "午","未", "申", "酉", "戌", "亥"};
    private static String[] sectionalTermNames =
        {"小寒", "立春", "惊蛰", "清明", "立夏", "芒种", "小暑", "立秋", "白露", "寒露",
                "立冬", "大雪"};
    private static String[] tgYears=new String[]{"癸","甲", "乙", "丙", "丁", "戊", "己", "庚",
        "辛", "壬"};
    private static String[] dzYears=new String[]{ "亥","子", "丑", "寅", "卯", "辰", "巳", "午",
        "未", "申", "酉", "戌"};
    private List jsList=new ArrayList();
    private int month = 0;
    private static String m_time= "";
    private static String year_1 = "";
    private static String month_1 = "";
    private static String day_1 = "";
    private TextView yiritext ;
    private TextView jiritext ;
	private SharedPreferences sp;
	private Editor editor;
	private static Boolean flag = false ;
	public static String day = "";
	private RelativeLayout RelativeLayoutiamge ;
	private GestureDetector mGestureDetector;
	private static final int CAL_LAYOUT_ID = 55;
	// 判断手势用
	private static final int SWIPE_MIN_DISTANCE = 120;

	private static final int SWIPE_MAX_OFF_PATH = 250;

	private static final int SWIPE_THRESHOLD_VELOCITY = 200;
	
	private Animation slideLeftIn;
	private Animation slideLeftOut;
	private Animation slideRightIn;
	private Animation slideRightOut;
	private ViewFlipper viewFlipper;
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.celenderdetail);
		findViewById(R.id.one).setBackgroundResource(background_photobg[num - 1]);
		findViewById(R.id.one1).setBackgroundResource(background_photobg1[num - 1]);
		findViewById(R.id.one2).setBackgroundResource(background_photobg1[num - 1]);
		eventadd = (Button) findViewById(R.id.eventadd);
		celender = (Button) findViewById(R.id.celender);
		more_back = (Button) findViewById(R.id.more_back);
		time_text = (TextView) findViewById(R.id.time_text);
		celenderday = (TextView) findViewById(R.id.celenderday);
		celendermonth = (TextView) findViewById(R.id.celendermonth);
		celenderweek = (TextView) findViewById(R.id.celenderweek);
		yiritext = (TextView) findViewById(R.id.yiritext);
		jiritext = (TextView) findViewById(R.id.jiritext);
		celenderjiyue = (TextView) findViewById(R.id.celenderjiyue);
		RelativeLayoutiamge = (RelativeLayout) findViewById(R.id.RelativeLayoutiamge);
		
		
		mGestureDetector = new GestureDetector(this, new GestureListener());
		slideLeftIn = AnimationUtils.loadAnimation(this, R.anim.slide_left_in);
		slideLeftOut = AnimationUtils
				.loadAnimation(this, R.anim.slide_left_out);
		slideRightIn = AnimationUtils
				.loadAnimation(this, R.anim.slide_right_in);
		slideRightOut = AnimationUtils.loadAnimation(this,
				R.anim.slide_right_out);
		generateContetView(RelativeLayoutiamge);
		slideLeftIn.setAnimationListener(animationListener);
		slideLeftOut.setAnimationListener(animationListener);
		slideRightIn.setAnimationListener(animationListener);
		slideRightOut.setAnimationListener(animationListener);
		sp = getSharedPreferences("config_two", Context.MODE_PRIVATE);
		editor = sp.edit();
		time_text.setText(sp.getString("time1", ""));
		celenderday.setText(sp.getString("time2", ""));
		celendermonth.setText(sp.getString("time3", ""));
		celenderweek.setText(sp.getString("time4", ""));
		celenderjiyue.setText(sp.getString("time5", ""));
		//返回
		more_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Celenderdetail.this, MainActivity0.class);
				startActivity(intent);
				finish();
			}
		});		
		Map map1=new HashMap();
		map1.put("hao", "赴任、祈福、求嗣、破土、安葬");
		map1.put("huai", "动土、开仓、掘井、乘船、新船下水");
		
		Map map2=new HashMap();
		map2.put("hao", "祭祀、祈福、婚姻、出行、入伙");
		map2.put("huai", "结婚、赴任、远行、签约");
		
		Map map3=new HashMap();
		map3.put("hao", "嫁娶、祈福、移徙、开市、交易");
		map3.put("huai", "造葬、赴任、求医");
		
		Map map4=new HashMap();
		map4.put("hao", "嫁娶、修造、破土、安葬、牧养");
		map4.put("huai", "祈福、求嗣、赴任、嫁娶、开市");
		
		Map map5=new HashMap();
		map5.put("hao", "祭祀、祈福、嫁娶、造屋、装修");
		map5.put("huai", "诉讼、出行、交涉");
		
		Map map6=new HashMap();
		map6.put("hao", "造屋、装修、嫁娶、收购、立契");
		map6.put("huai", "开市、求財、出行、搬迁");
		
		Map map7=new HashMap();
		map7.put("hao", "破土、拆卸、求医");
		map7.put("huai", "嫁娶、签约、交涉、出行、搬迁");
		
		Map map8=new HashMap();
		map8.put("hao", "祭祀、祈福、安床、拆卸、破土");
		map8.put("huai", "登山、乘船、出行、嫁娶、造葬");
		
		Map map9=new HashMap();
		map9.put("hao", "结婚、开市、修造、动土、安床");
		map9.put("huai", "诉讼");
		
		Map map10=new HashMap();
		map10.put("hao", "祈福、求嗣、赴任、嫁娶、安床");
		map10.put("huai", "放债、新船下水、新车下地、破土、安葬");
		
		Map map11=new HashMap();  
		map11.put("hao", "祭祀、祈福、入学、上任、修造");
		map11.put("huai", "放债、诉讼、安葬");
		
		Map map12=new HashMap();
		map12.put("hao", "祭祀、祈福、筑堤、埋池、埋穴");
		map12.put("huai", "开市、出行、求医、手术、嫁娶");
		jsList.add(map1);
		jsList.add(map2);
		jsList.add(map3);
		jsList.add(map4);
		jsList.add(map5);
		jsList.add(map6);
		jsList.add(map7);
		jsList.add(map8);
		jsList.add(map9);
		jsList.add(map10);
		jsList.add(map11);
		jsList.add(map12);
		
		Bundle  b = getIntent().getExtras();
		/*Bundle  b1 = getIntent().getExtras();
		if(null!=b1 && !b1.equals("")){
			time_text.setText(b.getString("time_text").toString());
			celenderday.setText(b.getString("celenderday").toString());
			celendermonth.setText(b.getString("celendermonth").toString());
			celenderweek.setText(b.getString("celenderweek").toString());
			celenderjiyue.setText(b.getString("celenderjiyue").toString());
		}*/
		if(null!=b && !b.equals("")){
			
				day = b.getString("day");
				String str1[] = day.split("\\-");
				
				for(int i=0;i<str1.length;i++){
					year_1 = str1[0];
					month_1 = str1[1];
					day_1 = str1[2];
					m_time = str1[0]+"年"+str1[1]+"月"+str1[2]+"日";
				}
				time_text.setText(m_time);
				
				String str = cyclical();
				String str_year = animalsYear();
				Calendar cal = Calendar.getInstance();
				
				try {
					cal.setTime(Lunar.chineseDateFormat.parse(year_1+"年"+month_1+"月"+day_1+"日"));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				Lunar lunar = new Lunar(cal);
				String str2 = lunar.toString1();  
				String day_new = lunar.toString2(); 
				celenderday.setText(day_new);
				celendermonth.setText(str+str_year+"年"+str2);
				celenderweek.setText(CalendarUtil.getWeek(cal));//周几
				
				
				 String message = "";
				 String solarMsg = new SolarTermsUtil(cal).getSolartermsMsg();
		            //判断当前日期是否为节气
		            if (!StringUtil.isNullOrEmpty(solarMsg)) {
		                message = solarMsg;
		            } else {
		            	
		                /**
		                 * 判断当前日期是否为公历节日
		                 */
		                String gremessage = new GregorianUtil(cal).getGremessage();
		                if (!StringUtil.isNullOrEmpty(gremessage)) {
		                    message = gremessage;
		                } else if (Integer.valueOf(day_1) == 1) {
		                    message = CalendarUtil.CHINESE_NUMBER[Integer.valueOf(day_1) - 1] + "月";
		                } else {
		                    message = CalendarUtil.getChinaDayString(Integer.valueOf(day_1))+day_new;
		                }

		            }
				SolarTermsUtil so = new SolarTermsUtil(Integer.valueOf(year_1),Integer.valueOf(month_1),Integer.valueOf(day_1));
				if(str.subSequence(0, 1).equals("")){
					
				}
				if(message.equals(sectionalTermNames[1])){
					month = 2;
				}
				
				Log.i("wyq", cal+"===========");
				Log.i("wyq", new SolarTermsUtil(cal).getTGDZMonth()+"===========");
				
				celenderjiyue.setText(getJiyue()+getJiri());
				
//				Editor editor = sp.edit();
//				editor.putString("time1", m_time);
//				editor.putString("time2", day_new);
//				editor.putString("time3", str+str_year+"年"+str2);
//				editor.putString("time4", CalendarUtil.getWeek(cal));
//				editor.putString("time5", getJiyue()+getJiri());
//				editor.commit();
			
		}
		Map tempMap=getji();
		yiritext.setText(tempMap.get("hao").toString());
		jiritext.setText(tempMap.get("huai").toString());
		time_text.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			/*	Intent intent = new Intent(Celenderdetail.this, ShareIndexAty.class);
				startActivity(intent);
				finish();*/
			}
		});
		
		
		resolver = getContentResolver();
		findshare = (ListView) findViewById(R.id.findshare11);
		Eventdetail.id = "";  
		c = resolver.query(Meta.CONTENT_URI, null, "_selecttime = ?", new String[] { day }, null);
		
		if(null != c && c.moveToFirst()){
			for(c.moveToFirst();!c.isAfterLast();c.moveToNext())  {  
				int nameColumn = c.getColumnIndex(com.notbook.things.Meta.TableMeta._LEIXING);  
				leixing = c.getString(nameColumn);  
				int nameColumn1 = c.getColumnIndex(com.notbook.things.Meta.TableMeta._TIME);  
				time = c.getString(nameColumn1);  
				mtime = c.getString(c.getColumnIndex(com.notbook.things.Meta.TableMeta._MTIME));  
				zhuti = c.getString(c.getColumnIndex(com.notbook.things.Meta.TableMeta._ZHUTI));  
				jishi = c.getString(c.getColumnIndex(com.notbook.things.Meta.TableMeta._JISHI)); 
				youxian = c.getString(c.getColumnIndex(com.notbook.things.Meta.TableMeta._YOUXIAN)); 
				lingyin = c.getString(c.getColumnIndex(com.notbook.things.Meta.TableMeta._LINGYIN)); 
				luyin = c.getString(c.getColumnIndex(com.notbook.things.Meta.TableMeta._LUYIN)); 
				id = c.getString(c.getColumnIndex(com.notbook.things.Meta.TableMeta._ID)); 
				
				datetext = c.getString(c.getColumnIndex(com.notbook.things.Meta.TableMeta._DATE)); 
				byte[] picData = c.getBlob(c.getColumnIndex(Meta.TableMeta._IMAGE));
				if(picData!=null){
					imageurl = c.getString(c.getColumnIndex(com.notbook.things.Meta.TableMeta._IMAGE)); 
				}
				
				Log.i("log", "-----------leixing："+leixing);
				Map<String,String> item = new HashMap<String, String>();
				item.put("leixing", leixing);		
				item.put("time", time);		
				item.put("mtime", mtime);	
				item.put("zhuti", zhuti);	
				item.put("jishi", jishi);
				item.put("youxian", youxian);
				item.put("lingyin", lingyin);
				item.put("imageurl", imageurl);
				item.put("luyin", luyin);
				item.put("id", id);
				item.put("datetext", datetext); 
				data.add(item);
				Log.i("log", "-----------data："+data);
			} 
		}
		
		
		
		myadapter = new MyAdapter(this,data);
		
		
		/*SimpleCursorAdapter sca = new SimpleCursorAdapter(this,
				R.layout.share_list1, c, new String[] {
						Meta.TableMeta._LEIXING, Meta.TableMeta._TIME },
				new int[] { R.id.leixing, R.id.time});*/
		findshare.setAdapter(myadapter);
		findshare.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int arg2, long id) {
				
				Intent it = new Intent(Celenderdetail.this,Eventdetail.class);
				it.putExtra("id", data.get(arg2).get("id").toString());
				Eventlistid = (id+1)+"";
				it.putExtra("list", "true");
				it.putExtra("day", day);
				startActivity(it);
				Celenderdetail.this.finish();
			}
		});  
		eventadd.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Celenderdetail.this, Eventadd.class);
				intent.putExtra("list", "true");
				intent.putExtra("day", day);
				startActivity(intent);
				Celenderdetail.this.finish();
			}
		});
		celender.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Celenderdetail.this, MainActivity0.class);
				startActivity(intent);
			}
		});
		init();
		
		
	}
	public void init(){
		iamgebutton1 = (Button) findViewById(R.id.iamgebutton1);
		iamgebutton1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Celenderdetail.this, MainActivity0.class);
				startActivity(intent);
			}
		});
		iamgebutton2 = (Button) findViewById(R.id.iamgebutton2);
		iamgebutton2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Celenderdetail.this, MainActivity2.class);
				startActivity(intent);
			}
		});
		iamgebutton3 = (Button) findViewById(R.id.iamgebutton3);
		iamgebutton3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Celenderdetail.this, MainActivity3.class);
				startActivity(intent);
			}
		});
		iamgebutton4 = (Button) findViewById(R.id.iamgebutton4);
		iamgebutton4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Celenderdetail.this, MainActivity4.class);
				startActivity(intent);
			}
		});
		iamgebutton5 = (Button) findViewById(R.id.iamgebutton5);
		iamgebutton5.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Celenderdetail.this, MainActivity1.class);
				startActivity(intent);
			}
		});
	}
	private class MyAdapter extends BaseAdapter {
		private Context xontext;
		private List list;
		
		
		public MyAdapter(){
			
		}

		public MyAdapter(Context xontext, List list) {
			super();
			this.xontext = xontext;
			this.list = list;    
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return data.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = View.inflate(getApplicationContext(),
						R.layout.share_list1, null);
				holder.time=(TextView)convertView.findViewById(R.id.time);
				holder.leixing =(TextView)convertView.findViewById(R.id.leixing);
				holder.chuanyu03=(Button)convertView.findViewById(R.id.chuanyu03);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			final Map map = (Map)list.get(position);
			holder.leixing.setText(map.get("leixing").toString());
			
			holder.time.setText(map.get("datetext").toString());
			
			
			holder.chuanyu03.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if(Login.NUM==0){
						Intent intent = new Intent(Celenderdetail.this, Login.class);
						startActivity(intent);
						Toast.makeText(Celenderdetail.this, "请您登录", 0).show();
					}else{
						Intent intent = new Intent(Celenderdetail.this, Friend1.class);
						leixing = map.get("leixing").toString();
						time = map.get("time").toString();
						mtime = map.get("mtime").toString();
						zhuti = map.get("zhuti").toString();
						jishi = map.get("jishi").toString();
						youxian = map.get("youxian").toString();
						lingyin = map.get("lingyin").toString();
						imageurl = map.get("imageurl").toString();
						luyin = map.get("luyin").toString();
						Log.i("log", "-------------leixing="+leixing);
						startActivity(intent);
					}
				}
			});
			return convertView;
		}
	}
	static class ViewHolder {
		TextView leixing;
		TextView time;
		Button chuanyu03;
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		c.close();
		super.onDestroy();
	}
	// ====== 传回农历 y年的生肖
    public String animalsYear() {
        String[] Animals = new String[]{"鼠", "牛", "虎", "兔", "龙", "蛇",
                "马", "羊", "猴", "鸡", "狗", "猪"};
        return Animals[(CalendarView.calStartDate.get(Calendar.YEAR) - 4) % 12];
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
        int num = CalendarView.calStartDate.get(Calendar.YEAR) - 1900 + 36;
        return (cyclicalm(num));
    }
    private String getJiyue()
    {
    	
    	Calendar cal = Calendar.getInstance();
    	int tempYear=Integer.parseInt(year_1);
		if(Integer.parseInt(month_1)==2)
			tempYear=tempYear-1;
		try {
			cal.setTime(Lunar.chineseDateFormat.parse(tempYear+"年"+month_1+"月"+day_1+"日"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int month=new SolarTermsUtil(cal).getTGDZMonth()-1;
		int tgmod=(tempYear-3)%10;
		if(tgmod==1||tgmod==6)
		{
			return tgYears[(month+2)%10]+dzYears[(month+2)%12]+"月";
		}
		if(tgmod==2||tgmod==7)
		{
			return tgYears[(month+4)%10]+dzYears[(month+2)%12]+"月";
			
		}
		if(tgmod==3||tgmod==8)
		{
			return tgYears[(month+6)%10]+dzYears[(month+2)%12]+"月";
			
		}
		if(tgmod==4||tgmod==9)
		{
			return tgYears[(month+8)%10]+dzYears[(month+2)%12]+"月";
			
		}
		if(tgmod==0||tgmod==5)
		{
			return tgYears[month%10]+dzYears[(month+2)%12]+"月";
			
		}
    	return "";
    	
    }
   private String getJiri()
   {
	   int yearT=0;
	   Calendar cal = Calendar.getInstance();
   	int tempYear=Integer.parseInt(year_1);
   	yearT=tempYear;
		if(Integer.parseInt(month_1)==2)
			tempYear=tempYear-1;
		try {
			cal.setTime(Lunar.chineseDateFormat.parse(tempYear+"年"+month_1+"月"+day_1+"日"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//int month=new SolarTermsUtil(cal).getTGDZMonth()-1;
		int month=Integer.parseInt(month_1);
		if(month==1)
		{
			yearT=yearT-1;
			month=13;
		}
		if(month==2)
		{
			yearT=yearT-1;
			month=14;
		}
		int day=Integer.parseInt(day_1);
		int jiriT=4*20+20/4+5*(yearT-2000)+(yearT-2000)/4+(3*(month+1)/5)+day-3;
	    int jiriD=0;
	        if(month%2==0)
	    {
	        jiriD=8*20+20/4+5*(yearT-2000)+(yearT-2000)/4+(3*(month+1)/5)+day+7+6;
	    }else
	    	Log.i("log", jiriT%10+"||||||||||");
	        Log.i("log", jiriD%12+"||||||||||");
	        jiriD=8*20+20/4+5*(yearT-2000)+(yearT-2000)/4+(3*(month+1)/5)+day+7;
	        return tgYears[jiriT%10]+dzYears[jiriD%12]+"日";
   }
	 private Map getji() {
		   
		   Calendar cal = Calendar.getInstance();   
	
				try {
					cal.setTime(Lunar.chineseDateFormat.parse(year_1+"年"+month_1+"月"+day_1+"日"));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				int month=new SolarTermsUtil(cal).getTGDZMonth();
				int jieqiday=new SolarTermsUtil(cal).getJieQiDay();
				Log.i("log", jieqiday+"====jieqiday________________-");
				Log.i("log", month+"====month_________________");
				Log.i("log", day_1+"======day_1_________________");
				int i = 0;
				if(Integer.valueOf(day_1)>=jieqiday){
					i = (Integer.valueOf(day_1) -  jieqiday+1)%12;
				}else{
					int tempMonth=0;
					int tempYear=0;
					if(Integer.valueOf(month_1)==1){
						tempMonth=12;
						tempYear=Integer.valueOf(year_1)-1;
					}else
					{
						tempMonth=Integer.valueOf(month_1)-1;
						tempYear=Integer.valueOf(year_1);
					}
						
					 int monthdays=SolarTermsUtil.daysInGregorianMonth(Integer.valueOf(tempYear), Integer.valueOf(tempMonth));
					 i=(monthdays-jieqiday+Integer.parseInt(day_1))%12;
				}
				Log.i("log", i+"i_________________");
				return (Map)jsList.get(i);
	   }
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
						Log.i("log", "===============left");
						return true;

					} else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
							&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
						viewFlipper.setInAnimation(slideRightIn);
						viewFlipper.setOutAnimation(slideRightOut);
						viewFlipper.showPrevious();
						setPrevViewItem();
						Log.i("log", "===============right");
						return true;

					}
				} catch (Exception e) {
					// nothing
				}
				return false;
			}

		}
//	@Override
//	public boolean onTouch(View v, MotionEvent event) {
//		return mGestureDetector.onTouchEvent(event);
//	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		//return super.onTouchEvent(event);
		//Log.i("log", "===================");
		return mGestureDetector.onTouchEvent(event);
	}
	/**
	 * 下一个月
	 */
	private void setNextViewItem() {
		//Log.i("log", "i_________________222222222222222222222222222");
		Intent intent = new Intent(Celenderdetail.this,
				Celenderdetail.class);
		
		
		Bundle  b = getIntent().getExtras();
		if(null!=b && !b.equals("")){
			
				day = b.getString("day");
				String str1[] = day.split("\\-");
				
				for(int i=0;i<str1.length;i++){
					year_1 = str1[0];
					month_1 = str1[1];
					day_1 = str1[2];
					m_time = str1[0]+"年"+str1[1]+"月"+str1[2]+"日";
				}
				time_text.setText(m_time);
				
				String str = cyclical();
				String str_year = animalsYear();
				Calendar cal = Calendar.getInstance();
				try {
					cal.setTime(Lunar.chineseDateFormat.parse(year_1+"年"+month_1+"月"+day_1+"日"));
					cal.add(Calendar.DAY_OF_MONTH, 1); 
					SimpleDateFormat format = new   SimpleDateFormat("yyyy-MM-dd");  
					String tempStr= format.format(cal.getTime() ) ;
					Log.i("log", "=========time========"+tempStr);
					intent.putExtra("day", tempStr);
					CalendarView.selectedDay = tempStr;
					startActivity(intent);
					Celenderdetail.this.finish();
					//cal.getTime();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
				
		
	}
	/**
	 * 上一个月
	 */
	private void setPrevViewItem() {
		Log.i("log", "i_________________1111111111111111111111111111");
		
		Intent intent = new Intent(Celenderdetail.this,
				Celenderdetail.class);
		
		
		Bundle  b = getIntent().getExtras();
		if(null!=b && !b.equals("")){
			
				day = b.getString("day");
				String str1[] = day.split("\\-");
				
				for(int i=0;i<str1.length;i++){
					year_1 = str1[0];
					month_1 = str1[1];
					day_1 = str1[2];
					m_time = str1[0]+"年"+str1[1]+"月"+str1[2]+"日";
				}
				time_text.setText(m_time);
				
				String str = cyclical();
				String str_year = animalsYear();
				Calendar cal = Calendar.getInstance();
				try {
					cal.setTime(Lunar.chineseDateFormat.parse(year_1+"年"+month_1+"月"+day_1+"日"));
					cal.add(Calendar.DAY_OF_MONTH, -1); 
					SimpleDateFormat format = new   SimpleDateFormat("yyyy-MM-dd");  
					String tempStr= format.format(cal.getTime() ) ;
					Log.i("log", "=========time========"+tempStr);
					intent.putExtra("day", tempStr);
					CalendarView.selectedDay = tempStr;
					startActivity(intent);
					Celenderdetail.this.finish();
					//cal.getTime();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
				
		
		
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
//		calStartDate = getCalendarStartDate();
//		CreateGirdView();
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
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			Intent intent = new Intent(Celenderdetail.this, MainActivity0.class);
			startActivity(intent);
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}
}
