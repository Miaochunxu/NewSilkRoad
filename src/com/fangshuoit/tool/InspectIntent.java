package com.fangshuoit.tool;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 
 * @ClassName: InspectIntent
 * @Description: TODO 网络判断
 * @author 方硕IT 缪春旭
 * @date 2015-7-24 下午5:26:07
 * 
 */
public class InspectIntent {

	/**
	 * 
	 * @Title: InspectAllIntent
	 * @Description: TODO(当前是否有网)
	 * @param @param context
	 * @param @return 设定文件
	 * @return boolean 返回类型
	 * @throws
	 */
	static boolean InspectAllIntent(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager
					.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	/**
	 * 
	 * @Title: InspectWifiIntent
	 * @Description: TODO(wifi是否可用)
	 * @param @param context
	 * @param @return 设定文件
	 * @return boolean 返回类型
	 * @throws
	 */
	static boolean InspectWifiIntent(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mWiFiNetworkInfo = mConnectivityManager
					.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			if (mWiFiNetworkInfo != null) {
				return mWiFiNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	/**
	 * 
	 * @Title: InspectMobileIntent
	 * @Description: TODO(手机网络是否可用)
	 * @param @param context
	 * @param @return 设定文件
	 * @return boolean 返回类型
	 * @throws
	 */
	static boolean InspectMobileIntent(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mMobileNetworkInfo = mConnectivityManager
					.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			if (mMobileNetworkInfo != null) {
				return mMobileNetworkInfo.isAvailable();
			}
		}
		return false;
	}
}
