package zing.sinawb;

import java.io.IOException;
import java.net.MalformedURLException;

import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.weibo.net.Weibo;
import com.weibo.net.WeiboException;
import com.weibo.net.WeiboParameters;

public class QueryWeiboActivity extends Activity  
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
					String s = getPublicTimeline();
					JSONObject json = new JSONObject(s);
					Log.e("zing", s);
					JSONObject data = new JSONObject(s);
					JSONObject u=data.getJSONObject("statuses");
                    String userName=u.getString("idstr");
					System.out.println("id="+userName);
					
				} catch (Exception e)
				{
					Log.e("zing", e.getMessage() + "");
				}
			}

		});

	}

	private String getPublicTimeline() throws MalformedURLException,
			IOException, WeiboException
	{
		String url = Weibo.SERVER + "statuses/public_timeline.json";
		WeiboParameters bundle = new WeiboParameters();
		bundle.add("count", "1"); 
		String rlt = weibo.request(QueryWeiboActivity.this, url, bundle, "GET", weibo
				.getAccessToken());
		return rlt;
	}

	 

}
