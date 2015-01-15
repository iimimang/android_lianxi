package com.example.android_db.test;

import java.util.List;
import java.util.Map;

import com.example.android_db.dao.PersonDao;
import com.example.android_db.db.DbOpenHelper;
import com.example.android_db.service.PersonService;

import android.test.AndroidTestCase;
import android.util.Log;

public class MyTest extends AndroidTestCase {

	public MyTest() {
		// TODO Auto-generated constructor stub
	}
	
	public void createDb(){
		DbOpenHelper helper = new DbOpenHelper(getContext());
		helper.getWritableDatabase();
	}

	public void insertDB(){
		PersonService service = new PersonDao(getContext());
		Object[] params = {"李斯","广西","女"};
		boolean flag = service.addPerson(params);
		System.out.println("--->>"+flag);
	}
	
	public void deleteDB(){
		PersonService service = new PersonDao(getContext());
		Object[] params = {1};
		boolean flag = service.deletePerson(params);
		System.out.println("--->>"+flag);
	}
	
	public void updateDB(){
		PersonService service = new PersonDao(getContext());
		Object[] params = {"王五","上海","不祥","3"};
		service.updatePerson(params);
	}
	
	public void viewDB(){
		PersonService service = new PersonDao(getContext());
		String[] selectionArgs = {"3"};
		Map<String, String> map = service.viewPerson(selectionArgs);
		Log.i("Test", "-->>"+map.toString());
	}
	
	public void listDB(){
		PersonService service = new PersonDao(getContext());
		
		List<Map<String,String>> list = service.listPersonMaps(null);
		Log.i("Test", "-->>"+list.toString());
	}
}
