package com.fangshuoit.tool;

import com.fangshuoit.application.SysConstants;

public class IfHttpToStart {
	private static boolean HttpToStart(String imgUrl) {

		return imgUrl.startsWith("http");

	}

	public static String initUr(String url, String w, String h) {
		String newurl = "";
		if (HttpToStart(url)) {
			newurl = url + "?imageView2/1/w/" + w + "/h/" + h;
		} else {
			newurl = SysConstants.SERVER + url + "?imageView2/1/w/" + w + "/h/"
					+ h;
		}
		return newurl;
	}

	public static String initUr(String url) {
		String newurl = "";
		if (HttpToStart(url)) {
			newurl = url;
		} else {
			newurl = SysConstants.SERVER + url;
		}
		return newurl;
	}
}
