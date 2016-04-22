package com.froad.cbank.coremodule.normal.boss.pojo.activities;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;
import com.froad.cbank.coremodule.framework.common.valid.Regular;

/**
 * 注册促销规则修改请求对象
 * @author luwanquan@f-road.com.cn
 * @date 2015年12月3日 下午2:11:37
 */
public class RegisteredRuleUpdReq {
	@NotEmpty("活动ID为空")
	private String activeId;
	@NotEmpty("活动名称为空")
	@Regular(reg = "^[\\s\\S]{1,50}$", value = "活动名称限50字符内")
	private String activeName;//活动名称
	@NotEmpty("客户端ID为空")
	private String clientId;//客户端ID
	@NotEmpty("活动开始时间为空")
	private String startTime;//活动开始时间
	@NotEmpty("活动结束时间为空")
	private String endTime;//活动结束时间
	@NotEmpty("奖励方式为空")
	private String awardType;//奖励方式（1-满减、2-红包、3-实物、4-联盟积分、5-银行积分）
	private String vouchersActiveId;//送红包的代金券规则ID
	private Boolean isAwardCre;//是否奖励推荐人（false-否-不奖励、true-是-奖励）
	private Boolean triggerType;//触发方式（false–注册、true–首单交易）
	@NotEmpty("银行补贴为空")
	@Regular(reg = "^(\\d{1,2}(\\.\\d{1,2}0{0,})?)|(100(\\.0{1,})?)$", value = "银行补贴数值有误，请传入大于等于0并且小于等于100的数值，仅可保留2位小数")
	private String bankRate;//银行补贴比例，实际值乘以1000入库
	@NotEmpty("方付通补贴为空")
	@Regular(reg = "^(\\d{1,2}(\\.\\d{1,2}0{0,})?)|(100(\\.0{1,})?)$", value = "方付通补贴数值有误，请传入大于等于0并且小于等于100的数值，仅可保留2位小数")
	private String fftRate;//方付通贴比例，实际值乘以1000入库
	@NotEmpty("活动描述为空")
	@Regular(reg = "^[\\s\\S]{1,300}$", value = "活动描述限300字符内")
	private String description;//活动描述
	private Integer perBankIntegral;//银行积分
	private Integer totalBankIntegral;//银行积分总额
	private Integer perUnionIntegral;//联盟积分
	private Integer totalUnionIntegral;//联盟积分总额
	private String limitType;//活动范围0-不限制 1-限制商户 2-限制门店 3-限制商品
	private String itemType;//标签类型0商户/1门店/2商品
	private String itemId;//商户/门店/商品标签ID
	private Long limitMoney;//满减的金额下限 award_type=1时有效
	private Long cutMoney;//满减的额度 award_type=1时有效 
	private Long totalMoney;//满减活动总额  award_type=1时有效
	/**
	 * 每人时间间隔单位为false时或者true日
	 */
	private Boolean isTotalDay;
	/**
	 * 全局限定的日间隔
	 */
	private Integer totalDay;
	/**
	 * 全局限定的次数
	 */
	private Integer totalCount;
	public String getActiveName() {
		return activeName;
	}
	public String getActiveId() {
		return activeId;
	}
	public void setActiveId(String activeId) {
		this.activeId = activeId;
	}
	public void setActiveName(String activeName) {
		this.activeName = activeName;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getAwardType() {
		return awardType;
	}
	public void setAwardType(String awardType) {
		this.awardType = awardType;
	}
	public String getVouchersActiveId() {
		return vouchersActiveId;
	}
	public void setVouchersActiveId(String vouchersActiveId) {
		this.vouchersActiveId = vouchersActiveId;
	}
	public Boolean getIsAwardCre() {
		return isAwardCre;
	}
	public void setIsAwardCre(Boolean isAwardCre) {
		this.isAwardCre = isAwardCre;
	}
	public Boolean getTriggerType() {
		return triggerType;
	}
	public void setTriggerType(Boolean triggerType) {
		this.triggerType = triggerType;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	public String getLimitType() {
		return limitType;
	}
	public void setLimitType(String limitType) {
		this.limitType = limitType;
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
	public Long getTotalMoney() {
		return totalMoney;
	}
	public void setTotalMoney(Long totalMoney) {
		this.totalMoney = totalMoney;
	}
	
}
