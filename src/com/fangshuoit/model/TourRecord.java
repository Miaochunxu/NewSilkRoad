package com.fangshuoit.model;

public class TourRecord {

	public TourRecord(String title, String username, String number,
			String time, String img, String headimg) {

		this.title = title;
		this.username = username;
		this.number = number;
		this.time = time;
		this.img = img;
		this.headimg = headimg;

	}

	private String title;
	private String username;
	private String number;
	private String time;
	private String img;
	private String headimg;

	public String getHeadimg() {
		return headimg;
	}

	public void setHeadimg(String headimg) {
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

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

}
