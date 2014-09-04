package com.notbook.ui;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
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
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notbook.R;
import com.notbook.app.CustomMultiPartEntity;
import com.notbook.app.CustomMultiPartEntity.ProgressListener;
import com.notbook.app.Info;

public class Register extends BaseActivity1 {

	private Button more_back, registersave, img, img1, img2, img3, img4,
			yanzhengma;
	private LinearLayout moreinformation;
	private EditText name, phone, vcode, password, resetpwd, email, zhiye,
			address;
	private String mname = "", mphone = "", mvcode = "", mpassword = "",
			mresetpwd = "", memail = "", mzhiye = "", maddress = "";
	private ProgressDialog dia;
	private LinearLayout tanchu_act;
	private Button image01, image02, image03;
	private ImageButton image;
	private TextView more01;
	private RelativeLayout more, shouqi;
	public static final int NONE = 0;
	public static final int PHOTOHRAPH = 1;// 拍照
	public static final int PHOTOZOOM = 2; // 相册
	public static final int PHOTORESOULT = 3;// 结果
	public static final String IMAGE_UNSPECIFIED = "image/*";
	public static String filepath = "";
	private File mRecordDir;
	String url = "";
	private int i= 0;
	private static long   timenum = 0;
	private static long timenum1 = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		findViewById(R.id.one).setBackgroundResource(background_photobg[num - 1]);
		findViewById(R.id.one1).setBackgroundResource(background_photobg1[num - 1]);
		more_back = (Button) findViewById(R.id.more_back);
		registersave = (Button) findViewById(R.id.registersave);
		shouqi = (RelativeLayout) findViewById(R.id.shouqi);
		more = (RelativeLayout) findViewById(R.id.more);
		moreinformation = (LinearLayout) findViewById(R.id.moreinformation);
		img = (Button) findViewById(R.id.img);
		img1 = (Button) findViewById(R.id.img1);
		img2 = (Button) findViewById(R.id.img2);
		img3 = (Button) findViewById(R.id.img3);
		img4 = (Button) findViewById(R.id.img4);
		yanzhengma = (Button) findViewById(R.id.yanzhengma);
		name = (EditText) findViewById(R.id.name);
		phone = (EditText) findViewById(R.id.phone);
		vcode = (EditText) findViewById(R.id.vcode);
		password = (EditText) findViewById(R.id.password);
		resetpwd = (EditText) findViewById(R.id.resetpwd);
		email = (EditText) findViewById(R.id.email);
		zhiye = (EditText) findViewById(R.id.zhiye);
		address = (EditText) findViewById(R.id.address);
		image = (ImageButton) findViewById(R.id.image);
		tanchu_act = (LinearLayout) findViewById(R.id.tanchu_act);
		image01 = (Button) findViewById(R.id.image01);
		image02 = (Button) findViewById(R.id.image02);
		image03 = (Button) findViewById(R.id.image03);
		more01 = (TextView) findViewById(R.id.more01);

		more_back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
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
		yanzhengma.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				img1.setVisibility(View.INVISIBLE);
				mphone = phone.getText().toString();
				if (mphone.equals("") || mphone == null)
						{
					img1.setVisibility(View.VISIBLE);
					Toast.makeText(getApplicationContext(), "手机号码为空", 0).show();
					return;
				}
				if(mphone.length()!=11){
					img1.setVisibility(View.VISIBLE);
					Toast.makeText(getApplicationContext(), "手机号码长度有误", 0).show();
					return;
				}
				if(!mphone.substring(0, 1).equals("1")){
					img1.setVisibility(View.VISIBLE);
					Toast.makeText(getApplicationContext(), "手机号码开头必须为1", 0).show();
					return ;
				}
				String url = Info.uri + "/gaininfoAction.php?uphone="
						+ Uri.encode(mphone);
				Log.d("log", "-------------str" + url);
				Calendar c = Calendar.getInstance();
				if(i==0){
					new TextAsyncTask1().execute(url);
					i++;
					timenum = c.getTimeInMillis();
				}else{
					timenum1 = c.getTimeInMillis();
					if((timenum1-timenum)<10*1000){
						Toast.makeText(getApplicationContext(), "30秒以后再点击", 0).show();
					}else{
						new TextAsyncTask1().execute(url);
						i=0;
					}
				}
			}
		});
		
		registersave.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				img.setVisibility(View.INVISIBLE);
				img1.setVisibility(View.INVISIBLE);
				img2.setVisibility(View.INVISIBLE);
				img3.setVisibility(View.INVISIBLE);
				img4.setVisibility(View.INVISIBLE);
				mname = name.getText().toString();
				mphone = phone.getText().toString();
				mvcode = vcode.getText().toString();
				mpassword = password.getText().toString();
				mresetpwd = resetpwd.getText().toString();
				memail = email.getText().toString();
				mzhiye = zhiye.getText().toString();
				maddress = address.getText().toString();
				if (mname.equals("") || mname == null) {
					Toast.makeText(Register.this, "姓名不能为空", 0).show();
					img.setVisibility(View.VISIBLE);
					return;
				}
				String regex = "([a-z]|[A-Z]|[\\u4e00-\\u9fa5])+";
				Pattern pat = Pattern.compile(regex);
				pat.matcher(mname).matches();
				if (pat.matcher(mname).matches() == false) {
					Toast.makeText(Register.this, "姓名只能是中文，英文", 0).show();
					img.setVisibility(View.VISIBLE);
					return;
				}
				if (length(mname) > 18) {
					Toast.makeText(Register.this, "姓名长度太长", 0).show();
					img.setVisibility(View.VISIBLE);
					return;
				}
				if (mphone.equals("") || mphone == null) {
					Toast.makeText(Register.this, "手机号码为空", 0).show();
					img1.setVisibility(View.VISIBLE);
					return;
				}
				if (mphone.length() != 11) {
					Toast.makeText(Register.this, "手机号码长度有误", 0).show();
					img1.setVisibility(View.VISIBLE);
					return;
				}
				if (!mphone.substring(0, 1).equals("1")) {
					Toast.makeText(Register.this, "手机号码开头必须为1", 0).show();
					img1.setVisibility(View.VISIBLE);
					return;
				}
				if (mvcode.equals("") || mvcode == null) {
					Toast.makeText(Register.this, "验证码为空", 0).show();
					img2.setVisibility(View.VISIBLE);
					return;
				}
				if (mvcode.length() != 6) {
					Toast.makeText(Register.this, "验证码长度有误", 0).show();
					img2.setVisibility(View.VISIBLE);
					return;
				}
				if (mpassword.equals("") || mpassword == null) {
					Toast.makeText(Register.this, "密码为空", 0).show();
					img3.setVisibility(View.VISIBLE);
					return;
				}
				if (mpassword.length() > 16 || mpassword.length() < 6) {
					Toast.makeText(Register.this, "密码长度有误", 0).show();
					img3.setVisibility(View.VISIBLE);
					return;
				}
				if (mresetpwd.equals("") || mresetpwd == null) {
					Toast.makeText(Register.this, "重复密码为空", 0).show();
					img4.setVisibility(View.VISIBLE);
					return;
				}
				if (mresetpwd.length() > 16 || mpassword.length() < 6) {
					Toast.makeText(Register.this, "重复密码长度有误", 0).show();
					img4.setVisibility(View.VISIBLE);
					return;
				}
				if (!mresetpwd.equals(mpassword)) {
					Toast.makeText(getApplicationContext(), "确认密码,必须和之前输入的一样",
							0).show();
					return;
				}

				Pattern pattern = Pattern.compile(
						"[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+",
						Pattern.CASE_INSENSITIVE);
				Matcher matcher = pattern.matcher(memail);
				if (matcher.matches() == false&&!memail.equals("")) {
					Toast.makeText(getApplicationContext(), "邮箱格式有误", 0).show();
					return;
				}

				url = Info.uri + "/registerAction.php?username="
						+ Uri.encode(mname) + "&password="
						+ Uri.encode(mpassword) + "&resetpwd="
						+ Uri.encode(mresetpwd) + "&uphone="
						+ Uri.encode(mphone) + "&vcode=" + Uri.encode(mvcode)
						+ "&uemail=" + Uri.encode(memail) + "&job="
						+ Uri.encode(mzhiye) + "&uaddress="
						+ Uri.encode(maddress);
				Log.d("log", "-------------str=" + url);
//				if(filepath.equals("")||filepath==null){
////		        	Log.i( "log", "-------------str11111111111111"+url);
//					new TextAsyncTask()
//					.execute(url);
//				}	else{
//					Log.i( "log", "-------------str222222222222222===="+url);
					
					HttpMultipartPost task = new HttpMultipartPost();
					task.execute();
//				}
			}
		});
		more.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				more.setVisibility(View.GONE);
				more01.setVisibility(View.GONE);
				moreinformation.setVisibility(View.VISIBLE);
			}
		});
		shouqi.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				more.setVisibility(View.VISIBLE);
				more01.setVisibility(View.VISIBLE);
				moreinformation.setVisibility(View.GONE);
			}
		});
	}

	public static int length(String mname) {
		int valueLength = 0;
		String chinese = "[\u0391-\uFFE5]";
		/* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
		for (int i = 0; i < mname.length(); i++) {
			/* 获取一个字符 */
			String temp = mname.substring(i, i + 1);
			/* 判断是否为中文字符 */
			if (temp.matches(chinese)) {
				/* 中文字符长度为2 */
				valueLength += 2;
			} else {
				/* 其他字符长度为1 */
				valueLength += 1;
			}
		}
		return valueLength;

	}

	class TextAsyncTask extends AsyncTask<String, Integer, String> {

		@Override
		protected void onPreExecute() {
			dia = new ProgressDialog(Register.this);
			dia.setMessage("正在注册...");
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
				Toast.makeText(Register.this, "网络加载失败..", 0).show();
				dia.dismiss();
			} else {
				dia.dismiss();
				Log.d("log", "----------------------------result=" + result);
				JSONObject object,object1;
				try {
					
					object = new JSONObject(result);
					int responsecode = object.getInt("responsecode"); 
					if(responsecode==31){
						Toast.makeText(Register.this, "手机号码已注册", 0).show();
					}else if(responsecode==10){
						Intent intent = new Intent(Register.this, Login.class);
						startActivity(intent);
						Toast.makeText(getApplicationContext(), "注册成功", 0).show();
						Register.this.finish();	
					}else{
						Toast.makeText(Register.this, "注册失败", 0).show();
					}
					
					
					
					
				} catch (JSONException e) {
				e.printStackTrace();
				}
				
			}
			super.onPostExecute(result);
		}
	}

	class TextAsyncTask1 extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {
			return getResultByPost(params[0]);
		}
		@Override
		protected void onPreExecute() {
			dia = new ProgressDialog(Register.this);
			dia.setMessage("正在获取...");
			dia.show();
			dia.setCanceledOnTouchOutside(false);
			super.onPreExecute();
		}
		@Override
		protected void onPostExecute(String result) {

			if (result == null) {
				Log.d("log", "----------------------------result=" + result);
				Toast.makeText(Register.this, "网络加载失败..", 0).show();
				dia.dismiss();
			} else {
				dia.dismiss();
				Log.d("log", "----------------------------result=" + result);
				JSONObject object,object1;
				try {
					
					object = new JSONObject(result);
					int responsecode = object.getInt("responsecode"); 
					if(responsecode==31){
						Toast.makeText(Register.this, "手机号码已注册", 0).show();
					}else if(responsecode==10){
						Toast.makeText(Register.this, "发送成功，请稍候", 0).show();
						
					}else{
						Toast.makeText(Register.this, "查找失败", 0).show();
					}
					
					
					
					
				} catch (JSONException e) {
				e.printStackTrace();
				}
			}
			super.onPostExecute(result);
		}
	}

	private String getResultByPost(String url) {
		String result = null;
		try {
			HttpGet request = new HttpGet(url);
			HttpClient client = new DefaultHttpClient();
			HttpResponse response = client.execute(request);

			HttpEntity entity = response.getEntity();
			result = EntityUtils.toString(entity);

		} catch (IOException e) {
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
			dia = new ProgressDialog(Register.this);
			dia.setMessage("正在注册...");
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
				multipartContent.addPart("pic", new FileBody(file));
				totalSize = multipartContent.getContentLength();

				// Send it
				httpPost.setEntity(multipartContent);
				HttpResponse response = httpClient.execute(httpPost,
						httpContext);
				String serverResponse = EntityUtils.toString(response
						.getEntity());

				Log.i("log", "------------------serverResponse=" + serverResponse);
				dia.dismiss();
				
				try {
					JSONObject object = new JSONObject(serverResponse);
					Object responsecode = object.getJSONObject("responsecode"); 
					Log.i("log", "------------------responsecode=" + responsecode);
					
					if(responsecode.equals("10")){
						Toast.makeText(getApplicationContext(), "注册成功", 0).show();
						Intent intent = new Intent(Register.this, Login.class);
						startActivity(intent);
						Register.this.finish();	
					}
					if(responsecode.equals("31")){
						Toast.makeText(Register.this, "手机号码已注册", 0).show();
					}
					if(!responsecode.equals("10")&&!responsecode.equals("31")){
						Toast.makeText(Register.this, "注册失败", 0).show();
					}
					
					
					
					
				} catch (JSONException e) {
				e.printStackTrace();
				}
				
				
				
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
