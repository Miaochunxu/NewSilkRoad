package com.fangshuoit.tool;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * 
 * @ClassName: DispalyUtil
 * @Description: TODO 计算子控件的宽度
 * @author 方硕IT 缪春旭
 * @date 2015-6-11 下午4:00:40
 * 
 */
public class DispalyUtil {

	private static DisplayMetrics metrics = null;

	public static double getPxFromDp(Context context, double dp) {
		getDisplayMetrics(context);
		return dp * (metrics.densityDpi / 160);
	}

	private static DisplayMetrics getDisplayMetrics(Context context) {
		if (metrics == null) {
			metrics = new DisplayMetrics();
			WindowManager manager = (WindowManager) context
					.getSystemService(Context.WINDOW_SERVICE);
			manager.getDefaultDisplay().getMetrics(metrics);
		}

		return metrics;
	}

}
