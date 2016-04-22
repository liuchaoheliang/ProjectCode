package com.froad.cbank.coremodule.normal.boss.pojo.ad;

import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.common.valid.Regular;
import com.froad.cbank.coremodule.normal.boss.pojo.Page;

/**
 * 广告位列表查询请求对象
 * @author luwanquan@f-road.com.cn
 * @date 2015年9月21日 下午2:34:42
 */
public class AdPositionListReq extends Page {
	private String terminalType;//终端类型 1-pc 2-android 3-ios
	@Regular(reg = "^[\\s\\S]{0,20}$", value = "广告位名称限20字内")
	private String name;//广告位名称
	private String enableStatus;//启用状态（是否启用）
	
	public String getTerminalType() {
		return terminalType;
	}
	public void setTerminalType(String terminalType) {
		this.terminalType = StringUtil.isBlank(terminalType) ? null : terminalType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = StringUtil.isBlank(name) ? null : name;
	}
	public String getEnableStatus() {
		return enableStatus;
	}
	public void setEnableStatus(String enableStatus) {
		this.enableStatus = StringUtil.isBlank(enableStatus) ? null : enableStatus;
	}
}
