package com.fangshuoit.model;

import java.io.Serializable;

public class MyOrderCell implements Serializable{

	private String goodJians;
	private String goodPhoto;
	private String goodPrice;
	private String goodName;
	private String payNo;
	private String attributeName;
	private String goodId;

	public MyOrderCell() {
		super();
	}

	public MyOrderCell(String goodJians, String goodPhoto, String goodPrice,
			String goodName, String payNo, String attributeName, String goodId) {
		super();
		this.goodJians = goodJians;
		this.goodPhoto = goodPhoto;
		this.goodPrice = goodPrice;
		this.goodName = goodName;
		this.payNo = payNo;
		this.attributeName = attributeName;
		this.goodId = goodId;
	}

	public String getGoodJians() {
		return goodJians;
	}

	public void setGoodJians(String goodJians) {
		this.goodJians = goodJians;
	}

	public String getGoodPhoto() {
		return goodPhoto;
	}

	public void setGoodPhoto(String goodPhoto) {
		this.goodPhoto = goodPhoto;
	}

	public String getGoodPrice() {
		return goodPrice;
	}

	public void setGoodPrice(String goodPrice) {
		this.goodPrice = goodPrice;
	}

	public String getGoodName() {
		return goodName;
	}

	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}

	public String getPayNo() {
		return payNo;
	}

	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}

	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public String getGoodId() {
		return goodId;
	}

	public void setGoodId(String goodId) {
		this.goodId = goodId;
	}

}
