package zing.sinawb;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.weibo.net.AccessToken;
import com.weibo.net.Utility;
import com.weibo.net.Weibo;
import com.weibo.net.WeiboException;
import com.weibo.net.WeiboParameters;

public class SendWeiboActivity extends Activity 
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
		edit = (EditText) findViewById(R.id.editText1);
		but.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				try
				{ 
					str = edit.getText().toString();
					update(weibo, str, null, null);
				} catch (Exception e)
				{
					Log.e("zing", e.getMessage() + " ");
				}
			}

		});

	}

	private String update(Weibo weibo,  String status, String lon, String lat)
            throws WeiboException {
        WeiboParameters bundle = new WeiboParameters();
        bundle.add("status", status);
        if (!TextUtils.isEmpty(lon)) {
            bundle.add("lon", lon);
        }
        if (!TextUtils.isEmpty(lat)) {
            bundle.add("lat", lat);
        }
        String rlt = "";
        String url = Weibo.SERVER + "statuses/update.json";
        rlt = weibo.request(this, url, bundle, Utility.HTTPMETHOD_POST, weibo.getAccessToken());
        return rlt;
    }
	/*public void shareToSina(String content, String picUrl, String lon,
			String lat)
	{

		if (content.isEmpty())
		{
			Toast.makeText(this, "分享的内容不能为空", Toast.LENGTH_SHORT).show();
			// DialogUtil.showToast(this.context, "分享的内容不能为空");
		}
		Weibo weibo = Weibo.getInstance();
		AccessToken token = new AccessToken(MyResource.key, MyResource.secret);
		weibo.setAccessToken(token);
		try
		{
			if (!picUrl.isEmpty())
			{
				// 分享的内容有图片时
				// sharePicAndWords(weibo, Weibo.APP_KEY, picUrl, content, lon,
				// lat);
				// shareWordsAndPic(weibo, Weibo.APP_KEY, content, lon, lat,
				// picUrl);
			} else
			{
				// 分享的内容为纯文字
				shareWords(weibo, MyResource.key, content, lon, lat);
			}
		} catch (MalformedURLException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		} catch (WeiboException e)
		{
			e.printStackTrace();
		}
	}

	private void shareWords(Weibo weibo, String source, String status,
			String lon, String lat) throws MalformedURLException, IOException,
			WeiboException
	{
		WeiboParameters bundle = new WeiboParameters();
		bundle.add("source", source);
		bundle.add("status", status);
		if (!TextUtils.isEmpty(lon))
		{
			bundle.add("long", lon);
		}
		if (!TextUtils.isEmpty(lat))
		{
			bundle.add("lat", lat);
		}
		String url = Weibo.SERVER + "statuses/update.json";
		AsyncWeiboRunner weiboRunner = new AsyncWeiboRunner(weibo);
		weiboRunner.request(SendWeiboActivity.this, url, bundle,
				Utility.HTTPMETHOD_POST, this);
	}*/

	

}
