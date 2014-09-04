package com.android.mytab;

import com.android.bean.LeastProduct;
import com.android.http_data.BitmapManager;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

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
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.Toast;

public class Layout1 extends Activity {
	
	private static final String TAG = "Layout1";
	private ListView listView;
	private String result = ""; // ����һ�������ʾ�����ַ�
	private Handler handler; // ����һ��Handler����
	private SimpleAdapter adapter;
	private MyCustomAdapter mAdapter;
	private ArrayList listData;
	
	
     
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout1);
        listView = (ListView)findViewById(R.id.lists);
        listData = new ArrayList();
        prepareData(); //׼�����
       
    }

    private void prepareData(){  //׼�����
    	handler=new Handler() {  
            @Override  
            public void handleMessage(Message msg) { 
            	if (result != null) {
            	listData=parseJsonMulti(result);
            	}
            	appda(listData);
                super.handleMessage(msg);  
            }  
        };  
        new Thread(new Runnable() {
			public void run() {
				send();
				Message m = handler.obtainMessage(); // ��ȡһ��Message
				handler.sendMessage(m); // ������Ϣ
			}
		}).start(); // �����߳�
    }  


	public void appda(ArrayList list) {
		

		mAdapter=new MyCustomAdapter();
		mAdapter.setValues(list);
		mAdapter.notifyDataSetChanged();
		listView.setAdapter(mAdapter); //����adapter
		
	}  
	public String send() {
		
		String target = "http://122.49.1.103/appapi/app_1008_a.php?userid=1008&act=index_jq";	//Ҫ�ύ��Ŀ���ַ
		
		HttpClient httpclient = new DefaultHttpClient();//����HttpClient����
		HttpGet httpRequest = new HttpGet(target);	//����HttpGet���Ӷ���
		HttpResponse httpResponse;
		try {
			httpResponse = httpclient.execute(httpRequest);	//ִ��HttpClient����
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				result = EntityUtils.toString(httpResponse.getEntity());	//��ȡ���ص��ַ�
 
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
	
    //���������ݵ�Json
	
	private ArrayList parseJsonMulti(String strResult) { 

		List list=new ArrayList();
    	  
		try { 
    	   
    	  JSONArray jsonObjs = new JSONArray(strResult);
          //  JSONArray jsonObjs = new JSONObject(strResult).getJSONArray("singers"); 
          
            String s = "";        
            for(int i = 0; i < jsonObjs.length() ; i++){ 

                JSONObject jsonObj = ((JSONObject)jsonObjs.opt(i)) ; 

              //  int id = jsonObj.getInt("id"); 

                String title = jsonObj.getString("title");   
                String description = jsonObj.getString("description");
                String thumb = jsonObj.getString("thumb");
        
                 
                LeastProduct tempbean=new LeastProduct();
                tempbean.title=title;
                tempbean.description=description;
                tempbean.thumb=thumb;
//                HashMap<String, Object> map = new HashMap<String, Object>();
//            	
//    			map.put("title", title);
//    			map.put("description", description);
//    			map.put("thumb", thumb);
    			
              
                
    			listData.add(tempbean);
    			 
        		
            }
          
           // Log.i("aaaa", listData.toString()); 

        } catch (JSONException e) { 

        	// Log.i("TAG4444","3123");

            e.printStackTrace(); 

        } 
			
      return listData;
    } 
 
 
	
		
	 private class MyCustomAdapter extends BaseAdapter {
		  
	       
	  
	       private List mData = new ArrayList();
	        
	       //  private List<LeastProduct> mData = null;
	        private LayoutInflater mInflater;
	  
	        private TreeSet mSeparatorsSet = new TreeSet();
	  
	        private HashMap<Integer, View> viewMap = null;
	        
	    	public void setValues(List pList){
	    		Log.i("aaaa","0000000000000000");
				this.mData = pList;
				
				
				viewMap = new HashMap<Integer, View>();
			}
			
	  
	        @Override
	        public int getCount() {
	            return mData.size();
	        }
	  
	        @Override
	        public Object getItem(int position) {
	            return mData.get(position);
	        }
	     

	        @Override
	        public long getItemId(int position) {
	            return position;
	        }
	  
	        @Override
	        public View getView(int position, View convertView, ViewGroup parent) {
	            ViewHolder holder = null;
	            int type = getItemViewType(position);
	           
	            if (convertView == null) {
	                holder = new ViewHolder();
	              
	              convertView =View.inflate(Layout1.this, R.layout.item, null);
	              holder.title = (TextView)convertView.findViewById(R.id.title);
	              holder.info = (TextView)convertView.findViewById(R.id.info);
	              holder.iv = (ImageView) convertView.findViewById(R.id.imgs); 
	              
                convertView.setTag(holder);
	            } else {
	                holder = (ViewHolder)convertView.getTag();
	            }
	           
	            LeastProduct p=(LeastProduct)mData.get(position);
	           
	            holder.title.setText(p.title);
				holder.info.setText(p.description);
	            Bitmap rawBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.gggg); 
	            BitmapManager.getInstance().loadBitmap(p.thumb, holder.iv, rawBitmap);
	           
				
	            return convertView;
	        }
	  
	    }
	class ViewHolder{
		public TextView title;
		public TextView info;
		public ImageView iv;
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		//Log.i("TAG","----"+keyCode);
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			
			//webView.goBack();
			exitApp();
			return false;
			
	

		default:
			break;
		}
		//Log.i("sdfsfds", "")
		return super.onKeyDown(keyCode, event);
	}
	
	public void exitApp(){
		new AlertDialog.Builder(this).setTitle("ȷ���˳�").setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();
				System.exit(0);
			}
		}).setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
		}).create().show();
	}

    
}












