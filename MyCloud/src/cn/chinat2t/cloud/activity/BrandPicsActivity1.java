package cn.chinat2t.cloud.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.chinat2t.cloud.R;
import cn.chinat2t.cloud.bean.BrandPicsListBean;
import cn.chinat2t.cloud.bean.BusinessListBean;
import cn.chinat2t.cloud.bean.HotNewsBean;
import cn.chinat2t.cloud.bean.LeastProduct;
import cn.chinat2t.cloud.http.BitmapManager;
import cn.chinat2t.cloud.http.CommunicationManager;
import cn.chinat2t.cloud.http.CommunicationResultListener;
import cn.chinat2t.cloud.utils.CtLog;
import cn.chinat2t.cloud.utils.Tools;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class BrandPicsActivity1 extends Activity implements OnItemClickListener, OnScrollListener  {
	private ListView listview =null;
	private List<BrandPicsListBean> brandPicsList;
	private View moreView; //加载更多页面
	private int lastItem;
    private int count;
	static int i=1;
	private Button backBtn=null;
	public List<BrandPicsListBean> getBrandPicsList() {
		return brandPicsList;
	}
	public void setBrandPicsList(List<BrandPicsListBean> brandPicsList) {
		this.brandPicsList = brandPicsList;
	}

	private BrandPicsAdapter brandPicsAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.brand_pics_layout1);
	      //生成动态数组，并且转入数据   
		initViews();
		initData();
	}
	private void initData(){
		CommunicationManager.getInstance().getBrandPicsList1(i,(String) this.getResources().getText(R.string.pageSize),brandPicsResultListener);
		i++;
		count=getCount();
	}
	private int getCount()
	{
		if (brandPicsList.size() % 3 == 0) 
		{ 
			return  brandPicsList.size() / 3; 
	    }else
	    {
	    	return brandPicsList.size() / 3 + 1;
	    }
	}
	private void initViews(){
		backBtn=(Button)findViewById(R.id.brand_back);
		backBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				MoreGroup.getInstance().back();
			}
		});
		i=1;
		brandPicsList=new ArrayList<BrandPicsListBean>();
		
		count=getCount();
		moreView =getLayoutInflater().inflate(R.layout.load, null);
		listview = (ListView) findViewById(R.id.brandlistview); 
		listview.setDividerHeight(0);
		//listview.setOnItemClickListener(this);
//		listview.setFocusable(false);
//		listview.setFocusableInTouchMode(false);
		//listview.setEnabled(false);
		brandPicsAdapter=new BrandPicsAdapter();
		listview.addFooterView(moreView);
		listview.setOnScrollListener(this);
		listview.setAdapter(brandPicsAdapter);
	}
	
	private CommunicationResultListener brandPicsResultListener = new CommunicationResultListener() {
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
	Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case CommunicationManager.FAIL:
				
				break;
			case CommunicationManager.SUCCEED:
				if(msg.arg1 == 1){
					List tempList=new ArrayList<BrandPicsListBean>();
					tempList=(List<BrandPicsListBean>) msg.obj;
					//brandPicsList = (List<BrandPicsListBean>) msg.obj;
					brandPicsList.addAll(tempList);
					brandPicsAdapter.setValues(brandPicsList);
					brandPicsAdapter.notifyDataSetChanged();
					count=getCount();
				} 
				break;

			default:
				break;
			}
		};
	};
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			MoreGroup.getInstance().onKeyDown(keyCode, event);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	class BrandPicsAdapter extends BaseAdapter{

		private List<BrandPicsListBean> brandPicsList1 = null;
		private HashMap<Integer, View> viewMap = null;
		
		public void setValues(List<BrandPicsListBean> bList){
			this.brandPicsList1 = bList;
			viewMap = new HashMap<Integer, View>();
		}
		
		@Override
		public int getCount() {
			if(brandPicsList1==null)
			{
				return 0;
			}else
			{
				if (brandPicsList1.size() % 3 == 0) 
				{ 
					Log.i("wyq", "%3=0执行="+brandPicsList1.size() / 3);
					return brandPicsList1.size() / 3; 
			    }else
			    {
			    	Log.i("wyq", "%3!=0执行="+brandPicsList1.size() / 3+1);
			    	return brandPicsList1.size() / 3 + 1;
			    }
				
			}

		}

		@Override
		public Object getItem(int position) {
			return brandPicsList1.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Log.i("wyq","看看position的值="+position);
			convertView = viewMap.get(position);
			ViewHolder holder = null;
			final int temp=position*3;
			if(convertView == null){
				convertView = View.inflate(BrandPicsActivity1.this, R.layout.brand_pics_item1, null);
				holder = new ViewHolder();
				
				holder.brandImage1 = (ImageView) convertView.findViewById(R.id.brandImage1);
//				holder.brandImage1.setFocusable(true);
//				holder.brandImage1.setFocusableInTouchMode(true);
				if(position*3<brandPicsList1.size())
				{
					
					BrandPicsListBean p = brandPicsList1.get(position*3);
					//holder.brandName.setText(p.getName());
					BitmapManager.getInstance().loadRoundBitmap(p.getLogo(), holder.brandImage1, Tools.readBitmap(BrandPicsActivity1.this, R.drawable.image6),0,0);
					holder.brandImage1.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							//Toast.makeText(BrandPicsActivity1.this,"imageView点击的id="+brandPicsList1.get(temp).getLinkageid()+"", 2).show();
							Intent intent = new Intent(BrandPicsActivity1.this,BrandPicDetailActivity.class);
							intent.putExtra("id", brandPicsList1.get(temp).getLinkageid());
							//startActivity(intent);
							MoreGroup.getInstance().switchActivity("BrandPicDetailActivity",intent,-1,-1);
						}
					});
				}
				
				holder.brandImage2 = (ImageView) convertView.findViewById(R.id.brandImage2);
//				holder.brandImage2.setFocusable(true);
//				holder.brandImage2.setFocusableInTouchMode(true);
				if(position*3+1<brandPicsList1.size())
				{
					BrandPicsListBean p1 = brandPicsList1.get(position*3+1);
					if(p1!=null)
					{
						BitmapManager.getInstance().loadRoundBitmap(p1.getLogo(), holder.brandImage2, Tools.readBitmap(BrandPicsActivity1.this, R.drawable.image6),0,0);
					}
					holder.brandImage2.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							//Toast.makeText(BrandPicsActivity1.this,"imageView点击的id="+brandPicsList1.get(temp+1).getLinkageid()+"", 2).show();
							Intent intent = new Intent(BrandPicsActivity1.this,BrandPicDetailActivity.class);
							intent.putExtra("id", brandPicsList1.get(temp+1).getLinkageid());
							//startActivity(intent);
							MoreGroup.getInstance().switchActivity("BrandPicDetailActivity",intent,-1,-1);
						}
					});
				}
				
				holder.brandImage3 = (ImageView) convertView.findViewById(R.id.brandImage3);
//				holder.brandImage3.setFocusable(true);
//				holder.brandImage3.setFocusableInTouchMode(true);
				if(position*3+2<brandPicsList1.size())
				{
					BrandPicsListBean p2 = brandPicsList1.get(position*3+2);
					if(p2!=null)
					{
						BitmapManager.getInstance().loadRoundBitmap(p2.getLogo(), holder.brandImage3, Tools.readBitmap(BrandPicsActivity1.this, R.drawable.image6),0,0);
					}
					holder.brandImage3.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							//Toast.makeText(BrandPicsActivity1.this,"imageView点击的id="+brandPicsList1.get(temp+2).getLinkageid()+"", 2).show();
							Intent intent = new Intent(BrandPicsActivity1.this,BrandPicDetailActivity.class);
							intent.putExtra("id", brandPicsList1.get(temp+2).getLinkageid());
							//startActivity(intent);
							MoreGroup.getInstance().switchActivity("BrandPicDetailActivity",intent,-1,-1);
						}
					});
				}

				//holder.brandName = (TextView)convertView.findViewById(R.id.brandName);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}



			return convertView;
	
		//	holder.business_log
//			holder.name.setText(p.title);
//			holder.desc.setText(p.description);
//			BitmapManager.getInstance().loadBitmap(p.thumb, holder.iv, Tools.readBitmap(Activity.this, R.drawable.image6));
//			
		}
		class ViewHolder{
			//public TextView brandName;
			public ImageView brandImage1;
			public ImageView brandImage2;
			public ImageView brandImage3;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(BrandPicsActivity1.this,BrandPicDetailActivity.class);
		intent.putExtra("id", brandPicsList.get(arg2).getLinkageid());
		//startActivity(intent);
		MoreGroup.getInstance().switchActivity("BrandPicDetailActivity",intent,-1,-1);
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
	     	 count = brandPicsAdapter.getCount(); 
	     	CommunicationManager.getInstance().getBrandPicsList1(i,(String) this.getResources().getText(R.string.pageSize),brandPicsResultListener);
			i++;
			count = getCount();
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
				    loadMoreData();  //加载更多数据，这里可以使用异步加载
				    brandPicsAdapter.setValues(brandPicsList);
				    brandPicsAdapter.notifyDataSetChanged();
				    
				    moreView.setVisibility(View.GONE); 
//				    
//				    if(count > 30){
//				    	Toast.makeText(MainActivity.this, "木有更多数据！", 3000).show();
//				          listView.removeFooterView(moreView); //移除底部视图
//				    }
//					Log.i(TAG, "加载更多数据");
					break;
	            case 1:
					
					break;
				default:
					break;
				}
			};
		};
	
}
