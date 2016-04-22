package com.froad.cbank.coremodule.normal.boss.pojo.system;

import java.io.Serializable;

/**
 * 
 * @ClassName: UserListVoRes
 * @author chenzhangwei@f-road.com.cn
 * @createTime 2016年1月6日 下午6:36:08
 * @desc <p>
 *       查询用户列表返回实体
 *       </p>
 */
public class UserListVoRes implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7402371975110117641L;
	/**
	 * id
	 */
	private long id;
	/**
	 * 创建时间
	 */
	private long createTime;
	/**
	 * 更新时间
	 */
	private long updateTime;
	/**
	 * 登录名
	 */
	private String username;
	/**
	 * 手机号
	 */
	private String mobile;
	/**
	 * 邮箱号
	 */
	private String email;
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 0-禁用 1-启用
	 */
	private String status;
	/**
	 * 1-是 0-否
	 */
	private String isReset;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 组织Id
	 */
	private String orgId;
	/**
	 * 组织名称
	 */
	private String orgName;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIsReset() {
		return isReset;
	}

	public void setIsReset(String isReset) {
		this.isReset = isReset;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

}
