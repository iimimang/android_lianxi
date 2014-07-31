package com.android.mytab;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.util.Log;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class new_detail extends Activity
{
    private TextView txtInMoney = null,txtInTime = null;
	private String result = ""; // 声明一个代表显示结果的字符串
	private SimpleAdapter adapter;
	private ArrayList listData;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_detail);
        
        
        txtInMoney = (TextView) findViewById(R.id.title_detal);// 获取金额文本框
        txtInTime = (TextView) findViewById(R.id.content_detal);// 获取时间文本框
        
        
        int lv_item_id = getIntent().getIntExtra("lv_item_id", 0);
        
      //  Log.i("aaaa",lv_item_id+"\\\\\\");
        
        	  if(lv_item_id != -1) {
        	            //根据ID查询做数据库操作吧
        		  
        		  result=send(lv_item_id);
        		listData= parseJsonMulti(result);
        	
        		HashMap<String,Object> map=(HashMap<String, Object>) listData.get(0);
        		
        		String titles=(String) map.get("title"); 
        		String content=(String) map.get("content"); 
        		 
        		
        		CharSequence richText = Html.fromHtml(content,imgGetter,null);
        		
        		
        		Log.i("aaaa",titles);
  
      		  
        		txtInMoney.setText(titles);
        		txtInTime.setText(richText);
        		
     
        		  
        		  
        		
       
        	  }
     
    }
    
    ImageGetter imgGetter = new Html.ImageGetter() {
        public Drawable getDrawable(String source) {
              Drawable drawable = null;
              URL url; 
              try {  
                  url = new URL(source); 
                  drawable = Drawable.createFromStream(url.openStream(), "");  //获取网路图片
              } catch (Exception e) { 
                  return null; 
              } 
              drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable
                            .getIntrinsicHeight());
              return drawable;
        }
};
    
    
	public String send(int id) {
		   
		
		String target = "http://admin.yunco.net/appapi/app_1008_a.php?userid=1008&act=show_news&catid=123&id="+id;	//要提交的目标地址
		
		HttpClient httpclient = new DefaultHttpClient();//创建HttpClient对象
		HttpGet httpRequest = new HttpGet(target);	//创建HttpGet连接对象
		HttpResponse httpResponse;
		try {
			httpResponse = httpclient.execute(httpRequest);	//执行HttpClient请求 
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				result = EntityUtils.toString(httpResponse.getEntity());	//获取返回的字符串
				

			
				 Log.i("aaaa",result+"vvvvvvvvvvvvvvvvvvvv");
			
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
	
    //解析多个数据的Json
	
 private ArrayList parseJsonMulti(String strResult) { 


	 
    ArrayList list=new ArrayList();
    	  
      try { 
    	   
    	  JSONObject jsonObj = new JSONObject(strResult); 


    	   int id = jsonObj.getInt("id"); 

    	     String title = jsonObj.getString("title");  
    	     String content = jsonObj.getString("content");  
    	     
    	
         
    	     HashMap<String, Object> map = new HashMap<String, Object>();
            	
    	     map.put("title", title);
    	
    		 map.put("content", content);
    		
    		 list.add(map);             

        } catch (JSONException e) { 

        	// Log.i("TAG4444","3123");

            e.printStackTrace(); 

        } 
      
     
      return list;
    }
 
 

 
 
 
}
