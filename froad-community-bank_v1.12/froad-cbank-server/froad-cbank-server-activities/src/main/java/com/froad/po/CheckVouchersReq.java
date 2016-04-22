/**
 * @Title: CheckVouchersReq.java
 * @Package com.froad.po
 * @Description: TODO
 * Copyright:2015 F-Road All Rights Reserved   
 * Company:f-road.com.cn
 * 
 * @creater froad-Joker 2015年11月27日
 * @version V1.0
 **/

package com.froad.po;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: CheckVouchersReq
 * @Description: TODO
 * @author froad-Joker 2015年11月27日
 * @modify froad-Joker 2015年11月27日
 */

public class CheckVouchersReq implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO
	 */

	private static final long serialVersionUID = 1L;

	private String reqId;

	private String clientId;

	private Long memberCode;

	private String vouchersId;

	private Double orderMoney;

	private List<String> sustainActiveIds;

	private List<ProductOfFindUse> productOfFindUseList;

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

	public String getVouchersId() {
		return vouchersId;
	}

	public void setVouchersId(String vouchersId) {
		this.vouchersId = vouchersId;
	}

	public Double getOrderMoney() {
		return orderMoney;
	}

	public void setOrderMoney(Double orderMoney) {
		this.orderMoney = orderMoney;
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

	public Boolean getIsFtoF() {
		return isFtoF;
	}

	public void setIsFtoF(Boolean isFtoF) {
		this.isFtoF = isFtoF;
	}

}
