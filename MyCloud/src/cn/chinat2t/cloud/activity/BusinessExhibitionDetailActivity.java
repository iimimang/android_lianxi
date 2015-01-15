package cn.chinat2t.cloud.activity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.chinat2t.cloud.R;
import cn.chinat2t.cloud.bean.BusinessExhibitionBean;
import cn.chinat2t.cloud.bean.HotNewsBean;
import cn.chinat2t.cloud.bean.NewsDetail;
import cn.chinat2t.cloud.bean.TopNewsBean;
import cn.chinat2t.cloud.http.BitmapManager;
import cn.chinat2t.cloud.http.CommunicationManager;
import cn.chinat2t.cloud.http.CommunicationResultListener;
import cn.chinat2t.cloud.utils.Tools;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class BusinessExhibitionDetailActivity extends Activity implements OnClickListener{
	
	private TextView title,time,zhrq,zgmc,zhdz,sfcs,hylb,zhfw,zbdw,cbdw,lxr,lxdh,lxcz,lxdz,yx,jsrq;
	private ImageView image;
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
		setContentView(R.layout.business_exhibition_detail_layout);
		
		initViews();
		initData();
	}
	
	private void initViews(){
		title=(TextView)findViewById(R.id.exhibition_detail_name);
		time=(TextView)findViewById(R.id.exhibition_detail_time1);
		zhrq=(TextView)findViewById(R.id.zhrq1);
		zgmc=(TextView)findViewById(R.id.zhmc1);
		zhdz=(TextView)findViewById(R.id.zhdz1);
		sfcs=(TextView)findViewById(R.id.sfcs1);
		hylb=(TextView)findViewById(R.id.hylb1);
		zhfw=(TextView)findViewById(R.id.zhfw1);
		zbdw=(TextView)findViewById(R.id.zbdw);
		cbdw=(TextView)findViewById(R.id.cbdw);
		lxr=(TextView)findViewById(R.id.lxr);
		lxdh=(TextView)findViewById(R.id.lxdh);
		lxcz=(TextView)findViewById(R.id.lxcz);
		lxdz=(TextView)findViewById(R.id.lxdz);
		yx=(TextView)findViewById(R.id.yx);
		jsrq=(TextView)findViewById(R.id.zhrq2);
		image=(ImageView)findViewById(R.id.exhibition_detail_pic);
		//exhibitionDetailContent=(TextView)findViewById(R.id.exhibition_detail_content);
		backBtn=(Button)findViewById(R.id.exhibition_detail_back);
		searchBtn=(Button)findViewById(R.id.exhibition_detail_search);
		backBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MoreGroup.getInstance().back();
			}
		});
	}
	
	private void initData(){
		//CommunicationManager.getInstance().getTopNews(topResultListener);
		id = getIntent().getStringExtra("id");
		CommunicationManager.getInstance().getExhibitionDetail(id,exhibitionDetailResultListener);
		
	}
	
	
	private CommunicationResultListener exhibitionDetailResultListener = new CommunicationResultListener() {
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
					BusinessExhibitionBean exhibitionDetail = (BusinessExhibitionBean) msg.obj;
					if(exhibitionDetail != null){
						cbdw.setText(exhibitionDetail.getCbdw());
						title.setText(exhibitionDetail.getTitle());
						time.setText(exhibitionDetail.getInputtime());
						zhrq.setText(exhibitionDetail.getKsrq());
						zgmc.setText(exhibitionDetail.getZgmc());
						zhdz.setText(exhibitionDetail.getZhdz());
						sfcs.setText(exhibitionDetail.getSfcs());
						hylb.setText(exhibitionDetail.getHylb());
						zhfw.setText(exhibitionDetail.getZhfw());
						zbdw.setText(exhibitionDetail.getZbdw());
						cbdw.setText(exhibitionDetail.getCbdw());
						lxr.setText(exhibitionDetail.getLxr());
						lxdh.setText(exhibitionDetail.getLxdh());
						lxcz.setText(exhibitionDetail.getLxcz());
						lxdz.setText(exhibitionDetail.getLxdz());
						yx.setText(exhibitionDetail.getYx());
						jsrq.setText(exhibitionDetail.getJsrq());
						BitmapManager.getInstance().loadBitmap(exhibitionDetail.getThumb(), image, Tools.readBitmap(BusinessExhibitionDetailActivity.this, R.drawable.image6));
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
		case R.id.exhibition_detail_back:
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
			MoreGroup.getInstance().back();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
}
