package com.fangshuoit.tool;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;

import com.fangshuoit.application.FSApplication;

/**
 * 基础工具类
 * 
 * @project NewSilkRoad
 * @Company 宁夏方硕信息技术有限公司
 * @author 周庆鹏
 * @phone 15009666891
 * @QQ 872123675
 * @Blog http://blog.csdn.net/lovelvyan
 * @date 2015-4-13
 * @time 上午11:45:29
 */
public class Tool {

	private static String mUserAgent = null;

	public static String getExternalStoragePath() {
		boolean bExists = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);
		String path = null;
		if (bExists) {
			path = Environment.getExternalStorageDirectory().getAbsolutePath();

			if (path == null) {
				return "/";
			}
			if ("/mnt/flash".equalsIgnoreCase(path)) {
				path = "/mnt/sdcard";
				File file = new File(path);
				if (!file.exists()) {
					path = "/sdcard";
					file = new File(path);
					if (!file.exists()) {
						path = "/";
					}
				}
			}
			return path;
		} else {
			return "/";
		}
	}

	public static String getFileDir() {
		String dir = getExternalStoragePath() + "/carserver/";
		File file = new File(dir);
		if (!file.exists()) {
			file.mkdirs();
		}
		return dir;
	}

	public static void makeServerDir() {
		String dir = getExternalStoragePath() + "/carserver/";
		File file = new File(dir);
		if (!file.exists()) {
			file.mkdir();
		}
		File file1 = new File(dir + "temp");
		if (!file1.exists()) {
			file1.mkdir();
		}
	}

	public static String getAppAgent() {
		if (null == mUserAgent) {
			mUserAgent = "cargard android " + getVersionName();
		}
		return mUserAgent;
	}

	public static String getVersionName() {
		PackageManager packageManager = FSApplication.getInstance()
				.getPackageManager();
		PackageInfo packInfo = null;
		try {
			packInfo = packageManager.getPackageInfo(FSApplication
					.getInstance().getPackageName(),
					PackageManager.GET_CONFIGURATIONS);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

		return (packInfo != null) ? packInfo.versionName : "";
	}

	/**
	 * 判断网络连接是否存在
	 * 
	 **/
	public static boolean hasInternet(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = manager.getActiveNetworkInfo();
		if (info == null || !info.isConnected()) {
			return false;
		}
		if (info.isRoaming()) {
			return true;
		}
		return true;
	}

	/**
	 * 获取版本号
	 * 
	 * @Description: TODO
	 * @date 2015-4-13
	 * @time 上午11:54:27
	 * @author lvyan
	 * @return
	 */
	public static String getVersionCode(Context context) {
		String ver = "1.0";
		try {
			PackageManager packageManager = context.getPackageManager();
			PackageInfo info = packageManager.getPackageInfo(
					context.getPackageName(), 0);

			if (info.versionCode > 0) {
				ver = String.valueOf(info.versionCode);
			} else if (info.versionName != null) {
				ver = info.versionName;
			}
		} catch (Exception e) {
		}
		return ver;
	}

	public static boolean isIntentAvailable(Context context, Intent intent) {
		final PackageManager packageManager = context.getPackageManager();
		List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
				PackageManager.MATCH_DEFAULT_ONLY);
		return list.size() > 0;
	}

	/**
	 * 判断是否是URL
	 * 
	 * @Description: TODO
	 * @date 2015-4-13
	 * @time 上午11:53:55
	 * @author lvyan
	 * @return
	 */
	public static boolean isHttpUrl(String url) {
		String header = "http";
		int indexOf = url.indexOf(header);
		if (indexOf == 0) {
			return true;
		}
		return false;
	}

	/**
	 * dip尺寸转成像素尺寸
	 * 
	 * @Description: TODO
	 * @date 2015-4-13
	 * @time 上午11:53:15
	 * @author lvyan
	 * @return
	 */
	public static int dipToPx(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/*
	 * 转换编码格式
	 */
	public static String getString(String oldStr, String oldEncode,
			String encode) {
		String newStr = "";
		try {
			newStr = new String(oldStr.getBytes(oldEncode), encode);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return newStr;
	}

}
