package com.fangshuoit.tool;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密工具类
 * 
 * @project 易2易客户端
 * @Company 宁夏方硕信息技术有限公司
 * @author 周庆鹏
 * @phone 15009666891
 * @QQ 872123675
 * @Blog http://blog.csdn.net/lovelvyan
 * @date 2014-11-30
 * @time 下午12:38:56
 */

public class MD5Util {
	/**
	 * 加密方法
	 * 
	 * @Description: TODO
	 * @date 2014-11-30
	 * @time 下午12:39:35
	 * @author lvyan
	 * @return String
	 */
	public static final String md5(String s) {
		try {
			MessageDigest digest = java.security.MessageDigest
					.getInstance("MD5");
			digest.update(s.getBytes());
			byte messageDigest[] = digest.digest();
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < messageDigest.length; i++) {
				String h = Integer.toHexString(0xFF & messageDigest[i]);
				while (h.length() < 2)
					h = "0" + h;
				hexString.append(h);
			}
			String md5s = hexString.toString();
			DebugLog.logv("after MD5", md5s);
			return md5s;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		DebugLog.loge("MD5 Error", s);
		return "";
	}
}
