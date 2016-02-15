package com.fangshuoit.model;

public class Touritem {

	public Touritem(int img, String content) {
		this.img = img;
		this.content = content;
	}

	private int img;
	private String content;

	public int getImg() {
		return img;
	}

	public void setImg(int img) {
		this.img = img;
	}

	public String getTitle() {
		return content;
	}

	public void setTitle(String title) {
		this.content = title;
	}

}
