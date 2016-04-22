package com.froad.db.chonggou.entity;

public class ProductAudit {
    
    /**
     * 商品Id
     */
    private String productId;
    /**
     * 客户端id
     */
    private String clientId;
    /**
     * 商户ID
     */
    private String merchantId;
    /**
     * 审核机构代码
     */
    private String auditOrgCode;
    /**
     * 机构代码
     */
    private String orgCode;
    /**
     * 商品审核状态(1审核通过,2审核未通过)
     */
    private String auditState;
    /**
     * 审核步骤
     */
    private String auditStage;
    /**
     * 审核人
     */
    private String auditStaff;
    /**
     * 银行用户所属机构代码-审核人所属机构编号
     */
    private String auditOrg;
    /**
     * 审核备注
     */
    private String auditComment;
    
    public String getProductId() {
        return productId;
    }
    public void setProductId(String productId) {
        this.productId = productId;
    }
    public String getClientId() {
        return clientId;
    }
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
    public String getMerchantId() {
        return merchantId;
    }
    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }
    public String getAuditOrgCode() {
        return auditOrgCode;
    }
    public void setAuditOrgCode(String auditOrgCode) {
        this.auditOrgCode = auditOrgCode;
    }
    public String getOrgCode() {
        return orgCode;
    }
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }
    public String getAuditState() {
        return auditState;
    }
    public void setAuditState(String auditState) {
        this.auditState = auditState;
    }
    public String getAuditStage() {
        return auditStage;
    }
    public void setAuditStage(String auditStage) {
        this.auditStage = auditStage;
    }
    public String getAuditStaff() {
        return auditStaff;
    }
    public void setAuditStaff(String auditStaff) {
        this.auditStaff = auditStaff;
    }
    public String getAuditOrg() {
        return auditOrg;
    }
    public void setAuditOrg(String auditOrg) {
        this.auditOrg = auditOrg;
    }
    public String getAuditComment() {
        return auditComment;
    }
    public void setAuditComment(String auditComment) {
        this.auditComment = auditComment;
    }

}
