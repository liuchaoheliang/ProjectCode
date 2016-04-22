package com.froad.po;

import java.util.Date;


/**
 * 
 * @author liuyanyun
 *
 */
public class Waybill implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	
	// Fields
	/**主键ID*/
	private Long id;
	
	/**创建时间*/
	private Date createTime;
	
	/**更新时间*/
	private Date updateTime;
	
	/**子订单号*/
	private String subOrderId;
	
	/**物流公司编号*/
	private String shippingCorpCode;
	
	/**物流公司*/
	private String shippingCorp;
	
	/**物流单号*/
	private String shippingId;
	
	/**状态*/
	private String status;
	
	/**物流信息*/
	private String message;
	
	/**发货时间*/
	private Date shippingTime;
	
	/**确认收货时间*/
	private Date arriveTime;
	
	/**确认收货状态  0-未收货  1-人工确认 2-系统确认*/
	private Integer shippingState;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getSubOrderId() {
		return subOrderId;
	}

	public void setSubOrderId(String subOrderId) {
		this.subOrderId = subOrderId;
	}

	public String getShippingCorpCode() {
		return shippingCorpCode;
	}

	public void setShippingCorpCode(String shippingCorpCode) {
		this.shippingCorpCode = shippingCorpCode;
	}

	public String getShippingCorp() {
		return shippingCorp;
	}

	public void setShippingCorp(String shippingCorp) {
		this.shippingCorp = shippingCorp;
	}

	public String getShippingId() {
		return shippingId;
	}

	public void setShippingId(String shippingId) {
		this.shippingId = shippingId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getShippingTime() {
		return shippingTime;
	}

	public void setShippingTime(Date shippingTime) {
		this.shippingTime = shippingTime;
	}

	
	public Date getArriveTime() {
		return arriveTime;
	}

	public void setArriveTime(Date arriveTime) {
		this.arriveTime = arriveTime;
	}

	public Integer getShippingState() {
		return shippingState;
	}

	public void setShippingState(Integer shippingState) {
		this.shippingState = shippingState;
	}

	
}
