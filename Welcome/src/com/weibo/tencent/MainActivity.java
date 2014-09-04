package com.weibo.tencent;

import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;

import com.example.notbook.R;
import com.weibo.joechan.util.DisplayUtil;
import com.weibo.joechan.util.TextUtil;
import com.weibo.tencent.service.Weibo;
/**
 * 腾讯微博认证
 * @author Administrator
 *
 */
		
public class MainActivity extends Activity {
	private static final String TAG = "MainActivity";
	private final static int REQUEST_CODE = 1;

	SharedPreferences pres;

	private String requestToken;
	private String requestTokenSecret;
	private String verifier;
	private String accessToken;
	private String accessTokenSecret;
	private String str, imageUrl;
	private Bundle bundle;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.qqmain);
		DisplayUtil.initWindows(this);
		bundle = getIntent().getExtras();
		str = bundle.getString("msg2");
		imageUrl = bundle.getString("imageUrl");
		Log.e("uri1", "-------ccccccc------->" + imageUrl);
		// 从配置文件中读取accessToken和accessTokenSecret
		pres = this.getSharedPreferences("AccessToken", Context.MODE_PRIVATE);
		accessToken = pres.getString("accessToken", "");
		accessTokenSecret = pres.getString("accessTokenSecret", "");
		// 当accessToken和accessTokenSecret都不为空时
		if (!TextUtil.isEmpty(accessToken)
				&& !TextUtil.isEmpty(accessTokenSecret)) {
			Intent intent = new Intent(this, WeiBoActivity.class);
			intent.putExtra("accessToken", accessToken);
			intent.putExtra("accessTokenSecret", accessTokenSecret);
			Bundle c = new Bundle();
			c.putString("msg2", str);
			c.putString("imageUrl", imageUrl);
			Log.e("uri1", "-------xxxxx------->" + imageUrl);
			intent.putExtras(c);
			startActivity(intent);
			MainActivity.this.finish();
		} else {
			authorization(); // 为空的时候认证
		}
	}

	/**
	 * 完成OAuth认证
	 */
	protected void authorization() {
		startWebView();
	}

	/**
	 * 启动WebView进行OAuth认证
	 */
	protected void startWebView() {
		String urlStr = "http://open.t.qq.com/cgi-bin/authorize";
		Weibo weibo = new Weibo();
		// 获取未授权的Request Token
		Map<String, String> map = weibo.getRequestToken();
		// 获取oauth_token
		requestToken = map.get("oauth_token");
		requestTokenSecret = map.get("oauth_token_secret");
		Log.i(TAG, "Request Token=" + requestToken);
		Log.i(TAG, "Request Token Secret=" + requestTokenSecret);
		Log.i("urlStr----", "--" + urlStr);
		// 构造请求的URL
		StringBuilder urlBuilder = new StringBuilder();
		urlBuilder.append(urlStr);
		urlBuilder.append("?");
		urlBuilder.append("oauth_token=" + requestToken
				+ "&oauth_token_secret=" + requestTokenSecret);
		Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString("urlStr", urlBuilder.toString());
		intent.putExtras(bundle);
		Bundle d = new Bundle();
		d.putString("msg2", str);
		d.putString("imageUrl", imageUrl);
		Log.e("uri1", "-------yyyyyyy------->" + imageUrl);
		intent.putExtras(d);
		startActivityForResult(intent, REQUEST_CODE);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CODE) {
			if (resultCode == WebViewActivity.RESULT_CODE) {
				Bundle bundle = data.getExtras();
				// 获取验证码
				verifier = bundle.getString("verifier");
				if (!TextUtil.isEmpty(verifier)) {
					getAccessToken();
				}
			}
		}
		MainActivity.this.finish();// Joe:关闭跳转页面
	}

	protected void getAccessToken() {
		Weibo weibo = new Weibo();
		Map<String, String> map = weibo.getAccessToken(requestToken,
				requestTokenSecret, verifier);
		accessToken = map.get("oauth_token");
		accessTokenSecret = map.get("oauth_token_secret");
		Log.i(TAG, "Access Token=" + accessToken);
		Log.i(TAG, "Access Token Secret=" + accessTokenSecret);
		if (!TextUtil.isEmpty(accessToken)) {
			Editor editor = pres.edit();
			editor.putString("accessToken", accessToken);
			editor.putString("accessTokenSecret", accessTokenSecret);
			editor.commit();

			Intent intent = new Intent(this, WeiBoActivity.class);
			intent.putExtra("accessToken", accessToken);
			intent.putExtra("accessTokenSecret", accessTokenSecret);
			Bundle e = new Bundle();
			e.putString("msg2", str);
			e.putString("imageUrl", imageUrl);
			Log.e("uri1", "-------wwwwww------->" + imageUrl);
			intent.putExtras(e);
			startActivity(intent);
		}
		MainActivity.this.finish();// Joe:关闭跳转页面
	}
}