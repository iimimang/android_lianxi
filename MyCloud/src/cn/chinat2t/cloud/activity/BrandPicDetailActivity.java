package cn.chinat2t.cloud.activity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.chinat2t.cloud.R;
import cn.chinat2t.cloud.bean.BrandPicsDetailBean;
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

public class BrandPicDetailActivity extends Activity implements OnClickListener{
	
	private TextView name,ppxx,ppjs;
	private ImageView logo;
	private Button backBtn,searchBtn;
	private String id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.brandpic_detail_layout);
		
		initViews();
		initData();
	}
	
	private void initViews(){
		name=(TextView)findViewById(R.id.brandPic_detail_name);
		logo=(ImageView)findViewById(R.id.brandPic_detail_pic);
		ppxx=(TextView)findViewById(R.id.ppxx);
		ppjs=(TextView)findViewById(R.id.ppjs);
		findViewById(R.id.brandPicTitle1CloseLayout).setOnClickListener(this);
		findViewById(R.id.brandPicTitle1OpenLayout).setOnClickListener(this);
		findViewById(R.id.brandPicTitle2CloseLayout).setOnClickListener(this);
		findViewById(R.id.brandPicTitle2OpenLayout).setOnClickListener(this);
		backBtn=(Button)findViewById(R.id.brandPic_detail_back);
		searchBtn=(Button)findViewById(R.id.brandPic_detail_search);
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
//				intent.setClass(brandPicDetailActivity.this, supplySearchActivity.class);
//				supplyGroup.getInstance().switchActivity("supplySearchActivity",intent,-1,-1);
			}
		});
	}
	
	private void initData(){
		//CommunicationManager.getInstance().getTopsupply(topResultListener);
		id = getIntent().getStringExtra("id");
		CommunicationManager.getInstance().getBrandPicDetail(id,brandPicDetailResultListener);
		
	}
	
	
	private CommunicationResultListener brandPicDetailResultListener = new CommunicationResultListener() {
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
					BrandPicsDetailBean bean = (BrandPicsDetailBean) msg.obj;
					if(bean != null){
						name.setText(bean.getName());
						//logo.setText(bean.getName());
						ppxx.setText(Html.fromHtml(bean.getPpxx()));
						ppjs.setText(Html.fromHtml(bean.getPpjs()));
						BitmapManager.getInstance().loadBitmap(bean.getLogo(), logo, Tools.readBitmap(BrandPicDetailActivity.this, R.drawable.image6));
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
		case R.id.brandPic_detail_back:
			MoreGroup.getInstance().back();
			break;
		case R.id.brandPicTitle1CloseLayout:
			findViewById(R.id.brandPicTitle1CloseLayout).setVisibility(View.GONE);
			//findViewById(R.id.businessOrderTitle1OpenLayout).setVisibility(View.VISIBLE);
			findViewById(R.id.brandContentLayout).setVisibility(View.VISIBLE);
			break;
		case R.id.brandPicTitle1OpenLayout:
			findViewById(R.id.brandPicTitle1CloseLayout).setVisibility(View.VISIBLE);
			//findViewById(R.id.businessOrderTitle1OpenLayout).setVisibility(View.GONE);
			findViewById(R.id.brandContentLayout).setVisibility(View.GONE);
			break;
		case R.id.brandPicTitle2CloseLayout:
			findViewById(R.id.brandPicTitle2CloseLayout).setVisibility(View.GONE);
			//findViewById(R.id.businessOrderTitle1OpenLayout).setVisibility(View.VISIBLE);
			findViewById(R.id.contentLayout1).setVisibility(View.VISIBLE);
			break;
		case R.id.brandPicTitle2OpenLayout:
			findViewById(R.id.brandPicTitle2CloseLayout).setVisibility(View.VISIBLE);
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
