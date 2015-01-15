package cn.chinat2t.cloud.activity;

import java.util.List;

import cn.chinat2t.cloud.R;
import cn.chinat2t.cloud.bean.CommentBean;
import cn.chinat2t.cloud.bean.ProductBean;
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
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ProductDetailActivity extends Activity implements OnClickListener{

	private ImageView pImageView = null;
	private TextView pName,pSeller,pPlace,pDesc;
	private ListView commListView = null;
	
	private CommentAdapter cAdapter = null;
	
	private String id = "";
	private ProductBean product = null;
	private List<CommentBean> commentList = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		id = getIntent().getStringExtra("id");
		
		setContentView(R.layout.product_detail_layout);
		initViews();
		initData();
	}
	
	private void initViews(){
		pImageView = (ImageView) findViewById(R.id.product_detail_pic);
		pName = (TextView) findViewById(R.id.product_detail_name);
		pSeller = (TextView) findViewById(R.id.product_detail_maijia2);
		pPlace = (TextView) findViewById(R.id.product_detail_place2);
		pDesc = (TextView) findViewById(R.id.product_detail_desc);
		findViewById(R.id.product_detail_back).setOnClickListener(this);
		commListView = (ListView) findViewById(R.id.product_comment_list);
		cAdapter = new CommentAdapter();
		commListView.setAdapter(cAdapter);
	}
	
	private void initData(){
		CommunicationManager.getInstance().getProductDetail(id, detailResultListener);
		CommunicationManager.getInstance().getProductComments(id, commentResultListener);
	}
	
	private CommunicationResultListener detailResultListener = new CommunicationResultListener() {
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
	private CommunicationResultListener commentResultListener = new CommunicationResultListener() {
		public void resultListener(byte result, Object resultData) {
			switch (result) {
			case CommunicationManager.FAIL:
				break;
				
			case CommunicationManager.SUCCEED:
				break;
			}
			Message msg = mHandler.obtainMessage();
			msg.what = result;
			msg.arg1 = 2;
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
					product = (ProductBean) msg.obj;
					pName.setText(product.title);
					pSeller.setText(product.sj);
					pPlace.setText(product.cd);
					Log.i("wyq", "description="+product.description);
					pDesc.setText(Html.fromHtml(product.description));
					BitmapManager.getInstance().loadBitmap(product.thumb, pImageView, Tools.readBitmap(ProductDetailActivity.this, R.drawable.image6));
				} else if(msg.arg1 == 2){
					commentList = (List<CommentBean>) msg.obj;
					cAdapter.setValue(commentList);
					cAdapter.notifyDataSetChanged();
				}
				break;

			default:
				break;
			}
		};
	};
	
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			ProductGroup.getInstance().back();
			Log.i("wyq", "执行了");
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.product_detail_back:
			ProductGroup.getInstance().back();
			//finish();
//			Intent intent = new Intent(this,ProductActivity.class);
//			ProductGroup.getInstance().switchActivity("ProductActivity",intent,-1,-1);
			break;

		default:
			break;
		}
	}
	
	class CommentAdapter extends BaseAdapter{

		private List<CommentBean> mList;
		public void setValue(List<CommentBean> mList){
			this.mList = mList;
		}
		
		@Override
		public int getCount() {
			if(mList == null) return 0;
			
			return mList.size();
		}

		@Override
		public Object getItem(int arg0) {
			return mList.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			if(arg1 == null){
				arg1 = View.inflate(ProductDetailActivity.this, R.layout.comment_listitem, null);
			}
			CommentBean commnet = mList.get(arg0);
			((TextView)arg1.findViewById(R.id.comment_name)).setText(commnet.username+":");
			((TextView)arg1.findViewById(R.id.comment_text)).setText(commnet.content);
			((TextView)arg1.findViewById(R.id.comment_time)).setText(commnet.creat_at);
			if(arg0==0)
			{
				View listitem = cAdapter.getView(0, null, commListView);
				listitem.measure(0, 0);
				ViewGroup.LayoutParams linearParams = commListView.getLayoutParams(); // 取控件mGrid当前的布局参数
				linearParams.height = (listitem.getMeasuredHeight()+3) * commentList.size();
				commListView.setLayoutParams(linearParams); 
			}

			return arg1;
		}
		
	}
}
