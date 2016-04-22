package com.froad.cbank.coremodule.normal.boss.pojo.refund;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;
import com.froad.cbank.coremodule.normal.boss.pojo.Page;


public class RefundDetailReq  extends Page {
	private String clientId;//客户端ID
	@NotEmpty("退款号为空")
	private String refundId;//退款号
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getRefundId() {
		return refundId;
	}
	public void setRefundId(String refundId) {
		this.refundId = refundId;
	}
}
