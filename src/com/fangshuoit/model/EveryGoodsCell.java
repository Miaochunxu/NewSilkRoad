package com.fangshuoit.model;

/**
 * 
 * @ClassName: EveryGoodsCell
 * @Description: TODO 商品详细页，显示的信息
 * @author 方硕IT 缪春旭
 * @date 2015-6-16 下午2:36:05
 * 
 */
public class EveryGoodsCell {

	public EveryGoodsCell(String ifSea, String logo, String state,
			String jians, String selNo, String ifComment, String name,
			String merchantName, String merchantId, String photoUrl, String id,
			String language, String content) {
		this.ifSea = ifSea;
		this.logo = logo;
		this.state = state;
		this.jians = jians;
		this.selNo = selNo;
		this.ifComment = ifComment;
		this.name = name;
		this.merchantName = merchantName;
		this.merchantId = merchantId;
		this.photoUrl = photoUrl;
		this.language = language;
		this.content = content;
	}

	// 商品是否可以购买（国际支付）
	private String ifSea;
	// 小图标
	private String logo;
	// 决定事都可以购买物品
	private String state;
	// 商品简述
	private String jians;
	// 总计销量
	private String selNo;
	// 是否可以评论
	private String ifComment;
	// 商品名称
	private String name;
	// 供应商
	private String merchantName;
	// 供应商id
	private String merchantId;
	// 商品显示的图片
	private String photoUrl;

	private String language;
	// web详情
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getIfSea() {
		return ifSea;
	}

	public void setIfSea(String ifSea) {
		this.ifSea = ifSea;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getJians() {
		return jians;
	}

	public void setJians(String jians) {
		this.jians = jians;
	}

	public String getSelNo() {
		return selNo;
	}

	public void setSelNo(String selNo) {
		this.selNo = selNo;
	}

	public String getIfComment() {
		return ifComment;
	}

	public void setIfComment(String ifComment) {
		this.ifComment = ifComment;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
}
