package com.notbook.ui;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notbook.R;
import com.notbook.app.CustomMultiPartEntity;
import com.notbook.app.CustomMultiPartEntity.ProgressListener;
import com.notbook.app.cache.ImageLoader;
import com.notbook.things.Meta;
import com.notbook.ui.Login.TextAsyncTask;
import com.notbook.ui.Register.HttpMultipartPost;

public class Personinformationedit extends BaseActivity {

	private Button more_back ,personinforsave;
	private EditText name,job,email,address;
	private String tname="",tjob="",temail="",taddress="";
	private ProgressDialog dia;
	private LinearLayout tanchu_act;
	private Button image01, image02, image03;
	private ImageButton image ;
	public static final int NONE = 0;
	public static final int PHOTOHRAPH = 1;// 拍照
	public static final int PHOTOZOOM = 2; // 相册
	public static final int PHOTORESOULT = 3;// 结果
	public static final String IMAGE_UNSPECIFIED = "image/*";
	public static String filepath = "";
	private File mRecordDir;
	String url = "";
	private ImageLoader imageLoader = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.personinforedit);
		findViewById(R.id.one).setBackgroundResource(background_photobg[num - 1]);
		findViewById(R.id.one1).setBackgroundResource(background_photobg1[num - 1]);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); 
		more_back = (Button)findViewById(R.id.more_back);
		personinforsave = (Button)findViewById(R.id.personinforsave);
		name = (EditText)findViewById(R.id.name);
		job = (EditText)findViewById(R.id.job);
		email = (EditText)findViewById(R.id.email);
		address = (EditText)findViewById(R.id.address);
		image = (ImageButton) findViewById(R.id.image);
		tanchu_act = (LinearLayout) findViewById(R.id.tanchu_act);
		image01 = (Button) findViewById(R.id.image01);
		image02 = (Button) findViewById(R.id.image02);
		image03 = (Button) findViewById(R.id.image03);
		
		
		
		name.setText(Personinformation.tname);
		name.setSelection(Personinformation.tname.length());
		job.setText(Personinformation.tjob);
		job.setSelection(Personinformation.tjob.length());
		email.setText(Personinformation.temail);
		email.setSelection(Personinformation.temail.length());
		address.setText(Personinformation.taddress);
		address.setSelection(Personinformation.taddress.length());
		imageLoader = new ImageLoader(Personinformationedit.this);
		if(null != Personinformation.tphoto && !Personinformation.tphoto.equals("")){
			imageLoader.DisplayImage(Personinformation.tphoto, image, false);
		}
		
		
		
		// 取消
				image03.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						// Toast.makeText(TanchuActivity.this, "gone", 0).show();
						AnimationSet animationSet = new AnimationSet(true);
						TranslateAnimation translateAnimation = new TranslateAnimation(
								Animation.RELATIVE_TO_SELF, 0f,
								Animation.RELATIVE_TO_SELF, 0f,
								Animation.RELATIVE_TO_SELF, 0f,
								Animation.RELATIVE_TO_SELF, 1f);
						translateAnimation.setDuration(400);
						animationSet.addAnimation(translateAnimation);
						tanchu_act.startAnimation(animationSet);
						tanchu_act.setVisibility(View.GONE);

					}
				});
				// 手机相册
				image01.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						Intent intent1 = new Intent(Intent.ACTION_GET_CONTENT, null);
						intent1.setDataAndType(
								MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
								IMAGE_UNSPECIFIED);
						startActivityForResult(intent1, PHOTOZOOM);
						AnimationSet animationSet = new AnimationSet(true);
						TranslateAnimation translateAnimation = new TranslateAnimation(
								Animation.RELATIVE_TO_SELF, 0f,
								Animation.RELATIVE_TO_SELF, 0f,
								Animation.RELATIVE_TO_SELF, 0f,
								Animation.RELATIVE_TO_SELF, 1f);
						translateAnimation.setDuration(400);
						animationSet.addAnimation(translateAnimation);
						tanchu_act.startAnimation(animationSet);
						tanchu_act.setVisibility(View.GONE);

					}
				});
				// 系统拍照
				image02.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
						/*
						 * String
						 * file=Environment.getExternalStorageDirectory()+"/yashily";
						 * File f=new File(file); if(!f.exists()){ f.mkdirs(); }
						 * 
						 * f=new File(file+"/camera.jpg"); Uri uri = Uri.fromFile(f);
						 * intent2.putExtra(MediaStore.EXTRA_OUTPUT, uri);
						 */

						// 启动一个Activity，希望接收到返回结果
						startActivityForResult(intent2, PHOTOHRAPH);
						AnimationSet animationSet = new AnimationSet(true);
						TranslateAnimation translateAnimation = new TranslateAnimation(
								Animation.RELATIVE_TO_SELF, 0f,
								Animation.RELATIVE_TO_SELF, 0f,
								Animation.RELATIVE_TO_SELF, 0f,
								Animation.RELATIVE_TO_SELF, 1f);
						translateAnimation.setDuration(400);
						animationSet.addAnimation(translateAnimation);
						tanchu_act.startAnimation(animationSet);
						tanchu_act.setVisibility(View.GONE);

					}
				});
				// 图片选择
				image.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (tanchu_act.getVisibility() == View.GONE) {
							// Toast.makeText(TanchuActivity.this, "visibile",
							// 0).show();
							tanchu_act.setVisibility(1);
							AnimationSet animationSet = new AnimationSet(true);
							TranslateAnimation translateAnimation = new TranslateAnimation(
									Animation.RELATIVE_TO_SELF, 0f,
									Animation.RELATIVE_TO_SELF, 0f,
									Animation.RELATIVE_TO_SELF, 1f,
									Animation.RELATIVE_TO_SELF, 0f);

							translateAnimation.setDuration(400);
							animationSet.addAnimation(translateAnimation);
							tanchu_act.startAnimation(animationSet);
						} else {
							// Toast.makeText(TanchuActivity.this, "gone", 0).show();
							AnimationSet animationSet = new AnimationSet(true);
							TranslateAnimation translateAnimation = new TranslateAnimation(
									Animation.RELATIVE_TO_SELF, 0f,
									Animation.RELATIVE_TO_SELF, 0f,
									Animation.RELATIVE_TO_SELF, 0f,
									Animation.RELATIVE_TO_SELF, 1f);
							translateAnimation.setDuration(400);
							animationSet.addAnimation(translateAnimation);
							tanchu_act.startAnimation(animationSet);
							tanchu_act.setVisibility(View.GONE);
						}

					}
				});
		more_back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent it = new Intent(Personinformationedit.this,Personinformation.class);
				startActivity(it);
				finish();
			}
		});
		personinforsave.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				tname = name.getText().toString().trim();
				tjob = job.getText().toString();
				temail = email.getText().toString();
				taddress = address.getText().toString();
				if (tname.equals("") || tname == null) {
					Toast.makeText(Personinformationedit.this, "姓名不能为空", 0).show();
					return;
				}
				String regex = "([a-z]|[A-Z]|[\\u4e00-\\u9fa5])+";
				Pattern pat = Pattern.compile(regex);
				pat.matcher(tname).matches();
				if (pat.matcher(tname).matches() == false) {
					Toast.makeText(Personinformationedit.this, "姓名只能是中文，英文", 0).show();
					return;
				}
				
				if (Register.length(tname) > 12&&Register.length(tname)<4) {
					Toast.makeText(Personinformationedit.this, "姓名长度不正确", 0).show();
					return;
				}
				Pattern pattern = Pattern.compile(
						"[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+",
						Pattern.CASE_INSENSITIVE);
				Matcher matcher = pattern.matcher(temail);
				if (matcher.matches() == false&&!temail.equals("")) {
					Toast.makeText(getApplicationContext(), "邮箱格式有误", 0).show();
					return;
				}
				url = com.notbook.app.Info.uri+"/edituserAction.php?uid="+Uri.encode(Login.id)+"&uname="+Uri.encode(tname)+"&job="
				+Uri.encode(tjob)+"&email="+Uri.encode(temail)+"&uaddress="+Uri.encode(taddress);
				if(filepath.equals("")||filepath==null){
					new TextAsyncTask().execute(url);
					Log.d( "log", "1111111111111111111111111="+url);
				}	else{
					HttpMultipartPost task = new HttpMultipartPost();
					task.execute();
					Log.d( "log", "2222222222222222222222222="+url);
				}
				
				
			}
		});
	}
	class TextAsyncTask extends AsyncTask<String, Integer, String> {

		@Override
		protected void onPreExecute() {
			dia = new ProgressDialog(Personinformationedit.this);
			dia.setMessage("正在修改...");
			dia.show();
			dia.setCanceledOnTouchOutside(false);
			super.onPreExecute();
		}
		
		@Override
		protected String doInBackground(String... params) {
			return getResultByPost(params[0]);
		}

		@Override
		protected void onPostExecute(String result) {
			if (result == null) {
				Toast.makeText(Personinformationedit.this, "网络加载失败..", 0).show();
				dia.dismiss();
			} else {
				
				Log.d( "log", "-------------result"+result);
				dia.dismiss();
				
				Intent it = new Intent(Personinformationedit.this,Personinformation.class);
				startActivity(it);
				Toast.makeText(getApplication(), "保存成功！", 0).show();
				Personinformationedit.this.finish();
			}   
			super.onPostExecute(result);
		}
	}

	private String getResultByPost(String url) {
		String result = null;
		try{
			HttpGet request=new HttpGet(url);
			HttpClient client=new DefaultHttpClient();
		    HttpResponse response=client.execute(request);
		   
		    HttpEntity entity = response.getEntity();
        	result = EntityUtils.toString(entity);
        	
		}catch(IOException e){
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == NONE)
			return;

		if (data == null)
			return;
		// 拍照
		if (requestCode == PHOTOHRAPH) {
			Log.d("log", "==========================PHOTOHRAPH=" + PHOTOHRAPH);
			/*
			 * String
			 * ph=Environment.getExternalStorageDirectory()+"/yashily/camera.jpg"
			 * ; File f=new File(ph);
			 * 
			 * //图片处理，压缩剪切 startPhotoZoom(Uri.fromFile(f));
			 */
			Bundle bundle = data.getExtras();

			Bitmap bitmap = (Bitmap) bundle.get("data");
			image.setImageBitmap(bitmap);
			saveMyBitmap2(bitmap, "up_pic.png");
		}
		// 读取相册缩放图片
		if (requestCode == PHOTOZOOM) {
			ContentResolver resolver = getContentResolver();
			// 获取图片

			Uri originalUri = data.getData(); // 获得图片的uri
			// 这里开始的第二部分，获取图片的路径：
			String[] proj = { MediaStore.Images.Media.DATA };

			Cursor cursor = managedQuery(originalUri, proj, null, null, null);
			// 按我个人理解 这个是获得用户选择的图片的索引值
			int column_index = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			// 最后根据索引值获取图片路径
			filepath = cursor.getString(column_index);
			Log.d("log", "-------------相册处理前filepath==" + filepath);
			startPhotoZoom(data.getData());
		}
		// 处理结果
		if (requestCode == PHOTORESOULT) {
			Bundle extras = data.getExtras();
			if (extras != null) {
				Bitmap photo = extras.getParcelable("data");
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				photo.compress(Bitmap.CompressFormat.PNG, 75, stream);// (0 -
				saveMyBitmap2(photo, "up_pic.png"); // 100)压缩文件
				image.setImageBitmap(photo);

				AnimationSet animationSet = new AnimationSet(true);
				TranslateAnimation translateAnimation = new TranslateAnimation(
						Animation.RELATIVE_TO_SELF, 0f,
						Animation.RELATIVE_TO_SELF, 0f,
						Animation.RELATIVE_TO_SELF, 0f,
						Animation.RELATIVE_TO_SELF, 1f);
				translateAnimation.setDuration(400);
				animationSet.addAnimation(translateAnimation);
				tanchu_act.startAnimation(animationSet);
				tanchu_act.setVisibility(View.GONE);
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	// 获取照片
	public File saveMyBitmap2(Bitmap bm, String filename) {
		File defaultDir = Environment.getExternalStorageDirectory();
		String path = defaultDir.getAbsolutePath() + File.separator
				+ "notebook" + File.separator;
		// 创建文件夹存放图片
		mRecordDir = new File(path);
		if (!mRecordDir.exists()) {
			mRecordDir.mkdirs();
		}
		File file = null;

		FileOutputStream fOut = null;
		file = new File(mRecordDir, filename);
		if (file.exists()) {
			file.delete();
		}
		try {

			file.createNewFile();
			fOut = new FileOutputStream(file);
			bm.compress(Bitmap.CompressFormat.PNG, 100, fOut);
			fOut.flush();
			fOut.close();

			filepath = file.getAbsolutePath();
			return file;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	public void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 100);
		intent.putExtra("outputY", 100);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, PHOTORESOULT);
	}
	class HttpMultipartPost extends AsyncTask<HttpResponse, Integer, String> {
		ProgressDialog pd;
		long totalSize;
		Context context;
		@Override
		protected void onPreExecute() {
			dia = new ProgressDialog(Personinformationedit.this);
			dia.setMessage("正在修改...");
			dia.show();
			dia.setCanceledOnTouchOutside(false);
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(HttpResponse... arg0) {

			HttpClient httpClient = new DefaultHttpClient();
			HttpContext httpContext = new BasicHttpContext();
			HttpPost httpPost;
			httpPost = new HttpPost(url);
			CustomMultiPartEntity multipartContent = new CustomMultiPartEntity(new ProgressListener() {
				@Override
				public void transferred(long num) {
					publishProgress((int) ((num / (float) totalSize) * 100));
				}
			});
			try {
				

				File file = new File(filepath);
				Log.d("TAG", "file = " + file.length());
				multipartContent.addPart("image", new FileBody(file));
				totalSize = multipartContent.getContentLength();

				// Send it
				httpPost.setEntity(multipartContent);
				HttpResponse response = httpClient.execute(httpPost,
						httpContext);
				String serverResponse = EntityUtils.toString(response
						.getEntity());

				Log.i("log", "------------------serverResponse=" + serverResponse);
				dia.dismiss();
				Intent intent = new Intent(Personinformationedit.this, Personinformation.class);
				startActivity(intent);
				Toast.makeText(getApplicationContext(), "保存成功", 0).show();
				Personinformationedit.this.finish();
				return serverResponse;
			}

			catch (Exception e) {
				System.out.println(e);
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(Integer... progress) {
		}

		@Override
		protected void onPostExecute(String ui) {

		}
	}
	
}
