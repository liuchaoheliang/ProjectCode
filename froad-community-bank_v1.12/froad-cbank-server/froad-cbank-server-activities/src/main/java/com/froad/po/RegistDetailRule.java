package com.froad.po;

public class RegistDetailRule implements java.io.Serializable {

	// Fields

	/**
	 * @Fields serialVersionUID : TODO
	 */

	private static final long serialVersionUID = 1L;
	/**
	 * 序号
	 */	
	private Long id;
	/**
	 * 客户端
	 */	
	private String clientId;
	/**
	 * 注册活动ID
	 */	
	private String activeId;
	/**
	 * 触发方式0注册1收单交易
	 */	
	private Boolean triggerType;
	/**
	 * 奖励方式1满减2红包3实物4.联盟积分5.银行积分
	 */		
	private String awardType;
	/**
	 * 满减的金额下限 award_type=1时有效
	 */	
	private Long limitMoney;
	/**
	 * 满减的额度 award_type=1时有效
	 */	
	private Long cutMoney;
	
	/**
	 * 满减活动总额
	 */
	private Long totalMoney;
	
	/**
	 * 	送红包的代金券规则 idaward_type=2时有效
	 */	
	private String vouchersActiveId;
	/**
	 * 	送实物的商品 idaward_type=3时有效
	 */	
	private String productId;
	/**
	 * 送实物的商品数量 award_type=3时有效
	 */		
	private Integer productCount;
	/**
	 * 活动奖励人数 award_type=2/3时要检查红包个数/商品数量和奖励人数的约束
	 */			
	private Integer awardCount;
	/**
	 * 每次送银行积分 
	 */	
	private Integer perBankIntegral;
	/**
	 * 银行总积分 
	 */		
	private Integer totalBankIntegral;
	/**
	 * 每次送联盟积分 
	 */		
	private Integer perUnionIntegral;
	/**
	 * 联盟总积分 
	 */		
	private Integer totalUnionIntegral;
	/**
	 * 时间段限制的时间单位天或者日(1天,0是时) 
	 */	
	private Boolean isTotalDay;
	/**
	 * 时间段限制的时间数量 
	 */	
	private Integer totalDay;
	/**
	 * 时间段限制的次数
	 */		
	private Integer totalCount;
	/**
	 * 是否奖励推荐人0不奖励1奖励
	 */		
	private Boolean isAwardCre;
	/**
	 * 推荐人奖励方式0红包1实物 is_award_cre=1时有效
	 */		
	private Boolean creAwardType;
	/**
	 * 推荐人奖励红包的代金券规则id cre_award_type=0时有效
	 */		
	private String creVouchersActiveId;
	/**
	 * 推荐人奖励实物的商品id cre_award_type=1时有效
	 */		
	private String creProductId;
	/**
	 * 是否限制奖励推荐人次数0不限制1限制 is_award_cre=1时有效
	 */	
	private Boolean isLimitCreCount;
	/**
	 * 推荐人奖励次数 is_award_cre=1时有效
	 */	
	private Integer creAwardCount;

	// Constructors

	/** default constructor */
	public RegistDetailRule() {
	}

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

	public Integer getPerBankIntegral() {
		return perBankIntegral;
	}

	public void setPerBankIntegral(Integer perBankIntegral) {
		this.perBankIntegral = perBankIntegral;
	}

	public Integer getPerUnionIntegral() {
		return perUnionIntegral;
	}

	public void setPerUnionIntegral(Integer perUnionIntegral) {
		this.perUnionIntegral = perUnionIntegral;
	}

	public Integer getTotalBankIntegral() {
		return totalBankIntegral;
	}

	public void setTotalBankIntegral(Integer totalBankIntegral) {
		this.totalBankIntegral = totalBankIntegral;
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

	public Long getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(Long total_money) {
		this.totalMoney = total_money;
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

}