package com.froad.po;

import java.util.Date;

public class ReportBossOrgMerchant {
    private Long id;

    private Integer reportDate;

    private Date reportBegDate;

    private Date reportEndDate;

    private Date createTime;

    private String clientId;

    private String orgCode;

    private String orgName;

    private Integer addnewGroupCount;

    private Integer totalGroupCount;

    private Integer addnewPreferentCount;

    private Integer totalPreferentCount;

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

    public Integer getAddnewGroupCount() {
        return addnewGroupCount;
    }

    public void setAddnewGroupCount(Integer addnewGroupCount) {
        this.addnewGroupCount = addnewGroupCount;
    }

    public Integer getTotalGroupCount() {
        return totalGroupCount;
    }

    public void setTotalGroupCount(Integer totalGroupCount) {
        this.totalGroupCount = totalGroupCount;
    }

    public Integer getAddnewPreferentCount() {
        return addnewPreferentCount;
    }

    public void setAddnewPreferentCount(Integer addnewPreferentCount) {
        this.addnewPreferentCount = addnewPreferentCount;
    }

    public Integer getTotalPreferentCount() {
        return totalPreferentCount;
    }

    public void setTotalPreferentCount(Integer totalPreferentCount) {
        this.totalPreferentCount = totalPreferentCount;
    }
}