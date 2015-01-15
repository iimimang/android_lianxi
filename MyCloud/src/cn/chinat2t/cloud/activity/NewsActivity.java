package cn.chinat2t.cloud.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.chinat2t.cloud.R;
import cn.chinat2t.cloud.bean.HotNewsBean;
import cn.chinat2t.cloud.bean.TopNewsBean;
import cn.chinat2t.cloud.http.CommunicationManager;
import cn.chinat2t.cloud.http.CommunicationResultListener;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class NewsActivity extends Activity implements OnClickListener, OnItemClickListener, OnScrollListener{
	
	private TextView topTitle,topText,timeText;
	private ListView hotListView = null;
	
	private View moreView; //加载更多页面
	private int lastItem;
    private int count;
	static int i=1;
	
	private HotListAdapter hotAdapter = null;
	private String id;
	private List<HotNewsBean> hotList = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.news_layout);
		initViews();
		initData();
	}
	
	private void initViews(){
		i=1;
		hotListView = (ListView) findViewById(R.id.news_hot);
		hotAdapter = new HotListAdapter();
		hotList=new ArrayList<HotNewsBean>();
		count=hotList.size();
		moreView =getLayoutInflater().inflate(R.layout.load, null);
		hotListView.addFooterView(moreView);
		hotListView.setOnScrollListener(this);
		topTitle = (TextView) findViewById(R.id.news_first_title);
		topText = (TextView) findViewById(R.id.news_first_text);
		timeText = (TextView) findViewById(R.id.news_time);
		
		hotListView.setAdapter(hotAdapter);
		hotListView.setOnItemClickListener(this);
		findViewById(R.id.news_search).setOnClickListener(this);
	}
	
	private void initData(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		timeText.setText("日期:"+sdf.format(new Date()));
		CommunicationManager.getInstance().getTopNews(topResultListener);
		CommunicationManager.getInstance().getHotNews(i,(String) this.getResources().getText(R.string.pageSize),hotResultListener);
		i++;
		count = hotList.size();
	}
    private void loadMoreData(){ //加载更多数据
      	 count = hotAdapter.getCount(); 
      	CommunicationManager.getInstance().getHotNews(i, (String) this.getResources().getText(R.string.pageSize), hotResultListener);
      	i++;
      	count = hotList.size();
      }
	
	private CommunicationResultListener topResultListener = new CommunicationResultListener() {
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
	
	private CommunicationResultListener hotResultListener = new CommunicationResultListener() {
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
	
	Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case CommunicationManager.FAIL:
				break;
			case CommunicationManager.SUCCEED:
				if(msg.arg1 == 1){
					TopNewsBean top = (TopNewsBean) msg.obj;
					if(top != null){
						topTitle.setText(top.title);
						topText.setText(top.description);
					}
				} else if(msg.arg1 == 2){
					List tempList=new ArrayList<HotNewsBean>();
					tempList=(List<HotNewsBean>) msg.obj;
					hotList.addAll(tempList);
					//hotList = (List<HotNewsBean>) msg.obj;
					hotAdapter.setValue(hotList);
					hotAdapter.notifyDataSetChanged();
					count=hotList.size();
				}
				break;

			default:
				break;
			}
		};
	};
	
	class HotListAdapter extends BaseAdapter{

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
				convertView = View.inflate(NewsActivity.this, R.layout.hotnews_listitem, null);
			}
			HotNewsBean news = hot.get(position);
			((TextView)convertView.findViewById(R.id.hotitem_title)).setText(news.title);
			((TextView)convertView.findViewById(R.id.hotitem_text)).setText(news.description);
			((TextView)convertView.findViewById(R.id.hotitem_time)).setText(news.inputtime);
			return convertView;
		}
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.news_search:
			Intent intent = new Intent(this,NewsSearchActivity.class);
			NewsGroup.getInstance().switchActivity("NewsSearchActivity",intent,-1,-1);
			break;

		default:
			break;
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			NewsGroup.getInstance().onKeyDown(keyCode, event);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(NewsActivity.this,NewsDetailActivity.class);
		intent.putExtra("id", hotList.get(arg2).id);
		NewsGroup.getInstance().switchActivity("NewsDetailActivity",intent,-1,-1);
		
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
			    hotAdapter.setValue(hotList);
			    hotAdapter.notifyDataSetChanged();
			    
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
