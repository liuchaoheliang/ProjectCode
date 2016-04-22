package com.froad.cbank.coremodule.normal.boss.pojo.ad;

import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.common.valid.Regular;
import com.froad.cbank.coremodule.normal.boss.pojo.Page;

/**
 * 广告列表查询请求对象
 * @author luwanquan@f-road.com.cn
 * @date 2015年9月21日 下午4:29:25
 */
public class AdListReq extends Page {
	private Long adLocationId;//广告位ID
	private String clientId;//客户端ID
	@Regular(reg = "^[\\s\\S]{0,30}$", value = "广告名称限30字内")
	private String title;//广告标题
	
	public Long getAdLocationId() {
		return adLocationId;
	}
	public void setAdLocationId(Long adLocationId) {
		this.adLocationId = adLocationId;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = StringUtil.isBlank(clientId) ? null : clientId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = StringUtil.isBlank(title) ? null : title;
	}
}
