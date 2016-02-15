package com.fangshuoit.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alipay.sdk.util.LogUtils;
import com.fangshuoit.tool.DispalyUtil;

public class AutoLineFeedLayout extends ViewGroup {

	private Context context;

	private int childHeight;
	private int childWidthRatio;

	private int chidlWidthSpace;
	private int childHeightSpace;

	public AutoLineFeedLayout(Context context) {
		super(context);
		this.context = context;
	}

	public AutoLineFeedLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	public AutoLineFeedLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
	}

	/**
	 * 统一设置childView的高度;通过该值可以计算整体布局的高度
	 * 
	 * @param childHeight
	 */
	public void setChildHeight(int childHeight) {
		this.childHeight = childHeight;
	}

	/**
	 * 统一设置childView的宽度比例系数。textView.length * ratio
	 * 
	 * @param childWidthRatio
	 */
	public void setChildWidthRatio(int childWidthRatio) {
		this.childWidthRatio = childWidthRatio;
	}

	/**
	 * 设置childView之间的左右间隔大小
	 * 
	 * @param childViewWidthSpace
	 */
	public void setChildViewWidthSpace(int childViewWidthSpace) {
		chidlWidthSpace = childViewWidthSpace;
		requestLayout();
	}

	/**
	 * 设置childView之间的上下间隔大小
	 * 
	 * @param childHeightSpace
	 */
	public void setChildHeightSpace(int childHeightSpace) {
		this.childHeightSpace = childHeightSpace;
		requestLayout();
	}

	/**
	 * 计算控件及子控件所占区域
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// 父控件宽度
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);

		// 父控件高度--方法：累计每个子控件的宽度+左右间隔，当数值超过父控件的宽度时，换行。记录行数。
		// 记录ViewGroup中Child的总个数
		int count = getChildCount();
		int lines = 1;

		int currWidth = 0; // 每行当前宽度总和
		for (int index = 0; index < count; index++) {

			final TextView child = (TextView) getChildAt(index);
			int childWidth = getWidth(child);

			currWidth += (chidlWidthSpace + childWidth);// 左侧间隔+子控件宽度

			// 另起一行
			if (currWidth > widthSize) {
				currWidth = 0;
				currWidth += (chidlWidthSpace + childWidth);// 左侧间隔+子控件宽度
				lines++;
			}
		}
		// 子控件高度+间隔
		int heightSize = childHeight * lines + (lines + 1) * childHeightSpace;

		LogUtils.e("widthSize = " + widthSize + " heightSize = " + heightSize);
		setMeasuredDimension(widthSize, heightSize);
	}

	/**
	 * 子控件的宽度通过计算字符数转化而来
	 * 
	 * @param view
	 * @return
	 */
	private int getWidth(TextView view) {
		int width = (int) DispalyUtil.getPxFromDp(context, view.length()
				* childWidthRatio);
		return width;
	}

	/**
	 * 控制子控件的换行
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		LogUtils.d("changed = " + changed + " left = " + l + " top = " + t
				+ " right = " + r + " bottom = " + b);

		int parentWidth = r - l;
		// int parentHeight = b - t;

		int x = 0;
		int y = 0;

		int count = getChildCount();
		for (int j = 0; j < count; j++) {
			final TextView childView = (TextView) getChildAt(j);
			// 获取子控件Child的宽高
			int w = getWidth(childView);
			int h = childHeight;
			// 计算子控件的顶点坐标
			int left = x + chidlWidthSpace;
			int top = y + childHeightSpace;
			int right = left + w;
			int bottom = top + h;

			if (right > parentWidth) { // 子控件右侧坐标超过父控件，则换行
				x = 0;
				y = bottom;

				left = x + chidlWidthSpace;
				top = y + childHeightSpace;
				right = left + w;
				bottom = top + h;
			}

			// x向右平移
			x = right;

			// 布局子控件
			childView.layout(left, top, right, bottom);
			childView.setVisibility(View.VISIBLE);
			System.out.println(childView.getMeasuredWidth() + ":"
					+ childView.getMeasuredHeight());
		}

	}

}
