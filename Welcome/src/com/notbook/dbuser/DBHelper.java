package com.notbook.dbuser;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper implements Meta{

	public DBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		
		String sql = "CREATE TABLE IF NOT EXISTS " + TABLENAME +"(" +
				"_id INTEGER PRIMARY KEY," +
				"_username TEXT," +
				"_job TEXT," +
				"_email TEXT," +
				"_address TEXT)";
		
		db.execSQL(sql);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		String sql = "DROP TABLEN IF EXISTS "+TABLENAME;
		db.execSQL(sql);
		onCreate(db);
		
	}

}
