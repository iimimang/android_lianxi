package com.app.note.db;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class NoteInfo {
	private NoteDBHelper malllightDBHelper;

	public NoteInfo(Context context) {
		malllightDBHelper = new NoteDBHelper(context);
	}

	public void insertNoteInfo(String noteExplain, String notetime) {
		SQLiteDatabase db = null;
		db = malllightDBHelper.getReadableDatabase();
		String sql = "insert into NoteDefine(noteExplain,notetime) values(?,?)";
		db.execSQL(sql, new Object[] { noteExplain, notetime });
		db.close();
	}

	public void deleteNoteInfo(int id) {
		SQLiteDatabase db = null;
		db = malllightDBHelper.getReadableDatabase();
		String sql = "delete from NoteDefine where noteID=?";
		db.execSQL(sql, new Object[] { id });
		db.close();
	}
	


	
	/**
	 * @param db
	 * @return 查询所有数据
	 */
	public List<NoteDefine> getAllDeviceInfo() {
		SQLiteDatabase db = null;
		db = malllightDBHelper.getReadableDatabase();
		List<NoteDefine> list = new ArrayList<NoteDefine>();
		String sql = "SELECT * FROM NoteDefine";
		Cursor cursor = db.rawQuery(sql, null);
		if (cursor != null) {
			int count = cursor.getCount();
			if (count > 0) {
				while (cursor.moveToNext()) {
					NoteDefine device = new NoteDefine();
					device.setNoteID(cursor.getInt(cursor
							.getColumnIndex("noteID")));
					device.setNoteExplain(cursor.getString(cursor
							.getColumnIndex("noteexplain")));
					device.setNotetime(cursor.getString(cursor
							.getColumnIndex("notetime")));
					list.add(device);
					// db.close();
				}
			}

		}
		db.close();
		cursor.close();
		return list;
	}
}
