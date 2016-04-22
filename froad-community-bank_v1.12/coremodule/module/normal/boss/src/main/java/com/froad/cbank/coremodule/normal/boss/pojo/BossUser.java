package com.froad.cbank.coremodule.normal.boss.pojo;

public class BossUser {

	/**
	 * id
	 */
	private long id;

	/**
	 * 登陆名
	 */
	private String username;

	/**
	 * 手机号
	 */
	private String mobile;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 操作员姓名
	 */
	private String name;

	/**
	 * 部门
	 */
	private String department;

	/**
	 * 职位
	 */
	private String position;

	/**
	 * 是否可用 0-不可用，1-可用
	 */
	private boolean isEnable;

	/**
	 * 是否重置
	 */
	private boolean isReset;
	/**
	 * 备注
	 */
	private String remark;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public boolean isEnable() {
		return isEnable;
	}
	public void setEnable(boolean isEnable) {
		this.isEnable = isEnable;
	}
	public boolean isReset() {
		return isReset;
	}
	public void setReset(boolean isReset) {
		this.isReset = isReset;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
