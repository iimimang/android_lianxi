package com.notbook.weatherdb;


import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class SqliteProvider extends ContentProvider {
	
	SQLiteDatabase db;
	DBHelper helper ;

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		if(helper != null){
			db = helper.getWritableDatabase();
			
			return db.delete(Meta.TABLENAME, selection, selectionArgs);
		}
		db.close();
		return 0;
	}

	@Override
	public String getType(Uri uri) {
		
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		long id = 0;
		if(helper != null){
			db = helper.getWritableDatabase();
			
			id = db.insert(Meta.TABLENAME, null, values);
		}
		db.close();
		return ContentUris.withAppendedId(uri, id);
	}

	@Override
	public boolean onCreate() {
		helper = new DBHelper(getContext(), Meta.DATABASENAME, null, Meta.VESION);
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		if(helper != null){
			db = helper.getWritableDatabase();
			
			return db.query(Meta.TABLENAME, projection, selection, selectionArgs, null, null, sortOrder);
		}
		db.close();
		return null;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		if(helper != null){
			db = helper.getWritableDatabase();
			
			return db.update(Meta.TABLENAME, values, selection, selectionArgs);
		}
		db.close();
		return 0;
	}

}
