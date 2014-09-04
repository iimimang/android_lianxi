package com.weibo.joechan;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notbook.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.notbook.app.Info;
import com.notbook.entity.MPhone;
import com.notbook.entity.Thing;
import com.notbook.ui.BaseActivity;
import com.notbook.ui.Chuanyudetail;
import com.notbook.ui.FriendList;
import com.notbook.ui.Login;
import com.notbook.ui.MainActivity0;
import com.notbook.ui.MainActivity1;
import com.notbook.ui.MainActivity2;
import com.notbook.ui.MainActivity3;
import com.notbook.ui.MainActivity4;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.openapi.WXMediaMessage;
import com.tencent.mm.sdk.openapi.WXTextObject;
import com.tencent.mm.sdk.openapi.WXWebpageObject;
import com.weibo.sina.AuthorizeActivity;
import com.weibo.tencent.MainActivity;

/**
 * 微博demo主界面，若想分享图片，请在SD卡根目录放一张abc.jpg图片 若想用任意图片，稍稍修改下demo即可。希望大家使用愉快！
 * 
 * @author Joe Chan QQ370165545
 * @data 2012/10/16
 */
public class ShareIndexAty extends BaseActivity implements Serializable {
	private Intent intent1, intent2;
	private Bundle bundle1, bundle11, bundle2, bundle22;
	private TextView textview;
	private ImageView imageview;
	private Button btn,more_back;
	private String imageUrl = ""; // 这就是你需要显示的网络图片---网上随便找的
	Bitmap bmImg;
	private static final String WX_APP_ID = "wx1aad97beeed580ea";
	private IWXAPI api;
	private String text = "你好！";
	private Button iamgebutton1,iamgebutton2,iamgebutton3,iamgebutton4,iamgebutton5;
	private ListView sharelist ;
	private String type_type = "";
	private String type_num = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.index);
		findViewById(R.id.one).setBackgroundResource(background_photobg[num - 1]);
		findViewById(R.id.one2).setBackgroundResource(background_photobg1[num - 1]);
		findViewById(R.id.one1).setBackgroundResource(background_photobg1[num - 1]);
		init();
		btn = (Button) findViewById(R.id.button1);
		more_back = (Button) findViewById(R.id.more_back);
		textview = (TextView) findViewById(R.id.text);
		sharelist = (ListView) findViewById(R.id.sharelist);
		
		imageview = (ImageView) findViewById(R.id.image);
		
		
		
		String url = Info.uri+"/shareNumAction.php?uphone="+Login.uphone;
		Log.d( "log", "-------------str"+url);
        new TextAsyncTask().execute(url);
		// Bitmap bitmap =
		// getHttpBitmap(imageUrl);
		// imageview.setImageBitmap(returnBitMap(imageUrl));
		// imageview.setImageBitmap(bitmap);
		Bitmap bitmap = getLoacalBitmap(imageUrl);
		imageview.setImageBitmap(bitmap);
		
		
		more_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ShareDialog().show();
			}
		});
		
		api = WXAPIFactory.createWXAPI(ShareIndexAty.this, WX_APP_ID, true);
		api.registerApp(WX_APP_ID);
	}

	public static Bitmap getHttpBitmap(String url) {
		URL myFileUrl = null;
		Bitmap bitmap = null;
		try {
			myFileUrl = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		try {
			HttpURLConnection conn = (HttpURLConnection) myFileUrl
					.openConnection();
			conn.setConnectTimeout(0);
			conn.setDoInput(true);
			conn.connect();
			InputStream is = conn.getInputStream();
			bitmap = BitmapFactory.decodeStream(is);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	public static Bitmap getLoacalBitmap(String url) {
		try {
			FileInputStream fis = new FileInputStream(url);
			return BitmapFactory.decodeStream(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	public AlertDialog ShareDialog() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		// map.put("img",R.drawable.sina);
		map.put("text", "   新浪微博");
		list.add(map);
		Map<String, Object> map1 = new HashMap<String, Object>();
		// map1.put("img",R.drawable.tencent);
		map1.put("text", "   腾讯微博");
		list.add(map1);
		Map<String, Object> map2 = new HashMap<String, Object>();
		// map1.put("img",R.drawable.tencent);
		map2.put("text", "   微信朋友圈");
		list.add(map2);
		SimpleAdapter adapter = new SimpleAdapter(this, list, R.layout.share,
				new String[] { "img", "text" }, new int[] { R.id.ivshareimg,
						R.id.tvsharevalues });
		return new AlertDialog.Builder(this)
				.setAdapter(adapter, new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface paramDialogInterface,
							int paramInt) {
						switch (paramInt) {
						case 0:
							bundle1 = new Bundle();
							bundle1.putString("msg1", "@记事本：\n"+textview.getText()
									.toString());
							bundle1.putString("imageUrl", imageUrl);
							intent1 = new Intent(ShareIndexAty.this,
									AuthorizeActivity.class);
							intent1.putExtras(bundle1);
							startActivity(intent1);
							break;
						case 1:
							bundle2 = new Bundle();
							bundle2.putString("msg2", "@记事本：\n"+textview.getText()
									.toString());
							bundle2.putString("imageUrl", imageUrl);
							intent2 = new Intent(ShareIndexAty.this,
									MainActivity.class);
							intent2.putExtras(bundle2);
							startActivity(intent2);
							break;
						case 2:
							bundle2 = new Bundle();
							bundle2.putString("msg2", "@记事本：\n"+textview.getText()
									.toString());
							bundle2.putString("imageUrl", imageUrl);
							Toast.makeText(ShareIndexAty.this, "分享微信！", 0).show();
							WXTextObject textObj = new WXTextObject();
							textObj.text = textview.getText().toString();

							WXMediaMessage msg = new WXMediaMessage();
							msg.mediaObject = textObj;
							msg.title = "记事本";
							msg.description = "分享微信";

							SendMessageToWX.Req req = new SendMessageToWX.Req();
							req.transaction = String.valueOf(System.currentTimeMillis());
							req.message = msg;
							req.scene = SendMessageToWX.Req.WXSceneTimeline;
							api.sendReq(req);

							break;
						}

					}
				}).setTitle("分享到").setNegativeButton("关闭", null).create();
	}
	public void init(){
		iamgebutton1 = (Button) findViewById(R.id.iamgebutton1);
		iamgebutton1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ShareIndexAty.this, MainActivity0.class);
				startActivity(intent);
			}
		});
		iamgebutton2 = (Button) findViewById(R.id.iamgebutton2);
		iamgebutton2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ShareIndexAty.this, MainActivity2.class);
				startActivity(intent);
			}
		});
		iamgebutton3 = (Button) findViewById(R.id.iamgebutton3);
		iamgebutton3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ShareIndexAty.this, MainActivity3.class);
				startActivity(intent);
			}
		});
		iamgebutton4 = (Button) findViewById(R.id.iamgebutton4);
		iamgebutton4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ShareIndexAty.this, MainActivity4.class);
				startActivity(intent);
			}
		});
		iamgebutton5 = (Button) findViewById(R.id.iamgebutton5);
		iamgebutton5.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ShareIndexAty.this, MainActivity1.class);
				startActivity(intent);
			}
		});
	}
class TextAsyncTask extends AsyncTask<String, Integer, String> {

		
		
		@Override
		protected String doInBackground(String... params) {
			return getResultByPost(params[0]);
		}
		@Override
		protected void onPreExecute() {
			dia = new ProgressDialog(ShareIndexAty.this);
			dia.setMessage("正在加载...");
			dia.show();
			dia.setCanceledOnTouchOutside(false);
			super.onPreExecute();
		}
		@Override
		protected void onPostExecute(String result) {
			if (result == null) {
				Toast.makeText(ShareIndexAty.this, "网络加载失败..", 0).show();
				dia.dismiss();
			} else {
				dia.dismiss();
				Log.d("log", "---------------------result="+result);
				JSONObject object;
				try {
//					try {
//						result = new String(result.getBytes("UTF-8"), "GBK");
//					} catch (UnsupportedEncodingException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
					object = new JSONObject(result);
					String share = object.getString("result"); 
					if(!share.equals("false")){
						Gson gson = new Gson();
						Type type = new TypeToken<List<Thing>>() {}.getType();
						List<Thing> things = gson.fromJson(share, type);
						Log.d("log", "---------------------things="+things);
						ShareListAdapter hlada = new ShareListAdapter(ShareIndexAty.this, things);
						sharelist.setAdapter(hlada);  
						StringBuffer str = new StringBuffer();
						String content = "";
						for(int i= 0;i<things.size();i++){
							type_type = things.get(i).getType();
							type_num  = things.get(i).getNum();
							content = type_type +"           "+ type_num+"\n";
							content = str.append(content).toString();
						}
						textview.setText("@记事本：\n"+content);
					}
					
				} catch (JSONException e) {
				e.printStackTrace();
				}
			}   
			super.onPostExecute(result);
		}
	}

	private String getResultByPost(String url) {
		String result = null;
		try{
			HttpGet request=new HttpGet(url);
			HttpClient client=new DefaultHttpClient();
		    HttpResponse response=client.execute(request);
		   
		    HttpEntity entity = response.getEntity();
        	result = EntityUtils.toString(entity);
        	
		}catch(IOException e){
			e.printStackTrace();
		}
		return result;
	}
	class ShareListAdapter extends BaseAdapter {
		private Context xontext;
		private List<Thing> list;
		public ShareListAdapter(Context xontext, List<Thing> list) {
			this.xontext = xontext;
			this.list = list;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = View.inflate(getApplicationContext(),R.layout.share_list5, null);
				holder.leixing=(TextView)convertView.findViewById(R.id.leixing);
				holder.time =(TextView)convertView.findViewById(R.id.time);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.leixing.setText(list.get(position).getType());
			holder.time.setText(list.get(position).getNum());
			
			return convertView;
		}
	}
	static class ViewHolder {
		TextView leixing;
		TextView time;
	}
}