package com.fangshuoit.model;

import com.fangshuoit.tool.JSONUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class OrderInfoBody {

	private String order;
	private String goodNameList;

	public OrderInfo getOrderInfo() {
		OrderInfo orderInfo = new OrderInfo();
		Gson gson = new Gson();
		String body = JSONUtils.getString(this.order, "body", "");
		String code = JSONUtils.getString(this.order, "code", "");
		if (code.equals("0")) {
			orderInfo = gson.fromJson(body, new TypeToken<OrderInfo>() {
			}.getType());
		}
		return orderInfo;
	}

	public OrderInfoBody() {
		super();
	}

	public OrderInfoBody(String order, String goodNameList) {
		super();
		this.order = order;
		this.goodNameList = goodNameList;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getGoodNameList() {
		return goodNameList;
	}

	public void setGoodNameList(String goodNameList) {
		this.goodNameList = goodNameList;
	}
}
