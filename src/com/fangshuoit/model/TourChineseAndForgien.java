package com.fangshuoit.model;

public class TourChineseAndForgien {

	public TourChineseAndForgien(String img, String title, String context,
			String money) {

		this.img = img;
		this.title = title;
		this.context = context;
		this.money = money;

	}

	private String img;
	private String title;
	private String context;
	private String money;

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
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

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}
}
