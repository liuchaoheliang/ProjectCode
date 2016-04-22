package com.froad.db.chonggou.entity;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

/**
 *  机构商户关系Mongo结构
  * @ClassName: OrgRelation
  * @Description: TODO
  * @author ll 2015年3月26日
 */
public class OrgRelation {
	
//	cb_org_relation{
//		 "_id":org_code,
//		 "client_id":,
//		 "merchant_id":,
//		 "outlet_id":,
//		 "sub_orgs":[
//		 {
//		 "org_code":,
//		 "orgName":,
//		 "merchant_id":,
//		 "outlet_id":
//		 },
//		 ]
//	}
	
	
	private String id;
	
	private String clientId;
	
	private String merchantId;//商户id
	
	private String outletId;//门店id
	
	private List<SubOrgsInfo> subOrgs;//下级机构list

	
	@JSONField(name="client_id")
	public String getClientId() {
		return clientId;
	}

	@JSONField(name="client_id")
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	@JSONField(name="_id")
	public String getId() {
		return id;
	}

	@JSONField(name="_id")
	public void setId(String id) {
		this.id = id;
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

	@JSONField(name="sub_orgs")
	public List<SubOrgsInfo> getSubOrgs() {
		return subOrgs;
	}

	@JSONField(name="sub_orgs")
	public void setSubOrgs(List<SubOrgsInfo> subOrgs) {
		this.subOrgs = subOrgs;
	}
	
	
	
}
