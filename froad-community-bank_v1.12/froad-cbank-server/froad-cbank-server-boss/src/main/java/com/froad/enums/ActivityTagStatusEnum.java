package com.froad.enums;

public enum ActivityTagStatusEnum {
	
	enable("1", "启用"),
	disable("2", "禁用"),
	add_auditing("3", "新增审核中"),
	edit_auditing("4", "编辑审核中"),
	disable_auditing("5", "禁用审核中"),
	;
	
	private ActivityTagStatusEnum(String status, String desc){
		this.status = status;
		this.desc = desc;
	}
	
	private String status;
	private String desc;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
}
