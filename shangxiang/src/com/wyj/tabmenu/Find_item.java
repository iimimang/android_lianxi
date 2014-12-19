package com.wyj.tabmenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Find_item extends Activity implements OnClickListener
{
	
	private ListView mListView;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.find_item);
		
		ImageView back=(ImageView) findViewById(R.id.back);
		back.setOnClickListener(this);
		
		mListView = (ListView) findViewById(R.id.find_list_item);
		mListView.setAdapter(new FindListAdapter(this));
	}
	
	public void onClick(View v) {
		
		Intent intent = new Intent(Find_item.this, Find.class);  

        // 把Activity转换成一个Window，然后转换成View  
        Window w = FindGroupTab.group.getLocalActivityManager()  
                .startActivity("Find",intent);  
        View view = w.getDecorView();  
        //设置要跳转的Activity显示为本ActivityGroup的内容  
        FindGroupTab.group.setContentView(view); 
		
	}
	
    @Override  
    public void onBackPressed() {     
    	
    	 //要跳转的Activity  
        Intent intent = new Intent(Find_item.this, Find.class);  
         // 把Activity转换成一个Window，然后转换成View  
        Window w = FindGroupTab.group.getLocalActivityManager()  
                .startActivity("Find",intent);  
        View view = w.getDecorView();  
        //设置要跳转的Activity显示为本ActivityGroup的内容  
        FindGroupTab.group.setContentView(view); 
    }  
    
	

	

    private List<HashMap<String, Object>>  getData() {
        List<HashMap<String, Object>> maps = new ArrayList<HashMap<String,Object>>();
        HashMap<String, Object> map = new HashMap<String, Object>();

		for(int i = 0;i<10;i++){
			map=new HashMap<String, Object>();
			map.put("username", "寂静的风");
			map.put("img", R.drawable.foot_07);
			
			map.put("jiachi", "刚刚加持祈福");
			maps.add(map);
		}
		
		return maps;

    }
    
    private class FindListAdapter extends BaseAdapter  {

        private Context mContext;
        private LayoutInflater inflater;
		private List<HashMap<String, Object>> mData;
        
        public FindListAdapter(Context mContext) {
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
                convertView = inflater.inflate(R.layout.find_list_items, null);
                
                viewHolder.img = (ImageView) convertView.findViewById(R.id.img);
              
                viewHolder.username = (TextView) convertView.findViewById(R.id.username);
                viewHolder.jiachi = (TextView) convertView.findViewById(R.id.jiachi);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag(); 
            }
            
           
            viewHolder.img.setBackgroundResource(R.drawable.foot_07);
           
            viewHolder.username.setText((CharSequence) this.mData.get(position).get("username"));
            viewHolder.jiachi.setText((CharSequence) this.mData.get(position).get("jiachi"));

            return convertView;
        }
        
        class ViewHolder {
        	 ImageView img;
			 TextView title;
			 TextView username;
			 TextView jiachi;
	
        } 	
   
    }
	

	             
	
	
}
