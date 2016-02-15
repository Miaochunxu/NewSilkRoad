package com.fangshuoit.model;

/**
 * 
 * @ClassName: GoodToAttribute
 * @Description: TODO 商品详细页，最终需要提交的信息
 * @author 方硕IT 缪春旭
 * @date 2015-6-16 下午2:35:40
 * 
 */
public class GoodToAttribute {
	public GoodToAttribute(String id, String goodNo, String price, String memo,
			String selNo, String state, String attributeId, String disPrice) {

		this.id = id;
		this.goodNo = goodNo;
		this.price = price;
		this.memo = memo;
		this.selNo = selNo;
		this.state = state;
		this.attributeId = attributeId;
		this.disPrice = disPrice;

	}

	// 最终加入购物车或者购买需要的id，已经关联好属性
	private String id;
	// 当前属性下商品的库存
	private String goodNo;
	// 当前属性下的商品价格
	private String price;
	// 当前属性下的商品图片
	private String memo;
	// 当前属性下商品的销量
	private String selNo;
	// 当前属性下商品是否可以购买（state=1为可以购买或加入收藏）
	private String state;
	// 关联的属性id，以“;”隔开
	private String attributeId;
	// 当前属性下商品的折扣价
	private String disPrice;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGoodNo() {
		return goodNo;
	}

	public void setGoodNo(String goodNo) {
		this.goodNo = goodNo;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getSelNo() {
		return selNo;
	}

	public void setSelNo(String selNo) {
		this.selNo = selNo;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getAttributeId() {
		return attributeId;
	}

	public void setAttributeId(String attributeId) {
		this.attributeId = attributeId;
	}

	public String getDisPrice() {
		return disPrice;
	}

	public void setDisPrice(String disPrice) {
		this.disPrice = disPrice;
	}
}
