package com.froad.cbank.coremodule.module.normal.user.pojo;

import java.util.List;

/**
 * 红包
 * 
 * @author artPing
 *
 */
public class CanUsePacketsReqPojo extends PagePojo {
	private String reqId;
	private String clientId;
	private long memberCode;
	private boolean isAvailable;
	private String[] sustainActiveIds;
	private List<ProductOfFindUseReqPojo> productOfFindUseList;

	public String getReqId() {
		return reqId;
	}

	public void setReqId(String reqId) {
		this.reqId = reqId;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public long getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(long memberCode) {
		this.memberCode = memberCode;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public String[] getSustainActiveIds() {
		return sustainActiveIds;
	}

	public void setSustainActiveIds(String[] sustainActiveIds) {
		this.sustainActiveIds = sustainActiveIds;
	}

	public List<ProductOfFindUseReqPojo> getProductOfFindUseList() {
		return productOfFindUseList;
	}

	public void setProductOfFindUseList(
			List<ProductOfFindUseReqPojo> productOfFindUseList) {
		this.productOfFindUseList = productOfFindUseList;
	}

}
