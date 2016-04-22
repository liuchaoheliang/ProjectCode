package com.froad.po;

public class BossProductFilter {

    /**
     * 客户端id即所属行id
     */
    private String clientId;
    /**
     * 商品类型  "1":团购,"2":预售,"3":名优特惠,"4":在线积分兑换,"5":网点礼品;
     */
    private String type;
    /**
     * 商品分类id
     */
    private Long categoryId;
    /**
     * 上下架状态
     */
    private String marketableStatus;
    /**
     * 审核状态
     */
    private String auditStatus;
    /**
     * 商品名称
     */
    private String name;
    /**
     * 录入渠道 "1":boss,"2":银行端,"3":商户pc,"4":商户h5,"5":个人pc,"6":个人h5;
     */
    private String platType;
    
    /**
     * 不能查出的状态
     */
    private String excludeStatuts;
    
    /**
     * 不能查出的审核状态
     */
    private String excludeAuditState;
    
    /**
     * 商品分类树
     */
    private String categoryTreePath;
    
    public String getClientId() {
        return clientId;
    }
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
    
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    
    public Long getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
    
    public String getMarketableStatus() {
        return marketableStatus;
    }
    public void setMarketableStatus(String marketableStatus) {
        this.marketableStatus = marketableStatus;
    }
    
    public String getAuditStatus() {
        return auditStatus;
    }
    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    public String getPlatType() {
        return platType;
    }
    public void setPlatType(String platType) {
        this.platType = platType;
    }
    
    public String getExcludeStatuts() {
        return excludeStatuts;
    }
    public void setExcludeStatuts(String excludeStatuts) {
        this.excludeStatuts = excludeStatuts;
    }
    
    public String getExcludeAuditState() {
        return excludeAuditState;
    }
    public void setExcludeAuditState(String excludeAuditState) {
        this.excludeAuditState = excludeAuditState;
    }
    
    public String getCategoryTreePath() {
        return categoryTreePath;
    }
    public void setCategoryTreePath(String categoryTreePath) {
        this.categoryTreePath = categoryTreePath;
    }
    
}
