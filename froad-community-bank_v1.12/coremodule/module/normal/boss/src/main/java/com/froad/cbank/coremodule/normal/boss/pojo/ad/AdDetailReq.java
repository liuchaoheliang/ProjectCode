package com.froad.cbank.coremodule.normal.boss.pojo.ad;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;


/**
 * 广告详情请求对象
 * @author luwanquan@f-road.com.cn
 * @date 2015年9月21日 下午4:34:32
 */
public class AdDetailReq {
	@NotEmpty("客户端ID为空")
	private String clientId;
	private Long id;

	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
}
