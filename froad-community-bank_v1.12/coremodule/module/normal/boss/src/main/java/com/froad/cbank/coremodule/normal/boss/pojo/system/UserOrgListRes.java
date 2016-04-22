package com.froad.cbank.coremodule.normal.boss.pojo.system;

import java.io.Serializable;

/**
 * 
 * @ClassName: UserOrgListRes
 * @author chenzhangwei@f-road.com.cn
 * @createTime 2016年1月7日 下午3:15:24 
 * @desc <p>用户机构列表返回vo</p>
 */
public class UserOrgListRes implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 827451368233882888L;
	/**
	   * 主键id
	   */
	  private long id; // optional
	  /**
	   * 组织Id
	   */
	  private String orgId; // optional
	  /**
	   * 父级组织代码，顶级组织的父级组织Id为0
	   */
	  private long parentId; // optional
	  /**
	   * 组织级别
	   */
	  private int level; // optional
	  /**
	   * 平台名称(boss、bank、merchant)
	   */
	  private String platform; // optional
	  /**
	   * 所属客户端
	   */
	  private String clientId; // optional
	  /**
	   * 树路径(从顶级到自己本身的ID，逗号分隔)
	   */
	  private String treePath; // optional
	  /**
	   * 组织代码(boss对应主键ID，bank对应机构号，merchant对应商户Id)
	   */
	  private String code; // optional
	  /**
	   * 组织名(boss对应方付通部门，bank对应机构名，merchant对应商户名)
	   */
	  private String orgName; // optional
	  /**
	   * 电话号码
	   */
	  private String phone; // optional
	  /**
	   * 区域Id
	   */
	  private long areaId; // optional
	  /**
	   * 地址信息
	   */
	  private String address; // optional
	  /**
	   * 是否删除
	   */
	  private boolean isDelete; // optional
	  
	  
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public long getParentId() {
		return parentId;
	}
	public void setParentId(long parentId) {
		this.parentId = parentId;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getTreePath() {
		return treePath;
	}
	public void setTreePath(String treePath) {
		this.treePath = treePath;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public long getAreaId() {
		return areaId;
	}
	public void setAreaId(long areaId) {
		this.areaId = areaId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public boolean isDelete() {
		return isDelete;
	}
	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}
	  
	  
}
