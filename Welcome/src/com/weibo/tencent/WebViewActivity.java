package com.weibo.tencent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.notbook.R;
import com.weibo.joechan.util.DisplayUtil;
import com.weibo.joechan.util.TextUtil;

/**
 * @author coolszy
 * @date 2011-7-4
 * @blog http://blog.csdn.net/coolszy
 */

public class WebViewActivity extends Activity {
	private static final String TAG = "MainActivity";
	public final static int RESULT_CODE = 1;
	private String str, imageUrl;
	private Bundle bundle;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview);
		DisplayUtil.initWindows(this);
		WebView webView = (WebView) findViewById(R.id.oauth_webView);
		bundle = getIntent().getExtras();
		str = bundle.getString("msg2");
		imageUrl = bundle.getString("imageUrl");
		Log.e("uri1", "-------oooooooo------->" + imageUrl);
		Intent intent = this.getIntent();
		if (!intent.equals(null)) {
			Bundle bundle = intent.getExtras();
			if (bundle != null) {
				if (bundle.containsKey("urlStr")) {
					String urlStr = bundle.getString("urlStr");
					WebSettings webSettings = webView.getSettings();
					// 支持JavScript
					webSettings.setJavaScriptEnabled(true);
					webSettings.setSupportZoom(true);
					webView.requestFocus();
					webView.loadUrl(urlStr);
					Log.i(TAG, "WebView Starting....");
				}
			}
		}
		// 绑定java对象到JavaScript中，这样就能在JavaScript中调用java对象，实现通信。
		// 这种方法第一个参数就是java对象，第二个参数表示java对象的别名，在JavaScript中使用
		webView.addJavascriptInterface(new JavaScriptInterface(), "Methods");
		WebViewClient client = new WebViewClient() {
			/**
			 * 回调方法，当页面加载完毕后执行
			 */
			@Override
			public void onPageFinished(WebView view, String url) {
				Log.i(TAG, "WebView onPageFinished...");
				// 执行获取授权码的JavaScript
				view.loadUrl("javascript:window.Methods.getHTML('<head>'+document.getElementsByTagName('body')[0].innerHTML+'</head>');");
				// WebViewActivity.this.finish();// 回调时结束webview验证页面
				super.onPageFinished(view, url);
			}
		};
		webView.setWebViewClient(client);
		// WebViewActivity.this.finish();
	}

	class JavaScriptInterface {
		private static final String TAG = "MainActivity";

		public void getHTML(String html) {
			String verifier = getVerifier(html);
			if (!TextUtil.isEmpty(verifier)) {
				Log.i(TAG, "verifier:" + verifier);
				Intent intent = new Intent();
				intent.putExtra("verifier", verifier);
				intent.putExtra("imageUrl", imageUrl);
				setResult(RESULT_CODE, intent);
				finish();// 显示授权码后关闭webview界面
			}
		}

		/**
		 * 根据正则表达式获取验证码
		 * 
		 * @param html
		 * @return
		 */
		public String getVerifier(String html) {
			String ret = "";
			String regEx = "授权码：[0-9]{6}"; // 匹配模式
			Pattern p = Pattern.compile(regEx);
			Matcher m = p.matcher(html);
			boolean result = m.find();
			if (result) {
				// 获取验证码
				ret = m.group(0).substring(4);
			}
			return ret;
		}
	}
}
