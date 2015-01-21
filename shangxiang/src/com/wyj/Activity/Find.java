package com.wyj.Activity;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;

import com.wyj.dataprocessing.AsynTaskHelper;
import com.wyj.dataprocessing.BitmapManager;
import com.wyj.dataprocessing.JsonToListHelper;
import com.wyj.dataprocessing.AsynTaskHelper.OnDataDownloadListener;

import com.wyj.http.WebApiUrl;
import com.wyj.Activity.R;
import com.wyj.utils.Tools;

import android.app.Activity;

import android.content.Context;

import android.content.Intent;

import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;

import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import android.widget.TextView;

import android.widget.Toast;

public class Find extends Activity {

	private List<Map<String, Object>> countriesStr = new ArrayList<Map<String, Object>>();
	private Spinner mySpinner;
	private ArrayAdapter<String> adapter;
	private int tid = 0; // 道场id的标识
	View views;
	List<Map<String, Object>> templelist_list;
	List<Map<String, Object>> order_list;

	private View moreView;
	private ListView mListView;
	private List<Map<String, Object>> Listdata; // 加载到适配器中的数据源
	private BaseListAdapter mAdapter;
	private int page = 1;
	private int pagesize = 30;
	private boolean isBottom = false;// 判断是否滚动到数据最后一条
	private int lastItemId;
	private int count;
	private PullToRefreshListView mPullRefreshListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Listdata = new ArrayList<Map<String, Object>>();
		View contenView = LayoutInflater.from(this.getParent()).inflate(
				R.layout.tab_find, null);

		setContentView(contenView);

		int detail_tid = getIntent().getIntExtra("tid", 0);

		if (detail_tid != 0) {
			tid = detail_tid; // 详情页面返回寺庙的传值
		}
		get_daochang_list(null, WebApiUrl.GET_TEMPLELIST, getParent());
		select_order_list();

		// mListView.setAdapter(new BaseListAdapter(this));
	}

	private void get_daochang_list(Map<String, Object> map, String url,
			final Context context) {

		mySpinner = (Spinner) findViewById(R.id.daochang_select);
		AsynTaskHelper asyntask = new AsynTaskHelper();
		asyntask.dataDownload(url, map, new OnDataDownloadListener() {
			@Override
			public void onDataDownload(String result) {
				if (result != null) {
					// Listdata.clear();
					List<Map<String, Object>> items;

					items = JsonToListHelper.gettemplelist_json(result);

					// 初始化------------------------------------
					Map<String, Object> defaultmap = new HashMap<String, Object>();
					defaultmap.put("templeid", 0);
					defaultmap.put("templename", "全部道场");

					countriesStr.add(defaultmap);
					countriesStr.addAll(items);

					// 初始化下拉选项------------------------------------
					List<String> countriesStrlist = new ArrayList<String>();
					countriesStrlist.add("全部道场");
					for (int i = 0; i < items.size(); i++) {
						Map<String, Object> map = items.get(i);
						countriesStrlist.add(map.get("templename").toString());
					}

					default_template_list(countriesStrlist);// 初始化适配数据

				} else {
					Toast.makeText(context, "网络异常", Toast.LENGTH_SHORT).show();
				}

			}
		}, context, "GET");

		// 建立数据源
		// List<templates> templates=new ArrayList<templates>();
		// templates.add(new templates("张三","张三", 1));
		// templates.add(new templates("李四", "李四",2));
		// TemplateAdapter TemplateAdapter=new TemplateAdapter(getBaseContext(),
		// templates);

		mySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// 获取item内容
				String data = arg0.getItemAtPosition(arg2).toString();

				for (int i = 0; i < countriesStr.size(); i++) {

					// countriesStr[i]=items.get(i).toString();
					Map<String, Object> map = countriesStr.get(i);
					if (map.get("templename").toString().equals(data)) {

						tid = (Integer) map.get("templeid");
						Log.i("aaaa", "------templeid更改为--------------" + tid);
						default_order_list();
					}
				}
				// templates templates=arg0.getItemAtPosition(arg2);

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}

		});
	}

	private void default_template_list(List<String> countriesStr) { // 默认加载
																	// 和更换道场加载

		adapter = new ArrayAdapter<String>(this, R.layout.template_spinner,
				countriesStr);
		/* myspinner_dropdown为自定义下拉菜单模式定义在res/layout目录下 */
		adapter.setDropDownViewResource(R.layout.template_spinner_items);
		// 绑定Adapter
		mySpinner.setAdapter(adapter);
	}

	private void default_order_list() { // 默认加载 和更换道场加载

		if (isBottom) {
			isBottom = false;
		}

		page = 1;
		Listdata.clear();
		// Log.i("bbbb","------变前"+Listdata.toString());

		listAdapter(null, WebApiUrl.GET_ORDERLIST + "?p=" + page + "&&pz="
				+ pagesize + "&&tid=" + tid, getParent()); // 默认加载第一页
	}

	private void select_order_list() {
		// TODO Auto-generated method stub
		mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.find_list);
		// 设置底部加载的字幕

		mPullRefreshListView.setMode(Mode.BOTH); // 支持上拉和下拉操作
		mPullRefreshListView.getLoadingLayoutProxy(false, true).setPullLabel(
				"加载中");
		mPullRefreshListView.getLoadingLayoutProxy(false, true)
				.setRefreshingLabel("正在加载");
		mPullRefreshListView.getLoadingLayoutProxy(false, true)
				.setReleaseLabel("上拉加载");

		// 下拉更新时间
		mPullRefreshListView
				.setOnRefreshListener(new OnRefreshListener2<ListView>() {

					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						// Do work to refresh the list here.
						// new
						// GetDataTask().execute(WebApiUrl.GET_ORDERLIST+"?p=5&&pz=1");
						pull_listAdapter(null, WebApiUrl.GET_ORDERLIST
								+ "?p=1&&pz=100&&tid=7", getParent());

					}

					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<ListView> refreshView) {

						if (!isBottom) {
							page++;
							morelistAdapter(null, WebApiUrl.GET_ORDERLIST
									+ "?p=" + page + "&&pz=" + pagesize
									+ "&&tid=" + tid, getParent());
						} else {
							Log.i("bbbb", "------bug出现的时候-----");

							Toast.makeText(getParent(), "没有了",
									Toast.LENGTH_SHORT).show();
							mPullRefreshListView.onRefreshComplete();

						}

					}
				});

		mListView = mPullRefreshListView.getRefreshableView();

		// mListView = (ListView) findViewById(R.id.find_list);
		mAdapter = new BaseListAdapter(getBaseContext(), Listdata);
		mListView.setAdapter(mAdapter);

		mPullRefreshListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				// String data = arg0.getItemAtPosition(arg2).toString();

				Integer orderid = (Integer) Listdata.get(arg2 - 1).get(
						"orderid");
				// 要跳转的Activity
				Intent intent = new Intent(Find.this, Find_item.class);

				Bundle bu = new Bundle(); // 这个组件 存值
				bu.putInt("orderid", orderid);
				bu.putInt("tid", tid);
				intent.putExtras(bu); // 放到 intent 里面 然后 传出去
				// 把Activity转换成一个Window，然后转换成View
				Log.i("bbbb", "------传了没传啊----" + orderid);
				FindGroupTab.getInstance().switchActivity("Find_item", intent,
						-1, -1);
			}
		});
	}

	private void listAdapter(Map<String, Object> map, String url,
			final Context context) {
		AsynTaskHelper asyntask = new AsynTaskHelper();
		asyntask.dataDownload(url, map, new OnDataDownloadListener() {

			@Override
			public void onDataDownload(String result) {
				if (result != null) {

					List<Map<String, Object>> items;
					items = JsonToListHelper.orderlist_json(result);

					Listdata.addAll(items);
					count = Listdata.size();
					mListView.setAdapter(mAdapter);
					mAdapter.notifyDataSetChanged();

					if (items.toString().equals("[]")) {
						isBottom = true;
					} else {
						lastItemId = (Integer) items.get(0).get("orderid");
					}

				} else {
					Toast.makeText(context, "网络异常", Toast.LENGTH_SHORT).show();
				}

			}
		}, context, "GET");
	}

	// 上拉加载操作-------
	private void morelistAdapter(Map<String, Object> map, String url,
			final Context context) {
		AsynTaskHelper asyntask = new AsynTaskHelper();
		asyntask.more_dataDownload(url, map, new OnDataDownloadListener() {

			@Override
			public void onDataDownload(String result) {
				if (result != null) {
					// Listdata.clear();
					List<Map<String, Object>> items;
					items = JsonToListHelper.orderlist_json(result);
					Listdata.addAll(items);
					count = Listdata.size();
					mAdapter.notifyDataSetChanged();

					if (items.toString().equals("[]")) {
						Toast.makeText(getParent(), "没有了", Toast.LENGTH_SHORT)
								.show();
						mPullRefreshListView.onRefreshComplete();
						isBottom = true;
					}

				} else {
					Toast.makeText(context, "网络异常", Toast.LENGTH_SHORT).show();
				}

			}
		}, context, "GET", mPullRefreshListView);
	}

	// 下拉更新操作-------
	private void pull_listAdapter(Map<String, Object> map, String url,
			final Context context) {
		AsynTaskHelper asyntask = new AsynTaskHelper();
		asyntask.pull_dataDownload(url, map, new OnDataDownloadListener() {

			@Override
			public void onDataDownload(String result) {
				if (result != null) {
					// Listdata.clear();
					List<Map<String, Object>> items;
					items = JsonToListHelper.orderlist_json(result);

					// Log.i("bbbb","------上拉如果没有数据的时候-----"+items.toString());
					if (items.toString().equals("[]")) {
						mPullRefreshListView.onRefreshComplete();
						Toast.makeText(context, "没有最新的啦", Toast.LENGTH_SHORT)
								.show();
					} else {

						lastItemId = (Integer) items.get(0).get("orderid");
						Listdata.addAll(0, items);
						mAdapter.notifyDataSetChanged();
						mPullRefreshListView.onRefreshComplete();
					}

				} else {
					Toast.makeText(context, "网络异常", Toast.LENGTH_SHORT).show();
				}

			}
		}, context, "GET");

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			FindGroupTab.getInstance().onKeyDown(keyCode, event);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private class BaseListAdapter extends BaseAdapter {

		private Context mContext;
		private LayoutInflater inflater;
		private List<Map<String, Object>> mData;

		public BaseListAdapter(Context mContext, List<Map<String, Object>> list) {
			this.mContext = mContext;
			inflater = LayoutInflater.from(mContext);
			this.mData = list;
		}

		public void addFirst(List<Map<String, Object>> items) {
			// TODO Auto-generated method stub

		}

		public void setValues(List<HashMap<String, Object>> listdata) {
			// TODO Auto-generated method stub
		}

		@Override
		public int getCount() {
			return this.mData.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder = null;
			if (convertView == null) {
				viewHolder = new ViewHolder();
				convertView = inflater.inflate(R.layout.items, null);

				viewHolder.img = (ImageView) convertView.findViewById(R.id.img);
				viewHolder.title = (TextView) convertView
						.findViewById(R.id.title);
				viewHolder.username = (TextView) convertView
						.findViewById(R.id.username);
				viewHolder.address = (TextView) convertView
						.findViewById(R.id.address);
				viewHolder.jiachi = (TextView) convertView
						.findViewById(R.id.jiachi);

				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}

			// viewHolder.img.setBackgroundResource(R.drawable.foot_07);
			BitmapManager.getInstance().loadBitmap(
					(String) this.mData.get(position).get("headface"),
					viewHolder.img,
					Tools.readBitmap(Find.this, R.drawable.foot_07));

			viewHolder.title.setText((CharSequence) this.mData.get(position)
					.get("wishtext"));
			viewHolder.username.setText((CharSequence) this.mData.get(position)
					.get("truename"));
			viewHolder.address.setText((CharSequence) this.mData.get(position)
					.get("templename"));
			viewHolder.jiachi.setText((CharSequence) this.mData.get(position)
					.get("wishname"));

			// viewHolder.jiachi.setOnClickListener(this);

			return convertView;
		}

		class ViewHolder {
			ImageView img;
			TextView title;
			TextView username;
			TextView address;
			TextView jiachi;

		}

		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		//
		// //要跳转的Activity
		// Intent intent = new Intent(Find.this, Find_item.class);
		// Bundle bu=new Bundle(); // 这个组件 存值
		// bu.putString("username", v.toString());
		// intent.putExtras(bu); //放到 intent 里面 然后 传出去
		// // 把Activity转换成一个Window，然后转换成View
		// Window w = FindGroupTab.group.getLocalActivityManager()
		// .startActivity("Find_item",intent);
		// View view = w.getDecorView();
		// //设置要跳转的Activity显示为本ActivityGroup的内容
		// FindGroupTab.group.setContentView(view);
		// }

	}

}
