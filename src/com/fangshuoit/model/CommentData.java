package com.fangshuoit.model;

/**
 * 
 * @ClassName: CommentData
 * @Description: TODO 评论数据model
 * @author 方硕IT 缪春旭
 * @date 2015-6-16 上午9:16:47
 * 
 */
public class CommentData {

	private String id;
	private String ifPhotoUrl;
	private String content;
	private String username;
	private String level;
	private String opId;
	private String creatTime;
	private String types;
	private String praiseNo;
	private String ifLable;

	public CommentData() {
		super();
	}

	public CommentData(String id, String ifPhotoUrl, String content,
			String username, String level, String opId, String creatTime,
			String types, String praiseNo, String ifLable) {
		super();
		this.id = id;
		this.ifPhotoUrl = ifPhotoUrl;
		this.content = content;
		this.username = username;
		this.level = level;
		this.opId = opId;
		this.creatTime = creatTime;
		this.types = types;
		this.praiseNo = praiseNo;
		this.ifLable = ifLable;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIfPhotoUrl() {
		return ifPhotoUrl;
	}

	public void setIfPhotoUrl(String ifPhotoUrl) {
		this.ifPhotoUrl = ifPhotoUrl;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getOpId() {
		return opId;
	}

	public void setOpId(String opId) {
		this.opId = opId;
	}

	public String getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(String creatTime) {
		this.creatTime = creatTime;
	}

	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
	}

	public String getPraiseNo() {
		return praiseNo;
	}

	public void setPraiseNo(String praiseNo) {
		this.praiseNo = praiseNo;
	}

	public String getIfLable() {
		return ifLable;
	}

	public void setIfLable(String ifLable) {
		this.ifLable = ifLable;
	}

}
