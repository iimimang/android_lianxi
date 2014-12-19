package com.wyj.tabmenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import android.app.Activity;
import android.app.ActivityGroup;
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

public class FindGroupTab extends ActivityGroup  
{
	
	 //用于管理本Group中的所有Activity       
    public static ActivityGroup group;  
  
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        group = this;  
    }  
  
    @Override  
    public void onBackPressed() {         
        //把后退事件交给子Activity处理  
        group.getLocalActivityManager().getCurrentActivity().onBackPressed();  
    }  
  
    @Override  
    protected void onStart() {  
        super.onStart();  
        //要跳转的Activity  
        Intent intent = new Intent(this, Find.class);  
        // 把Activity转换成一个Window，然后转换成View  
        Window w = group.getLocalActivityManager().startActivity(  
 "Find", intent);  
        View view = w.getDecorView();  
        //设置要跳转的Activity显示为本ActivityGroup的内容  
        group.setContentView(view);  
    } 
		
    
}
