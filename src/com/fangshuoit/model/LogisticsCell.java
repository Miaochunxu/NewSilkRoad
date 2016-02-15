package com.fangshuoit.model;

import java.io.Serializable;

/**
 * 
 * @ClassName: LogisticsCell
 * @Description: TODO 物流
 * @author 方硕IT 缪春旭
 * @date 2015-7-29 下午4:22:44
 * 
 */
@SuppressWarnings("serial")
public class LogisticsCell implements Serializable {
	// 签收状态
	private String signstatus;
	private String id;
	private String name;
	private String creatTime;
	// 物流订单号
	private String sumbers;

	public LogisticsCell() {
		super();
	}

	public LogisticsCell(String signstatus, String id, String name,
			String creatTime, String sumbers) {
		super();
		this.signstatus = signstatus;
		this.id = id;
		this.name = name;
		this.creatTime = creatTime;
		this.sumbers = sumbers;
	}

	public String getSignstatus() {
		return signstatus;
	}

	public void setSignstatus(String signstatus) {
		this.signstatus = signstatus;
	}

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

	public String getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(String creatTime) {
		this.creatTime = creatTime;
	}

	public String getSumbers() {
		return sumbers;
	}

	public void setSumbers(String sumbers) {
		this.sumbers = sumbers;
	}
}
