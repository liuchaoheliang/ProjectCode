package com.froad.cbank.coremodule.normal.boss.pojo.activities;

/**
 * 注册促销详情响应类
 * @author luwanquan@f-road.com.cn
 * @date 2015年12月3日 上午11:30:16
 */
public class RegisteredRuleDetailRes {
	private String clientId;//客户端ID
	private String clientName;//客户端名称
	private String activeId;//活动ID
	private Boolean triggerType;//触发方式（false–注册、true–首单交易）
	private String awardType;//奖励方式（1-满减、2-红包、3-实物、4-联盟积分、5-银行积分）
	private Long limitMoney;//满减的金额下限
	private Long cutMoney;//满减的额度
	private Long totalMoney;//满减活动总额 
	private String vouchersActiveId;//送红包的代金券规则ID
	private String vouchersActiveName;//送红包的代金券规则名称
	private String productId;//送实物的商品ID
	private Integer productCount;//送实物的商品数量
	private Integer awardCount;//活动奖励人数
	private Boolean isTotalDay;//时间段限制的时间单位（天/日）
	private Integer totalDay;//时间段限制的时间数量
	private Integer totalCount;//时间段限制的次数
	private Boolean isAwardCre;//是否奖励推荐人（false-否-不奖励、true-是-奖励）
	private Boolean creAwardType;//推荐人奖励方式（false-红包、true-实物）
	private String creVouchersActiveId;//推荐人奖励红包的代金券规则ID
	private String creProductId;//推荐人奖励实物的商品ID
	private Boolean isLimitCreCount;//是否限制奖励推荐人次数（false-否-不限制、true-是-限制）
	private Integer creAwardCount;//推荐人奖励次数
	private Integer perBankIntegral;//银行积分
	private Integer totalBankIntegral;//银行积分总额
	private Integer perUnionIntegral;//联盟积分
	private Integer totalUnionIntegral;//联盟积分总额
	
	private String type;//活动类型（1-满减活动、2-满送、3-代金券、4-打折促销、5-首单、6-注册送）
	private String status;//活动状态（0-待提交、1-审核中、2-审核不通过、3-启用、4-禁用）
	private String activeName;//活动名称
	private String activeLogo;//活动Logo
	private Long expireStartTime;//有效期开始时间
	private Long expireEndTime;//有效期结束时间
	private String limitType;//限制类型（0–不限制、1–限制商户、2–限制门店、3–限制商品、4-未定义）
	private Integer merchantRate;//商户补贴比例，实际值乘以1000入库
	private String bankRate;//银行补贴比例，实际值乘以1000入库
	private String fftRate;//方付通贴比例，实际值乘以1000入库
	private String settleType;//结算方式（0-实时结算、1-延期结算）
	private String description;//活动描述
	
	private String itemType;//活动类型（0-不限定、1–商户、2–门店、3–商品、4-未定义）
	private String itemId;//商户/门店/商品标签ID
	
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getActiveId() {
		return activeId;
	}
	public void setActiveId(String activeId) {
		this.activeId = activeId;
	}
	public Boolean getTriggerType() {
		return triggerType;
	}
	public void setTriggerType(Boolean triggerType) {
		this.triggerType = triggerType;
	}
	public String getAwardType() {
		return awardType;
	}
	public void setAwardType(String awardType) {
		this.awardType = awardType;
	}
	public Long getLimitMoney() {
		return limitMoney;
	}
	public void setLimitMoney(Long limitMoney) {
		this.limitMoney = limitMoney;
	}
	public Long getCutMoney() {
		return cutMoney;
	}
	public void setCutMoney(Long cutMoney) {
		this.cutMoney = cutMoney;
	}
	public String getVouchersActiveId() {
		return vouchersActiveId;
	}
	public void setVouchersActiveId(String vouchersActiveId) {
		this.vouchersActiveId = vouchersActiveId;
	}
	public String getVouchersActiveName() {
		return vouchersActiveName;
	}
	public void setVouchersActiveName(String vouchersActiveName) {
		this.vouchersActiveName = vouchersActiveName;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public Integer getProductCount() {
		return productCount;
	}
	public void setProductCount(Integer productCount) {
		this.productCount = productCount;
	}
	public Integer getAwardCount() {
		return awardCount;
	}
	public void setAwardCount(Integer awardCount) {
		this.awardCount = awardCount;
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
	public Boolean getIsAwardCre() {
		return isAwardCre;
	}
	public void setIsAwardCre(Boolean isAwardCre) {
		this.isAwardCre = isAwardCre;
	}
	public Boolean getCreAwardType() {
		return creAwardType;
	}
	public void setCreAwardType(Boolean creAwardType) {
		this.creAwardType = creAwardType;
	}
	public String getCreVouchersActiveId() {
		return creVouchersActiveId;
	}
	public void setCreVouchersActiveId(String creVouchersActiveId) {
		this.creVouchersActiveId = creVouchersActiveId;
	}
	public String getCreProductId() {
		return creProductId;
	}
	public void setCreProductId(String creProductId) {
		this.creProductId = creProductId;
	}
	public Boolean getIsLimitCreCount() {
		return isLimitCreCount;
	}
	public void setIsLimitCreCount(Boolean isLimitCreCount) {
		this.isLimitCreCount = isLimitCreCount;
	}
	public Integer getCreAwardCount() {
		return creAwardCount;
	}
	public void setCreAwardCount(Integer creAwardCount) {
		this.creAwardCount = creAwardCount;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getActiveName() {
		return activeName;
	}
	public void setActiveName(String activeName) {
		this.activeName = activeName;
	}
	public String getActiveLogo() {
		return activeLogo;
	}
	public void setActiveLogo(String activeLogo) {
		this.activeLogo = activeLogo;
	}
	public Long getExpireStartTime() {
		return expireStartTime;
	}
	public void setExpireStartTime(Long expireStartTime) {
		this.expireStartTime = expireStartTime;
	}
	public Long getExpireEndTime() {
		return expireEndTime;
	}
	public void setExpireEndTime(Long expireEndTime) {
		this.expireEndTime = expireEndTime;
	}
	public String getLimitType() {
		return limitType;
	}
	public void setLimitType(String limitType) {
		this.limitType = limitType;
	}
	public Integer getMerchantRate() {
		return merchantRate;
	}
	public void setMerchantRate(Integer merchantRate) {
		this.merchantRate = merchantRate;
	}
	public String getSettleType() {
		return settleType;
	}
	public void setSettleType(String settleType) {
		this.settleType = settleType;
	}
	public String getDescription() {
		return description;
	}
	public String getBankRate() {
		return bankRate;
	}
	public void setBankRate(String bankRate) {
		this.bankRate = bankRate;
	}
	public String getFftRate() {
		return fftRate;
	}
	public void setFftRate(String fftRate) {
		this.fftRate = fftRate;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getItemType() {
		return itemType;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public Integer getPerBankIntegral() {
		return perBankIntegral;
	}
	public void setPerBankIntegral(Integer perBankIntegral) {
		this.perBankIntegral = perBankIntegral;
	}
	public Integer getTotalBankIntegral() {
		return totalBankIntegral;
	}
	public void setTotalBankIntegral(Integer totalBankIntegral) {
		this.totalBankIntegral = totalBankIntegral;
	}
	public Integer getPerUnionIntegral() {
		return perUnionIntegral;
	}
	public void setPerUnionIntegral(Integer perUnionIntegral) {
		this.perUnionIntegral = perUnionIntegral;
	}
	public Integer getTotalUnionIntegral() {
		return totalUnionIntegral;
	}
	public void setTotalUnionIntegral(Integer totalUnionIntegral) {
		this.totalUnionIntegral = totalUnionIntegral;
	}
	public Long getTotalMoney() {
		return totalMoney;
	}
	public void setTotalMoney(Long totalMoney) {
		this.totalMoney = totalMoney;
	}
}
