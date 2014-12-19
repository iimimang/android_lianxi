package com.wyj.tabmenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.TabHost.TabSpec;

public class Find extends Activity  
{
	

		private ListView mListView;

	    private List<HashMap<String, Object>> Listdata;
	    private BaseListAdapter mAdapter;
	    private int count=0;
	
		
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_find);
		
		//getData();
	    mListView = (ListView) findViewById(R.id.find_list);
	    mListView.setAdapter(new BaseListAdapter(this));
	}


    private List<HashMap<String, Object>>  getData() {
        List<HashMap<String, Object>> maps = new ArrayList<HashMap<String,Object>>();
        HashMap<String, Object> map = new HashMap<String, Object>();

		for(int i = 0;i<10;i++){
			map=new HashMap<String, Object>();
			map.put("username", "寂静的风");
			map.put("img", R.drawable.foot_07);
			map.put("address", "刚刚在五台山求愿祈福");
			map.put("title", "身体健康，万事如意。心想事成，财源广进。恭喜发财，长寿多福");
			map.put("jiachi", "太乙真人，居然三人等2532人加持");
			maps.add(map);
		}
		
		return maps;
		//count = Listdata.size();
		//Log.i("bbbb", "count="+count);  
		//Log.i("bbbb", "Listdata="+Listdata); 
    }
    
    @Override  
    public void onBackPressed() {     
    	
    	new AlertDialog.Builder(Find.this.getParent()).setTitle("确定要退出么？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();
				System.exit(0);
			}
		}).setNegativeButton("不确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
		}).create().show();
    }  
    
    
    private class BaseListAdapter extends BaseAdapter implements OnClickListener {

        private Context mContext;
        private LayoutInflater inflater;
		private List<HashMap<String, Object>> mData;
        
        public BaseListAdapter(Context mContext) {
            this.mContext = mContext;
            inflater = LayoutInflater.from(mContext);
            
            this.mData = getData();
        }
        
        public void setValues(List<HashMap<String, Object>> listdata) {
			// TODO Auto-generated method stub
			
        	this.mData = listdata;
		}

		@Override
        public int getCount() {
            return this.mData.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if(convertView == null) {
                viewHolder = new ViewHolder();
                convertView = inflater.inflate(R.layout.items, null);
                
                viewHolder.img = (ImageView) convertView.findViewById(R.id.img);
                viewHolder.title = (TextView) convertView.findViewById(R.id.title);
                viewHolder.username = (TextView) convertView.findViewById(R.id.username);
                viewHolder.address = (TextView) convertView.findViewById(R.id.address);
                viewHolder.jiachi = (TextView) convertView.findViewById(R.id.jiachi);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag(); 
            }
            
           
            viewHolder.img.setBackgroundResource(R.drawable.foot_07);
            viewHolder.title.setText((CharSequence) this.mData.get(position).get("title"));
            viewHolder.username.setText((CharSequence) this.mData.get(position).get("username"));
            viewHolder.address.setText((CharSequence) this.mData.get(position).get("address"));
            viewHolder.jiachi.setText((CharSequence) this.mData.get(position).get("jiachi"));
            
            viewHolder.title.setOnClickListener(this);
            
            return convertView;
        }
        
        class ViewHolder {
        	 ImageView img;
			 TextView title;
			 TextView username;
			 TextView address;
			 TextView jiachi;
	
        }

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

            
            //要跳转的Activity  
            Intent intent = new Intent(Find.this, Find_item.class);  
            Bundle bu=new Bundle(); // 这个组件 存值
            bu.putString("username", v.toString());
            intent.putExtras(bu);  //放到 intent 里面  然后 传出去
            // 把Activity转换成一个Window，然后转换成View  
            Window w = FindGroupTab.group.getLocalActivityManager()  
                    .startActivity("Find_item",intent);  
            View view = w.getDecorView();  
            //设置要跳转的Activity显示为本ActivityGroup的内容  
            FindGroupTab.group.setContentView(view); 
		}

   	
   
    }
}
