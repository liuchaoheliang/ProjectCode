package com.froad.po;

import java.util.List;

import com.froad.po.mongo.ProductBuyLimit;
import com.froad.po.mongo.ProductImage;
import com.froad.po.mongo.ProductOutlet;

public class ProductInfo {
    
    private String platType;
    private Product product;
    private ProductGroup productGroup;
    private ProductPresell productPresell;
    private ProductCategory productCategory;
    private ProductBuyLimit buyLimit;
    private List<ProductImage> productImages;
    private List<ProductOutlet> productOutlets;
    private List<ActivitiesInfo> activities;
    private String isAudit;
	/**
     * 商品对应网店所属的机构代码列表
     */
    private List<String> orgCodes;
    
    
    public String getIsAudit() {
		return isAudit;
	}
	public void setIsAudit(String isAudit) {
		this.isAudit = isAudit;
	}
    public String getPlatType() {
        return platType;
    }
    public void setPlatType(String platType) {
        this.platType = platType;
    }
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }
    public ProductGroup getProductGroup() {
        return productGroup;
    }
    public void setProductGroup(ProductGroup productGroup) {
        this.productGroup = productGroup;
    }
    public ProductPresell getProductPresell() {
        return productPresell;
    }
    public void setProductPresell(ProductPresell productPresell) {
        this.productPresell = productPresell;
    }
    public ProductCategory getProductCategory() {
        return productCategory;
    }
    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }
    public ProductBuyLimit getBuyLimit() {
        return buyLimit;
    }
    public void setBuyLimit(ProductBuyLimit buyLimit) {
        this.buyLimit = buyLimit;
    }
    public List<ProductImage> getProductImages() {
        return productImages;
    }
    public void setProductImages(List<ProductImage> productImages) {
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
