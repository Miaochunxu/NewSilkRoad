package com.fangshuoit.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.ab.view.sliding.AbSlidingPlayView;

public class MyAbSlidingPlayView extends AbSlidingPlayView {

	// 滑动距离及坐标
	private float xDistance, yDistance, xLast, yLast;

	public MyAbSlidingPlayView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			xDistance = yDistance = 0f;
			xLast = ev.getX();
			yLast = ev.getY();
			break;
		case MotionEvent.ACTION_MOVE:

			final float curX = ev.getX();
			final float curY = ev.getY();

			xDistance += Math.abs(curX - xLast);
			yDistance += Math.abs(curY - yLast);

			if (xDistance > yDistance) {
				getParent().requestDisallowInterceptTouchEvent(true);
				return false;
			} else {
				getParent().requestDisallowInterceptTouchEvent(false);
				return true;
			}
		}
		return super.onInterceptTouchEvent(ev);
	}
}
