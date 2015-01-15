package com.example.mypulltorefresh;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class MainActivity extends ActionBarActivity {
	
	private PullToRefreshListView pullToRefresh;
	private List<PullBean> data = new ArrayList<PullBean>();
	MyAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		pullToRefresh = (PullToRefreshListView) findViewById(R.id.pullToRefresh);
		data = getData();
		adapter = new MyAdapter(this);
		pullToRefresh.setAdapter(adapter);
		/*
		 * Mode.BOTH：同时支持上拉下拉
         * Mode.PULL_FROM_START：只支持下拉Pulling Down
         * Mode.PULL_FROM_END：只支持上拉Pulling Up
		 */
		/*
		 * 如果Mode设置成Mode.BOTH，需要设置刷新Listener为OnRefreshListener2，并实现onPullDownToRefresh()、onPullUpToRefresh()两个方法。 
         * 如果Mode设置成Mode.PULL_FROM_START或Mode.PULL_FROM_END，需要设置刷新Listener为OnRefreshListener，同时实现onRefresh()方法。
         * 当然也可以设置为OnRefreshListener2，但是Mode.PULL_FROM_START的时候只调用onPullDownToRefresh()方法，
         * Mode.PULL_FROM的时候只调用onPullUpToRefresh()方法. 
		 */
		pullToRefresh.setMode(Mode.BOTH);
		init();
		
		/*
		 * setOnRefreshListener(OnRefreshListener listener):设置刷新监听器；
		 * setOnLastItemVisibleListener(OnLastItemVisibleListener listener):设置是否到底部监听器；
		 * setOnPullEventListener(OnPullEventListener listener);设置事件监听器；
		 * onRefreshComplete()：设置刷新完成
		 */
		/*
		 * pulltorefresh.setOnScrollListener()
		 */
		// SCROLL_STATE_TOUCH_SCROLL 正在滚动    
        // SCROLL_STATE_FLING 手指做了抛的动作（手指离开屏幕前，用力滑了一下）    
        // SCROLL_STATE_IDLE 停止滚动       
		/*
		 * setOnLastItemVisibleListener
		 * 当用户拉到底时调用  
		 */
		/*
		 * setOnTouchListener是监控从点下鼠标 （可能拖动鼠标）到放开鼠标（鼠标可以换成手指）的整个过程 ，他的回调函数是onTouchEvent（MotionEvent event）,
		 * 然后通过判断event.getAction()是MotionEvent.ACTION_UP还是ACTION_DOWN还是ACTION_MOVE分别作不同行为。
         * setOnClickListener的监控时间只监控到手指ACTION_DOWN时发生的行为
		 */
		pullToRefresh.setOnRefreshListener(new OnRefreshListener2<ListView>(){
			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				 PullBean bean = new PullBean();
	             bean.setTitle("下拉刷新");
	             bean.setContent("我的神");
	             adapter.addFirst(bean);
	             new FinishRefresh().execute();
	             adapter.notifyDataSetChanged();
			}
			
			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				PullBean bean = new PullBean();
				bean.setTitle("上拉刷新");
				bean.setContent("我的神");
				adapter.addLast(bean);
				new FinishRefresh().execute();
				adapter.notifyDataSetChanged();
			}
		});
		
	
//		pullToRefresh.setOnRefreshListener(new OnRefreshListener<ListView>() {
//
//			@Override
//			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
//				// TODO Auto-generated method stub
//				String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),  
//                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);  
//  
//                // Update the LastUpdatedLabel  
//                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
//                PullBean bean = new PullBean();
//                bean.setTitle("我的神");
//                bean.setContent("我的神");
//                adapter.addFirst(bean);
//                new FinishRefresh().execute();
//			}
//			
//		});
	}
	
	private void init()  
    {  
        ILoadingLayout startLabels = pullToRefresh  
                .getLoadingLayoutProxy(true, false);  
        startLabels.setPullLabel("下拉刷新...");// 刚下拉时，显示的提示  
        startLabels.setRefreshingLabel("正在载入...");// 刷新时  
        startLabels.setReleaseLabel("放开刷新...");// 下来达到一定距离时，显示的提示  
  
        ILoadingLayout endLabels = pullToRefresh.getLoadingLayoutProxy(  
                false, true);  
        endLabels.setPullLabel("上拉刷新...");// 刚下拉时，显示的提示  
        endLabels.setRefreshingLabel("正在载入...");// 刷新时  
        endLabels.setReleaseLabel("放开刷新...");// 下来达到一定距离时，显示的提示  
        
		// 设置下拉刷新文本
		pullToRefresh.getLoadingLayoutProxy(false, true)
				.setPullLabel("上拉刷新...");
		pullToRefresh.getLoadingLayoutProxy(false, true).setReleaseLabel(
				"放开刷新...");
		pullToRefresh.getLoadingLayoutProxy(false, true).setRefreshingLabel(
				"正在加载...");
		// 设置上拉刷新文本
		pullToRefresh.getLoadingLayoutProxy(true, false)
				.setPullLabel("下拉刷新...");
		pullToRefresh.getLoadingLayoutProxy(true, false).setReleaseLabel(
				"放开刷新...");
		pullToRefresh.getLoadingLayoutProxy(true, false).setRefreshingLabel(
				"正在加载...");
    }  
	
	private List<PullBean> getData(){
		List<PullBean> list = new ArrayList<PullBean>();
		for(int i = 0;i < 10;i ++){
			PullBean bean = new PullBean();
			bean.setTitle("item " + i + " 搜索业务增速下滑 Google廉颇老矣?");
			bean.setContent("Google于10月17日发布了2014年第三季度财报");
			list.add(bean);
		}
		
		return list;
	}
	
	private class FinishRefresh extends AsyncTask<Void, Void, Void>{  
        @Override  
        protected Void doInBackground(Void... params) {  
        	 try {  
                 Thread.sleep(1000);  
             } catch (InterruptedException e) {  
             }  
            return null;  
        }  
   
        @Override  
        protected void onPostExecute(Void result){  
//        	adapter.notifyDataSetChanged();
        	pullToRefresh.onRefreshComplete();  
        }  
    }  
	
	private class MyAdapter extends BaseAdapter{
		private LayoutInflater mInflater;
		
		public MyAdapter(Context context) {
			// TODO Auto-generated constructor stub
			mInflater = LayoutInflater.from(context);
		}
		
		public void addFirst(PullBean bean){
			data.add(0, bean);
		}
		
		public void addLast(PullBean bean){
			data.add(bean);
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return data.size();
		}
		
		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return data.get(position);
		}
		
		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder viewHolder = null;
			if(convertView == null){
				viewHolder = new ViewHolder();
				convertView = mInflater.inflate(R.layout.item, null);
				viewHolder.title = (TextView) convertView.findViewById(R.id.title);
				viewHolder.content = (TextView) convertView.findViewById(R.id.content);
				
				convertView.setTag(viewHolder);
			}else{
				viewHolder = (ViewHolder) convertView.getTag();
			}
			
			viewHolder.title.setText(data.get(position).getTitle());
			viewHolder.content.setText(data.get(position).getContent());
			
			return convertView;
		}
		
		class ViewHolder{
			TextView title;
			TextView content;
		}
	}

	

}
