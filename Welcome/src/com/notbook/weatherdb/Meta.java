package com.notbook.weatherdb;

import android.net.Uri;
import android.provider.BaseColumns;

public interface Meta {
	
	String AUTHROTY = "com.notebook.wearherdb";
	
	
	String DATABASENAME = "t_weather";
	String TABLENAME = "t_weather";
	int VESION = 1;

	Uri CONTENT_URI = Uri.parse("content://"+AUTHROTY+"/"+TABLENAME);
	String CONTACT_LIST="vnd.android.cursor.dir/vnd.myprovider.memorandum";   
	String CONTACT_ITEM="vnd.android.cursor.item/vnd.myprovider.memorandum"; 
	// /�����_id, ��������_date,����ʱ��_time, ��������_content, �Ƿ�����_enabled, �Ƿ���������_alarmed,
	// ����ʱ��_createTime)
	public interface TableMeta extends BaseColumns {
		String _CITY = "_city";
		String _TODAYWENDU = "_todaywendu";
		String _SHIDU = "_shidu";
		String _WEATHER1 = "_weather1";
		String _WEATHER2 = "_weather2";
		String _WEATHER3 = "_weather3";
		String _WEATHER4 = "_weather4";
		String _WEATHER5 = "_weather5";
		String _TEMP1 = "_temp1";
		String _TEMP2 = "_temp2";
		String _TEMP3 = "_temp3";
		String _TEMP4 = "_temp4";
		String _TEMP5 = "_temp5";
		String _WEEK1 = "_week1";
		String _WEEK2 = "_week2";
		String _WEEK3 = "_week3";
		String _WEEK4 = "_week4";
		String _WEEK5 = "_week5";
		String _DATE1_Y = "_date1_y";
		String _DATE2_Y = "_date2_y";
		String _DATE3_Y = "_date3_y";
		String _DATE4_Y = "_date4_y";
		String _DATE5_Y = "_date5_y";
		String _DATE1 = "_date1";
		String _DATE2 = "_date2";
		String _DATE3 = "_date3";
		String _DATE4 = "_date4";
		String _DATE5 = "_date5";
	}
}
