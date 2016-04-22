package com.froad.cbank.coremodule.normal.boss.pojo.order;

import java.util.ArrayList;
import java.util.HashMap;
/**
 * 物流信息
 * @author yfy
 *
 */
public class DeliveryBillVo {

	/**
	 * 子订单编号
	 */
	private String subOrderId;
	/**
	 * 物流公司
	 */
	private String shippingCorp;
	/**
	 * 物流公司编码
	 */
	private String shippingCorpCode;
	/**
	 * 运单编号
	 */
	private String shippingId;
	/**
	 * 运输时间
	 */
	private String shippingTime;
	/**
	 * 物流状态
	 */
	private String status;
	/**
	 * 物流消息
	 */
	private ArrayList<HashMap<String, Object>> msgList;
	
	public String getSubOrderId() {
		return subOrderId;
	}
	public void setSubOrderId(String subOrderId) {
		this.subOrderId = subOrderId;
	}
	public String getShippingCorp() {
		return shippingCorp;
	}
	public void setShippingCorp(String shippingCorp) {
		this.shippingCorp = shippingCorp;
	}
	public String getShippingCorpCode() {
		return shippingCorpCode;
	}
	public void setShippingCorpCode(String shippingCorpCode) {
		this.shippingCorpCode = shippingCorpCode;
	}
	public String getShippingId() {
		return shippingId;
	}
	public void setShippingId(String shippingId) {
		this.shippingId = shippingId;
	}
	public String getShippingTime() {
		return shippingTime;
	}
	public void setShippingTime(String shippingTime) {
		this.shippingTime = shippingTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public ArrayList<HashMap<String, Object>> getMsgList() {
		return msgList;
	}
	public void setMsgList(ArrayList<HashMap<String, Object>> msgList) {
		this.msgList = msgList;
	}
	
}
