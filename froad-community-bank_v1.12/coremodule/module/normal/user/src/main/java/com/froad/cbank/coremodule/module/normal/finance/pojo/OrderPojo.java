package com.froad.cbank.coremodule.module.normal.finance.pojo;

/**
 * 
 * <p>标题: —— 我的交易记录基本信息</p>
 * <p>说明: —— 简要描述职责、使用注意事项等</p>
 * <p>创建时间：2015-6-15下午04:26:17</p>
 * <p>作者: 吴菲</p>
 */
public class OrderPojo {

	/** 订单ID */
	private String orderId;
	/** 理财产品名称   */
	private String productName;
	/** 创建时间   */
	private long createTime;
	/** 购买金额   */
	private String buyAmount;
	/** 订单状态   0-未购买 1-购买中 2-购买失败 3-购买成功 4-撤销中 5-撤销失败 6-撤销成功 4-赎回中 5-赎回失败 6-赎回成功   */
	private String orderStatus;
	/** 产品状态   1-未成立 2-成立 3-不成立 4-已赎回  */
	private String productStatus;
	/** 是否可取消  */
	private boolean isEnableCancel;
	/** 是否可支付  */
	private boolean isEnablePay;
	/** 是否可撤销  */
	private boolean isEnableRescind;
	
	
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public String getBuyAmount() {
		return buyAmount;
	}
	public void setBuyAmount(String buyAmount) {
		this.buyAmount = buyAmount;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getProductStatus() {
		return productStatus;
	}
	public void setProductStatus(String productStatus) {
		this.productStatus = productStatus;
	}
	public boolean isEnableCancel() {
		return isEnableCancel;
	}
	public void setEnableCancel(boolean isEnableCancel) {
		this.isEnableCancel = isEnableCancel;
	}
	public boolean isEnablePay() {
		return isEnablePay;
	}
	public void setEnablePay(boolean isEnablePay) {
		this.isEnablePay = isEnablePay;
	}
	public boolean isEnableRescind() {
		return isEnableRescind;
	}
	public void setEnableRescind(boolean isEnableRescind) {
		this.isEnableRescind = isEnableRescind;
	}
	
	
}
