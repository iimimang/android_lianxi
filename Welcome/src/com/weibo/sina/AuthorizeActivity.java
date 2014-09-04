package com.weibo.sina;

import java.io.IOException;
import java.net.MalformedURLException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.notbook.R;
import com.weibo.joechan.util.DisplayUtil;
/**
 * 新浪微博认证
 * @author Administrator
 *
 */
public class AuthorizeActivity extends Activity {

	private ImageView SharePic;
	private String str, imageUrl;
	private Bundle bundle, bundle2;
	// Bitmap bitmap1;
	private static final String URL_ACTIVITY_CALLBACK = "weiboandroidsdk://TimeLineActivity";
	private static final String FROM = "xweibo";

	// 设置appkey及appsecret，如何获取新浪微博appkey和appsecret请另外查询相关信息，此处不作介绍
	public static final String CONSUMER_KEY = "972949479";// 替换为开发者的appkey，例如"1646212960";
	private static final String CONSUMER_SECRET = "6e30351724ab9afe0f59e73acfce06d8";// 替换为开发者的appkey，例如"94098772160b6f8ffc1315374d8861f9";

	private String username = "";
	private String password = "";

	private String uid;
	private AccessToken accessToken;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		bundle = getIntent().getExtras();
		str = bundle.getString("msg1");
		imageUrl = bundle.getString("imageUrl");
		Log.e("STR", "-------dd-------" + str);
		Log.e("URL", "-------dd------->" + imageUrl);
		setContentView(R.layout.top);
		DisplayUtil.initWindows(this);
		if (!(TextUtils.isEmpty(AuthoSharePreference
				.getToken(AuthorizeActivity.this))
				&& TextUtils.isEmpty(AuthoSharePreference
						.getSecret(AuthorizeActivity.this)))) {
			login();
		} else {
			Toast.makeText(AuthorizeActivity.this, R.string.binding,
					Toast.LENGTH_LONG).show();
			Weibo weibo = Weibo.getInstance();
			weibo.setupConsumerConfig(CONSUMER_KEY, CONSUMER_SECRET);
			// Oauth2.0隐式授权认证方式
			weibo.setRedirectUrl("http://www.baidu.com");// 此处回调页内容应该替换为与appkey对应的应用回调页
			System.out.println("Token0--->" + weibo.getAccessToken());
			weibo.authorize(AuthorizeActivity.this, new AuthDialogListener());
		}
	}

	public void login() {

		Utility.setAuthorization(new Oauth2AccessTokenHeader());
		String token = AuthoSharePreference.getToken(this);
		AccessToken accessToken = new AccessToken(token, CONSUMER_SECRET);
		Weibo.getInstance().setAccessToken(accessToken);
		goShareActivity();

	}

	public void goShareActivity() {
		Intent intent = new Intent();
		intent.setClass(AuthorizeActivity.this, ShareActivity.class);
		/*
		 * this, weibo.getAccessToken().getToken(), weibo
		 * .getAccessToken().getSecret(), content, picPath
		 */
		intent.putExtra(ShareActivity.EXTRA_ACCESS_TOKEN,
				AuthoSharePreference.getToken(AuthorizeActivity.this));
		intent.putExtra(ShareActivity.EXTRA_TOKEN_SECRET,
				AuthoSharePreference.getSecret(AuthorizeActivity.this));
		intent.putExtra(ShareActivity.EXTRA_WEIBO_CONTENT, str);
		intent.putExtra(ShareActivity.EXTRA_PIC_URI, imageUrl);
		startActivity(intent);
		finish();
	}

	class AuthDialogListener implements WeiboDialogListener {

		@Override
		public void onComplete(Bundle values) {
			String token = values.getString("access_token");
			String expires_in = values.getString("expires_in");
			String remind_in = values.getString("remind_in");
			String content = "access_token : " + token + "  expires_in: "
					+ expires_in;
			uid = values.getString("uid");
			System.out.println("token---@>" + token);

			AccessToken accessToken = new AccessToken(token, CONSUMER_SECRET);

			accessToken.setExpiresIn(expires_in);
			Weibo.getInstance().setAccessToken(accessToken);

			AuthoSharePreference.putToken(AuthorizeActivity.this, token);
			AuthoSharePreference.putExpires(AuthorizeActivity.this, expires_in);
			AuthoSharePreference.putRemind(AuthorizeActivity.this, remind_in);
			AuthoSharePreference.putUid(AuthorizeActivity.this, uid);
			AuthoSharePreference.putSecret(AuthorizeActivity.this, Weibo
					.getInstance().getAccessToken().getSecret());

			System.out.println("getToken--->"
					+ Weibo.getInstance().getAccessToken().getToken()
							.toString());
			System.out.println("accessToken" + accessToken.toString() + "---"
					+ accessToken.getToken());
			System.out.println("Secret--->"
					+ Weibo.getInstance().getAccessToken().getSecret());
			try {
				share2weibo(str, imageUrl);
				AuthorizeActivity.this.finish();

			} catch (WeiboException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {

			}

		}

		@Override
		public void onError(DialogError e) {
			Toast.makeText(getApplicationContext(),
					"Auth error : " + e.getMessage(), Toast.LENGTH_LONG).show();
		}

		@Override
		public void onCancel() {
			Toast.makeText(getApplicationContext(), "取消验证",
					Toast.LENGTH_LONG).show();
		}

		@Override
		public void onWeiboException(WeiboException e) {
			Toast.makeText(getApplicationContext(),
					"Auth exception : " + e.getMessage(), Toast.LENGTH_LONG)
					.show();
		}

	}

	// 分享微博
	private void share2weibo(String content, String picPath)
			throws WeiboException {
		Weibo weibo = Weibo.getInstance();
		weibo.share2weibo(this, weibo.getAccessToken().getToken(), weibo
				.getAccessToken().getSecret(), content, picPath);
	}

	// 获取用户资料
	public String getCounts(Weibo weibo) throws MalformedURLException,
			IOException, WeiboException {
		String url = Weibo.SERVER + "users/show.json";
		WeiboParameters bundle = new WeiboParameters();
		bundle.add("source", Weibo.getAppKey());
		bundle.add("uid", uid);
		String rlt = weibo.request(this, url, bundle, "GET",
				weibo.getAccessToken());
		System.out.println("rlt--->" + rlt);
		return rlt;
	}

	// 返回最新的公共微博
	private String getPublicTimeline(Weibo weibo) throws MalformedURLException,
			IOException, WeiboException {
		String url = Weibo.SERVER + "statuses/public_timeline.json";
		WeiboParameters bundle = new WeiboParameters();
		bundle.add("source", Weibo.getAppKey());
		String rlt = weibo.request(this, url, bundle, "GET",
				weibo.getAccessToken());
		return rlt;
	}

	// 上传图片并发布一条新微博
	private String upload(Weibo weibo, String source, String file,
			String status, String lon, String lat) throws WeiboException {
		WeiboParameters bundle = new WeiboParameters();
		bundle.add("source", source);
		bundle.add("pic", file);
		bundle.add("status", status);
		if (!TextUtils.isEmpty(lon)) {
			bundle.add("lon", lon);
		}
		if (!TextUtils.isEmpty(lat)) {
			bundle.add("lat", lat);
		}
		String rlt = "";
		String url = Weibo.SERVER + "statuses/upload.json";
		try {
			rlt = weibo.request(this, url, bundle, Utility.HTTPMETHOD_POST,
					weibo.getAccessToken());
		} catch (WeiboException e) {
			throw new WeiboException(e);
		}
		return rlt;
	}

	// 发布一条微博信息
	private String update(Weibo weibo, String source, String status,
			String lon, String lat) throws WeiboException {
		WeiboParameters bundle = new WeiboParameters();
		bundle.add("source", source);
		bundle.add("status", status);
		if (!TextUtils.isEmpty(lon)) {
			bundle.add("lon", lon);
		}
		if (!TextUtils.isEmpty(lat)) {
			bundle.add("lat", lat);
		}
		String rlt = "";
		String url = Weibo.SERVER + "statuses/update.json";
		rlt = weibo.request(this, url, bundle, Utility.HTTPMETHOD_POST,
				weibo.getAccessToken());
		return rlt;
	}

}
