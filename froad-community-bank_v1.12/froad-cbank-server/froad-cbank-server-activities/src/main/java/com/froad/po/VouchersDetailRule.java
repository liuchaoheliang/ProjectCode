package com.froad.po;

import java.util.List;

/**
 * @ClassName: VouchersDetailRule
 * @Description: 代金券详细规则
 * @author froad-shenshaocheng 2015年11月26日
 * @modify froad-shenshaocheng 2015年11月26日
 */
public class VouchersDetailRule {
	/**
	 * 主键ID
	 */
	private Long id;
	/**
	 * 客户端ID
	 */
	private String clientId;
	/**
	 * 活动ID
	 */
	private String activeId;
	/**
	 * 代金券最小金额
	 */
	private Double minMoney;
	/**
	 * 代金券最大金额
	 */
	private Double maxMoney;
	/**
	 * 代金券总金额
	 */
	private Double totalMoney;
	/**
	 * 时间段限制单位0小时1天
	 */
	private Boolean isTotalDay;
	/**
	 * 时间段限制数量
	 */
	private Integer totalDay;
	/**
	 * 时间段限制次数
	 */
	private Integer totalCount;
	/**
	 * 订单最小限额-才能使用
	 */
	private Double orderMinMoney;
	/**
	 * 每人限购-时间段单位
	 */
	private Boolean isPerDay;
	/**
	 * 每人限购-时间段数量
	 */
	private Integer perDay;
	/**
	 * 每人限购-数量
	 */
	private Integer perCount;
	/**
	 * 是否可以和其它红包重复使用
	 */
	private Boolean isRepeat;
	/**
	 * 是否支持参与其它促销
	 */
	private Boolean isOtherActive;
	/**
	 * 0-⽀付前 1-⽀付后
	 */
	private Boolean isPrePay;
	/**
	 * 是否支持面对面
	 */
	private Boolean isFtof;
	
	/**
	 * 是否仅限新用户使用
	 */	
	private Boolean isOnlyNewUsers;
	/**
	 * ⽀付⽅式限制类型
	 */
	private String payMethod;

	/**
	 * 代金券ID集
	 */
	private List<VouchersCouponCode> vouchersCouponCodelist;

	/**
	 * 支持参与的促销活动列表
	 */
	private List<ActiveSustainRelation> activeSustainRelationList;	
	
	/**
	 * 红包券码总数（分批录入）
	 */	
	private Long vouchersTotal;
	
	/**
	 * 临时券码活动ID
	 */	
	private String temporaryActiveId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getActiveId() {
		return activeId;
	}

	public void setActiveId(String activeId) {
		this.activeId = activeId;
	}

	

	public Boolean getIsTotalDay() {
		return isTotalDay;
	}

	public void setIsTotalDay(Boolean isTotalDay) {
		this.isTotalDay = isTotalDay;
	}

	public Integer getTotalDay() {
		return totalDay;
	}

	public void setTotalDay(Integer totalDay) {
		this.totalDay = totalDay;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}



	public Double getMinMoney() {
		return minMoney;
	}

	public void setMinMoney(Double minMoney) {
		this.minMoney = minMoney;
	}

	public Double getMaxMoney() {
		return maxMoney;
	}

	public void setMaxMoney(Double maxMoney) {
		this.maxMoney = maxMoney;
	}

	public Double getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(Double totalMoney) {
		this.totalMoney = totalMoney;
	}

	public Double getOrderMinMoney() {
		return orderMinMoney;
	}

	public void setOrderMinMoney(Double orderMinMoney) {
		this.orderMinMoney = orderMinMoney;
	}

	public Boolean getIsPerDay() {
		return isPerDay;
	}

	public void setIsPerDay(Boolean isPerDay) {
		this.isPerDay = isPerDay;
	}

	public Integer getPerDay() {
		return perDay;
	}

	public void setPerDay(Integer perDay) {
		this.perDay = perDay;
	}

	public Integer getPerCount() {
		return perCount;
	}

	public void setPerCount(Integer perCount) {
		this.perCount = perCount;
	}

	public Boolean getIsRepeat() {
		return isRepeat;
	}

	public void setIsRepeat(Boolean isRepeat) {
		this.isRepeat = isRepeat;
	}

	public Boolean getIsOtherActive() {
		return isOtherActive;
	}

	public void setIsOtherActive(Boolean isOtherActive) {
		this.isOtherActive = isOtherActive;
	}

	public Boolean getIsPrePay() {
		return isPrePay;
	}

	public void setIsPrePay(Boolean isPrePay) {
		this.isPrePay = isPrePay;
	}

	public Boolean getIsFtof() {
		return isFtof;
	}

	public void setIsFtof(Boolean isFtof) {
		this.isFtof = isFtof;
	}

	public String getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}

	

	public List<VouchersCouponCode> getVouchersCouponCodelist() {
		return vouchersCouponCodelist;
	}

	public void setVouchersCouponCodelist(
			List<VouchersCouponCode> vouchersCouponCodelist) {
		this.vouchersCouponCodelist = vouchersCouponCodelist;
	}

	public List<ActiveSustainRelation> getActiveSustainRelationList() {
		return activeSustainRelationList;
	}

	public void setActiveSustainRelationList(
			List<ActiveSustainRelation> activeSustainRelationList) {
		this.activeSustainRelationList = activeSustainRelationList;
	}

	public Boolean getIsOnlyNewUsers() {
		return isOnlyNewUsers;
	}

	public void setIsOnlyNewUsers(Boolean isOnlyNewUsers) {
		this.isOnlyNewUsers = isOnlyNewUsers;
	}

	public Long getVouchersTotal() {
		return vouchersTotal;
	}

	public void setVouchersTotal(Long vouchersTotal) {
		this.vouchersTotal = vouchersTotal;
	}

	public String getTemporaryActiveId() {
		return temporaryActiveId;
	}

	public void setTemporaryActiveId(String temporaryActiveId) {
		this.temporaryActiveId = temporaryActiveId;
	}

}
