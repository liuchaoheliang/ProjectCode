package com.froad.cbank.coremodule.normal.boss.pojo.settle;

import java.io.Serializable;
import java.util.List;

/**
 * 类描述：结算返回相关实体类
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2015 
 * @author: f-road.com.cn
 * @time: 2015-5-19下午3:26:01 
 */
public class SettleInfoVo implements Serializable{
	
	/**
	 * UID
	 */
	private static final long serialVersionUID = 7170831055660122825L;
	private String id; // required
	private String settlementId; // required
	private long createTime; // required
	private String clientId; // required
	private String orderId; // required
	private String subOrderId; // required
	private String merchantId; // required
	private String merchantName; // required
	private String outletId; // required
	private String outletName; // required
	private int type; // required
	private String paymentId; // required
	private String settleState; // required
	private double money; // required
	private String remark; // required
	private String productName; // required
	private String productId; // required
	private int productCount; // required
	private String billNo;//返回的账单编号 required
	private List<String> tickets; // required
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSettlementId() {
		return settlementId;
	}
	public void setSettlementId(String settlementId) {
		this.settlementId = settlementId;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getSubOrderId() {
		return subOrderId;
	}
	public void setSubOrderId(String subOrderId) {
		this.subOrderId = subOrderId;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getOutletId() {
		return outletId;
	}
	public void setOutletId(String outletId) {
		this.outletId = outletId;
	}
	public String getOutletName() {
		return outletName;
	}
	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	public String getSettleState() {
		return settleState;
	}
	public void setSettleState(String settleState) {
		this.settleState = settleState;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public int getProductCount() {
		return productCount;
	}
	public void setProductCount(int productCount) {
		this.productCount = productCount;
	}
	public List<String> getTickets() {
		return tickets;
	}
	public void setTickets(List<String> tickets) {
		this.tickets = tickets;
	}
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	
}
