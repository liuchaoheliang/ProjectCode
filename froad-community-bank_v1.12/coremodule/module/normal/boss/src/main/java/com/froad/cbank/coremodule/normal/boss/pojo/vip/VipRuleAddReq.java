package com.froad.cbank.coremodule.normal.boss.pojo.vip;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;

/**
 * @author 陆万全 luwanquan@f-road.com.cn
 * @date 创建时间：2015年6月16日 下午3:01:02
 * @version 1.0
 * @desc VIP规则新增请求参数
 */
public class VipRuleAddReq {
	@NotEmpty("客户端ID为空")
	private String clientId;	//客户端ID
	@NotEmpty("客户端名称为空")
	private String clientName;	//客户端名称
	@NotEmpty("VIP规则名称为空")
	private String activitiesName;//VIP规则名称
	@NotEmpty("VIP价格为空")
	private Double vipPrice;	//VIP价格
	
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getActivitiesName() {
		return activitiesName;
	}
	public void setActivitiesName(String activitiesName) {
		this.activitiesName = activitiesName;
	}
	public Double getVipPrice() {
		return vipPrice;
	}
	public void setVipPrice(Double vipPrice) {
		this.vipPrice = vipPrice;
	}
}
