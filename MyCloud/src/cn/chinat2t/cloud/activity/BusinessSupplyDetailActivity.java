package cn.chinat2t.cloud.activity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.chinat2t.cloud.R;
import cn.chinat2t.cloud.bean.BusinessSupplyBean;
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

public class BusinessSupplyDetailActivity extends Activity implements OnClickListener{
	
	private TextView title,yxq,lb,ggxh, cpsl,dq,lxr,email,lxdh,inputtime;
	private Button backBtn,searchBtn;
	private String id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.business_supply_detail_layout);
		
		initViews();
		initData();
	}
	
	private void initViews(){
		title=(TextView)findViewById(R.id.businessSupply_detail_name);
		yxq=(TextView)findViewById(R.id.yxq);
		lb=(TextView)findViewById(R.id.lb);
		ggxh=(TextView)findViewById(R.id.ggxh);
		cpsl=(TextView)findViewById(R.id.cpsl);
		dq=(TextView)findViewById(R.id.dq);
		lxr=(TextView)findViewById(R.id.lxr);
		email=(TextView)findViewById(R.id.email);
		lxdh=(TextView)findViewById(R.id.lxdh);
		inputtime=(TextView)findViewById(R.id.businessSupply_detail_fbsj1);
		findViewById(R.id.businessSupplyTitle1CloseLayout).setOnClickListener(this);
		findViewById(R.id.businessSupplyTitle1OpenLayout).setOnClickListener(this);
		findViewById(R.id.businessSupplyTitle2CloseLayout).setOnClickListener(this);
		findViewById(R.id.businessSupplyTitle2OpenLayout).setOnClickListener(this);
		backBtn=(Button)findViewById(R.id.businessSupply_detail_back);
		searchBtn=(Button)findViewById(R.id.businessSupply_detail_search);
		backBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				MoreGroup.getInstance().back();
			}
		});
		searchBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Intent intent=new Intent();
//				intent.setClass(BusinessSupplyDetailActivity.this, supplySearchActivity.class);
//				supplyGroup.getInstance().switchActivity("supplySearchActivity",intent,-1,-1);
			}
		});
	}
	
	private void initData(){
		//CommunicationManager.getInstance().getTopsupply(topResultListener);
		id = getIntent().getStringExtra("id");
		CommunicationManager.getInstance().getSupplyDetail(id,supplyDetailResultListener);
		
	}
	
	
	private CommunicationResultListener supplyDetailResultListener = new CommunicationResultListener() {
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
					BusinessSupplyBean supplyDetail = (BusinessSupplyBean) msg.obj;
					if(supplyDetail != null){
						title.setText(supplyDetail.getTitle());
						yxq.setText(supplyDetail.getYxq());
						lb.setText(supplyDetail.getLb());
						ggxh.setText(supplyDetail.getGgxh());
						cpsl.setText(supplyDetail.getCpsl());
						dq.setText(supplyDetail.getDq());
						lxr.setText(supplyDetail.getLxr());
						email.setText(supplyDetail.getEmail());
						lxdh.setText(supplyDetail.getLxdh());
						inputtime.setText(supplyDetail.getInputtime());
						
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
		case R.id.businessSupply_detail_back:
			MoreGroup.getInstance().back();
			break;
		case R.id.businessSupplyTitle1CloseLayout:
			findViewById(R.id.businessSupplyTitle1CloseLayout).setVisibility(View.GONE);
			//findViewById(R.id.businessOrderTitle1OpenLayout).setVisibility(View.VISIBLE);
			findViewById(R.id.supplycontentLayout).setVisibility(View.VISIBLE);
			break;
		case R.id.businessSupplyTitle1OpenLayout:
			findViewById(R.id.businessSupplyTitle1CloseLayout).setVisibility(View.VISIBLE);
			//findViewById(R.id.businessOrderTitle1OpenLayout).setVisibility(View.GONE);
			findViewById(R.id.supplycontentLayout).setVisibility(View.GONE);
			break;
		case R.id.businessSupplyTitle2CloseLayout:
			findViewById(R.id.businessSupplyTitle2CloseLayout).setVisibility(View.GONE);
			//findViewById(R.id.businessOrderTitle1OpenLayout).setVisibility(View.VISIBLE);
			findViewById(R.id.contentLayout1).setVisibility(View.VISIBLE);
			break;
		case R.id.businessSupplyTitle2OpenLayout:
			findViewById(R.id.businessSupplyTitle2CloseLayout).setVisibility(View.VISIBLE);
			//findViewById(R.id.businessOrderTitle1OpenLayout).setVisibility(View.GONE);
			findViewById(R.id.contentLayout1).setVisibility(View.GONE);
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
