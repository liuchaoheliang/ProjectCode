package com.froad.db.chonggou.entity;

import java.util.List;



public class ProductSeckillInfo {
    
    private String platType;
    private ProductSeckill productSeckill;
    private ProductGroupCG productGroup;
    private ProductPresellCG productPresell;
    private ProductCategoryCG productCategory;
    private ProductBuyLimit buyLimit;
    private List<ProductImageCG> productImages;
    private List<ProductOutlet> productOutlets;
    private List<ActivitiesInfo> activities;
    private List<String> productIdList;
    private List<Double> secPriceList;
    private List<Double> vipSecPriceList;
    private List<String> orgCodes;
	public String getPlatType() {
		return platType;
	}
	public void setPlatType(String platType) {
		this.platType = platType;
	}
	public ProductSeckill getProductSeckill() {
		return productSeckill;
	}
	public void setProductSeckill(ProductSeckill productSeckill) {
		this.productSeckill = productSeckill;
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
	public List<String> getProductIdList() {
		return productIdList;
	}
	public void setProductIdList(List<String> productIdList) {
		this.productIdList = productIdList;
	}
	public List<Double> getSecPriceList() {
		return secPriceList;
	}
	public void setSecPriceList(List<Double> secPriceList) {
		this.secPriceList = secPriceList;
	}
	public List<Double> getVipSecPriceList() {
		return vipSecPriceList;
	}
	public void setVipSecPriceList(List<Double> vipSecPriceList) {
		this.vipSecPriceList = vipSecPriceList;
	}
	public List<String> getOrgCodes() {
		return orgCodes;
	}
	public void setOrgCodes(List<String> orgCodes) {
		this.orgCodes = orgCodes;
	}

    
    
}
