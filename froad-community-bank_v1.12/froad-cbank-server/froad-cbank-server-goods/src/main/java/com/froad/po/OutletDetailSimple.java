package com.froad.po;

public class OutletDetailSimple {

    /**
     * 商户id
     */
    private String merchantId;
    /**
     * 商户名称
     */
    private String merchantName;
    /**
     * 门店Id
     */
    private String outletId;
    /**
     * 门店名称
     */
    private String outletName;
    /**
     * 门店默认图片(小图)
     */
    private String defaultImage;
    /**
     * 地址
     */
    private String address;
    /**
     * 计算出的距离
     */
    private Double dis;
    /**
     * 星评
     */
    private String starLevel;
    
    public String getMerchantId() {
        return merchantId;
    }
    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }
    public String getMerchantName() {
        return merchantName;
    }
    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
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
    public String getDefaultImage() {
        return defaultImage;
    }
    public void setDefaultImage(String defaultImage) {
        this.defaultImage = defaultImage;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public Double getDis() {
        return dis;
    }
    public void setDis(Double dis) {
        this.dis = dis;
    }
    public String getStarLevel() {
        return starLevel;
    }
    public void setStarLevel(String starLevel) {
        this.starLevel = starLevel;
    }
}
