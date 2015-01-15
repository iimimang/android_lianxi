package zing.sinawb;

import com.weibo.net.AccessToken;
import com.weibo.net.DialogError;
import com.weibo.net.Weibo;
import com.weibo.net.WeiboDialogListener;
import com.weibo.net.WeiboException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

 

public class OAuthWeiboActivity extends Activity
{
	private static final String CONSUMER_KEY = "xxxxxxxx"; 
	private static final String CONSUMER_SECRET = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"; 
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		
		Weibo weibo = Weibo.getInstance();
		// !!Don't forget to set app_key and secret before get token!!!
		weibo.setupConsumerConfig(CONSUMER_KEY, CONSUMER_SECRET);
		// Oauth2.0 隐式授权认证方式
		weibo.setRedirectUrl("http://www.sina.com");
		weibo.authorize(OAuthWeiboActivity.this, new AuthDialogListener());
		 
	}
	
	
	class AuthDialogListener implements WeiboDialogListener {

        @Override
        public void onComplete(Bundle values) {
            String token = values.getString("access_token");
            String expires_in = values.getString("expires_in");
            System.out.println("access_token : " + token + "  expires_in: " + expires_in);
            AccessToken accessToken = new AccessToken(token, CONSUMER_SECRET);
            accessToken.setExpiresIn(expires_in);
            Weibo.getInstance().setAccessToken(accessToken);
            
            Intent intent = new Intent();
            intent.setClass(OAuthWeiboActivity.this, MySinaWeiboActivity.class);
            startActivity(intent);
        }

        @Override
        public void onError(DialogError e) {
            Toast.makeText(getApplicationContext(), "Auth error : " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCancel() {
            Toast.makeText(getApplicationContext(), "Auth cancel", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onWeiboException(WeiboException e) {
            Toast.makeText(getApplicationContext(), "Auth exception : " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }

    }

}
