package com.froad.cbank.coremodule.module.normal.user.pojo;

import java.util.List;

/**
 * 物流信息
 * @author yfy
 *
 */
public class DeliveryBillVo {

	/**
	 * id
	 */
	private String id;
	/**
	 * 创建时间
	 */
	private Long createTime;
	/**
	 * 更新时间
	 */
	private Long updateTime;
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
	private List<DeliveryMessageVo> msgList;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public Long getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}
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
	public List<DeliveryMessageVo> getMsgList() {
		return msgList;
	}
	public void setMsgList(List<DeliveryMessageVo> msgList) {
		this.msgList = msgList;
	}
	
}
