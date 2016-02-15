package com.fangshuoit.application;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Process;
import android.util.Log;

import com.fangshuoit.tool.DebugLog;
import com.fangshuoit.tool.LoginUtil;

/**
 * 项目全局变量
 * 
 * @project NewSilkRoad
 * @Company 宁夏方硕信息技术有限公司
 * @author 周庆鹏
 * @phone 15009666891
 * @QQ 872123675
 * @Blog http://blog.csdn.net/lovelvyan
 * @date 2015-4-13
 * @time 下午2:00:04
 */

public class FSApplication extends Application {

	private static final String TAG = "JPush";
	private static Context mContext;
	private static FSApplication instance;
	private List<Activity> mActivityList = new LinkedList<Activity>();

	public static FSApplication getInstance() {
		return instance;
	}

	@Override
	public void onCreate() {
		Log.d(TAG, "[ExampleApplication] onCreate");
		mContext = this;
		super.onCreate();
		instance = this;

		if (Locale.getDefault().getLanguage().equals("ar")) {
			LoginUtil.remberlanguage(2);
		} else if (Locale.getDefault().getLanguage().equals("en")) {
			LoginUtil.remberlanguage(1);
		} else {
			LoginUtil.remberlanguage(0);
		}

		// Resources resources = getResources();// 获得res资源对象
		// Configuration config = resources.getConfiguration();// 获得设置对象
		// DisplayMetrics dm = resources.getDisplayMetrics();//
		// 获得屏幕参数：主要是分辨率，像素等。
		//
		// config.locale = Locale.SIMPLIFIED_CHINESE;// 简体中文
		// resources.updateConfiguration(config, dm);

		// 极光推送
		// JPushInterface.setDebugMode(true); // 设置开启日志,发布时请关闭日志
		// JPushInterface.init(this); // 初始化 JPush

		DebugLog.logw("...AppApplication OnCreate    Application onCreate... pid="
				+ Process.myPid());
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
	}

	public static Context getAppContext() {
		return mContext;
	}

	/*
	 * 添加Activity到容器中
	 */
	public synchronized void addActivity(Activity activity) {
		mActivityList.add(activity);
	}

	public void applicationExit() {
		while (mActivityList.size() > 0) {
			Activity activity = mActivityList.get(mActivityList.size() - 1);
			mActivityList.remove(mActivityList.size() - 1);
			activity.finish();
		}
	}

	public synchronized void delActivity(Activity activity) {
		int size = mActivityList.size();
		for (int i = size - 1; i >= 0; i--) {
			if (activity == mActivityList.get(i)) {
				mActivityList.remove(i);
				break;
			}
		}
	}

	public Activity getStackTopActivity() {

		if (mActivityList != null && mActivityList.size() > 0) {
			return mActivityList.get(mActivityList.size() - 1);
		}
		return null;
	}

	public void finishExcept(Activity activity) {
		if (mActivityList != null) {
			int cnt = mActivityList.size();
			for (int i = 0; i < cnt; i++) {
				if (activity != mActivityList.get(i)) {
					mActivityList.get(i).finish();
				}
			}
			mActivityList.clear();
			mActivityList.add(activity);
		}
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
	}

	public void logoutApp() {

	}
}
