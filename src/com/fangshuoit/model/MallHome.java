package com.fangshuoit.model;

/**
 * @author 宁夏方硕-缪春旭
 * @ClassName: MallHome
 * @Description: TODO(商城主页信息model)
 * 
 */
public class MallHome {

	public MallHome(String id, String typeName, String logo, String name,
			String typeId) {

		this.id = id;
		this.typeName = typeName;
		this.logo = logo;
		this.name = name;
		this.typeId = typeId;

	}

	private String id;
	private String typeName;
	private String logo;
	private String name;
	private String typeId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

}
