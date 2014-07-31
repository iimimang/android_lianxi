package com.android.http_data;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.mytab.R;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.ListActivity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.Toast;

public class Httpdata {
	
	private ListView listView;
	private String result = ""; // 声明一个代表显示结果的字符串
	private Handler handler; // 声明一个Handler对象
	private ArrayList<HashMap<String, Object>> listData;
	private ArrayList listData2;
	
    public ArrayList prepareDatas1(final String biaoshi){  //准备数据
    	
    	String resultaa = "";
    	
    	 
    	resultaa=send(biaoshi);
    	
    	listData=parseJsonMulti(resultaa);
    	return listData;
  	 
    }

	 public String prepareDatas2(final String biaoshi2){  //准备数据
	    	
	    	String resultaa = "";
	       	resultaa=send(biaoshi2);
	       	return resultaa;
	  	 
	    }
    
	public String send(String aa) {
		 
		
		String target = aa;	//要提交的目标地址
		
		HttpClient httpclient = new DefaultHttpClient();//创建HttpClient对象
		HttpGet httpRequest = new HttpGet(target);	//创建HttpGet连接对象
		HttpResponse httpResponse;
		try {
			httpResponse = httpclient.execute(httpRequest);	//执行HttpClient请求
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				result = EntityUtils.toString(httpResponse.getEntity());	//获取返回的字符串
				
			 
				Log.i("INFO22",result); 	 
			
			
			}else{
				result="请求失败！";
				
			}	
					
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			

			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(result.equals("0")){
			 
				
			Log.i("INFO", "444444444444"); 	
		}
		return result;
	}
	
    //解析多个数据的Json （没图的列表）
	
	private ArrayList parseJsonMulti(String strResult) { 

		ArrayList list=new ArrayList();
    	  
		try { 
    	   
    	  JSONArray jsonObjs = new JSONArray(strResult);
          //  JSONArray jsonObjs = new JSONObject(strResult).getJSONArray("singers"); 
          
            String s = "";        
            for(int i = 0; i < jsonObjs.length() ; i++){ 

                JSONObject jsonObj = ((JSONObject)jsonObjs.opt(i)) ; 

                String title = jsonObj.getString("title");   
                String description = jsonObj.getString("description");
                String inputtime = jsonObj.getString("inputtime");
               
                HashMap<String, Object> map = new HashMap<String, Object>();
            	
    			map.put("title", title);
    			map.put("description", description);
    			map.put("inputtime", inputtime);
    			list.add(map);
      		
            }

        } catch (JSONException e) { 

        	// Log.i("TAG4444","3123");

            e.printStackTrace(); 

        } 
			
      return list;
    }
	
		

    
}












