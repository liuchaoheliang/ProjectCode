package com.froad.po;

public class ProductAreaOutlet {

    /**
     * 商品Id
     */
    private String productId;
    /**
     * 客户端id
     */
    private String clientId;
    /**
     * 区域id
     */
    private Long areaId;
    /**
     * 门店id
     */
    private String outletId;
    /**
     * 门店名称
     */
    private String outletName;
    /**
     * 商户id
     */
    private String merchantId;
    /**
     * 门店对应机构编号
     */
    private String orgCode;
    
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
    
    public Long getAreaId() {
        return areaId;
    }
    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }
    
    public String getOutletId() {
        return outletId;
    }
    public void setOutletId(String outletId) {
        this.outletId = outletId;
    }
    
    public String getOutletName() {
        return outletName;
    }
    public void setOutletName(String outletName) {
        this.outletName = outletName;
    }
    
    public String getMerchantId() {
        return merchantId;
    }
    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }
    
    public String getOrgCode() {
        return orgCode;
    }
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }
    
}
