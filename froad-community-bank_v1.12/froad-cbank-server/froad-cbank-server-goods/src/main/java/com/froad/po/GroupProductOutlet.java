package com.froad.po;

import java.util.List;

import com.froad.po.mongo.ProductDetail;

public class GroupProductOutlet {
    
    /**
     * 客户端id
     */
    private String clientId;
    /**
     * 门店ID
     */
    private String outletId;
    /**
     * 店名
     */
    private String outletName;
    /**
     * 门店地址
     */
    private String address;
    /**
     * 门店评论星级
     */
    private Integer starLevel;
    /**
     * 距离
     */
    private Double distance;
    /**
     * 商户ID
     */
    private String merchantId;
    /**
     * 商户名
     */
    private String merchantName;
    
    /**
     * 门店下特惠商品列表
     */
    private List<ProductDetail> products;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getStarLevel() {
        return starLevel;
    }

    public void setStarLevel(Integer starLevel) {
        this.starLevel = starLevel;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

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

    public List<ProductDetail> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDetail> products) {
        this.products = products;
    }

}
