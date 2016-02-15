package com.fangshuoit.tool;

import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

/**
 * 
 * @ClassName: CheckList
 * @Description: TODO LIST操作
 * @author 方硕IT 缪春旭
 * @date 2015-6-19 上午11:14:25
 * 
 */
public class CheckList {

	/**
	 * 
	 * @Title: CheckListSubtract
	 * @Description: TODO(判断两个集合是否相等，返回boobean)
	 * @param @param a
	 * @param @param b
	 * @param @return 设定文件
	 * @return boolean 返回类型
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public static boolean CheckListSubtract(List<String> a, List<String> b) {

		Collection<String> subtract = CollectionUtils.subtract(a, b);
		if (subtract.size() == 0) {
			return true;
		} else {
			return false;
		}
	}
}
