package com.froad.po;

import java.util.List;

import com.froad.po.mongo.ProductBuyLimit;
import com.froad.po.mongo.ProductImage;
import com.froad.po.mongo.ProductOutlet;

public class ProductInfo {
    
    private String vipId;//有关联上VIP规则id
    private Product product;//商品基础信息
    private ProductGroup productGroup;//团购商品子信息
    private ProductPresell productPresell;//预售商品子信息
    private ProductCategory productCategory;//商品分类
    private ProductBuyLimit buyLimit;//限购对象
    private List<ProductImage> productImages;//图片列表
    private List<ProductOutlet> productOutlets;//商品对应提货网店列表
    private List<String> orgCodes;//商品对应网店所属的法人行社机构代码列表
    
    public String getVipId() {
        return vipId;
    }
    public void setVipId(String vipId) {
        this.vipId = vipId;
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
    
    public List<String> getOrgCodes() {
        return orgCodes;
    }
    public void setOrgCodes(List<String> orgCodes) {
        this.orgCodes = orgCodes;
    }

}
