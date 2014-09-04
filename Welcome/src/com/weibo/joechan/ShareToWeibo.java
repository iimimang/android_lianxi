package com.weibo.joechan;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.SimpleAdapter;

import com.example.notbook.R;
import com.weibo.sina.AuthorizeActivity;
import com.weibo.tencent.MainActivity;

/**
 * 分享到微博类
 * 
 * @author Joe Chan
 * @data 2012/10/16
 * 
 */
public class ShareToWeibo {
	private static ShareToWeibo mShareToWeibo = null;
	private Context context;
	private String text;
	private String imagePath;

	/**
	 * 分享到微博类的构造函数
	 * 
	 * @param string
	 *            这就是你需要分享的文本内容
	 * @param imageUrl
	 *            这就是你需要分享的图片的本地地址
	 */
	public ShareToWeibo(Context context, String text, String imagePath) {
		this.context = context;
		this.text = text;
		this.imagePath = imagePath;
	}

	// 单例模式
	public synchronized static ShareToWeibo getInstance(Context context,
			String text, String imagePath) {
		if (mShareToWeibo == null) {
			mShareToWeibo = new ShareToWeibo(context, text, imagePath);
		}
		return mShareToWeibo;
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

	//直接调用，跳转到分享页面
	public void ShareTo(String tag) {
		if (tag.equals("sina")) {
			Bundle bundle1 = new Bundle();
			bundle1.putString("msg1", text);
			bundle1.putString("imageUrl", imagePath);
			Intent intent1 = new Intent(context, AuthorizeActivity.class);
			intent1.putExtras(bundle1);
			context.startActivity(intent1);
		} else if (tag.equals("tencent")) {
			Bundle bundle2 = new Bundle();
			bundle2.putString("msg2", text);
			bundle2.putString("imageUrl", imagePath);
			Intent intent2 = new Intent(context, MainActivity.class);
			intent2.putExtras(bundle2);
			context.startActivity(intent2);
		}
	}

	// 显示对话框形式，让用户选择
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
		SimpleAdapter adapter = new SimpleAdapter(context, list,
				R.layout.share, new String[] { "img", "text" }, new int[] {
						R.id.ivshareimg, R.id.tvsharevalues });
		return new AlertDialog.Builder(context)
				.setAdapter(adapter, new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface paramDialogInterface,
							int paramInt) {
						switch (paramInt) {
						case 0:
							Bundle bundle1 = new Bundle();
							bundle1.putString("msg1", text);
							bundle1.putString("imageUrl", imagePath);
							Intent intent1 = new Intent(context,
									AuthorizeActivity.class);
							intent1.putExtras(bundle1);
							context.startActivity(intent1);
							break;
						case 1:
							Bundle bundle2 = new Bundle();
							bundle2.putString("msg2", text);
							bundle2.putString("imageUrl", imagePath);
							Intent intent2 = new Intent(context,
									MainActivity.class);
							intent2.putExtras(bundle2);
							context.startActivity(intent2);
							break;
						}

					}
				}).setTitle("分享到").setNegativeButton("关闭", null).create();
	}
}