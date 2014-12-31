package com.wyj.adapter;

import java.util.List;

import com.wyj.define.templates;
import com.wyj.tabmenu.R;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TemplateAdapter  extends BaseAdapter {
	
	 private List<templates> mList;  
	 private Context mContext;  
	  
	 public TemplateAdapter(Context pContext, List<templates> pList) {  
	        this.mContext = pContext;  
	        this.mList = pList;  
	 } 
	 @Override  
	    public int getCount() {  
	        return mList.size();  
	    }  
	  
	    @Override  
	    public Object getItem(int position) {  
	        return mList.get(position);  
	    }  
	  
	    @Override  
	    public long getItemId(int position) {  
	        return position;  
	    }

	/** 
     * 下面是重要代码 
     */  
    @Override  
  
    
    public View getView(int position, View convertView, ViewGroup parent) {
//        ViewHolder viewHolder = null;
//        if(convertView == null) {
//            viewHolder = new ViewHolder();
//            LayoutInflater _LayoutInflater=LayoutInflater.from(mContext);  
//            convertView=_LayoutInflater.inflate(R.layout.template_items, null); 
//            viewHolder.templename = (TextView) convertView.findViewById(R.id.templename);
//            viewHolder.templeid = (TextView) convertView.findViewById(R.id.templeid);
//            convertView.setTag(viewHolder);
//        } else {
//            viewHolder = (ViewHolder) convertView.getTag(); 
//        }
//        viewHolder.templename.setText(this.mList.get(position).getTemplename());
//        viewHolder.templeid.setText( this.mList.get(position).getTempleid());
    	
    	LayoutInflater _LayoutInflater=LayoutInflater.from(mContext);  
        convertView=_LayoutInflater.inflate(R.layout.template_spinner_items, null); 
    	 if(convertView!=null)  
         {  
    		 TextView  templename = (TextView) convertView.findViewById(R.id.templename);
    		 
    		 templename.setText(mList.get(position).getTemplename()); 
    		// Log.i("aaaa","-------TextView"+templename.toString());
    		// Log.i("aaaa","-------getTempleid"+mList.get(position).getTempleid());
    		 
         }  
      
        return convertView;
    }
    
    class ViewHolder {
   	 	
		 TextView templename;
		 TextView templeid;
		 

   }

}