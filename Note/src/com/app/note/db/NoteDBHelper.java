package com.app.note.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NoteDBHelper extends SQLiteOpenHelper {
	private static Context context;
	private static String dbName = "notedb";
	private static int version = 1;

	public NoteDBHelper(Context context) {
		super(context, dbName, null, version);
	}

	public NoteDBHelper() {
		super(context, dbName, null, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql1 = "create table NoteDefine(noteID integer primary key autoincrement not null,noteexplain varchar,notetime varchar)";
		db.execSQL(sql1);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		// TODO Auto-generated method stub
		onCreate(db);
	}
}
