package com.froad.cbank.coremodule.normal.boss.pojo.merchant;


/**
 * 商户列表返回
 * @ClassName MerchantListRes
 * @author zxl
 * @date 2016年1月20日 上午9:37:54
 */
public class MerchantListRes{
	
	private String createTime;
	private String merchantId;
	private String merchantName;
	private String merchantFullName;
	public String areaId;
	private String areaName;
	
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getMerchantFullName() {
		return merchantFullName;
	}
	public void setMerchantFullName(String merchantFullName) {
		this.merchantFullName = merchantFullName;
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

	
}
