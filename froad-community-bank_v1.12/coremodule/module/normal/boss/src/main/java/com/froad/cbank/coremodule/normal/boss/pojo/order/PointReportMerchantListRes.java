package com.froad.cbank.coremodule.normal.boss.pojo.order;

/**
 * 商户信息汇总
 * @author liaopeixin
 *	@date 2016年1月22日 上午10:01:26
 */
public class PointReportMerchantListRes{

	/**
	 * 所属商户
	 */
	private String merchantName;		 	
	/**
	 * 联盟积分总计
	 */
	private double froadPointCount;	
	/**
	 * 银行积分总计
	 */
	private double bankPointCount;	
	/**
	 * 银行积分比例
	 */
	private String bankPointRate;	
	/**
	 * 订单数量
	 */
	private Integer orderCount;
	/**
	 * 所属客户端
	 */
	private String clientName;
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public double getFroadPointCount() {
		return froadPointCount;
	}
	public void setFroadPointCount(double froadPointCount) {
		this.froadPointCount = froadPointCount;
	}
	public double getBankPointCount() {
		return bankPointCount;
	}
	public void setBankPointCount(double bankPointCount) {
		this.bankPointCount = bankPointCount;
	}
	public String getBankPointRate() {
		return bankPointRate;
	}
	public void setBankPointRate(String bankPointRate) {
		this.bankPointRate = bankPointRate;
	}
	public Integer getOrderCount() {
		return orderCount;
	}
	public void setOrderCount(Integer orderCount) {
		this.orderCount = orderCount;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}	
	
}
