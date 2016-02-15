package com.fangshuoit.model;

public class SearchCell {

	private String logo;
	private String memo;
	private String ifTop;
	private String state;
	private String creatTime;
	private String jians;
	private String id;
	private String selNo;
	private String sorter;
	private String name;
	private String reservation;
	private String language;
	private String photoUrl;

	public SearchCell(String logo, String memo, String ifTop, String state,
			String creatTime, String jians, String id, String selNo,
			String sorter, String name, String reservation, String language,
			String photoUrl) {
		super();
		this.logo = logo;
		this.memo = memo;
		this.ifTop = ifTop;
		this.state = state;
		this.creatTime = creatTime;
		this.jians = jians;
		this.id = id;
		this.selNo = selNo;
		this.sorter = sorter;
		this.name = name;
		this.reservation = reservation;
		this.language = language;
		this.photoUrl = photoUrl;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
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

	public String getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(String creatTime) {
		this.creatTime = creatTime;
	}

	public String getJians() {
		return jians;
	}

	public void setJians(String jians) {
		this.jians = jians;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSelNo() {
		return selNo;
	}

	public void setSelNo(String selNo) {
		this.selNo = selNo;
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

	public String getReservation() {
		return reservation;
	}

	public void setReservation(String reservation) {
		this.reservation = reservation;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

}