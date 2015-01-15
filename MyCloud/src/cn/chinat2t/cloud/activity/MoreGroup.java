package cn.chinat2t.cloud.activity;

import cn.chinat2t.cloud.R;
import cn.chinat2t.cloud.framework.BaseGroup;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ViewFlipper;

public class MoreGroup extends BaseGroup{

	public static MoreGroup group = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.group_main);
		
		group = this;
		
		initViews();
	}
	
	private void initViews(){
		containerFlipper = (ViewFlipper) findViewById(R.id.group_content);
		String id=getIntent().getStringExtra("id");
//		if(id==null||"".equals(id))
//		{
			Intent intent = new Intent(this,MoreActivity.class);
			switchActivity("MoreActivity",intent,-1,-1);
		//}
//		else
//		{
			Intent intent1 = new Intent(this,BrandPicDetailActivity.class);
			intent1.putExtra("id", id);
			switchActivity("BrandPicDetailActivity",intent1,-1,-1);
		//}
		
//		Intent intent = new Intent(this,MoreActivity.class);
//		switchActivity("MoreActivity",intent,-1,-1);
	}
	
	public static MoreGroup getInstance(){
		return group;
	}
}
