package com.example.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;

import com.example.db.DBHelper;
import com.example.db.panduola_db;
import com.example.panduola.R;


public class List_json  {

	
	 public static  String LISTGOOD ="http://192.168.1.30/app.php";
	private Context context;
	
	 public List_json(Context context) {
			
		 this.context=context;
	 }
	 public List<HashMap<String, Object>> parseJsonMulti(String strResult) { 
		 
		 List<HashMap<String, Object>> maps = new ArrayList<HashMap<String,Object>>();
         HashMap<String, Object> map = new HashMap<String, Object>();
	    	  
	      try { 
	    	   
	    	  JSONArray jsonObjs = new JSONArray(strResult);
	          //  JSONArray jsonObjs = new JSONObject(strResult).getJSONArray("singers"); 
	          
	            String s = "";   
	            
                
	            for(int i = 0; i < jsonObjs.length() ; i++){ 

	                JSONObject jsonObj = ((JSONObject)jsonObjs.opt(i)) ; 

	                int id = jsonObj.getInt("id"); 
	                String title = jsonObj.getString("title");   
	                String description = jsonObj.getString("description");
	                String thumb = jsonObj.getString("thumb");
	                
	                Save_db( id, title, thumb, description);
	                
	        		map=new HashMap<String, Object>();
	        		map.put("title", title);
	        		map.put("img", thumb);
	        		map.put("jiage", "78.0");
	        		map.put("old_jiage", "165");
	        		map.put("xiaoliang", "100");
	        		maps.add(map);
	        	  }
	           
	        } catch (JSONException e) { 

	           e.printStackTrace(); 

	        } 
	      return maps;
	    }
	 
	 public void Save_db(int id,String title,String img,String description){
		 
		 panduola_db panduoladb=new panduola_db(this.context);
		 
			ContentValues values = new ContentValues(); // 相当于map
		    values.put("id", id);
		 	values.put("title", title);
		 	values.put("thumb", img);
		 	values.put("description", description);
		 
		 	panduoladb.insert(values);
	 }

	
}
