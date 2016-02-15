package com.fangshuoit.model;

public class TourAvds {

	private String id;
	private String urls;

	public TourAvds(String id, String urls) {
		super();
		this.id = id;
		this.urls = urls;
	}

	public String getUrls() {
		return urls;
	}

	public void setUrls(String urls) {
		this.urls = urls;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
