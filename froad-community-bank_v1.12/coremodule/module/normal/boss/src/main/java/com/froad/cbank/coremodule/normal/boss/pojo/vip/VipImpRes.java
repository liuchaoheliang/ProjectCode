package com.froad.cbank.coremodule.normal.boss.pojo.vip;


/**
 * VIP导入批次响应对象
 * @author 陆万全 luwanquan@f-road.com.cn
 * @date 创建时间：2015年7月2日 上午10:33:08
 */
public class VipImpRes {
	private Long batchCode;	//批次ID
	private String fileName;//导入CSV文件名
	private Integer count;	//导入会员数量
	private String description;//备注（事由）
	private String descriptionFail;//审核未通过备注
	private String createType;//创建方式
	private Long createTime;//创建时间
	private Long auditTime;//审核时间
	private Long execTime;//执行时间
	private String status;//批次状态（0-待审核，1-提交审核，2-审核通过，3-执行，4-审核未通过，5-废弃）
	private String operator;//操作人
	private String checker;//审核人
	private String bankOrg;//银行标识
	private String labelId;//会员标签ID
	private String labelName;//会员标签名称
	private Integer defaultPeriod;//会员期限（例如：365）
	private String defaultLevel;//会员等级
	
	public Long getBatchCode() {
		return batchCode;
	}
	public void setBatchCode(Long batchCode) {
		this.batchCode = batchCode;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDescriptionFail() {
		return descriptionFail;
	}
	public void setDescriptionFail(String descriptionFail) {
		this.descriptionFail = descriptionFail;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public Long getAuditTime() {
		return auditTime;
	}
	public void setAuditTime(Long auditTime) {
		this.auditTime = auditTime;
	}
	public Long getExecTime() {
		return execTime;
	}
	public void setExecTime(Long execTime) {
		this.execTime = execTime;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getChecker() {
		return checker;
	}
	public void setChecker(String checker) {
		this.checker = checker;
	}
	public String getLabelName() {
		return labelName;
	}
	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}
	public String getLabelId() {
		return labelId;
	}
	public void setLabelId(String labelId) {
		this.labelId = labelId;
	}
	public Integer getDefaultPeriod() {
		return defaultPeriod;
	}
	public void setDefaultPeriod(Integer defaultPeriod) {
		this.defaultPeriod = defaultPeriod;
	}
	public String getCreateType() {
		return createType;
	}
	public void setCreateType(String createType) {
		this.createType = createType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getBankOrg() {
		return bankOrg;
	}
	public void setBankOrg(String bankOrg) {
		this.bankOrg = bankOrg;
	}
	public String getDefaultLevel() {
		return defaultLevel;
	}
	public void setDefaultLevel(String defaultLevel) {
		this.defaultLevel = defaultLevel;
	}
}
