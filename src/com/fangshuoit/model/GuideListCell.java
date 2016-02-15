package com.fangshuoit.model;

public class GuideListCell {

	public GuideListCell(String logo, String title, String context, String time) {

		this.logo = logo;
		this.title = title;
		this.context = context;
		this.time = time;
	}

	private String logo;
	private String title;
	private String context;
	private String time;

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

}
