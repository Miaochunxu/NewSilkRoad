package com.fangshuoit.model;

public class TicketCell {
	public TicketCell(String img , String bigtv,String smalltv,String price) {
		this.img = img;
		this.bigtv = bigtv;
		this.smalltv = smalltv;
		this.price = price;
	}

	private String img;
	private String bigtv;
	private String smalltv;
	private String price;

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getBigtv() {
		return bigtv;
	}

	public void setBigtv(String bigtv) {
		this.bigtv = bigtv;
	}

	public String getSmalltv() {
		return smalltv;
	}

	public void setSmalltv(String smalltv) {
		this.smalltv = smalltv;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

}
