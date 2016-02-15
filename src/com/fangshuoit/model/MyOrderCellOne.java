package com.fangshuoit.model;

public class MyOrderCellOne {

	private String order;
	private String orderId;
	// 支付状态
	private String payState;
	// 收货状态
	private String outState;
	// 订单状态
	private String paymentState;

	public MyOrderCellOne() {
		super();
	}

	public MyOrderCellOne(String order, String orderId, String payState,
			String outState, String paymentState) {
		super();
		this.order = order;
		this.orderId = orderId;
		this.payState = payState;
		this.outState = outState;
		this.paymentState = paymentState;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getPayState() {
		return payState;
	}

	public void setPayState(String payState) {
		this.payState = payState;
	}

	public String getOutState() {
		return outState;
	}

	public void setOutState(String outState) {
		this.outState = outState;
	}

	public String getPaymentState() {
		return paymentState;
	}

	public void setPaymentState(String paymentState) {
		this.paymentState = paymentState;
	}

}
