package com.froad.cbank.coremodule.normal.boss.pojo.message;

public class SmsElementVoRes {

	private String id;// 主键id
	private String name;// 元素名称
	private String typeName;// 类名
	private String registValue;// 注册属性
	private Integer mapType;// 映射方式
	private String mapValue;// 映射值

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

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getRegistValue() {
		return registValue;
	}

	public void setRegistValue(String registValue) {
		this.registValue = registValue;
	}

	public Integer getMapType() {
		return mapType;
	}

	public void setMapType(Integer mapType) {
		this.mapType = mapType;
	}

	public String getMapValue() {
		return mapValue;
	}

	public void setMapValue(String mapValue) {
		this.mapValue = mapValue;
	}

}
