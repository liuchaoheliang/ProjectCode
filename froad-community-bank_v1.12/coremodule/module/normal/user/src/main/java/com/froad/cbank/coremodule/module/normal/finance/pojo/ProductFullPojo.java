package com.froad.cbank.coremodule.module.normal.finance.pojo;

/**   
 * 理财产品完整属性信息
 * @author liuhuangle@f-road.com.cn   
 * com.froad.cbank.coremodule.module.normal.finance.pojo
 * @date 2015-6-15 上午10:15:09
 */
public class ProductFullPojo extends ProductPojo{
    /**产品发售行机构名称*/
	private String provideOrgName;	
	/**收益类型：1：保本浮动收益   2：非保本浮动收益   3：保本固定收益*/
	private String incomeType;
	/**第一档收益率对应起点金额*/
	private double minRateMoney;
	/**第二档收益率对应起点金额*/
	private double maxRateMoney;
	/**银行产品编号*/
	private String bankProductId;
	/**产品成立日期*/
	private long deadlineStartTime;
	/**产品到期日期*/
	private long deadlineEndTime;
	/**单笔购买最高金额*/
	private double buyMaxAmount;
	/**购买递增金额*/
	private double buyAddAmount;
	/**用户购买限额*/
	private double userBuyLimit;
	/**风险等级:1保守型 2稳健型 3平衡型 4积极型 5激进型*/
	private String riskLevel;
	/**风险描述*/
	private String riskDesc;
	/**客户权益须知*/
	private String bookKhqyxzUrl;
	/**风险揭示书*/
	private String bookFxjssUrl;
	/**理财产品说明书*/
	private String bookLccpsmsUrl; 
	/**理财产品协议书*/
	private String bookLccpxysUrl;	
	/**理财电子账户开立协议书*/
	private String bookLcdzzhklxysUrl;
	
	public String getProvideOrgName() {
		return provideOrgName;
	}
	public void setProvideOrgName(String provideOrgName) {
		this.provideOrgName = provideOrgName;
	}
	public String getIncomeType() {
		return incomeType;
	}
	public void setIncomeType(String incomeType) {
		this.incomeType = incomeType;
	}
	public double getMinRateMoney() {
		return minRateMoney;
	}
	public void setMinRateMoney(double minRateMoney) {
		this.minRateMoney = minRateMoney;
	}
	public double getMaxRateMoney() {
		return maxRateMoney;
	}
	public void setMaxRateMoney(double maxRateMoney) {
		this.maxRateMoney = maxRateMoney;
	}
	public String getBankProductId() {
		return bankProductId;
	}
	public void setBankProductId(String bankProductId) {
		this.bankProductId = bankProductId;
	}
	public long getDeadlineStartTime() {
		return deadlineStartTime;
	}
	public void setDeadlineStartTime(long deadlineStartTime) {
		this.deadlineStartTime = deadlineStartTime;
	}
	public long getDeadlineEndTime() {
		return deadlineEndTime;
	}
	public void setDeadlineEndTime(long deadlineEndTime) {
		this.deadlineEndTime = deadlineEndTime;
	}
	public double getBuyMaxAmount() {
		return buyMaxAmount;
	}
	public void setBuyMaxAmount(double buyMaxAmount) {
		this.buyMaxAmount = buyMaxAmount;
	}
	public double getBuyAddAmount() {
		return buyAddAmount;
	}
	public void setBuyAddAmount(double buyAddAmount) {
		this.buyAddAmount = buyAddAmount;
	}
	public double getUserBuyLimit() {
		return userBuyLimit;
	}
	public void setUserBuyLimit(double userBuyLimit) {
		this.userBuyLimit = userBuyLimit;
	}
	public String getRiskLevel() {
		return riskLevel;
	}
	public void setRiskLevel(String riskLevel) {
		this.riskLevel = riskLevel;
	}
	public String getRiskDesc() {
		return riskDesc;
	}
	public void setRiskDesc(String riskDesc) {
		this.riskDesc = riskDesc;
	}
	public String getBookKhqyxzUrl() {
		return bookKhqyxzUrl;
	}
	public void setBookKhqyxzUrl(String bookKhqyxzUrl) {
		this.bookKhqyxzUrl = bookKhqyxzUrl;
	}
	public String getBookFxjssUrl() {
		return bookFxjssUrl;
	}
	public void setBookFxjssUrl(String bookFxjssUrl) {
		this.bookFxjssUrl = bookFxjssUrl;
	}
	public String getBookLccpsmsUrl() {
		return bookLccpsmsUrl;
	}
	public void setBookLccpsmsUrl(String bookLccpsmsUrl) {
		this.bookLccpsmsUrl = bookLccpsmsUrl;
	}
	public String getBookLccpxysUrl() {
		return bookLccpxysUrl;
	}
	public void setBookLccpxysUrl(String bookLccpxysUrl) {
		this.bookLccpxysUrl = bookLccpxysUrl;
	}
	public String getBookLcdzzhklxysUrl() {
		return bookLcdzzhklxysUrl;
	}
	public void setBookLcdzzhklxysUrl(String bookLcdzzhklxysUrl) {
		this.bookLcdzzhklxysUrl = bookLcdzzhklxysUrl;
	}
 
}
