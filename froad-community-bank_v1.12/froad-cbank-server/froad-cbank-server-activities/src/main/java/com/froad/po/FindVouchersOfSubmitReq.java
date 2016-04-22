package com.froad.po;

import java.io.Serializable;
import java.util.List;

import com.froad.db.mysql.bean.Page;

public class FindVouchersOfSubmitReq implements Serializable {
	/**
	 * @Fields serialVersionUID : TODO
	 */

	private static final long serialVersionUID = 1L;

	private String reqId;
	private String clientId;
	private Long memberCode;
	private Boolean isAvailable;
	private List<String> sustainActiveIds;
	private List<ProductOfFindUse> productOfFindUseList;
	private Page page;
	private Double orderMoney;
	private Boolean isFtoF;

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

	public Long getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}

	public Boolean getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(Boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public List<String> getSustainActiveIds() {
		return sustainActiveIds;
	}

	public void setSustainActiveIds(List<String> sustainActiveIds) {
		this.sustainActiveIds = sustainActiveIds;
	}

	public List<ProductOfFindUse> getProductOfFindUseList() {
		return productOfFindUseList;
	}

	public void setProductOfFindUseList(
			List<ProductOfFindUse> productOfFindUseList) {
		this.productOfFindUseList = productOfFindUseList;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public Double getOrderMoney() {
		return orderMoney;
	}

	public void setOrderMoney(Double orderMoney) {
		this.orderMoney = orderMoney;
	}

	public Boolean getIsFtoF() {
		return isFtoF;
	}

	public void setIsFtoF(Boolean isFtoF) {
		this.isFtoF = isFtoF;
	}

}
