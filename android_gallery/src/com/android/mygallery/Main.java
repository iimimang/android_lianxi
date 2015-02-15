package com.android.mygallery;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class Main extends Activity {
	/** Called when the activity is first created. */
	private Gallery gallery;
	private ImageAdapter imageAdapter;
	// 声明图片的数组
	private int[] resIds = { R.drawable.item1, R.drawable.item2,
			R.drawable.item3, R.drawable.item4, R.drawable.item5,
			R.drawable.item6, R.drawable.item7, R.drawable.item8,
			R.drawable.item9, R.drawable.item10, R.drawable.item11,
			R.drawable.item12, R.drawable.item13, R.drawable.item14,
			R.drawable.item15 };

	// android的适配器
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		gallery = (Gallery) this.findViewById(R.id.gallery);
		imageAdapter = new ImageAdapter(this);
		gallery.setAdapter(imageAdapter);
	}

	public class ImageAdapter extends BaseAdapter {

		private Context context;
		int mGralleyItemBackground;// 使用简单的计数器，填充背景图片

		public ImageAdapter(Context context) {
			this.context = context;
			// 读取属性
			TypedArray typedArray = obtainStyledAttributes(R.styleable.Gallery);
			mGralleyItemBackground = typedArray.getResourceId(
					R.styleable.Gallery_android_galleryItemBackground, 0);

		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return Integer.MAX_VALUE;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return resIds[position];
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			// 自定义的适配器，需要用自定义的布局来显示，通常android的通用布局是不能满足我们的需求
			// 可以手工创建一个View视图，也可以inflate填充一个XML
			// 从数据源中根据position 获得每一个Item的值，填充到指定的XML布局中
			// View convertView 是一个旧的布局，如果没有新的布局填充的时候，将使用旧的布局
			// 当前的布局，会被追加到父布局中
			ImageView imageView = new ImageView(context);
			imageView.setImageResource(resIds[position % resIds.length]);
			imageView.setScaleType(ImageView.ScaleType.FIT_XY);
			imageView.setLayoutParams(new Gallery.LayoutParams(136, 88));
			imageView.setBackgroundResource(mGralleyItemBackground);
			return imageView;
		}

	}
}