/**
 * @Title: CheckVouchersRes.java
 * @Package com.froad.po
 * @Description: TODO
 * Copyright:2015 F-Road All Rights Reserved   
 * Company:f-road.com.cn
 * 
 * @creater froad-Joker 2015年11月27日
 * @version V1.0
 **/

package com.froad.po;

/**
 * @ClassName: CheckVouchersRes
 * @Description: TODO
 * @author froad-Joker 2015年11月27日
 * @modify froad-Joker 2015年11月27日
 */

public class CheckVouchersRes {
	public String reqId;

	public String clientId;

	public Long memberCode;

	public Result result;

	public Double vouchersMonry;

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

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public Double getVouchersMonry() {
		return vouchersMonry;
	}

	public void setVouchersMonry(Double vouchersMonry) {
		this.vouchersMonry = vouchersMonry;
	}

}
