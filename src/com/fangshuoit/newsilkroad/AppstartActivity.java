package com.fangshuoit.newsilkroad;

import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import com.fangshuoit.adapter.ImgViewAdapter;
import com.fangshuoit.tool.LoginUtil;

public class AppstartActivity extends Activity {
	boolean isFirstIn = false;
	private static final int GO_HOME = 1000;
	private static final int GO_GUIDE = 1001;
	private static final String SHAREDPREFERENCES_NAME = "first_pref";
	private static final long SPLASH_DELAY_MILLIS = 3000;

	private ImageView imageView;

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case GO_HOME:
				goHome();
				break;
			case GO_GUIDE:
				goGuide();
				break;
			}
			super.handleMessage(msg);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_appstart);
		init();
	}

	/* 判断是否是第一次安装软件 */
	private void init() {

		imageView = (ImageView) findViewById(R.id.img_appstart);

		SharedPreferences preferences = getSharedPreferences(
				SHAREDPREFERENCES_NAME, MODE_PRIVATE);
		isFirstIn = preferences.getBoolean("isFirstIn", true);
		if (!isFirstIn) {
			mHandler.sendEmptyMessageDelayed(GO_HOME, SPLASH_DELAY_MILLIS);
		} else {
			mHandler.sendEmptyMessageDelayed(GO_GUIDE, SPLASH_DELAY_MILLIS);
		}
	}

	/* 如果不是第一次启动应用,进入主界面 */
	private void goHome() {

		Resources resources = getResources();// 获得res资源对象
		Configuration config = resources.getConfiguration();// 获得设置对象
		DisplayMetrics dm = resources.getDisplayMetrics();// 获得屏幕参数：主要是分辨率，像素等。
		if (LoginUtil.get_language() == 1) {
			config.locale = Locale.ENGLISH; // 英语
			resources.updateConfiguration(config, dm);
		} else if (LoginUtil.get_language() == 2) {
			config.locale = new Locale("ar");// 阿拉伯语
			resources.updateConfiguration(config, dm);
		} else {
			config.locale = Locale.SIMPLIFIED_CHINESE; // 简体中文
			resources.updateConfiguration(config, dm);
		}

		Intent intent = new Intent(AppstartActivity.this, MainActivity.class);
		AppstartActivity.this.startActivity(intent);
		AppstartActivity.this.finish();
	}

	/* 如果是第一次启动应用,进入导航界面 */
	private void goGuide() {

		Resources resources = getResources();// 获得res资源对象
		Configuration config = resources.getConfiguration();// 获得设置对象
		DisplayMetrics dm = resources.getDisplayMetrics();// 获得屏幕参数：主要是分辨率，像素等。

		if (config.locale == Locale.ENGLISH) {
			LoginUtil.remberlanguage(1);
		} else if (config.locale == new Locale("ar")) {
			LoginUtil.remberlanguage(2);
		}

		Intent intent = new Intent(AppstartActivity.this, GuideActivity.class);
		AppstartActivity.this.startActivity(intent);
		AppstartActivity.this.finish();
	}

	@Override
	protected void onDestroy() {
		imageView.setImageBitmap(null);
		System.gc();
		super.onDestroy();
	}

}
