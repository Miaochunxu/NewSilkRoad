package com.fangshuoit.model;

public class TourBigitem {

	public TourBigitem(int bigimg, String bigcontent, String smallcontent) {
		this.bigimg = bigimg;
		this.bigcontent = bigcontent;
		this.smallcontent = smallcontent;
	}

	private int bigimg;
	private String bigcontent;
	private String smallcontent;

	public int getBigimg() {
		return bigimg;
	}

	public void setBigimg(int bigimg) {
		this.bigimg = bigimg;
	}

	public String getBigcontent() {
		return bigcontent;
	}

	public void setBigcontent(String bigcontent) {
		this.bigcontent = bigcontent;
	}

	public String getSmallcontent() {
		return smallcontent;
	}

	public void setSmallcontent(String smallcontent) {
		this.smallcontent = smallcontent;
	}

}
