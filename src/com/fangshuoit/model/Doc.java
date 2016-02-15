package com.fangshuoit.model;

public class Doc  {
	private String id;
	private String name;
	private String typesList;
	private String typesIdList;
	private String state;
	private String content;
	private String sorter;
	private String author;
	private String docKeys;
	private String ifTop;
	private String ifBefore;
	private String clickNo;
	private String photoUrl;
	private String mediaUrl;
	private String creater;
	private String creatTime;
	private String memo;
	private String reservation;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTypesList() {
		return typesList;
	}
	public void setTypesList(String typesList) {
		this.typesList = typesList;
	}
	public String getTypesIdList() {
		return typesIdList;
	}
	public void setTypesIdList(String typesIdList) {
		this.typesIdList = typesIdList;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSorter() {
		return sorter;
	}
	public void setSorter(String sorter) {
		this.sorter = sorter;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getDocKeys() {
		return docKeys;
	}
	public void setDocKeys(String docKeys) {
		this.docKeys = docKeys;
	}
	public String getIfTop() {
		return ifTop;
	}
	public void setIfTop(String ifTop) {
		this.ifTop = ifTop;
	}
	public String getIfBefore() {
		return ifBefore;
	}
	public void setIfBefore(String ifBefore) {
		this.ifBefore = ifBefore;
	}
	public String getClickNo() {
		return clickNo;
	}
	public void setClickNo(String clickNo) {
		this.clickNo = clickNo;
	}
	public String getPhotoUrl() {
		return photoUrl;
	}
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
	public String getMediaUrl() {
		return mediaUrl;
	}
	public void setMediaUrl(String mediaUrl) {
		this.mediaUrl = mediaUrl;
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
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getReservation() {
		return reservation;
	}
	public void setReservation(String reservation) {
		this.reservation = reservation;
	}
	@Override
	public String toString() {
		return "Doc [id=" + id + ", name=" + name + ", typesList=" + typesList
				+ ", typesIdList=" + typesIdList + ", state=" + state
				+ ", content=" + content + ", sorter=" + sorter + ", author="
				+ author + ", docKeys=" + docKeys + ", ifTop=" + ifTop
				+ ", ifBefore=" + ifBefore + ", clickNo=" + clickNo
				+ ", photoUrl=" + photoUrl + ", mediaUrl=" + mediaUrl
				+ ", creater=" + creater + ", creatTime=" + creatTime
				+ ", memo=" + memo + ", reservation=" + reservation + "]";
	}
	public Doc(String id, String name, String typesList, String typesIdList,
			String state, String content, String sorter, String author,
			String docKeys, String ifTop, String ifBefore, String clickNo,
			String photoUrl, String mediaUrl, String creater, String creatTime,
			String memo, String reservation) {
		super();
		this.id = id;
		this.name = name;
		this.typesList = typesList;
		this.typesIdList = typesIdList;
		this.state = state;
		this.content = content;
		this.sorter = sorter;
		this.author = author;
		this.docKeys = docKeys;
		this.ifTop = ifTop;
		this.ifBefore = ifBefore;
		this.clickNo = clickNo;
		this.photoUrl = photoUrl;
		this.mediaUrl = mediaUrl;
		this.creater = creater;
		this.creatTime = creatTime;
		this.memo = memo;
		this.reservation = reservation;
	}
	public Doc() {
		super();
		// TODO Auto-generated constructor stub
	}


}
