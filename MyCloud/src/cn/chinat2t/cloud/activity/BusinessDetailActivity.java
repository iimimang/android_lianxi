package cn.chinat2t.cloud.activity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.PublicKey;
import java.util.List;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.SyncStateContract.Constants;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.chinat2t.cloud.R;
import cn.chinat2t.cloud.bean.BusinessListBean;
import cn.chinat2t.cloud.bean.CommentBean;
import cn.chinat2t.cloud.http.BitmapManager;
import cn.chinat2t.cloud.http.CommunicationManager;
import cn.chinat2t.cloud.http.CommunicationResultListener;
import cn.chinat2t.cloud.utils.CtLog;
import cn.chinat2t.cloud.utils.Tools;

public class BusinessDetailActivity extends Activity{

	private static final String CONSUMER_KEY = "972949479";// �滻Ϊ�����ߵ�appkey������"1646212860";
	private static final String CONSUMER_SECRET = "6e30351724ab9afe0f59e73acfce06d8";// �滻Ϊ�����ߵ�appkey������"1646212860";
	private static final String CONSUMER_URL = "http://www.baidu.com";//�滻Ϊ�����ߵ�redirect_url
	private ImageView business_Logo = null;
	private TextView business_name,business_address,business_tel;
	private TextView business_zyyw,business_gsjj,business_lxr,business_lxdh,business_cz;
	//private ListView commListView = null;
	private Button backBtn,searchBtn;
	private CommentAdapter cAdapter = null;
	private LinearLayout sharedWeibo=null;
	private String id = "";
	private BusinessListBean bean  = null;
	private SharedPreferences pre;
	private Editor editor;
	//private List<CommentBean> commentList = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		id = getIntent().getStringExtra("id");
		setContentView(R.layout.business_detail_layout);
//		Bitmap map=getHttpBitmap("http://122.49.1.116/users/1006/upload/2013/0515/20130515091608119.jpg");
//		Log.i("wyq", "map"+map.getWidth());
		
		initViews();
		initData();
	}

		
		// ͼƬ���뵽SD��
		 public void storeImageToSDCARD(Bitmap colorImage, String ImageName,
		   String path) {
		  File file = new File(path);
		  if (!file.exists()) {
		   file.mkdir();
		   Log.i("wyq", "1111");
		  }
		  File imagefile = new File(file, ImageName + ".png");
		  if (!imagefile.exists()) {
		   try {
			   Log.i("wyq", "1112");
		    imagefile.createNewFile();
		    Log.i("wyq", "1114");
		    FileOutputStream fos = new FileOutputStream(imagefile);
		    Log.i("wyq", "1115");
		    colorImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
		    Log.i("wyq", "1116");
		    fos.flush();
		    fos.close();
		   } catch (Exception e) {
		    e.printStackTrace();
		   }
		  } else {
			  Log.i("wyq", "1113");
		  }
		 }



	private void initViews(){
	//	pre = getSharedPreferences("setting", MODE_PRIVATE);
//		Weibo weibo = Weibo.getInstance();
//		weibo.setupConsumerConfig(CONSUMER_KEY, CONSUMER_SECRET);
//		weibo.setRedirectUrl(REDIRECT_URL);
		//mWeibo=Weibo.getInstance(CONSUMER_KEY, REDIRECT_URL);
		//View view=LayoutInflater.from(getParent().getParent()).inflate(R.layout.business_detail_layout, null);
		sharedWeibo=(LinearLayout)findViewById(R.id.sharWeibo);
		business_Logo = (ImageView) findViewById(R.id.business_detail_pic);
		business_name = (TextView) findViewById(R.id.business_detail_name);
		business_address = (TextView) findViewById(R.id.business_detail_address1);
		business_tel = (TextView) findViewById(R.id.business_detail_tel1);
		business_zyyw=(TextView) findViewById(R.id.business_detail_zyyw);
		
		business_gsjj=(TextView) findViewById(R.id.business_detail_csjj);
		business_lxr=(TextView) findViewById(R.id.business_detail_lxr);
		business_lxdh=(TextView) findViewById(R.id.business_detail_lxdh);
		business_cz=(TextView) findViewById(R.id.business_detail_cz);
		backBtn=(Button)findViewById(R.id.business_detail_back);
		searchBtn=(Button)findViewById(R.id.business_detail_search);
		backBtn.setOnClickListener(buttonClick);
		searchBtn.setOnClickListener(buttonClick);
		//findViewById(R.id.product_detail_back).setOnClickListener(this);
		//commListView = (ListView) findViewById(R.id.product_comment_list);
		//cAdapter = new CommentAdapter();
		//commListView.setAdapter(cAdapter);
	}
	
	private void initData(){
		CommunicationManager.getInstance().getBusinessDetail(id, detailResultListener);
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
					bean = (BusinessListBean) msg.obj;
					business_name.setText(bean.getC_name());
					business_address.setText(bean.getGsdz());
					business_tel.setText(bean.getLxdh());
					business_zyyw.setText(bean.getZyyw());
					business_gsjj.setText(bean.getGsjj());
					business_lxr.setText(bean.getLxr());
					business_lxdh.setText(bean.getLxdh());
					business_cz.setText(bean.getCz());
					BitmapManager.getInstance().loadBitmap(bean.getLogo(), business_Logo, Tools.readBitmap(BusinessDetailActivity.this, R.drawable.image6));
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
			//BusinessGroup.getInstance().back();
//			Intent intent=new Intent(BusinessDetailActivity.this, BusinessActivity.class);
//			BusinessGroup.getInstance().switchActivity("BusinessActivity", intent, -1, -1);
			finish();
//			Intent intent = new Intent(BusinessDetailActivity.this,BusinessActivity.class);
//			BusinessGroup.getInstance().switchActivity("BusinessActivity",intent,-1,-1);
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
			//BusinessGroup.getInstance().back();
			finish();
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

//	@Override
//	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.business_detail_back:
//			Log.i("wyq", "businessDetailActivityִ����");
//			
//			break;
//
//		default:
//			break;
//		}
//	}
	
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
				arg1 = View.inflate(BusinessDetailActivity.this, R.layout.comment_listitem, null);
			}
			CommentBean commnet = mList.get(arg0);
			((TextView)arg1.findViewById(R.id.comment_name)).setText(commnet.username+":");
			((TextView)arg1.findViewById(R.id.comment_text)).setText(commnet.content);
			((TextView)arg1.findViewById(R.id.comment_time)).setText(commnet.creat_at);
			return arg1;
		}
		
	}
    //�û�ע���߳�
//    class WeiboThread implements Runnable
//    {	
//		public void run() 
//		{
//			Log.i("wyq", "����ִ����û"+getParent());
//			Looper.prepare();
//			Log.i("wyq", "����ִ����û2"+getParent());
//			Log.i("wyq", "����ִ����û3"+getParent().getParent());
//			mWeibo.authorize(getParent(), new AuthDialogListener());
//		}
//	}
    
//    class AuthDialogListener implements  WeiboAuthListener {
//
//		@Override
//		public void onComplete(Bundle values) {
//			Log.i("wyq", "����ִ����û1");
//			//mWeibo.accessToken.ge
//			String token = values.getString("access_token");
//			String expires_in = values.getString("expires_in");
//			System.out.println("token:"+token+"expires_in"+expires_in);
//			editor = pre.edit();
//			editor.putString("token", token);
//			editor.putString("expires_in", expires_in);
//			boolean b = editor.commit();
//			
//			Log.i("wyq", "logo="+bean.getLogo()+"|!!!");
//			Bitmap bitmap=getHttpBitmap(bean.getLogo());
//			Log.i("wyq", "ִ����1="+bitmap);
//			ByteArrayOutputStream os = new ByteArrayOutputStream();  
//			bitmap.compress(CompressFormat.PNG, 100,os); 
//			byte[] bytes = os.toByteArray();
//			Log.i("wyq", "ִ����2="+bytes.length);
//			if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
//				Log.i("wyq", "ִ����3");
//			try {
////				File sdCardDir = Environment.getExternalStorageDirectory();
////				Log.i("wyq", "ִ����301");
////			
////				File saveFile = new File(sdCardDir, "pic1.png");
//				//Log.i("wyq", "ִ����="+saveFile);
////				FileOutputStream outStream =getApplicationContext().openFileOutput("pic1.png", Context.MODE_PRIVATE);
////				Log.i("wyq", "ִ����306");
////				outStream.write(bytes);
////				Log.i("wyq", "ִ����308");
////				outStream.close();
////				Log.i("wyq", "ִ����2");
////				
//				File file = Environment.getExternalStorageDirectory();
//                String sdPath = file.getAbsolutePath();
//                // �뱣֤SD����Ŀ¼��������ͼƬ�ļ�
//                String picPath = sdPath + "/" + "pic.png";
//                File picFile = new File(picPath);
//                if (!picFile.exists()) {
//                	Log.i("wyq", "ͼƬ������");
//                   Toast.makeText(BusinessDetailActivity.this, "ͼƬ������", 2).show();
//                    picPath = null;
//                }
//                try {
//                	//Weibo.getInstance().share2weibo(BusinessDetailActivity.this, Weibo.getInstance().getAccessToken().getToken(), Weibo.getInstance().getAccessToken().getSecret(), "abc", picPath);
////                    Intent i = new Intent(TestActivity.this, ShareActivity.class);
////                    TestActivity.this.startActivity(i);
//                	uploadStatus(picFile, "���", "http://api.weibo.com/2/statuses/upload.json");
//                } catch (Exception e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                } finally {
//
//                }
//
//				
//			//	share2weibo("abc", );
//				//com.weibo.net.Weibo weibo=com.weibo.net.Weibo.getInstance();
//				//uploadStatus(saveFile, "���", "https://api.weibo.com/2/statuses/upload.json");
//				Log.i("wyq", "ִ����1");
//			} catch (Exception e) {
//				// TODO: handle exception
//				e.printStackTrace();
//			}
//				
//
//
//
//				}
//
//
//			//File file=new 
//		}
//
//		/**
//		 * �����ͼƬ��΢��
//		 * 
//		 * @param token
//		 * @param tokenSecret
//		 * @param status
//		 *            ���������
//		 * @param urlPath
//		 *            ͼƬ�ĵ�ַ   ������û���õ�����ֱ���ں����д����ġ�
//		 * @return
//		 */
//		/** 
//	     * �����ͼƬ��΢�� 
//	     * @param token 
//	     * @param tokenSecret 
//	     * @param aFile 
//	     * @param status 
//	     * @param urlPath 
//	     * @return 
//	     */  
//
//
//	    public String uploadStatus( File aFile, String status, String urlPath) {  
//	    	DefaultOAuthConsumer httpOAuthConsumer = new DefaultOAuthConsumer("972949479","6e30351724ab9afe0f59e73acfce06d8");  
//	      //  httpOAuthConsumer.setTokenWithSecret(Weibo.getInstance(CONSUMER_KEY, "http://www.baidu.com").accessToken.getToken(),null);  
//	        String result = null;  
//	        try {  
//	        	Log.i("wyq", "ִ����1111");
//	            URL url = new URL(urlPath);  
//	            HttpURLConnection request = (HttpURLConnection) url.openConnection();  
//	            request.setDoOutput(true);  
//	            request.setRequestMethod("POST"); 
//	            request.setUseCaches(false);
//	            HttpParameters para = new HttpParameters();  
//	            para.put("status", URLEncoder.encode(status,"utf-8").replaceAll("\\+", "%20"));  
//	            String boundary = "---------------------------37531613912423";  
//	            String content = "--"+boundary+"\r\nContent-Disposition: form-data; name=\"status\"\r\n\r\n";  
//	            String pic = "\r\n--"+boundary+"\r\nContent-Disposition: form-data; name=\"pic\"; filename=\"image.jpg\"\r\nContent-Type: image/jpeg\r\n\r\n";  
//	            byte[] end_data = ("\r\n--" + boundary + "--\r\n").getBytes();   
//	            FileInputStream stream = new FileInputStream(aFile);  
//	            byte[] file = new byte[(int) aFile.length()];  
//	            stream.read(file); 
//	            Log.i("wyq", "ִ����2222");
//	            request.setRequestProperty("Content-Type", "multipart/form-data; boundary="+boundary); //���ñ?���ͺͷָ���   
//	            Log.i("wyq", "ִ����2222");
//	            request.setRequestProperty("Content-Length", String.valueOf(content.getBytes().length + status.getBytes().length + pic.getBytes().length + aFile.length() + end_data.length)); //�������ݳ���   
//	            Log.i("wyq", "ִ����2222");
//	            httpOAuthConsumer.setAdditionalParameters(para);  
//	            Log.i("wyq", "ִ����2222"+httpOAuthConsumer);
//	            Log.i("wyq", "ִ����2222"+request);
//	            httpOAuthConsumer.sign(request);  
//	            Log.i("wyq", "ִ����2222"+request.getOutputStream());
//	            OutputStream ot = request.getOutputStream();  
//	            Log.i("wyq", "ִ����2222");
//	            ot.write(content.getBytes()); 
//	            Log.i("wyq", "ִ����1111");
//	            ot.write(status.getBytes());  
//	            ot.write(pic.getBytes());  
//	            Log.i("wyq", "ִ����1111");
//	            ot.write(file);  
//	            ot.write(end_data);  
//	            ot.flush();  
//	            ot.close();  
//	           request.connect();  
//		        Log.i("wyq", "request.getResponseCode="+request.getResponseCode());
//	            if (200 == request.getResponseCode()) {  
//	                result = "SUCCESS";  
//	            }  
//	        } catch (Exception e1) {  
//	            e1.printStackTrace();  
//	        } 
//	        Log.i("wyq", "result="+result);
//	        return result;  
//	    }   
//
//		
//		
//
//
//
//		public void onCancel() {
//			Toast.makeText(getApplicationContext(), "Auth cancel : ",
//					Toast.LENGTH_LONG).show();
//		}
//
//
//		@Override
//		public void onError(WeiboDialogError arg0) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public void onWeiboException(WeiboException arg0) {
//			// TODO Auto-generated method stub
//			
//		}
//	}
	public static Bitmap getHttpBitmap(String url) {
		Log.i("wyq", "ִ����1");
		URL myFileUrl = null;
		Bitmap bitmap = null;
		try {
			myFileUrl = new URL(url);
			Log.i("wyq", "ִ����2");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		try {
			Log.i("wyq", "ִ����3");
			HttpURLConnection conn = (HttpURLConnection) myFileUrl
					.openConnection();
			Log.i("wyq", "ִ����4");
			conn.setConnectTimeout(0);
			Log.i("wyq", "ִ����5");
			conn.setDoInput(true);
			Log.i("wyq", "ִ����6");
			//conn.connect();
			Log.i("wyq", "ִ����7");
			InputStream is = conn.getInputStream();
			bitmap = BitmapFactory.decodeStream(is);
			Log.i("wyq", "ִ����6"+bitmap);
			is.close();
			conn.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bitmap;
	}
}
