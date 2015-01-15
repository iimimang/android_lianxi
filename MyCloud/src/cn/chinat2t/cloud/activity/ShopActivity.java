package cn.chinat2t.cloud.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import cn.chinat2t.cloud.MainActivity;
import cn.chinat2t.cloud.R;
import cn.chinat2t.cloud.bean.GalleryBean;
import cn.chinat2t.cloud.bean.HotBusinessBean;
import cn.chinat2t.cloud.bean.LeastProduct;
import cn.chinat2t.cloud.bean.NewsBean;
import cn.chinat2t.cloud.bean.ShopLogo;
import cn.chinat2t.cloud.bean.SortBean;
import cn.chinat2t.cloud.http.BitmapManager;
import cn.chinat2t.cloud.http.CommunicationManager;
import cn.chinat2t.cloud.http.CommunicationResultListener;
import cn.chinat2t.cloud.utils.TextViewEt;
import cn.chinat2t.cloud.utils.Tools;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ShopActivity extends Activity implements OnClickListener, OnItemClickListener{

	//最新企业下边的图片
	private List<ShopLogo> shopLogoList=null;
	private ImageView shopLogo1,shopLogo2,shopLogo3;
	private GridView mNewsGrid,mSortGrid;
	private ListView productListView,hotListView;
	private List<ImageView> pageViews = null;
	private ViewPager mPager = null;
	private ImageView dotImg1,dotImg2,dotImg3,dotImg4;
	
	private ProductAdapter pAdapter = null;
	private HotAdapter hAdapter = null;
	private NewsAdapter nAdapter = null;
	private SortAdapter sAdapter = null;
	private PicPagerAdapter pagerAdapter = null;
	
	private List<NewsBean> newsList = null;
	private List<GalleryBean> gList = null;
	private List<SortBean> sortList = null;
	private List<LeastProduct> productList = null;
	private List<HotBusinessBean> hotBusinessList=null;
	private HotBusinessAdapter hotBusinessApater=null;
	private int pagerPosition = 0;
	private ScheduledExecutorService scheduledExecutorService;
	private TextView colorTextView;
	private static boolean news=false,sort=false,product=false,hot=false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shop_layout);
		
		initViews();
	//	initData();
		refreshPageData();
	}
	
	private void initViews(){
		findViewById(R.id.shop_news_close_layout).setOnClickListener(this);
		findViewById(R.id.shop_news_open_layout).setOnClickListener(this);
		findViewById(R.id.shop_sort_close_layout).setOnClickListener(this);
		findViewById(R.id.shop_sort_open_layout).setOnClickListener(this);
		findViewById(R.id.shop_product_close_layout).setOnClickListener(this);
		findViewById(R.id.shop_product_open_layout).setOnClickListener(this);
		findViewById(R.id.shop_hot_close_layout).setOnClickListener(this);
		findViewById(R.id.shop_hot_open_layout).setOnClickListener(this);
		mNewsGrid = (GridView) findViewById(R.id.shop_news_grid);
		mNewsGrid.setOnItemClickListener(this);
		mSortGrid = (GridView) findViewById(R.id.shop_sort_grid);
		//mSortGrid.setOnItemClickListener(this);
		productListView = (ListView)findViewById(R.id.shop_product_list);
		productListView.setOnItemClickListener(this);
		hotListView = (ListView)findViewById(R.id.shop_hot_list);
		hotListView.setOnItemClickListener(this);
		dotImg1 = (ImageView) findViewById(R.id.shop_dot1);
		dotImg2 = (ImageView) findViewById(R.id.shop_dot2);
		dotImg3 = (ImageView) findViewById(R.id.shop_dot3);
		dotImg4 = (ImageView) findViewById(R.id.shop_dot4);
		shopLogo1=(ImageView) findViewById(R.id.shopLogo1);
		shopLogo2=(ImageView) findViewById(R.id.shopLogo2);
		shopLogo3=(ImageView) findViewById(R.id.shopLogo3);
		mPager  = (ViewPager) findViewById(R.id.shop_gallery_pager);
		mPager.setOnPageChangeListener(pageChanger);
		ImageView iv = null;
		pageViews = new ArrayList<ImageView>();
		for(int i = 0 ; i < 4; i++){
			iv = new ImageView(this);
			iv.setScaleType(ScaleType.FIT_XY);
			pageViews.add(iv);
		}
//		pageViews.get(0).setImageResource(R.drawable.image8);
//		pageViews.get(1).setImageResource(R.drawable.image9);
		pagerAdapter = new PicPagerAdapter();
		mPager.setAdapter(pagerAdapter);
	}
	
//	private void initData(){
//    //	List<HashMap<String, String>> gridList = new ArrayList<HashMap<String, String>>();
//    //	HashMap<String, String> newsMap = null;
//    	newsList = new ArrayList<NewsBean>();
//		
//    	View listitem ;
//    	ViewGroup.LayoutParams linearParams;
////		pAdapter = new ProductAdapter();
////		productListView.setAdapter(pAdapter);
////		listitem = pAdapter.getView(0, null, productListView);
////		listitem.measure(0, 0);
////		linearParams = productListView.getLayoutParams(); // 取控件mGrid当前的布局参数
////		linearParams.height = (listitem.getMeasuredHeight()+3) * 2;
////		productListView.setLayoutParams(linearParams); // 使设置好的布局参数应用到控件mGrid2
//		
//		
//		hAdapter = new HotAdapter();
//		hotListView.setAdapter(hAdapter);
////		listitem = hAdapter.getView(0, null, hotListView);
////		listitem.measure(0, 0);
////		linearParams = hotListView.getLayoutParams(); // 取控件mGrid当前的布局参数
////		linearParams.height = (listitem.getMeasuredHeight()+3) * 2;
////		hotListView.setLayoutParams(linearParams); // 使设置好的布局参数应用到控件mGrid2
//    }
	
	private void refreshPageData(){
		CommunicationManager.getInstance().getGalleryImage(galleryResultListener);
		CommunicationManager.getInstance().getNews(newsResultListener);
		CommunicationManager.getInstance().getShopSort(sortResultListener);
		CommunicationManager.getInstance().getLeastProduct(productResultListener);
		CommunicationManager.getInstance().getHotBusiness(hotBusinessResultListener);
		CommunicationManager.getInstance().getShopLogo(shopLogoResultListener);
	}

	private void refreshNews(List<NewsBean> newsList){
    	if(nAdapter == null){
    		nAdapter = new NewsAdapter();
    		mNewsGrid.setAdapter(nAdapter);
    	}
    	nAdapter.setValues(newsList);
    	if(newsList.size()>0)
    	{
    	   	View listitem = nAdapter.getView(0, null, mNewsGrid);
    		listitem.measure(0, 0);
    		ViewGroup.LayoutParams linearParams = mNewsGrid.getLayoutParams(); // 取控件mGrid当前的布局参数
    		int line = newsList.size() % 2 == 0 ? newsList.size()/2:newsList.size()/2+1;
    		linearParams.height = (listitem.getMeasuredHeight()+3) * line;
    		mNewsGrid.setLayoutParams(linearParams); // 使设置好的布局参数应用到控件mGrid2
    		nAdapter.notifyDataSetChanged();
    	}
 
	}
	
	private void refreshSort(List<SortBean> sBeanList){
		
		if(sAdapter == null){
			sAdapter = new SortAdapter();
			mSortGrid.setAdapter(sAdapter);
		}
		
		sAdapter.setValues(sBeanList);
		Log.i("wyq", sBeanList.size()+"");
		Log.i("wyq", sAdapter.getCount()+"");
		if(sBeanList.size()>0)
		{
			View listitem = sAdapter.getView(0, null, mSortGrid);
			listitem.measure(0, 0);
			ViewGroup.LayoutParams linearParams = mSortGrid.getLayoutParams(); // 取控件mGrid当前的布局参数
			int line = sBeanList.size() % 2 == 0 ? sBeanList.size()/2:sBeanList.size()/2+1;
			linearParams.height = (listitem.getMeasuredHeight()+3) * line;
			mSortGrid.setLayoutParams(linearParams); // 使设置好的布局参数应用到控件mGrid2
		}
		
	}
	
	
	private void refreshHotBusiness(){
		if(hotBusinessApater == null){
			hotBusinessApater = new HotBusinessAdapter();
			hotListView.setAdapter(hotBusinessApater);
		}
		hotBusinessApater.setValues(hotBusinessList);
		hotBusinessApater.notifyDataSetChanged();
		if(hotBusinessList.size()>0)
		{
			View listitem = hotBusinessApater.getView(0, null, hotListView);
			listitem.measure(0, 0);
			ViewGroup.LayoutParams linearParams = hotListView.getLayoutParams(); // 取控件mGrid当前的布局参数
			linearParams.height = (listitem.getMeasuredHeight()+3) * hotBusinessList.size();
			hotListView.setLayoutParams(linearParams); // 使设置好的布局参数应用到控件mGrid2
		}
	
	}
	private void refreshShopLogo(){
		if(shopLogoList.size()==1)
		{
			BitmapManager.getInstance().loadBitmap(shopLogoList.get(0).getLogo(), shopLogo1, Tools.readBitmap(ShopActivity.this, R.drawable.image6));
			shopLogo1.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					MainActivity mainactivity=(MainActivity)getParent();
					(mainactivity.mMoreIntent).putExtra("id", shopLogoList.get(0).getLinkageid());
					if(MoreGroup.getInstance()==null)
					{
						
						mainactivity.setCurrentActivity(4);
						
					}
					else
					{
						
						mainactivity.setCurrentActivity(4);
						//mainactivity.button1.requestFocus();
						Intent intent = new Intent(ShopActivity.this,BrandPicDetailActivity.class);
						intent.putExtra("id", shopLogoList.get(0).getLinkageid());
						MoreGroup.getInstance().switchActivity("BrandPicDetailActivity",intent,-1,-1);
					}
					
				}
			});
		}
		if(shopLogoList.size()==2)
		{
			BitmapManager.getInstance().loadBitmap(shopLogoList.get(0).getLogo(), shopLogo1, Tools.readBitmap(ShopActivity.this, R.drawable.image6));
			shopLogo1.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					MainActivity mainactivity=(MainActivity)getParent();
					(mainactivity.mMoreIntent).putExtra("id", shopLogoList.get(0).getLinkageid());
					if(MoreGroup.getInstance()==null)
					{
						
						mainactivity.setCurrentActivity(4);
						
					}
					else
					{
						
						mainactivity.setCurrentActivity(4);
						//mainactivity.button1.requestFocus();
						Intent intent = new Intent(ShopActivity.this,BrandPicDetailActivity.class);
						intent.putExtra("id", shopLogoList.get(0).getLinkageid());
						MoreGroup.getInstance().switchActivity("BrandPicDetailActivity",intent,-1,-1);
					}
					
				}
			});
			BitmapManager.getInstance().loadBitmap(shopLogoList.get(1).getLogo(), shopLogo2, Tools.readBitmap(ShopActivity.this, R.drawable.image6));
			shopLogo2.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					MainActivity mainactivity=(MainActivity)getParent();
					(mainactivity.mMoreIntent).putExtra("id", shopLogoList.get(1).getLinkageid());
					if(MoreGroup.getInstance()==null)
					{
						
						mainactivity.setCurrentActivity(4);
						
					}
					else
					{
						
						mainactivity.setCurrentActivity(4);
						//mainactivity.button1.requestFocus();
						Intent intent = new Intent(ShopActivity.this,BrandPicDetailActivity.class);
						intent.putExtra("id", shopLogoList.get(1).getLinkageid());
						MoreGroup.getInstance().switchActivity("BrandPicDetailActivity",intent,-1,-1);
					}
					
				}
			});
		}
		if(shopLogoList.size()==3)
		{
			BitmapManager.getInstance().loadBitmap(shopLogoList.get(0).getLogo(), shopLogo1, Tools.readBitmap(ShopActivity.this, R.drawable.image6));
			shopLogo1.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					MainActivity mainactivity=(MainActivity)getParent();
					(mainactivity.mMoreIntent).putExtra("id", shopLogoList.get(0).getLinkageid());
					if(MoreGroup.getInstance()==null)
					{
						
						mainactivity.setCurrentActivity(4);
						
					}
					else
					{
						mainactivity.setCurrentActivity(4);
						//mainactivity.button1.requestFocus();
						Intent intent = new Intent(ShopActivity.this,BrandPicDetailActivity.class);
						intent.putExtra("id", shopLogoList.get(0).getLinkageid());
						MoreGroup.getInstance().switchActivity("BrandPicDetailActivity",intent,-1,-1);
					}
					
				}
			});
			BitmapManager.getInstance().loadBitmap(shopLogoList.get(1).getLogo(), shopLogo2, Tools.readBitmap(ShopActivity.this, R.drawable.image6));
			shopLogo2.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					MainActivity mainactivity=(MainActivity)getParent();
					(mainactivity.mMoreIntent).putExtra("id", shopLogoList.get(1).getLinkageid());
					if(MoreGroup.getInstance()==null)
					{
						
						mainactivity.setCurrentActivity(4);
						
					}
					else
					{
						
						mainactivity.setCurrentActivity(4);
						//mainactivity.button1.requestFocus();
						Intent intent = new Intent(ShopActivity.this,BrandPicDetailActivity.class);
						intent.putExtra("id", shopLogoList.get(1).getLinkageid());
						MoreGroup.getInstance().switchActivity("BrandPicDetailActivity",intent,-1,-1);
					}
					
				}
			});
			BitmapManager.getInstance().loadBitmap(shopLogoList.get(2).getLogo(), shopLogo3, Tools.readBitmap(ShopActivity.this, R.drawable.image6));
			shopLogo3.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					MainActivity mainactivity=(MainActivity)getParent();
					(mainactivity.mMoreIntent).putExtra("id", shopLogoList.get(2).getLinkageid());
					if(MoreGroup.getInstance()==null)
					{
						
						mainactivity.setCurrentActivity(4);
						
					}
					else
					{
						
						mainactivity.setCurrentActivity(4);
						//mainactivity.button1.requestFocus();
						Intent intent = new Intent(ShopActivity.this,BrandPicDetailActivity.class);
						intent.putExtra("id", shopLogoList.get(2).getLinkageid());
						MoreGroup.getInstance().switchActivity("BrandPicDetailActivity",intent,-1,-1);
					}
					
				}
			});
		}
	}
	private void refreshProduct(List<LeastProduct> productList){
		if(pAdapter == null){
			pAdapter = new ProductAdapter();
			productListView.setAdapter(pAdapter);
		}
		pAdapter.setValues(productList);
		pAdapter.notifyDataSetChanged();
		if(productList.size()>0)
		{
			View listitem = pAdapter.getView(0, null, productListView);
			listitem.measure(0, 0);
			ViewGroup.LayoutParams linearParams = productListView.getLayoutParams(); // 取控件mGrid当前的布局参数
			linearParams.height = (listitem.getMeasuredHeight()+3) * productList.size();
			productListView.setLayoutParams(linearParams); // 使设置好的布局参数应用到控件mGrid2
		}

	}
	
	private CommunicationResultListener galleryResultListener = new CommunicationResultListener() {
		public void resultListener(byte result, Object resultData) {
			switch (result) {
			case CommunicationManager.FAIL:
				break;

			case CommunicationManager.SUCCEED:
				break;
			}
			Message msg = mHandler.obtainMessage();
			msg.what = result;
			msg.arg1 = 2;
			msg.obj = resultData;
			mHandler.sendMessage(msg);
		};
	};
	private CommunicationResultListener newsResultListener = new CommunicationResultListener() {
		public void resultListener(byte result, Object resultData) {
			switch (result) {
			case CommunicationManager.FAIL:
				break;
				
			case CommunicationManager.SUCCEED:
				break;
			}
			Message msg = mHandler.obtainMessage();
			msg.what = result;
			msg.arg1 = 1;
			msg.obj = resultData;
			mHandler.sendMessage(msg);
		};
	};
	private CommunicationResultListener sortResultListener = new CommunicationResultListener() {
		public void resultListener(byte result, Object resultData) {
			switch (result) {
			case CommunicationManager.FAIL:
				break;
				
			case CommunicationManager.SUCCEED:
				break;
			}
			Message msg = mHandler.obtainMessage();
			msg.what = result;
			msg.arg1 = 3;
			msg.obj = resultData;
			mHandler.sendMessage(msg);
		};
	};
	private CommunicationResultListener hotBusinessResultListener = new CommunicationResultListener() {
		public void resultListener(byte result, Object resultData) {
			switch (result) {
			case CommunicationManager.FAIL:
				break;
				
			case CommunicationManager.SUCCEED:
				break;
			}
			Message msg = mHandler.obtainMessage();
			msg.what = result;
			msg.arg1 = 101;
			msg.obj = resultData;
			mHandler.sendMessage(msg);
		};
	};
	/**
	 * 
	 * @author wyq
	 */
	private CommunicationResultListener shopLogoResultListener = new CommunicationResultListener() {
		public void resultListener(byte result, Object resultData) {
			switch (result) {
			case CommunicationManager.FAIL:
				break;
				
			case CommunicationManager.SUCCEED:
				break;
			}
			Message msg = mHandler.obtainMessage();
			msg.what = result;
			msg.arg1 = 102;
			msg.obj = resultData;
			mHandler.sendMessage(msg);
		};
	};
	private CommunicationResultListener productResultListener = new CommunicationResultListener() {
		public void resultListener(byte result, Object resultData) {
			switch (result) {
			case CommunicationManager.FAIL:
				break;
				
			case CommunicationManager.SUCCEED:
				break;
			}
			Message msg = mHandler.obtainMessage();
			msg.what = result;
			msg.arg1 = 4;
			msg.obj = resultData;
			mHandler.sendMessage(msg);
		};
	};
	
	Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case CommunicationManager.FAIL:
				
				break;
			case CommunicationManager.SUCCEED:
				if(msg.arg1 == 1){    //新闻资讯
					newsList = (List<NewsBean>) msg.obj;
					if(newsList != null){
						refreshNews(newsList);
					}
				} else if(msg.arg1 == 2){   //幻灯片
					gList = (List<GalleryBean>) msg.obj;
					if(gList != null){
						for(int i = 0 ; i < gList.size();i++){
							GalleryBean ga = gList.get(i);
							BitmapManager.getInstance().loadBitmap(ga.picurl, pageViews.get(i));
						}
					}
				} else if(msg.arg1 == 3){  //分类
					sortList = (List<SortBean>) msg.obj;
					if(sortList != null){
						refreshSort(sortList);
					}
				} else if(msg.arg1 == 4){  //最新产品
					productList = (List<LeastProduct>) msg.obj;
					if(productList != null){
						refreshProduct(productList);
					}
				}else if(msg.arg1 == 101){  //最新企业
					hotBusinessList = (List<HotBusinessBean>) msg.obj;
					if(hotBusinessList != null){
						refreshHotBusiness();
					}
				}else if(msg.arg1 == 102){  //最新企业下边的三张图片
					shopLogoList = (List<ShopLogo>) msg.obj;
					if(shopLogoList != null){
						refreshShopLogo();
					}
				}
				break;
			case 2:
				mPager.setCurrentItem(pagerPosition);
				break;

			default:
				break;
			}
		};
	};
	
	@Override
	protected void onStart() {
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		scheduledExecutorService.scheduleAtFixedRate(new ChangeTask(), 10, 10, TimeUnit.SECONDS); 
		super.onStart();
	};
	
	@Override
	protected void onStop() {
		scheduledExecutorService.shutdown();
		super.onStop();
	}
	
	
    private class ChangeTask implements Runnable { 
 
        public void run() { 
            synchronized (mPager) { 
                pagerPosition = (pagerPosition + 1) % 4; 
                mHandler.sendEmptyMessage(2);
            } 
        } 
 
    } 
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.shop_news_close_layout:
			controlVisible(0);
//			findViewById(R.id.shop_news_close_layout).setVisibility(View.GONE);
//			findViewById(R.id.shop_news_open_layout).setVisibility(View.VISIBLE);
			break;
		case R.id.shop_news_open_layout:
			
			findViewById(R.id.shop_news_close_layout).setVisibility(View.VISIBLE);
			findViewById(R.id.shop_news_open_layout).setVisibility(View.GONE);
			break;
		case R.id.shop_sort_close_layout:
			controlVisible(1);
//			findViewById(R.id.shop_sort_close_layout).setVisibility(View.GONE);
//			findViewById(R.id.shop_sort_open_layout).setVisibility(View.VISIBLE);
			break;
		case R.id.shop_sort_open_layout:
			
			findViewById(R.id.shop_sort_close_layout).setVisibility(View.VISIBLE);
			findViewById(R.id.shop_sort_open_layout).setVisibility(View.GONE);
			break;
		case R.id.shop_product_close_layout:
			controlVisible(2);
//			findViewById(R.id.shop_product_close_layout).setVisibility(View.GONE);
//			findViewById(R.id.shop_product_open_layout).setVisibility(View.VISIBLE);
			break;
		case R.id.shop_product_open_layout:
			findViewById(R.id.shop_product_close_layout).setVisibility(View.VISIBLE);
			findViewById(R.id.shop_product_open_layout).setVisibility(View.GONE);
			break;
		case R.id.shop_hot_close_layout:
			controlVisible(3);
//			findViewById(R.id.shop_hot_close_layout).setVisibility(View.GONE);
//			findViewById(R.id.shop_hot_open_layout).setVisibility(View.VISIBLE);
			break;
		case R.id.shop_hot_open_layout:
			findViewById(R.id.shop_hot_close_layout).setVisibility(View.VISIBLE);
			findViewById(R.id.shop_hot_open_layout).setVisibility(View.GONE);
			break;

		default:
			break;
		}
	}
	private void controlVisible(int flag)
	{
		if(flag==0)
		{
			findViewById(R.id.shop_news_close_layout).setVisibility(View.GONE);
			findViewById(R.id.shop_news_open_layout).setVisibility(View.VISIBLE);
			findViewById(R.id.shop_sort_close_layout).setVisibility(View.VISIBLE);
			findViewById(R.id.shop_sort_open_layout).setVisibility(View.GONE);
			findViewById(R.id.shop_product_close_layout).setVisibility(View.VISIBLE);
			findViewById(R.id.shop_product_open_layout).setVisibility(View.GONE);
			findViewById(R.id.shop_hot_close_layout).setVisibility(View.VISIBLE);
			findViewById(R.id.shop_hot_open_layout).setVisibility(View.GONE);
		}
		if(flag==1)
		{
			findViewById(R.id.shop_news_close_layout).setVisibility(View.VISIBLE);
			findViewById(R.id.shop_news_open_layout).setVisibility(View.GONE);
			findViewById(R.id.shop_sort_close_layout).setVisibility(View.GONE);
			findViewById(R.id.shop_sort_open_layout).setVisibility(View.VISIBLE);
			findViewById(R.id.shop_product_close_layout).setVisibility(View.VISIBLE);
			findViewById(R.id.shop_product_open_layout).setVisibility(View.GONE);
			findViewById(R.id.shop_hot_close_layout).setVisibility(View.VISIBLE);
			findViewById(R.id.shop_hot_open_layout).setVisibility(View.GONE);
		}
		if(flag==2)
		{
			findViewById(R.id.shop_news_close_layout).setVisibility(View.VISIBLE);
			findViewById(R.id.shop_news_open_layout).setVisibility(View.GONE);
			findViewById(R.id.shop_sort_close_layout).setVisibility(View.VISIBLE);
			findViewById(R.id.shop_sort_open_layout).setVisibility(View.GONE);
			findViewById(R.id.shop_product_close_layout).setVisibility(View.GONE);
			findViewById(R.id.shop_product_open_layout).setVisibility(View.VISIBLE);
			findViewById(R.id.shop_hot_close_layout).setVisibility(View.VISIBLE);
			findViewById(R.id.shop_hot_open_layout).setVisibility(View.GONE);
		}
		if(flag==3)
		{
			findViewById(R.id.shop_news_close_layout).setVisibility(View.VISIBLE);
			findViewById(R.id.shop_news_open_layout).setVisibility(View.GONE);
			findViewById(R.id.shop_sort_close_layout).setVisibility(View.VISIBLE);
			findViewById(R.id.shop_sort_open_layout).setVisibility(View.GONE);
			findViewById(R.id.shop_product_close_layout).setVisibility(View.VISIBLE);
			findViewById(R.id.shop_product_open_layout).setVisibility(View.GONE);
			findViewById(R.id.shop_hot_close_layout).setVisibility(View.GONE);
			findViewById(R.id.shop_hot_open_layout).setVisibility(View.VISIBLE);
		}
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			((MainActivity)getParent()).onKeyDown(keyCode, event);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	class ProductAdapter extends BaseAdapter{

		private List<LeastProduct> pList = null;
		private HashMap<Integer, View> viewMap = null;
		
		public void setValues(List<LeastProduct> pList){
			this.pList = pList;
			viewMap = new HashMap<Integer, View>();
		}
		
		@Override
		public int getCount() {
			if(pList == null ) return 0;
			return pList.size();
		}

		@Override
		public Object getItem(int position) {
			return pList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			convertView = viewMap.get(position);
			ViewHolder holder = null;
			if(convertView == null){
				convertView = View.inflate(ShopActivity.this, R.layout.product_grid_item, null);
				holder = new ViewHolder();
				holder.iv = (ImageView) convertView.findViewById(R.id.product_list_image);
				holder.name = (TextView)convertView.findViewById(R.id.product_list_title);
				holder.desc = (TextView)convertView.findViewById(R.id.product_list_desc);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			
			LeastProduct p = pList.get(position);
			holder.name.setText(p.title);
			holder.desc.setText(p.description);
			BitmapManager.getInstance().loadBitmap(p.thumb, holder.iv, Tools.readBitmap(ShopActivity.this, R.drawable.image6));
			
			return convertView;
		}
		
	}
	
	class ViewHolder{
		public TextView name;
		public TextView desc;
		public ImageView iv;
	}
	
	
	class HotAdapter extends BaseAdapter{
		
		@Override
		public int getCount() {
			return hotBusinessList.size();
		}
		
		@Override
		public Object getItem(int position) {
			return position;
		}
		
		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if(convertView == null){
				convertView = View.inflate(ShopActivity.this, R.layout.hot_grid_item, null);
			}
//			if(position == 0){
//				((ImageView)convertView.findViewById(R.id.hot_list_image)).setImageResource(R.drawable.image8);
//			} else if(position == 1){
//				((ImageView)convertView.findViewById(R.id.hot_list_image)).setImageResource(R.drawable.image9);
//			}
			return convertView;
		}
		
	}
	class HotBusinessAdapter extends BaseAdapter{
		List<HotBusinessBean> hotBeanList = null;
		
		public void setValues(List<HotBusinessBean> newsList1){
			this.hotBeanList = newsList1;
		}
		
		@Override
		public int getCount() {
			if(hotBeanList == null)return 0;
			
			return hotBeanList.size();
		}

		@Override
		public Object getItem(int position) {
			return hotBeanList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if(convertView == null){
				convertView = View.inflate(ShopActivity.this, R.layout.hot_grid_item, null);
			}
			((TextView)convertView.findViewById(R.id.hot_list_text)).setText(hotBeanList.get(position).getC_name());
			((TextView)convertView.findViewById(R.id.hot_address)).setText(hotBeanList.get(position).getGsdz());
			((TextView)convertView.findViewById(R.id.hot_tel)).setText(hotBeanList.get(position).getLxdh());
			BitmapManager.getInstance().loadBitmap(hotBeanList.get(position).getLogo(), (ImageView)convertView.findViewById(R.id.hot_list_image), Tools.readBitmap(ShopActivity.this, R.drawable.image6));
			return convertView;
		}
		
	}
	
	class NewsAdapter extends BaseAdapter{
		List<NewsBean> newsList = null;
		
		public void setValues(List<NewsBean> newsList){
			this.newsList = newsList;
		}
		
		@Override
		public int getCount() {
			if(newsList == null)return 0;
			
			return newsList.size();
		}

		@Override
		public Object getItem(int position) {
			return newsList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if(convertView == null){
				convertView = View.inflate(ShopActivity.this, R.layout.news_grid_item, null);
			}
			((TextView)convertView.findViewById(R.id.new_grid_text)).setText(newsList.get(position).title);
			return convertView;
		}
		
	}
	
	class SortAdapter extends BaseAdapter{
		List<SortBean> sList = null;
		
		public void setValues(List<SortBean> sList){
			this.sList = sList;
		}
		
		@Override
		public int getCount() {
			if(sList == null)return 0;
			
			return sList.size();
		}
		
		@Override
		public Object getItem(int position) {
			return sList.get(position);
		}
		
		@Override
		public long getItemId(int position) {
			return position;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if(convertView == null){
				convertView = View.inflate(ShopActivity.this, R.layout.sort_grid_item, null);
			}
			final SortBean sBean = sList.get(position);
			((TextView)convertView.findViewById(R.id.sort_grid_text1)).setText(sBean.name);
			//((TextView)convertView.findViewById(R.id.sort_grid_text1)).setText(sBean.subList.get(0).name);
//			((TextView)convertView.findViewById(R.id.sort_grid_text1)).setFocusable(true);
//			((TextView)convertView.findViewById(R.id.sort_grid_text1)).setFocusableInTouchMode(true);
//			((TextView)convertView.findViewById(R.id.sort_grid_text1)).setClickable(true);
			((TextView)convertView.findViewById(R.id.sort_grid_text1)).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(colorTextView!=null)
					{
						colorTextView.setTextColor(Color.BLACK);
					}
					colorTextView=(TextView)v;
					((TextView)v).setTextColor(Color.RED);
					clickFunction(sBean.linkageid);
				}
			});
			int subNum = sBean.subList.size();
			if(subNum == 1){
				((TextView)convertView.findViewById(R.id.sort_grid_text2)).setText(sBean.subList.get(0).name);
//				((TextView)convertView.findViewById(R.id.sort_grid_text2)).setFocusable(true);
//				((TextView)convertView.findViewById(R.id.sort_grid_text2)).setFocusableInTouchMode(true);
//				((TextView)convertView.findViewById(R.id.sort_grid_text2)).setClickable(true);
				((TextView)convertView.findViewById(R.id.sort_grid_text2)).setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if(colorTextView!=null)
						{
							colorTextView.setTextColor(Color.BLACK);
						}
						colorTextView=(TextView)v;
						((TextView)v).setTextColor(Color.RED);
						//Toast.makeText(ShopActivity.this, "小类ID="+sBean.subList.get(0).linkageid, 2).show();
						clickFunction(sBean.subList.get(0).linkageid);
					}
				});
			} else if(subNum == 2){
				((TextView)convertView.findViewById(R.id.sort_grid_text2)).setText(sBean.subList.get(0).name);
				((TextView)convertView.findViewById(R.id.sort_grid_text3)).setText(sBean.subList.get(1).name);
//				((TextView)convertView.findViewById(R.id.sort_grid_text2)).setFocusable(true);
//				((TextView)convertView.findViewById(R.id.sort_grid_text2)).setFocusableInTouchMode(true);
//				((TextView)convertView.findViewById(R.id.sort_grid_text2)).setClickable(true);
			
				((TextView)convertView.findViewById(R.id.sort_grid_text2)).setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if(colorTextView!=null)
						{
							colorTextView.setTextColor(Color.BLACK);
						}
						colorTextView=(TextView)v;
						((TextView)v).setTextColor(Color.RED);
						clickFunction(sBean.subList.get(0).linkageid);
					}
				});
//				((TextView)convertView.findViewById(R.id.sort_grid_text3)).setFocusable(true);
//				((TextView)convertView.findViewById(R.id.sort_grid_text3)).setFocusableInTouchMode(true);
//				((TextView)convertView.findViewById(R.id.sort_grid_text3)).setClickable(true);
				((TextView)convertView.findViewById(R.id.sort_grid_text3)).setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if(colorTextView!=null)
						{
							colorTextView.setTextColor(Color.BLACK);
						}
						colorTextView=(TextView)v;
						((TextView)v).setTextColor(Color.RED);
						clickFunction(sBean.subList.get(1).linkageid);
					}
				});
			}
			return convertView;
		}
		
	}
	
	
	class PicPagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return pageViews.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public int getItemPosition(Object object) {
			// TODO Auto-generated method stub
			return super.getItemPosition(object);
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			// TODO Auto-generated method stub
			((ViewPager) arg0).removeView(pageViews.get(arg1));
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			// TODO Auto-generated method stub
			((ViewPager) arg0).addView(pageViews.get(arg1));
			return pageViews.get(arg1);
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
			// TODO Auto-generated method stub
		}

		@Override
		public Parcelable saveState() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void startUpdate(View arg0) {
			// TODO Auto-generated method stub
		}

		@Override
		public void finishUpdate(View arg0) {
			// TODO Auto-generated method stub
		}
	}
	
	OnPageChangeListener pageChanger = new OnPageChangeListener() {
		
		@Override
		public void onPageSelected(int arg0) {
			pagerPosition = arg0;
			switch (arg0) {
			case 0:
				dotImg1.setImageResource(R.drawable.point1);
				dotImg2.setImageResource(R.drawable.point2);
				dotImg3.setImageResource(R.drawable.point2);
				dotImg4.setImageResource(R.drawable.point2);
				break;
			case 1:
				dotImg1.setImageResource(R.drawable.point2);
				dotImg2.setImageResource(R.drawable.point1);
				dotImg3.setImageResource(R.drawable.point2);
				dotImg4.setImageResource(R.drawable.point2);
				break;
			case 2:
				dotImg1.setImageResource(R.drawable.point2);
				dotImg2.setImageResource(R.drawable.point2);
				dotImg3.setImageResource(R.drawable.point1);
				dotImg4.setImageResource(R.drawable.point2);
				break;
			case 3:
				dotImg1.setImageResource(R.drawable.point2);
				dotImg2.setImageResource(R.drawable.point2);
				dotImg3.setImageResource(R.drawable.point2);
				dotImg4.setImageResource(R.drawable.point1);
				break;

			default:
				break;
			}
		}
		
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			
		}
		
		@Override
		public void onPageScrollStateChanged(int arg0) {
			
		}
	};
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
//		Intent intent = new Intent(ShopActivity.this,NewsDetailActivity.class);
//		intent.putExtra("id", newsList.get(arg2).id);
//		NewsGroup
//		NewsGroup.getInstance().switchActivity("NewsDetailActivity",intent,-1,-1);
	Log.i("wyq", arg0.getId()+"fffffffffffffff");
		switch (arg0.getId()) {
		case R.id.shop_news_grid:
			MainActivity mainactivity=(MainActivity)getParent();
			(mainactivity.mNewsIntent).putExtra("id", newsList.get(arg2).id);
			if(NewsGroup.getInstance()==null)
			{
				
				mainactivity.setCurrentActivity(1);
				
			}
			else
			{
				
				mainactivity.setCurrentActivity(1);
				mainactivity.button1.requestFocus();
				Intent intent = new Intent(ShopActivity.this,NewsDetailActivity.class);
				intent.putExtra("id", newsList.get(arg2).id);
				NewsGroup.getInstance().switchActivity("NewsDetailActivity",intent,-1,-1);
			}
			break;
		case R.id.shop_product_list:
			MainActivity mainactivity1=(MainActivity)getParent();
			(mainactivity1.mProductInent).putExtra("id", productList.get(arg2).id);
			if(ProductGroup.getInstance()==null)
			{
				mainactivity1.setCurrentActivity(2);
			}
			else
			{
				mainactivity1.setCurrentActivity(2);
				Intent intent = new Intent(ShopActivity.this,ProductDetailActivity.class);
				intent.putExtra("id", productList.get(arg2).id);
				ProductGroup.getInstance().switchActivity("ProductDetailActivity",intent,-1,-1);
			}
			break;
		case R.id.shop_hot_list:
			Intent intent = new Intent(ShopActivity.this,BusinessDetailActivity.class);
			intent.putExtra("id", hotBusinessList.get(arg2).getUserid());
			startActivity(intent);
//			MainActivity mainactivity2=(MainActivity)getParent();
//			(mainactivity2.mBusinessIntent).putExtra("id", hotBusinessList.get(arg2).getUserid());
//			if(BusinessGroup.getInstance()==null)
//			{
//				//mainactivity2.setCurrentActivity(3);
//			}
//			else
//			{
//				Log.i("wyq", "shop_hot_list");
//				mainactivity2.setCurrentActivity(3);
//				Intent intent = new Intent(ShopActivity.this,BusinessDetailActivity.class);
//				intent.putExtra("id", hotBusinessList.get(arg2).getUserid());
//				startActivity(intent);
//				//BusinessGroup.getInstance().switchActivity("BusinessDetailActivity",intent,-1,-1);
//			}
			break;
//		case R.id.shop_sort_grid:
//			MainActivity mainactivity3=(MainActivity)getParent();
//			(mainactivity3.mProductInent).putExtra("pid", sortList.get(arg2).linkageid);
//			Log.i("wyq", "看看分类的id="+sortList.get(arg2).linkageid);
//			if(ProductGroup.getInstance()==null)
//			{
//				mainactivity3.setCurrentActivity(2);
//			}
//			else
//			{
//				mainactivity3.setCurrentActivity(2);
//				Intent intent = new Intent(ShopActivity.this,ProductActivity.class);
//				intent.putExtra("pid", sortList.get(arg2).linkageid);
//				ProductGroup.getInstance().switchActivity("ProductActivity",intent,-1,-1);
//			}
//			break;
		default:
			break;
		}
		
		
	}
	public void clickFunction(String id)
	{
		
		MainActivity mainactivity3=(MainActivity)getParent();
		(mainactivity3.mProductInent).putExtra("pid", id);
		//Log.i("wyq", "看看分类的id="+sortList.get(arg2).linkageid);
		if(ProductGroup.getInstance()==null)
		{
			mainactivity3.setCurrentActivity(2);
		}
		else
		{
			mainactivity3.setCurrentActivity(2);
			Intent intent = new Intent(ShopActivity.this,ProductActivity.class);
			intent.putExtra("pid",id);
			ProductGroup.getInstance().switchActivity("ProductActivity",intent,-1,-1);
		}
		
	}
}
