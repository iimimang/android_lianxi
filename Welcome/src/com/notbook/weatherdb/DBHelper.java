package com.notbook.weatherdb;

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
				"_city TEXT," +
				"_todaywendu," +
				"_shidu," +
				"_week1 TEXT," +
				"_week2 TEXT," +
				"_week3 TEXT," +
				"_week4 TEXT," +
				"_week5 TEXT," +
				"_date1 TEXT," +
				"_date2 TEXT," +
				"_date3 TEXT," +
				"_date4 TEXT," +
				"_date5 TEXT," +
				"_date1_y TEXT," +
				"_date2_y TEXT," +
				"_date3_y TEXT," +
				"_date4_y TEXT," +
				"_date5_y TEXT," +
				"_weather1 TEXT," +
				"_weather2 TEXT," +
				"_weather3 TEXT," +
				"_weather4 TEXT," +
				"_weather5 TEXT," +
				"_temp1 TEXT," +
				"_temp2 TEXT," +
				"_temp3 TEXT," +
				"_temp4 TEXT," +
				"_temp5 TEXT)";
		
		db.execSQL(sql);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		String sql = "DROP TABLEN IF EXISTS "+TABLENAME;
		db.execSQL(sql);
		onCreate(db);
		
	}

}
