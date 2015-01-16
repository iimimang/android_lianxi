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
		 * Mode.BOTH��ͬʱ֧����������
         * Mode.PULL_FROM_START��ֻ֧������Pulling Down
         * Mode.PULL_FROM_END��ֻ֧������Pulling Up
		 */
		/*
		 * ���Mode���ó�Mode.BOTH����Ҫ����ˢ��ListenerΪOnRefreshListener2����ʵ��onPullDownToRefresh()��onPullUpToRefresh()���������� 
         * ���Mode���ó�Mode.PULL_FROM_START��Mode.PULL_FROM_END����Ҫ����ˢ��ListenerΪOnRefreshListener��ͬʱʵ��onRefresh()������
         * ��ȻҲ��������ΪOnRefreshListener2������Mode.PULL_FROM_START��ʱ��ֻ����onPullDownToRefresh()������
         * Mode.PULL_FROM��ʱ��ֻ����onPullUpToRefresh()����. 
		 */
		pullToRefresh.setMode(Mode.BOTH);
		init();
		
		/*
		 * setOnRefreshListener(OnRefreshListener listener):����ˢ�¼�������
		 * setOnLastItemVisibleListener(OnLastItemVisibleListener listener):�����Ƿ񵽵ײ���������
		 * setOnPullEventListener(OnPullEventListener listener);�����¼���������
		 * onRefreshComplete()������ˢ�����
		 */
		/*
		 * pulltorefresh.setOnScrollListener()
		 */
		// SCROLL_STATE_TOUCH_SCROLL ���ڹ���    
        // SCROLL_STATE_FLING ��ָ�����׵Ķ�������ָ�뿪��Ļǰ����������һ�£�    
        // SCROLL_STATE_IDLE ֹͣ����       
		/*
		 * setOnLastItemVisibleListener
		 * ���û�������ʱ����  
		 */
		/*
		 * setOnTouchListener�Ǽ�شӵ������ �������϶���꣩���ſ���꣨�����Ի�����ָ������������ �����Ļص�������onTouchEvent��MotionEvent event��,
		 * Ȼ��ͨ���ж�event.getAction()��MotionEvent.ACTION_UP����ACTION_DOWN����ACTION_MOVE�ֱ�����ͬ��Ϊ��
         * setOnClickListener�ļ��ʱ��ֻ��ص���ָACTION_DOWNʱ��������Ϊ
		 */
		pullToRefresh.setOnRefreshListener(new OnRefreshListener2<ListView>(){
			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				 PullBean bean = new PullBean();
	             bean.setTitle("����ˢ��");
	             bean.setContent("�ҵ���");
	             adapter.addFirst(bean);
	             new FinishRefresh().execute();
	             adapter.notifyDataSetChanged();
			}
			
			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				PullBean bean = new PullBean();
				bean.setTitle("����ˢ��");
				bean.setContent("�ҵ���");
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
//                bean.setTitle("�ҵ���");
//                bean.setContent("�ҵ���");
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
        startLabels.setPullLabel("����ˢ��...");// ������ʱ����ʾ����ʾ  
        startLabels.setRefreshingLabel("��������...");// ˢ��ʱ  
        startLabels.setReleaseLabel("�ſ�ˢ��...");// �����ﵽһ������ʱ����ʾ����ʾ  
  
        ILoadingLayout endLabels = pullToRefresh.getLoadingLayoutProxy(  
                false, true);  
        endLabels.setPullLabel("����ˢ��...");// ������ʱ����ʾ����ʾ  
        endLabels.setRefreshingLabel("��������...");// ˢ��ʱ  
        endLabels.setReleaseLabel("�ſ�ˢ��...");// �����ﵽһ������ʱ����ʾ����ʾ  
        
		// ��������ˢ���ı�
		pullToRefresh.getLoadingLayoutProxy(false, true)
				.setPullLabel("����ˢ��...");
		pullToRefresh.getLoadingLayoutProxy(false, true).setReleaseLabel(
				"�ſ�ˢ��...");
		pullToRefresh.getLoadingLayoutProxy(false, true).setRefreshingLabel(
				"���ڼ���...");
		// ��������ˢ���ı�
		pullToRefresh.getLoadingLayoutProxy(true, false)
				.setPullLabel("����ˢ��...");
		pullToRefresh.getLoadingLayoutProxy(true, false).setReleaseLabel(
				"�ſ�ˢ��...");
		pullToRefresh.getLoadingLayoutProxy(true, false).setRefreshingLabel(
				"���ڼ���...");
    }  
	
	private List<PullBean> getData(){
		List<PullBean> list = new ArrayList<PullBean>();
		for(int i = 0;i < 10;i ++){
			PullBean bean = new PullBean();
			bean.setTitle("item " + i + " ����ҵ�������»� Google��������?");
			bean.setContent("Google��10��17�շ�����2014��������ȲƱ�");
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