package com.froad.CB.po;

public class RuleDetailTemplet {
	private String formulaOfDetailRule;
	private String ruleDetailCategory;//规则明细的类别-资金，积分，虚拟商品
	private Integer id;
	private Integer seqNo;	//顺序
	private String ruleType;//规则类型
	private String toAccountType;	//到账户类型
	private String fromAccountType;	//从账户类型
	private String remark;	//备注
	private String updateTime;
	private String createTime;
	private String state;
	private String description;
	private String paramOfFormulaFrom;
	private String paramOfFormula;
	private String settleType;
	private String useTime;
	private String clazzName;
	private String detailRuleFormulaFrom;
	private String detailRuleFormulaFromType;
	private String calculateType;
	private String toRole;
	private String fromRole;
	private String ruleDetailType;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRuleDetailCategory() {
		return ruleDetailCategory;
	}

	public void setRuleDetailCategory(String ruleDetailCategory) {
		this.ruleDetailCategory = ruleDetailCategory;
	}

	public String getDetailRuleFormulaFromType() {
		return detailRuleFormulaFromType;
	}

	public void setDetailRuleFormulaFromType(String detailRuleFormulaFromType) {
		this.detailRuleFormulaFromType = detailRuleFormulaFromType;
	}

	public Integer getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(Integer seqNo) {
		this.seqNo = seqNo;
	}

	public String getRuleType() {
		return ruleType;
	}

	public void setRuleType(String ruleType) {
		this.ruleType = ruleType == null ? null : ruleType.trim();
	}

	public String getToAccountType() {
		return toAccountType;
	}

	public void setToAccountType(String toAccountType) {
		this.toAccountType = toAccountType == null ? null : toAccountType
				.trim();
	}

	public String getFromAccountType() {
		return fromAccountType;
	}

	public void setFromAccountType(String fromAccountType) {
		this.fromAccountType = fromAccountType == null ? null : fromAccountType
				.trim();
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime == null ? null : updateTime.trim();
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime == null ? null : createTime.trim();
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state == null ? null : state.trim();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description == null ? null : description.trim();
	}

	public String getParamOfFormulaFrom() {
		return paramOfFormulaFrom;
	}

	public void setParamOfFormulaFrom(String paramOfFormulaFrom) {
		this.paramOfFormulaFrom = paramOfFormulaFrom == null ? null
				: paramOfFormulaFrom.trim();
	}

	public String getParamOfFormula() {
		return paramOfFormula;
	}

	public void setParamOfFormula(String paramOfFormula) {
		this.paramOfFormula = paramOfFormula == null ? null : paramOfFormula
				.trim();
	}

	public String getSettleType() {
		return settleType;
	}

	public void setSettleType(String settleType) {
		this.settleType = settleType == null ? null : settleType.trim();
	}

	public String getUseTime() {
		return useTime;
	}

	public void setUseTime(String useTime) {
		this.useTime = useTime == null ? null : useTime.trim();
	}

	public String getClazzName() {
		return clazzName;
	}

	public void setClazzName(String clazzName) {
		this.clazzName = clazzName == null ? null : clazzName.trim();
	}

	public String getDetailRuleFormulaFrom() {
		return detailRuleFormulaFrom;
	}

	public void setDetailRuleFormulaFrom(String detailRuleFormulaFrom) {
		this.detailRuleFormulaFrom = detailRuleFormulaFrom;
	}

	public String getCalculateType() {
		return calculateType;
	}

	public void setCalculateType(String calculateType) {
		this.calculateType = calculateType == null ? null : calculateType
				.trim();
	}

	public String getToRole() {
		return toRole;
	}

	public void setToRole(String toRole) {
		this.toRole = toRole == null ? null : toRole.trim();
	}

	public String getFromRole() {
		return fromRole;
	}

	public void setFromRole(String fromRole) {
		this.fromRole = fromRole == null ? null : fromRole.trim();
	}

	public String getRuleDetailType() {
		return ruleDetailType;
	}

	public void setRuleDetailType(String ruleDetailType) {
		this.ruleDetailType = ruleDetailType == null ? null : ruleDetailType
				.trim();
	}

	public String getFormulaOfDetailRule() {
		return formulaOfDetailRule;
	}

	public void setFormulaOfDetailRule(String formulaOfDetailRule) {
		this.formulaOfDetailRule = formulaOfDetailRule;
	}

	@Override
	public String toString() {
		return "RuleDetailTemplet [calculateType=" + calculateType
				+ ", clazzName=" + clazzName + ", createTime=" + createTime
				+ ", description=" + description + ", detailRuleFormulaFrom="
				+ detailRuleFormulaFrom + ", detailRuleFormulaFromType="
				+ detailRuleFormulaFromType + ", formulaOfDetailRule="
				+ formulaOfDetailRule + ", fromAccountType=" + fromAccountType
				+ ", fromRole=" + fromRole + ", id=" + id + ", paramOfFormula="
				+ paramOfFormula + ", paramOfFormulaFrom=" + paramOfFormulaFrom
				+ ", remark=" + remark + ", ruleDetailCategory="
				+ ruleDetailCategory + ", ruleDetailType=" + ruleDetailType
				+ ", ruleType=" + ruleType + ", seqNo=" + seqNo
				+ ", settleType=" + settleType + ", state=" + state
				+ ", toAccountType=" + toAccountType + ", toRole=" + toRole
				+ ", updateTime=" + updateTime + ", useTime=" + useTime + "]";
	}

	

}