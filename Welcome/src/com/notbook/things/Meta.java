package com.notbook.things;

import android.net.Uri;
import android.provider.BaseColumns;

public interface Meta {
	
	String AUTHROTY = "com.notebook.db";
	
	
	String DATABASENAME = "notebook";
	String TABLENAME = "notebook";
	int VESION = 1;

	Uri CONTENT_URI = Uri.parse("content://"+AUTHROTY+"/"+TABLENAME);
	String CONTACT_LIST="vnd.android.cursor.dir/vnd.myprovider.notebook";   
	String CONTACT_ITEM="vnd.android.cursor.item/vnd.myprovider.notebook"; 
	// /�����_id, ��������_date,����ʱ��_time, ��������_content, �Ƿ�����_enabled, �Ƿ���������_alarmed,
	// ����ʱ��_createTime)
	public interface TableMeta extends BaseColumns {
		String _LEIXING = "_leixing";
		String _TIME = "_time";
		String _MTIME = "_mtime";
		String _ZHUTI = "_zhuti";
		String _JISHI = "_jishi";
		String _IMAGE = "_image";
		String _YOUXIAN = "_youxian";
		String _LINGYIN = "_lingyin";
		String _LUYIN = "_luyin";
		String _SELECTTIME = "_selecttime";
		String _TIXING_ID = "_tixing_id";
		String _TIMEUIQUE = "_timeuique";
		String _TIME_UIQUE = "_time_uique";
		String _DATE = "_date";
	}
}
