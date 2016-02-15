package com.fangshuoit.model;

public class OrderPayWay {

	private String id;
	private String reservation;

	public OrderPayWay(String id, String reservation) {
		super();
		this.id = id;
		this.reservation = reservation;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getReservation() {
		return reservation;
	}

	public void setReservation(String reservation) {
		this.reservation = reservation;
	}

}
