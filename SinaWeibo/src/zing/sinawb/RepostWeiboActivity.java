package zing.sinawb;

import java.io.IOException;
import java.net.MalformedURLException;

import com.weibo.net.AccessToken;
import com.weibo.net.AsyncWeiboRunner;
import com.weibo.net.Weibo;
import com.weibo.net.WeiboException;
import com.weibo.net.WeiboParameters;
import com.weibo.net.AsyncWeiboRunner.RequestListener;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RepostWeiboActivity extends Activity 
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
					repost("", "微博ID");
				} catch (Exception e)
				{
					Log.e("zing", e.getMessage() + "");
				}
			}

		});

	}

	private String repost(String str, String id)
			throws MalformedURLException, IOException, WeiboException
	{
		String url = Weibo.SERVER + "statuses/repost.json";
		WeiboParameters bundle = new WeiboParameters();
		bundle.add("id", id);
		bundle.add("status", str);
		String rlt = weibo.request(RepostWeiboActivity.this, url, bundle,
				"POST", weibo.getAccessToken());
		return rlt;
	}

}
