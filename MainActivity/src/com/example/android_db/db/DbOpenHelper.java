package com.example.android_db.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DbOpenHelper extends SQLiteOpenHelper {

	private static String name = "mydb.db";// ��ʾ���ݿ������
	private static int version = 2;// ��ʾ���ݿ�İ汾����

	public DbOpenHelper(Context context) {
		super(context, name, null, version);
	}

	// �����ݿⴴ����ʱ���ǵ�һ�α�ִ��,��ɶ����ݿ�ı�Ĵ���
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		//֧�ֵ��������ͣ��������ݣ��ַ������ͣ��������ͣ������Ƶ��������ͣ�
		String sql = "create table person(id integer primary key autoincrement,name varchar(64),address varchar(64))";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		String sql = "alter table person add sex varchar(8)";
		db.execSQL(sql);
	}

}
