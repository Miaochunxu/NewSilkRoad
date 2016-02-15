package com.fangshuoit.tool;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @Title: NoUtil.java
 * @Package com.fangshuoit.basic.tool
 * @Description: 根据时间生成随机编号工具类
 * @Copyright: Copyright (c) 2015
 * @Company:宁夏方硕信息技术有限公司
 * @author FangshuoIT-周庆鹏
 * @date 2015年5月12日 上午9:09:05
 */
public class NoUtil {

	/**
	 * getNoByYYYY00(根据年月日时分秒按两位随机数获取编号)
	 * 
	 * @Title: getNoByYYYY00
	 * @Description: 获取两位随机订单号
	 * @param prefix
	 * @return String 返回类型
	 * @author 周庆鹏
	 */
	public static String getNoByYYYY00(String prefix) {
		Random random = new Random();
		DecimalFormat df = new DecimalFormat("00");
		String no = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())
				+ df.format(random.nextInt(100));
		if (!StringUtils.isEmpty(prefix)) {
			no = prefix + no;
		}
		return no;
	}

	/**
	 * getNoByYYYY0000(根据年月日时分秒按四位随机数获取编号)
	 * 
	 * @Title: getNoByYYYY0000
	 * @Description: 获取四位随机订单编号
	 * @param prefix
	 * @return String 返回类型
	 * @author 周庆鹏
	 */
	public static String getNoByYYYY0000(String prefix) {
		Random random = new Random();
		DecimalFormat df = new DecimalFormat("0000");
		String no = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())
				+ df.format(random.nextInt(10000));
		if (!StringUtils.isEmpty(prefix)) {
			no = prefix + no;
		}
		return no;
	}

	/**
	 * getNoByYY0000(根据年月日时分秒按四位随机数获取编号)
	 * 
	 * @Title: getNoByYY0000
	 * @Description: 获取四位随机订单号
	 * @param prefix
	 * @return String 返回类型
	 * @author 周庆鹏
	 */
	public static String getNoByYY0000(String prefix) {
		Random random = new Random();
		DecimalFormat df = new DecimalFormat("0000");
		String no = new SimpleDateFormat("yyMMddHHmmss").format(new Date())
				+ df.format(random.nextInt(10000));
		if (!StringUtils.isEmpty(prefix)) {
			no = prefix + no;
		}
		return no;
	}

	/**
	 * getNoByYY(根据年月日时分秒按无随机数获取编号)
	 * 
	 * @Title: getNoByYY
	 * @Description: 获取年月日订单号
	 * @param prefix
	 * @return String 返回类型
	 * @author 周庆鹏
	 */
	public static String getNoByYY(String prefix) {
		String no = new SimpleDateFormat("yyMMddHHmmss").format(new Date());
		if (!StringUtils.isEmpty(prefix)) {
			no = prefix + no;
		}
		return no;
	}
}
