package com.example.calendargridview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;

public class MyGridView extends GridView {

	private float xDown;
	private float xUp;
	private Context context;

	public MyGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	public MyGridView(Context context) {
		super(context);
		this.context = context;
	}

	public MyGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
	}

	// 该自定义控件只是重写了GridView的onMeasure方法，使其不会出现滚动条，ScrollView嵌套ListView也是同样的道理，不再赘述。
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		((CalendarActivity) context).createVelocityTracker(event);
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// 手指按下时，记录按下时的横坐标
			xDown = event.getRawX();
			break;
		case MotionEvent.ACTION_UP:
			// 手指抬起时，进行判断当前手势的意图，从而决定是滚动到左侧布局，还是滚动到右侧布局
			xUp = event.getRawX();
			if (xUp > xDown) {
				 if(((CalendarActivity) context).DoIt()){
				((CalendarActivity) context).LastMonth();
			}
			 }
			else if (xUp < xDown) {
				if (((CalendarActivity) context).DoIt()) {
					((CalendarActivity) context).NextMonth();
				}
			}
			((CalendarActivity) context).recycleVelocityTracker();
			break;
		}
		return super.onTouchEvent(event);
	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}