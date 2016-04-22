package com.froad.cbank.coremodule.normal.boss.pojo.order;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;

/**
 * 订单详情请求对象
 * @author luwanquan@f-road.com.cn
 * @date 2015年9月2日 下午1:47:26
 */
public class OrderDetailReq {
	@NotEmpty("订单ID为空")
	private String orderId;//订单ID
	private String clientId;//客户端ID
	private Integer isQrcode;//1-面对面、0-非面对面
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public Integer getIsQrcode() {
		return isQrcode;
	}
	public void setIsQrcode(Integer isQrcode) {
		this.isQrcode = isQrcode == null ? 0 : isQrcode;
	}
}
