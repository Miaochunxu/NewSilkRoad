package com.fangshuoit.tool;

public class LoginUtil {
	public static void rember_Count(String user, String passw) {
		SharedPreferencesHelper.putString("user", user);
		SharedPreferencesHelper.putString("passw", passw);
	}

	// 记录用户默认收货地址信息
	public static void rember_Adress_default(String adress_id,
			String adress_name, String adress_phone, String adress_adress) {
		SharedPreferencesHelper.putString("adress_id", adress_id);
		SharedPreferencesHelper.putString("adress_name", adress_name);
		SharedPreferencesHelper.putString("adress_phone", adress_phone);
		SharedPreferencesHelper.putString("adress_adress", adress_adress);
	}

	// 得到用户默认地址ID
	public static String get_Adress_id() {
		return SharedPreferencesHelper.getString("adress_id", "");
	}

	// 得到用户默认地址收货人姓名
	public static String get_Adress_name() {
		return SharedPreferencesHelper.getString("adress_name", "");
	}

	// 得到用户默认地址联系方式
	public static String get_Adress_phone() {
		return SharedPreferencesHelper.getString("adress_phone", "");
	}

	// 得到用户默认地址
	public static String get_Adress_adress() {
		return SharedPreferencesHelper.getString("adress_adress", "");
	}

	// 记录密码
	public static void rember_passWorld(String passw) {
		SharedPreferencesHelper.putString("passw", passw);
	}

	// 记录当前跳转的Activity
	public static void rember_activityFrom(String activityFrom) {
		SharedPreferencesHelper.putString("activityFrom", activityFrom);
	}

	// 得到当前跳转的Activity
	public static String get_activityFrom() {
		return SharedPreferencesHelper.getString("activityFrom", "");
	}

	// 记录当前用户昵称
	public static void rember_nickname(String nickname) {
		SharedPreferencesHelper.putString("nickname", nickname);
	}

	// 得到当前用户的昵称
	public static String get_nickname() {
		return SharedPreferencesHelper.getString("nickname", "");
	}

	// 记录获取图片的尺寸
	public static void rember_ImageSize(int Xsize) {
		SharedPreferencesHelper.putString("Xsize", Xsize);
	}

	// 得到图片尺寸X
	public static int get_ImageSizeX() {
		return SharedPreferencesHelper.getInt("Xsize", 0);

	}

	// 记录当前选择的地区ID
	public static void remberPlaceId(String placeId) {
		SharedPreferencesHelper.putString("placeId", placeId);
	}

	// 得到当前地区的ID
	public static String get_PlaceId() {
		return SharedPreferencesHelper.getString("placeId", "");

	}

	// 记录当前选择的地区
	public static void remberPlace(String place) {
		SharedPreferencesHelper.putString("place", place);
	}

	// 记录当前选中的语言
	public static void remberlanguage(int language) {
		SharedPreferencesHelper.putString("language", language);
	}

	// 记录跳转的界面
	public static void remberIntentKey(String intentKey) {
		SharedPreferencesHelper.putString("intentKey", intentKey);
	}

	public static void remberUerId(String userid) {
		SharedPreferencesHelper.putString("userid", userid);
	}

	public static void remberTag(String tag) {
		SharedPreferencesHelper.putString("tag", tag);
	}

	public static void remberIsCheck(String isCheckRe) {
		SharedPreferencesHelper.putString("isCheckRe", isCheckRe);
	}

	public static void remberIsLogin(boolean is_Login) {
		SharedPreferencesHelper.putString("is_Logibn", is_Login);
	}

	public static boolean get_is_Login() {
		return SharedPreferencesHelper.getBoolean("is_Logibn", false);
	}

	// 得到选择的区域
	public static String get_Place() {
		return SharedPreferencesHelper.getString("place", "定位");

	}

	// 得到选中的语言信息
	public static int get_language() {
		return SharedPreferencesHelper.getInt("language", 0);

	}

	// 得到界面跳转信息
	public static String get_IntentKey() {
		return SharedPreferencesHelper.getString("intentKey", "");

	}

	public static String get_UserName() {
		return SharedPreferencesHelper.getString("user", "");

	}

	public static String get_UserPassw() {
		return SharedPreferencesHelper.getString("passw", "");

	}

	public static String get_IsCheckRe() {
		return SharedPreferencesHelper.getString("isCheckRe", "");

	}

	public static String get_UserId() {
		return SharedPreferencesHelper.getString("userid", "");

	}

	public static String get_Tag() {
		return SharedPreferencesHelper.getString("tag", "tag");
	}

	// 清除昵称，用户名和密码
	public static void emptyData() {
		SharedPreferencesHelper.putString("nickname", "");
		SharedPreferencesHelper.putString("user", "");
		SharedPreferencesHelper.putString("passw", "");
	}
}
