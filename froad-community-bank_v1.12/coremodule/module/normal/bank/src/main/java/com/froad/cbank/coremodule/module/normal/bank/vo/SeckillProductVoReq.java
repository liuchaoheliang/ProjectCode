package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

/**
 * 秒杀商品请求参数
 * 
 * @author liuyanyun
 *
 */
public class SeckillProductVoReq extends BaseVo implements Serializable {

	private static final long serialVersionUID = -8702168294635766094L;

	private String parentOrgCode;
	private String orgCode;
	private String productType; // 商品名称类型
	private String productName; // 秒杀商品名称
	
	private String auditState; // 审核状态
	
	private Integer secStore;// 秒杀限量
	private Integer buyLimit;// 限购数量
	
	private String productId;// 商品ID
	private String secPrice;// 秒杀价
	private String vipSecPrice;// VIP秒杀家

	private String isMarketable;// 上下架状态
	
	public String getParentOrgCode() {
		return parentOrgCode;
	}

	public void setParentOrgCode(String parentOrgCode) {
		this.parentOrgCode = parentOrgCode;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getIsMarketable() {
		return isMarketable;
	}

	public void setIsMarketable(String isMarketable) {
		this.isMarketable = isMarketable;
	}

	public String getSecPrice() {
		return secPrice;
	}

	public void setSecPrice(String secPrice) {
		this.secPrice = secPrice;
	}

	public String getVipSecPrice() {
		return vipSecPrice;
	}

	public void setVipSecPrice(String vipSecPrice) {
		this.vipSecPrice = vipSecPrice;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	public Integer getSecStore() {
		return secStore;
	}

	public void setSecStore(Integer secStore) {
		this.secStore = secStore;
	}

	public Integer getBuyLimit() {
		return buyLimit;
	}

	public void setBuyLimit(Integer buyLimit) {
		this.buyLimit = buyLimit;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getAuditState() {
		return auditState;
	}

	public void setAuditState(String auditState) {
		this.auditState = auditState;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
