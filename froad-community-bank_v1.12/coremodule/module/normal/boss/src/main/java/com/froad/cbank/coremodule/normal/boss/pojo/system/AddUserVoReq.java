package com.froad.cbank.coremodule.normal.boss.pojo.system;

import java.util.List;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;

/**
 * 
 * @ClassName: AddUserVoReq
 * @author chenzhangwei@f-road.com.cn
 * @createTime 2016年1月6日 下午4:25:22 
 * @desc <p>添加用户请求req</p>
 */
public class AddUserVoReq {
	
	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 登录名
	 */
	@NotEmpty(value="登录名不能为空")
	private String userName;
	
	
	private String password;
	/**
	 * 手机
	 */
	@NotEmpty(value="手机号码不能为空")
	private String mobile;
	/**
	 * 邮箱
	 */
	@NotEmpty(value="邮箱不能为空")
	private String email;
	/**
	 * 姓名
	 */
	@NotEmpty(value="姓名不能为空")
	private String name;
	/**
	 * 状态
	 */
	@NotEmpty(value="状态不能为空")
	private String status;
	/**
	 * 是否重置密码
	 */
	@NotEmpty(value="是否重置密码不能为空")
	private String isReset;
	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 关联组织机构
	 */
	private List<String> orgIds;
	/**
	 * 关联用户角色
	 */
	private List<Long> roleIds;
	
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public List<String> getOrgIds() {
		return orgIds;
	}
	public void setOrgIds(List<String> orgIds) {
		this.orgIds = orgIds;
	}
	public List<Long> getRoleIds() {
		return roleIds;
	}
	public void setRoleIds(List<Long> roleIds) {
		this.roleIds = roleIds;
	}
	
	
}
