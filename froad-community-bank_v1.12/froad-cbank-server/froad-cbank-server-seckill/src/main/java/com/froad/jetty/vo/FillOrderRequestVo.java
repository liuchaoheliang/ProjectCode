package com.froad.jetty.vo;

import java.io.Serializable;

/**
 * 填充订单请求
 * @author wangzhangxu
 * @date 2015年5月3日 下午6:20:13
 * @version v1.0
 */
public class FillOrderRequestVo implements Serializable {
	
	private static final long serialVersionUID = 3864045027957022198L;
	
	private String clientId;
	private Long memberCode;
	private String token;
	/** 订单编号 */
	private String orderId;
	/** 提货类型 */
	private String deliveryType;
	/** 收货信息编号 */
	private String recvId;
	/** 接手券和短信的手机号 */
	private String phone;
	/** 预售商品自提网点|线下积分兑换网点 */
	private String orgCode;
	/** 预售商品自提网点名称|线下积分兑换网点名称 */
	private String orgName;
	
	public FillOrderRequestVo(){}

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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	public String getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}

	public String getRecvId() {
		return recvId;
	}

	public void setRecvId(String recvId) {
		this.recvId = recvId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
	
}
