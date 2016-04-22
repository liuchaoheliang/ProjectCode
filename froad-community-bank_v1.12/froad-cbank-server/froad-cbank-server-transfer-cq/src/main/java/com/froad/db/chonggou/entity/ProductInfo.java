package com.froad.db.chonggou.entity;

import java.util.List;



public class ProductInfo {
    
    private String platType;
    private ProductCG product;
    private ProductGroupCG productGroup;
    private ProductPresellCG productPresell;
    private ProductCategoryCG productCategory;
    private ProductBuyLimit buyLimit;
    private List<ProductImageCG> productImages;
    private List<ProductOutlet> productOutlets;
    private List<ActivitiesInfo> activities;
    /**
     * 商品对应网店所属的机构代码列表
     */
    private List<String> orgCodes;
    
    public String getPlatType() {
        return platType;
    }
    public void setPlatType(String platType) {
        this.platType = platType;
    }
    public ProductCG getProduct() {
        return product;
    }
    public void setProduct(ProductCG product) {
        this.product = product;
    }
    public ProductGroupCG getProductGroup() {
        return productGroup;
    }
    public void setProductGroup(ProductGroupCG productGroup) {
        this.productGroup = productGroup;
    }
    public ProductPresellCG getProductPresell() {
        return productPresell;
    }
    public void setProductPresell(ProductPresellCG productPresell) {
        this.productPresell = productPresell;
    }
    public ProductCategoryCG getProductCategory() {
        return productCategory;
    }
    public void setProductCategory(ProductCategoryCG productCategory) {
        this.productCategory = productCategory;
    }
    public ProductBuyLimit getBuyLimit() {
        return buyLimit;
    }
    public void setBuyLimit(ProductBuyLimit buyLimit) {
        this.buyLimit = buyLimit;
    }
    public List<ProductImageCG> getProductImages() {
        return productImages;
    }
    public void setProductImages(List<ProductImageCG> productImages) {
        this.productImages = productImages;
    }
    public List<ProductOutlet> getProductOutlets() {
        return productOutlets;
    }
    public void setProductOutlets(List<ProductOutlet> productOutlets) {
        this.productOutlets = productOutlets;
    }
    public List<ActivitiesInfo> getActivities() {
        return activities;
    }
    public void setActivities(List<ActivitiesInfo> activities) {
        this.activities = activities;
    }
    public List<String> getOrgCodes() {
        return orgCodes;
    }
    public void setOrgCodes(List<String> orgCodes) {
        this.orgCodes = orgCodes;
    }

}
