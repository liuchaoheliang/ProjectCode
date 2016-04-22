package com.froad.cbank.coremodule.module.normal.user.vo;

public class PointsTypeVo {
	
	/**
	 * 积分类型  0-联盟积分，1-银行积分
	 */
	private String type;
	/**
	 * 积分名称
	 */
	private String typeName;
	/**
	 * 积分数量
	 */
	private String total;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	
}
