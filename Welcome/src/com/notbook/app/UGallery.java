package com.notbook.app;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Gallery;

public class UGallery extends Gallery {

	public UGallery(Context context, AttributeSet attrs) {

		super(context, attrs);

	}

	private boolean isScrollingLeft(MotionEvent e1, MotionEvent e2) {
	    return e2.getX() < e1.getX();
	}

	private boolean isScrollingRight(MotionEvent e1, MotionEvent e2) {
	    return e2.getX() > e1.getX();
	}

	int mSelection = 0;

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
	        float velocityY) {
	    boolean leftScroll = isScrollingLeft(e1, e2);
	    boolean rightScroll = isScrollingRight(e1, e2);

	    if (rightScroll) {
	        if (mSelection != 0)             
	            setSelection(--mSelection, true);
	    } else if (leftScroll) {

	        if (mSelection != getCount() - 1)
	            setSelection(++mSelection, true);
	    }
	    return false;
	}

}