package com.froad.cbank.coremodule.normal.boss.pojo.product;

import com.froad.cbank.coremodule.normal.boss.pojo.Page;

/**
 * 
 * @ClassName: ProductListVoReq
 * @author chenzhangwei@f-road.com.cn
 * @createTime 2015年11月25日 上午9:34:50 
 * @desc <p>精品商城商品列表查询请求实体</p>
 */
public class ProductListVoReq extends Page{
	/**
	 * 商品编号
	 */
	private String productId;
	/**
	 * 商品名称
	 */
	private String productName;
	/**
	 * 搜索关键字
	 */
	private String seoKeyWords;
	/**
	 * 客户端ID
	 */
	private String clientId;
	/**
	 * 商品分类id
	 */
	private Long categoryId;
	
	/**
	   * 是否秒杀 0非秒杀,1秒杀,2秒杀未上架
	   */
	  private String isSeckill; // required
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getSeoKeyWords() {
		return seoKeyWords;
	}
	public void setSeoKeyWords(String seoKeyWords) {
		this.seoKeyWords = seoKeyWords;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	public String getIsSeckill() {
		return isSeckill;
	}
	public void setIsSeckill(String isSeckill) {
		this.isSeckill = isSeckill;
	}
	
	
	
}
