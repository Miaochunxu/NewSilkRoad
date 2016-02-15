package com.fangshuoit.application;

import java.util.ArrayList;
import java.util.List;

import com.fangshuoit.model.Adress;
import com.fangshuoit.model.EveryGoodsType;
import com.fangshuoit.model.GoodToAttribute;
import com.fangshuoit.model.SearchCell;
import com.fangshuoit.model.ShoppingCarListCell;

public class TourConstants {
	// 独家推荐id
	public static final String DJTJ = "402880f94dd5d11c014dd5e49b170001";
	// 旅游向导id
	public static final String LYXD = "402880f94dd5d11c014dd5e4ed7c0003";
	// 更多推荐id
	public static final String GDTJ = "402880f94dd5d11c014dd5e53e300005";
	// 商城特产推荐
	public static final String MALLTCTJ = "402880f94dd7833a014dd793e4de0003";

	// 已经排序完成的类型List
	public static List<EveryGoodsType> everyGoodsTypeSortList = new ArrayList<EveryGoodsType>();
	// 记录level为2的String在list里的位置
	public static List<Integer> numberList = new ArrayList<Integer>();
	// 记录最终要匹配的id的集合
	public static List<List<String>> finishIdList = new ArrayList<List<String>>();
	// 记录level为3的类型的selfid的集合
	public static List<String> finishSelfid = new ArrayList<String>();
	// 最终提交给后台的Id
	public static List<GoodToAttribute> submitId = new ArrayList<GoodToAttribute>();
	// 管理收货地址
	public static List<Adress> adresses = new ArrayList<Adress>();
	// 购物车选中结算的商品列表
	public static List<ShoppingCarListCell> shoppingcarList = new ArrayList<ShoppingCarListCell>();
	// 搜索结果列表
	public static List<SearchCell> searchCellList = new ArrayList<SearchCell>();
	// 付款订单号
	public static String payOrderId = "";
	// 订单号
	public static String OrderId = "";
}
