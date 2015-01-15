package zing.sinawb;

import java.io.IOException;
import java.net.MalformedURLException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.weibo.net.AccessToken;
import com.weibo.net.Utility;
import com.weibo.net.Weibo;
import com.weibo.net.WeiboException;
import com.weibo.net.WeiboParameters;
import com.weibo.net.AsyncWeiboRunner.RequestListener;

public class CommentWeiboActivity extends Activity  
{

	Button but;
	EditText edit;
	String str;
	Weibo weibo = Weibo.getInstance();
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.send_weibo);
		but = (Button) findViewById(R.id.button1);
 
		but.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				try
				{
					String s = setComment("微博ID");
					Log.e("zing", s);
				} catch (Exception e)
				{
					Log.e("zing", e.getMessage() + "");
				}
			}

		});

	}

	private String setComment(String id) throws MalformedURLException,
			IOException, WeiboException
	{
		String url = Weibo.SERVER + "comments/create.json";
		 
		WeiboParameters bundle = new WeiboParameters();
		bundle.add("comment", "测试");
		bundle.add("id", id);
		Log.e("zing", weibo.getAccessToken().getSecret());
		String rlt = weibo.request(CommentWeiboActivity.this, url, bundle, "POST", weibo
				.getAccessToken());
		return rlt;
	}
}
 
