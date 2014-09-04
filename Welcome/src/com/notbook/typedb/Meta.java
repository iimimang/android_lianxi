package com.notbook.typedb;

import android.net.Uri;
import android.provider.BaseColumns;

public interface Meta {
	
	String AUTHROTY = "com.notebook.typedb";
	
	
	String DATABASENAME = "typedb";
	String TABLENAME = "typedb";
	int VESION = 1;

	Uri CONTENT_URI = Uri.parse("content://"+AUTHROTY+"/"+TABLENAME);
	String CONTACT_LIST="vnd.android.cursor.dir/vnd.myprovider.memorandum";   
	String CONTACT_ITEM="vnd.android.cursor.item/vnd.myprovider.memorandum"; 
	// /�����_id, ��������_date,����ʱ��_time, ��������_content, �Ƿ�����_enabled, �Ƿ���������_alarmed,
	// ����ʱ��_createTime)
	public interface TableMeta extends BaseColumns {
		String _TYPE = "_type";
		String _CHECKED = "_checked";
		
	}
}
