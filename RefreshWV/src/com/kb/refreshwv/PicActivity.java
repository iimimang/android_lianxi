package com.kb.refreshwv;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class PicActivity extends Activity {
	private Button mBtn;
	private ImageView mImageView;
	private Uri mImageCaptureUri;

	private static final int PICK_FROM_CAMERA = 1;
	private static final int CROP_FROM_CAMERA = 2;
	private static final int PICK_FROM_FILE = 3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pic_activity);
		mBtn = (Button) findViewById(R.id.my_btn);
		mImageView = (ImageView) findViewById(R.id.image_view);
	}

	public void btnClick(View view) {
		new AlertDialog.Builder(PicActivity.this)
				.setTitle(R.string.dialog_title)
				.setPositiveButton(R.string.dialog_p_btn,
						new OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								Intent intent = new Intent();

								/**
								 * 下面这句话，与其它方式写是一样的效果，如果：
								 * intent.setData(MediaStore
								 * .Images.Media.EXTERNAL_CONTENT_URI);
								 * intent.setType(""image/*");设置数据类型
								 * 如果朋友们要限制上传到服务器的图片类型时可以直接写如
								 * ："image/jpeg 、 image/png等的类型"
								 */
								intent.setType("image/*");
								intent.setAction(Intent.ACTION_GET_CONTENT);
								startActivityForResult(Intent.createChooser(
										intent, "Complete action using"),
										PICK_FROM_FILE);
								mBtn.setVisibility(View.GONE);
							}
						})
				.setNegativeButton(R.string.dialog_n_btn,
						new OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								Intent intent = new Intent(
										MediaStore.ACTION_IMAGE_CAPTURE);

								mImageCaptureUri = Uri.fromFile(new File(
										Environment
												.getExternalStorageDirectory(),
										"tmp_avatar_"
												+ String.valueOf(System
														.currentTimeMillis())
												+ ".jpg"));

								intent.putExtra(
										android.provider.MediaStore.EXTRA_OUTPUT,
										mImageCaptureUri);

								try {
									intent.putExtra("return-data", true);
									Toast.makeText(PicActivity.this,
											"進人相機，進行拍照", Toast.LENGTH_LONG)
											.show();
									startActivityForResult(intent,
											PICK_FROM_CAMERA);
								} catch (ActivityNotFoundException e) {
									e.printStackTrace();
								}
								mBtn.setVisibility(View.GONE);
							}
						}).create().show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Toast.makeText(
				PicActivity.this,
				"這是拍照所得" + requestCode + "resultCode===" + resultCode
						+ "RESULT_OK======" + RESULT_OK, Toast.LENGTH_LONG)
				.show();
		if (resultCode != RESULT_OK) {
			return;
		}
		switch (requestCode) {
		case PICK_FROM_CAMERA:
			Toast.makeText(PicActivity.this, "這是拍照所得", Toast.LENGTH_LONG)
					.show();
			doCrop();
			break;
		case PICK_FROM_FILE:
			mImageCaptureUri = data.getData();
			doCrop();
			break;
		case CROP_FROM_CAMERA:
			if (null != data) {
				saveCutPic(data);
			}
			break;

		default:
			break;
		}
	}

	private void doCrop() {
		final ArrayList<CropOption> cropOptions = new ArrayList<CropOption>();

		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setType("image/*");

		List<ResolveInfo> list = getPackageManager().queryIntentActivities(
				intent, 0);

		int size = list.size();
		Toast.makeText(this, "Can not find image crop app"+size,
				Toast.LENGTH_LONG).show();

		if (size == 0) {
			Toast.makeText(this, "Can not find image crop app",
					Toast.LENGTH_SHORT).show();

			return;
		} else {
			intent.setData(mImageCaptureUri);

			intent.putExtra("outputX", 200);
			intent.putExtra("outputY", 200);
			intent.putExtra("aspectX", 1);
			intent.putExtra("aspectY", 1);
			intent.putExtra("scale", true);
			intent.putExtra("return-data", true);

			if (size == 1) {
				Intent i = new Intent(intent);
				ResolveInfo res = list.get(0);

				i.setComponent(new ComponentName(res.activityInfo.packageName,
						res.activityInfo.name));

				startActivityForResult(i, CROP_FROM_CAMERA);
			} else {
				for (ResolveInfo res : list) {
					final CropOption co = new CropOption();

					co.title = getPackageManager().getApplicationLabel(
							res.activityInfo.applicationInfo);
					co.icon = getPackageManager().getApplicationIcon(
							res.activityInfo.applicationInfo);
					co.appIntent = new Intent(intent);

					co.appIntent
							.setComponent(new ComponentName(
									res.activityInfo.packageName,
									res.activityInfo.name));

					cropOptions.add(co);
				}

				CropOptionAdapter adapter = new CropOptionAdapter(
						getApplicationContext(), cropOptions);

				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle("Choose Crop App");
				builder.setAdapter(adapter,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int item) {
								startActivityForResult(
										cropOptions.get(item).appIntent,
										CROP_FROM_CAMERA);
							}
						});

				builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
					@Override
					public void onCancel(DialogInterface dialog) {

						if (mImageCaptureUri != null) {
							getContentResolver().delete(mImageCaptureUri, null,
									null);
							mImageCaptureUri = null;
						}
					}
				});

				AlertDialog alert = builder.create();

				alert.show();
			}
		}
	}

	private void saveCutPic(Intent picdata) {
		Bundle bundle = picdata.getExtras();
		if (null != bundle) {
			Bitmap mBitmap = bundle.getParcelable("data");
			mImageView.setImageBitmap(mBitmap);
		}
		File f = new File(mImageCaptureUri.getPath());

		if (f.exists()) {
			f.delete();
		}
	}
}
