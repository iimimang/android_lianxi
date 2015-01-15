/**
 * 
 */
package com.flysnow.sina.weibo;

import java.text.SimpleDateFormat;

import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 首页Activity
 * @author 
 * @since 2011-3-8
 */
public class HomeActivity extends Activity {
	
	private static final String SINA_TAG = "weibosdk";

    /** 显示认证后的信息，如 AccessToken */
    private TextView mTokenText;
    
    private AuthInfo mAuthInfo;
    
    /** 封装了 "access_token"，"expires_in"，"refresh_token"，并提供了他们的管理功能  */
    private Oauth2AccessToken mAccessToken;

    /** 注意：SsoHandler 仅当 SDK 支持 SSO 时有效 */
    private SsoHandler mSsoHandler;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_table);     
		
		Button login= (Button) findViewById(R.id.login);
		mTokenText = (TextView) findViewById(R.id.token_text_view);
		
	    mAuthInfo = new AuthInfo(this, Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE);
	    mSsoHandler = new SsoHandler(HomeActivity.this, mAuthInfo);
	    // SSO 授权, 仅Web
        findViewById(R.id.weibo_logo).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mSsoHandler.authorizeWeb(new AuthListener());
            }
        });
	
		login.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					EditText u = (EditText) findViewById(R.id.username);
					EditText p = (EditText) findViewById(R.id.password);
					EditText h = (EditText) findViewById(R.id.height);
					RadioButton s= (RadioButton) findViewById(R.id.sex_man);
					String username=u.toString();
					String passwd=p.toString();
					double  height= Double.parseDouble(h.getText().toString());  //转换为double类型 
					String sex;
					if(s.isChecked()){
						sex ="男性";
					}else{
						
						sex ="女性";
					}
					Intent intent=new Intent();
					intent.setClass(HomeActivity.this, MoreActivity.class);
					Bundle bu=new Bundle();
					bu.putString("username", username);
					bu.putString("passwd", passwd);
					bu.putString("sex", sex);
					bu.putDouble("height", height);
					intent.putExtras(bu);
					startActivity(intent);
				}catch(Exception e){
					// TODO: handle exception
					Toast.makeText(HomeActivity.this,"errorrrrr", Toast.LENGTH_LONG).show();
				}
				
			}});
		
		  // 从 SharedPreferences 中读取上次已保存好 AccessToken 等信息，
        // 第一次启动本应用，AccessToken 不可用
        mAccessToken = AccessTokenKeeper.readAccessToken(this);
        if (mAccessToken.isSessionValid()) {
            updateTokenView(true);
        }
		
	};
	
										//返回数据
//	protected void onActivityResult(int requestCode, int resultCode,Intent data) {
//			// TODO Auto-generated method stub
//			super.onActivityResult(requestCode, resultCode, data);
//			RadioButton radiobutton_Man= (RadioButton) findViewById(R.id.sex_man);
//			RadioButton radiobutton_Woman= (RadioButton) findViewById(R.id.sex_woman);
//			EditText edit_height = (EditText) findViewById(R.id.height);
//		switch (resultCode) {
//			case RESULT_OK:
//			/* 取得来自Activity2 的数据，并显示于画面上*/
//			Bundle bunde = data.getExtras();
//			String sex = bunde.getString("sex");
//			double height = bunde.getDouble("height");
//			edit_height.setText("" + height);
//			if (sex.equals("男性")) {
//			radiobutton_Man.setChecked(true);
//			} else {
//			radiobutton_Woman.setChecked(true);
//			} 
//			break;
//			
//			default:
//			break;
//			}
//	}
	   /**
     * 当 SSO 授权 Activity 退出时，该函数被调用。
     * 
     * @see {@link Activity#onActivityResult}
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        // SSO 授权回调
        // 重要：发起 SSO 登陆的 Activity 必须重写 onActivityResult
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }
	
    /**
     * 微博认证授权回调类。
     * 1. SSO 授权时，需要在 {@link #onActivityResult} 中调用 {@link SsoHandler#authorizeCallBack} 后，
     *    该回调才会被执行。
     * 2. 非 SSO 授权时，当授权结束后，该回调就会被执行。
     * 当授权成功后，请保存该 access_token、expires_in、uid 等信息到 SharedPreferences 中。
     */
    class AuthListener implements WeiboAuthListener {
        
        @Override
        public void onComplete(Bundle values) {
            // 从 Bundle 中解析 Token
            mAccessToken = Oauth2AccessToken.parseAccessToken(values);
            if (mAccessToken.isSessionValid()) {
                // 显示 Token
                updateTokenView(false);
                
                // 保存 Token 到 SharedPreferences
                AccessTokenKeeper.writeAccessToken(HomeActivity.this, mAccessToken);
                Toast.makeText(HomeActivity.this,  "授权成功", Toast.LENGTH_SHORT).show();
            } else {
                // 以下几种情况，您会收到 Code：
                // 1. 当您未在平台上注册的应用程序的包名与签名时；
                // 2. 当您注册的应用程序包名与签名不正确时；
                // 3. 当您在平台上注册的包名和签名与您当前测试的应用的包名和签名不匹配时。
                String code = values.getString("code");
                String message = "授权失败";
                if (!TextUtils.isEmpty(code)) {
                    message = message + "\nObtained the code: " + code;
                }
                Toast.makeText(HomeActivity.this, message, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onCancel() {
            Toast.makeText(HomeActivity.this,  "取消授权", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onWeiboException(WeiboException e) {
            Toast.makeText(HomeActivity.this, 
                    "Auth exception : " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    
    /**
     * 显示当前 Token 信息。
     * 
     * @param hasExisted 配置文件中是否已存在 token 信息并且合法
     */
    private void updateTokenView(boolean hasExisted) {
        String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(
                new java.util.Date(mAccessToken.getExpiresTime()));
        String format = "Token：%1$s \n有效期：%2$s";
        mTokenText.setText(String.format(format, mAccessToken.getToken(), date));
        
        String message = String.format(format, mAccessToken.getToken(), date);
        if (hasExisted) {
            message =  "Token 仍在有效期内，无需再次登录。\n" + message;
        }
        mTokenText.setText(message);
    }
    
    
	
}
