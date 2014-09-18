package com.example.android_db.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.android_db.db.DbOpenHelper;
import com.example.android_db.service.PersonService;

public class PersonDao implements PersonService {

	private DbOpenHelper helper = null;
	public PersonDao(Context context) {
		// TODO Auto-generated constructor stub
		helper = new DbOpenHelper(context);
	}

	@Override
	public boolean addPerson(Object[] params) {
		// TODO Auto-generated method stub
		boolean flag = false;
		//ʵ�ֶ����ݿ�����ɾ�����޸Ĳ�ѯ�Ĺ���
		SQLiteDatabase database = null;
		try {
			String sql = "insert into person(name,address,sex) values(?,?,?)";
			database = helper.getWritableDatabase();//ʵ�ֶ����ݿ��д�Ĳ���
			database.execSQL(sql, params);
			flag = true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			if(database!=null){
				database.close();
			}
		}
		return flag;
	}

	@Override
	public boolean deletePerson(Object[] params) {
		// TODO Auto-generated method stub
		boolean flag = false;
		SQLiteDatabase database = null;
		try {
			String sql = "delete from person where id = ? ";
			database = helper.getWritableDatabase();
			database.execSQL(sql, params);
			flag = true;
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			if(database!=null){
				database.close();
			}
		}
		return flag;
	}

	@Override
	public boolean updatePerson(Object[] params) {
		// TODO Auto-generated method stub
		boolean flag = false;
		SQLiteDatabase database = null;
		try {
			String sql = "update person set name = ? ,address = ?, sex = ? where id = ? ";
			database = helper.getWritableDatabase();
			database.execSQL(sql, params);
			flag = true;
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			if(database!=null){
				database.close();
			}
		}
		return flag;
	}

	@Override
	public Map<String, String> viewPerson(String[] selectionArgs) {
		Map<String,String> map = new HashMap<String, String>();
		SQLiteDatabase database = null;
		try {
			String sql = "select * from person where id = ? ";
			database = helper.getReadableDatabase();
			Cursor cursor = database.rawQuery(sql, selectionArgs);
			//������ݿ���еĸ���
			int colums = cursor.getColumnCount();
			while(cursor.moveToNext()){
				for(int i=0;i<colums;i++){
					String cols_name = cursor.getColumnName(i);
					String cols_value = cursor.getString(cursor.getColumnIndex(cols_name));
					if(cols_value==null){
						cols_value = "";
					}
					map.put(cols_name, cols_value);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			if(database!=null){
				database.close();
			}
		}
		return map;
	}

	@Override
	public List<Map<String, String>> listPersonMaps(String[] selectionArgs) {
		// TODO Auto-generated method stub
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		String sql = "select * from person ";
		SQLiteDatabase database = null;
		try {
			database = helper.getReadableDatabase();
			Cursor cursor = database.rawQuery(sql, selectionArgs);
			int colums = cursor.getColumnCount();
			while(cursor.moveToNext()){
				Map<String,String> map = new HashMap<String, String>();
				for(int i=0;i<colums;i++){
					String cols_name = cursor.getColumnName(i);
					String cols_value = cursor.getString(cursor.getColumnIndex(cols_name));
					if(cols_value==null){
						cols_value="";
					}
					map.put(cols_name, cols_value);
				}
				list.add(map);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			if(database!=null){
				database.close();
			}
		}
		return list;
	}

}
