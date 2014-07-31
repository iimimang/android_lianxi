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
 * <һ�仰���ܼ���>���ƾӵ׵�TabHost<BR>
 * <������ϸ����>
 * 
 * @author chenli
 * @version [�汾��, 2011-1-27]
 * @see [�����/����]
 * @since [��Ʒ/ģ��汾]
 */
public class MyTabHostFive extends TabActivity
{
    /**
     * TabHost�ؼ� 
     */
    private TabHost mTabHost;

    /**
     * TabWidget�ؼ�
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
         
        BitmapManager.getInstance().loadBitmap(thumb, shopLogo1, rawBitmap);	//�첽����ͼƬ
        
        
        mTabHost = this.getTabHost();
        /* ȥ����ǩ�·��İ��� */
        mTabHost.setPadding(mTabHost.getPaddingLeft(),
        mTabHost.getPaddingTop(), mTabHost.getPaddingRight(),
        mTabHost.getPaddingBottom() - 5); 
        Resources rs = getResources();

        Intent layout1intent = new Intent();
        layout1intent.setClass(this, Layout1.class);
        TabHost.TabSpec layout1spec = mTabHost.newTabSpec("layout1");
        layout1spec.setIndicator("��ҳ",rs.getDrawable(R.drawable.menu11));
        layout1spec.setContent(layout1intent);
        mTabHost.addTab(layout1spec);

        Intent layout2intent = new Intent();
        layout2intent.setClass(this, Layout2.class);
        TabHost.TabSpec layout2spec = mTabHost.newTabSpec("layout2");
        layout2spec.setIndicator("��ѯ",rs.getDrawable(R.drawable.menu22));
        layout2spec.setContent(layout2intent);
        mTabHost.addTab(layout2spec);

        Intent layout3intent = new Intent();
        layout3intent.setClass(this, Layout3.class); 
        TabHost.TabSpec layout3spec = mTabHost.newTabSpec("layout3");
        layout3spec.setIndicator("��·",rs.getDrawable(R.drawable.menu33));
        layout3spec.setContent(layout3intent);
        mTabHost.addTab(layout3spec);
        
        
        Intent layout4intent = new Intent();
        layout4intent.setClass(this, Layout4.class);
        TabHost.TabSpec layout4spec = mTabHost.newTabSpec("layout4");
        layout4spec.setIndicator("����",rs.getDrawable(R.drawable.menu44));
        layout4spec.setContent(layout4intent);
        mTabHost.addTab(layout4spec);
        
        Intent layout5intent = new Intent();
        layout5intent.setClass(this, Layout5.class);
        TabHost.TabSpec layout5spec = mTabHost.newTabSpec("layout5");
        layout5spec.setIndicator("����",rs.getDrawable(R.drawable.menu55));
        layout5spec.setContent(layout5intent);
        mTabHost.addTab(layout5spec);
           
       
        

        /* ��Tab��ǩ�Ķ��� */
        mTabWidget = mTabHost.getTabWidget();
        for (int i = 0; i < mTabWidget.getChildCount(); i++)
        {
            /* �õ�ÿ����ǩ����ͼ */
            View view = mTabWidget.getChildAt(i);
        
            
            /* ����ÿ����ǩ�ı��� */
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
            /* ����Tab��ָ����ߵ���ɫ */
            // tabWidget.setBackgroundColor(Color.WHITE);
            /* ����Tab��ָ����ߵı���ͼƬ */
            // tabWidget.setBackgroundResource(R.drawable.icon);
            /* ����tab�ĸ߶� */
        //   mTabWidget.getChildAt(i).getLayoutParams().height = 70;
            TextView tv = (TextView) mTabWidget.getChildAt(i).findViewById(
                    android.R.id.title);
            /* ����tab���������ɫ */
            tv.setTextColor(Color.rgb(255, 255, 255));
           // tv.setPadding(0, 0, 0, 10);
            
            ImageView imf = (ImageView) mTabWidget.getChildAt(i).findViewById(
                    android.R.id.icon);
            /* ����tab���������ɫ */
            imf.setAdjustViewBounds(true);
            imf.setMaxHeight(65);
            imf.setMaxWidth(65);
            imf.setPadding(0, 2, 0, 1);
         
             
            
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) tv.getLayoutParams();  
           // params.topMargin=5;
           // params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 0); //ȡ�����ֵױ߶���  
          //  params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE); //�������־��ж���   
        }
 
        /* �����Tabѡ���ʱ�򣬸��ĵ�ǰTab��ǩ�ı��� */
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
