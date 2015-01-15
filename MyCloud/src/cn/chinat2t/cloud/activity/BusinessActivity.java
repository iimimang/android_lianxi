package cn.chinat2t.cloud.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.chinat2t.cloud.R;
import cn.chinat2t.cloud.bean.BusinessListBean;
import cn.chinat2t.cloud.bean.HotNewsBean;
import cn.chinat2t.cloud.http.BitmapManager;
import cn.chinat2t.cloud.http.CommunicationManager;
import cn.chinat2t.cloud.http.CommunicationResultListener;
import cn.chinat2t.cloud.utils.CtLog;
import cn.chinat2t.cloud.utils.Tools;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
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
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class BusinessActivity extends Activity implements OnClickListener, OnScrollListener {
	private ListView businessListView;
	private List<BusinessListBean> businessList;
	private List<BusinessListBean> businessSearchList;
	private BusinessAdapter businessAdapter;
	private LinearLayout layout;
	private EditText searchEt = null;
	private Button searchBtn = null;
	static boolean isSearch=false;
	String text="";
	private View moreView; //加载更多页面
	private int lastItem;
    private int count;
    private int count1;
	static int i=1;
	static int j=1;
	public List<BusinessListBean> getBusinessList() {
		return businessList;
	}
	public void setBusinessList(List<BusinessListBean> businessList) {
		this.businessList = businessList;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.business_layout);
		initViews();
		initData();
//		String flag=getIntent().getStringExtra("flag");
//		if("1".equals(flag))
//		{
//			searchEt.setFocusable(true);
//			searchEt.setFocusableInTouchMode(true);
//			searchEt.requestFocus();
//			searchEt.requestFocusFromTouch();
//
//		}
//		else
//		{
//			CtLog.d("执行了");
//			layout.setFocusable(true);
//			layout.setFocusableInTouchMode(true);
//			layout.requestFocus();
//			layout.requestFocusFromTouch();
//		}
	}
	 private void loadMoreData(){ //加载更多数据
		 if(!isSearch)
		 {
	      	count = businessAdapter.getCount(); 
	       	CommunicationManager.getInstance().getBusinessList(i, (String) this.getResources().getText(R.string.pageSize), businessResultListener);
	       	i++;
	       	count = businessList.size();
		 }
		 else
		 {
		      	count1 = businessAdapter.getCount(); 
		       	CommunicationManager.getInstance().getSearchBusiness(i, (String) this.getResources().getText(R.string.pageSize),text, searchResultListener);
		       	j++;
		       	count1 = businessSearchList.size();
		 }
      }
		@Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {
			if(keyCode == KeyEvent.KEYCODE_BACK){
				BusinessGroup.getInstance().onKeyDown(keyCode, event);
				return true;
			}
			return super.onKeyDown(keyCode, event);
		}
	private void initData(){
		CommunicationManager.getInstance().getBusinessList(i,(String) this.getResources().getText(R.string.pageSize),businessResultListener);
		i++;
      	count = businessList.size();
	}
	private void initViews(){
		i=1;
		isSearch=false;
		businessList=new ArrayList<BusinessListBean>();
		businessListView = (ListView) findViewById(R.id.business_list);
		moreView =getLayoutInflater().inflate(R.layout.load, null);
		businessListView.addFooterView(moreView);
		businessListView.setOnScrollListener(this);
		searchEt = (EditText) findViewById(R.id.business_search_text);
	searchEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            
            public void onFocusChange(View v, boolean hasFocus) {
            	 InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if(hasFocus){
                            //searchauto.setInputType(InputType.TYPE_NULL);
                             imm.showSoftInputFromInputMethod(searchEt.getWindowToken(), 0);
                    }
                    else{
                            imm.hideSoftInputFromWindow(searchEt.getWindowToken(), 0);
                    }
            }
    });
	searchEt.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
            	Log.i("wyq", keyCode+"键盘被点下");
                    if(keyCode == KeyEvent.KEYCODE_ENTER){//keyCode==KeyEvent.KEYCODE_ENTER
                    	Log.i("wyq", keyCode+"键盘被点下，执行了");
                    	 InputMethodManager    imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    	 imm.hideSoftInputFromWindow(searchEt.getWindowToken(), 0);
                            return true;
                    }
                    return false;
            }
    });
		searchBtn = (Button) findViewById(R.id.business_search_btn);
		searchBtn.setOnClickListener(this);
		layout=(LinearLayout)findViewById(R.id.business_layout);
		
		businessAdapter = new BusinessAdapter();
		businessListView.setAdapter(businessAdapter);
		businessListView.setOnItemClickListener(businessItemClick);
	}
	
	OnItemClickListener businessItemClick = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			Intent intent = new Intent(BusinessActivity.this,BusinessDetailActivity.class);
			if(isSearch)
			{
				intent.putExtra("id", businessSearchList.get(arg2).getUserid());
			}
			else
			intent.putExtra("id", businessList.get(arg2).getUserid());
			startActivity(intent);
			//BusinessGroup.getInstance().switchActivity("BusinessDetailActivity",intent,-1,-1);
			//startActivity(intent);
		}
	};
	private CommunicationResultListener businessResultListener = new CommunicationResultListener() {
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
					if("0".equals(msg.obj.toString().trim()))
					{
						return;
					}
					List tempList=new ArrayList<BusinessListBean>();
					tempList=(List<BusinessListBean>) msg.obj;
					businessList.addAll(tempList);
					//businessList = (List<BusinessListBean>) msg.obj;
					
					businessAdapter.setValues(businessList);
					businessAdapter.notifyDataSetChanged();
					count=businessList.size();
				} 
				break;

			default:
				break;
			}
		};
	};
	
	class BusinessAdapter extends BaseAdapter{

		private List<BusinessListBean> businessList = null;
		private HashMap<Integer, View> viewMap = null;
		
		public void setValues(List<BusinessListBean> bList){
			this.businessList = bList;
			viewMap = new HashMap<Integer, View>();
		}
		
		@Override
		public int getCount() {
			if(businessList == null ) return 0;
			return businessList.size();
		}

		@Override
		public Object getItem(int position) {
			return businessList.get(position);
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
				convertView = View.inflate(BusinessActivity.this, R.layout.business_listitem, null);
				holder = new ViewHolder();
				holder.business_log = (ImageView) convertView.findViewById(R.id.business_list_Logo);
				holder.business_address = (TextView)convertView.findViewById(R.id.business_list_address1);
				holder.business_name = (TextView)convertView.findViewById(R.id.business_list_name);
				holder.business_tel = (TextView)convertView.findViewById(R.id.business_list_tel1);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			
			BusinessListBean p = businessList.get(position);
			holder.business_address.setText(p.getGsdz());
			holder.business_name.setText(p.getC_name());
			holder.business_tel.setText(p.getLxdh());
			BitmapManager.getInstance().loadBitmap(p.getLogo(), holder.business_log, Tools.readBitmap(BusinessActivity.this, R.drawable.image6));
//			holder.business_log
//			holder.name.setText(p.title);
//			holder.desc.setText(p.description);
//			BitmapManager.getInstance().loadBitmap(p.thumb, holder.iv, Tools.readBitmap(ProductActivity.this, R.drawable.image6));
//			
			return convertView;
		}
		class ViewHolder{
			public TextView business_name;
			public TextView business_address;
			public TextView business_tel;
			public ImageView business_log;
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.business_search_btn:
			 InputMethodManager    imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        	 imm.hideSoftInputFromWindow(searchBtn.getWindowToken(), 0);
		    text = searchEt.getText().toString();
			if(text.equals("")){
				//Toast.makeText(this, "请输入搜索内容", Toast.LENGTH_SHORT).show();
				isSearch=false;
				businessAdapter.setValues(businessList);
				businessAdapter.notifyDataSetChanged();
				return;
			}else
			{	
				businessSearchList=new ArrayList<BusinessListBean>();
				j=1;
				CommunicationManager.getInstance().getSearchBusiness(j,(String) this.getResources().getText(R.string.pageSize),text, searchResultListener);
				j++;
				count1=businessSearchList.size();
				isSearch=true;
			}
			
			break;

		default:
			break;
		}
	}

	
	
	private CommunicationResultListener searchResultListener = new CommunicationResultListener() {
		public void resultListener(byte result, Object resultData) {
			switch (result) {
			case CommunicationManager.FAIL:
				break;
				
			case CommunicationManager.SUCCEED:
				break;
			}
			Message msg = SearchmHandler.obtainMessage();
			msg.what = result;
			msg.arg1 = 1;
			msg.obj = resultData;
			SearchmHandler.sendMessage(msg);
		};
	};
	
	Handler SearchmHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case CommunicationManager.FAIL:
				Toast.makeText(BusinessActivity.this, (String)msg.obj, Toast.LENGTH_SHORT).show();
//				mSearchAdapter.setValue(null);
//				mSearchAdapter.notifyDataSetChanged();
				break;
			case CommunicationManager.SUCCEED:
				if(msg.arg1 == 1){
					if("0".equals(msg.obj.toString()))
					{
//						businessAdapter.setValues(null);
//						businessAdapter.notifyDataSetChanged();
//						Toast.makeText(BusinessActivity.this, "未找到符合条件数据", Toast.LENGTH_SHORT).show();
						return;
					}
					List tempList=new ArrayList<BusinessListBean>();
					tempList=(List<BusinessListBean>) msg.obj;
					businessSearchList.addAll(tempList);
					//businessSearchList = (List<BusinessListBean>) msg.obj;
					businessAdapter.setValues(businessSearchList);
					businessAdapter.notifyDataSetChanged();
					count1=businessSearchList.size();
				}
				break;

			default:
				break;
			}
		};
	};

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		lastItem = firstVisibleItem + visibleItemCount - 1;
	}
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		if(!isSearch)
		{
			if(lastItem == count  && scrollState == this.SCROLL_STATE_IDLE){ 
				//Log.i(TAG, "拉到最底部");
				moreView.setVisibility(view.VISIBLE);
			 
			    mHandler1.sendEmptyMessage(0);
				 
			}
		}
		else
		{
			if(lastItem == count1  && scrollState == this.SCROLL_STATE_IDLE){ 
				//Log.i(TAG, "拉到最底部");
				moreView.setVisibility(view.VISIBLE);
			 
			    mHandler1.sendEmptyMessage(0);
				 
			}
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
			    loadMoreData();  //加载更多数据，这里可以使用异步加载
			    if(!isSearch)
			    	businessAdapter.setValues(businessList);
			    else
			    	 businessAdapter.setValues(businessSearchList);
			    businessAdapter.notifyDataSetChanged();
			    
			    moreView.setVisibility(View.GONE); 
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
}
