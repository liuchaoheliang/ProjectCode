package com.froad.cbank.coremodule.normal.boss.pojo.system;


import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;
/**
 *  角色删除请求VO
 * @author liaopeixin
 *	@date 2016年1月12日 下午4:42:15
 */
public class FinityRoleVoDeleteReq{

	@NotEmpty(value = "角色ID不能为空!")
	private Long id;
	/**
	 * 平台名称（boss、bank、merchant）
	 */
	@NotEmpty(value = "平台名称不能为空!")
	private String platform;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}

	
}
