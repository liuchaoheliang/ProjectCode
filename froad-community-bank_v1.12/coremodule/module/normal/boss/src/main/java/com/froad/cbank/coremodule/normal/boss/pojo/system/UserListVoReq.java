package com.froad.cbank.coremodule.normal.boss.pojo.system;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;
import com.froad.cbank.coremodule.normal.boss.pojo.Page;

/**
 * 
 * @ClassName: UserListVoReq
 * @author chenzhangwei@f-road.com.cn
 * @createTime 2016年1月6日 下午4:03:13 
 * @desc <p>查询用户列表请求vo</p>
 */
public class UserListVoReq extends Page{
	/**
	 * 组织Id
	 */
	@NotEmpty(value="组织id不能为空")
	private String orgId; // optional
	/**
	 * 用户姓名(模糊查询)
	 */
	private String name; // optional
	/**
	 *  用户登录名(精确查找)
	 */
	private String username; // optional
	/**
	 * 状态
	 */
	
	private String status; // optional
	  
	/**
	 * type 1-当前组织下查找 2-当前节点、子节点以及孙子节点下查找
	 */
	@NotEmpty(value="查询类型不能为空")
	private String type;

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	  
	   
}
