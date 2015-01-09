package com.wyj.shangxiang;

import java.util.ArrayList;


import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.wyj.Activity.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

public class SinhaImagePaper extends FrameLayout {

	private ViewPager viewPager;
	private ArrayList<String> ListImageUrl;
	private ViewGroup group;
	private LayoutInflater inflater;
	private Context context;
	private ImageView[] imageViews = null;
	private PagerAdapter pagerAdapter;
	private DisplayImageOptions options;
	private ImageLoader imageLoader;
	private int curIndex = 0;
	private Handler handler;

	public SinhaImagePaper(Context context) {
		super(context);
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		this.inflater.inflate(R.layout.image_pager_layout, this, true);
		init();
	}

	public SinhaImagePaper(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		this.inflater.inflate(R.layout.image_pager_layout, this, true);
		init();
	}

	public void setPageImgUrlList(ArrayList<String> pageImgUrl) {
		this.ListImageUrl.clear();
		this.ListImageUrl = pageImgUrl;
		this.pagerAdapter.notifyDataSetChanged();
		addCircle();
	}

	public void init() {
		this.viewPager = (ViewPager) findViewById(R.id.guidePages);
		this.group = (ViewGroup) findViewById(R.id.viewGroup);
		this.ListImageUrl = new ArrayList<String>();
		this.imageLoader = ImageLoader.getInstance();

		this.options = new DisplayImageOptions.Builder()
				.showImageForEmptyUri(R.drawable.ic_launcher)
				.showImageOnFail(R.drawable.ic_launcher)
				.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.displayer(new RoundedBitmapDisplayer(20))
				.displayer(new FadeInBitmapDisplayer(100))
				.build();

		this.pagerAdapter = new ImagePagerAdapter();
		this.viewPager.setAdapter(this.pagerAdapter);
		this.viewPager.setOnPageChangeListener(new GuidePageChangeListener());
		this.handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				viewPager.setCurrentItem(msg.arg1);
			}
		};
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					if (ListImageUrl != null && ListImageUrl.size() > 0) {
						if (curIndex >= ListImageUrl.size()) {
							curIndex = 0;
						}
						Message msg = new Message();
						msg.arg1 = curIndex++;
						try {
							Thread.sleep(3000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						handler.sendMessage(msg);
					}
				}

			}
		}).start();
	}

	public void addCircle() {
		if (this.imageViews != null) {
			this.imageViews = null;
		}
		this.imageViews = new ImageView[this.ListImageUrl.size()];
		ImageView imageView;
		this.group.removeAllViews();
		for (int i = 0; i < this.ListImageUrl.size(); i++) {
			LinearLayout.LayoutParams margin = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			margin.setMargins(10, 0, 0, 0);
			imageView = new ImageView(this.context);
			imageView.setLayoutParams(new LayoutParams(15, 15));
			this.imageViews[i] = imageView;
			if (i == 0) {
				this.imageViews[i].setBackgroundResource(R.drawable.page_indicator_focused);
			} else {
				this.imageViews[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
			}
			this.group.addView(this.imageViews[i], margin);
		}
	}

	private class ImagePagerAdapter extends PagerAdapter {

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			((ViewPager) container).removeView((View) object);
		}

		@Override
		public void finishUpdate(View container) {
		}

		@Override
		public int getCount() {
			return ListImageUrl.size();
		}

		@Override
		public Object instantiateItem(ViewGroup view, int position) {
			View imageLayout = inflater.inflate(R.layout.image_paper_item_layout, view, false);
			final ProgressBar progressBar = (ProgressBar) imageLayout.findViewById(R.id.image_paper_loading);
			ImageView imageView = (ImageView) imageLayout.findViewById(R.id.image_paper_item_view);
			imageLoader.displayImage(ListImageUrl.get(position), imageView, options, new ImageLoadingListener() {

				@Override
				public void onLoadingStarted(String imageUri, View view) {
					progressBar.setVisibility(VISIBLE);
				}

				@Override
				public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
					progressBar.setVisibility(GONE);
				}

				@Override
				public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
					progressBar.setVisibility(GONE);
				}

				@Override
				public void onLoadingCancelled(String imageUri, View view) {
					progressBar.setVisibility(GONE);
				}
			});
			((ViewPager) view).addView(imageLayout, 0);
			return imageLayout;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view.equals(object);
		}

		@Override
		public void restoreState(Parcelable state, ClassLoader loader) {
		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View container) {
		}
	}

	class GuidePageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageSelected(int arg0) {
			for (int i = 0; i < imageViews.length; i++) {
				imageViews[arg0].setBackgroundResource(R.drawable.page_indicator_focused);
				if (arg0 != i) {
					imageViews[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
				}
			}
		}
	}
}
