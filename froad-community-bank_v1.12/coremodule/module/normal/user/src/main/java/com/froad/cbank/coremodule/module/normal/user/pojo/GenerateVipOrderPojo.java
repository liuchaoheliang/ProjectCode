package com.froad.cbank.coremodule.module.normal.user.pojo;

/**
 * 开通VIP资格订单参数实体
 */
public class GenerateVipOrderPojo {

	private String vipId;
	private String areaId;
	private String areaName;
	private String orgCode;
	private String createSource;
	private String clientBankType;
	
	
	
	public String getVipId() {
		return vipId;
	}
	public void setVipId(String vipId) {
		this.vipId = vipId;
	}
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getCreateSource() {
		return createSource;
	}
	public void setCreateSource(String createSource) {
		this.createSource = createSource;
	}
	public String getClientBankType() {
		return clientBankType;
	}
	public void setClientBankType(String clientBankType) {
		this.clientBankType = clientBankType;
	}
	
	
	
}
