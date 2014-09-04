package com.notbook.app;
import java.io.File;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import com.notbook.app.CustomMultiPartEntity.ProgressListener;
import com.notbook.ui.Eventlist;
import com.notbook.ui.Friend;
import com.notbook.ui.Hudianlist;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;


public class HttpMultipartPost extends AsyncTask<HttpResponse, Integer, String> {
	ProgressDialog pd;
	long totalSize;
	Context context;

	public HttpMultipartPost(Context context) {
		this.context = context;
	}

	@Override
	protected void onPreExecute() {
		if(pd==null){
			pd = new ProgressDialog(context);
			pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			pd.setMessage("正在发送...");
			pd.setCancelable(true);
			pd.show();
		}
	
	}

	@Override
	protected String doInBackground(HttpResponse... arg0) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpContext httpContext = new BasicHttpContext();
		HttpPost httpPost = new HttpPost(Friend.url);

		try {
			CustomMultiPartEntity multipartContent = new CustomMultiPartEntity(new ProgressListener() {
				@Override
				public void transferred(long num) {
					publishProgress((int) ((num / (float) totalSize) * 100));
				}
			});

			// We use FileBody to transfer an image
			Log.i("TAG","new FileBody(new File(Eventlist.imageurl))"+new FileBody(new File(Eventlist.imageurl)));
			multipartContent.addPart("images", new FileBody(new File(Eventlist.imageurl)));
			totalSize = multipartContent.getContentLength();

			// Send it
			httpPost.setEntity(multipartContent);
			HttpResponse response = httpClient.execute(httpPost, httpContext);
			String serverResponse = EntityUtils.toString(response.getEntity());
			
			// ResponseFactory rp = new ResponseFactory(serverResponse);
			// return (TypeImage) rp.getData();
			Log.i("TAG","�ϴ����"+serverResponse);
			Intent intent = new Intent(context, Hudianlist.class);
			context.startActivity(intent);
			Toast.makeText(context, "发送成功", 0).show();
			return serverResponse;
		}

		catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	@Override
	protected void onProgressUpdate(Integer... progress) {
		pd.setProgress((int) (progress[0]));
	}

	@Override
	protected void onPostExecute(String ui) {
		pd.dismiss();
		
	}
}