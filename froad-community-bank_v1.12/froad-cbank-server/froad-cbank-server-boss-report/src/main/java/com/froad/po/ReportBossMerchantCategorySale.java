package com.froad.po;

import java.util.Date;

public class ReportBossMerchantCategorySale {
    private Long id;

    private Integer reportDate;

    private Date reportBegDate;

    private Date reportEndDate;

    private Date createTime;

    private String clientId;

    private Long merchantCategoryId;

    private String merchantCategoryName;

    private Long totalSoldCount;

    private String totalSoldCountPercent;

    private Long totalSoldAmount;

    private String totalSoldAmountPercent;

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

    public Long getMerchantCategoryId() {
        return merchantCategoryId;
    }

    public void setMerchantCategoryId(Long merchantCategoryId) {
        this.merchantCategoryId = merchantCategoryId;
    }

    public String getMerchantCategoryName() {
        return merchantCategoryName;
    }

    public void setMerchantCategoryName(String merchantCategoryName) {
        this.merchantCategoryName = merchantCategoryName == null ? null : merchantCategoryName.trim();
    }

    public Long getTotalSoldCount() {
        return totalSoldCount;
    }

    public void setTotalSoldCount(Long totalSoldCount) {
        this.totalSoldCount = totalSoldCount;
    }

    public String getTotalSoldCountPercent() {
        return totalSoldCountPercent;
    }

    public void setTotalSoldCountPercent(String totalSoldCountPercent) {
        this.totalSoldCountPercent = totalSoldCountPercent == null ? null : totalSoldCountPercent.trim();
    }

    public Long getTotalSoldAmount() {
        return totalSoldAmount;
    }

    public void setTotalSoldAmount(Long totalSoldAmount) {
        this.totalSoldAmount = totalSoldAmount;
    }

    public String getTotalSoldAmountPercent() {
        return totalSoldAmountPercent;
    }

    public void setTotalSoldAmountPercent(String totalSoldAmountPercent) {
        this.totalSoldAmountPercent = totalSoldAmountPercent == null ? null : totalSoldAmountPercent.trim();
    }
}