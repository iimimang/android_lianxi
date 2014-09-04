package com.notbook.dbuser;

import android.net.Uri;
import android.provider.BaseColumns;

public interface Meta {
	
	String AUTHROTY = "com.homework.dbuser";
	
	
	String DATABASENAME = "dbuser";
	String TABLENAME = "dbuser";
	int VESION = 1;

	Uri CONTENT_URI = Uri.parse("content://"+AUTHROTY+"/"+TABLENAME);
	String CONTACT_LIST="vnd.android.cursor.dir/vnd.myprovider.memorandum";   
	String CONTACT_ITEM="vnd.android.cursor.item/vnd.myprovider.memorandum"; 
	// /�����_id, ��������_date,����ʱ��_time, ��������_content, �Ƿ�����_enabled, �Ƿ���������_alarmed,
	// ����ʱ��_createTime)
	public interface TableMeta extends BaseColumns {
		String _USERNAME = "_username";
		String _JOB = "_job";
		String _EMAIL = "_email";
		String _ADDRESS = "_address";
	}
}
