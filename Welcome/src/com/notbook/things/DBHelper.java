package com.notbook.things;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
				"_leixing TEXT," +
				"_time TEXT," +
				"_selecttime TEXT," +
				"_mtime TEXT," +
				"_zhuti TEXT," +
				"_jishi TEXT," +
				"_youxian TEXT," +
				"_lingyin TEXT," +
				"_luyin TEXT," +
				"_tixing_id TEXT," +
				"_timeuique TEXT," +
				"_time_uique TEXT," +
				"_date TEXT," +
				"_image TEXT)";
		
		db.execSQL(sql);
		Log.i("log", "----------------------------------------");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		String sql = "DROP TABLEN IF EXISTS "+TABLENAME;
		db.execSQL(sql);
		onCreate(db);
		
	}

}
