package com.fangshuoit.model;

public class CultureCell {
	// 前台是否显示
	private String ifBefore;
	// 视频
	private String mediaUrl;
	// 类型列表
	private String typesList;
	// 是否置顶
	private String ifTop;
	private String state;
	private String creater;
	private String creatTime;
	// 关键字
	private String docKeys;
	// 点击数
	private String clickNo;
	// 文化id
	private String id;
	// 内容
	private String content;
	// 作者
	private String author;
	// 排序号
	private String sorter;
	// 标题
	private String name;
	// 类型id列表
	private String typesIdList;
	// 图片
	private String photoUrl;

	public CultureCell(String ifBefore, String mediaUrl, String typesList,
			String ifTop, String state, String creater, String creatTime,
			String docKeys, String clickNo, String id, String content,
			String author, String sorter, String name, String typesIdList,
			String photoUrl) {
		super();
		this.ifBefore = ifBefore;
		this.mediaUrl = mediaUrl;
		this.typesList = typesList;
		this.ifTop = ifTop;
		this.state = state;
		this.creater = creater;
		this.creatTime = creatTime;
		this.docKeys = docKeys;
		this.clickNo = clickNo;
		this.id = id;
		this.content = content;
		this.author = author;
		this.sorter = sorter;
		this.name = name;
		this.typesIdList = typesIdList;
		this.photoUrl = photoUrl;
	}

	public String getIfBefore() {
		return ifBefore;
	}

	public void setIfBefore(String ifBefore) {
		this.ifBefore = ifBefore;
	}

	public String getMediaUrl() {
		return mediaUrl;
	}

	public void setMediaUrl(String mediaUrl) {
		this.mediaUrl = mediaUrl;
	}

	public String getTypesList() {
		return typesList;
	}

	public void setTypesList(String typesList) {
		this.typesList = typesList;
	}

	public String getIfTop() {
		return ifTop;
	}

	public void setIfTop(String ifTop) {
		this.ifTop = ifTop;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(String creatTime) {
		this.creatTime = creatTime;
	}

	public String getDocKeys() {
		return docKeys;
	}

	public void setDocKeys(String docKeys) {
		this.docKeys = docKeys;
	}

	public String getClickNo() {
		return clickNo;
	}

	public void setClickNo(String clickNo) {
		this.clickNo = clickNo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getSorter() {
		return sorter;
	}

	public void setSorter(String sorter) {
		this.sorter = sorter;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTypesIdList() {
		return typesIdList;
	}

	public void setTypesIdList(String typesIdList) {
		this.typesIdList = typesIdList;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

}
