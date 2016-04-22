package com.froad.cbank.coremodule.normal.boss.pojo.system;

import java.io.Serializable;

/**
 * 
 * @ClassName: UserRolesRes
 * @author chenzhangwei@f-road.com.cn
 * @createTime 2016年1月7日 上午11:26:56 
 * @desc <p>查询用户有关角色</p>
 */
public class UserRolesRes implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2974167306218743887L;
	/**
	   * 主键id 角色id
	   */
	  private long id; // optional
	  /**
	   * 客户端id
	   */
	  private String clientId; // optional
	  /**
	   * 角色名称
	   */
	  private String roleName; // optional
	  /**
	   * 0-不可用 1-可用
	   */
	  private boolean status; // optional
	  /**
	   * 备注
	   */
	  private String remark; // optional
	  /**
	   * 是否删除 0-未删除 1-删除
	   */
	  private boolean isDelete; // optional
	  /**
	   * 标明角色身份，0法人行社管理员  2管理员 1普通角色
	   */
	  private String tag; // optional
	  /**
	   * 创建角色的机构号
	   */
	  private String orgCode; // optional
	  
	   /**
	   * 来源 0-继承自组织 1-用户直接分配
	   */
	  private Integer source; // optional
	  
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public boolean isDelete() {
		return isDelete;
	}
	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserRolesRes other = (UserRolesRes) obj;
		if (id != other.id)
			return false;
		return true;
	}
	public Integer getSource() {
		return source;
	}
	public void setSource(Integer source) {
		this.source = source;
	}
	
	
	  
	  
}
