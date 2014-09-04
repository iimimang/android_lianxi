package com.notbook.ui;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notbook.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.notbook.entity.MPhone;
import com.notbook.entity.Xiangqing;
import com.notbook.ui.ContactListAdapter.ViewHolder;
import com.notbook.ui.Hudianlist.TextAsyncTask;
import com.notbook.ui.Myaboutchuanyulist.TextAsyncTask3;

public class Myattentionlist extends BaseActivity {
//	private Xiangqing xqdetailBean;
//	public Xiangqing getXqdetailBean() {
//		return xqdetailBean;
//	}
//	public void setXqdetailBean(Xiangqing xqdetailBean) {
//		this.xqdetailBean = xqdetailBean;
//	}
	public static Xiangqing xqdetailBean;
	public static Xiangqing xqdetailBean1;
	private Button more_back;
	private Button iamgebutton1, iamgebutton2, iamgebutton3, iamgebutton4,
			iamgebutton5;
	private ListView list = null;
	private List<Xiangqing> xiangqing = new ArrayList<Xiangqing>();
	private List<Xiangqing> xiangqing1 = new ArrayList<Xiangqing>();
	private TextView xiangqingtext;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myattentionlist);
		findViewById(R.id.one).setBackgroundResource(background_photobg[num - 1]);
		findViewById(R.id.one2).setBackgroundResource(background_photobg1[num - 1]);
		findViewById(R.id.one1).setBackgroundResource(background_photobg1[num - 1]);
		more_back = (Button) findViewById(R.id.more_back);
		list = (ListView)findViewById(R.id.list);
		xiangqingtext = (TextView)findViewById(R.id.xiangqing);
		
		Bundle b = getIntent().getExtras();
		if(null!=b&&!b.equals("")){
			String id = b.getString("id");
			if(id.equals("2")){
				
				if(null!=Myattention.namelist&&!Myattention.namelist.equals("")){
					HangyelistAdater hla = new HangyelistAdater(xqdetailBean.getList(), Myattentionlist.this);
					list.setAdapter(hla);
					xiangqing.clear();
					xiangqing.addAll(xqdetailBean.getList());
					
					list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
								long id) {

							Intent it = new Intent(Myattentionlist.this, Industrydetail.class);
							it.putExtra("text", xiangqing.get(arg2).getText());
							startActivity(it);
						}
					});
				}
				xiangqingtext.setText("行业详情");
			}
			if(id.equals("1")){
				if(null!=Myattention.namelist&&!Myattention.namelist.equals("")){
					XiangmulistAdater hla1 = new XiangmulistAdater(xqdetailBean1.getList(), Myattentionlist.this);
					list.setAdapter(hla1);
					xiangqing1.clear();
					xiangqing1.addAll(xqdetailBean1.getList());
					list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
								long id) {

							Intent it = new Intent(Myattentionlist.this, Industrydetail.class);
							it.putExtra("text", xiangqing1.get(arg2).getText());
							startActivity(it);
						}
					});
				}
				xiangqingtext.setText("项目详情");
			}
		}
		
		
		more_back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
//				Intent intent = new Intent(Myattentionlist.this,Myattention.class);
//				startActivity(intent);
				finish();
			}
		});
		
		
		init();
	}
	public void init() {
		iamgebutton1 = (Button) findViewById(R.id.iamgebutton1);
		iamgebutton1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Myattentionlist.this,
						MainActivity0.class);
				startActivity(intent);
			}
		});
		iamgebutton2 = (Button) findViewById(R.id.iamgebutton2);
		iamgebutton2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Myattentionlist.this,
						MainActivity2.class);
				startActivity(intent);
			}
		});
		iamgebutton3 = (Button) findViewById(R.id.iamgebutton3);
		iamgebutton3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Myattentionlist.this,
						MainActivity3.class);
				startActivity(intent);
			}
		});
		iamgebutton4 = (Button) findViewById(R.id.iamgebutton4);
		iamgebutton4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Myattentionlist.this,
						MainActivity4.class);
				startActivity(intent);
			}
		});
		iamgebutton5 = (Button) findViewById(R.id.iamgebutton5);
		iamgebutton5.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Myattentionlist.this,
						MainActivity1.class);
				startActivity(intent);
			}
		});
	}
	public class HangyelistAdater extends BaseAdapter {
		private List<Xiangqing> xq ;
		private Context context ;

		public HangyelistAdater(List<Xiangqing> xq, Context context) {
			this.xq = xq;
			this.context = context;
		}

		@Override
		public int getCount() {
			return xq.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return xq.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (null == convertView) {
				convertView = LayoutInflater.from(context).inflate(R.layout.share_list10, null);
				holder = new ViewHolder();
				holder.contact = (TextView) convertView.findViewById(R.id.leixing);
				holder.time = (TextView) convertView.findViewById(R.id.time);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			
			holder.contact.setText(xq.get(position).getProjectName().toString());
			holder.time.setText(xq.get(position).getFoundTime().toString());
			return convertView;
		}
	}
	public class XiangmulistAdater extends BaseAdapter {
		private List<Xiangqing> xq ;
		private Context context ;

		public XiangmulistAdater(List<Xiangqing> xq, Context context) {
			this.xq = xq;
			this.context = context;
		}

		@Override
		public int getCount() {
			return xq.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return xq.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (null == convertView) {
				convertView = LayoutInflater.from(context).inflate(R.layout.share_list9, null);
				holder = new ViewHolder();
				holder.contact = (TextView) convertView.findViewById(R.id.leixing);
				holder.time = (TextView) convertView.findViewById(R.id.time);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			
			holder.contact.setText(xq.get(position).getProjectName().toString());
			holder.time.setText(xq.get(position).getFoundTime().toString());
			return convertView;
		}
	}
	static class ViewHolder {
		TextView contact;
		TextView time;
	}
}
