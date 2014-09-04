package com.flysnow.sina.adapter;


import com.flysnow.sina.weibo.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class myImageAdapter extends BaseAdapter {
	
	/*变量声明*/
	int mGalleryItemBackground;
	private Context context;//上下文
	public Integer[] myImageIds = { R.drawable.ad1,R.drawable.banner,R.drawable.ph_1};
	
	
	public myImageAdapter(Context context) {
		// TODO Auto-generated constructor stub
		this.context=context;
		/*
		* 使用在res/values/attrs.xml 中的<declare-styleable>定义的Gallery 属性.
		*/
		TypedArray typed_array=context.obtainStyledAttributes(R.styleable.Gallery);
		/* 取得Gallery 属性的Index id */
		mGalleryItemBackground=typed_array.getResourceId(R.styleable.Gallery_android_galleryItemBackground , 0);
		/* 让对象的styleable 属性能够反复使用*/
		typed_array.recycle();
		} 
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return myImageIds.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ImageView imageview=new ImageView(context);
		imageview.setBackgroundResource(myImageIds[position]);
		/* 重新设置图片的宽高*/
		imageview.setScaleType(ImageView.ScaleType.FIT_XY);
		/* 重新设置Layout 的宽高*/
		imageview.setLayoutParams(new Gallery.LayoutParams(128, 128));
		/* 设置Gallery 背景图*/
		//imageview.setBackgroundResource(mGalleryItemBackground);
		/* 返回imageView 对象*/
		return imageview;
	}

	
	
	
}
