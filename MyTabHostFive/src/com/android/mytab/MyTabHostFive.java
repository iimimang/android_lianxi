package com.android.mytab;



import com.android.http_data.BitmapManager;
import com.android.mytab.R;
import com.android.utils.Tools;

import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.TabHost.OnTabChangeListener;

/**
 * <一句话功能简述>定制居底的TabHost<BR>
 * <功能详细描述>
 * 
 * @author chenli
 * @version [版本号, 2011-1-27]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class MyTabHostFive extends TabActivity
{
    /**
     * TabHost控件 
     */
    private TabHost mTabHost;

    /**
     * TabWidget控件
     */
    private TabWidget mTabWidget; 
   

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
    	 
 
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);  
        
       	
        String thumb="http://1008.yunco.net/upload/2013/0627/20130627042134901.jpg";
        
        ImageView shopLogo1=(ImageView) findViewById(R.id.logoaaa);
        // shopLogo1.setImageDrawable(getResources().getDrawable(R.drawable.gggg));
       Bitmap rawBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.gggg); 
         
        BitmapManager.getInstance().loadBitmap(thumb, shopLogo1, rawBitmap);	//异步加载图片
        
        
        mTabHost = this.getTabHost();
        /* 去除标签下方的白线 */
        mTabHost.setPadding(mTabHost.getPaddingLeft(),
        mTabHost.getPaddingTop(), mTabHost.getPaddingRight(),
        mTabHost.getPaddingBottom() - 5); 
        Resources rs = getResources();

        Intent layout1intent = new Intent();
        layout1intent.setClass(this, Layout1.class);
        TabHost.TabSpec layout1spec = mTabHost.newTabSpec("layout1");
        layout1spec.setIndicator("首页",rs.getDrawable(R.drawable.menu11));
        layout1spec.setContent(layout1intent);
        mTabHost.addTab(layout1spec);

        Intent layout2intent = new Intent();
        layout2intent.setClass(this, Layout2.class);
        TabHost.TabSpec layout2spec = mTabHost.newTabSpec("layout2");
        layout2spec.setIndicator("咨询",rs.getDrawable(R.drawable.menu22));
        layout2spec.setContent(layout2intent);
        mTabHost.addTab(layout2spec);

        Intent layout3intent = new Intent();
        layout3intent.setClass(this, Layout3.class); 
        TabHost.TabSpec layout3spec = mTabHost.newTabSpec("layout3");
        layout3spec.setIndicator("线路",rs.getDrawable(R.drawable.menu33));
        layout3spec.setContent(layout3intent);
        mTabHost.addTab(layout3spec);
        
        
        Intent layout4intent = new Intent();
        layout4intent.setClass(this, Layout4.class);
        TabHost.TabSpec layout4spec = mTabHost.newTabSpec("layout4");
        layout4spec.setIndicator("景区",rs.getDrawable(R.drawable.menu44));
        layout4spec.setContent(layout4intent);
        mTabHost.addTab(layout4spec);
        
        Intent layout5intent = new Intent();
        layout5intent.setClass(this, Layout5.class);
        TabHost.TabSpec layout5spec = mTabHost.newTabSpec("layout5");
        layout5spec.setIndicator("更多",rs.getDrawable(R.drawable.menu55));
        layout5spec.setContent(layout5intent);
        mTabHost.addTab(layout5spec);
           
       
        

        /* 对Tab标签的定制 */
        mTabWidget = mTabHost.getTabWidget();
        for (int i = 0; i < mTabWidget.getChildCount(); i++)
        {
            /* 得到每个标签的视图 */
            View view = mTabWidget.getChildAt(i);
        
            
            /* 设置每个标签的背景 */
            if (mTabHost.getCurrentTab() == i)
            {
            	ImageView img=(ImageView)mTabWidget.getChildAt(i).findViewById(
                        android.R.id.icon);
            	img.setImageDrawable(getResources().getDrawable(
                     R.drawable.menu1));
            	 view.setBackgroundDrawable(getResources().getDrawable(
                         R.drawable.fdgg));
                
            }
           else
            {
                view.setBackgroundDrawable(getResources().getDrawable(
                        R.drawable.fdgg));
            }
            /* 设置Tab间分割竖线的颜色 */
            // tabWidget.setBackgroundColor(Color.WHITE);
            /* 设置Tab间分割竖线的背景图片 */
            // tabWidget.setBackgroundResource(R.drawable.icon);
            /* 设置tab的高度 */
        //   mTabWidget.getChildAt(i).getLayoutParams().height = 70;
            TextView tv = (TextView) mTabWidget.getChildAt(i).findViewById(
                    android.R.id.title);
            /* 设置tab内字体的颜色 */
            tv.setTextColor(Color.rgb(255, 255, 255));
           // tv.setPadding(0, 0, 0, 10);
            
            ImageView imf = (ImageView) mTabWidget.getChildAt(i).findViewById(
                    android.R.id.icon);
            /* 设置tab内字体的颜色 */
            imf.setAdjustViewBounds(true);
            imf.setMaxHeight(65);
            imf.setMaxWidth(65);
            imf.setPadding(0, 2, 0, 1);
         
             
            
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) tv.getLayoutParams();  
           // params.topMargin=5;
           // params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 0); //取消文字底边对齐  
          //  params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE); //设置文字居中对齐   
        }
 
        /* 当点击Tab选项卡的时候，更改当前Tab标签的背景 */
        mTabHost.setOnTabChangedListener(new OnTabChangeListener()
        {
            @Override
            public void onTabChanged(String tabId) 
            {
               
            	int aa = mTabHost.getCurrentTab();
            	
            	Log.i("TAG","---"+aa);
          
            	for (int i = 0; i < mTabWidget.getChildCount(); i++)
                {
                if (mTabHost.getCurrentTab() == i)
                    {
                	
                	xuanzecaidan();
                   	switch (i) {
       			 case 0:
       				
       				ImageView img=(ImageView)mTabWidget.getChildAt(i).findViewById(
       	                        android.R.id.icon);
       	            	img.setImageDrawable(getResources().getDrawable(
       	                     R.drawable.menu1));
       	            	break;
       			 case 1:
       				 
       				
       				 ImageView img1=(ImageView)mTabWidget.getChildAt(i).findViewById(
    	                        android.R.id.icon);
    	            	img1.setImageDrawable(getResources().getDrawable(
    	                     R.drawable.menu2));
    	            	break;
       			 case 2:
       				 ImageView img2=(ImageView)mTabWidget.getChildAt(i).findViewById(
    	                        android.R.id.icon);
    	            	img2.setImageDrawable(getResources().getDrawable(
    	                     R.drawable.menu3));
    	            	break;
       			 case 3:
       				 ImageView img3=(ImageView)mTabWidget.getChildAt(i).findViewById(
    	                        android.R.id.icon);
    	            	img3.setImageDrawable(getResources().getDrawable(
    	                     R.drawable.menu4));
    	            	break;
       			 case 4:
       				
       				 ImageView img4=(ImageView)mTabWidget.getChildAt(i).findViewById(
    	                        android.R.id.icon);
    	            	img4.setImageDrawable(getResources().getDrawable(
    	                     R.drawable.menu5));
    	            	break;
       			default:
       				break;
       			}
               	       
                 }else{
                	 	
                 }

                }
            }
        });
    }
    
    public void xuanzecaidan(){
    	
    	for (int i = 0; i< mTabWidget.getChildCount(); i++)
        {
				
    	   	switch (i) {
  			 case 0:
  				
  				ImageView img=(ImageView)mTabWidget.getChildAt(i).findViewById(
  	                        android.R.id.icon);
  	            	img.setImageDrawable(getResources().getDrawable(
  	                     R.drawable.menu11));
  	            	break;
  			 case 1:
  				 
  				
  				 ImageView img1=(ImageView)mTabWidget.getChildAt(i).findViewById(
	                        android.R.id.icon);
	            	img1.setImageDrawable(getResources().getDrawable(
	                     R.drawable.menu22));
	            	break;
  			 case 2:
  				 ImageView img2=(ImageView)mTabWidget.getChildAt(i).findViewById(
	                        android.R.id.icon);
	            	img2.setImageDrawable(getResources().getDrawable(
	                     R.drawable.menu33));
	            	break;
  			 case 3:
  				 ImageView img3=(ImageView)mTabWidget.getChildAt(i).findViewById(
	                        android.R.id.icon);
	            	img3.setImageDrawable(getResources().getDrawable(
	                     R.drawable.menu44));
	            	break;
  			 case 4:
  				
  				 ImageView img4=(ImageView)mTabWidget.getChildAt(i).findViewById(
	                        android.R.id.icon);
	            	img4.setImageDrawable(getResources().getDrawable(
	                     R.drawable.menu55));
	            	break;
  			default:
  				break;
  			}
        }
    	
    }
    
    
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	} 
	

    
    
}
