package com.froad.cbank.coremodule.module.normal.bank.vo.report;

import com.froad.cbank.coremodule.module.normal.bank.vo.BaseVo;

public class ReportBankReqVo extends BaseVo{

	/**
	   * 查询纬度类型：amountLat 金额纬度;orderLat 订单纬度;merchantLat 商户纬度;productLat 商品纬度;memberLat 用户纬度;不传则所有*
	   */
	  private String latitudeTupe; // optional
	  /**
	   * 统计类型：day：按天;week：按周;month：按月;diy：自定义*
	   */
	  private String countType; // optional
	  /**
	   * 筛选开始日期*
	   */
	  private String startDate; // optional
	  /**
	   * 筛选结束日期*
	   */
	  private String endDate; // optional
	  /**
	   * 银行*
	   */
	  private String clientId; // optional
	  /**
	   * 业务平台*
	   */
	  private String platform; // optional
	  /**
	   * 机构号*
	   */
	  private String orgCode; // optional
	  /**
	   * 机构等级*
	   */
	  private int orgLevel; // optional
	  /**
	   * 是否选中商户维度*
	   */
	  private Boolean checkedMerchant=false; // optional
	  /**
	   * 是否选中商户类目纬度*
	   */
	  private Boolean checkedMerchantCategory=false; // optional
	  /**
	   * 业务类型*
	   */
	  private String orderType; // optional
	  /**
	   * 支付方式*
	   */
	  private String payType; // optional
	  /**
	   * 金额指标—金额是否选中*
	   */
	  private Boolean checkedAmount=false; // optional
	  /**
	   * 金额指标—累积金额否选中*
	   */
	  private Boolean checkedAmountCumulation=false; // optional
	  /**
	   * 金额指标—退款金额否选中*
	   */
	  private Boolean checkedAmountRefund=false; // optional
	  /**
	   * 金额指标—成交金额否选中*
	   */
	  private Boolean checkedAmountTurnover=false; // optional
	  /**
	   * 金额指标-累积成交金额否选中*
	   */
	  private Boolean checkedAmountCumulationTurnover=false; // optional
	  /**
	   * 订单指标-订单否选中*
	   */
	  private Boolean checkedOrderCount=false; // optional
	  /**
	   * 订单指标-累积订单否选中*
	   */
	  private Boolean checkedOrderCumulation=false; // optional
	  /**
	   * 订单指标-退款订单否选中*
	   */
	  private Boolean checkedOrderRefund=false; // optional
	  /**
	   * 订单指标-成交订单否选中*
	   */
	  private Boolean checkedOrderTurnover=false; // optional
	  /**
	   * 订单指标-累积成交订单否选中*
	   */
	  private Boolean checkedOrderCumulationTurnover=false; // optional
	  /**
	   * 商户指标--商户数 否选中*
	   */
	  private Boolean checkedMerchantSum=false; // optional
	  /**
	   * 商户指标--解约商户数 否选中*
	   */
	  private Boolean checkedMerchantCancelContract=false; // optional
	  /**
	   * 商户指标--累积商户数 否选中*
	   */
	  private Boolean checkedMerchantCumulation=false; // optional
	  /**
	   * 商户指标--门店数 否选中*
	   */
	  private Boolean checkedOutletCount=false; // optional
	  /**
	   * 商户指标--累积门店数 否选中*
	   */
	  private Boolean checkedOutletCumulation=false; // optional
	  /**
	   * 商品指标—商品数量 否选中*
	   */
	  private Boolean checkedProductSum=false; // optional
	  /**
	   * 商品指数—累积商品数量 否选中*
	   */
	  private Boolean checkedProductCumulation=false; // optional
	  /**
	   * 商品指数—下架商品数量 否选中*
	   */
	  private Boolean checkedProductDownSum=false; // optional
	  /**
	   * 商品只是—累积下架商品数量 否选中*
	   */
	  private Boolean checkedProductDownComulation=false; // optional
	  /**
	   * 用户指数-消费用户数 否选中*
	   */
	  private Boolean checkedMemberCount=false; // optional
	  /**
	   * 用户指数—累积消费用户数 否选中*
	   */
	  private Boolean checkedMemberComulationCount=false; // optional
	public String getLatitudeTupe() {
		return latitudeTupe;
	}
	public void setLatitudeTupe(String latitudeTupe) {
		this.latitudeTupe = latitudeTupe;
	}
	public String getCountType() {
		return countType;
	}
	public void setCountType(String countType) {
		this.countType = countType;
	}
	 
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public int getOrgLevel() {
		return orgLevel;
	}
	public void setOrgLevel(int orgLevel) {
		this.orgLevel = orgLevel;
	}
	public Boolean getCheckedMerchant() {
		return checkedMerchant;
	}
	public void setCheckedMerchant(Boolean checkedMerchant) {
		this.checkedMerchant = checkedMerchant;
	}
	public Boolean getCheckedMerchantCategory() {
		return checkedMerchantCategory;
	}
	public void setCheckedMerchantCategory(Boolean checkedMerchantCategory) {
		this.checkedMerchantCategory = checkedMerchantCategory;
	}
	 
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public Boolean getCheckedAmount() {
		return checkedAmount;
	}
	public void setCheckedAmount(Boolean checkedAmount) {
		this.checkedAmount = checkedAmount;
	}
	public Boolean getCheckedAmountCumulation() {
		return checkedAmountCumulation;
	}
	public void setCheckedAmountCumulation(Boolean checkedAmountCumulation) {
		this.checkedAmountCumulation = checkedAmountCumulation;
	}
	public Boolean getCheckedAmountRefund() {
		return checkedAmountRefund;
	}
	public void setCheckedAmountRefund(Boolean checkedAmountRefund) {
		this.checkedAmountRefund = checkedAmountRefund;
	}
	public Boolean getCheckedAmountTurnover() {
		return checkedAmountTurnover;
	}
	public void setCheckedAmountTurnover(Boolean checkedAmountTurnover) {
		this.checkedAmountTurnover = checkedAmountTurnover;
	}
	public Boolean getCheckedAmountCumulationTurnover() {
		return checkedAmountCumulationTurnover;
	}
	public void setCheckedAmountCumulationTurnover(Boolean checkedAmountCumulationTurnover) {
		this.checkedAmountCumulationTurnover = checkedAmountCumulationTurnover;
	}
	public Boolean getCheckedOrderCount() {
		return checkedOrderCount;
	}
	public void setCheckedOrderCount(Boolean checkedOrderCount) {
		this.checkedOrderCount = checkedOrderCount;
	}
	public Boolean getCheckedOrderCumulation() {
		return checkedOrderCumulation;
	}
	public void setCheckedOrderCumulation(Boolean checkedOrderCumulation) {
		this.checkedOrderCumulation = checkedOrderCumulation;
	}
	public Boolean getCheckedOrderRefund() {
		return checkedOrderRefund;
	}
	public void setCheckedOrderRefund(Boolean checkedOrderRefund) {
		this.checkedOrderRefund = checkedOrderRefund;
	}
	public Boolean getCheckedOrderTurnover() {
		return checkedOrderTurnover;
	}
	public void setCheckedOrderTurnover(Boolean checkedOrderTurnover) {
		this.checkedOrderTurnover = checkedOrderTurnover;
	}
	public Boolean getCheckedOrderCumulationTurnover() {
		return checkedOrderCumulationTurnover;
	}
	public void setCheckedOrderCumulationTurnover(Boolean checkedOrderCumulationTurnover) {
		this.checkedOrderCumulationTurnover = checkedOrderCumulationTurnover;
	}
	public Boolean getCheckedMerchantSum() {
		return checkedMerchantSum;
	}
	public void setCheckedMerchantSum(Boolean checkedMerchantSum) {
		this.checkedMerchantSum = checkedMerchantSum;
	}
	public Boolean getCheckedMerchantCancelContract() {
		return checkedMerchantCancelContract;
	}
	public void setCheckedMerchantCancelContract(Boolean checkedMerchantCancelContract) {
		this.checkedMerchantCancelContract = checkedMerchantCancelContract;
	}
	public Boolean getCheckedMerchantCumulation() {
		return checkedMerchantCumulation;
	}
	public void setCheckedMerchantCumulation(Boolean checkedMerchantCumulation) {
		this.checkedMerchantCumulation = checkedMerchantCumulation;
	}
	public Boolean getCheckedOutletCount() {
		return checkedOutletCount;
	}
	public void setCheckedOutletCount(Boolean checkedOutletCount) {
		this.checkedOutletCount = checkedOutletCount;
	}
	public Boolean getCheckedOutletCumulation() {
		return checkedOutletCumulation;
	}
	public void setCheckedOutletCumulation(Boolean checkedOutletCumulation) {
		this.checkedOutletCumulation = checkedOutletCumulation;
	}
	public Boolean getCheckedProductSum() {
		return checkedProductSum;
	}
	public void setCheckedProductSum(Boolean checkedProductSum) {
		this.checkedProductSum = checkedProductSum;
	}
	public Boolean getCheckedProductCumulation() {
		return checkedProductCumulation;
	}
	public void setCheckedProductCumulation(Boolean checkedProductCumulation) {
		this.checkedProductCumulation = checkedProductCumulation;
	}
	public Boolean getCheckedProductDownSum() {
		return checkedProductDownSum;
	}
	public void setCheckedProductDownSum(Boolean checkedProductDownSum) {
		this.checkedProductDownSum = checkedProductDownSum;
	}
	public Boolean getCheckedProductDownComulation() {
		return checkedProductDownComulation;
	}
	public void setCheckedProductDownComulation(Boolean checkedProductDownComulation) {
		this.checkedProductDownComulation = checkedProductDownComulation;
	}
	public Boolean getCheckedMemberCount() {
		return checkedMemberCount;
	}
	public void setCheckedMemberCount(Boolean checkedMemberCount) {
		this.checkedMemberCount = checkedMemberCount;
	}
	public Boolean getCheckedMemberComulationCount() {
		return checkedMemberComulationCount;
	}
	public void setCheckedMemberComulationCount(Boolean checkedMemberComulationCount) {
		this.checkedMemberComulationCount = checkedMemberComulationCount;
	} 
}
