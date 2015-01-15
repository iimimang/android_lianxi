package cn.chinat2t.cloud.activity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.chinat2t.cloud.R;
import cn.chinat2t.cloud.bean.HotNewsBean;
import cn.chinat2t.cloud.bean.NewsDetail;
import cn.chinat2t.cloud.bean.TopNewsBean;
import cn.chinat2t.cloud.http.CommunicationManager;
import cn.chinat2t.cloud.http.CommunicationResultListener;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class NewsDetailActivity extends Activity implements OnClickListener{
	
	private TextView newsDetailName,newsDetailTime,newsDetailContent;
	private String id;
	private Button backBtn,searchBtn;
//	private ListView hotListView = null;
//	
//	private HotListAdapter hotAdapter = null;
//
//	private List<HotNewsBean> hotList = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.news_detail_layout);
		
		initViews();
		initData();
	}
	
	private void initViews(){
		newsDetailName=(TextView)findViewById(R.id.news_detail_name);
		newsDetailTime=(TextView)findViewById(R.id.news_detail_time);
		newsDetailContent=(TextView)findViewById(R.id.news_detail_content);
		backBtn=(Button)findViewById(R.id.news_detail_back);
		searchBtn=(Button)findViewById(R.id.news_detail_search);
		backBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(NewsDetailActivity.this, NewsActivity.class);
				NewsGroup.getInstance().switchActivity("NewsActivity",intent,-1,-1);
			}
		});
		searchBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(NewsDetailActivity.this, NewsSearchActivity.class);
				NewsGroup.getInstance().switchActivity("NewsSearchActivity",intent,-1,-1);
			}
		});
	}
	
	private void initData(){
		//CommunicationManager.getInstance().getTopNews(topResultListener);
		id = getIntent().getStringExtra("id");
		CommunicationManager.getInstance().getNewsDetail(id,newsDetailResultListener);
		
	}
	
	
	private CommunicationResultListener newsDetailResultListener = new CommunicationResultListener() {
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
					NewsDetail newsDetail = (NewsDetail) msg.obj;
					if(newsDetail != null){
						newsDetailName.setText(newsDetail.getTitle());
						newsDetailTime.setText(newsDetail.getInputtime());
						newsDetailContent.setText(Html.fromHtml(newsDetail.getContent()));
					}
				} 
				break;

			default:
				break;
			}
		};
	};
	
	
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
			NewsGroup.getInstance().back();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
}
