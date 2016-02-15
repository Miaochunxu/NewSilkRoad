package com.fangshuoit.model;

public class ShoppingCarListCell {

	private String logo;
	private String name;
	private String jians;
	private String price;
	private String goodNo;
	private String id;
	private String goodId;
	private String attrList;

	public ShoppingCarListCell(String logo, String name, String jians,
			String price, String goodNo, String id, String goodId,
			String attrList) {
		super();
		this.logo = logo;
		this.name = name;
		this.jians = jians;
		this.price = price;
		this.goodNo = goodNo;
		this.id = id;
		this.goodId = goodId;
		this.attrList = attrList;
	}

	public String getGoodId() {
		return goodId;
	}

	public void setGoodId(String goodId) {
		this.goodId = goodId;
	}

	public String getAttrList() {
		return attrList;
	}

	public void setAttrList(String attrList) {
		this.attrList = attrList;
	}

	public String getId() {
		return id;
	}

	public String getGoodNo() {
		return goodNo;
	}

	public void setGoodNo(String goodNo) {
		this.goodNo = goodNo;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getJians() {
		return jians;
	}

	public void setJians(String jians) {
		this.jians = jians;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

}
