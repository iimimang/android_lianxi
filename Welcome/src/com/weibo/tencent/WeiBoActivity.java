package com.weibo.tencent;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notbook.R;
import com.weibo.joechan.ShareIndexAty;
import com.weibo.joechan.util.DisplayUtil;
import com.weibo.tencent.service.Weibo;

/**
 * @author coolszy
 * @date 2012-10-16
  */

public class WeiBoActivity extends Activity {
	public static final String MYTAG = "xiaoshi";
	private Button btnAdd;
	private String img_path, imageUrl;
	private Button mSend;
	private ImageView iv_deitImage, ivDelPic;
	private TextView text_num;
	private EditText edit_text;
	private Bitmap myBitmap;
	private ContentResolver cr;
	private Bitmap bitmap;
	private byte[] mContent;
	private String text;
	private Uri uri;
	private Intent intent;
	private String str;
	private Bundle bundle;
	private Weibo weibo;
	private String result;

	private String accessToken;
	private String accessTokenSecret;
	private Handler handler = new Handler();
	private ProgressDialog progressDialog = null;

	private static final String TAG = "MainActivity";
	public static final int WEIBO_MAX_LENGTH = 140;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weibo_sendbox);
		DisplayUtil.initWindows(this);
		Intent intent = getIntent();
		accessToken = intent.getExtras().getString("accessToken");
		accessTokenSecret = intent.getExtras().getString("accessTokenSecret");
		btnAdd = (Button) findViewById(R.id.btnkill);
		mSend = (Button) findViewById(R.id.btnSendTencent);
		text_num = (TextView) findViewById(R.id.text_num);
		edit_text = (EditText) findViewById(R.id.edit_text);
		bundle = getIntent().getExtras();
		imageUrl = bundle.getString("imageUrl");
		Log.e("uri1", "-------ggggg------->" + imageUrl);
		img_path = imageUrl;
		Log.e("uri1", "-------ggggg------->" + img_path);
		edit_text.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				String mText = edit_text.getText().toString();
				String mStr;
				int len = mText.length();
				if (len <= WEIBO_MAX_LENGTH) {
					len = WEIBO_MAX_LENGTH - len;
					text_num.setTextColor(R.color.text_num_gray);
					if (!mSend.isEnabled())
						mSend.setEnabled(true);
				} else {
					len = len - WEIBO_MAX_LENGTH;

					text_num.setTextColor(Color.RED);
					if (mSend.isEnabled())
						mSend.setEnabled(false);
				}
				text_num.setText(String.valueOf(len));
			}
		});
		edit_text.setText(bundle.getString("msg2"));
		iv_deitImage = (ImageView) findViewById(R.id.edit_image);
		// loadRmoteImage(bundle.getString("imageUrl"));
		// Bitmap bitmap =
		// getHttpBitmap(bundle.getString("imageUrl"));
		// imageview.setImageBitmap(returnBitMap(imageUrl));
		// iv_deitImage.setImageBitmap(bitmap);
		Bitmap bitmap = getLoacalBitmap(bundle.getString("imageUrl"));
		iv_deitImage.setImageBitmap(bitmap);
		// img_path=bundle.getString("iamgeUrl");
		ivDelPic = (ImageView) findViewById(R.id.ivDelPic);

		btnAdd.setOnClickListener(listener);
		mSend.setOnClickListener(listener);
		ivDelPic.setOnClickListener(listener);
		// iv_deitImage.setOnClickListener(listener);

	}

	public static Bitmap getPicFromBytes(byte[] bytes,
			BitmapFactory.Options opts) {
		if (bytes != null)
			if (opts != null)
				return BitmapFactory.decodeByteArray(bytes, 0, bytes.length,
						opts);
			else
				return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
		return null;
	}

	public static byte[] readStream(InputStream in) throws Exception {
		byte[] buffer = new byte[1024];
		int len = -1;
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();

		while ((len = in.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		byte[] data = outStream.toByteArray();
		outStream.close();
		in.close();
		return data;
	}

	// private void loadRmoteImage(String imgUrl){
	// URL fileURL=null;
	// Bitmap bitmap=null;
	// try {
	// fileURL=new URL(imgUrl);
	// } catch (MalformedURLException err) {
	// // TODO: handle exception
	// err.printStackTrace();
	// }
	// try {
	// HttpURLConnection conn = (HttpURLConnection) fileURL.openConnection();
	// conn.setDoInput(true);
	// conn.connect();
	// InputStream is = conn.getInputStream();
	// int length = (int) conn.getContentLength();
	// if (length != -1) {
	// byte[] imgData = new byte[length];
	// byte[] buffer = new byte[512];
	// int readLen = 0;
	// int destPos = 0;
	// while ((readLen = is.read(buffer)) > 0) {
	// System.arraycopy(buffer, 0, imgData, destPos,readLen);
	// destPos+=readLen;
	// }
	// bitmap = BitmapFactory.decodeByteArray(imgData, 0,imgData.length);
	// }
	// } catch (IOException e) {
	// // TODO: handle exception
	// e.printStackTrace();
	// }
	// iv_deitImage.setImageBitmap(bitmap);
	// }

	// public static Bitmap getHttpBitmap(String url) {
	// URL myFileUrl = null;
	// Bitmap bitmap = null;
	// try {
	// myFileUrl = new URL(url);
	// } catch (MalformedURLException e) {
	// e.printStackTrace();
	// }
	// try {
	// HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
	// conn.setConnectTimeout(0);
	// conn.setDoInput(true);
	// conn.connect();
	// InputStream is = conn.getInputStream();
	// bitmap = BitmapFactory.decodeStream(is);
	// is.close();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// return bitmap;
	// }
	public static Bitmap getLoacalBitmap(String url) {
		try {
			FileInputStream fis = new FileInputStream(url);
			return BitmapFactory.decodeStream(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			Uri uri = data.getData();
			String[] proj = { MediaStore.Images.Media.DATA };

			Cursor actualimagecursor = managedQuery(uri, proj, null, null, null);

			int actual_image_column_index = actualimagecursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

			actualimagecursor.moveToFirst();

			img_path = actualimagecursor.getString(actual_image_column_index);
			Log.e("uri1", "-------dd------->" + img_path);
			File file = new File(img_path);
			Log.e("uri2", "-------dd------->" + img_path);
			Log.e("uri", uri.toString());
			cr = this.getContentResolver();
			try {
				bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
				/* 将Bitmap设定到ImageView */
				iv_deitImage.setImageBitmap(bitmap);
			} catch (FileNotFoundException e) {
				Log.e("Exception", e.getMessage(), e);
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private OnClickListener listener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			weibo = new Weibo();
			result = "";
			text = (edit_text.getText()).toString();
			switch (v.getId()) {
			/*
			 * case R.id.edit_image: Intent intent = new Intent();
			 * 开启Pictures画面Type设定为image intent.setType("image/*");
			 * 使用Intent.ACTION_GET_CONTENT这个Action
			 * intent.setAction(Intent.ACTION_GET_CONTENT); 取得相片后返回本画面
			 * startActivityForResult(intent, 1); // result =
			 * weibo.add(accessToken,accessTokenSecret, "json", "weibo",
			 * "172.17.19.97"); break;
			 */
			case R.id.btnSendTencent:
//				progressDialog = ProgressDialog.show(WeiBoActivity.this,
//						"请稍等...", "分享中...", true);

				new Thread(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						// 向服务器请求数据
						String[] picsPath = new String[] { img_path, img_path };
						result = weibo.addWithPic(accessToken,
								accessTokenSecret, "json", text,
								"172.17.19.97", picsPath);
						// 更新完列表数据，则关闭对话框
//						progressDialog.dismiss();
     					WeiBoActivity.this.fileList();
						WeiBoActivity.this.finish();
					}
				}).start();
				Toast.makeText(WeiBoActivity.this, R.string.send_sucess, Toast.LENGTH_LONG).show();
				break;
			case R.id.btnkill:
				Intent it = new Intent(WeiBoActivity.this, ShareIndexAty.class);
				startActivity(it);
				WeiBoActivity.this.finish();
				break;
			default:
				break;
			}
			Log.i(TAG+"result", result);
		}
	};

}
