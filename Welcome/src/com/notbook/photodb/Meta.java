package com.notbook.photodb;

import android.net.Uri;
import android.provider.BaseColumns;

public interface Meta {
	
	String AUTHROTY = "com.notebook.photodb";
	
	
	String DATABASENAME = "photodb";
	String TABLENAME = "photodb";
	int VESION = 1;

	Uri CONTENT_URI = Uri.parse("content://"+AUTHROTY+"/"+TABLENAME);
	String CONTACT_LIST="vnd.android.cursor.dir/vnd.myprovider.memorandum";   
	String CONTACT_ITEM="vnd.android.cursor.item/vnd.myprovider.memorandum"; 
	// /�����_id, ��������_date,����ʱ��_time, ��������_content, �Ƿ�����_enabled, �Ƿ���������_alarmed,
	// ����ʱ��_createTime)
	public interface TableMeta extends BaseColumns {
		String _DATE = "_date";
	}
}
