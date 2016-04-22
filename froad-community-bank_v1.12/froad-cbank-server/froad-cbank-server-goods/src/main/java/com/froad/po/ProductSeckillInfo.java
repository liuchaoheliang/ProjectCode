package com.froad.po;

import java.util.List;

import com.froad.po.mongo.ProductBuyLimit;
import com.froad.po.mongo.ProductImage;
import com.froad.po.mongo.ProductOutlet;

public class ProductSeckillInfo {
    
    private String platType;
    private ProductSeckill productSeckill;
    private ProductGroup productGroup;
    private ProductPresell productPresell;
    private ProductCategory productCategory;
    private ProductBuyLimit buyLimit;
    private List<ProductImage> productImages;
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
