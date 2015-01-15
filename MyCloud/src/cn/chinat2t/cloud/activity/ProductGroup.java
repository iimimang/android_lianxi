package cn.chinat2t.cloud.activity;

import cn.chinat2t.cloud.R;
import cn.chinat2t.cloud.framework.BaseGroup;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ViewFlipper;

public class ProductGroup extends BaseGroup{

	public static ProductGroup group = null;
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
		String pid=getIntent().getStringExtra("pid");
		Log.i("wyq", pid+"看看pid是否传过来");
//		if((id==null||"".equals(id))&&(pid==null||"".equals(pid)))
//		{
			Intent intent = new Intent(this,ProductActivity.class);
			switchActivity("ProductActivity",intent,-1,-1);
	//	}
//		else
//		{
			if(id!=null&&!"".equals(id))
			{	
				Intent intent1 = new Intent(this,ProductDetailActivity.class);
				intent1.putExtra("id", id);
				switchActivity("ProductDetailActivity",intent1,-1,-1);
			}else
				if(pid!=null&&!"".equals(pid))
				{
					Intent intent2 = new Intent(this,ProductActivity.class);
					intent2.putExtra("pid", pid);
					switchActivity("ProductActivity",intent2,-1,-1);
				}

	//	}
		
		
		
	}
	
	public static ProductGroup getInstance(){
		return group;
	}
}
