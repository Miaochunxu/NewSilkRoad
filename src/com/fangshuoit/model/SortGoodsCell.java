/**   
 * @Title: SortGoodsCell.java 
 * @Package com.fangshuoit.model 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author 方硕IT 缪春旭    
 * @date 2015-5-21 上午8:42:30   
 */
package com.fangshuoit.model;

/**
 * @ClassName: SortGoodsCell
 * @Description: TODO 商品列表
 * @author 方硕IT 缪春旭
 * @date 2015-5-21 上午8:42:30
 * 
 */
public class SortGoodsCell {

	public SortGoodsCell(String id, String logo, String price, String name,
			String commentNo, String jians) {
		this.commentNo = commentNo;
		this.id = id;
		this.jians = jians;
		this.logo = logo;
		this.price = price;
		this.name = name;
	}

	private String id;
	private String logo;
	private String price;
	private String name;
	private String commentNo;
	private String jians;

	public String getId() {
		return id;
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

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCommentNo() {
		return commentNo;
	}

	public void setCommentNo(String commentNo) {
		this.commentNo = commentNo;
	}

	public String getJians() {
		return jians;
	}

	public void setJians(String jians) {
		this.jians = jians;
	}
}
