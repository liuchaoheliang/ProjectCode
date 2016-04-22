package com.froad.cbank.coremodule.normal.boss.pojo.order;

/**
 * 
 * 类描述:boss线上积分兑换查询返回实体
 * @Copyright www.f-road.com.cn Corporation 2015 
 * @author "chenzhangwei"
 * @time 2015年8月20日 下午2:08:56
 * @email "chenzhangwei@f-road.com.cn"
 */
public class PointOrderRes {

	/**
	 * 订单编号
	 */
	private String orderId;
	/**
	 * 下单时间
	 */
	private String createTime;
	/**
	 * 总价
	 */
	private Double totalMoney;
	/**
	 * 订单状态
	 */
	private String orderStatus;
	/**
	 * 商品ID
	 */
	private String productId;
	/**
	 * 商品名称
	 */
	private String productName;
	/**
	 * 购买数量
	 */
	private Integer buyCount;
	/**
	 * 提货人
	 */
	private String reciver;
	/**
	 * 城市
	 */
	private String city;
	/**
	 * 地址
	 */
	private String address;
	/**
	 * 手机号
	 */
	private String phone;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Double getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(Double totalMoney) {
		this.totalMoney = totalMoney;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getBuyCount() {
		return buyCount;
	}

	public void setBuyCount(Integer buyCount) {
		this.buyCount = buyCount;
	}

	public String getReciver() {
		return reciver;
	}

	public void setReciver(String reciver) {
		this.reciver = reciver;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
