package cn.chinat2t.cloud.activity;

import cn.chinat2t.cloud.R;
import cn.chinat2t.cloud.framework.BaseGroup;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ViewFlipper;

public class BusinessGroup extends BaseGroup{

	public static BusinessGroup group = null;
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
		Log.i("wyq", id+"看看id是否传过来");
//		if(id==null||"".equals(id))
//		{
			Intent intent = new Intent(this,BusinessActivity.class);
			switchActivity("BusinessActivity",intent,-1,-1);
		//}
//		else
//		{
			if(id!=null&&!"".equals(id))
			{
				Intent intent1 = new Intent(this,BusinessDetailActivity.class);
				intent1.putExtra("id", id);
				//switchActivity("BusinessDetailActivity",intent,-1,-1);
				startActivity(intent1);
			}

		//}
		
	}
	
	public static BusinessGroup getInstance(){
		return group;
	}
}
