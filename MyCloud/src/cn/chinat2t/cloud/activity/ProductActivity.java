package cn.chinat2t.cloud.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.chinat2t.cloud.MainActivity;
import cn.chinat2t.cloud.R;
import cn.chinat2t.cloud.bean.HotNewsBean;
import cn.chinat2t.cloud.bean.LeastProduct;
import cn.chinat2t.cloud.bean.SortBean;
import cn.chinat2t.cloud.http.BitmapManager;
import cn.chinat2t.cloud.http.CommunicationManager;
import cn.chinat2t.cloud.http.CommunicationResultListener;
import cn.chinat2t.cloud.utils.Tools;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ProductActivity extends Activity implements OnClickListener, OnScrollListener{

	private Button searchBtn=null;
	private GridView catGrid;
	private ImageView catImage1,catImage2 = null;
	private ListView pListView = null;
	
	private View moreView; //加载更多页面
	private int lastItem;
    private int count;
	static int i=1;
	String pid="";
	
	private SortAdapter sAdapter = null;
	private ProductAdapter pAdapter = null;
	private List<SortBean> partSortList = null;
	private List<SortBean> allSortList = null;
	private List<LeastProduct> productList = null;
	private TextView colorTextView;
	int currentColor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.product_layout);
		
		initViews();
		initData();
	}
	
	private void initViews(){
		i=1;
		pListView = (ListView) findViewById(R.id.product_new_list);
		productList=new ArrayList<LeastProduct>();
		count=productList.size();
		moreView =getLayoutInflater().inflate(R.layout.load, null);
		pListView.addFooterView(moreView);
		searchBtn=(Button)findViewById(R.id.product_search);
		searchBtn.setOnClickListener(this);
		catGrid = (GridView) findViewById(R.id.product_sort_grid);
		//catGrid.setClickable(false);
		catGrid.setFocusable(false);
		
		catGrid.setFocusableInTouchMode(false);
		sAdapter = new SortAdapter();
		catGrid.setAdapter(sAdapter);
		catImage1 = (ImageView) findViewById(R.id.product_sort_btn1);
		catImage1.setOnClickListener(this);
		catImage2 = (ImageView) findViewById(R.id.product_sort_btn2);
		catImage2.setOnClickListener(this);
		
	
		pAdapter = new ProductAdapter();
		pListView.setAdapter(pAdapter);
		pListView.setOnItemClickListener(productItemClick);
		pListView.setOnScrollListener(this);
	}
	
	private void initData(){
		CommunicationManager.getInstance().getPartCat(sortResultListener);
		pid=getIntent().getStringExtra("pid");
		if(pid==null||"".equals(pid))
		{
			CommunicationManager.getInstance().getProductList(i,(String) this.getResources().getText(R.string.pageSize),productResultListener);
			i++;
			count = productList.size();
		}
		else
		{
			CommunicationManager.getInstance().getProductSortList(i,(String) this.getResources().getText(R.string.pageSize),pid,productResultListener);
			i++;
			count = productList.size();
		}
		
	}
	
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
			msg.arg1 = 1;
			msg.obj = resultData;
			mHandler.sendMessage(msg);
		};
	};
	private CommunicationResultListener allResultListener = new CommunicationResultListener() {
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
			msg.arg1 = 3;
			msg.obj = resultData;
			mHandler.sendMessage(msg);
		};
	};
	
	Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case CommunicationManager.FAIL:
				if("0".equals(msg.obj.toString().trim()))
				{
					pAdapter.setValues(null);
					pAdapter.notifyDataSetChanged();
				}
				break;
			case CommunicationManager.SUCCEED:
				if(msg.arg1 == 1){
					partSortList = (List<SortBean>) msg.obj;
					sAdapter.setValues(partSortList);
					sAdapter.notifyDataSetChanged();
				} else if(msg.arg1 == 2){
					allSortList = (List<SortBean>) msg.obj;
					sAdapter.setValues(allSortList);
					sAdapter.notifyDataSetChanged();
				} else if(msg.arg1 == 3){
					
					if("0".equals(msg.obj.toString()))
					{
//						pAdapter.setValues(null);
//						pAdapter.notifyDataSetChanged();
						Toast.makeText(ProductActivity.this, "无最新数据",2).show();
						return;
					}
					List tempList=new ArrayList<LeastProduct>();
					tempList=(List<LeastProduct>) msg.obj;
					productList.addAll(tempList);
					//productList=(List<LeastProduct>) msg.obj;
					pAdapter.setValues(productList);
					pAdapter.notifyDataSetChanged();
					count=productList.size();
				}
				break;

			default:
				break;
			}
		};
	};
	
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
				convertView = View.inflate(ProductActivity.this, R.layout.sort_grid_item, null);
			}
			final SortBean sBean = sList.get(position);
			((TextView)convertView.findViewById(R.id.sort_grid_text1)).setText(sBean.name);
			((TextView)convertView.findViewById(R.id.sort_grid_text1)).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(colorTextView!=null)
					{
						colorTextView.setTextColor(currentColor);
					}
					colorTextView=(TextView)v;
					currentColor=colorTextView.getCurrentTextColor();
					((TextView)v).setTextColor(Color.RED);
					clickFunction(sBean.linkageid);
				}
			});
			int subNum = sBean.subList.size();
			if(subNum == 1){
				((TextView)convertView.findViewById(R.id.sort_grid_text2)).setText(sBean.subList.get(0).name);
				((TextView)convertView.findViewById(R.id.sort_grid_text2)).setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if(colorTextView!=null)
						{
							//if(convertView.get)
							Log.i("wyq", "看看currentColor="+currentColor);
							colorTextView.setTextColor(currentColor);
						}
						colorTextView=(TextView)v;
						currentColor=colorTextView.getCurrentTextColor();
						((TextView)v).setTextColor(Color.RED);
						clickFunction(sBean.subList.get(0).linkageid);
					}
				});
			} else if(subNum == 2){
				((TextView)convertView.findViewById(R.id.sort_grid_text2)).setText(sBean.subList.get(0).name);
				((TextView)convertView.findViewById(R.id.sort_grid_text2)).setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if(colorTextView!=null)
						{
							colorTextView.setTextColor(currentColor);
						}
						colorTextView=(TextView)v;
						currentColor=colorTextView.getCurrentTextColor();
						((TextView)v).setTextColor(Color.RED);
						clickFunction(sBean.subList.get(0).linkageid);
					}
				});
				((TextView)convertView.findViewById(R.id.sort_grid_text3)).setText(sBean.subList.get(1).name);
				((TextView)convertView.findViewById(R.id.sort_grid_text3)).setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if(colorTextView!=null)
						{
							colorTextView.setTextColor(currentColor);
						}
						colorTextView=(TextView)v;
						currentColor=colorTextView.getCurrentTextColor();
						((TextView)v).setTextColor(Color.RED);
						clickFunction(sBean.subList.get(1).linkageid);
					}
				});
			}
			return convertView;
		}
		
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
				convertView = View.inflate(ProductActivity.this, R.layout.product_grid_item, null);
				holder = new ViewHolder();
				holder.iv = (ImageView) convertView.findViewById(R.id.product_list_image);
				holder.name = (TextView)convertView.findViewById(R.id.product_list_title);
				holder.desc = (TextView)convertView.findViewById(R.id.product_list_desc);
				holder.time=(TextView)convertView.findViewById(R.id.product_list_time);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			
			LeastProduct p = pList.get(position);
			holder.name.setText(p.title);
			holder.desc.setText(Html.fromHtml(p.description));
			holder.time.setText(p.time);
			BitmapManager.getInstance().loadBitmap(p.thumb, holder.iv, Tools.readBitmap(ProductActivity.this, R.drawable.image6));
			
			return convertView;
		}
		
	}
	
	class ViewHolder{
		public TextView name;
		public TextView desc;
		public TextView time;
		public ImageView iv;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.product_search:
			Intent intent = new Intent(this,ProductSearchActivity.class);
			ProductGroup.getInstance().switchActivity("ProductSearchActivity",intent,-1,-1);
			break;
		case R.id.product_sort_btn1:
			if(allSortList == null){
				CommunicationManager.getInstance().getAllCat(allResultListener);
			} else {
				sAdapter.setValues(allSortList);
				sAdapter.notifyDataSetChanged();
			}
			catImage1.setVisibility(View.GONE);
			catImage2.setVisibility(View.VISIBLE);
			break;
		case R.id.product_sort_btn2:
			if(partSortList == null){
				CommunicationManager.getInstance().getAllCat(sortResultListener);
			} else {
				sAdapter.setValues(partSortList);
				sAdapter.notifyDataSetChanged();
			}
			catImage2.setVisibility(View.GONE);
			catImage1.setVisibility(View.VISIBLE);
			break;

		default:
			break;
		}
	}
	
	OnItemClickListener productItemClick = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			Intent intent = new Intent(ProductActivity.this,ProductDetailActivity.class);
			intent.putExtra("id", productList.get(arg2).id);
			ProductGroup.getInstance().switchActivity("ProductDetailActivity",intent,-1,-1);
		}
	};
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			ProductGroup.getInstance().onKeyDown(keyCode, event);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	public void clickFunction(String id)
	{
	    pAdapter.setValues(null);
	    pAdapter.notifyDataSetChanged();
		productList.clear();
		pid=id;
		i=1;
		CommunicationManager.getInstance().getProductSortList(i,(String) this.getResources().getText(R.string.pageSize),id,productResultListener);
		i++;
		count = productList.size();
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		lastItem = firstVisibleItem + visibleItemCount - 1;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		if(lastItem == count  && scrollState == this.SCROLL_STATE_IDLE){ 
			//Log.i(TAG, "拉到最底部");
			moreView.setVisibility(view.VISIBLE);
		 
		    mHandler1.sendEmptyMessage(0);
			 
		}
	}
    private void loadMoreData(){ //加载更多数据
     	 count = pAdapter.getCount(); 
     	if(pid==null||"".equals(pid))
		{
			CommunicationManager.getInstance().getProductList(i,(String) this.getResources().getText(R.string.pageSize),productResultListener);
			i++;
			count = productList.size();
		}
		else
		{
			CommunicationManager.getInstance().getProductSortList(i,(String) this.getResources().getText(R.string.pageSize),pid,productResultListener);
			i++;
			count = productList.size();
		}
     }
	private Handler mHandler1 = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
			     
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			   // loadMoreData();  //加载更多数据，这里可以使用异步加载
				//new AsyncTask().execute(params);
//			    pAdapter.setValues(productList);
//			    pAdapter.notifyDataSetChanged();
			    new LongOperation().execute(null);
			   // moreView.setVisibility(View.GONE); 
//			    
//			    if(count > 30){
//			    	Toast.makeText(MainActivity.this, "木有更多数据！", 3000).show();
//			          listView.removeFooterView(moreView); //移除底部视图
//			    }
//				Log.i(TAG, "加载更多数据");
				break;
            case 1:
				
				break;
			default:
				break;
			}
		};
	};
	
	
	private class LongOperation extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			loadMoreData();
			return null;
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			//super.onPostExecute(result);
			 pAdapter.setValues(productList);
			 pAdapter.notifyDataSetChanged();
			 moreView.setVisibility(View.GONE); 
		}

	}
	
	
}
