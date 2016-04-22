package com.froad.po;

import java.util.Date;

public class ProductBase {

    /**
     * 客户端id
     */
    private String clientId;
    /**
     * 商品id
     */
    private String productId;
    /**
     * 商品库存数量
     */
    private Integer store;
    /**
     * 团购或预售有效期开始
     */
    private Date startTime;
    /**
     * 团购或预售有效期结束
     */
    private Date endTime;
    /**
     * 团购或预售券有效起始日
     */
    private Date expireStartTime;
    /**
     * 团购或预售券有效结束日
     */
    private Date expireEndTime;
    /**
     * 待审核机构编号
     */
    private String auditOrgCode;
    /**
     * 起始审核机构编号
     */
    private String auditStartOrgCode;
    /**
     * 最终审核机编号
     */
    private String auditEndOrgCode;
    
    public String getClientId() {
        return clientId;
    }
    
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
    
    public String getProductId() {
        return productId;
    }
    
    public void setProductId(String productId) {
        this.productId = productId;
    }
    
    public Integer getStore() {
        return store;
    }
    
    public void setStore(Integer store) {
        this.store = store;
    }
    
    public Date getStartTime() {
        return startTime;
    }
    
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
    
    public Date getEndTime() {
        return endTime;
    }
    
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
    
    public Date getExpireStartTime() {
        return expireStartTime;
    }
    
    public void setExpireStartTime(Date expireStartTime) {
        this.expireStartTime = expireStartTime;
    }
    
    public Date getExpireEndTime() {
        return expireEndTime;
    }
    
    public void setExpireEndTime(Date expireEndTime) {
        this.expireEndTime = expireEndTime;
    }
    
    public String getAuditOrgCode() {
        return auditOrgCode;
    }
    
    public void setAuditOrgCode(String auditOrgCode) {
        this.auditOrgCode = auditOrgCode;
    }
    
    public String getAuditStartOrgCode() {
        return auditStartOrgCode;
    }
    
    public void setAuditStartOrgCode(String auditStartOrgCode) {
        this.auditStartOrgCode = auditStartOrgCode;
    }
    
    public String getAuditEndOrgCode() {
        return auditEndOrgCode;
    }
    
    public void setAuditEndOrgCode(String auditEndOrgCode) {
        this.auditEndOrgCode = auditEndOrgCode;
    }
    
}
