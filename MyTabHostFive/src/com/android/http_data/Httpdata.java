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
	private String result = ""; // ����һ��������ʾ������ַ���
	private Handler handler; // ����һ��Handler����
	private ArrayList<HashMap<String, Object>> listData;
	private ArrayList listData2;
	
    public ArrayList prepareDatas1(final String biaoshi){  //׼������
    	
    	String resultaa = "";
    	
    	 
    	resultaa=send(biaoshi);
    	
    	listData=parseJsonMulti(resultaa);
    	return listData;
  	 
    }

	 public String prepareDatas2(final String biaoshi2){  //׼������
	    	
	    	String resultaa = "";
	       	resultaa=send(biaoshi2);
	       	return resultaa;
	  	 
	    }
    
	public String send(String aa) {
		 
		
		String target = aa;	//Ҫ�ύ��Ŀ���ַ
		
		HttpClient httpclient = new DefaultHttpClient();//����HttpClient����
		HttpGet httpRequest = new HttpGet(target);	//����HttpGet���Ӷ���
		HttpResponse httpResponse;
		try {
			httpResponse = httpclient.execute(httpRequest);	//ִ��HttpClient����
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				result = EntityUtils.toString(httpResponse.getEntity());	//��ȡ���ص��ַ���
				
			 
				Log.i("INFO22",result); 	 
			
			
			}else{
				result="����ʧ�ܣ�";
				
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
	
    //����������ݵ�Json ��ûͼ���б�
	
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












