package com.wyj.Activity;

import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import com.wyj.pipe.Cms;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

public class Imageviewpager extends Activity implements OnClickListener{
	/**
	 * ViewPager
	 */
	private ViewPager viewPager;
	
	/**
	 * 装点点的ImageView数组
	 */
	private ImageView[] tips;
	
	/**
	 * 装ImageView数组
	 */
	private ImageView[] mImageViews;
	
	/**
	 * 图片资源id
	 */
	private String[] imgIdArray ;
	
	private ProgressBar loading;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.imageviewpager);
		ViewGroup group = (ViewGroup)findViewById(R.id.OrderviewGroup);
		viewPager = (ViewPager) findViewById(R.id.OrderviewPager);
		
		loading =(ProgressBar) findViewById(R.id.OrderImageloading);
		
		Bundle bundle = this.getIntent().getExtras();
		imgIdArray=bundle.getStringArray("thumbs");
		
		if(null != bundle && imgIdArray!=null){
			
			Log.i("aaaa", "-------viewpager"+imgIdArray[0]);
			//将点点加入到ViewGroup中
			tips = new ImageView[imgIdArray.length];
			for(int i=0; i<tips.length; i++){
				ImageView imageView = new ImageView(this);
		    	imageView.setLayoutParams(new LayoutParams(20,20));
		    	imageView.setPadding(0, 0, 10, 0);
		    	tips[i] = imageView;
		    	if(i == 0){
		    		tips[i].setBackgroundResource(R.drawable.page_indicator_focused);
		    	}else{
		    		tips[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
		    	}
		    	
		    	 group.addView(imageView);
			}
			
			
			//设置Adapter
			viewPager.setAdapter(new MyAdapter());
			//设置监听，主要是设置点点的背景
			viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
			//设置ViewPager的默认项, 设置为长度的100倍，这样子开始就能往左滑动
			//viewPager.setCurrentItem((imgIdArray.length) * 100);
			
			viewPager.setOffscreenPageLimit(imgIdArray.length);
		}		
	}
	
	/**
	 * 
	 * @author xiaanming
	 *
	 */
	public class MyAdapter extends PagerAdapter{

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
			//((ViewPager)container).removeView(mImageViews[position % mImageViews.length]);
			((ViewPager) container).removeView((ImageView) object);
			
		}

		/**
		 * 载入图片进去，用当前的position 除以 图片数组长度取余数是关键
		 */
		@Override
		public Object instantiateItem(View container, int position) {
			String path = imgIdArray[position];
			
			Log.i("aaaa", "-------viewpager位置----"+position);
			ImageView thumb = new ImageView(Imageviewpager.this);
			thumb.setLayoutParams(new LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
			thumb.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
			
			thumb.setId(9);
			thumb.setOnClickListener(Imageviewpager.this);
			Cms.imageLoader.displayImage(path, thumb, Cms.imageLoaderOptions, new ImageLoadingListener() {
				@Override
				public void onLoadingStarted(String imageUri, View view) {
					loading.setVisibility(View.VISIBLE);
				}

				@Override
				public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
					loading.setVisibility(View.GONE);
				}

				@Override
				public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
					loading.setVisibility(View.GONE);
				}

				@Override
				public void onLoadingCancelled(String imageUri, View view) {
					loading.setVisibility(View.GONE);
				}
			});
			((ViewPager) container).addView(thumb, position);
			return thumb;
		}		
	}

	
	public class MyOnPageChangeListener implements OnPageChangeListener {
		@Override
		public void onPageSelected(int position) {
			setImageBackground(position % imgIdArray.length);
		}

		@Override
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	}
	
	/**
	 * 设置选中的tip的背景
	 * @param selectItems
	 */
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
		int sender = v.getId();
		
		Log.i("aaaa", "-------viewpager点击---"+sender);
		if (9 == sender) {
			this.finish();
		}
	}

}
