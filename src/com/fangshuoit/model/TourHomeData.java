package com.fangshuoit.model;

public class TourHomeData {

	public TourHomeData(String id, String logo, String name, String jians) {
		this.id = id;
		this.logo = logo;
		this.name = name;
		this.jians = jians;
	}

	private String id;
	private String logo;
	private String name;
	private String jians;

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

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
