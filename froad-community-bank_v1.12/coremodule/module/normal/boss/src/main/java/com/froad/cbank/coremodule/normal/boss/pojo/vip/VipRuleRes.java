package com.froad.cbank.coremodule.normal.boss.pojo.vip;

/**
 * @author 陆万全 luwanquan@f-road.com.cn
 * @date 创建时间：2015年6月16日 下午3:04:00
 * @version 1.0
 * @desc VIP规则响应实例
 */
public class VipRuleRes {
	private String vipId;		//VIP规则ID
	private Long createTime;	//创建时间
	private String clientId;	//客户端ID
	private String clientName;	//客户端名称
	private String activitiesName;//VIP规则名称
	private Double vipPrice;	//VIP价格
	private String status;		//状态：0-未生效、1-已生效、2-已作废
	private Integer count;		//VIP规则对应的商品总数
	
	public String getVipId() {
		return vipId;
	}
	public void setVipId(String vipId) {
		this.vipId = vipId;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
}
