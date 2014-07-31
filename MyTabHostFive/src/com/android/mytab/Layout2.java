

package com.android.mytab;
import com.android.bean.Leastmeishi;
import com.android.http_data.BitmapManager;
import com.android.http_data.Httpdata;
import com.android.http_data.ServerAddress;
import com.android.mytab.R;


import com.android.mytab.Layout1.ViewHolder;

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

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.Toast;

public class Layout2 extends Activity  {
	
	 private GridView gvInfo;// 创建GridView对象
	private ListView listView1,listView2;
	private String result = ""; // 
	private Handler handler; // 声明Handler对象
	private SimpleAdapter adapter1,adapter2;
	private ArrayList<HashMap<String, Object>> listData1;
	private ArrayList listData2;
     
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout2);     

       init_view1();
      init_view2();
      
    }
    
    
    public void init_view1() {
    
    
    	final Handler handler2 = new Handler() {
			public void handleMessage(Message msg) {
				
				
					if (msg.obj != null) {
						
						
						init_app1((ArrayList) msg.obj);
						
					}
				
			}
		};

		new Thread(new Runnable() {
			public void run() {
			
				Message message = handler2.obtainMessage();
				Httpdata Httpdata=new Httpdata();
				message.obj = Httpdata.prepareDatas1(ServerAddress.GET_ZIXUN1_LIST);
				handler2.sendMessage(message);
			
			}
		}).start();
       
        
    }
    
    public void init_app1(ArrayList listData1){
    	
    	 listView1 = (ListView)findViewById(R.id.zixunlists_one);
	        adapter1 = new SimpleAdapter(this, listData1,R.layout.zixun, 
	        		new String[]{"title","description","inputtime"}, new int[]{R.id.title,R.id.description,R.id.inputtime});
	        listView1.setAdapter(adapter1); //设置adapter（上部）
    	
    }
    
    
    
    
    public void init_view2() {
    
    
    	final Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				
				
					if (msg.obj != null) {
						
						
						init_app2((ArrayList) msg.obj);
						
					}
				
			}
		};

		new Thread(new Runnable() {
			public void run() {
			
				Message message = handler.obtainMessage();
				Httpdata Httpdata=new Httpdata();
				message.obj = parseJsonMulti2(Httpdata.prepareDatas2(ServerAddress.GET_ZIXUN2_LIST));
				handler.sendMessage(message);
			
			}
		}).start();
       
        
    }
    
    public void init_app2(ArrayList listData2) {
    	
 
//    	 Httpdata Httpdata=new Httpdata();
//    	 
//    	listData2= parseJsonMulti2(Httpdata.prepareDatas2(ServerAddress.GET_ZIXUN2_LIST));	 
		gvInfo = (GridView) findViewById(R.id.zixunlists_two);
		meishi_Adapter mAdapter = new meishi_Adapter();
		mAdapter.setValues(listData2);
		mAdapter.notifyDataSetChanged();
		gvInfo.setAdapter(mAdapter); //设置adapter
		
    }
    
   
    
    //解析多个数据的Json (有图的列表)
	
	private ArrayList parseJsonMulti2(String strResult) { 

		ArrayList list2=new ArrayList();
    	  
		
		try { 
    	   
    	  JSONArray jsonObjs = new JSONArray(strResult);
          //  JSONArray jsonObjs = new JSONObject(strResult).getJSONArray("singers"); 
    	
            String s = "";        
           
            for(int i = 0; i < jsonObjs.length() ; i++){ 

                JSONObject jsonObj = ((JSONObject)jsonObjs.opt(i)) ; 
              
                String title = jsonObj.getString("title");   
                String description = jsonObj.getString("description");
                String thumb = jsonObj.getString("thumb");
               if(thumb.equals("")){
    				thumb="http://1008.yunco.net/upload/2013/0701/20130701033047414.jpg";
    			}
               
//                HashMap<String, Object> map = new HashMap<String, Object>();
//            	map.put("title", title);
//            	map.put("thumb", thumb);
               
               Leastmeishi tempbean=new Leastmeishi();
               tempbean.title=title;
               tempbean.thumb=thumb;
               
            
               list2.add(tempbean);  
    			
    			
            }
                  
        } catch (JSONException e) { 

        	// Log.i("TAG4444","3123");

            e.printStackTrace(); 

        } 
		
      return list2;
    }
	
 private class meishi_Adapter extends BaseAdapter {
	  
       
  
       private List mData = new ArrayList();
        
       //  private List<LeastProduct> mData = null;
        private LayoutInflater mInflater;
  
        private TreeSet mSeparatorsSet = new TreeSet();
  
        private HashMap<Integer, View> viewMap = null;
        
    	public void setValues(List pList){
    	
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
              
              convertView =View.inflate(Layout2.this, R.layout.zixun_tu1, null);
              holder.title = (TextView)convertView.findViewById(R.id.title2);
              holder.iv = (ImageView) convertView.findViewById(R.id.thumb2); 
              
            convertView.setTag(holder);
            } else {
                holder = (ViewHolder)convertView.getTag();
            }
           
          //  Log.i("aaaa",mData.get(position).toString()+"13131313");
        
            Leastmeishi p=(Leastmeishi) mData.get(position);
         
            holder.title.setText(p.title);
            Bitmap rawBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.gggg); 
            BitmapManager.getInstance().loadBitmap(p.thumb, holder.iv);
           
			
            return convertView;
        } 
  
    }
class ViewHolder{
	public TextView title;
	public ImageView iv;
}



       
}












