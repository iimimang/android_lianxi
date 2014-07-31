
package com.android.mytab;

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



import com.android.bean.Leasttuku;
import com.android.http_data.BitmapManager;
import com.android.mytab.R;
import com.android.mytab.Layout2.ViewHolder;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.Toast;

public class Layout4 extends Activity implements OnScrollListener {
	
	private static final String TAG = "Layout4";
	
	private ListView listView;
	private View moreView; //���ظ���ҳ��
	private String result = ""; // ����һ��������ʾ������ַ���
	
	private zixun_Adapter mAdapter;
	private ArrayList listData;
	private Handler handler; // ����һ��Handler����
	
	private int lastItem;
    private int count;
    private int page=1;
    private boolean  sj=true;
      
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout4);
        listData = new ArrayList();
        listView = (ListView)findViewById(R.id.jingqu);
        moreView = getLayoutInflater().inflate(R.layout.load, null);
         
        listView.addFooterView(moreView); //��ӵײ�view��һ��Ҫ��setAdapter֮ǰ��ӣ�����ᱨ��
        loadMoreData(); //׼������
        
      
      
    }
    
    
    
    private  void loadMoreData(){ //���ظ�������
    	
    	
      	handler=new Handler() {  
            @Override  
            public void handleMessage(Message msg) { 
            	if (result != null) {
            	
            	
            	parseJsonMulti(result);
            	addp();
            	}
            
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
//    	result=send();
//    	parseJsonMulti(result);  	
    	
    }
    public void addp(){
    	
    
        mAdapter = new zixun_Adapter();
        mAdapter.setValues(listData);
        mAdapter.notifyDataSetChanged();
        listView.setAdapter(mAdapter); //����adapter
        listView.setOnScrollListener(this); //����listview�Ĺ����¼�
       
        listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				
				//HashMap<String,Object> map=(HashMap<String,Object>)listView.getItemAtPosition(arg2);  
				
				Leasttuku aaddd= (Leasttuku) listView.getItemAtPosition(arg2); 
		
				//int id=(Integer) map.get("id"); 
				int id=aaddd.id; 
				
				Log.i("aaaa",aaddd.id+"ccccccccccccccccccccc");
				Intent intent = new Intent(Layout4.this,new_detail.class);
				intent.putExtra("lv_item_id", id);
				 
		        startActivity(intent);	
			
			}
        	
        }); 
    	
    }
	public String send() {
		   
		
		String target = "http://admin.yunco.net/appapi/app_1008_a.php?userid=1008&act=list_news2&catid=86&pagesize=5&page="+page;	//Ҫ�ύ��Ŀ���ַ
		
		HttpClient httpclient = new DefaultHttpClient();//����HttpClient����
		HttpGet httpRequest = new HttpGet(target);	//����HttpGet���Ӷ���
		HttpResponse httpResponse;
		try {
			httpResponse = httpclient.execute(httpRequest);	//ִ��HttpClient���� 
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				result = EntityUtils.toString(httpResponse.getEntity());	//��ȡ���ص��ַ���
				 
				Log.i("aaaa",page+"---------ҳ��");
				page++; 
				
			}else{
				result="����ʧ�ܣ�";
				 
			}	
					
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			

			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(result.equals("0")){
			 
			sj=false;	
			Log.i("aaaa", "44444444444444444444444444"); 	
		}
		return result;
	}
	
    //����������ݵ�Json
	
 private ArrayList parseJsonMulti(String strResult) { 
 
	 ArrayList list=new ArrayList();
    	  
      try { 
    	   
    	  JSONArray jsonObjs = new JSONArray(strResult);
          //  JSONArray jsonObjs = new JSONObject(strResult).getJSONArray("singers"); 
          
            String s = "";        
            for(int i = 0; i < jsonObjs.length() ; i++){ 

                JSONObject jsonObj = ((JSONObject)jsonObjs.opt(i)) ; 

                int id = jsonObj.getInt("id"); 
                
                String title = jsonObj.getString("title");   
                String description = jsonObj.getString("description");
                String inputtime = jsonObj.getString("inputtime");
                String thumb = jsonObj.getString("thumb");
                       
              
                Leasttuku tempbean=new Leasttuku();
                tempbean.id=id;
                tempbean.title=title;
                tempbean.description=description;
                tempbean.thumb=thumb;
                listData.add(tempbean);		
            }
           
            count = listData.size();

        } catch (JSONException e) { 

           e.printStackTrace(); 

        } 
      return list;
    } 
 

	
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		
		Log.i(TAG, "firstVisibleItem="+firstVisibleItem+"\nvisibleItemCount="+
				visibleItemCount+"\ntotalItemCount"+totalItemCount);
		
		lastItem = firstVisibleItem + visibleItemCount - 1;  //��1����Ϊ������˸�addFooterView
		
		//Log.i("aaaa",lastItem+"������"); 
		
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) { 

//		Log.i("aaaa", "scrollState="+scrollState);
//		
//		Log.i("aaaa", "lastItem="+lastItem);
//		
//		Log.i("aaaa", "count="+count);
		//�����������ǣ������һ��item�����������ݵ�����ʱ�����и���
		if(lastItem == count  && scrollState == this.SCROLL_STATE_IDLE){ 
			//Log.i("aaaa", "������ײ�");
			moreView.setVisibility(view.VISIBLE);
		    mHandler.sendEmptyMessage(0);
			
		 } 
		
	}
	//����Handler
	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
			   	try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			   	 loadMoreData();  //���ظ������ݣ��������ʹ���첽����
			   	
			    mAdapter.notifyDataSetChanged();
			   
			    moreView.setVisibility(View.GONE); 
					
			  //  Log.i("aaaa", sj+"����");
			    if(!sj){
			    	
			    	Toast.makeText(Layout4.this, "ľ�и������ݣ�", 3000).show();
			          listView.removeFooterView(moreView); //�Ƴ��ײ���ͼ
			    }
				
				break;
            case 1: 
				
            	Log.i("TAGaa1", "eeeeeeeeeeeeeee=");
				break;
			default:
				Log.i("TAGaa2", "qqqqqqqqqqqqqqq=");
				break;
			}
		};
	};
	
	
	
	
	 private class zixun_Adapter extends BaseAdapter {
		  
	       
		  
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
	              
	              convertView =View.inflate(Layout4.this, R.layout.item, null);
	              holder.title = (TextView)convertView.findViewById(R.id.title);
	              holder.description = (TextView) convertView.findViewById(R.id.info); 
	              holder.iv = (ImageView) convertView.findViewById(R.id.imgs);
	              
	            convertView.setTag(holder);
	            } else {
	                holder = (ViewHolder)convertView.getTag();
	            }
	           
	          //  Log.i("aaaa",mData.get(position).toString()+"13131313");
	        
	            Leasttuku p=(Leasttuku) mData.get(position);
	         
	            holder.title.setText(p.title);
	            holder.description.setText(p.description);
	            BitmapManager.getInstance().loadBitmap(p.thumb, holder.iv);
	           
				
	            return convertView;
	        } 
	  
	    }
	class ViewHolder{
		public TextView title;
		public TextView description;
		public TextView inputtime;
		public ImageView iv;
	
	}

	
    
}












