package com.fangshuoit.tool;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.fangshuoit.application.FSApplication;

public class SharedPreferencesHelper {

	public static final String APP_SHARD = "fanghusoit";
	private static SharedPreferences mSharedPreferences = getSharedPreferences();
	private static Editor mEditor = getEditor();

	public static SharedPreferences getSharedPreferences() {

		if (mSharedPreferences == null) {
			mSharedPreferences = FSApplication.getAppContext()
					.getSharedPreferences("newsilkroad", Context.MODE_PRIVATE);
		}
		return mSharedPreferences;
	}

	public static Editor getEditor() {

		if (mEditor == null) {
			mEditor = mSharedPreferences.edit();
		}
		return mEditor;
	}

	public static void putString(String key, String value) {
		mEditor.putString(key, value);
		mEditor.commit();
	}

	public static void putString(String key, int value) {
		mEditor.putInt(key, value);
		mEditor.commit();
	}

	public static void putString(String key, boolean value) {
		mEditor.putBoolean(key, value);
		mEditor.commit();
	}

	public static void putString(String key, Long value) {
		mEditor.putLong(key, value);
		mEditor.commit();
	}

	public static void putString(String key, Float value) {
		mEditor.putFloat(key, value);
		mEditor.commit();
	}

	public static String getString(String key, String value) {
		return mSharedPreferences.getString(key, value);
	}

	public static boolean getBoolean(String key, boolean defValue) {
		return mSharedPreferences.getBoolean(key, defValue);
	}

	public static float getFloat(String key, float defValue) {
		return mSharedPreferences.getFloat(key, defValue);
	}

	public static int getInt(String key, int defValue) {
		return mSharedPreferences.getInt(key, defValue);
	}
}
