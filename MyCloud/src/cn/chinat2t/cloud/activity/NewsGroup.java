package cn.chinat2t.cloud.activity;

import cn.chinat2t.cloud.R;
import cn.chinat2t.cloud.framework.BaseGroup;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ViewFlipper;

public class NewsGroup extends BaseGroup{

	public static NewsGroup group = null;
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
			Intent intent = new Intent(this,NewsActivity.class);
			switchActivity("NewsActivity",intent,-1,-1);
		//}
//		else
//		{
			Intent intent1 = new Intent(this,NewsDetailActivity.class);
			intent1.putExtra("id", id);
			switchActivity("NewsDetailActivity",intent1,-1,-1);
		//}
		
	}
	
	public static NewsGroup getInstance(){
		return group;
	}
}
