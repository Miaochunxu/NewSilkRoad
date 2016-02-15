package com.fangshuoit.model;

public class PlaceId {
	public PlaceId(String id, String name, String selfId) {
		this.id = id;
		this.name = name;
		this.selfId = selfId;
	}

	private String id;
	private String name;
	private String selfId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSelfId() {
		return selfId;
	}

	public void setSelfId(String selfId) {
		this.selfId = selfId;
	}

}
