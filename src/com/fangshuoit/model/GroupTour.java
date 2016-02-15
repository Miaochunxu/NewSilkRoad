package com.fangshuoit.model;

public class GroupTour {

	public GroupTour(String title, String username, String number,
			String money, String time, int img, int headimg) {

		this.title = title;
		this.username = username;
		this.number = number;
		this.money = money;
		this.time = time;
		this.img = img;
		this.headimg = headimg;

	}

	private String title;
	private String username;
	private String number;
	private String money;
	private String time;
	private int img;
	private int headimg;

	public int getHeadimg() {
		return headimg;
	}

	public void setHeadimg(int headimg) {
		this.headimg = headimg;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getImg() {
		return img;
	}

	public void setImg(int img) {
		this.img = img;
	}

}
