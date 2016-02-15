package com.fangshuoit.model;


/**
 * 
 * @ClassName: EveryGoodsType
 * @Description: TODO 商品属性类型cell
 * @author 方硕IT 缪春旭
 * @date 2015-6-16 下午3:43:49
 * 
 */
public class EveryGoodsType {

	public EveryGoodsType(String id, String level, String name, String state,
			String selfId, String photoUrl) {
		super();
		this.id = id;
		this.level = level;
		this.name = name;
		this.state = state;
		this.selfId = selfId;
		this.photoUrl = photoUrl;
	}

	// 类型id
	private String id;
	// 级别
	private String level;
	// 内容
	private String name;
	// 是否可以购买
	private String state;
	// 上级id
	private String selfId;
	// 后期使用
	private String photoUrl;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getSelfId() {
		return selfId;
	}

	public void setSelfId(String selfId) {
		this.selfId = selfId;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
}
