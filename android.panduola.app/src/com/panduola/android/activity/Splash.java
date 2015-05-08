package com.panduola.android.activity;

import com.panduola.android.PanDuoLa;
import com.panduola.android.R;
import com.panduola.android.services.AutoLoginService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;

import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


public class Splash extends Activity  implements OnClickListener{
	
	/**
	 * ViewPager
	 */
	private ViewPager viewPager;
	
	/**
	 * 
	 */
	private ImageView[] tips;
	
	/**
	 * 
	 * 	 */
	private RelativeLayout[] mImageViews;
	
	/**
	 * 
	 */
	private int[] imgIdArray ;
	
	
	@Override
	public void onCreate(Bundle sinha) {
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(sinha);
		setContentView(R.layout.splash);

		viewPager = (ViewPager) findViewById(R.id.viewPager);
		ViewGroup group = (ViewGroup)findViewById(R.id.viewGroup);	
		imgIdArray=new int[]{R.drawable.splash1, R.drawable.splash2, R.drawable.splash3};
		
		tips = new ImageView[imgIdArray.length];
		
		for(int i=0 ;i<tips.length; i++){
			
			ImageView image= new ImageView(this);
			
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
			20, 20); 
			lp.setMargins(10, 0, 10, 0);
					
			image.setLayoutParams(lp);
			
			 tips[i]=image;
			if(i==0){
				image.setBackgroundResource(R.drawable.page_indicator_focused);
			}else{
				image.setBackgroundResource(R.drawable.page_indicator_unfocused);
			}
			
			group.addView(image);
		}
		
		//mImageViews = new ImageView[imgIdArray.length];
		
		mImageViews = new RelativeLayout[imgIdArray.length];
		for(int i=0; i<mImageViews.length; i++){
			
			RelativeLayout Rela=new RelativeLayout(this);
			RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(
					ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
			Rela.setLayoutParams(param);
			Rela.setBackgroundColor(getResources().getColor(R.color.background_start));
			RelativeLayout.LayoutParams imageparam = new RelativeLayout.LayoutParams(
					ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			imageparam.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
			
			ImageView imageView = new ImageView(this);
			imageView.setLayoutParams(imageparam);
			imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
			imageView.setOnClickListener(this);
			imageView.setId(99);
			imageView.setTag(i);
			imageView.setImageResource(imgIdArray[i]);
			Rela.addView(imageView);
			
			mImageViews[i]=Rela;
		}
		
		viewPager.setAdapter(new SplashPager());
		
		viewPager.setOnPageChangeListener(new SplashOnPageChangeListener());
		
		viewPager.setCurrentItem(0);

		if (!TextUtils.isEmpty(PanDuoLa.APP.getMobile()) && !TextUtils.isEmpty(PanDuoLa.APP.getPassword())) {
			Intent intent = new Intent(PanDuoLa.APP, AutoLoginService.class);
			startService(intent);
		} else {
			PanDuoLa.APP.Logout();
		}
	
	}
	
	public class SplashPager extends PagerAdapter{

		@Override
		public int getCount() {
			return imgIdArray.length;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(View container, int position, Object object) {
			((ViewPager)container).removeView(mImageViews[position % mImageViews.length]);
			
		}

		@Override
		public Object instantiateItem(View container, int position) {
			((ViewPager)container).addView(mImageViews[position % mImageViews.length], 0);
			return mImageViews[position % mImageViews.length];
		}
		
	}

	public class SplashOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

			
		}

		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub
			setImageBackground(arg0 % mImageViews.length);
		}
		
	}
	
	private void setImageBackground(int selectItems){
		for(int i=0; i<tips.length; i++){
			if(i == selectItems){
				tips[i].setBackgroundResource(R.drawable.page_indicator_focused);
			}else{
				tips[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
			}
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId()==99){
			
			if(v.getTag().toString().equals("2")){
				
				Intent intent = new Intent(Splash.this, Navigation.class);
				Splash.this.startActivity(intent);
				Splash.this.finish();
			}
			
		}
	}

//	private void goHome() {
//		new Handler().postDelayed(new Runnable() {
//			@Override
//			public void run() {
//				Intent intent = new Intent(Splash.this, Navigation.class);
//				Splash.this.startActivity(intent);
//				Splash.this.finish();
//			}
//		}, 1000);
//	}
}