package com.wyj.Activity;

import java.io.File;
import java.io.IOException;

import com.wyj.dataprocessing.MyApplication;
import com.wyj.utils.FilePath;
import com.wyj.utils.Tools;
import com.wyj.Activity.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class User extends Activity implements OnClickListener {
	RelativeLayout avatar_edit;
	TextView username_input;
	TextView truename_input;
	TextView address_input;
	TextView sex_input;
	private static String TAG = "User";
	/* 请求码 */
	private static final int IMAGE_REQUEST_CODE = 10; // 相册选择
	private static final int CAMERA_REQUEST_CODE = 30; // 拍照
	private static final int RESULT_REQUEST_CODE = 20; // 返回图片数据 处理图片把图片放置当前页面
	/* 头像名称 */
	private static final String IMAGE_FILE_NAME = "faceImage.jpg";
	private ImageView faceImage;
	private ImageView userinfo_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user);

		findViewById();
		setListener();

	}

	private void findViewById() {

		avatar_edit = (RelativeLayout) findViewById(R.id.avatar_edit);
		faceImage = (ImageView) findViewById(R.id.avatar_face);
		username_input = (TextView) findViewById(R.id.username_input);
		truename_input = (TextView) findViewById(R.id.truename_input);
		address_input = (TextView) findViewById(R.id.address_input);
		sex_input = (TextView) findViewById(R.id.sex_input);
		userinfo_back = (ImageView) findViewById(R.id.userinfo_back);
	}

	private void setListener() {
		avatar_edit.setOnClickListener(this);
		userinfo_back.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.avatar_edit:
			avatar_Views();
			break;
		case R.id.userinfo_back:

			Intent intent = new Intent(User.this, My.class);
			UserGroupTab.getInstance().switchActivity("My", intent, -1, -1);
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		//Log.i("aaaa", "------User-回来了-----------" + requestCode);
		switch (requestCode) {
		case IMAGE_REQUEST_CODE:
			startPhotoZoom(data.getData());
			break;
		case CAMERA_REQUEST_CODE:
			
			if (Tools.hasSdcard()) {
				Log.i("aaaa", "------返回相机 返回图片数据 去除剪切-----------");
				File tempFile = new File(
						FilePath.ROOT_DIRECTORY+ IMAGE_FILE_NAME);
			
				startPhotoZoom(Uri.fromFile(tempFile));
			} else {
				Toast.makeText(User.this, "未找到存储卡，无法存储照片！", Toast.LENGTH_LONG)
						.show();
			}

			break;
		case RESULT_REQUEST_CODE:
			if (data != null) {
				getImageToView(data);
			}
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 裁剪图片方法实现
	 * 
	 * @param uri
	 */
	public void startPhotoZoom(Uri uri) {

		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// 设置裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 150);
		intent.putExtra("outputY", 150);
		intent.putExtra("return-data", true);
		UserGroupTab.getInstance().startActivityForResult(intent, 20);
	}

	/**
	 * 保存裁剪之后的图片数据
	 * 
	 * @param picdata
	 */
	private void getImageToView(Intent data) {
		Bundle extras = data.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
			Drawable drawable = new BitmapDrawable(photo);
			faceImage.setImageDrawable(drawable);
		}
	}

	private void avatar_Views() {
		// TODO Auto-generated method stub
		final String[] arrayFruit = new String[] { "拍照", "从手机相册选择", "查看大图" };

		Dialog alertDialog = new AlertDialog.Builder(getParent())
				.setItems(arrayFruit, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// Toast.makeText(Dialog_AlertDialogDemoActivity.this,
						// arrayFruit[which], Toast.LENGTH_SHORT).show();
						switch (which) {
						case 0:

							Intent intentFromCapture = new Intent(
									MediaStore.ACTION_IMAGE_CAPTURE);
							// 判断存储卡是否可以用，可用进行存储
							if (Tools.hasSdcard()) {
								Log.i(TAG, "储存卡可用--------------");
								intentFromCapture.putExtra(
										MediaStore.EXTRA_OUTPUT,
										Uri.fromFile(new File(FilePath.ROOT_DIRECTORY,
												IMAGE_FILE_NAME)));
								
								UserGroupTab.getInstance().startActivityForResult(
										intentFromCapture, CAMERA_REQUEST_CODE);
							}else{
								
								Toast.makeText(User.this, "未找到存储卡，无法存储照片！", Toast.LENGTH_LONG)
								.show();
							}
								break;
						case 1:
							Intent intentFromGallery = new Intent();
							intentFromGallery.setType("image/*"); // 设置文件类型
							intentFromGallery
									.setAction(Intent.ACTION_GET_CONTENT);

							UserGroupTab.getInstance().startActivityForResult(
									intentFromGallery, IMAGE_REQUEST_CODE);

							break;
						}
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				}).create();
		alertDialog.show();
	}

}