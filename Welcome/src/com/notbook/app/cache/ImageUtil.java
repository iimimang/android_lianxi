package com.notbook.app.cache;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

public class ImageUtil {
	   //图片剪切!
		public static void imageCut(File tempFile, Activity Activity) {
			 Intent intent = new Intent(Intent.ACTION_PICK, null);
//			 Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);  
//			intent.setType("image/*");
//			intent.setClassName("com.android.camera", "com.android.camera.CropImage"); 
//			intent.setData(Uri.fromFile(tempFile)); 
//			intent.putExtra("outputX", width); 
//			intent.putExtra("outputY", height); 
//			intent.putExtra("aspectX", width); 
//			intent.putExtra("aspectY", height); 
//			intent.putExtra("scale", true); 
//			intent.putExtra("noFaceDetection", true); 
//			intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(tempFile)); 
//			startActivityForResult(intent, REQUEST_CROP_IMAGE); 
			intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");  
			
			intent.putExtra("crop", "true");// crop=true 有这句才能出来最后的裁剪页面.
//	        intent.putExtra("input", Uri.fromFile(tempFile));
			intent.putExtra("aspectX", 1);// 这两项为裁剪框的比例.
			intent.putExtra("aspectY", 1);// x:y=1:2
			intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "temp.jpg")));
//			intent.putExtra("output", Uri.fromFile(tempFile));
			intent.putExtra("outputFormat", "JPEG");// 返回格式

			Activity
					.startActivityForResult(Intent.createChooser(intent, "选择图片"), 2);
		}

		// 放大缩小图片
		public static Bitmap zoomBitmap(Bitmap bitmap, int w, int h) {
			int width = bitmap.getWidth();
			int height = bitmap.getHeight();
			Matrix matrix = new Matrix();
			float scaleWidht = ((float) w / width);
			float scaleHeight = ((float) h / height);
			matrix.postScale(scaleWidht, scaleHeight);
			Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, width, height,
					matrix, true);
			return newbmp;
		}

		// 切割图片
		public static Bitmap cutBitmap(Bitmap bitmap, int x, int y, int width,
				int height) {
			Bitmap newbmp = Bitmap.createBitmap(bitmap, x, y, width, height);
			return newbmp;
		}

		// 将Drawable转化为Bitmap
		public static Bitmap drawableToBitmap(Drawable drawable) {
			int width = drawable.getIntrinsicWidth();
			int height = drawable.getIntrinsicHeight();
			Bitmap bitmap = Bitmap.createBitmap(width, height, drawable
					.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
					: Bitmap.Config.RGB_565);
			Canvas canvas = new Canvas(bitmap);
			drawable.setBounds(0, 0, width, height);
			drawable.draw(canvas);
			return bitmap;

		}

		// 获得圆角图片的方�?
		public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {

			Bitmap output = Bitmap.createScaledBitmap(bitmap,bitmap.getWidth(), bitmap
					.getHeight(), true);
			Canvas canvas = new Canvas(output);

			final int color = 0xff424242;
			final Paint paint = new Paint();
			final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
			final RectF rectF = new RectF(rect);
//			Bitmap roundBitmap = ImageUtil.getRoundedCornerBitmap(bitmap, 10.0f);

			paint.setAntiAlias(true);
			canvas.drawARGB(0, 0, 0, 0);
			paint.setColor(color);
			canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

			paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
			canvas.drawBitmap(bitmap, rect, rect, paint);

			return output;
		}

		// 获得带�?影的图片方法
		public static Bitmap createReflectionImageWithOrigin(Bitmap bitmap) {
			final int reflectionGap = 4;
			int width = bitmap.getWidth();
			int height = bitmap.getHeight();

			Matrix matrix = new Matrix();
			matrix.preScale(1, -1);

			Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 0, height / 2,
					width, height / 2, matrix, false);

			Bitmap bitmapWithReflection = Bitmap.createBitmap(width,
					(height + height / 2), Config.ARGB_8888);

			Canvas canvas = new Canvas(bitmapWithReflection);
			canvas.drawBitmap(bitmap, 0, 0, null);
			Paint deafalutPaint = new Paint();
			canvas
					.drawRect(0, height, width, height + reflectionGap,
							deafalutPaint);

			canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);

			Paint paint = new Paint();
			LinearGradient shader = new LinearGradient(0, bitmap.getHeight(), 0,
					bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff,
					0x00ffffff, TileMode.CLAMP);
			paint.setShader(shader);
			// Set the Transfer mode to be porter duff and destination in
			paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
			// Draw a rectangle using the paint with our linear gradient
			canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()
					+ reflectionGap, paint);

			return bitmapWithReflection;
		}

		public static void saveMyBitmap(String bitName, Bitmap mBitmap)
				throws IOException {
//			 Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
			File file = new File(bitName, System
					.currentTimeMillis()
					+ ".jpg");
			FileOutputStream outStream = new FileOutputStream(file);
			mBitmap.compress(CompressFormat.JPEG, 100, outStream);
			outStream.close();

		}
		
	}
