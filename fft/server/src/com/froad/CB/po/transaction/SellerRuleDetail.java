package com.froad.CB.po.transaction;

import java.io.Serializable;


	/**
	 * 类描述：卖家规则详情
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2012 
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: Dec 13, 2012 5:43:12 PM 
	 */
public class SellerRuleDetail implements Serializable{
	
	
	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = 1L;

	private Integer id;

	private String transType;

	private String toAccountType;

	private String fromAccountType;

	private String remark;

	private String updateTime;

	private String createTime;

	private String state;

	private String description;

	private String endTime;

	private String startTime;

	private String intervalTime;

	private String paymentStartTime;

	private String paramOfFormula;

	private String settleType;

	private String useTime;

	private String sellerRuleId;

	private String clazzName;

	private String formula;

	private String countType;

	private String toRole;

	private String fromRole;

	private String ruleType;
	
	private String paramOfFormulaFrom;//公式的参数来源于   
	
	private String detailRuleFrom;
	

	public String getDetailRuleFrom() {
		return detailRuleFrom;
	}

	public void setDetailRuleFrom(String detailRuleFrom) {
		this.detailRuleFrom = detailRuleFrom;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType == null ? null : transType.trim();
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

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime == null ? null : endTime.trim();
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime == null ? null : startTime.trim();
	}

	public String getIntervalTime() {
		return intervalTime;
	}

	public void setIntervalTime(String intervalTime) {
		this.intervalTime = intervalTime == null ? null : intervalTime.trim();
	}

	public String getPaymentStartTime() {
		return paymentStartTime;
	}

	public void setPaymentStartTime(String paymentStartTime) {
		this.paymentStartTime = paymentStartTime == null ? null
				: paymentStartTime.trim();
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

	public String getSellerRuleId() {
		return sellerRuleId;
	}

	public void setSellerRuleId(String sellerRuleId) {
		this.sellerRuleId = sellerRuleId == null ? null : sellerRuleId.trim();
	}

	public String getClazzName() {
		return clazzName;
	}

	public void setClazzName(String clazzName) {
		this.clazzName = clazzName == null ? null : clazzName.trim();
	}

	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula == null ? null : formula.trim();
	}

	public String getCountType() {
		return countType;
	}

	public void setCountType(String countType) {
		this.countType = countType == null ? null : countType.trim();
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

	public String getRuleType() {
		return ruleType;
	}

	public void setRuleType(String ruleType) {
		this.ruleType = ruleType == null ? null : ruleType.trim();
	}

	public String getParamOfFormulaFrom() {
		return paramOfFormulaFrom;
	}

	public void setParamOfFormulaFrom(String paramOfFormulaFrom) {
		this.paramOfFormulaFrom = paramOfFormulaFrom;
	}
}