package com.froad.po;

import java.util.Date;

public class ReportBossOrgSale {
    private Long id;

    private Integer reportDate;

    private Date reportBegDate;

    private Date reportEndDate;

    private Date createTime;

    private String clientId;
    
    private String orderType;
    
    private String orgCode;

    private String orgName;

    private Long curSoldCount;

    private Long curSoldAmount;

    private Long totalSoldCount;

    private Long totalSoldAmount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getReportDate() {
        return reportDate;
    }

    public void setReportDate(Integer reportDate) {
        this.reportDate = reportDate;
    }

    public Date getReportBegDate() {
        return reportBegDate;
    }

    public void setReportBegDate(Date reportBegDate) {
        this.reportBegDate = reportBegDate;
    }

    public Date getReportEndDate() {
        return reportEndDate;
    }

    public void setReportEndDate(Date reportEndDate) {
        this.reportEndDate = reportEndDate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId == null ? null : clientId.trim();
    }
    
	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode == null ? null : orgCode.trim();
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public Long getCurSoldCount() {
        return curSoldCount;
    }

    public void setCurSoldCount(Long curSoldCount) {
        this.curSoldCount = curSoldCount;
    }

    public Long getCurSoldAmount() {
        return curSoldAmount;
    }

    public void setCurSoldAmount(Long curSoldAmount) {
        this.curSoldAmount = curSoldAmount;
    }

    public Long getTotalSoldCount() {
        return totalSoldCount;
    }

    public void setTotalSoldCount(Long totalSoldCount) {
        this.totalSoldCount = totalSoldCount;
    }

    public Long getTotalSoldAmount() {
        return totalSoldAmount;
    }

    public void setTotalSoldAmount(Long totalSoldAmount) {
        this.totalSoldAmount = totalSoldAmount;
    }
}