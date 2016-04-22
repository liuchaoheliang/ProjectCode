package com.froad.cbank.coremodule.module.normal.merchant.pojo;

/**
 * @author Administrator
 *
 */
public class QueryProductListReq extends BasePojo {
	private String type;// "1":团购,"2":预售,"3":名优特惠,"4":在线积分兑换,"5":网点礼品,"6":精品商城商品;
						// 多个以逗号隔开
	private String productName;
	private String orgCode;// 多个以逗号隔开
	private String merchantName;// 商户模糊查询
	private String isMarketable;// 0未上架,1已上架,2已下架，全部不传
	private String auditState;// 审核状态("0待审核",1审核通过,2审核不通过,3未提交,全部不传值)
	private String startTime;// 销售开始时间
	private String endTime;// 销售结束时间

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getIsMarketable() {
		return isMarketable;
	}

	public void setIsMarketable(String isMarketable) {
		this.isMarketable = isMarketable;
	}

	public String getAuditState() {
		return auditState;
	}

	public void setAuditState(String auditState) {
		this.auditState = auditState;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

}
