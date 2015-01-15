package cn.chinat2t.cloud.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import cn.chinat2t.cloud.R;

public class MoreActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.more_layout);
		initViews();
	}
	private void initViews(){
		findViewById(R.id.more_zsdl).setOnClickListener(this);
		findViewById(R.id.more_gqsj).setOnClickListener(this);
		findViewById(R.id.more_hqjg).setOnClickListener(this);
		findViewById(R.id.more_ppb).setOnClickListener(this);
		findViewById(R.id.more_zlzh).setOnClickListener(this);
		//businessOrderListView.setOnItemClickListener(businessOrderItemClick);
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			MoreGroup.getInstance().onKeyDown(keyCode, event);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.more_zsdl:
			Intent intent_zsdl=new Intent(MoreActivity.this, BusinessOrderActivity.class);
			MoreGroup.getInstance().switchActivity("BusinessOrderActivity",intent_zsdl,-1,-1);
			break;
		case R.id.more_gqsj:
			Intent intent_gqsj=new Intent(MoreActivity.this, BusinessSupplyActivity.class);
			MoreGroup.getInstance().switchActivity("BusinessSupplyActivity",intent_gqsj,-1,-1);
			break;
		case R.id.more_hqjg:
			Intent intent_hqjg=new Intent(MoreActivity.this, BusinessPriceActivity.class);
			MoreGroup.getInstance().switchActivity("BusinessPriceActivity",intent_hqjg,-1,-1);
			break;
		case R.id.more_zlzh:
			Intent intent_zlzh=new Intent(MoreActivity.this, BusinessExhibitionActivity.class);
			MoreGroup.getInstance().switchActivity("BusinessExhibitionActivity",intent_zlzh,-1,-1);
			break;
		case R.id.more_ppb:
			Intent intent_ppb=new Intent(MoreActivity.this, BrandPicsActivity1.class);
			MoreGroup.getInstance().switchActivity("BrandPicsActivity1",intent_ppb,-1,-1);
			break;
		default:
			break;
		}
		
		
	}
}
