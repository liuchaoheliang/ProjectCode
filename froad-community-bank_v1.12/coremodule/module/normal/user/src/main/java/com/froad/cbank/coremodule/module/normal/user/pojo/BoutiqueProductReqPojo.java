/**
 * Project Name:coremodule-user
 * File Name:BoutiqueProductPojo.java
 * Package Name:com.froad.cbank.coremodule.module.normal.user.pojo
 * Date:2015年11月27日下午4:15:33
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.cbank.coremodule.module.normal.user.pojo;
/**
 * ClassName:BoutiqueProductPojo
 * Reason:	 TODO ADD REASON.
 * Date:     2015年11月27日 下午4:15:33
 * @author   wm
 * @version  
 * @see 	 
 */
public class BoutiqueProductReqPojo extends PagePojo{
	
	private String clientId;
	
	/**
	 * 商品名
	 */
	private String productName;
	/**
	 * 商品分类ID
	 */
	private long productCategoryId;
	/**
	 * 是否类目推荐商品
	 */
	private String isRecommend;
	/**
	 * 是否热销商品
	 */
	private String isHot;
	/**
	 * '1'推荐;'2'热销;'3'新品
	 */
	private String goodFlag;
	
	/**
	 * -1负数代表降序，1整数代表升序
	 */
	private int sortBy;
	/**
	 *  1  推荐优先 我的VIP页面里的为您推荐用到
	 * 	2  综合排序
	 * 	3  销量排序
	 * 	4   价格排序
	 */
	private String sortCode;
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public long getProductCategoryId() {
		return productCategoryId;
	}
	public void setProductCategoryId(long productCategoryId) {
		this.productCategoryId = productCategoryId;
	}
	public String getIsRecommend() {
		return isRecommend;
	}
	public void setIsRecommend(String isRecommend) {
		this.isRecommend = isRecommend;
	}
	public String getIsHot() {
		return isHot;
	}
	public void setIsHot(String isHot) {
		this.isHot = isHot;
	}
	public int getSortBy() {
		return sortBy;
	}
	public void setSortBy(int sortBy) {
		this.sortBy = sortBy;
	}
	public String getSortCode() {
		return sortCode;
	}
	public void setSortCode(String sortCode) {
		this.sortCode = sortCode;
	}
	public String getGoodFlag() {
		return goodFlag;
	}
	public void setGoodFlag(String goodFlag) {
		this.goodFlag = goodFlag;
	}
}
