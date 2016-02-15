package com.fangshuoit.model;

public class MyCollectionCell {

	// 收藏简述
	private String goodJians;
	// 收藏图片
	private String goodLogo;
	// 收藏id
	private String collectionId;
	// 收藏类型
	private String collectionTypes;
	// 收藏商品id
	private String goodId;
	// 收藏内容
	private String goodName;
	// 收藏状态
	private String collectionState;
	private String creatTime;

	public MyCollectionCell(String goodJians, String goodLogo,
			String collectionId, String collectionTypes, String goodId,
			String goodName, String collectionState, String creatTime) {
		super();
		this.goodJians = goodJians;
		this.goodLogo = goodLogo;
		this.collectionId = collectionId;
		this.collectionTypes = collectionTypes;
		this.goodId = goodId;
		this.goodName = goodName;
		this.collectionState = collectionState;
		this.creatTime = creatTime;
	}

	public String getGoodJians() {
		return goodJians;
	}

	public void setGoodJians(String goodJians) {
		this.goodJians = goodJians;
	}

	public String getGoodLogo() {
		return goodLogo;
	}

	public void setGoodLogo(String goodLogo) {
		this.goodLogo = goodLogo;
	}

	public String getCollectionId() {
		return collectionId;
	}

	public void setCollectionId(String collectionId) {
		this.collectionId = collectionId;
	}

	public String getCollectionTypes() {
		return collectionTypes;
	}

	public void setCollectionTypes(String collectionTypes) {
		this.collectionTypes = collectionTypes;
	}

	public String getGoodId() {
		return goodId;
	}

	public void setGoodId(String goodId) {
		this.goodId = goodId;
	}

	public String getGoodName() {
		return goodName;
	}

	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}

	public String getCollectionState() {
		return collectionState;
	}

	public void setCollectionState(String collectionState) {
		this.collectionState = collectionState;
	}

	public String getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(String creatTime) {
		this.creatTime = creatTime;
	}
}
