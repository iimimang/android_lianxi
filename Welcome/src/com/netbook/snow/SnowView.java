package com.netbook.snow;

import java.util.Random;

import com.example.notbook.R;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;

public class SnowView extends View {
	int MAX_SNOW_COUNT = 60;
	// 雪花图片
	Bitmap bitmap_snows = null;
	
	Bitmap bitmap_snow = null;
	// 画笔
	private final Paint mPaint = new Paint();
	// 随即生成器
	private static final Random random = new Random();
	// 花的位置
	private Snow[] snows = new Snow[MAX_SNOW_COUNT];
	// 屏幕的高度和宽度
	int view_height = 0;
	int view_width = 0;
	int MAX_SPEED = 8;
	// 0 雨 1 雪
	int type = 0;

	/**
	 * 构造器
	 * 
	 * 
	 */
	public SnowView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public SnowView(Context context, AttributeSet attrs) {
		super(context, attrs);

	}

	/**
	 * 加载天女散花的花图片到内存中
	 * 
	 */
	public void LoadRainImage() {
		type = 0;
		Resources r = this.getContext().getResources();
		bitmap_snows = small(((BitmapDrawable)r.getDrawable(R.drawable.ww7)).getBitmap(),0.05f,0.9f);
	}
	

	public void LoadSnowImage() {
		 type = 1;
		Resources r = this.getContext().getResources();
		bitmap_snows = small(((BitmapDrawable) r.getDrawable(R.drawable.flake))
				.getBitmap(),0.2f,0.2f);
	}

	 private static Bitmap small(Bitmap bitmap,float w,float h) {
		  Matrix matrix = new Matrix(); 
		  matrix.postScale(w,h); //长和宽放大缩小的比例
		  Bitmap resizeBmp = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
		  return resizeBmp;
      }
	
	/**
	 * 设置当前窗体的实际高度和宽度
	 * 
	 */
	public void SetView(int height, int width) {
//		view_height = height - 100;
		view_width = width - 50;
		view_height = height;
//		view_width = width;

	}

	/**
	 * 随机的生成花朵的位置
	 * 
	 */
	public void addRandomSnow() {
		for(int i =0; i< MAX_SNOW_COUNT;i++){
			snows[i] = new Snow(random.nextInt(view_width), 0,random.nextInt(7)+2);
		}
	}
	public void addRandomRain() {
		for(int i =0; i< MAX_SNOW_COUNT;i++){
			snows[i] = new Snow(random.nextInt(view_width), 0,(int)(Math.random()*100)+35);
		}
	}


	@Override
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		if(type == 0){
			for (int i = 0; i < MAX_SNOW_COUNT; i += 1) {
				if (snows[i].coordinate.x >= view_width || snows[i].coordinate.y >= view_height) {
					snows[i].coordinate.y = 0;
					snows[i].coordinate.x = random.nextInt(view_width);
				}
				// 雪花下落的速度
				snows[i].coordinate.y += snows[i].speed;
				//雪花飘动的效果

				// 随机产生一个数字，让雪花有水平移动的效果
				//为了动画的自然性，如果水平的速度大于雪花的下落速度，那么水平的速度我们取下落的速度。
//				snows[i].coordinate.x += snows[i].speed < tmp ? snows[i].speed : tmp;
				canvas.drawBitmap(bitmap_snows, ((float) snows[i].coordinate.x+1),
						((float) snows[i].coordinate.y), mPaint);
				
			}
			
		}else if(type == 1){
			
			for (int i = 0; i < MAX_SNOW_COUNT; i += 1) {
				if (snows[i].coordinate.x >= view_width || snows[i].coordinate.y >= view_height) {
					snows[i].coordinate.y = 0;
					snows[i].coordinate.x = random.nextInt(view_width);
				}
				// 雪花下落的速度
				snows[i].coordinate.y += snows[i].speed;
				//雪花飘动的效果
	 
				// 随机产生一个数字，让雪花有水平移动的效果
					snows[i].coordinate.x += 3;
				//为了动画的自然性，如果水平的速度大于雪花的下落速度，那么水平的速度我们取下落的速度。
			
				canvas.drawBitmap(bitmap_snows, ((float) snows[i].coordinate.x),
						((float) snows[i].coordinate.y), mPaint);
			}
			
			for (int i = 0; i < 30; i += 1) {
				if (snows[i].coordinate.x >= view_width || snows[i].coordinate.y >= view_height) {
					snows[i].coordinate.y = 0;
					snows[i].coordinate.x = random.nextInt(400);
				}
				// 雪花下落的速度
				snows[i].coordinate.y += snows[i].speed;
				//雪花飘动的效果
	 
				// 随机产生一个数字，让雪花有水平移动的效果
					snows[i].coordinate.x -= 6;
					
				//为了动画的自然性，如果水平的速度大于雪花的下落速度，那么水平的速度我们取下落的速度。
			
				canvas.drawBitmap(bitmap_snows, ((float) snows[i].coordinate.x),
						((float) snows[i].coordinate.y), mPaint);
			}
			
			
			
			
			
		}
		
		
		

	}

}
