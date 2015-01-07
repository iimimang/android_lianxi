package com.wyj.dataprocessing;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.wyj.utils.GetPostUtil;

public class AccessNetwork  implements Runnable{
	  private String op ;  
	    private String url;  
	    private String params;  
	    private Handler h;  
	    
	      
	    public AccessNetwork(String op, String url, String params,Handler h) {  
	        super();    
	        this.op = op;  
	        this.url = url;  
	        this.params = params;  
	        this.h = h;  
	    }  
	  
	    @Override  
	    public void run() {  
	        Message m = new Message();  
	        m.what = 0x123;   
	        
	        if(op.equals("GET")){  
	            Log.i("iiiiiii","发送GET请求");  
	            m.obj = GetPostUtil.sendGet(url, params);  
	            Log.i("iiiiiii",">>>>>>>>>>>>"+m.obj);  
	        } 
	        if(op.equals("POST")){  
	            Log.i("aaaa","发送POST请求");  
	            m.obj = GetPostUtil.sendPost(url, params);  
	            Log.i("aaaa",">>>>>>>>>>>>"+m.obj);  
	        }  
	        h.sendMessage(m);  
	    } 
}