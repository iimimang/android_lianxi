package cn.chinat2t.cloud.activity;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.chinat2t.cloud.R;
import cn.chinat2t.cloud.bean.BusinessListBean;
import cn.chinat2t.cloud.bean.BusinessOrderBean;
import cn.chinat2t.cloud.bean.CommentBean;
import cn.chinat2t.cloud.http.BitmapManager;
import cn.chinat2t.cloud.http.CommunicationManager;
import cn.chinat2t.cloud.http.CommunicationResultListener;
import cn.chinat2t.cloud.utils.Tools;

public class BusinessOrderDetailActivity extends Activity implements OnClickListener{

	private ImageView thumb = null;
	private ImageView title1Arrow = null;
	private TextView business_name,business_zsfw,business_zycp;
	private TextView business_cpdw,business_dmyq,business_pp,business_jbtze;
	private TextView business_pbfyd,business_bzj,business_jmf;
	private TextView business_qynf,business_xmqyyq,business_yjhbl,business_yjhbzq;
	private TextView business_htqx,business_txqsyf,business_jmszs,business_txjysj;
	private TextView business_fzms,business_jyms,business_sjlxfs;
	//private ListView commListView = null;
	private Button backBtn,searchBtn;
	private CommentAdapter cAdapter = null;
	
	private String id = "";
	private BusinessOrderBean bean  = null;
	//private List<CommentBean> commentList = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		id = getIntent().getStringExtra("id");
		
		setContentView(R.layout.business_order_detail_layout);
		initViews();
		initData();
	}
	
	private void initViews(){
		thumb = (ImageView) findViewById(R.id.businessOrder_detail_pic);
		title1Arrow= (ImageView) findViewById(R.id.businessOrderTitle1Arrow);
		business_name = (TextView) findViewById(R.id.businessOrder_detail_name);
		business_zsfw = (TextView) findViewById(R.id.businessOrder_detail_zsfw1);
		business_zycp = (TextView) findViewById(R.id.businessOrder_detail_zycp1);
		business_cpdw=(TextView) findViewById(R.id.cpdw);
		
		business_dmyq=(TextView) findViewById(R.id.dmyq);
		business_pp=(TextView) findViewById(R.id.pp);
		business_jbtze=(TextView) findViewById(R.id.jbtze);
		
		
		business_pbfyd=(TextView) findViewById(R.id.ppfyd);
		business_bzj=(TextView) findViewById(R.id.bzj);
		business_jmf=(TextView) findViewById(R.id.jmf);
		business_qynf=(TextView) findViewById(R.id.qynf);
		business_xmqyyq=(TextView) findViewById(R.id.xmqyyq);
		business_yjhbl=(TextView) findViewById(R.id.yjhbl);
		business_yjhbzq=(TextView) findViewById(R.id.yjhbzq);
		business_htqx=(TextView) findViewById(R.id.htqx);
		business_txqsyf=(TextView) findViewById(R.id.txqsyf);
		business_jmszs=(TextView) findViewById(R.id.jmszs);
		business_txjysj=(TextView) findViewById(R.id.txjysj);
		business_fzms=(TextView) findViewById(R.id.fzms);
		business_jyms=(TextView) findViewById(R.id.jyms);
		business_sjlxfs=(TextView) findViewById(R.id.businessOrderTel);
		
		
		backBtn=(Button)findViewById(R.id.businessOrder_detail_back);
		backBtn.setOnClickListener(this);
//		searchBtn=(Button)findViewById(R.id.businessOrder_detail_search);
//		backBtn.setOnClickListener(buttonClick);
//		searchBtn.setOnClickListener(buttonClick);
		findViewById(R.id.businessOrderTitle1CloseLayout).setOnClickListener(this);
		findViewById(R.id.businessOrderTitle1OpenLayout).setOnClickListener(this);
		//commListView = (ListView) findViewById(R.id.product_comment_list);
		//cAdapter = new CommentAdapter();
		//commListView.setAdapter(cAdapter);
	}
	
	private void initData(){
		CommunicationManager.getInstance().getBusinessOrderDetail(id, detailResultListener);
		//CommunicationManager.getInstance().getProductComments(id, commentResultListener);
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
	
	
	Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case CommunicationManager.FAIL:
				
				break;
				
			case CommunicationManager.SUCCEED:
				if(msg.arg1 == 1){
					bean = (BusinessOrderBean) msg.obj;
					business_name.setText(bean.getTitle());
					business_zsfw.setText(bean.getZsfw());
					business_zycp.setText(bean.getZycp());
					business_cpdw.setText(bean.getCpdw());
					business_dmyq.setText(bean.getDmyq());
					business_pp.setText(bean.getPb());
					business_jbtze.setText(bean.getJbtz());
					
					business_pbfyd.setText(bean.getPbfyd());
					business_bzj.setText(bean.getBzj());
					business_jmf.setText(bean.getJmf());
					business_qynf.setText(bean.getQynf());
					business_xmqyyq.setText(bean.getXmqyyq());
					business_yjhbl.setText(bean.getYjhbl());
					business_yjhbzq.setText(bean.getYjhbzq());
					business_htqx.setText(bean.getHtqx());
					business_txqsyf.setText(bean.getTxqsyf());
					business_jmszs.setText(bean.getJmszs());
					business_txjysj.setText(bean.getTxjysj());
					business_fzms.setText(bean.getFzms());
					business_jyms.setText(bean.getJyms());
					business_sjlxfs.setText(bean.getSjlxfs());
					BitmapManager.getInstance().loadBitmap(bean.getThumb(), thumb, Tools.readBitmap(BusinessOrderDetailActivity.this, R.drawable.image6));
				} 
				break;

			default:
				break;
			}
		};
	};
	
	OnClickListener buttonClick=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			Intent intent = new Intent(BusinessOrderDetailActivity.this,BusinessActivity.class);
			BusinessGroup.getInstance().switchActivity("BusinessActivity",intent,-1,-1);
			//intent.putExtra("id", businessList.get(arg2).getUserid());
//			if(v.getId()==R.id.business_detail_back)
//			{
//				intent.putExtra("flag", "0");
//			}else
//				if(v.getId()==R.id.business_detail_search)
//				{
//					intent.putExtra("flag", "1");
//				}
			//startActivity(intent);
		}
	};
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			MoreGroup.getInstance().back();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	static boolean flag=true;
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.businessOrder_detail_back:
			MoreGroup.getInstance().back();
			break;
		case R.id.businessOrderTitle1CloseLayout:
			findViewById(R.id.businessOrderTitle1CloseLayout).setVisibility(View.GONE);
			//findViewById(R.id.businessOrderTitle1OpenLayout).setVisibility(View.VISIBLE);
			findViewById(R.id.contentLayout).setVisibility(View.VISIBLE);
			break;
		case R.id.businessOrderTitle1OpenLayout:
			findViewById(R.id.businessOrderTitle1CloseLayout).setVisibility(View.VISIBLE);
			//findViewById(R.id.businessOrderTitle1OpenLayout).setVisibility(View.GONE);
			findViewById(R.id.contentLayout).setVisibility(View.GONE);
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
				arg1 = View.inflate(BusinessOrderDetailActivity.this, R.layout.comment_listitem, null);
			}
			CommentBean commnet = mList.get(arg0);
			((TextView)arg1.findViewById(R.id.comment_name)).setText(commnet.username+":");
			((TextView)arg1.findViewById(R.id.comment_text)).setText(commnet.content);
			((TextView)arg1.findViewById(R.id.comment_time)).setText(commnet.creat_at);
			return arg1;
		}
		
	}
}
