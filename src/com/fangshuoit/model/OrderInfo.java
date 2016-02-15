package com.fangshuoit.model;

import java.io.Serializable;

import com.fangshuoit.tool.JSONUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class OrderInfo implements Serializable {

	private String DAddress;
	private String outState;
	private String memo;
	private String DUserSimple;
	private String orderName;
	private String creatTime;
	private String payTime;
	private String id;
	private String amount;
	private String payState;
	private String reservation;
	private String payType;
	private String merchantName;
	private String orderPrice;
	private String merchantId;
	private String paymentState;
	private String DExpress;

	public LogisticsCell getLogisticsCell() {
		LogisticsCell logisticsCell = new LogisticsCell();
		Gson gson = new Gson();
		String body = JSONUtils.getString(this.DExpress, "body", "");
		String code = JSONUtils.getString(this.DExpress, "code", "");
		if (code.equals("0")) {
			logisticsCell = gson.fromJson(body, new TypeToken<LogisticsCell>() {
			}.getType());
		}
		return logisticsCell;
	}

	public Adress getAdress() {
		Adress adress = new Adress();
		Gson gson = new Gson();
		String body = JSONUtils.getString(this.DAddress, "body", "");
		String code = JSONUtils.getString(this.DAddress, "code", "");
		if (code.equals("0")) {
			adress = gson.fromJson(body, new TypeToken<Adress>() {
			}.getType());
		}
		return adress;
	}

	public OrderInfo() {
		super();
	}

	public OrderInfo(String dAddress, String outState, String memo,
			String dUserSimple, String orderName, String creatTime,
			String payTime, String id, String amount, String payState,
			String reservation, String payType, String merchantName,
			String orderPrice, String merchantId, String paymentState,
			String dExpress) {
		super();
		DAddress = dAddress;
		this.outState = outState;
		this.memo = memo;
		DUserSimple = dUserSimple;
		this.orderName = orderName;
		this.creatTime = creatTime;
		this.payTime = payTime;
		this.id = id;
		this.amount = amount;
		this.payState = payState;
		this.reservation = reservation;
		this.payType = payType;
		this.merchantName = merchantName;
		this.orderPrice = orderPrice;
		this.merchantId = merchantId;
		this.paymentState = paymentState;
		DExpress = dExpress;
	}

	public String getDAddress() {
		return DAddress;
	}

	public void setDAddress(String dAddress) {
		DAddress = dAddress;
	}

	public String getOutState() {
		return outState;
	}

	public void setOutState(String outState) {
		this.outState = outState;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getDUserSimple() {
		return DUserSimple;
	}

	public void setDUserSimple(String dUserSimple) {
		DUserSimple = dUserSimple;
	}

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public String getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(String creatTime) {
		this.creatTime = creatTime;
	}

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getPayState() {
		return payState;
	}

	public void setPayState(String payState) {
		this.payState = payState;
	}

	public String getReservation() {
		return reservation;
	}

	public void setReservation(String reservation) {
		this.reservation = reservation;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(String orderPrice) {
		this.orderPrice = orderPrice;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getPaymentState() {
		return paymentState;
	}

	public void setPaymentState(String paymentState) {
		this.paymentState = paymentState;
	}

	public String getDExpress() {
		return DExpress;
	}

	public void setDExpress(String dExpress) {
		DExpress = dExpress;
	}

}
