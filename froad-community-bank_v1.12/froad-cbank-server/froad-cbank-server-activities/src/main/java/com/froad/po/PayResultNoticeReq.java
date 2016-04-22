/**
 * @Title: PayResultNoticeReq.java
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
 * @ClassName: PayResultNoticeReq
 * @Description: TODO
 * @author froad-Joker 2015年11月27日
 * @modify froad-Joker 2015年11月27日
 */

public class PayResultNoticeReq implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO
	 */

	private static final long serialVersionUID = 1L;

	private String reqId;

	private String clientId;

	private Long memberCode;

	private String orderId;

	private List<String> vouchersIds;

	private Boolean isSuccess;

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

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public List<String> getVouchersIds() {
		return vouchersIds;
	}

	public void setVouchersIds(List<String> vouchersIds) {
		this.vouchersIds = vouchersIds;
	}

	public Boolean getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(Boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

}
