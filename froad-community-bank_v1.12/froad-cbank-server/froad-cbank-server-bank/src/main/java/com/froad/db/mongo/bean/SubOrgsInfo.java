package com.froad.db.mongo.bean;

import com.alibaba.fastjson.annotation.JSONField;

/**
 *  机构商户关系中的下级机构Mongo
  * @ClassName: OrgRelation
  * @Description: TODO
  * @author ll 2015年3月26日
 */
public class SubOrgsInfo {
	
	private String orgCode;	// 机构号
	
	private String orgName;//机构名称
	
	private String merchantId;	// 商户号
	
	private String outletId;	// 门店号

	@JSONField(name="org_code")
	public String getOrgCode() {
		return orgCode;
	}

	@JSONField(name="org_code")
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	@JSONField(name="org_name")
	public String getOrgName() {
		return orgName;
	}

	@JSONField(name="org_name")
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	@JSONField(name="merchant_id")
	public String getMerchantId() {
		return merchantId;
	}

	@JSONField(name="merchant_id")
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	@JSONField(name="outlet_id")
	public String getOutletId() {
		return outletId;
	}

	@JSONField(name="outlet_id")
	public void setOutletId(String outletId) {
		this.outletId = outletId;
	}

	
	
	
	
	
}
