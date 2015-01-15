package cn.chinat2t.cloud.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import cn.chinat2t.cloud.R;
import cn.chinat2t.cloud.activity.ProductActivity.ViewHolder;
import cn.chinat2t.cloud.bean.HotNewsBean;
import cn.chinat2t.cloud.bean.LeastProduct;
import cn.chinat2t.cloud.bean.ProductBean;
import cn.chinat2t.cloud.http.BitmapManager;
import cn.chinat2t.cloud.http.CommunicationManager;
import cn.chinat2t.cloud.http.CommunicationResultListener;
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
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ProductSearchActivity extends Activity implements OnClickListener, OnScrollListener, OnItemClickListener{

	private EditText searchEt = null;
	private Button searchBtn = null;
	private ListView sListView = null;
	String text="";
	private List<LeastProduct> searchList = null;
	private ProductAdapter mSearchAdapter = null;
	//private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	
	private View moreView; //加载更多页面
	private int lastItem;
    private int count;
	static int i=1;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.product_search_layout);
		
		initViews();
		initData();
	}
	
	private void initViews(){
		searchEt = (EditText) findViewById(R.id.product_search_text);
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
		searchBtn = (Button) findViewById(R.id.product_search_btn);
		searchBtn.setOnClickListener(this);
		sListView = (ListView)findViewById(R.id.product_search_list);
		sListView.setOnItemClickListener(this);
		moreView =getLayoutInflater().inflate(R.layout.load, null);
		sListView.addFooterView(moreView);
		sListView.setOnScrollListener(this);
		mSearchAdapter = new ProductAdapter();
		sListView.setAdapter(mSearchAdapter);
	}
	
	private void initData(){
		
	}
    private void loadMoreData(){ //加载更多数据
      	 count = mSearchAdapter.getCount(); 
      	CommunicationManager.getInstance().getSearchProduct(i,(String) this.getResources().getText(R.string.pageSize),text, resultListener);
      	i++;
      	count = searchList.size();
      }
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			ProductGroup.getInstance().back();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.product_search_btn:
			 InputMethodManager    imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        	 imm.hideSoftInputFromWindow(searchBtn.getWindowToken(), 0);
			searchList=new ArrayList<LeastProduct>();
			i=1;
			count=searchList.size();
		   text = searchEt.getText().toString();
			if(text.equals("")){
				Toast.makeText(this, "请输入搜索内容", Toast.LENGTH_SHORT).show();
				return;
			}
			CommunicationManager.getInstance().getSearchProduct(i,(String) this.getResources().getText(R.string.pageSize),text, resultListener);
			i++;
			break;

		default:
			break;
		}
	}
	
	private CommunicationResultListener resultListener = new CommunicationResultListener() {
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
				Toast.makeText(ProductSearchActivity.this, (String)msg.obj, Toast.LENGTH_SHORT).show();
//				mSearchAdapter.setValues(null);
//				mSearchAdapter.notifyDataSetChanged();
				break;
			case CommunicationManager.SUCCEED:
				if(msg.arg1 == 1){
					if(msg.obj.toString().trim().equals("0"))
					{
						Toast.makeText(ProductSearchActivity.this, "无最新数据", Toast.LENGTH_SHORT).show();
						return;
					}
					List<LeastProduct> tempList=(List<LeastProduct>) msg.obj;
					searchList.addAll(tempList);
					mSearchAdapter.setValues(searchList);
					mSearchAdapter.notifyDataSetChanged();
					count=searchList.size();
				}
				break;

			default:
				break;
			}
		};
	};
	
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
				convertView = View.inflate(ProductSearchActivity.this, R.layout.product_grid_item, null);
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
			BitmapManager.getInstance().loadBitmap(p.thumb, holder.iv, Tools.readBitmap(ProductSearchActivity.this, R.drawable.image6));
			
			return convertView;
		}
		
	}
	
	class ViewHolder{
		public TextView name;
		public TextView desc;
		public ImageView iv;
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
			    mSearchAdapter.setValues(searchList);
			    mSearchAdapter.notifyDataSetChanged();
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



	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(ProductSearchActivity.this,ProductDetailActivity.class);
		intent.putExtra("id", searchList.get(arg2).id);
		ProductGroup.getInstance().switchActivity("ProductDetailActivity",intent,-1,-1);
	}
}
