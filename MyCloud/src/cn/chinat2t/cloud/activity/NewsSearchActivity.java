package cn.chinat2t.cloud.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import cn.chinat2t.cloud.R;
import cn.chinat2t.cloud.bean.HotNewsBean;
import cn.chinat2t.cloud.http.CommunicationManager;
import cn.chinat2t.cloud.http.CommunicationResultListener;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class NewsSearchActivity extends Activity implements OnClickListener, OnScrollListener, OnItemClickListener{
	static int i=1;
	private EditText searchEt = null;
	private Button searchBtn = null;
	private ListView sListView = null;
	
	private List<HotNewsBean> searchList = null;
	private SearchListAdapter mSearchAdapter = null;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private String text;
	
	private View moreView; //加载更多页面
	private int lastItem;
    private int count;
    
	//private List<HotNewsBean> addList = null;
	private List<HotNewsBean> allList = new ArrayList<HotNewsBean>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.news_search_layout);
		
		initViews();
		initData();
	}
	
	private void initViews(){
		sListView = (ListView)findViewById(R.id.news_search_list);
		moreView =getLayoutInflater().inflate(R.layout.load, null);
		
		sListView.addFooterView(moreView); //添加底部view，一定要在setAdapter之前添加，否则会报错。
		sListView.setOnItemClickListener(this);
		searchEt = (EditText) findViewById(R.id.new_search_text);
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
		searchBtn = (Button) findViewById(R.id.new_search_btn);
		searchBtn.setOnClickListener(this);
		
		mSearchAdapter = new SearchListAdapter();
		sListView.setAdapter(mSearchAdapter);
		sListView.setOnScrollListener(this);
		
	}
	
    private void loadMoreData(){ //加载更多数据
   	 count = mSearchAdapter.getCount(); 
   	CommunicationManager.getInstance().getSearchNews(i,(String) this.getResources().getText(R.string.pageSize),text, resultListener);
   	i++;
   	count = searchList.size();
   }
	

	private void initData(){
		
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			NewsGroup.getInstance().back();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.new_search_btn:
			 InputMethodManager    imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        	 imm.hideSoftInputFromWindow(searchBtn.getWindowToken(), 0);
			searchList=new ArrayList<HotNewsBean>();
			i=1;
			count=searchList.size();
		   text = searchEt.getText().toString();
			if(text.equals("")){
				Toast.makeText(this, "请输入搜索内容", Toast.LENGTH_SHORT).show();
				return;
			}
			CommunicationManager.getInstance().getSearchNews(i,(String) this.getResources().getText(R.string.pageSize),text, resultListener);
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
				Toast.makeText(NewsSearchActivity.this, (String)msg.obj, Toast.LENGTH_SHORT).show();
//				
//				mSearchAdapter.setValue(null);
//				mSearchAdapter.notifyDataSetChanged();
				break;
			case CommunicationManager.SUCCEED:
				if(msg.arg1 == 1){
					List<HotNewsBean> tempList=(List<HotNewsBean>) msg.obj;
					searchList.addAll(tempList);
					mSearchAdapter.setValue(searchList);
					mSearchAdapter.notifyDataSetChanged();
					count=searchList.size();
				}
				break;

			default:
				break;
			}
		};
	};
	
	class SearchListAdapter extends BaseAdapter{

		private List<HotNewsBean> hot;
		public void setValue(List<HotNewsBean> hot){
			this.hot = hot;
		}
		
		@Override
		public int getCount() {
			if(hot == null) return 0;
			return hot.size();
		}

		@Override
		public Object getItem(int position) {
			return hot.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if(convertView == null){
				convertView = View.inflate(NewsSearchActivity.this, R.layout.hotnews_listitem, null);
			}
			HotNewsBean news = hot.get(position);
			((TextView)convertView.findViewById(R.id.hotitem_title)).setText(news.title);
			((TextView)convertView.findViewById(R.id.hotitem_text)).setText(news.description);
			((TextView)convertView.findViewById(R.id.hotitem_time)).setText(news.inputtime);
			return convertView;
		}
		
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		
		
		lastItem = firstVisibleItem + visibleItemCount - 1;  //减1是因为上面加了个addFooterView
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		Log.i("wyq", "lastItem="+lastItem);
		Log.i("wyq", "count="+count);
		Log.i("wyq", "scrollState="+this.SCROLL_STATE_IDLE);
		if(lastItem == count  && scrollState == this.SCROLL_STATE_IDLE){ 
			//Log.i(TAG, "拉到最底部");
			moreView.setVisibility(view.VISIBLE);
		 
		    mHandler1.sendEmptyMessage(0);
			 
		}
	}
	//声明Handler
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
				    mSearchAdapter.setValue(searchList);
				    mSearchAdapter.notifyDataSetChanged();
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
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(NewsSearchActivity.this,NewsDetailActivity.class);
			intent.putExtra("id", searchList.get(arg2).id);
			NewsGroup.getInstance().switchActivity("NewsDetailActivity",intent,-1,-1);
		}
}
