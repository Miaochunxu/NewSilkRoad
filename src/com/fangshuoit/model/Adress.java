package com.fangshuoit.model;

public class Adress {

	private String id;
	private String phone;
	private String memo;
	private String county;
	private String address;
	private String name;
	private String userId;
	private String province;
	private String creatTime;
	private String city;

	public Adress() {
		super();
	}

	public Adress(String id, String phone, String memo, String county,
			String address, String name, String userId, String province,
			String creatTime, String city) {
		super();
		this.id = id;
		this.phone = phone;
		this.memo = memo;
		this.county = county;
		this.address = address;
		this.name = name;
		this.userId = userId;
		this.province = province;
		this.creatTime = creatTime;
		this.city = city;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(String creatTime) {
		this.creatTime = creatTime;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

}
