package com.wyj.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JsonToListHelper {
	public static List<Map<String, Object>> jsonToList(String jsonString) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			JSONArray jsonArray_datas = jsonObject.getJSONArray("data");
			for (int i = 0; i < jsonArray_datas.length(); i++) {
				JSONObject jsonObject_data = jsonArray_datas.getJSONObject(i);
				map = new HashMap<String, Object>();
				map.put("id", jsonObject_data.getString("id"));
				map.put("title", jsonObject_data.getString("title"));
				map.put("source", jsonObject_data.getString("source"));
				map.put("wap_thumb", jsonObject_data.getString("wap_thumb"));
				map.put("create_time", jsonObject_data.getString("create_time"));
				map.put("nickname", jsonObject_data.getString("nickname"));
				list.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static List<Map<String, Object>> jsonToWBList(String jsonString) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			JSONObject jsonObject_data = jsonObject.getJSONObject("data");

			map = new HashMap<String, Object>();
			map.put("wap_content", jsonObject_data.getString("wap_content"));
			list.add(map);

		} catch (Exception e) {
			e.printStackTrace();
			Log.i("json", "==err");
		}
		return list;
	}

	public static List<Map<String, Object>> jsonToHeaderList(String jsonString) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			JSONArray jsonArray_datas = jsonObject.getJSONArray("data");
			for (int i = 0; i < jsonArray_datas.length(); i++) {
				JSONObject jsonObject_data = jsonArray_datas.getJSONObject(i);
				map = new HashMap<String, Object>();
				map.put("image", jsonObject_data.getString("image_s"));
				list.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.i("json", "==err");
		}
		return list;
	}
	
	
	public static List<Map<String, Object>> gettemplelist_json(String jsonString) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			JSONArray jsonArray_datas = jsonObject.getJSONArray("templelist");
			
			for (int i = 0; i < jsonArray_datas.length(); i++) {
				JSONObject jsonObject_data = jsonArray_datas.getJSONObject(i);
				map = new HashMap<String, Object>();
				map.put("templeid", jsonObject_data.getInt("templeid"));
				map.put("templename", jsonObject_data.getString("templename"));
				map.put("province", jsonObject_data.getString("province"));
				list.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public static List<Map<String, Object>> orderlist_json(String jsonString) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			JSONArray jsonArray_datas = jsonObject.getJSONArray("orderlist");
			
			for (int i = 0; i < jsonArray_datas.length(); i++) {
				JSONObject jsonObject_data = jsonArray_datas.getJSONObject(i);
				map = new HashMap<String, Object>();
				map.put("orderid", jsonObject_data.getInt("orderid"));
				map.put("wishname", jsonObject_data.getString("wishname"));
				map.put("wishtext", jsonObject_data.getString("wishtext"));
				map.put("truename", jsonObject_data.getString("truename"));
				map.put("templename", jsonObject_data.getString("templename"));
				
				list.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
