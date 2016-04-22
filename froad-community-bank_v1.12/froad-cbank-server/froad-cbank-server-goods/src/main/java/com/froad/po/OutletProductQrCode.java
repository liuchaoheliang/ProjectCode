package com.froad.po;

public class OutletProductQrCode {
    
    /**
     * 二维码
     */
    private String qrCode;
    /**
     * 商品id
     */
    private String productId;
    /**
     * 备注
     */
    private String url;
    
    public String getQrCode() {
        return qrCode;
    }
    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }
    public String getProductId() {
        return productId;
    }
    public void setProductId(String productId) {
        this.productId = productId;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

}
