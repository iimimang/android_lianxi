package cn.chinat2t.cloud.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.chinat2t.cloud.R;
import cn.chinat2t.cloud.bean.BrandPicsListBean;
import cn.chinat2t.cloud.bean.BusinessListBean;
import cn.chinat2t.cloud.bean.HotNewsBean;
import cn.chinat2t.cloud.http.BitmapManager;
import cn.chinat2t.cloud.http.CommunicationManager;
import cn.chinat2t.cloud.http.CommunicationResultListener;
import cn.chinat2t.cloud.utils.CtLog;
import cn.chinat2t.cloud.utils.Tools;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class BrandPicsActivity extends Activity implements OnItemClickListener  {
	private GridView gridview =null;
	private List<BrandPicsListBean> brandPicsList;
	private View moreView; //加载更多页面
	private int lastItem;
    private int count;
	static int i=1;
	public List<BrandPicsListBean> getBrandPicsList() {
		return brandPicsList;
	}
	public void setBrandPicsList(List<BrandPicsListBean> brandPicsList) {
		this.brandPicsList = brandPicsList;
	}

	private BrandPicsAdapter brandPicsAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.brand_pics_layout);
	      //生成动态数组，并且转入数据   
		initViews();
		initData();
		

	}
	private void initData(){
		CommunicationManager.getInstance().getBrandPicsList(brandPicsResultListener);
	}
	private void initViews(){
		i=1;
		brandPicsList=new ArrayList<BrandPicsListBean>();
		count=brandPicsList.size();
		moreView =getLayoutInflater().inflate(R.layout.load, null);
		gridview = (GridView) findViewById(R.id.gridview); 
		gridview.setOnItemClickListener(this);
		brandPicsAdapter=new BrandPicsAdapter();
//		gridview.addFooterView(moreView);
//		gridview.setOnScrollListener(this);
		gridview.setAdapter(brandPicsAdapter);
	}
	
	private CommunicationResultListener brandPicsResultListener = new CommunicationResultListener() {
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
					
					brandPicsList = (List<BrandPicsListBean>) msg.obj;
					brandPicsAdapter.setValues(brandPicsList);
					brandPicsAdapter.notifyDataSetChanged();
				} 
				break;

			default:
				break;
			}
		};
	};
	
	class BrandPicsAdapter extends BaseAdapter{

		private List<BrandPicsListBean> brandPicsList1 = null;
		private HashMap<Integer, View> viewMap = null;
		
		public void setValues(List<BrandPicsListBean> bList){
			this.brandPicsList1 = bList;
			viewMap = new HashMap<Integer, View>();
		}
		
		@Override
		public int getCount() {
			if(brandPicsList1 == null ) return 0;
			return brandPicsList1.size();
		}

		@Override
		public Object getItem(int position) {
			return brandPicsList1.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			convertView = viewMap.get(position);
			ViewHolder holder = null;
			if(convertView == null){
				convertView = View.inflate(BrandPicsActivity.this, R.layout.brand_pics_item, null);
				holder = new ViewHolder();
				holder.brandImage = (ImageView) convertView.findViewById(R.id.brandImage);
				//holder.brandName = (TextView)convertView.findViewById(R.id.brandName);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			
			BrandPicsListBean p = brandPicsList1.get(position);
			//holder.brandName.setText(p.getName());
			BitmapManager.getInstance().loadRoundBitmap(p.getLogo(), holder.brandImage, Tools.readBitmap(BrandPicsActivity.this, R.drawable.image6),0,0);
//			holder.business_log
//			holder.name.setText(p.title);
//			holder.desc.setText(p.description);
//			BitmapManager.getInstance().loadBitmap(p.thumb, holder.iv, Tools.readBitmap(Activity.this, R.drawable.image6));
//			
			return convertView;
		}
		class ViewHolder{
			//public TextView brandName;
			public ImageView brandImage;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(BrandPicsActivity.this,BrandPicDetailActivity.class);
		intent.putExtra("id", brandPicsList.get(arg2).getLinkageid());
		//startActivity(intent);
		MoreGroup.getInstance().switchActivity("BrandPicDetailActivity",intent,-1,-1);
	}

	
}
