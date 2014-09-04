package com.example.panduola;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 
 * @author shangzhenxiang
 *
 */
public class Textlist extends Activity {

    private ListView mListView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.baseadapterlist);
        mListView = (ListView) findViewById(R.id.xianlu2);
        mListView.setAdapter(new BaseListAdapter(this));
    }
    
    private List<HashMap<String, Object>> getData() {
        List<HashMap<String, Object>> maps = new ArrayList<HashMap<String,Object>>();
        HashMap<String, Object> map = new HashMap<String, Object>();

        map.put("title", "日本进口2H2D延时喷剂 男性助勃");
		map.put("img", R.drawable.ddd);
		map.put("jiage", "198.0");
		map.put("old_jiage", "237");
		map.put("xiaoliang", "1025");
		maps.add(map);
		
		map=new HashMap<String, Object>();
		map.put("title", "百乐大力神杯 7频震动 男用阴交自慰器");
		map.put("img", R.drawable.ccc);
		map.put("jiage", "78.0");
		map.put("old_jiage", "165");
		map.put("xiaoliang", "100");
		maps.add(map);
		
		map=new HashMap<String, Object>();
		map.put("title", "早泄克星 黑寡妇煞星 达克罗宁软膏");
		map.put("img", R.drawable.eee);
		map.put("jiage", "208.0");
		map.put("old_jiage", "447");
		map.put("xiaoliang", "145");
		maps.add(map);
		
        return maps;
        
        
    }
    
    private class BaseListAdapter extends BaseAdapter implements OnClickListener {

        private Context mContext;
        private LayoutInflater inflater;
        
        public BaseListAdapter(Context mContext) {
            this.mContext = mContext;
            inflater = LayoutInflater.from(mContext);
        }
        
        @Override
        public int getCount() {
            return getData().size();
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
                System.out.println("viewHolder111111111111 = " +  (TextView)findViewById(R.id.title));
                viewHolder.img = (ImageView) convertView.findViewById(R.id.img);
                viewHolder.title = (TextView) convertView.findViewById(R.id.title);
               
                viewHolder.sale_btn = (Button) convertView.findViewById(R.id.sale_btn);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            
            System.out.println("viewHolder = " + viewHolder);
            viewHolder.img.setBackgroundResource((Integer) getData().get(position).get("img"));
            viewHolder.title.setText((CharSequence) getData().get(position).get("title"));
           
            viewHolder.sale_btn.setOnClickListener(this);
            
            return convertView;
        }
        
        class ViewHolder {
        	 ImageView img;
			 TextView title;
			 TextView jiage;
			 TextView old_jiage;
			 TextView xiaoliang;
			 Button sale_btn;
        }

        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch(id) {
            case R.id.basebutton:
                showInfo();
                break;
            }
        }

        private void showInfo() {
            new AlertDialog.Builder(Textlist.this).setTitle("my listview").setMessage("introduce....").
            setPositiveButton("OK", new DialogInterface.OnClickListener() {
                
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub
                    
                }
            }).show();
        }
    }
}